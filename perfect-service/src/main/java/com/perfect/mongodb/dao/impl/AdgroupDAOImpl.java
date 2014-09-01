package com.perfect.mongodb.dao.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.LogProcessingDAO;
import com.perfect.entity.*;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.mongodb.utils.EntityConstants;
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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.perfect.mongodb.utils.EntityConstants.ACCOUNT_ID;
import static com.perfect.mongodb.utils.EntityConstants.ADGROUP_ID;
import static com.perfect.mongodb.utils.EntityConstants.CAMPAIGN_ID;

/**
 * Created by vbzer_000 on 2014-07-02.
 */
@Repository("adgroupDAO")
public class AdgroupDAOImpl extends AbstractUserBaseDAOImpl<AdgroupEntity, Long> implements AdgroupDAO {

    @Override
    public String getId() {
        return ADGROUP_ID;
    }

    @Resource
    private LogProcessingDAO logProcessingDAO;

    public List<Long> getAllAdgroupId() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new BasicQuery("{}", "{" + ADGROUP_ID + " : 1}");
        List<AdgroupEntity> list = mongoTemplate.find(query, AdgroupEntity.class, EntityConstants.TBL_ADGROUP);
        List<Long> adgroupIds = new ArrayList<>(list.size());
        for (AdgroupEntity type : list)
            adgroupIds.add(type.getAdgroupId());
        return adgroupIds;
    }

    public List<Long> getAdgroupIdByCampaignId(Long campaignId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new BasicQuery("{}", "{" + ADGROUP_ID + " : 1}");
        query.addCriteria(Criteria.where(CAMPAIGN_ID).is(campaignId));
        List<AdgroupEntity> list = mongoTemplate.find(query, AdgroupEntity.class, EntityConstants.TBL_ADGROUP);
        List<Long> adgroupIds = new ArrayList<>(list.size());
        for (AdgroupEntity type : list)
            adgroupIds.add(type.getAdgroupId());
        return adgroupIds;
    }

    public List<AdgroupEntity> getAdgroupByCampaignId(Long campaignId, Map<String, Object> params, int skip, int limit) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new Query();
        Criteria criteria = Criteria.where(CAMPAIGN_ID).is(campaignId);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet())
                criteria.and(entry.getKey()).is(entry.getValue());
        }
        query.addCriteria(criteria);
        query.with(new PageRequest(skip, limit));
        List<AdgroupEntity> _list = mongoTemplate.find(query, AdgroupEntity.class, EntityConstants.TBL_ADGROUP);
        return _list;
    }

    public AdgroupEntity findOne(Long adgroupId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        AdgroupEntity _adgroupEntity = mongoTemplate.findOne(
                new Query(Criteria.where(ADGROUP_ID).is(adgroupId)), AdgroupEntity.class, EntityConstants.TBL_ADGROUP);
        return _adgroupEntity;
    }

    public List<AdgroupEntity> findAll() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<AdgroupEntity> adgroupEntities = mongoTemplate.find(Query.query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId())), getEntityClass());
        return adgroupEntities;
    }

    /**
     * 条件查询, 分页
     *
     * @param params
     * @param skip
     * @param limit
     * @return
     */
    public List<AdgroupEntity> find(Map<String, Object> params, int skip, int limit) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new Query();
        if (params != null && params.size() > 0) {
            Criteria criteria = Criteria.where(ADGROUP_ID).ne(null);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                criteria.and(entry.getKey()).is(entry.getValue());
            }
            query.addCriteria(criteria);
        }
        query.with(new PageRequest(skip, limit, new Sort(Sort.Direction.DESC, "price")));
        List<AdgroupEntity> list = mongoTemplate.find(query, AdgroupEntity.class, EntityConstants.TBL_ADGROUP);
        return list;
    }

    /**
     * 条件查询
     *
     * @param query
     * @return
     */
    public List<AdgroupEntity> findByQuery(Query query) {
        return BaseMongoTemplate.getUserMongo().find(query, AdgroupEntity.class);
    }

    @Override
    public List<AdgroupEntity> findByCampaignId(Long cid) {
        return getMongoTemplate().find(Query.query(Criteria.where(CAMPAIGN_ID).is(cid)), getEntityClass());
    }

    @Override
    public List<AdgroupEntity> findIdByCampaignId(Long cid) {
        Query query = new BasicQuery("{}", "{ " + ADGROUP_ID + " : 1 }");

        return getMongoTemplate().find(query.addCriteria(Criteria.where(CAMPAIGN_ID).is(cid))
                , AdgroupEntity.class);
    }

    public void insert(AdgroupEntity adgroupEntity) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.insert(adgroupEntity, EntityConstants.TBL_ADGROUP);
        DataOperationLogEntity logEntity = LogUtils.getLog(adgroupEntity.getAdgroupId(), AdgroupEntity.class, null, adgroupEntity);
        logProcessingDAO.insert(logEntity);
    }

    public void insertAll(List<AdgroupEntity> entities) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.insertAll(entities);
        List<DataOperationLogEntity> logEntities = new LinkedList<>();
        for (AdgroupEntity entity : entities) {
            DataOperationLogEntity logEntity = LogUtils.getLog(entity.getAdgroupId(), AdgroupEntity.class, null, entity);
            logEntities.add(logEntity);
        }
        logProcessingDAO.insertAll(logEntities);
    }

    @SuppressWarnings("unchecked")
    public void update(AdgroupEntity adgroupEntity) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Long id = adgroupEntity.getAdgroupId();
        Query query = new Query();
        query.addCriteria(Criteria.where(ADGROUP_ID).is(id));
        Update update = new Update();
        DataOperationLogEntity log = null;
        try {
            Class _class = adgroupEntity.getClass();
            Field[] fields = _class.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                if ("adgroupId".equals(fieldName))
                    continue;
                StringBuilder fieldGetterName = new StringBuilder("get");
                fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
                Method method = _class.getDeclaredMethod(fieldGetterName.toString());
                Object after = method.invoke(adgroupEntity);
                if (after != null) {
                    update.set(field.getName(), after);
                    Object before = method.invoke(findOne(id));
                    log = LogUtils.getLog(id, AdgroupEntity.class,
                            new DataAttributeInfoEntity(field.getName(), before, after), null);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        mongoTemplate.updateFirst(query, update, AdgroupEntity.class, EntityConstants.TBL_ADGROUP);
        logProcessingDAO.insert(log);
    }

    public void update(List<AdgroupEntity> entities) {
        for (AdgroupEntity entity : entities)
            update(entity);
    }

    public void deleteById(final Long adgroupId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(new Query(Criteria.where(ADGROUP_ID).is(adgroupId)), AdgroupEntity.class, EntityConstants.TBL_ADGROUP);
        deleteSub(new ArrayList<Long>(1) {{
            add(adgroupId);
        }});
    }

    public void deleteByIds(List<Long> adgroupIds) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(new Query(Criteria.where(ADGROUP_ID).in(adgroupIds)), AdgroupEntity.class, EntityConstants.TBL_ADGROUP);
        deleteSub(adgroupIds);
    }

    @Override
    public Class<AdgroupEntity> getEntityClass() {
        return AdgroupEntity.class;
    }

    public void delete(AdgroupEntity adgroupEntity) {
        deleteById(adgroupEntity.getAdgroupId());
    }

    public void deleteAll() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.dropCollection(AdgroupEntity.class);
        deleteSub(getAllAdgroupId());
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

