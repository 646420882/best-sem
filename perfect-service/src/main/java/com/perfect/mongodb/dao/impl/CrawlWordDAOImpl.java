package com.perfect.mongodb.dao.impl;

import com.perfect.entity.CrawlWordEntity;
import com.perfect.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.mongodb.utils.Pager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-11-17.
 */
@Repository("crawlWordDAO")
public class CrawlWordDAOImpl extends AbstractSysBaseDAOImpl<CrawlWordEntity, String> {

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
