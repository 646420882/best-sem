package com.perfect.mongodb.impl;

import com.perfect.dao.CampaignDAO;
import com.perfect.entity.CampaignEntity;

import java.util.List;

/**
 * Created by vbzer_000 on 2014/6/27.
 */
public class CampaignDAOImplImpl extends AbstractBaseDAOImpl<CampaignEntity> implements CampaignDAO {
    @Override
    public void deleteAll() {
        getMongoTemplate().dropCollection(CampaignEntity.class);
    }

    @Override
    public void updateById(CampaignEntity campaignEntity) {

    }

    @Override
    public void update(CampaignEntity s, CampaignEntity d) {

    }

    @Override
    public CampaignEntity findById(String id) {
        return null;
    }

    @Override
    public List<CampaignEntity> findAll() {
        return null;
    }

    @Override
    public List<CampaignEntity> find(CampaignEntity campaignEntity, int skip, int limit) {
        return null;
    }

    @Override
    public CampaignEntity findAndModify(CampaignEntity q, CampaignEntity u) {
        return null;
    }

    @Override
    public CampaignEntity findAndRemove(CampaignEntity campaignEntity) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }
}
