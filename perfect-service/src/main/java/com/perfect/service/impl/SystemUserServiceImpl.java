package com.perfect.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.perfect.api.baidu.BaiduApiService;
import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ResHeaderUtil;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.commons.constants.PasswordSalts;
import com.perfect.commons.constants.SystemNameConstant;
import com.perfect.core.AppContext;
import com.perfect.dao.account.AccountManageDAO;
import com.perfect.dao.account.SystemAccountDAO;
import com.perfect.dao.adgroup.AdgroupDAO;
import com.perfect.dao.campaign.CampaignDAO;
import com.perfect.dao.creative.CreativeDAO;
import com.perfect.dao.keyword.KeywordDAO;
import com.perfect.dao.sys.SystemLogDAO;
import com.perfect.dao.sys.SystemModuleDAO;
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.dto.sys.*;
import com.perfect.service.SystemUserService;
import com.perfect.utils.EntityConvertUtils;
import com.perfect.utils.MD5;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.SystemUserUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.elasticsearch.common.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;

/**
 * Created by yousheng on 2014/8/12.
 *
 * @author yousheng
 */
@Service("systemUserService")
public class SystemUserServiceImpl implements SystemUserService {

    private Logger logger = LoggerFactory.getLogger(SystemUserServiceImpl.class);

    private final String user_salt = PasswordSalts.USER_SALT;

    @Resource
    private SystemUserDAO systemUserDAO;

    @Resource
    private AccountManageDAO accountManageDAO;

    @Resource
    private CampaignDAO campaignDAO;

    @Resource
    private AdgroupDAO adgroupDAO;

    @Resource
    private KeywordDAO keywordDAO;

    @Resource
    private CreativeDAO creativeDAO;

    @Resource
    private SystemModuleDAO systemModuleDAO;

    @Resource
    private SystemLogDAO systemLogDAO;

    @Resource
    private SystemAccountDAO systemAccountDAO;

