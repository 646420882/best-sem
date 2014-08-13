package com.perfect.service.impl;

import com.perfect.api.baidu.ResultUtils;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.entity.*;
import com.perfect.main.BaiduApiService;
import com.perfect.mongodb.utils.BaseMongoTemplate;
import com.perfect.service.AccountDataService;
import com.perfect.service.SystemUserService;
import com.perfect.utils.BaiduServiceSupport;
import com.perfect.utils.EntityConvertUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yousheng on 2014/8/12.
 *
 * @author yousheng
 */
@Service
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

        for (BaiduAccountInfoEntity baiduAccountInfoEntity : baiduAccountInfoEntityList) {
            CommonService commonService = BaiduServiceSupport.getCommonService(baiduAccountInfoEntity);
            BaiduApiService apiService = new BaiduApiService(commonService);

            List<CampaignType> campaignTypes = apiService.getAllCampaign();

            List<CampaignEntity> campaignEntities = EntityConvertUtils.convertToCamEntity(campaignTypes);

            if (mongoTemplate.collectionExists(CampaignEntity.class)) {
                mongoTemplate.dropCollection(CampaignEntity.class);
            }
            // 保存数据
//            mongoTemplate.insertAll(campaignEntities);

//            campaignEntities = mongoTemplate.findAll(CampaignEntity.class);


            // 查询推广单元

            List<Long> ids = new ArrayList<>(campaignEntities.size());

            for (CampaignEntity campaignEntity : campaignEntities) {
                ids.add(campaignEntity.getCampaignId());
            }

            List<CampaignAdgroup> campaignAdgroupList = apiService.getAllAdGroup(ids);


            List<AdgroupType> adgroupTypeList = new ArrayList<>(campaignAdgroupList.size() << 1);

            ids.clear();
            for (CampaignAdgroup campaignAdgroup : campaignAdgroupList) {
                List<AdgroupType> adgroupTypes = campaignAdgroup.getAdgroupTypes();
                adgroupTypeList.addAll(adgroupTypes);
            }
            List<AdgroupEntity> adgroupEntities = EntityConvertUtils.convertToAdEntity(adgroupTypeList);


            List<KeywordType> keywordTypes = apiService.getAllKeyword(ids);

            List<KeywordEntity> keywordEntities = EntityConvertUtils.convertToKwEntity(keywordTypes);


            // 开始保存数据
            // 保存推广计划
            mongoTemplate.insertAll(campaignEntities);
            mongoTemplate.insertAll(adgroupEntities);
            mongoTemplate.insertAll(keywordEntities);

//            Map<Long, CampaignEntity> campaignEntityMap = new HashMap<>(campaignEntities.size());
//            for (CampaignEntity campaignEntity : campaignEntities) {
//                campaignEntityMap.put(campaignEntity.getCampaignId(), campaignEntity);
//            }

            // 设置推广计划和推广单元的关联
//            for (AdgroupEntity adgroupEntity : adgroupEntities) {
//                ids.add(adgroupEntity.getAdgroupId());
//            }

//            Map<Long, AdgroupEntity> adgroupEntityMap = new HashMap<>(adgroupTypeList.size());
//            for (AdgroupEntity adgroupEntity : adgroupEntities) {
//                adgroupEntityMap.put(adgroupEntity.getAdgroupId(), adgroupEntity);
//            }


//            for (KeywordEntity keywordEntity : keywordEntities) {
//                keywordEntity.setAdgroupEntity(adgroupEntityMap.get(keywordEntity.getAdgroupId()));
//            }
        }

    }

}
