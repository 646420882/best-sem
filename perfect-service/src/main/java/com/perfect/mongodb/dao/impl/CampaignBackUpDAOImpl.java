package com.perfect.mongodb.dao.impl;

import com.perfect.dao.CampaignBackUpDAO;
import com.perfect.entity.backup.CampaignBackUpEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.utils.EntityConstants;
import com.perfect.mongodb.utils.Pager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by john on 2014/9/16.
 */
@Service
public class CampaignBackUpDAOImpl extends AbstractUserBaseDAOImpl<CampaignBackUpEntity,Long> implements CampaignBackUpDAO{
    @Override
    public Class<CampaignBackUpEntity> getEntityClass() {
        return CampaignBackUpEntity.class;
    }

    @Override
    public CampaignBackUpEntity findByObjectId(String id) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        return mongoTemplate.findOne(new Query(),getEntityClass(), EntityConstants.BAK_CAMPAIGN);
    }

    @Override
    public void deleteByObjectId(String id) {

    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    public  void deleteByCid(long cid){
        MongoTemplate mongoTemplate = getMongoTemplate();
        mongoTemplate.remove(new Query(Criteria.where(EntityConstants.CAMPAIGN_ID).is(cid)),getEntityClass(),EntityConstants.BAK_CAMPAIGN);
    }

    public  CampaignBackUpEntity findOne(long cid){
        MongoTemplate mongoTemplate = getMongoTemplate();
        List<CampaignBackUpEntity> list= mongoTemplate.find(new Query(Criteria.where(EntityConstants.CAMPAIGN_ID).is(cid)),getEntityClass(),EntityConstants.BAK_CAMPAIGN);
        return list.size()==0?null:list.get(0);
    }
}
