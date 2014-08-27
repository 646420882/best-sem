package com.perfect.service.impl;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ResHeaderUtil;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.entity.*;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.mongodb.dao.impl.CampaignDAOImpl;
import com.perfect.service.AccountDataService;
import com.perfect.service.BaiduApiService;
import com.perfect.service.SystemUserService;
import com.perfect.utils.BaiduServiceSupport;
import com.perfect.utils.EntityConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.perfect.mongodb.utils.EntityConstants.*;
/**
 * 获取账户完整数据的方法
 * 更新账户数据逻辑的方法
 * Created by yousheng on 2014/8/12.
 *
 * @author yousheng
 */
@Component("accountDataService")
public class AccountDataServiceImpl implements AccountDataService {

    private static Logger logger = LoggerFactory.getLogger(AccountDataServiceImpl.class);

    @Resource
    private SystemUserService systemUserService;

    @Resource
    private CampaignDAOImpl campaignDAO;

    @Override
    public void initAccountData(String userName, long accountId) {
        logger.info("开始导入数据: 用户名=" + userName + ", 账号= " + accountId);
        SystemUserEntity systemUserEntity = systemUserService.getSystemUser(userName);
        if (systemUserEntity == null) {
            logger.warn("没有此账号: " + userName);
            return;
        }

        List<BaiduAccountInfoEntity> baiduAccountInfoEntityList = systemUserEntity.getBaiduAccountInfoEntities();

        if (baiduAccountInfoEntityList == null || baiduAccountInfoEntityList.isEmpty()) {
            logger.warn("账号未绑定百度推广账户");
            return;
        }

        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo(userName);
        logger.info("清理已有数据...");
        clearCollectionData(mongoTemplate, accountId);
        logger.info("清理数据完成!");

        for (BaiduAccountInfoEntity baiduAccountInfoEntity : baiduAccountInfoEntityList) {

            Long aid = baiduAccountInfoEntity.getId();
            if (aid != accountId)
                continue;
            CommonService commonService = BaiduServiceSupport.getCommonService(baiduAccountInfoEntity);
            BaiduApiService apiService = new BaiduApiService(commonService);

            logger.info("查询账户信息...");
            // 初始化账户数据
            AccountInfoType accountInfoType = apiService.getAccountInfo();
            if (accountInfoType == null) {
                logger.error("获取账户信息错误: " + ResHeaderUtil.getJsonResHeader(false).toString());
                continue;
            }
            BeanUtils.copyProperties(accountInfoType, baiduAccountInfoEntity);

            logger.info("查询账户推广计划...");
            List<CampaignType> campaignTypes = apiService.getAllCampaign();
            logger.info("查询结束: 计划数=" + campaignTypes.size());

            List<CampaignEntity> campaignEntities = EntityConvertUtils.convertToCamEntity(campaignTypes);

            // 查询推广单元
            List<Long> ids = new ArrayList<>(campaignEntities.size());

            for (CampaignEntity campaignEntity : campaignEntities) {
                campaignEntity.setAccountId(aid);
                ids.add(campaignEntity.getCampaignId());
            }

            logger.info("查询账户推广单元...");
            List<AdgroupType> adgroupTypeList = apiService.getAllAdGroup(ids);

            logger.info("查询结束: 单元数=" + adgroupTypeList.size());

            List<AdgroupEntity> adgroupEntities = EntityConvertUtils.convertToAdEntity(adgroupTypeList);
            ids.clear();
            for (AdgroupEntity adgroupEntity : adgroupEntities) {
                adgroupEntity.setAccountId(aid);
                ids.add(adgroupEntity.getAdgroupId());
            }
//
            logger.info("查询账户推广关键词...");
            List<KeywordType> keywordTypes = apiService.getAllKeyword(ids);
            logger.info("查询结束: 关键词数=" + keywordTypes.size());

            List<KeywordEntity> keywordEntities = EntityConvertUtils.convertToKwEntity(keywordTypes);

            for (KeywordEntity keywordEntity : keywordEntities) {
                keywordEntity.setAccountId(aid);
            }
//

            logger.info("查询账户推广创意...");
            List<CreativeType> creativeTypes = apiService.getAllCreative(ids);
            logger.info("查询结束: 普通创意数=" + creativeTypes.size());

            List<CreativeEntity> creativeEntityList = EntityConvertUtils.convertToCrEntity(creativeTypes);

            for (CreativeEntity creativeEntity : creativeEntityList) {
                creativeEntity.setAccountId(aid);
            }
            // 开始保存数据

            // 保存推广计划
            mongoTemplate.insertAll(campaignEntities);
            mongoTemplate.insertAll(adgroupEntities);
            mongoTemplate.insertAll(keywordEntities);
            mongoTemplate.insertAll(creativeEntityList);
        }
        systemUserService.save(systemUserEntity);
    }

