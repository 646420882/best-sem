package com.perfect.db.mongodb.base;

import com.perfect.dto.BaseDTO;
import com.perfect.utils.ObjectUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

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

}