    @Override
    public void initAccount(String userName, Long accountId) {
        logger.info("开始导入数据: 用户名=" + userName + ", 账号= " + accountId);

        SystemUserDTO systemUserDTO = getSystemUser(userName);
        if (systemUserDTO == null) {
            logger.warn("没有此账号: " + userName);
            return;
        }


        logger.info("清理已有数据...");
        clearAccountData(accountId);
        logger.info("清理数据完成!");

        systemUserDTO.getSystemUserModules().stream().filter((systemUserModuleDTO -> systemUserModuleDTO.getModuleName().equals(AppContext.getModuleName()))).findFirst().ifPresent((systemUserModuleDTO1 -> {
            systemUserModuleDTO1.getAccounts().forEach((moduleAccountInfoDTO -> {
                Long aid = moduleAccountInfoDTO.getBaiduAccountId();
                if (!Objects.equals(aid, accountId))
                    return;
                CommonService commonService = BaiduServiceSupport.getCommonService(moduleAccountInfoDTO.getBaiduUserName(), moduleAccountInfoDTO.getBaiduPassword(), moduleAccountInfoDTO.getToken());
                BaiduApiService apiService = new BaiduApiService(commonService);

                logger.info("查询账户信息...");
                // 初始化账户数据
                AccountInfoType accountInfoType = apiService.getAccountInfo();
                if (accountInfoType == null) {
                    logger.error("获取账户信息错误: " + ResHeaderUtil.getJsonResHeader(false).toString());
                    return;
                }
                boolean isDefault = moduleAccountInfoDTO.isDfault();
                moduleAccountInfoDTO = ObjectUtils.convert(accountInfoType, ModuleAccountInfoDTO.class);
                moduleAccountInfoDTO.setBaiduAccountId(accountInfoType.getUserid());

                // TODO 为什么要重新设置 @subDong
//                baiduAccountInfoDTO.setBaiduUserName(baiduAccountInfoDTO.getBaiduUserName());
//                baiduAccountInfoDTO.setBaiduPassword(baiduAccountInfoDTO.getBaiduPassword());
//                baiduAccountInfoDTO.setToken(baiduAccountInfoDTO.getToken());
                moduleAccountInfoDTO.setDfault(isDefault);

                //新增百度账户
                systemUserDAO.insertAccountInfo(userName, moduleAccountInfoDTO);

                logger.info("查询账户推广计划...");
                List<CampaignType> campaignTypes = apiService.getAllCampaign();
                logger.info("查询结束: 计划数=" + campaignTypes.size());

                List<CampaignDTO> campaignEntities = EntityConvertUtils.convertToCamEntity(campaignTypes);

                // 查询推广单元
                List<Long> ids = new ArrayList<>(campaignEntities.size());

                for (CampaignDTO campaignEntity : campaignEntities) {
                    campaignEntity.setAccountId(aid);
                    ids.add(campaignEntity.getCampaignId());
                }

                logger.info("查询账户推广单元...");
                List<AdgroupType> adgroupTypeList = apiService.getAllAdGroup(ids);

                logger.info("查询结束: 单元数=" + adgroupTypeList.size());

                List<AdgroupDTO> adgroupEntities = EntityConvertUtils.convertToAdEntity(adgroupTypeList);
                ids.clear();
                for (AdgroupDTO adgroupEntity : adgroupEntities) {
                    adgroupEntity.setAccountId(aid);
                    ids.add(adgroupEntity.getAdgroupId());
                }

                logger.info("查询账户推广关键词...");

//            List<KeywordType> keywordTypes = apiService.getAllKeyword(ids);
//            logger.info("查询结束: 关键词数=" + keywordTypes.size());
//
//            List<KeywordDTO> keywordEntities = EntityConvertUtils.convertToKwEntity(keywordTypes);
//
//            for (KeywordDTO keywordEntity : keywordEntities) {
//                keywordEntity.setAccountId(aid);
//            }

                //分批次请求关键词数据
                List<Long> subList = new ArrayList<>(4);
                for (int i = 1; i <= ids.size(); i++) {
                    Long adgroupId = ids.get(i - 1);
                    subList.add(adgroupId);

                    if (i % 4 == 0) {
                        List<KeywordType> keywordTypes = apiService.getAllKeyword(subList);
                        List<KeywordDTO> keywordEntities = EntityConvertUtils.convertToKwEntity(keywordTypes);

                        for (KeywordDTO keywordEntity : keywordEntities) {
                            keywordEntity.setAccountId(aid);
                        }
                        keywordDAO.save(keywordEntities);
                        subList.clear();
                    }
                }


                if (!subList.isEmpty()) {
                    List<KeywordType> keywordTypes = apiService.getAllKeyword(subList);
                    List<KeywordDTO> keywordEntities = EntityConvertUtils.convertToKwEntity(keywordTypes);

                    for (KeywordDTO keywordEntity : keywordEntities) {
                        keywordEntity.setAccountId(aid);
                    }
                    keywordDAO.save(keywordEntities);
                    subList.clear();
                }

                logger.info("查询账户推广创意...");
                List<CreativeType> creativeTypes = apiService.getAllCreative(ids);
                logger.info("查询结束: 普通创意数=" + creativeTypes.size());

                List<CreativeDTO> creativeEntityList = EntityConvertUtils.convertToCrEntity(creativeTypes);

                for (CreativeDTO creativeEntity : creativeEntityList) {
                    creativeEntity.setAccountId(aid);
                }

                // 开始保存数据
                campaignDAO.save(campaignEntities);
                adgroupDAO.save(adgroupEntities);
                creativeDAO.save(creativeEntityList);
            }
            ));
        }
        ));


    }

