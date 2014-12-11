package com.perfect.db.mongodb.impl;

import com.google.common.collect.Lists;
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.entity.adgroup.AdgroupEntity;
import com.perfect.entity.campaign.CampaignEntity;
import com.perfect.entity.creative.CreativeEntity;
import com.perfect.entity.keyword.KeywordEntity;
import com.perfect.entity.sys.BaiduAccountInfoEntity;
import com.perfect.entity.sys.SystemUserEntity;
import com.perfect.utils.ObjectUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by vbzer_000 on 2014-6-19.
 * 2014-12-2 refactor
 */
@Repository("systemUserDAO")
public class SystemUserDAOImpl extends AbstractSysBaseDAOImpl<SystemUserDTO, String> implements SystemUserDAO {

    @Override
    @SuppressWarnings("unchecked")
    public Class<SystemUserEntity> getEntityClass() {
        return SystemUserEntity.class;
    }

    @Override
    public Class<SystemUserDTO> getDTOClass() {
        return SystemUserDTO.class;
    }

    @Override
    public void addBaiduAccount(List<BaiduAccountInfoDTO> list, String currSystemUserName) {
        SystemUserDTO currSystemUserDTO = findByUserName(currSystemUserName);
        List<BaiduAccountInfoDTO> _list = currSystemUserDTO.getBaiduAccounts();
        if (_list == null)
            _list = new ArrayList<>();

        _list.addAll(list);
        getSysMongoTemplate().updateFirst(Query.query(Criteria.where("userName").is(currSystemUserName)), Update.update("baiduAccountInfos", _list), "sys_user");
    }

    @Override
    public SystemUserDTO findByAid(long aid) {
        Query query = Query.query(Criteria.where("bdAccounts._id").is(aid));
        SystemUserEntity entity = getSysMongoTemplate().findOne(query, getEntityClass());
        SystemUserDTO dto=ObjectUtils.convertToObject(entity,getDTOClass());
        return dto;
    }

    @Override
    public void insertAccountInfo(String userName, BaiduAccountInfoDTO baiduAccountInfoDTO) {
        SystemUserDTO systemUserDTO = findByUserName(userName);
        if (systemUserDTO.getBaiduAccounts().isEmpty())
            baiduAccountInfoDTO.setDfault(true);

        BaiduAccountInfoEntity baiduAccountInfoEntity = ObjectUtils.convert(baiduAccountInfoDTO, BaiduAccountInfoEntity.class);
        Update update = new Update();
        update.addToSet("bdAccounts", baiduAccountInfoEntity);
        getSysMongoTemplate().upsert(Query.query(Criteria.where("userName").is(userName)), update, getEntityClass());
    }

    @Override
    public void removeAccountInfo(Long id) {
        Update update = new Update();
        update.unset("bdAccounts");
        getSysMongoTemplate().updateFirst(Query.query(Criteria.where("bdAccounts._id").is(id)), update, getEntityClass());
    }

    @Override
    public void clearAccountData(Long accountId) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        if (mongoTemplate.collectionExists(CampaignEntity.class))
            mongoTemplate.remove(Query.query(Criteria.where(ACCOUNT_ID).is(accountId)), CampaignEntity.class);

        if (mongoTemplate.collectionExists(AdgroupEntity.class))
            mongoTemplate.remove(Query.query(Criteria.where(ACCOUNT_ID).is(accountId)), AdgroupEntity.class);

        if (mongoTemplate.collectionExists(KeywordEntity.class))
            mongoTemplate.remove(Query.query(Criteria.where(ACCOUNT_ID).is(accountId)), KeywordEntity.class);

