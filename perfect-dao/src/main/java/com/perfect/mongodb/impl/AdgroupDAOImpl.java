package com.perfect.mongodb.impl;

import com.perfect.dao.AdgroupDAO;
import com.perfect.entity.AdgroupEntity;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.perfect.constants.AdgroupEntityConstant.*;

/**
 * Created by vbzer_000 on 2014/7/2.
 */
@Repository(value = "adgroupDAO")
public class AdgroupDAOImpl extends AbstractBaseDAOImpl<AdgroupEntity> implements AdgroupDAO {

    @Override
    public void deleteAll() {
        getMongoTemplate().dropCollection(AdgroupEntity.class);
    }

    @Override
    public void updateById(AdgroupEntity adgroupEntity) {

    }

    @Override
    public void update(AdgroupEntity s, AdgroupEntity d) {

    }

    @Override
    public AdgroupEntity findById(String id) {
        return getMongoTemplate().findById(id, AdgroupEntity.class);
    }

    @Override
    public List<AdgroupEntity> findAll() {
        return getMongoTemplate().findAll(AdgroupEntity.class);
    }

    @Override
    public List<AdgroupEntity> find(AdgroupEntity adgroupEntity, int skip, int limit) {
        List<AdgroupEntity> adgroupEntityList = getMongoTemplate().find(new Query(Criteria.where("adgroupName").regex("*" + adgroupEntity.getAdgroupName() + "*")), AdgroupEntity.class);

        return adgroupEntityList.subList(skip, limit);
    }

    @Override
    public AdgroupEntity findAndModify(AdgroupEntity q, AdgroupEntity u) {
//        getMongoTemplate().findAndModify(new Query(Criteria.where(ID).is(q.getAdgroupId())))
        return null;
    }

    @Override
    public AdgroupEntity findAndRemove(AdgroupEntity adgroupEntity) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }


    public Update getUpdate(AdgroupEntity adgroupEntity) {
        Update update = new Update();

        if (adgroupEntity.getAdgroupName() != null) {
            update = update.addToSet(ADGROUP_NAME, adgroupEntity.getAdgroupName());
        }

        if (adgroupEntity.getCampaignId() != null) {
            update = update.addToSet(CAMPAIGN_ID, adgroupEntity.getCampaignId());
        }

        if (adgroupEntity.getPause() != null) {
            update = update.addToSet(PAUSE, adgroupEntity.getPause());
        }

        if (adgroupEntity.getExactNegativeWords() != null) {
            update = update.addToSet(EXA_NEG_WORDS, adgroupEntity.getExactNegativeWords());
        }

        if (adgroupEntity.getNegativeWords() != null) {
            update = update.addToSet(NEG_WORDS, adgroupEntity.getNegativeWords());
        }

        if (adgroupEntity.getReserved() != null) {
            update = update.addToSet(RESERVED, adgroupEntity.getReserved());
        }


        if (adgroupEntity.getMaxPrice() != null) {
            update = update.addToSet(MAX_PRICE, adgroupEntity.getMaxPrice());
        }
        return update;
    }
}
