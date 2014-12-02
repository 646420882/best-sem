package com.perfect.service.impl;

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
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.service.SystemUserService;
import com.perfect.utils.EntityConvertUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yousheng on 2014/8/12.
 *
 * @author yousheng
 */
@Component
public class SystemUserServiceImpl implements SystemUserService {

    private Logger logger = Logger.getLogger(SystemUserServiceImpl.class);

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

    @Override
    public void initAccount(String userName, Long accountId) {
        logger.info("开始导入数据: 用户名=" + userName + ", 账号= " + accountId);

        SystemUserDTO systemUserDTO = getSystemUser(userName);
        if (systemUserDTO == null) {
            logger.warn("没有此账号: " + userName);
            return;
        }


        logger.info("清理已有数据...");
        clearCollectionData(accountId);
        logger.info("清理数据完成!");

        for (BaiduAccountInfoDTO baiduAccountInfoDTO : systemUserDTO.getBaiduAccountInfoDTOs()) {

            Long aid = baiduAccountInfoDTO.getId();
            if (aid != accountId)
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
            BeanUtils.copyProperties(accountInfoType, baiduAccountInfoDTO);

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
            List<KeywordType> keywordTypes = apiService.getAllKeyword(ids);
            logger.info("查询结束: 关键词数=" + keywordTypes.size());

            List<KeywordDTO> keywordEntities = EntityConvertUtils.convertToKwEntity(keywordTypes);

            for (KeywordDTO keywordEntity : keywordEntities) {
                keywordEntity.setAccountId(aid);
            }

            logger.info("查询账户推广创意...");
            List<CreativeType> creativeTypes = apiService.getAllCreative(ids);
            logger.info("查询结束: 普通创意数=" + creativeTypes.size());

            List<CreativeDTO> creativeEntityList = EntityConvertUtils.convertToCrEntity(creativeTypes);

            for (CreativeDTO creativeEntity : creativeEntityList) {
                creativeEntity.setAccountId(aid);
            }
            // 开始保存数据

            // 保存推广计划
            campaignDAO.save(campaignEntities);
            adgroupDAO.save(adgroupEntities);
            keywordDAO.save(keywordEntities);
            creativeDAO.save(creativeEntityList);
        }
        systemUserDAO.save(systemUserDTO);
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
    public void save(SystemUserDTO systemUserDTO) {
        systemUserDAO.save(systemUserDTO);
    }

    @Override
    public boolean removeAccount(Long id) {
        systemUserDAO.removeAccountInfo(id);
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
}