    @Override
    public void updateAccountData(String userName, long accountId) {
        SystemUserEntity systemUserEntity = systemUserService.getSystemUser(userName);

        if (systemUserEntity == null) {
            return;
        }

        List<BaiduAccountInfoEntity> baiduAccountInfoEntityList = systemUserEntity.getBaiduAccountInfoEntities();

        if (baiduAccountInfoEntityList == null || baiduAccountInfoEntityList.isEmpty()) {
            return;
        }

        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo(userName);
//        clearCollectionData(mongoTemplate);

        for (BaiduAccountInfoEntity baiduAccountInfoEntity : baiduAccountInfoEntityList) {

            Long aid = baiduAccountInfoEntity.getId();
            if (aid != accountId)
                continue;
            CommonService commonService = BaiduServiceSupport.getCommonService(baiduAccountInfoEntity);
            BaiduApiService apiService = new BaiduApiService(commonService);

            // 初始化账户数据
            AccountInfoType accountInfoType = apiService.getAccountInfo();
            BeanUtils.copyProperties(accountInfoType, baiduAccountInfoEntity);

            List<CampaignType> campaignTypes = apiService.getAllCampaign();

            List<CampaignEntity> campaignEntities = EntityConvertUtils.convertToCamEntity(campaignTypes);

            // 查询推广单元
            List<Long> camIds = new ArrayList<>(campaignEntities.size());

            for (CampaignEntity campaignEntity : campaignEntities) {
                campaignEntity.setAccountId(aid);
                camIds.add(campaignEntity.getCampaignId());
            }

            List<AdgroupType> adgroupTypeList = apiService.getAllAdGroup(camIds);


//            List<AdgroupType> adgroupTypeList = new ArrayList<>(campaignAdgroupList.size() << 1);
//
//            for (CampaignAdgroup campaignAdgroup : campaignAdgroupList) {
//                List<AdgroupType> adgroupTypes = campaignAdgroup.getAdgroupTypes();
//                adgroupTypeList.addAll(adgroupTypes);
//            }

            List<AdgroupEntity> adgroupEntities = EntityConvertUtils.convertToAdEntity(adgroupTypeList);

            List<Long> adgroupdIds = new ArrayList<>();
            for (AdgroupEntity adgroupEntity : adgroupEntities) {
                adgroupEntity.setAccountId(aid);
                adgroupdIds.add(adgroupEntity.getAdgroupId());
            }

            List<KeywordType> keywordTypes = apiService.getAllKeyword(adgroupdIds);

            List<KeywordEntity> keywordEntities = EntityConvertUtils.convertToKwEntity(keywordTypes);

            List<Long> kwids = new ArrayList<>(keywordEntities.size());
            for (KeywordEntity keywordEntity : keywordEntities) {
                keywordEntity.setAccountId(aid);
                kwids.add(keywordEntity.getKeywordId());
            }


            List<CreativeType> creativeTypes = apiService.getAllCreative(adgroupdIds);

            List<CreativeEntity> creativeEntityList = EntityConvertUtils.convertToCrEntity(creativeTypes);

            List<Long> creativeIds = new ArrayList<>(creativeEntityList.size());
            for (CreativeEntity creativeEntity : creativeEntityList) {
                creativeEntity.setAccountId(aid);
                creativeIds.add(creativeEntity.getCreativeId());
            }
            // 开始保存数据

            // 保存推广计划
            mongoTemplate.findAllAndRemove(Query.query(Criteria.where(CAMPAIGN_ID).in(camIds)), CampaignEntity.class);
            mongoTemplate.insertAll(campaignEntities);

            mongoTemplate.insertAll(adgroupEntities);
            mongoTemplate.insertAll(keywordEntities);
            mongoTemplate.insertAll(creativeEntityList);
        }
        systemUserService.save(systemUserEntity);
    }

