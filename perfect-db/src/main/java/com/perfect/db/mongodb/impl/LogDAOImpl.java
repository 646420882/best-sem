package com.perfect.db.mongodb.impl;

import com.perfect.commons.constants.LogStatusConstant;
import com.perfect.dao.sys.LogDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dto.LogDTO;
import com.perfect.entity.sys.LogEntity;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by vbzer_000 on 2014/9/2.
 */
@Component
public class LogDAOImpl extends AbstractUserBaseDAOImpl<LogDTO, String> implements LogDAO {
    @Override
    public Class<LogEntity> getEntityClass() {
        return LogEntity.class;
    }

    @Override
    public Iterable<LogDTO> findAll(Long accountId) {
        return convert(getMongoTemplate().find(Query.query(Criteria.where(ACCOUNT_ID).is(accountId)), getEntityClass()));
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
        LogDTO logEntity = new LogDTO();
        logEntity.setOid(oid);
        logEntity.setType(entity.toLowerCase().replaceAll("entity", ""));

        getMongoTemplate().insert(logEntity);
    }

    @Override
    public void insertLog(Long bid, String entity, int opt) {
        if (existsByBid(bid)) {
            boolean exists = getMongoTemplate().exists(Query.query(Criteria.where(BAIDU_ID).is(bid).and("opt").is(LogStatusConstant.OPT_DELETE)), getEntityClass());

            if (exists)
                return;
        }
        LogEntity logEntity = new LogEntity();
        logEntity.setBid(bid);
        logEntity.setType(entity.toLowerCase().replaceAll("entity", ""));
        logEntity.setOpt(opt);

        getMongoTemplate().insert(logEntity);
    }

    @Override
    public Class<LogDTO> getDTOClass() {
        return LogDTO.class;
    }
}
