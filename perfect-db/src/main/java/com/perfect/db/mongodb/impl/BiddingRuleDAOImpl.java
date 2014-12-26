package com.perfect.db.mongodb.impl;

import com.mongodb.WriteResult;
import com.perfect.dao.bidding.BiddingRuleDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.db.mongodb.utils.PageParamUtils;
import com.perfect.dto.bidding.BiddingRuleDTO;
import com.perfect.dto.bidding.StrategyDTO;
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.entity.bidding.StrategyEntity;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.paging.PaginationParam;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;

/**
 * Created by yousheng on 2014/8/1.
 *
 * @author yousheng
 */
@Repository("biddingRuleDAO")
public class BiddingRuleDAOImpl extends AbstractUserBaseDAOImpl<BiddingRuleDTO, Long> implements BiddingRuleDAO {


    @Override
    public Class<BiddingRuleDTO> getDTOClass() {
        return BiddingRuleDTO.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<BiddingRuleEntity> getEntityClass() {
        return BiddingRuleEntity.class;
    }

    @Override
    public List<BiddingRuleDTO> find(Map<String, Object> params, int skip, int limit) {
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

        if (criteria != null) {
            query.addCriteria(criteria).skip(skip).limit(limit);
        }

        return convertToDTOList(getMongoTemplate().find(query, getEntityClass()));
    }


    /**
     * 获取当前所有需要执行的竞价规则
     *
     * @return
     */
//    @Override
//    public List<BiddingRuleDTO> getReadyRule() {
//        Long time = System.currentTimeMillis();
//
//        Query query = Query.query(Criteria.where("next").lte(time));
//
//        List<SystemUserEntity> systemUserEntities = systemUserDAO.findAll();
//
//        List<BiddingRuleDTO> biddingRuleEntityList = new ArrayList<>();
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
//        WriteResult wr = mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(id)), Update.update("ebl", false), BiddingRuleDTO.class);
//
//
//        return wr.getN() > 0;
//    }
//
//    @Override
//    public List<BiddingRuleDTO> getTaskByAccountId(String userName, Long id, int gid) {
//
//        List<BiddingRuleDTO> biddingRuleEntityList = new ArrayList<>();
//
//        long time = System.currentTimeMillis();
//        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo(userName);
//
//        biddingRuleEntityList.addAll(mongoTemplate.find(Query.query(Criteria.where("ebl").is(true).and("gid").is(gid).and("aid").is(id).and("next").lte(time)), BiddingRuleDTO.class));
//
//        return biddingRuleEntityList;
//    }
//
//    @Override
//    public void updateToNextRunTime(List<BiddingRuleDTO> tasks) {
//
//        Map<Long, SystemUserEntity> userMap = new HashMap<>();
//
//        for (BiddingRuleDTO entity : tasks) {
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
//            mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(entity.getId())), Update.update("next", entity.getStart()), BiddingRuleDTO.class);
//        }
//    }
    @Override
    public void createBidding(BiddingRuleDTO biddingRuleDTO) {
        save(biddingRuleDTO);
    }

    @Override
    public BiddingRuleDTO save(BiddingRuleDTO dto) {
        StrategyDTO strategyDTO = dto.getStrategyDTO();
        BiddingRuleEntity biddingRule = ObjectUtils.convert(dto, getEntityClass());
        biddingRule.setStrategyEntity(ObjectUtils.convert(strategyDTO, StrategyEntity.class));
        getMongoTemplate().save(biddingRule);
        return dto;
    }

    @Override
    public BiddingRuleDTO getBiddingRuleByKeywordId(Long keywordId) {
        BiddingRuleEntity biddingRule = getMongoTemplate().findOne(Query.query(Criteria.where(KEYWORD_ID).is(keywordId)), getEntityClass());
        return convertToDTO(biddingRule);
    }

    @Override
    public void disableRule(Long accountId, Long keywordId) {
        getMongoTemplate().findAndModify(
                Query.query(Criteria.where(ACCOUNT_ID).is(accountId).and(KEYWORD_ID).is(keywordId)),
                Update.update("ebl", false).set("r", false),
                getEntityClass());
    }

    @Override
    public List<BiddingRuleDTO> getTaskByAccoundId(String userName, Long id, long time) {
        Query query = Query.query(Criteria.where("ebl").is(true)
                .and("r").is(false)
                .and("nxt").lte(time).not()
                .and("ct").ne(0)
                .and(ACCOUNT_ID).is(id));
        List<BiddingRuleEntity> list = BaseMongoTemplate.getUserMongo(userName).find(query, getEntityClass());
        return convertToDTOList(list);
    }

    @Override
    public void updateToNextRunTime(List<BiddingRuleDTO> tasks) {

    }


    @Override
    public void enableRule(Long accountId, Long keywordId) {
        getMongoTemplate().findAndModify(
                Query.query(Criteria.where(ACCOUNT_ID).is(accountId).and(KEYWORD_ID).is(keywordId)),
                Update.update("ebl", true),
                getEntityClass());
    }

    @Override
    public boolean isPause(Long accountId, Long keywordId) {
        return getMongoTemplate().findOne(Query.query(Criteria.where(ACCOUNT_ID).is(accountId).and(KEYWORD_ID).is(keywordId).and("ebl").is(false)), getEntityClass()) != null;
    }

    @Override
    public List<BiddingRuleDTO> findByKeywordIds(List<Long> ids) {
        return convertToDTOList(getMongoTemplate().find(Query.query(Criteria.where(KEYWORD_ID).in(ids)), getEntityClass()));
    }

    @Override
    public void removeByKeywordId(Long id) {
        getMongoTemplate().remove(Query.query(Criteria.where(KEYWORD_ID).is(id)), getEntityClass());
    }

    @Override
    public void removeByKeywordIds(List<Long> ids) {
        getMongoTemplate().remove(Query.query(Criteria.where(KEYWORD_ID).in(ids)), getEntityClass());
    }

    @Override
    public boolean existsByKeywordId(Long keywordId) {
        return getMongoTemplate().exists(Query.query(Criteria.where(KEYWORD_ID).is(keywordId)), getEntityClass());
    }

    @Override
    public boolean setEnable(Long[] ids, boolean ebl) {
        WriteResult writeResult = getMongoTemplate().updateMulti(Query.query(Criteria.where(KEYWORD_ID).in(ids)), Update.update("ebl", ebl), getEntityClass());
        return writeResult.getN() == ids.length;
    }

    @Override
    public List<BiddingRuleDTO> findByNames(String[] query, boolean fullMatch, PaginationParam param, Map<String, Object> queryParams) {
        Query mongoQuery = new Query();
        String prefix = "(";
        String suffix = ")";
        if (!fullMatch) {
            prefix = ".*(";
            suffix = ").*";
        }
        String reg = "";
        for (String name : query) {
            reg = reg + name + "|";
        }
        reg = reg.substring(0, reg.length() - 1);

        if (queryParams != null && !queryParams.isEmpty() && queryParams.size() > 0) {
            Criteria criteria = Criteria.where(NAME).regex(prefix + reg + suffix);
            for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
                if ("matchType".equals(entry.getKey())) {
                    Integer matchType = Integer.valueOf(entry.getValue().toString());
                    if (matchType == 1) {
                        criteria.and("mt").is(1);
                    } else if (matchType == 2) {
                        criteria.and("mt").is(2).and("pt").is(3);
                    } else if (matchType == 3) {
                        criteria.and("mt").is(2).and("pt").is(2);
                    } else if (matchType == 4) {
                        criteria.and("mt").is(2).and("pt").is(1);
                    } else if (matchType == 5) {
                        criteria.and("mt").is(3);
                    }
                }
                if ("price".equals(entry.getKey())) {
                    BigDecimal[] prices = (BigDecimal[]) entry.getValue();

                    criteria.and("stgy.min").gte(prices[0]).and("stgy.max").lte(prices[1]);
                }
            }
            mongoQuery.addCriteria(criteria);
        } else {
            mongoQuery.addCriteria(Criteria.where(NAME).regex(prefix + reg + suffix));
        }

        return convertToDTOList(getMongoTemplate().find(PageParamUtils.withParam(param, mongoQuery), getEntityClass()));
    }

