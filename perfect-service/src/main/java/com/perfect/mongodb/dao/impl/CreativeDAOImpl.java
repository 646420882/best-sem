package com.perfect.mongodb.dao.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.CreativeDAO;
import com.perfect.dao.LogProcessingDAO;
import com.perfect.entity.CreativeEntity;
import com.perfect.entity.DataAttributeInfoEntity;
import com.perfect.entity.DataOperationLogEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.mongodb.utils.EntityConstants;
import com.perfect.mongodb.utils.Pager;
import com.perfect.utils.LogUtils;
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

import static com.perfect.mongodb.utils.EntityConstants.*;

/**
 * Created by baizz on 2014-07-10.
 */
@Repository("creativeDAO")
public class CreativeDAOImpl extends AbstractUserBaseDAOImpl<CreativeEntity, Long> implements CreativeDAO {

    @Resource
    private LogProcessingDAO logProcessingDAO;

    public List<Long> getCreativeIdByAdgroupId(Long adgroupId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new BasicQuery("{}", "{" + EntityConstants.CREATIVE_ID + " : 1}");
        query.addCriteria(Criteria.where(EntityConstants.ADGROUP_ID).is(adgroupId));
        List<CreativeEntity> types = mongoTemplate.find(query, CreativeEntity.class, EntityConstants.TBL_CREATIVE);
        List<Long> creativeIds = new ArrayList<>(types.size());
        for (CreativeEntity type : types)
            creativeIds.add(type.getCreativeId());
        return creativeIds;
    }