    @Override
    public void updateAccountData(String userName, long accountId) {
        SystemUserDTO systemUserDTO = getSystemUser(userName);
        if (systemUserDTO == null) {
            return;
        }

        systemUserDTO.getSystemUserModules().stream().filter((systemUserModuleDTO -> systemUserModuleDTO.getModuleName().equals(AppContext.getModuleName()))).findFirst()
                .ifPresent((systemUserModuleDTO1 -> {
                    List<ModuleAccountInfoDTO> baiduAccountInfoDTOList = systemUserModuleDTO1.getAccounts();

                    if (baiduAccountInfoDTOList == null || baiduAccountInfoDTOList.isEmpty()) {
                        return;
                    }

                    //清除当前账户所有数据
                    clearAccountData(accountId);

                    ModuleAccountInfoDTO _dto;
                    for (ModuleAccountInfoDTO baiduAccountInfoDTO : baiduAccountInfoDTOList) {

                        Long aid = baiduAccountInfoDTO.getBaiduAccountId();
                        if (aid == null || aid != accountId)
                            continue;

                        CommonService commonService = BaiduServiceSupport.getCommonService(baiduAccountInfoDTO.getBaiduUserName(), baiduAccountInfoDTO.getBaiduPassword(), baiduAccountInfoDTO.getToken());
                        BaiduApiService apiService = new BaiduApiService(commonService);

                        // 初始化账户数据
                        AccountInfoType accountInfoType = apiService.getAccountInfo();
                        boolean isDefault = baiduAccountInfoDTO.isDfault();
                        _dto = ObjectUtils.convert(accountInfoType, ModuleAccountInfoDTO.class);
                        _dto.setBaiduAccountId(accountInfoType.getUserid());
                        _dto.setBaiduUserName(baiduAccountInfoDTO.getBaiduUserName());
                        _dto.setBaiduPassword(baiduAccountInfoDTO.getBaiduPassword());
                        _dto.setToken(baiduAccountInfoDTO.getToken());
                        _dto.setDfault(isDefault);

                        //更新账户数据
                        updateBaiduAccountInfo(userName, accountId, _dto);

                        //更新推广计划数据
                        List<CampaignType> campaignTypes = apiService.getAllCampaign();
                        List<CampaignDTO> campaignEntities = EntityConvertUtils.convertToCamEntity(campaignTypes);
                        //查询推广单元
                        List<Long> camIds = new ArrayList<>(campaignEntities.size());
                        for (CampaignDTO campaignEntity : campaignEntities) {
                            campaignEntity.setAccountId(aid);
                            camIds.add(campaignEntity.getCampaignId());
                        }
                        campaignDAO.save(campaignEntities);

                        //更新推广单元数据
                        List<AdgroupType> adgroupTypeList = apiService.getAllAdGroup(camIds);
                        List<AdgroupDTO> adgroupEntities = EntityConvertUtils.convertToAdEntity(adgroupTypeList);
                        List<Long> adgroupIds = new ArrayList<>();
                        for (AdgroupDTO adgroupEntity : adgroupEntities) {
                            adgroupEntity.setAccountId(aid);
                            adgroupIds.add(adgroupEntity.getAdgroupId());
                        }
                        adgroupDAO.save(adgroupEntities);

                        //分批次请求关键词数据
                        List<Long> subList = new ArrayList<>(4);
                        for (int i = 1; i <= adgroupIds.size(); i++) {
                            Long adgroupId = adgroupIds.get(i - 1);
                            subList.add(adgroupId);

                            if (i % 4 == 0) {
                                List<KeywordType> keywordTypes = apiService.getAllKeyword(subList);
                                List<KeywordDTO> keywordEntities = EntityConvertUtils.convertToKwEntity(keywordTypes);

                                for (KeywordDTO keywordEntity : keywordEntities) {
                                    keywordEntity.setAccountId(aid);
                                }
                                keywordDAO.save(keywordEntities);
                                subList.clear();
                            }
                        }


                        if (!subList.isEmpty()) {
                            List<KeywordType> keywordTypes = apiService.getAllKeyword(subList);
                            List<KeywordDTO> keywordEntities = EntityConvertUtils.convertToKwEntity(keywordTypes);

                            for (KeywordDTO keywordEntity : keywordEntities) {
                                keywordEntity.setAccountId(aid);
                            }
                            keywordDAO.save(keywordEntities);
                            subList.clear();
                        }

                        List<CreativeType> creativeTypes = apiService.getAllCreative(adgroupIds);

                        List<CreativeDTO> creativeEntityList = EntityConvertUtils.convertToCrEntity(creativeTypes);

                        for (CreativeDTO creativeEntity : creativeEntityList) {
                            creativeEntity.setAccountId(aid);
                        }
                        creativeDAO.save(creativeEntityList);
                    }
                }));


    }

    @Override
    public boolean updateBaiDuName(String name, Long baiduId) {

        try {
            String moduleAccountName = findByAid(baiduId)
                    .getSystemUserModules()
                    .stream()
                    .filter(o -> Objects.equals(SystemNameConstant.SOUKE_SYSTEM_NAME, o.getModuleName()))
                    .findFirst()
                    .get()
                    .getAccounts()
                    .stream()
                    .filter(o -> Objects.equals(baiduId, o.getBaiduAccountId()))
                    .findFirst()
                    .get()
                    .getBaiduUserName();

            return accountManageDAO.updateBaiduRemarkName(name, moduleAccountName);
        } catch (NullPointerException e) {
            return false;
        }

    }

