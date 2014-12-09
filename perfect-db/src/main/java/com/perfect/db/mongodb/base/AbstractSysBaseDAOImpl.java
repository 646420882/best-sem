package com.perfect.db.mongodb.base;

import com.perfect.dto.BaseDTO;
import com.perfect.utils.ObjectUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.Serializable;

/**
 * Created by yousheng on 2014/8/19.
 *
 * @author yousheng
 */
public abstract class AbstractSysBaseDAOImpl<T extends BaseDTO, ID extends Serializable> extends AbstractUserBaseDAOImpl<T, ID> {

    @Override
    public boolean delete(T t) {
        getSysMongoTemplate().remove(t);
        return false;
    }

    @Override
    public int delete(Iterable<? extends T> entities) {

        int count = 0;
        for (T t : entities) {
            count += getSysMongoTemplate().remove(t).getN();
        }
        return count;
    }

    @Override
    public boolean delete(ID id) {
        return getSysMongoTemplate().remove(Query.query(Criteria.where("id").is(id)), getEntityClass()).getN() > 0;
    }

    @Override
    public boolean deleteAll() {
        getSysMongoTemplate().dropCollection(getEntityClass());
        return false;
    }


    @Override
    public T save(T dto) {
        Object entity = ObjectUtils.convert(dto, getEntityClass());
        getSysMongoTemplate().save(entity);
        return dto;
    }


    @Override
    public T findOne(ID id) {
        return getSysMongoTemplate().findOne(Query.query(Criteria.where("id").is(id)), getEntityClass());
    }


    @Override
    public Iterable<T> findAll() {
        return getSysMongoTemplate().findAll(getEntityClass());
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {
        return getSysMongoTemplate().find(Query.query(Criteria.where(getId()).in(ids)), getEntityClass());
    }

    @Override
    public boolean exists(ID id) {
        return getSysMongoTemplate().exists(Query.query(Criteria.where(getId()).is(id)), getEntityClass());
    }

    @Override
    public long count() {
        return getSysMongoTemplate().count(null, getEntityClass());
    }

}
