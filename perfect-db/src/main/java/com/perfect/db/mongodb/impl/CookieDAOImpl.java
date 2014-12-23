package com.perfect.db.mongodb.impl;

import com.perfect.dao.sys.CookieDAO;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dto.CookieDTO;
import com.perfect.entity.sys.CookieEntity;
import com.perfect.utils.ObjectUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-11-10.
 * 2014-12-23 refactor
 */
@Repository("cookieDAO")
public class CookieDAOImpl extends AbstractSysBaseDAOImpl<CookieDTO, String> implements CookieDAO {

    private static final int TIME_INT = 5_000;

    @Override
    @SuppressWarnings("unchecked")
    public Class<CookieEntity> getEntityClass() {
        return CookieEntity.class;
    }

    @Override
    public Class<CookieDTO> getDTOClass() {
        return CookieDTO.class;
    }

    @Override
    public CookieDTO takeOne() {
        CookieEntity cookieEntity = getSysMongoTemplate().findAndModify(
                Query.query(
                        Criteria.where("i").is(true)
                                .and("f").lte(System.currentTimeMillis()))
                        .limit(1).with(new Sort(Sort.Direction.ASC, "f")),
                Update.update("i", false),
                FindAndModifyOptions.options().returnNew(true),
                getEntityClass());

        return ObjectUtils.convert(cookieEntity, getDTOClass());
    }

    @Override
    public void returnOne(String objectId) {
        getSysMongoTemplate().updateFirst(
                Query.query(Criteria.where(SYSTEM_ID).is(objectId)),
                Update.update("i", true).set("f", System.currentTimeMillis() + TIME_INT),
                getEntityClass());
    }

    @Override
    public Iterable<CookieDTO> findAll() {
        return ObjectUtils.convert(getSysMongoTemplate().findAll(getEntityClass()), getDTOClass());
    }

    @Override
    public List<CookieDTO> find(Map<String, Object> params, int skip, int limit) {
        return Collections.emptyList();
    }
}