    @Override
    public void updateAccountData(String userName, long accountId, List<Long> camIds) {
        SystemUserDTO systemUserDTO = getSystemUser(userName);

        if (systemUserDTO == null) {
            return;
        }

        SystemUserUtils.consumeCurrentSystemAccount(systemUserDTO, AppContext.getModuleName(), (systemUserModuleDTO -> {
            List<ModuleAccountInfoDTO> moduleAccountInfoDTOs = systemUserModuleDTO.getAccounts();
            if (moduleAccountInfoDTOs == null || moduleAccountInfoDTOs.isEmpty()) {
                return;
            }

            ModuleAccountInfoDTO moduleAccountInfoDTO = null;
            for (ModuleAccountInfoDTO dto : moduleAccountInfoDTOs) {
                if (accountId == dto.getBaiduAccountId()) {
                    moduleAccountInfoDTO = dto;
                    break;
                }
            }

            Objects.requireNonNull(moduleAccountInfoDTO);
            Long acid = moduleAccountInfoDTO.getBaiduAccountId();

            CommonService commonService = BaiduServiceSupport.getCommonService(moduleAccountInfoDTO.getBaiduUserName(), moduleAccountInfoDTO.getBaiduPassword(), moduleAccountInfoDTO.getToken());
            BaiduApiService apiService = new BaiduApiService(commonService);

            //获取账户总数据
            AccountInfoType accountInfoType = apiService.getAccountInfo();
            BeanUtils.copyProperties(accountInfoType, moduleAccountInfoDTO);

            //update account data
            updateBaiduAccountInfo(userName, accountId, moduleAccountInfoDTO);

            //获取指定id的推广计划
            List<CampaignType> campaignTypes = apiService.getCampaignById(camIds);

            //转换成本地系统的实体
            List<CampaignDTO> campaignEntities = EntityConvertUtils.convertToCamEntity(campaignTypes);

            List<Long> localAdgroupIds = getLocalAdgroupIds(accountId, camIds);
            List<Long> localKeywordIds = getLocalKeywordIds(accountId, localAdgroupIds);
            List<Long> localCreativeIds = getLocalCreativeIds(accountId, localAdgroupIds);

            //clear data
            clearCampaignData(accountId, camIds);
            clearAdgroupData(accountId, localAdgroupIds);
            clearKeywordData(accountId, localKeywordIds);
            clearCreativeData(accountId, localCreativeIds);

            //凤巢返回回来的计划实体id
            List<Long> campaignIds = new ArrayList<>(campaignEntities.size());

            for (CampaignDTO campaignEntity : campaignEntities) {
                campaignEntity.setAccountId(acid);
                campaignIds.add(campaignEntity.getCampaignId());
            }
            campaignDAO.save(campaignEntities);

            List<AdgroupType> adgroupTypeList = apiService.getAllAdGroup(campaignIds);

            List<AdgroupDTO> adgroupEntities = EntityConvertUtils.convertToAdEntity(adgroupTypeList);

            List<Long> adgroupIds = new ArrayList<>(adgroupEntities.size());
            for (AdgroupDTO adgroupEntity : adgroupEntities) {
                adgroupEntity.setAccountId(acid);
                adgroupIds.add(adgroupEntity.getAdgroupId());
            }
            adgroupDAO.save(adgroupEntities);


            //分批次请求关键词数据
            List<Long> subList = new ArrayList<>(4);
            for (int i = 1; i <= adgroupIds.size(); i++) {
                Long adgroupId = adgroupIds.get(i - 1);
                subList.add(adgroupId);

                if (i % 4 == 0) {
                    List<KeywordType> keywordTypes = apiService.getAllKeyword(subList);
                    List<KeywordDTO> keywordEntities = EntityConvertUtils.convertToKwEntity(keywordTypes);

                    for (KeywordDTO keywordEntity : keywordEntities) {
                        keywordEntity.setAccountId(acid);
                    }
                    keywordDAO.save(keywordEntities);
                    subList.clear();
                }
            }

            if (!subList.isEmpty()) {
                List<KeywordType> keywordTypes = apiService.getAllKeyword(subList);
                List<KeywordDTO> keywordEntities = EntityConvertUtils.convertToKwEntity(keywordTypes);

                for (KeywordDTO keywordEntity : keywordEntities) {
                    keywordEntity.setAccountId(acid);
                }
                keywordDAO.save(keywordEntities);
                subList.clear();
            }

            List<CreativeType> creativeTypes = apiService.getAllCreative(adgroupIds);

            List<CreativeDTO> creativeEntityList = EntityConvertUtils.convertToCrEntity(creativeTypes);

            for (CreativeDTO creativeEntity : creativeEntityList) {
                creativeEntity.setAccountId(acid);
            }
            creativeDAO.save(creativeEntityList);
        }));


    }

    @Override
    public void updateBaiduAccountInfo(String userName, Long accountId, ModuleAccountInfoDTO moduleAccountInfoDTO) {
        moduleAccountInfoDTO.setBaiduAccountId(accountId);
        String userId = systemAccountDAO.findByUserName(userName).getId();
        String sysUserModuleId = systemAccountDAO.findSysUserModuleId(userName, SystemNameConstant.SOUKE_SYSTEM_NAME);
        moduleAccountInfoDTO.setUserId(userId);
        moduleAccountInfoDTO.setModuleId(sysUserModuleId);

        systemAccountDAO.updateModuleAccount(moduleAccountInfoDTO);
//        accountManageDAO.updateBaiduAccountInfo(userName, moduleAccountInfoDTO);
    }

