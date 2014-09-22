package com.perfect.mongodb.dao.impl;

import com.perfect.autosdk.sms.v3.KeywordInfo;
import com.perfect.constants.LogStatusConstant;
import com.perfect.core.AppContext;
import com.perfect.dao.KeyWordBackUpDAO;
import com.perfect.dao.KeywordDAO;
import com.perfect.dao.LogDAO;
import com.perfect.dao.LogProcessingDAO;
import com.perfect.entity.AdgroupEntity;
import com.perfect.entity.DataAttributeInfoEntity;
import com.perfect.entity.DataOperationLogEntity;
import com.perfect.entity.KeywordEntity;
import com.perfect.entity.backup.KeyWordBackUpEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.mongodb.utils.EntityConstants;
import com.perfect.mongodb.utils.Pager;
import com.perfect.mongodb.utils.PagerInfo;
import com.perfect.mongodb.utils.PaginationParam;
import com.perfect.utils.LogUtils;
import org.springframework.beans.BeanUtils;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.perfect.mongodb.utils.EntityConstants.*;

/**
 * Created by baizz on 2014-07-07.
 */
@Repository("keywordDAO")
public class KeywordDAOImpl extends AbstractUserBaseDAOImpl<KeywordEntity, Long> implements KeywordDAO {

    @Resource
    private LogProcessingDAO logProcessingDAO;

    @Resource
    private LogDAO logDao;

    @Resource
    private KeyWordBackUpDAO keyWordBackUpDAO;

    @Override
    public String getId() {
        return KEYWORD_ID;
    }

    public List<Long> getKeywordIdByAdgroupId(Long adgroupId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new BasicQuery("{}", "{" + KEYWORD_ID + " : 1}");
        query.addCriteria(Criteria.where(ADGROUP_ID).is(adgroupId));
        List<KeywordEntity> list = mongoTemplate.find(query, KeywordEntity.class);
        List<Long> keywordIds = new ArrayList<>(list.size());
        for (KeywordEntity type : list)
            keywordIds.add(type.getKeywordId());
        return keywordIds;
    }

