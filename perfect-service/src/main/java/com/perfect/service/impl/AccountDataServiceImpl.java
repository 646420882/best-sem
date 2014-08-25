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
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yousheng on 2014/8/12.
 *
 * @author yousheng
 */
@Component("accountDataService")
public class AccountDataServiceImpl implements AccountDataService {

    @Resource
    private SystemUserService systemUserService;

    @Override
    public void initAccountData(String userName) {
        SystemUserEntity systemUserEntity = systemUserService.getSystemUser(userName);

        if (systemUserEntity == null) {
            return;
        }

        List<BaiduAccountInfoEntity> baiduAccountInfoEntityList = systemUserEntity.getBaiduAccountInfoEntities();

        if (baiduAccountInfoEntityList == null || baiduAccountInfoEntityList.isEmpty()) {
            return;
        }

        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo(userName);
        clearCollection(mongoTemplate);

        for (BaiduAccountInfoEntity baiduAccountInfoEntity : baiduAccountInfoEntityList) {

            Long aid = baiduAccountInfoEntity.getId();

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

    // 清除账户数据
    private void clearCollection(MongoTemplate mongoTemplate) {
        if (mongoTemplate.collectionExists(CampaignEntity.class)) {
            mongoTemplate.dropCollection(CampaignEntity.class);
        }

        if (mongoTemplate.collectionExists(AdgroupEntity.class)) {
            mongoTemplate.dropCollection(AdgroupEntity.class);
        }

        if (mongoTemplate.collectionExists(KeywordEntity.class)) {
            mongoTemplate.dropCollection(KeywordEntity.class);
        }

        if (mongoTemplate.collectionExists(CreativeEntity.class)) {
            mongoTemplate.dropCollection(CreativeEntity.class);
        }
    }

}