    @Override
    public List<CampaignDTO> getCampaign(String userName, long accountId) {
        SystemUserDTO systemUserDTO = getSystemUser(userName);

        if (systemUserDTO == null) {
            return Collections.emptyList();
        }

        List<CampaignDTO> list = Collections.EMPTY_LIST;

        SystemUserUtils.consumeCurrentSystemAccount(systemUserDTO, AppContext.getModuleName(), systemUserModuleDTO -> {
            List<ModuleAccountInfoDTO> moduleAccountInfoDTOs = systemUserModuleDTO.getAccounts();

            if (moduleAccountInfoDTOs == null || moduleAccountInfoDTOs.isEmpty()) {
                return;
            }

            ModuleAccountInfoDTO baiduAccountInfoDTO = null;
            for (ModuleAccountInfoDTO dto : moduleAccountInfoDTOs) {
                if (Long.valueOf(accountId).compareTo(dto.getBaiduAccountId()) == 0) {
                    baiduAccountInfoDTO = dto;
                    break;
                }
            }

            Objects.requireNonNull(baiduAccountInfoDTO);
            Long acid = baiduAccountInfoDTO.getBaiduAccountId();

            CommonService commonService = BaiduServiceSupport.getCommonService(baiduAccountInfoDTO.getBaiduUserName(), baiduAccountInfoDTO.getBaiduPassword(), baiduAccountInfoDTO.getToken());
            BaiduApiService apiService = new BaiduApiService(commonService);

            //本地的推广单元
            List<CampaignDTO> campaignEntityList = Lists.newArrayList(campaignDAO.findAll());
            List<CampaignType> campaignTypes = apiService.getAllCampaign();
            List<CampaignDTO> campaignEntities = EntityConvertUtils.convertToCamEntity(campaignTypes);
            //凤巢中的推广单元
            Map<Long, CampaignDTO> campaignEntityMap = new LinkedHashMap<>();
            for (CampaignDTO campaignEntity : campaignEntities) {
                campaignEntity.setAccountId(acid);
                campaignEntityMap.put(campaignEntity.getCampaignId(), campaignEntity);
            }

            List<CampaignDTO> sumList = new ArrayList<>(campaignEntityList);
            sumList.addAll(campaignEntities);
            for (CampaignDTO entity : sumList) {
                Long campaignId = entity.getCampaignId();
                if (campaignId == null) {
                    continue;
                }
                if (campaignEntityMap.get(campaignId) != null) {
                    campaignEntityMap.remove(campaignId);
                }
            }

            if (campaignEntityMap.size() == 0) {
                return;
            } else {
                List<CampaignDTO> campaignDTOList = new ArrayList<>();
                campaignEntityMap.values().forEach(e -> {
                    CampaignDTO campaignDTO = new CampaignDTO();
                    BeanUtils.copyProperties(e, campaignDTO);
                    campaignDTOList.add(campaignDTO);
                });
                list.addAll(campaignDTOList);
                return;
            }
        });

        return list;

    }

    @Override
    public SystemUserDTO getSystemUser(String userName) {
        return systemAccountDAO.findByUserName(userName);
//        return systemUserDAO.findByUserName(userName);
    }

    @Override
    public SystemUserDTO getSystemUser(long aid) {
        return systemUserDAO.findByAid(aid);
    }

    @Override
    public Iterable<SystemUserDTO> getAllUser() {
        return systemUserDAO.findAll();
    }

    @Override
    public List<SystemUserDTO> getAllValidUser() {
        return Lists.newArrayList(systemUserDAO.getAllValidUser());
    }

    @Override
    public void save(SystemUserDTO systemUserDTO) {
        systemUserDAO.save(systemUserDTO);
    }

    @Override
    public boolean removeAccount(Long id, String account) {

        SystemUserDTO byUserName = systemUserDAO.findByUserName(account);
        boolean Master = false;
        if (byUserName != null && byUserName.getSystemUserModules().size() > 0) {

            SystemUserModuleDTO systemUserModuleDTO = null;
            try {
                systemUserModuleDTO = byUserName.getSystemUserModules().stream().filter((tmp -> {
                    return tmp.getModuleName().equals(SystemNameConstant.SOUKE_SYSTEM_NAME);
                })).findFirst().get();

            } catch (Exception ex) {
                return false;
            }

            if (systemUserModuleDTO.getAccounts() == null) {
                return false;
            }

            boolean removed = systemUserModuleDTO.getAccounts().removeIf((tmp -> tmp.getBaiduAccountId().compareTo(id) == 0));

            if (removed) {
                if (systemUserModuleDTO.getAccounts().size() > 0 && Master) {
                    systemUserModuleDTO.getAccounts().get(0).setDfault(true);
                }
            }

            boolean update = systemUserDAO.updateModuleInfo(systemUserModuleDTO, byUserName.getId());
            return update;
        }
        return false;
    }

