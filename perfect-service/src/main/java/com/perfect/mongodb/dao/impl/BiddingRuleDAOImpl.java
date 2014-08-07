package com.perfect.mongodb.dao.impl;

import com.perfect.dao.BiddingRuleDAO;
import com.perfect.entity.BiddingRuleEntity;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yousheng on 2014/8/1.
 *
 * @author yousheng
 */
@Repository("biddingRuleDAO")
public class BiddingRuleDAOImpl extends AbstractBaseDAOImpl<BiddingRuleEntity> implements BiddingRuleDAO {

    @Override
    public void deleteAll() {
        getMongoTemplate().dropCollection(BiddingRuleEntity.class);
    }

    @Override
    public void updateById(BiddingRuleEntity biddingRuleEntity) {
        getMongoTemplate().updateFirst(new Query(Criteria.where("id").is(biddingRuleEntity.getId())), getUpdate(biddingRuleEntity), BiddingRuleEntity.class);
    }

    @Override
    public void update(BiddingRuleEntity s, BiddingRuleEntity d) {
    }

    @Override
    public BiddingRuleEntity findById(String id) {
        return getMongoTemplate().findById(id, BiddingRuleEntity.class);
    }

    @Override
    public List<BiddingRuleEntity> findAll() {
        return getMongoTemplate().findAll(BiddingRuleEntity.class);
    }

    @Override
    public List<BiddingRuleEntity> find(BiddingRuleEntity biddingRuleEntity, int skip, int limit) {
        return null;
    }

    @Override
    public BiddingRuleEntity findAndModify(BiddingRuleEntity q, BiddingRuleEntity u) {
        return null;
    }

    @Override
    public BiddingRuleEntity findAndRemove(BiddingRuleEntity biddingRuleEntity) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void createBidding(BiddingRuleEntity biddingRuleEntity) {
        getMongoTemplate().insert(biddingRuleEntity);

    }

    @Override
    public void batchCreate(List<BiddingRuleEntity> biddingRuleEntityList) {
        getMongoTemplate().insertAll(biddingRuleEntityList);
    }

    @Override
    public void updateBiddingRule(BiddingRuleEntity biddingRuleEntity) {
        updateById(biddingRuleEntity);
    }

    @Override
    public BiddingRuleEntity getBiddingRuleByKeywordId(String keywordId) {
        Query query = new Query(Criteria.where("kw.id").is(keywordId));

        return getMongoTemplate().findOne(query,BiddingRuleEntity.class);
    }

    private Update getUpdate(BiddingRuleEntity entity) {
        Update update = new Update();

        if (entity.getStartTime() > 0) {
            update = update.addToSet("start", entity.getStartTime());
        }

        if (entity.getEndTime() > 0) {
            update = update.addToSet("end", entity.getEndTime());
        }

        if (entity.getPosition() > 0) {
            update = update.addToSet("pos", entity.getPosition());
        }

        if (entity.getInterval() > 0) {
            update = update.addToSet("intval", entity.getInterval());
        }

        if (entity.getPositionStrategy() > 0) {
            update = update.addToSet("pstra", entity.getPositionStrategy());
        }

        if (entity.getMaxPrice() > 0) {
            update = update.addToSet("max", entity.getMaxPrice());
        }

        if (entity.getMinPrice() > 0) {
            update = update.addToSet("min", entity.getMinPrice());
        }

        if (entity.getPriority() > 0) {
            update = update.addToSet("priority", entity.getPriority());
        }


        return update;
    }

}
