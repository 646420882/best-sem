package com.perfect.mongodb.dao.impl;

import com.perfect.dao.CreativeDAO;
import com.perfect.dao.LogProcessingDAO;
import com.perfect.entity.CreativeEntity;
import com.perfect.entity.DataAttributeInfoEntity;
import com.perfect.entity.DataOperationLogEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-7-10.
 */
@Repository("creativeDAO")
public class CreativeDAOImpl implements CreativeDAO {

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private LogProcessingDAO logProcessingDAO;

    public List<Long> getCreativeIdByAdgroupId(Long adgroupId) {
        Query query = new BasicQuery("{}", "{creativeId : 1}");
        query.addCriteria(Criteria.where("adgroupId").is(adgroupId));
        List<CreativeEntity> types = mongoTemplate.find(query, CreativeEntity.class, "CreativeType");
        List<Long> creativeIds = new ArrayList<>(types.size());
        for (CreativeEntity type : types)
            creativeIds.add(type.getCreativeId());
        return creativeIds;
    }

    public List<CreativeEntity> getCreativeByAdgroupId(Long adgroupId, Map<String, Object> params, int skip, int limit) {
        Query query = new Query();
        Criteria criteria = Criteria.where("adgroupId").is(adgroupId);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet())
                criteria.and(entry.getKey()).is(entry.getValue());
        }
        query.addCriteria(criteria);
        query.with(new PageRequest(skip, limit));
        List<CreativeEntity> list = mongoTemplate.find(query, CreativeEntity.class, "CreativeType");
        return list;
    }

    public CreativeEntity findOne(Long creativeId) {
        CreativeEntity entity = mongoTemplate.findOne(
                new Query(Criteria.where("creativeId").is(creativeId)), CreativeEntity.class, "CreativeType");
        return entity;
    }

    public List<CreativeEntity> findAll() {
        List<CreativeEntity> entityList = mongoTemplate.findAll(CreativeEntity.class, "CreativeType");
        return entityList;
    }

    public List<CreativeEntity> find(Map<String, Object> params, int skip, int limit) {
        Query query = new Query();
        if (params != null && params.size() > 0) {
            Criteria criteria = Criteria.where("creativeId").ne(null);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                criteria.and(entry.getKey()).is(entry.getValue());
            }
            query.addCriteria(criteria);
        }
        query.with(new PageRequest(skip, limit));
        List<CreativeEntity> list = mongoTemplate.find(query, CreativeEntity.class, "CreativeType");
        return list;
    }

    public void insert(CreativeEntity creativeEntity) {
        mongoTemplate.insert(creativeEntity, "CreativeType");
        DataOperationLogEntity logEntity = logProcessingDAO.getLog(creativeEntity.getCreativeId(), CreativeEntity.class, null, creativeEntity);
        logProcessingDAO.insert(logEntity);
    }

    public void insertAll(List<CreativeEntity> entities) {
        mongoTemplate.insertAll(entities);
        List<DataOperationLogEntity> logEntities = new LinkedList<>();
        for (CreativeEntity entity : entities) {
            DataOperationLogEntity log = logProcessingDAO.getLog(entity.getCreativeId(), CreativeEntity.class, null, entity);
            logEntities.add(log);
        }
        logProcessingDAO.insertAll(logEntities);
    }

    @SuppressWarnings("unchecked")
    public void update(CreativeEntity creativeEntity) {
        Long id = creativeEntity.getCreativeId();
        Query query = new Query();
        query.addCriteria(Criteria.where("creativeId").is(id));
        Update update = new Update();
        DataOperationLogEntity log = null;
        try {
            Class _class = creativeEntity.getClass();
            Field[] fields = _class.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                if ("creativeId".equals(fieldName))
                    continue;
                StringBuilder fieldGetterName = new StringBuilder("get");
                fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
                Method method = _class.getDeclaredMethod(fieldGetterName.toString());
                Object after = method.invoke(creativeEntity);
                if (after != null) {
                    update.set(field.getName(), after);
                    Object before = method.invoke(findOne(id));
                    log = logProcessingDAO.getLog(id, CreativeEntity.class, new DataAttributeInfoEntity(field.getName(), before, after), null);
                    break;
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        mongoTemplate.updateFirst(query, update, CreativeEntity.class, "CreativeType");
        logProcessingDAO.insert(log);
    }

    public void update(List<CreativeEntity> entities) {
        for (CreativeEntity entity : entities)
            update(entity);
    }

    public void deleteById(Long creativeId) {
        mongoTemplate.remove(new Query(Criteria.where("creativeId").is(creativeId)), CreativeEntity.class, "CreativeType");
        DataOperationLogEntity log = logProcessingDAO.getLog(creativeId, CreativeEntity.class, null, null);
        logProcessingDAO.insert(log);
    }

    public void deleteByIds(List<Long> creativeIds) {
        List<DataOperationLogEntity> list = new LinkedList<>();
        for (Long id : creativeIds) {
            mongoTemplate.remove(new Query(Criteria.where("creativeId").is(id)), CreativeEntity.class, "CreativeType");
            DataOperationLogEntity log = logProcessingDAO.getLog(id, CreativeEntity.class, null, null);
            list.add(log);
        }
        logProcessingDAO.insertAll(list);
    }

    public void delete(CreativeEntity creativeEntity) {
        deleteById(creativeEntity.getCreativeId());
    }

    public void deleteAll() {
        List<CreativeEntity> creativeEntityList = findAll();
        mongoTemplate.dropCollection(CreativeEntity.class);
        List<DataOperationLogEntity> logEntities = new LinkedList<>();
        for (CreativeEntity entity : creativeEntityList) {
            DataOperationLogEntity log = logProcessingDAO.getLog(entity.getCreativeId(), CreativeEntity.class, null, null);
            logEntities.add(log);
        }
        logProcessingDAO.insertAll(logEntities);
    }
}