    public List<CreativeEntity> getCreativeByAdgroupId(Long adgroupId, Map<String, Object> params, int skip, int limit) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new Query();
        Criteria criteria = Criteria.where(EntityConstants.ADGROUP_ID).is(adgroupId);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet())
                criteria.and(entry.getKey()).is(entry.getValue());
        }
        query.addCriteria(criteria);
        query.with(new PageRequest(skip, limit));
        List<CreativeEntity> list = mongoTemplate.find(query, CreativeEntity.class, EntityConstants.TBL_CREATIVE);
        return list;
    }

    @Override
    public List<CreativeEntity> getCreativeByAdgroupId(String adgroupId, Map<String, Object> params, int skip, int limit) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new Query();
        Criteria criteria = Criteria.where(EntityConstants.OBJ_ADGROUP_ID).is(adgroupId);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet())
                criteria.and(entry.getKey()).is(entry.getValue());
        }
        query.addCriteria(criteria);
        query.with(new PageRequest(skip, limit));
        List<CreativeEntity> list = mongoTemplate.find(query, CreativeEntity.class,EntityConstants.TBL_CREATIVE);
        return list;
    }

    @Override
    public List<CreativeEntity> getAllsByAdgroupIds(List<Long> l) {
        return BaseMongoTemplate.getUserMongo().find(new Query(Criteria.where(EntityConstants.ADGROUP_ID).in(l)), CreativeEntity.class, EntityConstants.TBL_CREATIVE);
    }

    @Override
    public List<CreativeEntity> getAllsByAdgroupIdsForString(List<String> l) {
        return BaseMongoTemplate.getUserMongo().find(new Query(Criteria.where(EntityConstants.OBJ_ADGROUP_ID).in(l)),CreativeEntity.class,EntityConstants.TBL_CREATIVE);
    }

    @Override
    public void deleteByCacheId(Long objectId) {
        BaseMongoTemplate.getUserMongo().remove(new Query(Criteria.where(EntityConstants.CREATIVE_ID).is(objectId)), CreativeEntity.class, EntityConstants.TBL_CREATIVE);
    }

    @Override
    public void deleteByCacheId(String cacheCreativeId) {
        BaseMongoTemplate.getUserMongo().remove(new Query(Criteria.where(getId()).is(cacheCreativeId)),CreativeEntity.class,EntityConstants.TBL_CREATIVE);
    }

    @Override
    public String insertOutId(CreativeEntity creativeEntity) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.insert(creativeEntity, EntityConstants.TBL_CREATIVE);
        DataOperationLogEntity logEntity = LogUtils.getLog(creativeEntity.getCreativeId(), CreativeEntity.class, null, creativeEntity);
        logProcessingDAO.insert(logEntity);
        return creativeEntity.getId();
    }

    @Override
    public void updateAdgroupIdByOid(String id, Long adgroupId) {
        getMongoTemplate().updateMulti(Query.query(Criteria.where(OBJ_ADGROUP_ID).is(id)), Update.update(ADGROUP_ID, adgroupId).set(OBJ_ADGROUP_ID, null), getEntityClass());
    }

    public CreativeEntity findOne(Long creativeId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        CreativeEntity entity = mongoTemplate.findOne(
                new Query(Criteria.where(EntityConstants.CREATIVE_ID).is(creativeId)), CreativeEntity.class, EntityConstants.TBL_CREATIVE);
        return entity;
    }

    public List<CreativeEntity> findAll() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<CreativeEntity> entityList = mongoTemplate.find(Query.query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId())), getEntityClass());
        return entityList;
    }

    public List<CreativeEntity> find(Map<String, Object> params, int skip, int limit) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new Query();
        if (params != null && params.size() > 0) {
            Criteria criteria = Criteria.where(EntityConstants.CREATIVE_ID).ne(null);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                criteria.and(entry.getKey()).is(entry.getValue());
            }
            query.addCriteria(criteria);
        }
        query.with(new PageRequest(skip, limit));
        List<CreativeEntity> list = mongoTemplate.find(query, CreativeEntity.class, EntityConstants.TBL_CREATIVE);
        return list;
    }

    public void insert(CreativeEntity creativeEntity) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.insert(creativeEntity, EntityConstants.TBL_CREATIVE);
        DataOperationLogEntity logEntity = LogUtils.getLog(creativeEntity.getCreativeId(), CreativeEntity.class, null, creativeEntity);
        logProcessingDAO.insert(logEntity);
    }

    public void insertAll(List<CreativeEntity> entities) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.insertAll(entities);
        List<DataOperationLogEntity> logEntities = new LinkedList<>();
        for (CreativeEntity entity : entities) {
            DataOperationLogEntity log = LogUtils.getLog(entity.getCreativeId(), CreativeEntity.class, null, entity);
            logEntities.add(log);
        }
        logProcessingDAO.insertAll(logEntities);
    }

    @SuppressWarnings("unchecked")
    public void update(CreativeEntity creativeEntity) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Long id = creativeEntity.getCreativeId();
        Query query = new Query();
        query.addCriteria(Criteria.where(EntityConstants.CREATIVE_ID).is(id));
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
                    log = LogUtils.getLog(id, CreativeEntity.class,
                            new DataAttributeInfoEntity(field.getName(), before, after), null);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        mongoTemplate.updateFirst(query, update, CreativeEntity.class, EntityConstants.TBL_CREATIVE);
        logProcessingDAO.insert(log);
    }

    public void update(List<CreativeEntity> entities) {
        for (CreativeEntity entity : entities)
            update(entity);
    }

    public void deleteById(Long creativeId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(new Query(Criteria.where(EntityConstants.CREATIVE_ID).is(creativeId)), CreativeEntity.class, EntityConstants.TBL_CREATIVE);
        DataOperationLogEntity log = LogUtils.getLog(creativeId, CreativeEntity.class, null, null);
        logProcessingDAO.insert(log);
    }

    public void deleteByIds(List<Long> creativeIds) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<DataOperationLogEntity> list = new LinkedList<>();
        for (Long id : creativeIds) {
            mongoTemplate.remove(new Query(Criteria.where(EntityConstants.CREATIVE_ID).is(id)), CreativeEntity.class, EntityConstants.TBL_CREATIVE);
            DataOperationLogEntity log = LogUtils.getLog(id, CreativeEntity.class, null, null);
            list.add(log);
        }
        logProcessingDAO.insertAll(list);
    }

    @Override
    public Class<CreativeEntity> getEntityClass() {
        return CreativeEntity.class;
    }

    public void delete(CreativeEntity creativeEntity) {
        deleteById(creativeEntity.getCreativeId());
    }

    public void deleteAll() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<CreativeEntity> creativeEntityList = findAll();
        getMongoTemplate().dropCollection(CreativeEntity.class);
        List<DataOperationLogEntity> logEntities = new LinkedList<>();
        for (CreativeEntity entity : creativeEntityList) {
            DataOperationLogEntity log = LogUtils.getLog(entity.getCreativeId(), CreativeEntity.class, null, null);
            logEntities.add(log);
        }
        logProcessingDAO.insertAll(logEntities);
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }
}