        if (mongoTemplate.collectionExists(CreativeEntity.class))
            mongoTemplate.remove(Query.query(Criteria.where(ACCOUNT_ID).is(accountId)), CreativeEntity.class);
    }

    @Override
    public void clearCampaignData(Long accountId, List<Long> campaignIds) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        Query query = new Query(Criteria.where(ACCOUNT_ID).is(accountId).and(CAMPAIGN_ID).in(campaignIds));
        if (mongoTemplate.collectionExists(CampaignEntity.class))
            mongoTemplate.remove(query, TBL_CAMPAIGN);
    }

    @Override
    public void clearAdgroupData(Long accountId, List<Long> adgroupIds) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        Query query = new Query(Criteria.where(ACCOUNT_ID).is(accountId).and(ADGROUP_ID).in(adgroupIds));
        if (mongoTemplate.collectionExists(AdgroupEntity.class))
            mongoTemplate.remove(query, TBL_ADGROUP);
    }

    @Override
    public void clearKeywordData(Long accountId, List<Long> keywordIds) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        Query query = new Query(Criteria.where(ACCOUNT_ID).is(accountId).and(KEYWORD_ID).in(keywordIds));
        if (mongoTemplate.collectionExists(KeywordEntity.class))
            mongoTemplate.remove(query, TBL_KEYWORD);
    }

    @Override
    public void clearCreativeData(Long accountId, List<Long> creativeIds) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        Query query = new Query(Criteria.where(ACCOUNT_ID).is(accountId).and(CREATIVE_ID).in(creativeIds));
        if (mongoTemplate.collectionExists(CreativeEntity.class))
            mongoTemplate.remove(query, TBL_CREATIVE);
    }

    @Override
    public List<Long> getLocalAdgroupIds(Long accountId, List<Long> campaignIds) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where(ACCOUNT_ID).is(accountId).and(CAMPAIGN_ID).in(campaignIds)),
                project(ADGROUP_ID).andExclude(SYSTEM_ID)
        );
        AggregationResults<AdgroupEntity> results = getMongoTemplate().aggregate(aggregation, TBL_ADGROUP, AdgroupEntity.class);
        List<Long> ids = new ArrayList<>();
        results.getMappedResults().parallelStream().forEach(e -> ids.add(e.getAdgroupId()));
        return ids;
    }

    @Override
    public List<Long> getLocalKeywordIds(Long accountId, List<Long> adgroupIds) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where(ACCOUNT_ID).is(accountId).and(ADGROUP_ID).in(adgroupIds)),
                project(KEYWORD_ID).andExclude(SYSTEM_ID)
        );
        AggregationResults<KeywordEntity> results = getMongoTemplate().aggregate(aggregation, TBL_KEYWORD, KeywordEntity.class);
        List<Long> ids = new ArrayList<>();
        results.getMappedResults().parallelStream().forEach(e -> ids.add(e.getKeywordId()));
        return ids;
    }

    @Override
    public List<Long> getLocalCreativeIds(Long accountId, List<Long> adgroupIds) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where(ACCOUNT_ID).is(accountId).and(ADGROUP_ID).in(adgroupIds)),
                project(CREATIVE_ID).andExclude(SYSTEM_ID)
        );
        AggregationResults<CreativeEntity> results = getMongoTemplate().aggregate(aggregation, TBL_CREATIVE, CreativeEntity.class);
        List<Long> ids = new ArrayList<>();
        results.getMappedResults().parallelStream().forEach(e -> ids.add(e.getCreativeId()));
        return ids;
    }

    @Override
    public SystemUserDTO findByUserName(String userName) {
        SystemUserEntity systemUserEntity = getSysMongoTemplate().findOne(
                Query.query(Criteria.where("userName").is(userName)),
                getEntityClass(),
                "sys_user");
        return fromEntity(systemUserEntity);
    }

    @Override
    public SystemUserDTO save(SystemUserDTO dto) {
        getSysMongoTemplate().save(toEntity(dto));
        return dto;
    }

    @Override
    public List<SystemUserDTO> find(Map<String, Object> params, int skip, int limit) {
        return Lists.newArrayList();
    }

    protected SystemUserDTO fromEntity(SystemUserEntity systemUserEntity) {
        List<BaiduAccountInfoEntity> baiduAccountInfoEntityList = systemUserEntity.getBaiduAccounts();
        SystemUserDTO user = ObjectUtils.convert(systemUserEntity, getDTOClass());

        List<BaiduAccountInfoDTO> dtoList = convertByClass(baiduAccountInfoEntityList, BaiduAccountInfoDTO.class);
        user.setBaiduAccounts(dtoList);
        return user;
    }


    protected SystemUserEntity toEntity(SystemUserDTO systemUserDTO) {
        List<BaiduAccountInfoDTO> baiduAccountInfoDTOList = systemUserDTO.getBaiduAccounts();
        SystemUserEntity user = ObjectUtils.convert(systemUserDTO, getEntityClass());

        List<BaiduAccountInfoEntity> baiduAccountInfoEntityList = convertByClass(baiduAccountInfoDTOList, BaiduAccountInfoEntity.class);
        user.setBaiduAccounts(baiduAccountInfoEntityList);
        return user;
    }
}
