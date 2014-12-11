package com.perfect.db.mongodb.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.perfect.dao.keyword.KeywordGroupDAO;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dto.keyword.LexiconDTO;
import com.perfect.entity.keyword.LexiconEntity;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.mongodb.DBNameUtils;
import com.perfect.utils.paging.PagerInfo;
import com.perfect.utils.redis.JRedisUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by baizz on 2014-08-20.
 * 2014-12-2 refactor
 */
@Repository("keywordGroupDAO")
public class KeywordGroupDAOImpl extends AbstractSysBaseDAOImpl<LexiconDTO, String> implements KeywordGroupDAO {

    public static final String CATEGORY_KEY = "_keywords_category";

    private static final String SEP_KEYWORD = "|";

    private static final String SEP_NAME_COUNT = ":";

    @Override
    @SuppressWarnings("unchecked")
    public Class<LexiconEntity> getEntityClass() {
        return LexiconEntity.class;
    }

    @Override
    public Class<LexiconDTO> getDTOClass() {
        return LexiconDTO.class;
    }

    @Override
    public List<LexiconDTO> find(Map<String, Object> params, int skip, int limit) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        boolean status = !(skip == -1 && limit == -1);
        Query query = null;
        int s = params.size();
        if (s == 1) {
            //只选择的是行业
            if (status) {
                query = new BasicQuery("{}", "{cg : 1, gr : 1, kw : 1, _id : 0}");
            } else {
                query = new BasicQuery("{}", "{tr : 1, cg : 1, gr : 1, kw : 1, _id : 0}");
            }
        } else if (s == 2) {
            //选择的是行业和计划
            if (status) {
                query = new BasicQuery("{}", "{gr : 1, kw : 1, _id : 0}");
            } else {
                query = new BasicQuery("{}", "{tr : 1, cg : 1, gr : 1, kw : 1, _id : 0}");
            }
        }

