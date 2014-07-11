package com.perfect.mongodb.dao.impl;

import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.LogProcessingDAO;
import com.perfect.entity.*;
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
import java.util.*;

import static com.perfect.constants.AdgroupEntityConstant.*;

/**
 * Created by vbzer_000 on 2014-7-2.
 */
@Repository("adgroupDAO")
public class AdgroupDAOImpl implements AdgroupDAO {

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private LogProcessingDAO logProcessingDAO;

    public List<Long> getAllAdgroupId() {
        Query query = new BasicQuery("{}", "{adgroupId : 1}");
        List<AdgroupEntity> list = mongoTemplate.find(query, AdgroupEntity.class, "AdgroupType");
        List<Long> adgroupIds = new ArrayList<>(list.size());
        for (AdgroupEntity type : list)
            adgroupIds.add(type.getAdgroupId());
        return adgroupIds;
    }

    public List<Long> getAdgroupIdByCampaignId(Long campaignId) {
        Query query = new BasicQuery("{}", "{adgroupId : 1}");
        query.addCriteria(Criteria.where("campaignId").is(campaignId));
        List<AdgroupEntity> list = mongoTemplate.find(query, AdgroupEntity.class, "AdgroupType");
        List<Long> adgroupIds = new ArrayList<>(list.size());
        for (AdgroupEntity type : list)
            adgroupIds.add(type.getAdgroupId());
        return adgroupIds;
    }

    public List<AdgroupEntity> getAdgroupByCampaignId(Long campaignId, Map<String, Object> params, int skip, int limit) {
        Query query = new Query();
        Criteria criteria = Criteria.where("campainId").is(campaignId);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet())
                criteria.and(entry.getKey()).is(entry.getValue());
        }
        query.addCriteria(criteria);
        query.with(new PageRequest(skip, limit));
        List<AdgroupEntity> _list = mongoTemplate.find(query, AdgroupEntity.class, "AdgroupType");
        return _list;
    }

    public AdgroupEntity findOne(Long adgroupId) {
        AdgroupEntity _adgroupEntity = mongoTemplate.findOne(
                new Query(Criteria.where("adgroupId").is(adgroupId)), AdgroupEntity.class, "AdgroupType");
        return _adgroupEntity;
    }

    public List<AdgroupEntity> findAll() {
        List<AdgroupEntity> adgroupEntities = mongoTemplate.findAll(AdgroupEntity.class, "AdgroupType");
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
        Query query = new Query();
        if (params != null && params.size() > 0) {
            Criteria criteria = Criteria.where("adgroupId").ne(null);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                criteria.and(entry.getKey()).is(entry.getValue());
            }
            query.addCriteria(criteria);
        }
        query.with(new PageRequest(skip, limit, new Sort(Sort.Direction.DESC, "price")));
        List<AdgroupEntity> list = mongoTemplate.find(query, AdgroupEntity.class, "AdgroupType");
        return list;
    }

    public void insert(AdgroupEntity adgroupEntity) {
        mongoTemplate.insert(adgroupEntity, "AdgroupType");
        DataOperationLogEntity logEntity = logProcessingDAO.getLog(adgroupEntity.getAdgroupId(), AdgroupEntity.class, null, adgroupEntity);
        logProcessingDAO.insert(logEntity);
    }

    public void insertAll(List<AdgroupEntity> entities) {
        mongoTemplate.insertAll(entities);
        List<DataOperationLogEntity> logEntities = new LinkedList<>();
        for (AdgroupEntity entity : entities) {
            DataOperationLogEntity logEntity = logProcessingDAO.getLog(entity.getAdgroupId(), AdgroupEntity.class, null, entity);
            logEntities.add(logEntity);
        }
        logProcessingDAO.insertAll(logEntities);
    }

    @SuppressWarnings("unchecked")
    public void update(AdgroupEntity adgroupEntity) {
        Long id = adgroupEntity.getAdgroupId();
        Query query = new Query();
        query.addCriteria(Criteria.where("adgroupId").is(id));
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
                    log = logProcessingDAO.getLog(id, AdgroupEntity.class, new DataAttributeInfoEntity(field.getName(), before, after), null);
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
        mongoTemplate.updateFirst(query, update, AdgroupEntity.class, "AdgroupType");
        logProcessingDAO.insert(log);
    }

    public void update(List<AdgroupEntity> entities) {
        for (AdgroupEntity entity : entities)
            update(entity);
    }

    public void deleteById(Long adgroupId) {
        mongoTemplate.remove(new Query(Criteria.where("adgroupId").is(adgroupId)), AdgroupEntity.class, "AdgroupType");
        deleteSub(Arrays.asList(new Long[]{adgroupId}));
    }

    public void deleteByIds(List<Long> adgroupIds) {
        mongoTemplate.remove(new Query(Criteria.where("adgroupId").in(adgroupIds)), AdgroupEntity.class, "AdgroupType");
        deleteSub(adgroupIds);
    }

    public void delete(AdgroupEntity adgroupEntity) {
        deleteById(adgroupEntity.getAdgroupId());
    }

    public void deleteAll() {
        mongoTemplate.dropCollection("AdgroupType");
        deleteSub(getAllAdgroupId());
    }

    private Update getUpdate(AdgroupEntity adgroupEntity) {
        Update update = new Update();

        if (adgroupEntity.getAdgroupName() != null) {
            update = update.addToSet(ADGROUP_NAME, adgroupEntity.getAdgroupName());
        }

        if (adgroupEntity.getCampaignId() != null) {
            update = update.addToSet(CAMPAIGN_ID, adgroupEntity.getCampaignId());
        }

        if (adgroupEntity.getPause() != null) {
            update = update.addToSet(PAUSE, adgroupEntity.getPause());
        }

        if (adgroupEntity.getExactNegativeWords() != null) {
            update = update.addToSet(EXA_NEG_WORDS, adgroupEntity.getExactNegativeWords());
        }

        if (adgroupEntity.getNegativeWords() != null) {
            update = update.addToSet(NEG_WORDS, adgroupEntity.getNegativeWords());
        }

        if (adgroupEntity.getReserved() != null) {
            update = update.addToSet(RESERVED, adgroupEntity.getReserved());
        }

        if (adgroupEntity.getMaxPrice() != null) {
            update = update.addToSet(MAX_PRICE, adgroupEntity.getMaxPrice());
        }

        return update;
    }

    private void deleteSub(List<Long> adgroupIds) {
        mongoTemplate.remove(new Query(Criteria.where("adgroupId").in(adgroupIds)), KeywordEntity.class, "KeywordType");
        mongoTemplate.remove(new Query(Criteria.where("adgroupId").in(adgroupIds)), CreativeEntity.class, "CreativeType");
        List<DataOperationLogEntity> logEntities = new LinkedList<>();
        for (Long id : adgroupIds) {
            DataOperationLogEntity log = logProcessingDAO.getLog(id, AdgroupEntity.class, null, null);
            logEntities.add(log);
        }
        logProcessingDAO.insertAll(logEntities);
    }
}
