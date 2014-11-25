package com.perfect.db.mongodb.impl;

import com.perfect.commons.constants.LogStatusConstant;
import com.perfect.dao.LogDAO;
import com.perfect.entity.LogEntity;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dao.utils.Pager;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.perfect.commons.constants.MongoEntityConstants.*;

/**
 * Created by vbzer_000 on 2014/9/2.
 */
@Component
public class LogDAOImpl extends AbstractUserBaseDAOImpl<LogEntity, String> implements LogDAO {
    @Override
    public Class<LogEntity> getEntityClass() {
        return LogEntity.class;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public Iterable<LogEntity> findAll(Long accountId) {
        return getMongoTemplate().find(Query.query(Criteria.where(ACCOUNT_ID).is(accountId)), getEntityClass());
    }

    @Override
    public void deleteByBids(List<Long> ids) {
        getMongoTemplate().remove(Query.query(Criteria.where(BAIDU_ID).in(ids)), getEntityClass());
    }

    @Override
    public boolean existsByOid(String oid) {
        return getMongoTemplate().exists(Query.query(Criteria.where(OBJ_ID).is(oid)), getEntityClass());
    }

    @Override
    public boolean existsByBid(Long bid) {
        return getMongoTemplate().exists(Query.query(Criteria.where(BAIDU_ID).is(bid)), getEntityClass());
    }

    @Override
    public void insertLog(String oid, String entity) {
        if (existsByOid(oid)) {
            return;
        }
        LogEntity logEntity = new LogEntity();
        logEntity.setOid(oid);
        logEntity.setType(entity.toLowerCase().replaceAll("entity", ""));

        getMongoTemplate().insert(logEntity);
    }

    @Override
    public void insertLog(Long bid, String entity, int opt) {
        if (existsByBid(bid)) {
            LogEntity logEntity = getMongoTemplate().findOne(Query.query(Criteria.where(BAIDU_ID).is(bid)), getEntityClass());

            if (logEntity.getOpt() == LogStatusConstant.OPT_DELETE)
                return;
        }
        LogEntity logEntity = new LogEntity();
        logEntity.setBid(bid);
        logEntity.setType(entity.toLowerCase().replaceAll("entity", ""));
        logEntity.setOpt(opt);

        getMongoTemplate().insert(logEntity);
    }
}
