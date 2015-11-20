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

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.perfect.commons.constants.RedisConstants.CATEGORY_KEY;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created on 2014-08-20.
 * <p>关键词拓词.
 *
 * @author dolphineor
 * @update 2015-09-24
 */
@Repository("keywordGroupDAO")
public class KeywordGroupDAOImpl extends AbstractSysBaseDAOImpl<LexiconDTO, String> implements KeywordGroupDAO {

    private static final String SEP_KEYWORD_REG = "\\|";

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
    @SuppressWarnings("unchecked")
    public List<LexiconDTO> find(Map<String, Object> params, int skip, int limit) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        boolean status = !(skip == -1 && limit == -1);
        Query query;
        if (status) {
            query = new BasicQuery("{}", "{cg : 1, gr : 1, kw : 1, _id : 0}");
        } else {
            query = new BasicQuery("{}", "{tr : 1, cg : 1, gr : 1, kw : 1, _id : 0}");
        }

        Criteria criteria = Criteria.where(SYSTEM_ID).ne(null);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (Objects.equals(LEXICON_GROUP, entry.getKey())) {
                List<String> values = (List) entry.getValue();
                criteria.andOperator(Criteria.where(entry.getKey()).in(values));
                continue;
            }

            if (entry.getValue() instanceof List) {
                criteria.and(entry.getKey()).in((List) entry.getValue());
            } else {
                criteria.and(entry.getKey()).is(entry.getValue());
            }
        }

        query.addCriteria(criteria);

        if (status) {
            query.with(new PageRequest(skip, limit));
        }

        List<LexiconEntity> lexiconList = mongoTemplate.find(query, getEntityClass());

        return ObjectUtils.convert(lexiconList, getDTOClass());
    }

    @Override
    public List<TradeVO> findTr() {
        Jedis jc = JRedisUtils.get();
        boolean jcKey = jc.exists(TRADE_KEY);
        List<TradeVO> list;
        if (!jcKey) {
            MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getSysDBName());
            Aggregation aggregation = Aggregation.newAggregation(
                    project(LEXICON_TRADE),
                    group(LEXICON_TRADE),
                    sort(Sort.Direction.ASC, LEXICON_TRADE)
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
        c.and("tr").is(lexiconDTO.getTrade()).and(LEXICON_KEYWORD).is(lexiconDTO.getKeyword());
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

            String key = CATEGORY_KEY + ":" + trade;
            if (jedis.exists(key)) {
                List<CategoryVO> returnList = new ArrayList<>();
                String value = jedis.get(key);
                String[] categoryPairs = value.split(SEP_KEYWORD_REG);
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
                        match(Criteria.where(LEXICON_TRADE).is(trade)),
                        project(LEXICON_CATEGORY),
                        group(LEXICON_CATEGORY).count().as("count"),
                        sort(Sort.Direction.ASC, LEXICON_CATEGORY)
                ).withOptions(new AggregationOptions.Builder().allowDiskUse(true).build());
                AggregationResults<CategoryVO> aggregationResults = mongoTemplate.aggregate(aggregation, SYS_KEYWORD, CategoryVO.class);

                List<CategoryVO> returnList = aggregationResults.getMappedResults();

                List<String> pairs = new ArrayList<>();
                for (CategoryVO categoryVO : returnList) {
                    String pair = categoryVO.getCategory() + SEP_NAME_COUNT + categoryVO.getCount();
                    pairs.add(pair);
                }
                String value = String.join(SEP_KEYWORD, pairs.toArray(new String[pairs.size()]));
                jedis.setex(key, (int) TimeUnit.DAYS.toSeconds(1) * 7, value);
                return returnList;
            }
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            JRedisUtils.returnJedis(jedis);
        }

        return Collections.emptyList();
    }

    @Override
    public List<CategoryVO> findSecondDirectoryByCategories(List<String> categories) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        Aggregation aggregation = Aggregation.newAggregation(
                match(Criteria.where(LEXICON_CATEGORY).in(categories)),
                project(LEXICON_GROUP),
                group(LEXICON_GROUP).count().as("count"),
                sort(Sort.Direction.ASC, LEXICON_GROUP)
        ).withOptions(new AggregationOptions.Builder().allowDiskUse(true).build());
        AggregationResults<CategoryVO> aggregationResults = mongoTemplate.aggregate(aggregation, SYS_KEYWORD, CategoryVO.class);
        return aggregationResults.getMappedResults();
    }

    @Override
    public long getCurrentRowsSize(Map<String, Object> params) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();

        Criteria criteria = Criteria.where(SYSTEM_ID).ne(null);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() instanceof List) {
                criteria.and(entry.getKey()).in((List) entry.getValue());
            } else {
                criteria.and(entry.getKey()).is(entry.getValue());
            }
        }

        return mongoTemplate.count(Query.query(criteria), getEntityClass());
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
        Query query = Query.query(Criteria.where(LEXICON_TRADE).is(trade).and(LEXICON_KEYWORD).is(keyword));
        mongoTemplate.remove(query, getEntityClass());
    }

    @Override
    public void updateByParams(Map<String, Object> mapParams) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        Update up = new Update();
        up.set(LEXICON_TRADE, mapParams.get(LEXICON_TRADE));
        up.set(LEXICON_CATEGORY, mapParams.get(LEXICON_CATEGORY));
        up.set(LEXICON_GROUP, mapParams.get(LEXICON_GROUP));
        up.set(LEXICON_KEYWORD, mapParams.get(LEXICON_KEYWORD));
        up.set(LEXICON_URL, mapParams.get(LEXICON_URL));
        mongoTemplate.updateFirst(new Query(Criteria.where(SYSTEM_ID).is(mapParams.get(SYSTEM_ID))), up, getEntityClass());
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
