package com.perfect.mongodb.dao.impl;

import com.perfect.dao.BiddingRuleDAO;
import com.perfect.dao.SystemUserDAO;
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.utils.Pager;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by yousheng on 2014/8/1.
 *
 * @author yousheng
 */
@Repository("biddingRuleDAO")
public class BiddingRuleDAOImpl extends AbstractUserBaseDAOImpl<BiddingRuleEntity, String> implements BiddingRuleDAO {

    @Resource
    private SystemUserDAO systemUserDAO;

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public BiddingRuleEntity findOne(String s) {
        return getMongoTemplate().findOne(Query.query(Criteria.where("id").is(s)), BiddingRuleEntity.class);
    }

    @Override
    public boolean exists(String s) {
        return getMongoTemplate().exists(Query.query(Criteria.where("id").is(s)), BiddingRuleEntity.class);
    }

    @Override
    public List<BiddingRuleEntity> findAll() {
        return getMongoTemplate().findAll(BiddingRuleEntity.class);
    }

    @Override
    public Iterable<BiddingRuleEntity> findAll(Iterable<String> strings) {
        return getMongoTemplate().find(Query.query(Criteria.where("id").in(strings)), BiddingRuleEntity.class);
    }

    @Override
    public List<BiddingRuleEntity> find(Map<String, Object> params, int skip, int limit) {
        Query query = new Query();
        Criteria criteria = null;
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (criteria == null) {
                criteria = new Criteria(param.getKey());
                criteria.is(param.getValue());
                continue;
            }

            criteria.and(param.getKey()).is(param.getValue());
        }

        query.addCriteria(criteria).skip(skip).limit(limit);

        return getMongoTemplate().find(query, BiddingRuleEntity.class);
    }

    @Override
    public Class<BiddingRuleEntity> getEntityClass() {
        return BiddingRuleEntity.class;
    }

    @Override
    public void delete(Iterable<? extends BiddingRuleEntity> entities) {

    }

//    @Override
//    public void createBidding(BiddingRuleEntity biddingRuleEntity) {
//        getMongoTemplate().insert(biddingRuleEntity);
//
//    }
//
//    @Override
//    public void batchCreate(List<BiddingRuleEntity> biddingRuleEntityList) {
//        getMongoTemplate().insertAll(biddingRuleEntityList);
//    }

//    @Override
//    public void updateBiddingRule(BiddingRuleEntity biddingRuleEntity) {
//        updateById(biddingRuleEntity);
//    }

//    @Override
//    public BiddingRuleEntity getBiddingRuleByKeywordId(String keywordId) {
//        Query query = new Query(Criteria.where("kw.id").is(keywordId));
//
//        return getMongoTemplate().findOne(query, BiddingRuleEntity.class);
//    }

    /**
     * 获取当前所有需要执行的竞价规则
     *
     * @return
     */
//    @Override
//    public List<BiddingRuleEntity> getReadyRule() {
//        Long time = System.currentTimeMillis();
//
//        Query query = Query.query(Criteria.where("next").lte(time));
//
//        List<SystemUserEntity> systemUserEntities = systemUserDAO.findAll();
//
//        List<BiddingRuleEntity> biddingRuleEntityList = new ArrayList<>();
//        for (SystemUserEntity entity : systemUserEntities) {
//            MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo(entity.getUserName());
////            biddingRuleEntityList.addAll(mongoTemplate.findAndModify(query, Update.update("next",).class));
//        }
//
//        return biddingRuleEntityList;
//    }
//
//    @Override
//    public boolean disableRule(String id) {
//        MongoTemplate mongoTemplate = getMongoTemplate();
//
//        WriteResult wr = mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(id)), Update.update("ebl", false), BiddingRuleEntity.class);
//
//
//        return wr.getN() > 0;
//    }
//
//    @Override
//    public List<BiddingRuleEntity> getNextRunByGroupId(String userName, Long id, int gid) {
//
//        List<BiddingRuleEntity> biddingRuleEntityList = new ArrayList<>();
//
//        long time = System.currentTimeMillis();
//        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo(userName);
//
//        biddingRuleEntityList.addAll(mongoTemplate.find(Query.query(Criteria.where("ebl").is(true).and("gid").is(gid).and("aid").is(id).and("next").lte(time)), BiddingRuleEntity.class));
//
//        return biddingRuleEntityList;
//    }
//
//    @Override
//    public void updateToNextRunTime(List<BiddingRuleEntity> tasks) {
//
//        Map<Long, SystemUserEntity> userMap = new HashMap<>();
//
//        for (BiddingRuleEntity entity : tasks) {
//            long aid = entity.getAccountId();
//            SystemUserEntity systemUserEntity = null;
//            if (userMap.containsKey(aid)) {
//                systemUserEntity = userMap.get(aid);
//            } else {
//                systemUserEntity = systemUserDAO.findByAid(aid);
//                userMap.put(aid, systemUserEntity);
//            }
//
//            MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo(systemUserEntity.getUserName());
//            mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(entity.getId())), Update.update("next", entity.getNextTime()), BiddingRuleEntity.class);
//        }
//    }
    private Update getUpdate(BiddingRuleEntity entity) {
        Update update = new Update();

//        if (entity.getNextRunTime() > 0) {
//            update = update.addToSet("next", entity.getNextRunTime());
//        }
//
//        if (entity.getPosition() > 0) {
//            update = update.addToSet("pos", entity.getPosition());
//        }
//
//        if (entity.getInterval() > 0) {
//            update = update.addToSet("intval", entity.getInterval());
//        }
//
//        if (entity.getPositionStrategy() > 0) {
//            update = update.addToSet("pstra", entity.getPositionStrategy());
//        }
//
//        if (entity.getMaxPrice() > 0) {
//            update = update.addToSet("max", entity.getMaxPrice());
//        }
//
//        if (entity.getMinPrice() > 0) {
//            update = update.addToSet("min", entity.getMinPrice());
//        }
//
//        if (entity.getPriority() > 0) {
//            update = update.addToSet("priority", entity.getPriority());
//        }

        return update;
    }

    @Override
    public void createBidding(BiddingRuleEntity biddingRuleEntity) {

    }

    @Override
    public void batchCreate(List<BiddingRuleEntity> biddingRuleEntityList) {

    }

    @Override
    public void updateBiddingRule(BiddingRuleEntity biddingRuleEntity) {

    }

    @Override
    public BiddingRuleEntity getBiddingRuleByKeywordId(String keywordId) {
        return null;
    }

    @Override
    public List<BiddingRuleEntity> getReadyRule() {
        return null;
    }

    @Override
    public boolean disableRule(String id) {
        return false;
    }

    @Override
    public List<BiddingRuleEntity> getNextRunByGroupId(String userName, Long id, int gid) {
        return null;
    }

    @Override
    public void updateToNextRunTime(List<BiddingRuleEntity> tasks) {

    }
}
