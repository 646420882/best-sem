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
import com.perfect.utils.DBNameUtils;
import com.perfect.utils.EntityConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.invoke.MethodHandles;
import java.util.*;

import static com.perfect.mongodb.utils.EntityConstants.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * 获取账户完整数据的方法
 * 更新账户数据逻辑的方法
 * Created by yousheng on 2014/8/12.
 *
 * @author yousheng
 */
@Service("accountDataService")
public class AccountDataServiceImpl implements AccountDataService {

    private static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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

            logger.info("查询账户推广关键词...");
            List<KeywordType> keywordTypes = apiService.getAllKeyword(ids);
            logger.info("查询结束: 关键词数=" + keywordTypes.size());

            List<KeywordEntity> keywordEntities = EntityConvertUtils.convertToKwEntity(keywordTypes);

            for (KeywordEntity keywordEntity : keywordEntities) {
                keywordEntity.setAccountId(aid);
            }

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

        BaiduAccountInfoEntity _entity = null;
        for (BaiduAccountInfoEntity baiduAccountInfoEntity : baiduAccountInfoEntityList) {

            Long aid = baiduAccountInfoEntity.getId();
            if (aid != accountId)
                continue;
            _entity = baiduAccountInfoEntity;

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

            //clear data
            clearCollectionData(mongoTemplate, accountId);

            //update data
            mongoTemplate.insertAll(campaignEntities);

            mongoTemplate.insertAll(adgroupEntities);
            mongoTemplate.insertAll(keywordEntities);
            mongoTemplate.insertAll(creativeEntityList);

        }

        //update account data
        MongoTemplate mongoTemplate1 = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getSysDBName());
        Update update = new Update();
        update.set("bdAccounts.$", _entity);
        mongoTemplate1.updateFirst(
                Query.query(
                        Criteria.where("userName").is(userName).and("bdAccounts._id").is(accountId)),
                update, SystemUserEntity.class);
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
            if (accountId == entity.getId()) {
                baiduAccountInfoEntity = entity;
                break;
            }
        }

        Long acid = baiduAccountInfoEntity.getId();

        CommonService commonService = BaiduServiceSupport.getCommonService(baiduAccountInfoEntity);
        BaiduApiService apiService = new BaiduApiService(commonService);

        //获取账户总数据
        AccountInfoType accountInfoType = apiService.getAccountInfo();
        BeanUtils.copyProperties(accountInfoType, baiduAccountInfoEntity);

        //获取指定id的推广计划
        List<CampaignType> campaignTypes = apiService.getCampaignById(camIds);

        //转换成本地系统的实体
        List<CampaignEntity> campaignEntities = EntityConvertUtils.convertToCamEntity(campaignTypes);


        List<Long> localAdgroupIds = getLocalAdgroupIds(mongoTemplate, accountId, camIds);

        List<Long> localKeywordIds = getLocalKeywordIds(mongoTemplate, accountId, localAdgroupIds);

        List<Long> localCreativeIds = getLocalCreativeIds(mongoTemplate, accountId, localAdgroupIds);


        //凤巢返回回来的计划实体id
        List<Long> campaignIds = new ArrayList<>(campaignEntities.size());

        for (CampaignEntity campaignEntity : campaignEntities) {
            campaignEntity.setAccountId(acid);
            campaignIds.add(campaignEntity.getCampaignId());
        }

        List<AdgroupType> adgroupTypeList = apiService.getAllAdGroup(campaignIds);

        List<AdgroupEntity> adgroupEntities = EntityConvertUtils.convertToAdEntity(adgroupTypeList);

        List<Long> adgroupdIds = new ArrayList<>(adgroupEntities.size());
        for (AdgroupEntity adgroupEntity : adgroupEntities) {
            adgroupEntity.setAccountId(acid);
            adgroupdIds.add(adgroupEntity.getAdgroupId());
        }

        List<KeywordType> keywordTypes = apiService.getAllKeyword(adgroupdIds);

        List<KeywordEntity> keywordEntities = EntityConvertUtils.convertToKwEntity(keywordTypes);

//        List<Long> kwids = new ArrayList<>(keywordEntities.size());
        for (KeywordEntity keywordEntity : keywordEntities) {
            keywordEntity.setAccountId(acid);
//            kwids.add(keywordEntity.getKeywordId());
        }

        List<CreativeType> creativeTypes = apiService.getAllCreative(adgroupdIds);

        List<CreativeEntity> creativeEntityList = EntityConvertUtils.convertToCrEntity(creativeTypes);