    //    @Override
    public void addAccount(String user, ModuleAccountInfoDTO moduleAccountInfoDTO) {
        systemUserDAO.insertAccountInfo(user, moduleAccountInfoDTO);
    }

    @Override
    public boolean updatePassword(String userName, String pwd) {
        boolean success = accountManageDAO.updatePwd(userName, pwd);
        if (success) {
            systemLogDAO.log("用户:" + userName + "修改密码");
        }
        return success;
    }

    @Override
    public SystemUserDTO findByAid(long aid) {
        return systemAccountDAO.findUserByModuleAccountId(aid);
    }

    @Override
    public void clearAccountData(Long accountId) {
        systemUserDAO.clearAccountData(accountId);
    }

    @Override
    public void clearCampaignData(Long accountId, List<Long> campaignIds) {
        systemUserDAO.clearCampaignData(accountId, campaignIds);
    }

    @Override
    public void clearAdgroupData(Long accountId, List<Long> adgroupIds) {
        systemUserDAO.clearAdgroupData(accountId, adgroupIds);
    }

    @Override
    public void clearKeywordData(Long accountId, List<Long> keywordIds) {
        systemUserDAO.clearKeywordData(accountId, keywordIds);
    }

    @Override
    public void clearCreativeData(Long accountId, List<Long> creativeIds) {
        systemUserDAO.clearCreativeData(accountId, creativeIds);
    }

    @Override
    public List<Long> getLocalAdgroupIds(Long accountId, List<Long> campaignIds) {
        return systemUserDAO.getLocalAdgroupIds(accountId, campaignIds);
    }

    @Override
    public List<Long> getLocalKeywordIds(Long accountId, List<Long> adgroupIds) {
        return systemUserDAO.getLocalKeywordIds(accountId, adgroupIds);
    }

    @Override
    public List<Long> getLocalCreativeIds(Long accountId, List<Long> adgroupIds) {
        return systemUserDAO.getLocalCreativeIds(accountId, adgroupIds);
    }

    @Override
    public SystemUserDTO findByUserName(String userName) {
        return systemAccountDAO.findByUserName(userName);
    }

    @Override
    public Iterable<SystemUserDTO> findAll() {
        return systemUserDAO.findAll();
    }

    @Override
    public List<SystemUserDTO> findUsers(String companyName, String userName, Boolean accountStatus, int skip, int
            limit, String order, boolean asc) {
        Map<String, Object> paramMap = Maps.newHashMap();

        if (!Strings.isNullOrEmpty(companyName)) {
            paramMap.put("companyName", companyName);
        }

        if (!Strings.isNullOrEmpty(userName)) {
            paramMap.put("userName", userName);
        }

        if (accountStatus != null) {
            paramMap.put("acstate", accountStatus);
        }

        if (paramMap.isEmpty()) {
            return systemUserDAO.findAll(skip, limit, order, asc);
        }
        return systemUserDAO.find(paramMap, skip, limit, order, asc);
    }

    @Override
    public boolean updateAccountStatus(String id, Integer accountStatus) {
        SystemUserDTO oldDto = findByUserId(id);

        boolean success = systemUserDAO.updateAccountStatus(id, accountStatus);
        if (success) {

            systemLogDAO.log("修改用户: " + oldDto.getUserName() + " 审核状态:" + (oldDto.getAccountState() == 1 ? "审核通过" : "审核未通过") + " - > " + (accountStatus == 1 ? "审核通过" : "审核未通过"));
        }
        return success;
    }

    @Override
    public boolean updateAccountTime(String id, Date startDate, Date endDate) {
        boolean success = systemUserDAO.updateAccountTime(id, startDate, endDate);
        if (success) {
            String startTime = DateFormatUtils.format(startDate, "yyyy-MM-dd");
            String endTime = DateFormatUtils.format(endDate, "yyyy-MM-dd");
            SystemUserDTO userDTO = findByUserId(id);
            systemLogDAO.log("修改用户:" + userDTO.getUserName() + "使用时间开始为:" + startTime + ",结束时间为:" + endTime);
            return success;
        }
        return false;
    }