    @Override
    public BiddingRuleDTO takeOne(String userName, Long id, long time) {
        Query query = Query.query(Criteria.where("ebl").is(true)
                .and("r").is(false)
                .and("nxt").lte(time).not()
                .and("ct").ne(0)
                .and(ACCOUNT_ID).is(id)).limit(1);
        BiddingRuleEntity ruleEntity = BaseMongoTemplate.getUserMongo(userName).findAndModify(query, Update.update("r", true),
                FindAndModifyOptions.options().returnNew(true), getEntityClass());

        return convertToDTO(ruleEntity);
    }

    @Override
    public void hotRecovery(String username, Long bdAccountId) {
        BaseMongoTemplate.getUserMongo(username).updateMulti(
                Query.query(Criteria.where(ACCOUNT_ID).is(bdAccountId).and("ebl").is(true)),
                Update.update("r", false),
                getEntityClass());
    }

    /**
     * 由中央调度器获取可以执行的竞价任务,
     * 但不更改状态("r").
     * <p>
     *
     * @param username
     * @param time
     * @return
     */
    @Override
    public List<BiddingRuleDTO> getAvailableRules(String username, long time) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo(username);
        Criteria c = Criteria.where("ebl").is(true)
                .and("r").is(false)
                .and("nxt").not().lte(time)
                .and("ct").ne(0);
        Aggregation aggregation = Aggregation.newAggregation(match(c))
                .withOptions(new AggregationOptions.Builder().allowDiskUse(true).build());
        AggregationResults<BiddingRuleDTO> aggregationResults = mongoTemplate
                .aggregate(aggregation, TBL_BIDDINGRULE, getDTOClass());

//        aggregationResults.getMappedResults().stream().map(BiddingRuleIdVO::getId).forEach(results::add);
        return aggregationResults.getMappedResults();
    }

    /**
     * 竞价器根据objectId来获取相应的竞价规则,
     * 并标示为运行状态.
     * <p>
     *
     * @param username
     * @param objectId
     * @return
     */
    @Override
    public BiddingRuleDTO takeOneById(String username, String objectId) {
//        BiddingRuleEntity ruleEntity = BaseMongoTemplate.getUserMongo(username)
//                .findAndModify(
//                        Query.query(Criteria.where(SYSTEM_ID).is(objectId)),
//                        Update.update("r", true),
//                        FindAndModifyOptions.options().returnNew(true),
//                        getEntityClass());

        BiddingRuleEntity ruleEntity = BaseMongoTemplate.getUserMongo(username)
                .findOne(Query.query(Criteria.where(SYSTEM_ID).is(objectId)), getEntityClass(), TBL_BIDDINGRULE);

        return convertToDTO(ruleEntity);
    }

    private BiddingRuleDTO convertToDTO(BiddingRuleEntity biddingRuleEntity) {
        if (biddingRuleEntity == null) {
            return null;
        }
        StrategyEntity strategyEntity = biddingRuleEntity.getStrategyEntity();
        BiddingRuleDTO biddingRuleDTO = ObjectUtils.convert(biddingRuleEntity, getDTOClass());
        biddingRuleDTO.setStrategyDTO(ObjectUtils.convert(strategyEntity, StrategyDTO.class));
        return biddingRuleDTO;
    }

    private List<BiddingRuleDTO> convertToDTOList(List<BiddingRuleEntity> list) {
        List<BiddingRuleDTO> dtoList = new ArrayList<>(list.size());
        list.parallelStream().forEach(e -> {
            BiddingRuleDTO biddingRuleDTO = convertToDTO(e);
            if (biddingRuleDTO != null)
                dtoList.add(biddingRuleDTO);
        });
        return dtoList;
    }

//    class BiddingRuleIdVO {
//
//        private String id;
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//    }
}
