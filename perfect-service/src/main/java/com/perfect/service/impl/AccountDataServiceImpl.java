package com.perfect.service.impl;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.entity.*;
import com.perfect.main.BaiduApiService;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.service.AccountDataService;
import com.perfect.service.SystemUserService;
import com.perfect.utils.BaiduServiceSupport;
import com.perfect.utils.EntityConvertUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取账户完整数据的方法
 * Created by yousheng on 2014/8/12.
 *
 * @author yousheng
 */
@Component("accountDataService")
public class AccountDataServiceImpl implements AccountDataService {

    @Resource
    private SystemUserService systemUserService;

    @Override
    public void initAccountData(String userName, long accountId) {
        SystemUserEntity systemUserEntity = systemUserService.getSystemUser(userName);

        if (systemUserEntity == null) {
            return;
        }

        List<BaiduAccountInfoEntity> baiduAccountInfoEntityList = systemUserEntity.getBaiduAccountInfoEntities();

        if (baiduAccountInfoEntityList == null || baiduAccountInfoEntityList.isEmpty()) {
            return;
        }

        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo(userName);
        clearCollectionData(mongoTemplate, accountId);

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
            List<Long> ids = new ArrayList<>(campaignEntities.size());

            for (CampaignEntity campaignEntity : campaignEntities) {
                campaignEntity.setAccountId(aid);
                ids.add(campaignEntity.getCampaignId());
            }

            List<CampaignAdgroup> campaignAdgroupList = apiService.getAllAdGroup(ids);


            List<AdgroupType> adgroupTypeList = new ArrayList<>(campaignAdgroupList.size() << 1);

            for (CampaignAdgroup campaignAdgroup : campaignAdgroupList) {
                List<AdgroupType> adgroupTypes = campaignAdgroup.getAdgroupTypes();
                adgroupTypeList.addAll(adgroupTypes);
            }

            List<AdgroupEntity> adgroupEntities = EntityConvertUtils.convertToAdEntity(adgroupTypeList);
            ids.clear();
            for (AdgroupEntity adgroupEntity : adgroupEntities) {
                adgroupEntity.setAccountId(aid);
                ids.add(adgroupEntity.getAdgroupId());
            }

            List<KeywordType> keywordTypes = apiService.getAllKeyword(ids);

            List<KeywordEntity> keywordEntities = EntityConvertUtils.convertToKwEntity(keywordTypes);

            for (KeywordEntity keywordEntity : keywordEntities) {
                keywordEntity.setAccountId(aid);
            }


            List<CreativeType> creativeTypes = apiService.getAllCreative(ids);

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

            List<CampaignAdgroup> campaignAdgroupList = apiService.getAllAdGroup(camIds);


            List<AdgroupType> adgroupTypeList = new ArrayList<>(campaignAdgroupList.size() << 1);

            for (CampaignAdgroup campaignAdgroup : campaignAdgroupList) {
                List<AdgroupType> adgroupTypes = campaignAdgroup.getAdgroupTypes();
                adgroupTypeList.addAll(adgroupTypes);
            }

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
            mongoTemplate.findAllAndRemove(Query.query(Criteria.where("cid").in(camIds)), CampaignEntity.class);
            mongoTemplate.insertAll(campaignEntities);

            mongoTemplate.insertAll(adgroupEntities);
            mongoTemplate.insertAll(keywordEntities);
            mongoTemplate.insertAll(creativeEntityList);
        }
        systemUserService.save(systemUserEntity);
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
