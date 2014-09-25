package com.perfect.mongodb.dao.impl;

import com.perfect.dao.FarmDAO;
import com.perfect.entity.UrlEntity;
import com.perfect.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.mongodb.utils.Pager;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by vbzer_000 on 2014/9/24.
 */
@Component("farmDAO")
public class FarmDAOImpl extends AbstractSysBaseDAOImpl<UrlEntity, String> implements FarmDAO {
    @Override
    public Class<UrlEntity> getEntityClass() {
        return UrlEntity.class;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public UrlEntity takeOne() {
        return getSysMongoTemplate().findAndModify(Query.query(Criteria.where("i").is(true).and("f").lte(System.currentTimeMillis()))
                        .limit(1).with
                                (new Sort
                                        (Sort.Direction.ASC, "f")), Update.update("i", false), FindAndModifyOptions.options().returnNew(true),
                getEntityClass());
    }

    @Override
    public void returnOne(UrlEntity urlEntity) {
        urlEntity.setIdle(true);
        getSysMongoTemplate().save(urlEntity);
    }

}
