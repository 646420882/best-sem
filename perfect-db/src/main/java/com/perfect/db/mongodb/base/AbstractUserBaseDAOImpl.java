package com.perfect.db.mongodb.base;

import com.mongodb.WriteResult;
import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.BaseDTO;
import com.perfect.utils.ObjectUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by vbzer_000 on 2014/6/18.
 */
public abstract class AbstractUserBaseDAOImpl<T extends BaseDTO, ID extends Serializable> implements HeyCrudRepository<T, ID> {

    protected <S> List<T> convert(List<S> entities) {
        Objects.requireNonNull(entities);
        List<T> list = new ArrayList<>(entities.size());
        for (S entity : entities) {
            T dto = ObjectUtils.convert(entity, getDTOClass());
            list.add(dto);
        }
        return list;
    }

    protected <S, D> List<D> convertByClass(List<S> entities, Class<D> clz) {
        Objects.requireNonNull(entities);
        List<D> list = new ArrayList<>(entities.size());
        for (S entity : entities) {
            D dto = ObjectUtils.convert(entity, clz);
            list.add(dto);
        }
        return list;
    }

    public String getId() {
        return SYSTEM_ID;
    }

    @Resource
    private MongoTemplate mongoSysTemplate;

    public MongoTemplate getSysMongoTemplate() {
        return mongoSysTemplate;
    }

    public MongoTemplate getMongoTemplate() {
        return BaseMongoTemplate.getUserMongo();
    }


    @Override
    public T save(T dto) {
        Object entity = ObjectUtils.convert(dto, getEntityClass());
        getMongoTemplate().save(entity);
        return dto;
    }


    @Override
    public Iterable<T> save(Iterable<T> ts) {
        for (T t : ts) {
            save(t);
        }

        return ts;
    }

    @Override
    public boolean delete(T t) {
        getMongoTemplate().remove(t.getId());
        return false;
    }

    @Override
    public int delete(Iterable<? extends T> entities) {
        List<String> ids = new ArrayList<>();
        for (T t : entities) {
            ids.add(t.getId());
        }

        WriteResult wr = getMongoTemplate().remove(Query.query(Criteria.where(getId()).in(ids)), getEntityClass());
        return wr.getN();
    }

    @Override
    public boolean delete(ID id) {
        return getMongoTemplate().remove(Query.query(Criteria.where(getId()).is(id)), getEntityClass()).getN() > 0;
    }


    @Override
    public int deleteByIds(List<ID> ids) {
        return getMongoTemplate().remove(Query.query(Criteria.where(getId()).in(ids)), getEntityClass()).getN();
    }

    @Override
    public boolean deleteAll() {
        getMongoTemplate().dropCollection(getEntityClass());
        return false;
    }

    @Override
    public T findOne(ID id) {
        Object entity = getMongoTemplate().findById(id, getEntityClass());
        return ObjectUtils.convert(entity, getDTOClass());
    }


    @Override
    public List<T> find(Map<String, Object> params, int skip, int limit, String sort, boolean asc) {
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

        if (criteria == null) {
            query.skip(skip).limit(limit);
        } else {
            query.addCriteria(criteria).skip(skip).limit(limit);
        }

        if (sort != null) {
            query.with(new Sort((asc) ? Sort.Direction.ASC : Sort.Direction.DESC, sort));
        }

        return convert(getMongoTemplate().find(query, getEntityClass()));
    }

    @Override
    public boolean exists(ID id) {
        return getMongoTemplate().exists(Query.query(Criteria.where(getId()).is(id)), getEntityClass());
    }

    @Override
    public long count() {
        return getMongoTemplate().count(null, getEntityClass());
    }

//    public List<T> find(Map<String, Object> params, String fieldName, String q, int skip, int limit, String sort, Sort.Direction direction) {
//
//        Query query = new Query();
//        Criteria criteria = null;
//        for (Map.Entry<String, Object> param : params.entrySet()) {
//            if (criteria == null) {
//                criteria = new Criteria(param.getKey());
//                criteria.is(param.getValue());
//                continue;
//            }
//
//            criteria.and(param.getKey()).is(param.getValue());
//        }
//
//        if (fieldName != null && q != null) {
//            criteria.and(fieldName).regex(".*(" + q.replaceAll(" ", "|") + ").*");
//        }
//
//        query.addCriteria(criteria).skip(skip).limit(limit);
//        if (sort != null) {
//            query.with(new Sort(Sort.Direction.ASC, sort));
//        }
//
//        return getMongoTemplate().find(query, getEntityClass());
//
//    }

    public List<T> find(Map<String, Object> params, int skip, int limit) {
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

        if (criteria == null) {
            query.skip(skip).limit(limit);
        } else {
            query.addCriteria(criteria).skip(skip).limit(limit);
        }

        return getMongoTemplate().find(query, getEntityClass());
    }

//    private String getMatchReg(String query) {
//        return ".*(" + query.replaceAll(" ", "|") + ").*";
//    }

    @Override
    public Iterable<T> findAll() {
        return convert(getMongoTemplate().findAll(getEntityClass()));
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {

        return convert(getMongoTemplate().find(Query.query(Criteria.where(getId()).in(ids)), getEntityClass()));
    }
}
