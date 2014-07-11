package com.perfect.mongodb.dao.impl;

import com.perfect.dao.KeywordDAO;
import com.perfect.dao.LogProcessingDAO;
import com.perfect.entity.DataAttributeInfoEntity;
import com.perfect.entity.DataOperationLogEntity;
import com.perfect.entity.KeywordEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import java.util.regex.Pattern;

/**
 * Created by baizz on 2014-7-7.
 */
@Repository("keywordDAO")
public class KeywordDAOImpl implements KeywordDAO {

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private LogProcessingDAO logProcessingDAO;

    public List<Long> getKeywordIdByAdgroupId(Long adgroupId) {
        Query query = new BasicQuery("{}", "{keywordId : 1}");
        query.addCriteria(Criteria.where("adgroupId").is(adgroupId));
        List<KeywordEntity> list = mongoTemplate.find(query, KeywordEntity.class, "KeywordType");
        List<Long> keywordIds = new ArrayList<>(list.size());
        for (KeywordEntity type : list)
            keywordIds.add(type.getKeywordId());
        return keywordIds;
    }

    public List<KeywordEntity> getKeywordByAdgroupId(Long adgroupId, Map<String, Object> params, int skip, int limit) {
        Query query = new Query();
        Criteria criteria = Criteria.where("adgroupId").is(adgroupId);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet())
                criteria.and(entry.getKey()).is(entry.getValue());
        }
        query.addCriteria(criteria);
        query.with(new PageRequest(skip, limit, new Sort(Sort.Direction.DESC, "price")));
        List<KeywordEntity> _list = mongoTemplate.find(query, KeywordEntity.class, "KeywordType");
        return _list;
    }

    public KeywordEntity findOne(Long keywordId) {
        KeywordEntity entity = mongoTemplate.
                findOne(new Query(Criteria.where("keywordId").is(keywordId)), KeywordEntity.class, "KeywordType");
        return entity;
    }

    public List<KeywordEntity> findAll() {
        List<KeywordEntity> keywordEntityList = mongoTemplate.findAll(KeywordEntity.class, "KeywordType");
        return keywordEntityList;
    }

    public List<KeywordEntity> find(Map<String, Object> params, int skip, int limit) {
        Query query = new Query();
        if (params != null && params.size() > 0) {
            Criteria criteria = Criteria.where("keywordId").ne(null);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                criteria.and(entry.getKey()).is(entry.getValue());
            }
            query.addCriteria(criteria);
        }
        query.with(new PageRequest(skip, limit, new Sort(Sort.Direction.DESC, "price")));
        List<KeywordEntity> list = mongoTemplate.find(query, KeywordEntity.class, "KeywordType");
        return list;
    }

    public void insert(KeywordEntity keywordEntity) {
        mongoTemplate.insert(keywordEntity, "KeywordType");
        DataOperationLogEntity log = logProcessingDAO.getLog(keywordEntity.getKeywordId(), KeywordEntity.class, null, keywordEntity);
        logProcessingDAO.insert(log);
    }

    public void insertAll(List<KeywordEntity> entities) {
        mongoTemplate.insertAll(entities);
        List<DataOperationLogEntity> logEntities = new LinkedList<>();
        for (KeywordEntity entity : entities) {
            DataOperationLogEntity log = logProcessingDAO.getLog(entity.getKeywordId(), KeywordEntity.class, null, entity);
            logEntities.add(log);
        }
        logProcessingDAO.insertAll(logEntities);
    }

    @SuppressWarnings("unchecked")
    public void update(KeywordEntity keywordEntity) {
        Long id = keywordEntity.getKeywordId();
        Query query = new Query();
        query.addCriteria(Criteria.where("keywordId").is(id));
        Update update = new Update();
        DataOperationLogEntity log = null;
        try {
            Class _class = keywordEntity.getClass();
            Field[] fields = _class.getDeclaredFields();//get object's fields by reflect
            for (Field field : fields) {
                String fieldName = field.getName();
                if ("keywordId".equals(fieldName))
                    continue;
                StringBuilder fieldGetterName = new StringBuilder("get");
                fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
                Method method = _class.getDeclaredMethod(fieldGetterName.toString());
                Object after = method.invoke(keywordEntity);
                if (after != null) {
                    update.set(field.getName(), after);
                    Object before = method.invoke(findOne(id));
                    log = logProcessingDAO.getLog(id, KeywordEntity.class, new DataAttributeInfoEntity(field.getName(), before, after), null);
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
        mongoTemplate.updateFirst(query, update, KeywordEntity.class, "KeywordType");
        logProcessingDAO.insert(log);
    }

    public void update(List<KeywordEntity> entities) {
        for (KeywordEntity entity : entities)
            update(entity);
    }

    @SuppressWarnings("unchecked")
    public void updateMulti(String fieldName, String seedWord, Object value) {
        Class _class = KeywordEntity.class;
        Query query = new Query();
        query.addCriteria(Criteria.where("keyword").
                regex(Pattern.compile("^.*?" + seedWord + ".*$", Pattern.CASE_INSENSITIVE)));
        List<KeywordEntity> keywordEntities = mongoTemplate.find(query, _class, "KeywordType");
        Update update = new Update();
        update.set(fieldName, value);
        mongoTemplate.updateMulti(query, update, "KeywordType");
        List<DataOperationLogEntity> logEntities = new LinkedList<>();
        try {
            DataOperationLogEntity logEntity;
            DataAttributeInfoEntity attribute;
            for (KeywordEntity entity : keywordEntities) {
                StringBuilder fieldGetterName = new StringBuilder("get");
                fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
                Method method = _class.getDeclaredMethod(fieldGetterName.toString());
                Object before = method.invoke(entity);
                attribute = new DataAttributeInfoEntity(fieldName, before, value);
                logEntity = logProcessingDAO.getLog(entity.getKeywordId(), _class, attribute, null);
                logEntities.add(logEntity);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        logProcessingDAO.insertAll(logEntities);
    }

    public void deleteById(Long id) {
        mongoTemplate.remove(new Query(Criteria.where("keywordId").is(id)), KeywordEntity.class, "KeywordType");
        DataOperationLogEntity log = logProcessingDAO.getLog(id, KeywordEntity.class, null, null);
        logProcessingDAO.insert(log);
    }

    public void deleteByIds(List<Long> ids) {
        List<DataOperationLogEntity> list = new LinkedList<>();
        for (Long id : ids) {
            mongoTemplate.remove(new Query(Criteria.where("keywordId").is(id)), KeywordEntity.class, "KeywordType");
            DataOperationLogEntity log = logProcessingDAO.getLog(id, KeywordEntity.class, null, null);
            list.add(log);
        }
        logProcessingDAO.insertAll(list);
    }

    public void delete(KeywordEntity keywordEntity) {
        deleteById(keywordEntity.getKeywordId());
    }

    public void deleteAll() {
        List<KeywordEntity> keywordEntities = findAll();
        mongoTemplate.dropCollection(KeywordEntity.class);
        List<DataOperationLogEntity> logEntities = new LinkedList<>();
        for (KeywordEntity entity : keywordEntities) {
            DataOperationLogEntity log = logProcessingDAO.getLog(entity.getKeywordId(), KeywordEntity.class, null, null);
            logEntities.add(log);
        }
        logProcessingDAO.insertAll(logEntities);
    }
}
