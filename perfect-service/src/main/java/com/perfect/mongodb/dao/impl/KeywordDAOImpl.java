package com.perfect.mongodb.dao.impl;

import com.perfect.autosdk.sms.v3.KeywordInfo;
import com.perfect.dao.KeywordDAO;
import com.perfect.dao.LogProcessingDAO;
import com.perfect.entity.DataAttributeInfoEntity;
import com.perfect.entity.DataOperationLogEntity;
import com.perfect.entity.KeywordEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.mongodb.utils.Pager;
import com.perfect.utils.LogUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by baizz on 2014-07-07.
 */
@Repository("keywordDAO")
public class KeywordDAOImpl extends AbstractUserBaseDAOImpl<KeywordEntity, Long> implements KeywordDAO {

    @Resource
    private LogProcessingDAO logProcessingDAO;

    public List<Long> getKeywordIdByAdgroupId(Long adgroupId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new BasicQuery("{}", "{kwid : 1}");
        query.addCriteria(Criteria.where("adid").is(adgroupId));
        List<KeywordEntity> list = mongoTemplate.find(query, KeywordEntity.class, "keyword");
        List<Long> keywordIds = new ArrayList<>(list.size());
        for (KeywordEntity type : list)
            keywordIds.add(type.getKeywordId());
        return keywordIds;
    }

