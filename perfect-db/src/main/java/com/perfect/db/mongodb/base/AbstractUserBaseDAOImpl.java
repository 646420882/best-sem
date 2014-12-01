package com.perfect.db.mongodb.base;

import com.mongodb.WriteResult;
import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.BaseDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.perfect.commons.constants.MongoEntityConstants.SYSTEM_ID;

/**
 * Created by vbzer_000 on 2014/6/18.
 */
public abstract class AbstractUserBaseDAOImpl<T extends BaseDTO, ID extends Serializable> implements HeyCrudRepository<T, ID> {


    protected List<T> convertToDTOs(List entities) {
        List<T> list = new ArrayList<>();
        try {

            for (Object entity : entities) {
                T dto = getDTOClass().newInstance();
                BeanUtils.copyProperties(entity, dto);

                list.add(dto);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    protected T convertToDTO(Object entity) {
        try {
            T dto = getDTOClass().newInstance();

            BeanUtils.copyProperties(entity, dto);

            return dto;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
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

        try {
            Object entity = getEntityClass().newInstance();
            BeanUtils.copyProperties(dto, entity);
            getMongoTemplate().save(entity);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return dto;
    }


    @Override
    public Iterable<T> save(Iterable<T> entities) {

        for (T t : entities) {
            save(t);
        }

        return entities;
    }

    @Override
    public void delete(T t) {
        getMongoTemplate().remove(t.getId());
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
    public void deleteAll() {
        getMongoTemplate().dropCollection(getEntityClass());
    }

    @Override
    public T findOne(ID id) {
        Object entity = getMongoTemplate().findById(id, getEntityClass());

        try {
            T dto = getDTOClass().newInstance();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }


    //    public List<T> find(Map<String, Object> params, int skip, int limit, String sort, Sort.Direction direction) {
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
//        query.addCriteria(criteria).skip(skip).limit(limit);
//        if (sort != null) {
//            query.with(new Sort(Sort.Direction.ASC, sort));
//        }
//
//        E entity = getMongoTemplate().find(query, getEntityClass());
//
//        return null;
//    }

//    @Override
//    public T findOne(ID id) {
//        return getMongoTemplate().findOne(Query.query(Criteria.where(getId()).is(id)), getEntityClass());
//    }
//
//
//    @Override
//    public Iterable<T> findAll() {
//        return getMongoTemplate().findAll(getEntityClass());
//    }
//
//    @Override
//    public Iterable<T> findAll(Iterable<ID> ids) {
//        return getMongoTemplate().find(Query.query(Criteria.where(getId()).in(ids)), getEntityClass());
//    }

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
//
//    public List<T> find(Map<String, Object> params, int skip, int limit) {
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
//        query.addCriteria(criteria).skip(skip).limit(limit);
//
//        return getMongoTemplate().find(query, getEntityClass());
//    }

    String getMatchReg(String query) {
        return ".*(" + query.replaceAll(" ", "|") + ").*";
    }

    @Override
    public Iterable<T> findAll() {
        return convertToDTOs(getMongoTemplate().findAll(getEntityClass()));
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {

        return convertToDTOs(getMongoTemplate().find(Query.query(Criteria.where(getId()).in(ids)), getEntityClass()));
    }
}
