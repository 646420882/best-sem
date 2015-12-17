package com.perfect.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.perfect.api.baidu.BaiduApiService;
import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ResHeaderUtil;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.dao.account.AccountManageDAO;
import com.perfect.dao.adgroup.AdgroupDAO;
import com.perfect.dao.campaign.CampaignDAO;
import com.perfect.dao.creative.CreativeDAO;
import com.perfect.dao.keyword.KeywordDAO;
import com.perfect.dao.sys.SystemLogDAO;
import com.perfect.dao.sys.SystemModuleDAO;
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.dto.sys.*;
import com.perfect.service.SystemUserService;
import com.perfect.utils.EntityConvertUtils;
import com.perfect.utils.ObjectUtils;
import org.elasticsearch.common.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by yousheng on 2014/8/12.
 *
 * @author yousheng
 */
@Service("systemUserService")
public class SystemUserServiceImpl implements SystemUserService {

    private Logger logger = LoggerFactory.getLogger(SystemUserServiceImpl.class);

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

        for (BaiduAccountInfoDTO baiduAccountInfoDTO : systemUserDTO.getBaiduAccounts()) {

            Long aid = baiduAccountInfoDTO.getId();
            if (!Objects.equals(aid, accountId))
                continue;
            CommonService commonService = BaiduServiceSupport.getCommonService(baiduAccountInfoDTO.getBaiduUserName(), baiduAccountInfoDTO.getBaiduPassword(), baiduAccountInfoDTO.getToken());
            BaiduApiService apiService = new BaiduApiService(commonService);

            logger.info("查询账户信息...");
            // 初始化账户数据
            AccountInfoType accountInfoType = apiService.getAccountInfo();
            if (accountInfoType == null) {
                logger.error("获取账户信息错误: " + ResHeaderUtil.getJsonResHeader(false).toString());
                continue;
            }
            boolean isDefault = baiduAccountInfoDTO.isDfault();
            baiduAccountInfoDTO = ObjectUtils.convert(accountInfoType, BaiduAccountInfoDTO.class);
            baiduAccountInfoDTO.setId(accountInfoType.getUserid());
            baiduAccountInfoDTO.setBaiduUserName(baiduAccountInfoDTO.getBaiduUserName());
            baiduAccountInfoDTO.setBaiduPassword(baiduAccountInfoDTO.getBaiduPassword());
            baiduAccountInfoDTO.setToken(baiduAccountInfoDTO.getToken());
            baiduAccountInfoDTO.setDfault(isDefault);

            //新增百度账户
            systemUserDAO.insertAccountInfo(userName, baiduAccountInfoDTO);

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
    }

    @Override
    public void updateAccountData(String userName, long accountId) {
        SystemUserDTO systemUserDTO = getSystemUser(userName);
        if (systemUserDTO == null) {
            return;
        }

        List<BaiduAccountInfoDTO> baiduAccountInfoDTOList = systemUserDTO.getBaiduAccounts();

        if (baiduAccountInfoDTOList == null || baiduAccountInfoDTOList.isEmpty()) {
            return;
        }

        //清除当前账户所有数据
        clearAccountData(accountId);

        BaiduAccountInfoDTO _dto;
        for (BaiduAccountInfoDTO baiduAccountInfoDTO : baiduAccountInfoDTOList) {

            Long aid = baiduAccountInfoDTO.getId();
            if (aid != accountId)
                continue;

            CommonService commonService = BaiduServiceSupport.getCommonService(baiduAccountInfoDTO.getBaiduUserName(), baiduAccountInfoDTO.getBaiduPassword(), baiduAccountInfoDTO.getToken());
            BaiduApiService apiService = new BaiduApiService(commonService);

            // 初始化账户数据
            AccountInfoType accountInfoType = apiService.getAccountInfo();
            boolean isDefault = baiduAccountInfoDTO.isDfault();
            _dto = ObjectUtils.convert(accountInfoType, BaiduAccountInfoDTO.class);
            _dto.setId(accountInfoType.getUserid());
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
    }

    @Override
    public boolean updateBaiDuName(String name, Long baiduId) {
        boolean flag = accountManageDAO.updateBaiDuName(name, baiduId);
        return flag;
    }

    @Override
    public void updateAccountData(String userName, long accountId, List<Long> camIds) {
        SystemUserDTO systemUserDTO = getSystemUser(userName);

        if (systemUserDTO == null) {
            return;
        }

        List<BaiduAccountInfoDTO> baiduAccountInfoDTOList = systemUserDTO.getBaiduAccounts();
        if (baiduAccountInfoDTOList == null || baiduAccountInfoDTOList.isEmpty()) {
            return;
        }

        BaiduAccountInfoDTO baiduAccountInfoDTO = null;
        for (BaiduAccountInfoDTO dto : baiduAccountInfoDTOList) {
            if (accountId == dto.getId()) {
                baiduAccountInfoDTO = dto;
                break;
            }
        }

        Objects.requireNonNull(baiduAccountInfoDTO);
        Long acid = baiduAccountInfoDTO.getId();

        CommonService commonService = BaiduServiceSupport.getCommonService(baiduAccountInfoDTO.getBaiduUserName(), baiduAccountInfoDTO.getBaiduPassword(), baiduAccountInfoDTO.getToken());
        BaiduApiService apiService = new BaiduApiService(commonService);

        //获取账户总数据
        AccountInfoType accountInfoType = apiService.getAccountInfo();
        BeanUtils.copyProperties(accountInfoType, baiduAccountInfoDTO);

        //update account data
        updateBaiduAccountInfo(userName, accountId, baiduAccountInfoDTO);

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
    }

    @Override
    public void updateBaiduAccountInfo(String userName, Long accountId, BaiduAccountInfoDTO dto) {
        accountManageDAO.updateBaiduAccountInfo(userName, accountId, dto);
    }

    @Override
    public List<CampaignDTO> getCampaign(String userName, long accountId) {
        SystemUserDTO systemUserDTO = getSystemUser(userName);

        if (systemUserDTO == null) {
            return Collections.emptyList();
        }

        List<BaiduAccountInfoDTO> baiduAccountInfoDTOList = systemUserDTO.getBaiduAccounts();

        if (baiduAccountInfoDTOList == null || baiduAccountInfoDTOList.isEmpty()) {
            return Collections.emptyList();
        }

        BaiduAccountInfoDTO baiduAccountInfoDTO = null;
        for (BaiduAccountInfoDTO dto : baiduAccountInfoDTOList) {
            if (Long.valueOf(accountId).compareTo(dto.getId()) == 0) {
                baiduAccountInfoDTO = dto;
                break;
            }
        }

        Objects.requireNonNull(baiduAccountInfoDTO);
        Long acid = baiduAccountInfoDTO.getId();

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
            return Collections.emptyList();
        } else {
            List<CampaignDTO> campaignDTOList = new ArrayList<>();
            campaignEntityMap.values().forEach(e -> {
                CampaignDTO campaignDTO = new CampaignDTO();
                BeanUtils.copyProperties(e, campaignDTO);
                campaignDTOList.add(campaignDTO);
            });
            return new ArrayList<>(campaignDTOList);
        }
    }