//        List<Long> creativeIds = new ArrayList<>(creativeEntityList.size());
        for (CreativeEntity creativeEntity : creativeEntityList) {
            creativeEntity.setAccountId(acid);
//            creativeIds.add(creativeEntity.getCreativeId());
        }

        //clear data
        clearCampaignData(mongoTemplate, accountId, camIds);
        clearAdgroupData(mongoTemplate, accountId, localAdgroupIds);
        clearKeywordData(mongoTemplate, accountId, localKeywordIds);
        clearCreativeData(mongoTemplate, accountId, localCreativeIds);
        //update data
        mongoTemplate.insertAll(campaignEntities);

        mongoTemplate.insertAll(adgroupEntities);
        mongoTemplate.insertAll(keywordEntities);
        mongoTemplate.insertAll(creativeEntityList);

        //update account data
        MongoTemplate mongoTemplate1 = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getSysDBName());
        Update update = new Update();
        update.set("bdAccounts.$", baiduAccountInfoEntity);
        mongoTemplate1.updateFirst(
                Query.query(
                        Criteria.where("userName").is(userName).and("bdAccounts._id").is(accountId)),
                update, SystemUserEntity.class);

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
        Map<Long, CampaignEntity> campaignEntityMap = new LinkedHashMap<>();
        for (CampaignEntity campaignEntity : campaignEntities) {
            campaignEntity.setAccountId(acid);
            campaignEntityMap.put(campaignEntity.getCampaignId(), campaignEntity);
        }

        List<CampaignEntity> sumList = new ArrayList<>(campaignEntityList);
        sumList.addAll(campaignEntities);
        for (CampaignEntity entity : sumList) {
            Long campaignId = entity.getCampaignId();
            if (campaignId == null) {
                continue;
            }
            if (campaignEntityMap.get(campaignId) != null) {
                campaignEntityMap.remove(campaignId);
            }
        }

        if (campaignEntityMap.size() == 0) {
            return Collections.EMPTY_LIST;
        } else {
            return new ArrayList<>(campaignEntityMap.values());
        }
    }

    // 清除账户数据
    private void clearCollectionData(MongoTemplate mongoTemplate, long accountId) {
        if (mongoTemplate.collectionExists(CampaignEntity.class)) {
            mongoTemplate.remove(Query.query(Criteria.where(ACCOUNT_ID).is(accountId)), CampaignEntity.class);
        }

        if (mongoTemplate.collectionExists(AdgroupEntity.class)) {
            mongoTemplate.remove(Query.query(Criteria.where(ACCOUNT_ID).is(accountId)), AdgroupEntity.class);
        }

        if (mongoTemplate.collectionExists(KeywordEntity.class)) {
            mongoTemplate.remove(Query.query(Criteria.where(ACCOUNT_ID).is(accountId)), KeywordEntity.class);
        }

        if (mongoTemplate.collectionExists(CreativeEntity.class)) {
            mongoTemplate.remove(Query.query(Criteria.where(ACCOUNT_ID).is(accountId)), CreativeEntity.class);
        }
    }

    private void clearCampaignData(MongoTemplate mongoTemplate, long accountId, List<Long> campaignIds) {
        Query query = new Query(Criteria.where(ACCOUNT_ID).is(accountId).and(CAMPAIGN_ID).in(campaignIds));
        if (mongoTemplate.collectionExists(CampaignEntity.class)) {
            mongoTemplate.remove(query, TBL_CAMPAIGN);
        }
    }

    private void clearAdgroupData(MongoTemplate mongoTemplate, long accountId, List<Long> adgroupIds) {
        Query query = new Query(Criteria.where(ACCOUNT_ID).is(accountId).and(ADGROUP_ID).in(adgroupIds));
        if (mongoTemplate.collectionExists(AdgroupEntity.class)) {
            mongoTemplate.remove(query, TBL_ADGROUP);
        }
    }

    private void clearKeywordData(MongoTemplate mongoTemplate, long accountId, List<Long> keywordIds) {
        Query query = new Query(Criteria.where(ACCOUNT_ID).is(accountId).and(KEYWORD_ID).in(keywordIds));
        if (mongoTemplate.collectionExists(KeywordEntity.class)) {
            mongoTemplate.remove(query, TBL_KEYWORD);
        }
    }

    private void clearCreativeData(MongoTemplate mongoTemplate, long accountId, List<Long> creativeIds) {
        Query query = new Query(Criteria.where(ACCOUNT_ID).is(accountId).and(CREATIVE_ID).in(creativeIds));
        if (mongoTemplate.collectionExists(CreativeEntity.class)) {
            mongoTemplate.remove(query, TBL_CREATIVE);
        }
    }

    private List<Long> getLocalAdgroupIds(MongoTemplate mongoTemplate, Long accountId, List<Long> campaignIds) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where(ACCOUNT_ID).is(accountId).and(CAMPAIGN_ID).in(campaignIds)),
                project(ADGROUP_ID).andExclude("_id")
        );
        AggregationResults<AdgroupEntity> results = mongoTemplate.aggregate(aggregation, TBL_ADGROUP, AdgroupEntity.class);
        List<Long> ids = new ArrayList<>();
        for (AdgroupEntity entity : results) {
            ids.add(entity.getAdgroupId());
        }
        return ids;
    }

    private List<Long> getLocalKeywordIds(MongoTemplate mongoTemplate, Long accountId, List<Long> adgroupIds) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where(ACCOUNT_ID).is(accountId).and(ADGROUP_ID).in(adgroupIds)),
                project(KEYWORD_ID).andExclude("_id")
        );
        AggregationResults<KeywordEntity> results = mongoTemplate.aggregate(aggregation, TBL_KEYWORD, KeywordEntity.class);
        List<Long> ids = new ArrayList<>();
        for (KeywordEntity entity : results) {
            ids.add(entity.getKeywordId());
        }
        return ids;
    }

    private List<Long> getLocalCreativeIds(MongoTemplate mongoTemplate, Long accountId, List<Long> adgroupIds) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where(ACCOUNT_ID).is(accountId).and(ADGROUP_ID).in(adgroupIds)),
                project(CREATIVE_ID).andExclude("_id")
        );
        AggregationResults<CreativeEntity> results = mongoTemplate.aggregate(aggregation, TBL_CREATIVE, CreativeEntity.class);
        List<Long> ids = new ArrayList<>();
        for (CreativeEntity entity : results) {
            ids.add(entity.getCreativeId());
        }
        return ids;
    }

}