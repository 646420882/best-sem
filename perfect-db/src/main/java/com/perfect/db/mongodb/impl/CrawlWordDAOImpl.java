package com.perfect.db.mongodb.impl;

import com.google.common.collect.Lists;
import com.perfect.dao.sys.CrawlWordDAO;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dto.CrawlWordDTO;
import com.perfect.entity.sys.CrawlWordEntity;
import com.perfect.utils.ObjectUtils;
import org.springframework.data.domain.PageRequest;
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
 * 2014-12-4 refactor
 */
@Repository("crawlWordDAO")
public class CrawlWordDAOImpl extends AbstractSysBaseDAOImpl<CrawlWordDTO, String> implements CrawlWordDAO {

    @Override
    @SuppressWarnings("unchecked")
    public Class<CrawlWordEntity> getEntityClass() {
        return CrawlWordEntity.class;
    }

    @Override
    public Class<CrawlWordDTO> getDTOClass() {
        return CrawlWordDTO.class;
    }

    @Override
    public List<CrawlWordDTO> findBySite(String... sites) {
        Criteria criteria = Criteria.where("s").is(0);
        for (String site : sites) {
            criteria.and("site").is(site);
        }
        AggregationResults<CrawlWordEntity> results = getSysMongoTemplate().aggregate(
                Aggregation.newAggregation(
                        match(criteria)
                ), "sys_crawl_word", getEntityClass());
        return ObjectUtils.convert(results.getMappedResults(), getDTOClass());
    }

    @Override
    public List<CrawlWordDTO> find(Map<String, Object> params, int skip, int limit) {
        return ObjectUtils.convert(getSysMongoTemplate().find(
                Query.query(Criteria.where("s").is(0)).with(new PageRequest(skip, limit)),
                getEntityClass()), getDTOClass());
    }

    @Override
    public Iterable<CrawlWordDTO> save(Iterable<CrawlWordDTO> crawlWordDTOs) {
        List<CrawlWordEntity> entityList = ObjectUtils.convert(Lists.newArrayList(crawlWordDTOs), getEntityClass());
        getSysMongoTemplate().insertAll(entityList);
        return crawlWordDTOs;
    }
}