    @Override
    public SystemUserDTO getSystemUser(String userName) {
        return systemUserDAO.findByUserName(userName);
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
        if (byUserName != null && byUserName.getBaiduAccounts().size() > 0) {
            for (int i = 0; i < byUserName.getBaiduAccounts().size(); i++) {
                if (byUserName.getBaiduAccounts().get(i).getId().compareTo(id) == 0) {
                    if (byUserName.getBaiduAccounts().get(i).isDfault()) {

                    }
                    byUserName.getBaiduAccounts().remove(i);
                    --i;
                }
            }
            List<BaiduAccountInfoDTO> baiduAccountInfoDTOs = byUserName.getBaiduAccounts();
            if (baiduAccountInfoDTOs.size() > 0 && Master) {
                baiduAccountInfoDTOs.get(0).setDfault(true);
            }
            int falg = systemUserDAO.removeAccountInfo(baiduAccountInfoDTOs, account);
            if (falg > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addAccount(String user, BaiduAccountInfoDTO baiduAccountInfoDTO) {
        systemUserDAO.insertAccountInfo(user, baiduAccountInfoDTO);
    }

    @Override
    public boolean updatePassword(String userName, String pwd) {
        return accountManageDAO.updatePwd(userName, pwd);
    }

    @Override
    public SystemUserDTO findByAid(long aid) {
        return systemUserDAO.findByAid(aid);
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
        return systemUserDAO.findByUserName(userName);
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
    public boolean updateAccountStatus(String id, Boolean accountStatus) {
        return systemUserDAO.updateAccountStatus(id, accountStatus);
    }

    @Override
    public boolean updateAccountTime(String id, Date startDate, Date endDate) {
        return systemUserDAO.updateAccountTime(id, startDate, endDate);
    }

    @Override
    public boolean updateAccountPayed(String id, Boolean payed) {
        return systemUserDAO.updateAccountPayed(id, payed);
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

        systemUserModuleDTO.setIsPayed(false);
        systemUserModuleDTO.setEnabled(true);
//        List<SystemUserModuleDTO> systemUserModuleDTOs = systemUserDTO.getModuleDTOList();
//
//        if (systemUserModuleDTOs == null || systemUserModuleDTOs.isEmpty()) {
//            systemUserDTO.setModuleDTOList(Lists.newArrayList(systemUserModuleDTO));
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

        SystemUserModuleDTO systemUserModuleDTO = systemUserDTO.getModuleDTOList().stream().findFirst().filter((dto -> dto.getId().equals(id))).get();

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

        return systemUserDAO.addModuleAccount(id, moduleid, moduleAccountInfoDTO);
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
