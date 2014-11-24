package com.perfect.dao.mongodb.impl;

import com.perfect.dao.CrawlWordDAO;
import com.perfect.dao.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dao.mongodb.utils.Pager;
import com.perfect.entity.CrawlWordEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;

/**
 * Created by baizz on 2014-11-18.
 * 2014-11-24 refactor
 */
@Repository("crawlWordDAO")
public class CrawlWordDAOImpl extends AbstractSysBaseDAOImpl<CrawlWordEntity, String> implements CrawlWordDAO {

    @Override
    public List<CrawlWordEntity> findBySite(String... sites) {
        Criteria criteria = Criteria.where("s").is(0);
        for (String site : sites) {
            criteria.and("site").is(site);
        }
        AggregationResults<CrawlWordEntity> results = getSysMongoTemplate().aggregate(Aggregation.newAggregation(
                match(criteria)
        ), "sys_crawl_word", getEntityClass());
        return results.getMappedResults();
    }


    @Override
    public List<CrawlWordEntity> find(Map<String, Object> params, int skip, int limit, String order, Sort.Direction direction) {
        return getSysMongoTemplate().find(
                Query.query(Criteria.where("s").is(0)).with(new PageRequest(skip, limit)),
                getEntityClass());
    }

    @Override
    public Class<CrawlWordEntity> getEntityClass() {
        return CrawlWordEntity.class;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

}