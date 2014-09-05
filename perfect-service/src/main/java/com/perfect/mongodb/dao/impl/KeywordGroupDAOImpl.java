package com.perfect.mongodb.dao.impl;

import com.google.common.collect.Lists;
import com.perfect.dao.KeywordGroupDAO;
import com.perfect.entity.LexiconEntity;
import com.perfect.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.mongodb.utils.Pager;
import com.perfect.utils.DBNameUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.perfect.mongodb.utils.EntityConstants.SYS_KEYWORD;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by baizz on 2014-08-20.
 */
@Repository("keywordGroupDAO")
public class KeywordGroupDAOImpl extends AbstractSysBaseDAOImpl<LexiconEntity, Long> implements KeywordGroupDAO {
    @Override
    public Class<LexiconEntity> getEntityClass() {
        return LexiconEntity.class;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public List<LexiconEntity> find(Map<String, Object> params, int skip, int limit) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getSysDBName());
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

        Criteria criteria = Criteria.where("_id").ne(null);
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
        return mongoTemplate.find(query, getEntityClass());
    }

    public List<CategoryVO> findCategories(String trade) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getSysDBName());
        Aggregation aggregation = Aggregation.newAggregation(
                project("tr", "cg"),
                match(Criteria.where("tr").is(trade)),
                sort(Sort.Direction.ASC, "cg"),
                group("cg").count().as("count")
        );
        AggregationResults<CategoryVO> aggregationResults = mongoTemplate.aggregate(aggregation, "sys_keyword", CategoryVO.class);

        /*
        GroupByResults<CategoryVO> results = mongoTemplate
                .group(Criteria.where("tr").is(trade), "sys_keyword",
                        GroupBy.key("cg").initialDocument("{count : 1}")
                                .reduceFunction("function(key, values) {values.count += 1;}"),
                        CategoryVO.class);
        */
        List<CategoryVO> list = Lists.newArrayList(aggregationResults.iterator());
        return list;
    }

    public int getCurrentRowsSize(Map<String, Object> params) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getSysDBName());

        Criteria criteria = Criteria.where("_id").ne(null);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            criteria.and(entry.getKey()).is(entry.getValue());
        }

//        Query query = new Query(criteria);
//        List<LexiconEntity> list = mongoTemplate.find(query, getEntityClass());

        Aggregation aggregation = Aggregation.newAggregation(
                match(criteria),
                group("kw")
        );
        AggregationResults<Object> results = mongoTemplate.aggregate(aggregation, SYS_KEYWORD, Object.class);
        return results.getMappedResults().size();
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

        @Override
        public String toString() {
            return "CategoryVO{" +
                    "category='" + category + '\'' +
                    ", count=" + count +
                    '}';
        }
    }
}
