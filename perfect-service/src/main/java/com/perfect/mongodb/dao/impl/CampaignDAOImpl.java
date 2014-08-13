package com.perfect.mongodb.dao.impl;

import com.perfect.dao.CampaignDAO;
import com.perfect.dao.LogProcessingDAO;
import com.perfect.entity.*;
import com.perfect.mongodb.utils.Pager;
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
import java.util.*;

/**
 * Created by vbzer_000 on 2014-6-27.
 */
@Repository("campaignDAO")
public class CampaignDAOImpl implements CampaignDAO {

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private LogProcessingDAO logProcessingDAO;

    public List<Long> getAllCampaignId() {
        Query query = new BasicQuery("{}", "{campaignId : 1}");
        List<CampaignEntity> list = mongoTemplate.find(query, CampaignEntity.class, "CampaginType");
        List<Long> campaignIds = new ArrayList<>(list.size());
        for (CampaignEntity type : list)
            campaignIds.add(type.getCampaignId());
        return campaignIds;
    }

    public CampaignEntity findOne(Long campaignId) {
        CampaignEntity campaignEntity = mongoTemplate.findOne(
                new Query(Criteria.where("campaignId").is(campaignId)),
                CampaignEntity.class,
                "CampaginType");
        return campaignEntity;
    }

    public List<CampaignEntity> findAll() {
        List<CampaignEntity> list = mongoTemplate.findAll(CampaignEntity.class, "CampaginType");
        return list;
    }

    public List<CampaignEntity> find(Map<String, Object> params, int skip, int limit) {
        Query query = new Query();
        if (params != null && params.size() > 0) {
            Criteria criteria = Criteria.where("campaignId").ne(null);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                criteria.and(entry.getKey()).is(entry.getValue());
            }
            query.addCriteria(criteria);
        }
        query.with(new PageRequest(skip, limit));
        List<CampaignEntity> list = mongoTemplate.find(query, CampaignEntity.class, "CampaginType");
        return list;
    }

    public void insert(CampaignEntity campaignEntity) {
        mongoTemplate.insert(campaignEntity, "CampaginType");
        DataOperationLogEntity log = logProcessingDAO.getLog(campaignEntity.getCampaignId(), CampaignEntity.class, null, campaignEntity);
        logProcessingDAO.insert(log);
    }

    public void insertAll(List<CampaignEntity> entities) {
        mongoTemplate.insertAll(entities);
        List<DataOperationLogEntity> logEntities = new LinkedList<>();
        for (CampaignEntity entity : entities) {
            DataOperationLogEntity log = logProcessingDAO.getLog(entity.getCampaignId(), CampaignEntity.class, null, entity);
            logEntities.add(log);
        }
        logProcessingDAO.insertAll(logEntities);
    }

    @SuppressWarnings("unchecked")
    public void update(CampaignEntity campaignEntity) {
        Long id = campaignEntity.getCampaignId();
        Query query = new Query();
        query.addCriteria(Criteria.where("campaignId").is(id));
        Update update = new Update();
        DataOperationLogEntity log = null;
        try {
            Class _class = campaignEntity.getClass();
            Field[] fields = _class.getDeclaredFields();//get object's fields by reflect
            for (Field field : fields) {
                String fieldName = field.getName();
                if ("campaignId".equals(fieldName))
                    continue;
                StringBuilder fieldGetterName = new StringBuilder("get");
                fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
                Method method = _class.getDeclaredMethod(fieldGetterName.toString());
                Object after = method.invoke(campaignEntity);
                if (after != null) {
                    update.set(field.getName(), after);
                    Object before = method.invoke(findOne(id));
                    log = logProcessingDAO.getLog(id, CampaignEntity.class, new DataAttributeInfoEntity(field.getName(), before, after), null);
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
        mongoTemplate.updateFirst(query, update, CampaignEntity.class);
        logProcessingDAO.insert(log);
    }

    public void update(List<CampaignEntity> entities) {
        for (CampaignEntity entity : entities)
            update(entity);
    }

    public void deleteById(Long campaignId) {
        mongoTemplate.remove(
                new Query(Criteria.where("campaignId").is(campaignId)), CampaignEntity.class, "CampaginType");
        deleteSub(Arrays.asList(new Long[]{campaignId}));
    }

    public void deleteByIds(List<Long> campaignIds) {
        mongoTemplate.remove(
                new Query(Criteria.where("campaignId").in(campaignIds)), CampaignEntity.class, "CampaginType");
        deleteSub(campaignIds);
    }

    public void delete(CampaignEntity campaignEntity) {
        deleteById(campaignEntity.getCampaignId());
    }

    public void deleteAll() {
        mongoTemplate.dropCollection("CampaginType");
        deleteSub(getAllCampaignId());
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String,Object> q,int orderBy) {
        return null;
    }

    private List<Long> getAdgroupIdByCampaignId(List<Long> campaignIds) {
        Query query = new BasicQuery("{}", "{adgroupId : 1}");
        query.addCriteria(Criteria.where("campaignId").in(campaignIds));
        List<AdgroupEntity> list = mongoTemplate.find(query, AdgroupEntity.class, "AdgroupType");
        List<Long> adgroupIds = new ArrayList<>(list.size());
        for (AdgroupEntity type : list)
            adgroupIds.add(type.getAdgroupId());
        return adgroupIds;
    }

    //删除下级内容
    private void deleteSub(List<Long> campaignIds) {
        List<Long> adgroupIds = getAdgroupIdByCampaignId(campaignIds);
        mongoTemplate.remove(new Query(Criteria.where("adgroupId").in(adgroupIds)), AdgroupEntity.class, "AdgroupType");
        mongoTemplate.remove(new Query(Criteria.where("adgroupId").in(adgroupIds)), KeywordEntity.class, "KeywordType");
        mongoTemplate.remove(new Query(Criteria.where("adgroupId").in(adgroupIds)), CreativeEntity.class, "CreativeType");
        List<DataOperationLogEntity> logEntities = new LinkedList<>();
        for (Long id : campaignIds) {
            DataOperationLogEntity log = logProcessingDAO.getLog(id, CampaignEntity.class, null, null);
            logEntities.add(log);
        }
        logProcessingDAO.insertAll(logEntities);
    }

}