//    private Update getUpdate(AdgroupEntity adgroupEntity) {
//        Update update = new Update();
//
//        if (adgroupEntity.getAdgroupName() != null) {
//            update = update.addToSet(ADGROUP_NAME, adgroupEntity.getAdgroupName());
//        }
//
//        if (adgroupEntity.getCampaignId() != null) {
//            update = update.addToSet(CAMPAIGN_ID, adgroupEntity.getCampaignId());
//        }
//
//        if (adgroupEntity.getPause() != null) {
//            update = update.addToSet(PAUSE, adgroupEntity.getPause());
//        }
//
//        if (adgroupEntity.getExactNegativeWords() != null) {
//            update = update.addToSet(EXA_NEG_WORDS, adgroupEntity.getExactNegativeWords());
//        }
//
//        if (adgroupEntity.getNegativeWords() != null) {
//            update = update.addToSet(NEG_WORDS, adgroupEntity.getNegativeWords());
//        }
//
//        if (adgroupEntity.getReserved() != null) {
//            update = update.addToSet(RESERVED, adgroupEntity.getReserved());
//        }
//
//        if (adgroupEntity.getMaxPrice() != null) {
//            update = update.addToSet(MAX_PRICE, adgroupEntity.getMaxPrice());
//        }
//
//        return update;
//    }

    private void deleteSub(List<Long> adgroupIds) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(new Query(Criteria.where(ADGROUP_ID).in(adgroupIds)), KeywordEntity.class);
        mongoTemplate.remove(new Query(Criteria.where(ADGROUP_ID).in(adgroupIds)), CreativeEntity.class);
        List<DataOperationLogEntity> logEntities = new LinkedList<>();
        for (Long id : adgroupIds) {
            DataOperationLogEntity log = LogUtils.getLog(id, AdgroupEntity.class, null, null);
            logEntities.add(log);
        }
        logProcessingDAO.insertAll(logEntities);
    }
}