    @Override
    public void updateAccountData(String userName, long accountId, List<Long> camIds) {
        SystemUserEntity systemUserEntity = systemUserService.getSystemUser(userName);

        if (systemUserEntity == null) {
            return;
        }

        List<BaiduAccountInfoEntity> baiduAccountInfoEntityList = systemUserEntity.getBaiduAccountInfoEntities();

        if (baiduAccountInfoEntityList == null || baiduAccountInfoEntityList.isEmpty()) {
            return;
        }

        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo(userName);
        BaiduAccountInfoEntity baiduAccountInfoEntity = null;
        for (BaiduAccountInfoEntity entity : baiduAccountInfoEntityList) {
            if (Long.valueOf(accountId).compareTo(entity.getId()) == 0) {
                baiduAccountInfoEntity = entity;
                break;
            }
        }

        Long acid = baiduAccountInfoEntity.getId();

        CommonService commonService = BaiduServiceSupport.getCommonService(baiduAccountInfoEntity);
        BaiduApiService apiService = new BaiduApiService(commonService);

        // 初始化账户数据
        AccountInfoType accountInfoType = apiService.getAccountInfo();
        BeanUtils.copyProperties(accountInfoType, baiduAccountInfoEntity);

        //获取指定id的推广计划
        List<CampaignType> campaignTypes = apiService.getCampaignById(camIds);

        //转换成本地系统的实体
        List<CampaignEntity> campaignEntities = EntityConvertUtils.convertToCamEntity(campaignTypes);

        // 查询推广单元
//        List<Long> camIds = new ArrayList<>(campaignEntities.size());

        //凤巢返回回来的计划实体id
        List<Long> campaignIds = new ArrayList<>(campaignEntities.size());

        for (CampaignEntity campaignEntity : campaignEntities) {
            campaignEntity.setAccountId(acid);
            campaignIds.add(campaignEntity.getCampaignId());
        }

        List<AdgroupType> adgroupTypeList = apiService.getAllAdGroup(campaignIds);

        List<AdgroupEntity> adgroupEntities = EntityConvertUtils.convertToAdEntity(adgroupTypeList);

        List<Long> adgroupdIds = new ArrayList<>();
        for (AdgroupEntity adgroupEntity : adgroupEntities) {
            adgroupEntity.setAccountId(acid);
            adgroupdIds.add(adgroupEntity.getAdgroupId());
        }

        List<KeywordType> keywordTypes = apiService.getAllKeyword(adgroupdIds);

        List<KeywordEntity> keywordEntities = EntityConvertUtils.convertToKwEntity(keywordTypes);

        List<Long> kwids = new ArrayList<>(keywordEntities.size());
        for (KeywordEntity keywordEntity : keywordEntities) {
            keywordEntity.setAccountId(acid);
            kwids.add(keywordEntity.getKeywordId());
        }

        List<CreativeType> creativeTypes = apiService.getAllCreative(adgroupdIds);

        List<CreativeEntity> creativeEntityList = EntityConvertUtils.convertToCrEntity(creativeTypes);

        List<Long> creativeIds = new ArrayList<>(creativeEntityList.size());
        for (CreativeEntity creativeEntity : creativeEntityList) {
            creativeEntity.setAccountId(acid);
            creativeIds.add(creativeEntity.getCreativeId());
        }

        //clear data
        clearCollectionData(mongoTemplate, accountId);
        //update cakc data
        mongoTemplate.insertAll(campaignEntities);

        mongoTemplate.insertAll(adgroupEntities);
        mongoTemplate.insertAll(keywordEntities);
        mongoTemplate.insertAll(creativeEntityList);

        //update account data
        Update update1 = new Update();
        update1.unset("bdAccounts.$");
        mongoTemplate.updateFirst(
                Query.query(
                        Criteria.where("bdAccounts._id").is(baiduAccountInfoEntity.getId())),
                update1, SystemUserEntity.class);
        Update update2 = new Update();
        update2.addToSet("bdAccounts", baiduAccountInfoEntity);
        mongoTemplate.updateFirst(
                Query.query(
                        Criteria.where("userName").is(systemUserEntity.getUserName())),
                update2, SystemUserEntity.class);


    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CampaignEntity> getCampaign(String userName, long accountId) {
        SystemUserEntity systemUserEntity = systemUserService.getSystemUser(userName);

        if (systemUserEntity == null) {
            return Collections.EMPTY_LIST;
        }

        List<BaiduAccountInfoEntity> baiduAccountInfoEntityList = systemUserEntity.getBaiduAccountInfoEntities();

        if (baiduAccountInfoEntityList == null || baiduAccountInfoEntityList.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo(userName);
        BaiduAccountInfoEntity baiduAccountInfoEntity = null;
        for (BaiduAccountInfoEntity entity : baiduAccountInfoEntityList) {
            if (Long.valueOf(accountId).compareTo(entity.getId()) == 0) {
                baiduAccountInfoEntity = entity;
                break;
            }
        }

        Long acid = baiduAccountInfoEntity.getId();

        CommonService commonService = BaiduServiceSupport.getCommonService(baiduAccountInfoEntity);
        BaiduApiService apiService = new BaiduApiService(commonService);

        //本地的推广单元
        List<CampaignEntity> campaignEntityList = campaignDAO.findAll();

        List<CampaignType> campaignTypes = apiService.getAllCampaign();

        List<CampaignEntity> campaignEntities = EntityConvertUtils.convertToCamEntity(campaignTypes);

        //凤巢中的推广单元
        for (CampaignEntity campaignEntity : campaignEntities) {
            campaignEntity.setAccountId(acid);
        }

        List<CampaignEntity> sumList = new ArrayList<>(campaignEntityList);
        sumList.addAll(campaignEntities);
        for (int i = sumList.size() - 1; i >= 0; i--) {
            for (CampaignEntity entity : campaignEntityList) {
                if (sumList.get(i).getCampaignId().compareTo(entity.getCampaignId()) == 0) {
                    sumList.remove(i);
                    break;
                }
            }
        }

        if (sumList.size() == 0) {
            return Collections.EMPTY_LIST;
        } else {
            return sumList;
        }
    }

    // 清除账户数据
    private void clearCollectionData(MongoTemplate mongoTemplate, long accountId) {
        if (mongoTemplate.collectionExists(CampaignEntity.class)) {
            mongoTemplate.remove(Query.query(Criteria.where("acid").is(accountId)), CampaignEntity.class);
        }

        if (mongoTemplate.collectionExists(AdgroupEntity.class)) {
            mongoTemplate.remove(Query.query(Criteria.where("acid").is(accountId)), AdgroupEntity.class);
        }

        if (mongoTemplate.collectionExists(KeywordEntity.class)) {
            mongoTemplate.remove(Query.query(Criteria.where("acid").is(accountId)), KeywordEntity.class);
        }

        if (mongoTemplate.collectionExists(CreativeEntity.class)) {
            mongoTemplate.remove(Query.query(Criteria.where("acid").is(accountId)), CreativeEntity.class);
        }
    }

}
