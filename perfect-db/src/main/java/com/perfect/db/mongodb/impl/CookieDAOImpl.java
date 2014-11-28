package com.perfect.db.mongodb.impl;

import com.perfect.dao.sys.CookieDAO;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dto.CookieDTO;
import com.perfect.entity.sys.CookieEntity;
import com.perfect.ObjectUtils;
import com.perfect.paging.Pager;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-11-10.
 * 2014-11-26 refactor
 */
@Repository("cookieDAO")
public class CookieDAOImpl extends AbstractSysBaseDAOImpl<CookieDTO, String> implements CookieDAO {

    @Override
    public Class<CookieDTO> getEntityClass() {
        return CookieDTO.class;
    }

    private Class<CookieEntity> getCookieEntityClass() {
        return CookieEntity.class;
    }

    @Override
    public CookieDTO takeOne() {
        CookieDTO cookieDTO = new CookieDTO();
        CookieEntity cookieEntity = getSysMongoTemplate().findAndModify(
                Query.query(
                        Criteria.where("i").is(true)
                                .and("f").lte(System.currentTimeMillis()))
                        .limit(1).with(new Sort(Sort.Direction.ASC, "f")),
                Update.update("i", false),
                FindAndModifyOptions.options().returnNew(true),
                getCookieEntityClass());
        if (cookieEntity == null) {
            return null;
        }
        BeanUtils.copyProperties(cookieEntity, cookieDTO);
        return cookieDTO;
    }

    @Override
    public void returnOne(CookieDTO cookieDTO) {
        cookieDTO.setIdle(true);
        CookieEntity cookieEntity = new CookieEntity();
        BeanUtils.copyProperties(cookieDTO, cookieEntity);
        getMongoTemplate().save(cookieEntity);
    }

    @Override
    /**
     * 查询最后执行时间在5分钟之前的账号
     */
    public List<CookieDTO> allUnused() {
        return ObjectUtils.convert(getSysMongoTemplate()
                .find(Query.query(Criteria.where("f").lte(System.currentTimeMillis() - 5 * 60 * 1000))
                        .with(new Sort(Sort.Direction.ASC, "f")), getCookieEntityClass()), getEntityClass());
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }
}
