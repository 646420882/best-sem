package com.perfect.mongodb.base;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by yousheng on 2014/8/19.
 *
 * @author yousheng
 */
public abstract class AbstractSysBaseDAOImpl<T, ID extends Serializable> extends AbstractUserBaseDAOImpl<T, ID> {

    @Override
    public void delete(T t) {
        getSysMongoTemplate().remove(t);
    }

    @Override
    public void delete(Iterable<? extends T> entities) {
        for (T t : entities) {
            getSysMongoTemplate().remove(t);
        }
    }

    @Override
    public void delete(ID id) {
        getSysMongoTemplate().remove(Query.query(Criteria.where("id").is(id)), getEntityClass());
    }

    @Override
    public void deleteAll() {
        getSysMongoTemplate().dropCollection(getEntityClass());
    }

    @Override
    public <S extends T> Iterable<S> save(Iterable<S> entities) {
        for (S s : entities) {
            getSysMongoTemplate().save(s);
        }
        return entities;
    }

    @Override
    public <S extends T> S save(S entity) {
        getSysMongoTemplate().save(entity);
        return entity;
    }

    @Override
    public void insert(T t) {
        getSysMongoTemplate().insert(t);
    }

    @Override
    public void insertAll(List<T> entities) {
        getSysMongoTemplate().insertAll(entities);
    }

    @Override
    public List<T> find(Map<String, Object> params, int skip, int limit, String order, Sort.Direction direction) {
        Query query = new Query();
        Criteria criteria = null;
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (criteria == null) {
                criteria = new Criteria(param.getKey());
                criteria.is(param.getValue());
                continue;
            }

            criteria.and(param.getKey()).is(param.getValue());
        }

        query.addCriteria(criteria).skip(skip).limit(limit);

        if (order != null) {
            query.with(new Sort(direction, order));
        }
        return getSysMongoTemplate().find(query, getEntityClass());
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

    @Override
    public void update(T t) {
        getSysMongoTemplate().save(t);
    }
}