    public List<KeywordEntity> getKeywordByAdgroupId(Long adgroupId, Map<String, Object> params, int skip, int limit) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new Query();
        Criteria criteria = Criteria.where(ADGROUP_ID).is(adgroupId);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet())
                criteria.and(entry.getKey()).is(entry.getValue());
        }
        query.addCriteria(criteria);
        query.with(new PageRequest(skip, limit, new Sort(Sort.Direction.DESC, "price")));
        List<KeywordEntity> _list = mongoTemplate.find(query, KeywordEntity.class, TBL_KEYWORD);
        return _list;
    }


    //根据mongoID查询
    public List<KeywordEntity> getKeywordByAdgroupId(String adgroupId, Map<String, Object> params, int skip, int limit) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new Query();
        Criteria criteria = Criteria.where(EntityConstants.OBJ_ADGROUP_ID).is(adgroupId);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet())
                criteria.and(entry.getKey()).is(entry.getValue());
        }
        query.addCriteria(criteria);
        query.with(new PageRequest(skip, limit, new Sort(Sort.Direction.DESC, "price")));
        List<KeywordEntity> _list = mongoTemplate.find(query, KeywordEntity.class, TBL_KEYWORD);
        return _list;
    }

    @Override
    public List<KeywordEntity> findByAgroupId(Long oid) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<KeywordEntity> keywordEntityList = mongoTemplate.find(new Query(Criteria.where(EntityConstants.KEYWORD_ID)), KeywordEntity.class, EntityConstants.TBL_KEYWORD);
        return keywordEntityList;
    }

    @Override
    public List<KeywordEntity> getKeywordByIds(List<Long> ids) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        return mongoTemplate.find(new Query(Criteria.where(EntityConstants.KEYWORD_ID).in(ids)), getEntityClass(), EntityConstants.TBL_KEYWORD);
    }

    @Override
    public List<KeywordEntity> findByNames(String[] query, boolean fullMatch, PaginationParam param, Map<String, Object> queryParams) {

        Query mongoQuery = new Query();

        Criteria criteria = null;
        if (fullMatch) {
            criteria = Criteria.where(NAME).in(query);
        } else {
            String prefix = ".*(";
            String suffix = ").*";
            String reg = "";
            for (String name : query) {
                reg = reg + name + "|";
            }
            reg = reg.substring(0, reg.length() - 1);

            criteria = Criteria.where(NAME).regex(prefix + reg + suffix);

        }

        if (queryParams != null && !queryParams.isEmpty() && queryParams.size() > 0) {
//            Criteria criteria = Criteria.where(NAME).regex(prefix + reg + suffix);
            for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
                if ("matchType".equals(entry.getKey())) {
                    Integer matchType = Integer.valueOf(entry.getValue().toString());
                    if (matchType == 1) {
                        criteria.and("mt").is(1);
                    } else if (matchType == 2) {
                        criteria.and("mt").is(2).and("pt").is(3);
                    } else if (matchType == 3) {
                        criteria.and("mt").is(2).and("pt").is(2);
                    } else if (matchType == 4) {
                        criteria.and("mt").is(2).and("pt").is(1);
                    } else if (matchType == 5) {
                        criteria.and("mt").is(3);
                    }
                }
            }
        }
        mongoQuery.addCriteria(criteria);

        return getMongoTemplate().find(param.withParam(mongoQuery), getEntityClass());
    }

    @Override
    public List<KeywordEntity> findByIds(List<Long> ids) {
        return getMongoTemplate().find(Query.query(Criteria.where(KEYWORD_ID).in(ids)), getEntityClass());
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
        return mongoTemplate.findAll(KeywordInfo.class, "keywordInfo");
    }

    @Override
    public Long keywordCount(List<Long> adgroupIds) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        return mongoTemplate.count(Query.query(
                        Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and(ADGROUP_ID).in(adgroupIds)),
                getEntityClass());
    }

    @Override
    public void insertAndQuery(List<KeywordEntity> keywordEntity) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        for (KeywordEntity key : keywordEntity) {
            Query q = new Query(Criteria.where(getId()).is(key.getKeywordId()));
            if (!mongoTemplate.exists(q, KeywordEntity.class)) {
                mongoTemplate.insert(key);
                DataOperationLogEntity log = LogUtils.getLog(key.getKeywordId(), KeywordEntity.class, null, key);
                logProcessingDAO.insert(log);
            }
        }
    }

    @Override
    public KeywordEntity findByName(String name, Long accountId) {
        List<KeywordEntity> list = findByQuery(Query.query(Criteria.where("kw").is(name).and(ACCOUNT_ID).is(accountId)));
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public KeywordEntity findOne(Long keywordId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        KeywordEntity entity = mongoTemplate.
                findOne(new Query(Criteria.where(getId()).is(keywordId)), KeywordEntity.class, TBL_KEYWORD);
        return entity;
    }

    public List<KeywordEntity> findAll() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<KeywordEntity> keywordEntityList = mongoTemplate.find(Query.query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId())), getEntityClass());
        return keywordEntityList;
    }

    public List<KeywordEntity> find(Map<String, Object> params, int skip, int limit, String order) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new Query();
        if (params != null && params.size() > 0) {
            Criteria criteria = Criteria.where(getId()).ne(null);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                criteria.and(entry.getKey()).is(entry.getValue());
            }
            query.addCriteria(criteria);
        }
        query.with(new PageRequest(skip, limit, new Sort(Sort.Direction.DESC, order)));
        List<KeywordEntity> list = mongoTemplate.find(query, KeywordEntity.class, TBL_KEYWORD);
        return list;
    }


    //x
    public List<KeywordEntity> findByQuery(Query query) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        return mongoTemplate.find(query, KeywordEntity.class);
    }

    @Override
    public List<KeywordEntity> findByAdgroupId(Long adgroupId, PaginationParam param) {
        if (param == null) {
            return getMongoTemplate().find(Query.query(Criteria.where(ADGROUP_ID).is(adgroupId)), getEntityClass());
        } else {
            return getMongoTemplate().find(param.withParam(Query.query(Criteria.where(ADGROUP_ID).is(adgroupId))), getEntityClass());
        }
    }

    /**
     * 根据mongoID查询
     *
     * @param adgroupId
     * @param param
     * @return
     */
    @Override
    public List<KeywordEntity> findByAdgroupId(String adgroupId, PaginationParam param) {
        return getMongoTemplate().find(param.withParam(Query.query(Criteria.where(EntityConstants.OBJ_ADGROUP_ID).is(adgroupId))), getEntityClass());
    }

    @Override
    public List<KeywordEntity> findByAdgroupIds(List<Long> adgroupIds, PaginationParam param) {
        return getMongoTemplate().find(param.withParam(Query.query(Criteria.where(ADGROUP_ID).in(adgroupIds)))
                , getEntityClass());
    }

    @Override
    public KeywordEntity findByObjectId(String oid) {
        return getMongoTemplate().findOne(Query.query(Criteria.where(SYSTEM_ID).is(oid)), getEntityClass());
    }

    @Override
    public void updateAdgroupIdByOid(String id, Long adgroupId) {
        getMongoTemplate().updateMulti(Query.query(Criteria.where(OBJ_ADGROUP_ID).is(id)), Update.update(ADGROUP_ID, adgroupId).set(OBJ_ADGROUP_ID, null), getEntityClass());
    }

    public void insert(KeywordEntity keywordEntity) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.insert(keywordEntity, TBL_KEYWORD);
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
        query.addCriteria(Criteria.where(getId()).is(id));
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
        mongoTemplate.updateFirst(query, update, KeywordEntity.class, TBL_KEYWORD);
        logProcessingDAO.insert(log);
    }


    //xj
    public void update(KeywordEntity keywordEntity, KeyWordBackUpEntity keyWordBackUpEntity) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Long id = keywordEntity.getKeywordId();
        Query query = new Query();
        query.addCriteria(Criteria.where(EntityConstants.SYSTEM_ID).is(keywordEntity.getId()));
        Update update = new Update();
        try {
            Class _class = keywordEntity.getClass();
            Field[] fields = _class.getDeclaredFields();//get object's fields by reflect
            for (Field field : fields) {
                String fieldName = field.getName();
                if (EntityConstants.SYSTEM_ID.equals(fieldName))
                    continue;
                StringBuilder fieldGetterName = new StringBuilder("get");
                fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
                Method method = _class.getDeclaredMethod(fieldGetterName.toString());
                if (method == null)
                    continue;

                Object after = method.invoke(keywordEntity);
                if (after != null) {
                    update.set(field.getName(), after);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        mongoTemplate.updateFirst(query, update, KeywordEntity.class, TBL_KEYWORD);
        KeyWordBackUpEntity keyWordBackUpEntityFind = keyWordBackUpDAO.findByObjectId(keywordEntity.getId());
        if (keyWordBackUpEntityFind == null && keywordEntity.getLocalStatus() == 2) {
            KeyWordBackUpEntity backUpEntity = new KeyWordBackUpEntity();
            BeanUtils.copyProperties(keyWordBackUpEntity, backUpEntity);
            keyWordBackUpDAO.insert(backUpEntity);
        }
        logDao.insertLog(id, LogStatusConstant.ENTITY_KEYWORD, LogStatusConstant.OPT_UPDATE);
    }

    /**
     * 还原功能的软删除
     *
     * @param id
     */
    public void updateLocalstatu(long id) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Update update = new Update();
        update.set("ls", "");
        mongoTemplate.updateFirst(new Query(Criteria.where(EntityConstants.KEYWORD_ID).is(id)), update, EntityConstants.TBL_KEYWORD);
    }


    /**
     * 根据mongodbID修改
     *
     * @param keywordEntity
     */
    public void updateByMongoId(KeywordEntity keywordEntity) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        String id = keywordEntity.getId();
        Query query = new Query();
        query.addCriteria(Criteria.where(EntityConstants.SYSTEM_ID).is(id));
        Update update = new Update();

        try {
            Class _class = keywordEntity.getClass();
            Field[] fields = _class.getDeclaredFields();//get object's fields by reflect
            for (Field field : fields) {
                String fieldName = field.getName();
                if ("id".equals(fieldName))
                    continue;
                StringBuilder fieldGetterName = new StringBuilder("get");
                fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
                Method method = _class.getDeclaredMethod(fieldGetterName.toString());
                if (method == null)
                    continue;

                Object after = method.invoke(keywordEntity);
                if (after != null) {
                    update.set(field.getName(), after);
                    logDao.insertLog(id, LogStatusConstant.ENTITY_KEYWORD);
                    break;
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        mongoTemplate.updateFirst(query, update, KeywordEntity.class, TBL_KEYWORD);
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
        query.addCriteria(Criteria.where(TBL_KEYWORD).
                regex(Pattern.compile("^.*?" + seedWord + ".*$", Pattern.CASE_INSENSITIVE)));
        List<KeywordEntity> keywordEntities = mongoTemplate.find(query, _class, TBL_KEYWORD);
        Update update = new Update();
        update.set(fieldName, value);
        mongoTemplate.updateMulti(query, update, TBL_KEYWORD);
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

    @Override
    public void updateMultiKeyword(Long[] ids, BigDecimal price, String pcUrl) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = Query.query(Criteria.where(KEYWORD_ID).in(ids));
        Update update = new Update();
        if (price != null) {
            if (price.doubleValue() == 0) {
                //使用单元出价
                for (Long id : ids) {
                    AdgroupEntity adgroupEntity = findByKeywordId(id);
                    Double _price;
                    if (adgroupEntity != null) {
                        _price = adgroupEntity.getMaxPrice();
                        BigDecimal adgroupPrice = new BigDecimal(_price);
                        update.set("pr", adgroupPrice);
                        mongoTemplate.updateMulti(query, update, getEntityClass());
                    }
                }
            } else {
                update.set("pr", price);
                mongoTemplate.updateMulti(query, update, getEntityClass());
            }
        }
        if (pcUrl != null) {
            update.set("pc", pcUrl);
            mongoTemplate.updateMulti(query, update, getEntityClass());
        }
    }

    @Override
    public AdgroupEntity findByKeywordId(Long keywordId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        KeywordEntity keywordEntity = mongoTemplate.findOne(Query.query(Criteria.where(KEYWORD_ID).is(keywordId)), getEntityClass());
        Long adgroupId = keywordEntity.getAdgroupId();
        return mongoTemplate.findOne(Query.query(Criteria.where(ADGROUP_ID).is(adgroupId)), AdgroupEntity.class);
    }

    public void deleteById(Long id) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(new Query(Criteria.where(KEYWORD_ID).is(id)), KeywordEntity.class, TBL_KEYWORD);
        DataOperationLogEntity log = LogUtils.getLog(id, KeywordEntity.class, null, null);
        logProcessingDAO.insert(log);
    }

    /**
     * 根据mongoId硬删除
     *
     * @param id
     */
    public void deleteById(String id) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(new Query(Criteria.where(EntityConstants.SYSTEM_ID).is(id)), KeywordEntity.class, TBL_KEYWORD);
        logDao.insertLog(id, LogStatusConstant.ENTITY_KEYWORD);
    }

    /**
     * 根据Long类型id软删除
     *
     * @param id
     */
    public void softDelete(Long id) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Update update = new Update();
        update.set("ls", 3);
        mongoTemplate.updateFirst(new Query(Criteria.where(EntityConstants.KEYWORD_ID).is(id)), update, EntityConstants.TBL_KEYWORD);
    }


    @Override
    public void deleteByIds(List<Long> ids) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<DataOperationLogEntity> list = new LinkedList<>();
        for (Long id : ids) {
            mongoTemplate.remove(new Query(Criteria.where(KEYWORD_ID).is(id)), KeywordEntity.class, TBL_KEYWORD);
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
            Criteria where = Criteria.where(KEYWORD_ID).ne(null);
            for (Map.Entry<String, Object> m : params.entrySet()) {
                where.and(m.getKey()).is(m.getValue());
            }
            q.addCriteria(where);
        }
        addOrder(orderBy, q);
        list = mongoTemplate.find(q, KeywordEntity.class, TBL_KEYWORD);
        Pager p = new Pager();
        p.setRows(list);

        return p;
    }


    //xj
    @Override
    public PagerInfo findByPageInfo(Query q, int pageSize, int pageNo) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        int totalCount = getListTotalCount(q);

        PagerInfo p = new PagerInfo(pageNo, pageSize, totalCount);
        q.skip(p.getFirstStation());
        q.limit(p.getPageSize());
        q.with(new Sort(Sort.Direction.DESC, "name"));
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        List list = mongoTemplate.find(q, getEntityClass());
        p.setList(list);
        return p;
    }

    //xj
    public int getListTotalCount(Query q) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        return (int) mongoTemplate.count(q, EntityConstants.TBL_KEYWORD);
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


    public void remove(Query query) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(query, KeywordEntity.class, TBL_KEYWORD);
    }

}