    @Override
    public boolean updateAccountPayed(String id, Boolean payed) {
        SystemUserDTO oldDto = findByUserId(id);

        boolean success = systemUserDAO.updateAccountPayed(id, payed);
        if (success) {
            systemLogDAO.log("修改用户: " + oldDto.getUserName() + " 用户使用状态: " + ((oldDto.isPayed()) ? "付费" : "试用") + " - > " + ((payed) ? "付费" : "试用"));
        }

        return success;
    }

    @Override
    public List<SystemUserModuleDTO> getUserModules(String name) {
        List<SystemUserModuleDTO> systemUserModuleDTOs = systemUserDAO.getUserModules(name);

        if (systemUserModuleDTOs == null || systemUserModuleDTOs.isEmpty()) {
            return systemUserModuleDTOs;
        }

        systemUserModuleDTOs.removeIf((dto) -> !systemModuleDAO.exists(dto.getModuleId()));

        systemUserModuleDTOs.forEach((dto) -> {
            SystemModuleDTO systemModuleDTO = systemModuleDAO.findByModuleId(dto.getModuleId());
            if (systemModuleDTO == null) {
                return;
            }
            dto.setModuleName(systemModuleDTO.getModuleName());
            dto.setModuleUrl(systemModuleDTO.getModuleUrl());
        });
        return systemUserModuleDTOs;
    }

    @Override
    public boolean updateUserModuleMenus(String id, String moduleid, List<SystemMenuDTO> menus) {
        return systemUserDAO.updateModuleMenus(id, moduleid, menus);
    }

    @Override
    public boolean addModule(String userid, String moduleId) {

        SystemUserDTO systemUserDTO = systemUserDAO.findByUserId(userid);
        if (systemUserDTO == null) {
            return false;
        }

        boolean exists = systemUserDAO.existsModule(userid, moduleId);
        if (exists) {
            return false;
        }

        SystemModuleDTO systemModuleDTO = systemModuleDAO.findByModuleId(moduleId);
        if (systemModuleDTO == null) {
            return false;
        }

        SystemUserModuleDTO systemUserModuleDTO = new SystemUserModuleDTO();
        systemUserModuleDTO.setModuleId(moduleId);
//        systemUserModuleDTO.setModuleName(systemModuleDTO.getModuleName());
//        systemUserModuleDTO.setModuleUrl(systemModuleDTO.getModuleUrl());

        systemUserModuleDTO.setPayed(false);
        systemUserModuleDTO.setEnabled(true);
//        List<SystemUserModuleDTO> systemUserModuleDTOs = systemUserDTO.getSystemUserModules();
//
//        if (systemUserModuleDTOs == null || systemUserModuleDTOs.isEmpty()) {
//            systemUserDTO.setSystemUserModules(Lists.newArrayList(systemUserModuleDTO));
//        } else {
//            systemUserModuleDTOs.removeIf((dto) -> dto.getModuleName().equals(systemModuleDTO.getModuleName()));
//            systemUserModuleDTOs.add(systemUserModuleDTO);
//        }

        boolean success = systemUserDAO.saveUserModule(userid, systemUserModuleDTO);
        if (success) {
            systemLogDAO.log("用户:" + systemUserDTO.getUserName() + " 新增系统模块:" + systemModuleDTO.getModuleName());
        }

        return success;
    }

    @Override
    public boolean deleteModule(String userid, String id) {
        SystemUserDTO systemUserDTO = systemUserDAO.findByUserId(userid);
        if (systemUserDTO == null) {
            return false;
        }

        SystemUserModuleDTO systemUserModuleDTO = systemUserDTO.getSystemUserModules().stream().findFirst().filter((dto -> dto.getId().equals(id))).get();

        if (systemUserModuleDTO == null) {
            return false;
        }

        SystemModuleDTO systemModuleDTO = systemModuleDAO.findByModuleId(systemUserModuleDTO.getModuleId());
        if (systemModuleDTO == null) {
            return false;
        }

        boolean success = systemUserDAO.deleteModule(userid, id);
        if (success) {
            systemLogDAO.log("用户:" + systemUserDTO.getUserName() + " 删除系统模块:" + systemModuleDTO.getModuleName());
        }
        return success;
    }