    public List<KeywordEntity> getKeywordByAdgroupId(Long adgroupId, Map<String, Object> params, int skip, int limit) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new Query();
        Criteria criteria = Criteria.where("adid").is(adgroupId);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet())
                criteria.and(entry.getKey()).is(entry.getValue());
        }
        query.addCriteria(criteria);
        query.with(new PageRequest(skip, limit, new Sort(Sort.Direction.DESC, "price")));
        List<KeywordEntity> _list = mongoTemplate.find(query, KeywordEntity.class, "keyword");
        return _list;
    }

    @Override
    public Pager getKeywordByPager(HttpServletRequest request, Map<String, Object> params, int orderBy) {
        int start = Integer.parseInt(request.getParameter(START));
        int pageSize = Integer.parseInt(request.getParameter(PAGESIZE));
        Pager pager = findByPager(start, pageSize, params, orderBy);
        return pager;
    }

    @Override
    public List<KeywordInfo> getKeywordInfo() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        return mongoTemplate.findAll(KeywordInfo.class, "KeywordInfo");
    }

    @Override
    public void insertAndQuery(List<KeywordEntity> keywordEntity) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        for (KeywordEntity key : keywordEntity) {
            Query q = new Query(Criteria.where("kwid").is(key.getKeywordId()));
            if (!mongoTemplate.exists(q, KeywordEntity.class)) {
                mongoTemplate.insert(key, "keyword");
                DataOperationLogEntity log = LogUtils.getLog(key.getKeywordId(), KeywordEntity.class, null, key);
                logProcessingDAO.insert(log);
            }
        }
    }

    public KeywordEntity findOne(Long keywordId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        KeywordEntity entity = mongoTemplate.
                findOne(new Query(Criteria.where("kwid").is(keywordId)), KeywordEntity.class, "keyword");
        return entity;
    }

    public List<KeywordEntity> findAll() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<KeywordEntity> keywordEntityList = mongoTemplate.findAll(KeywordEntity.class, "keyword");
        return keywordEntityList;
    }

    public List<KeywordEntity> find(Map<String, Object> params, int skip, int limit) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new Query();
        if (params != null && params.size() > 0) {
            Criteria criteria = Criteria.where("kwid").ne(null);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                criteria.and(entry.getKey()).is(entry.getValue());
            }
            query.addCriteria(criteria);
        }
        query.with(new PageRequest(skip, limit, new Sort(Sort.Direction.DESC, "price")));
        List<KeywordEntity> list = mongoTemplate.find(query, KeywordEntity.class, "keyword");
        return list;
    }


    //x
    public  List<KeywordEntity> findByQuery(Query query){
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
       return mongoTemplate.find(query,KeywordEntity.class);
    }

    public void insert(KeywordEntity keywordEntity) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.insert(keywordEntity, "keyword");
        DataOperationLogEntity log = LogUtils.getLog(keywordEntity.getKeywordId(), KeywordEntity.class, null, keywordEntity);
        logProcessingDAO.insert(log);
    }

    public void insertAll(List<KeywordEntity> entities) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.insertAll(entities);
        List<DataOperationLogEntity> logEntities = new LinkedList<>();
        for (KeywordEntity entity : entities) {
            DataOperationLogEntity log = LogUtils.getLog(entity.getKeywordId(), KeywordEntity.class, null, entity);
            logEntities.add(log);
        }
        logProcessingDAO.insertAll(logEntities);
    }

    @SuppressWarnings("unchecked")
    public void update(KeywordEntity keywordEntity) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Long id = keywordEntity.getKeywordId();
        Query query = new Query();
        query.addCriteria(Criteria.where("kwid").is(id));
        Update update = new Update();
        DataOperationLogEntity log = null;
        try {
            Class _class = keywordEntity.getClass();
            Field[] fields = _class.getDeclaredFields();//get object's fields by reflect
            for (Field field : fields) {
                String fieldName = field.getName();
                if ("kwid".equals(fieldName))
                    continue;
                StringBuilder fieldGetterName = new StringBuilder("get");
                fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
                Method method = _class.getDeclaredMethod(fieldGetterName.toString());
                if (method == null)
                    continue;

                Object after = method.invoke(keywordEntity);
                if (after != null) {
                    update.set(field.getName(), after);
                    Object before = method.invoke(findOne(id));
                    log = LogUtils.getLog(id, KeywordEntity.class, new DataAttributeInfoEntity(field.getName(), before, after), null);
                    break;
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        mongoTemplate.updateFirst(query, update, KeywordEntity.class, "keyword");
        logProcessingDAO.insert(log);
    }

    public void update(List<KeywordEntity> entities) {
        for (KeywordEntity entity : entities)
            update(entity);
    }

    @SuppressWarnings("unchecked")
    public void updateMulti(String fieldName, String seedWord, Object value) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Class _class = KeywordEntity.class;
        Query query = new Query();
        query.addCriteria(Criteria.where("keyword").
                regex(Pattern.compile("^.*?" + seedWord + ".*$", Pattern.CASE_INSENSITIVE)));
        List<KeywordEntity> keywordEntities = mongoTemplate.find(query, _class, "keyword");
        Update update = new Update();
        update.set(fieldName, value);
        mongoTemplate.updateMulti(query, update, "keyword");
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
                logEntity = LogUtils.getLog(entity.getKeywordId(), _class, attribute, null);
                logEntities.add(logEntity);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        logProcessingDAO.insertAll(logEntities);
    }

    public void deleteById(Long id) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(new Query(Criteria.where("kwid").is(id)), KeywordEntity.class, "keyword");
        DataOperationLogEntity log = LogUtils.getLog(id, KeywordEntity.class, null, null);
        logProcessingDAO.insert(log);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<DataOperationLogEntity> list = new LinkedList<>();
        for (Long id : ids) {
            mongoTemplate.remove(new Query(Criteria.where("kwid").is(id)), KeywordEntity.class, "keyword");
            DataOperationLogEntity log = LogUtils.getLog(id, KeywordEntity.class, null, null);
            list.add(log);
        }
        logProcessingDAO.insertAll(list);
    }

    @Override
    public Class<KeywordEntity> getEntityClass() {
        return KeywordEntity.class;
    }

    public void delete(KeywordEntity keywordEntity) {
        deleteById(keywordEntity.getKeywordId());
    }


    public void deleteAll() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<KeywordEntity> keywordEntities = findAll();
        getMongoTemplate().dropCollection(KeywordEntity.class);
        List<DataOperationLogEntity> logEntities = new LinkedList<>();
        for (KeywordEntity entity : keywordEntities) {
            DataOperationLogEntity log = LogUtils.getLog(entity.getKeywordId(), KeywordEntity.class, null, null);
            logEntities.add(log);
        }
        logProcessingDAO.insertAll(logEntities);
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> params, int orderBy) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query q = new Query();
        List<KeywordEntity> list;
        if (params != null && params.size() > 0) {
            q.skip(start);
            q.limit(pageSize);
            Criteria where = Criteria.where("kwid").ne(null);
            for (Map.Entry<String, Object> m : params.entrySet()) {
                where.and(m.getKey()).is(m.getValue());
            }
            q.addCriteria(where);
        }
        addOrder(orderBy, q);
        list = mongoTemplate.find(q, KeywordEntity.class, "keyword");
        Pager p = new Pager();
        p.setRows(list);

        return p;
    }

    private int getCount(Map<String, Object> params, String collections, String nell) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query q = new Query();
        if (params != null && params.size() > 0) {
            Criteria where = nell != null ? Criteria.where(nell).ne(null) : null;
            for (Map.Entry<String, Object> m : params.entrySet()) {
                where.and(m.getKey()).is(m.getValue());
            }
            q.addCriteria(where);
        }
        return (int) mongoTemplate.count(q, collections);
    }

    private void addOrder(int orderBy, Query q) {
        switch (orderBy) {
            case 1:
                q.with(new Sort(Sort.Direction.DESC, "price"));
                break;
            default:
                q.with(new Sort(Sort.Direction.DESC, "price"));
                break;
        }
    }


    public void remove(Query query){
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(query, KeywordEntity.class, "keyword");
    }

}