        Criteria criteria = Criteria.where(SYSTEM_ID).ne(null);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            criteria.and(entry.getKey()).is(entry.getValue());
        }

        if (query == null) {
            query = new Query(criteria);
        } else {
            query.addCriteria(criteria);
        }

        if (status) {
            query.with(new PageRequest(skip, limit));
        }
        return ObjectUtils.convert(mongoTemplate.find(query, getEntityClass()), getDTOClass());
    }

    @Override
    public List<TradeVO> findTr() {
        Jedis jc = JRedisUtils.get();
        boolean jcKey = jc.exists(TRADE_KEY);
        List<TradeVO> list;
        if (!jcKey) {
            MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getSysDBName());
            Aggregation aggregation = Aggregation.newAggregation(
                    project("tr"),
                    group("tr"),
                    sort(Sort.Direction.ASC, "tr")
            ).withOptions(new AggregationOptions.Builder().allowDiskUse(true).build());
            AggregationResults<TradeVO> aggregationResults = mongoTemplate.aggregate(aggregation, SYS_KEYWORD, TradeVO.class);
            list = aggregationResults.getMappedResults();
            jc.set(TRADE_KEY, new Gson().toJson(list));
            jc.expire(TRADE_KEY, Integer.MAX_VALUE);
        } else {
            String data = jc.get(TRADE_KEY);
            Gson gson = new Gson();
            list = gson.fromJson(data, new TypeToken<List<TradeVO>>() {
            }.getType());
        }

        return list;
    }

    @Override
    public int saveTrade(LexiconDTO lexiconDTO) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        Criteria c = new Criteria();
        c.and("tr").is(lexiconDTO.getTrade()).and("kw").is(lexiconDTO.getKeyword());
        LexiconEntity lexiconEntity = mongoTemplate.findOne(new Query(c), getEntityClass(), SYS_KEYWORD);
        if (lexiconEntity == null) {
            lexiconEntity = ObjectUtils.convert(lexiconDTO, getEntityClass());
            mongoTemplate.insert(lexiconEntity, SYS_KEYWORD);
            return 1;
        } else {
            return 3;
        }
    }

    @Override
    public List<CategoryVO> findCategories(String trade) {

        // redis 缓存数据

        Jedis jedis = JRedisUtils.get();
        try {
            if (jedis.exists(CATEGORY_KEY)) {
                List<CategoryVO> returnList = new ArrayList<>();
                String value = jedis.get(CATEGORY_KEY);
                String[] categoryPairs = value.split(SEP_KEYWORD);
                for (String pairs : categoryPairs) {
                    String[] nameCount = pairs.split(SEP_NAME_COUNT);


                    CategoryVO categoryVO = new CategoryVO();
                    categoryVO.setCategory(nameCount[0]);
                    categoryVO.setCount(Integer.parseInt(nameCount[1]));

                    returnList.add(categoryVO);
                }
                return returnList;
            } else {
                MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
                Aggregation aggregation = Aggregation.newAggregation(
                        match(Criteria.where("tr").is(trade)),
                        project("cg"),
                        group("cg").count().as("count"),
                        sort(Sort.Direction.ASC, "cg")
                ).withOptions(new AggregationOptions.Builder().allowDiskUse(true).build());
                AggregationResults<CategoryVO> aggregationResults = mongoTemplate.aggregate(aggregation, SYS_KEYWORD, CategoryVO.class);

                List<CategoryVO> returnList = aggregationResults.getMappedResults();

                List<String> pairs = new ArrayList<>();
                for (CategoryVO categoryVO : returnList) {

                    String pair = categoryVO.getCategory() + SEP_NAME_COUNT + categoryVO.getCount();
                    pairs.add(pair);
                }
                String value = String.join(SEP_KEYWORD, pairs.toArray(new String[]{}));
                jedis.setex(CATEGORY_KEY, (int) TimeUnit.DAYS.toSeconds(1) * 7, value);
                return returnList;
            }
        } catch (Exception e) {

        } finally {
            JRedisUtils.returnJedis(jedis);
        }

        return Collections.emptyList();
    }

    @Override
    public int getCurrentRowsSize(Map<String, Object> params) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();

        Criteria criteria = Criteria.where(SYSTEM_ID).ne(null);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            criteria.and(entry.getKey()).is(entry.getValue());
        }

        Aggregation aggregation = Aggregation.newAggregation(
                match(criteria),
                project("kw")
        );
        AggregationResults<Object> results = mongoTemplate.aggregate(aggregation, SYS_KEYWORD, Object.class);
        return results.getMappedResults().size();
    }

    @Override
    public PagerInfo findByPager(Map<String, Object> params, int page, int limit) {
        Query q = new Query();
        Criteria c = new Criteria();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> cri : params.entrySet()) {
                c.and(cri.getKey()).is(cri.getValue());
            }
            q.addCriteria(c);
        }
        Integer totalCount = getTotalCount(q, getEntityClass());
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        PagerInfo p = new PagerInfo(page, limit, totalCount);
        q.skip(p.getFirstStation());
        q.limit(p.getPageSize());
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        List<LexiconEntity> creativeEntityList = mongoTemplate.find(q, getEntityClass());
        p.setList(ObjectUtils.convert(creativeEntityList, getDTOClass()));
        return p;
    }

    @Override
    public void deleteByParams(String trade, String keyword) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        Query q = new Query();
        q.addCriteria(Criteria.where("tr").is(trade).and("kw").is(keyword));
        mongoTemplate.remove(q, getEntityClass());
    }

    @Override
    public void updateByParams(Map<String, Object> mapParams) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        Update up = new Update();
        up.set("tr", mapParams.get("tr"));
        up.set("cg", mapParams.get("cg"));
        up.set("gr", mapParams.get("gr"));
        up.set("kw", mapParams.get("kw"));
        up.set("url", mapParams.get("url"));
        mongoTemplate.updateFirst(new Query(Criteria.where("id").is(mapParams.get("id"))), up, LexiconEntity.class);
    }

    private int getTotalCount(Query q, Class<?> clazz) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        return (int) mongoTemplate.count(q, clazz);
    }

    //行业词库下的类别VO实体
    class CategoryVO {
        @Id
        private String category;

        private int count;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

    }

    class TradeVO {
        @Id
        private String trade;

        public String getTrade() {
            return trade;
        }

        public void setTrade(String trade) {
            this.trade = trade;
        }

    }
}