    @Override
    public List<SystemMenuDTO> getUserSubMenu(String userid, String id) {

        SystemUserModuleDTO systemUserModuleDTO = systemUserDAO.getUserModuleById(userid, id);

        if (systemUserModuleDTO == null) {
            return Lists.newArrayList();
        }
        // 查询系统模块菜单，获取最新菜单名称，url等
        SystemModuleDTO systemModuleDTO = systemModuleDAO.findByModuleId(systemUserModuleDTO.getModuleId());

//        List<SystemMenuDTO> systemMenuDTOs = systemModuleDTO.getMenus();
//
//        List<SystemMenuDTO> userMenuDTOs = systemUserModuleDTO.getMenus();
//
//        userMenuDTOs.stream().forEach((userMenuDTO -> {
//            SystemMenuDTO matchedMenuDTO = systemMenuDTOs.stream().findFirst()
//                    .filter((systemMenuDTO -> userMenuDTO.getId().equals(systemMenuDTO.getId()))).get();
//
//            userMenuDTO.setMenuName(matchedMenuDTO.getMenuName());
//            userMenuDTO.setMenuUrl(matchedMenuDTO.getMenuName());
//        }));

        updateMenuData(systemModuleDTO.getMenus(), systemUserModuleDTO.getMenus());
        return systemUserModuleDTO.getMenus();
    }

    @Override
    public boolean addModuleAccount(String id, String moduleid, ModuleAccountInfoDTO moduleAccountInfoDTO) {
        boolean success = systemUserDAO.addModuleAccount(id, moduleid, moduleAccountInfoDTO);
        if (success) {
            SystemUserDTO userDTO = findByUserId(id);
            systemLogDAO.log("用户:" + userDTO.getUserName() + "添加了百度推广账号:" + moduleAccountInfoDTO.getBaiduUserName());
        }

        return success;
    }

    @Override
    public void updateUserImage(InputStream is, String fileSuffix) {
        String sysUserName = AppContext.getUser();
        Map<String, Object> metaData = Maps.newHashMap();
        String sysUserId = systemAccountDAO.findByUserName(sysUserName).getId();
        metaData.put("userId", sysUserId);

        systemUserDAO.updateUserImage(is, sysUserName + fileSuffix, metaData);
        systemLogDAO.log("用户:" + sysUserName + "修改了头像!");
    }

    @Override
    public InputStream findUserImage(String sysUserName) {
        String sysUserId = systemUserDAO.findByUserName(sysUserName).getId();

        return systemUserDAO.findUserImage(sysUserId);
    }

    @Override
    public boolean updateUserPassword(String userid, String password) {

        SystemUserDTO systemUserDTO = systemUserDAO.findOne(userid);
        if (systemUserDTO == null) {
            return false;
        }
        boolean success = systemUserDAO.updateAccountPassword(userid, new MD5.Builder().source(password).salt(user_salt).build().getMD5());

        if (success) {
            systemLogDAO.log("修改用户密码: " + systemUserDTO.getUserName());
        }
        return success;
    }

    @Override
    public boolean updateUserModuleMenus(String userid, UserModuleMenuDTO userModuleMenuDTO) {
        SystemUserDTO systemUserDTO = systemUserDAO.findByUserId(userid);

        if (systemUserDTO == null) {
            return false;
        }


        boolean updated = systemUserDAO.updateUserMenus(userid, userModuleMenuDTO);

        if (updated) {
            systemLogDAO.log("更新用户菜单:" + systemUserDTO.getUserName());
        }

        return updated;
    }

    @Override
    public SystemUserDTO findByUserId(String userid) {
        return systemUserDAO.findByUserId(userid);
    }

    @Override
    public boolean updateUserBaseInfo(String userid, SystemUserDTO systemUserDTO) {

        boolean success = systemUserDAO.updateUserBaseInfo(userid, systemUserDTO);
        if (success) {
            SystemUserDTO userDTO = findByUserId(userid);
            systemLogDAO.log("用户:" + userDTO.getUserName() + "修改了用户信息");
        }
        return success;
    }

    /**
     * @param from 原始数据
     * @param to   需要被设置的数据
     */
    private void updateMenuData(List<SystemMenuDTO> from, List<SystemMenuDTO> to) {
        if (to == null || from == null) {
            return;
        }
        to.stream().forEach((userMenuDTO -> {
            SystemMenuDTO matchedMenuDTO = null;

            Optional<SystemMenuDTO> optional = from.stream().filter((systemMenuDTO) -> userMenuDTO.getMenuId().equals(systemMenuDTO.getId())).findAny();

            if (optional.isPresent()) {
                matchedMenuDTO = optional.get();
            } else {
                return;
            }

            userMenuDTO.setMenuName(matchedMenuDTO.getMenuName());
            userMenuDTO.setMenuUrl(matchedMenuDTO.getMenuUrl());

            if (userMenuDTO.getSubMenus() == null || userMenuDTO.getSubMenus().isEmpty()) {
                return;
            } else {
                updateMenuData(matchedMenuDTO.getSubMenus(), userMenuDTO.getSubMenus());
            }
        }));
    }
}
