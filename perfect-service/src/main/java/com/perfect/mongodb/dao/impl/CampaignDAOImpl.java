package com.perfect.mongodb.dao.impl;

import com.google.common.collect.Lists;
import com.perfect.constants.LogStatusConstant;
import com.perfect.core.AppContext;
import com.perfect.dao.CampaignBackUpDAO;
import com.perfect.dao.CampaignDAO;
import com.perfect.dao.LogDAO;
import com.perfect.dao.LogProcessingDAO;
import com.perfect.entity.*;
import com.perfect.entity.backup.CampaignBackUpEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.mongodb.utils.EntityConstants;
import com.perfect.mongodb.utils.Pager;
import com.perfect.mongodb.utils.PagerInfo;
import com.perfect.utils.LogUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
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
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by baizz on 2014-07-03.
 */
@Repository("campaignDAO")
public class CampaignDAOImpl extends AbstractUserBaseDAOImpl<CampaignEntity, Long> implements CampaignDAO {

    @Resource
    private LogProcessingDAO logProcessingDAO;

    @Resource
    private CampaignBackUpDAO campaignBackUpDAO;

    @Resource
    private LogDAO logDAO;

    public List<Long> getAllCampaignId() {
        MongoTemplate mongoTemplate = getMongoTemplate();
        Query query = new BasicQuery("{}", "{" + CAMPAIGN_ID + " : 1}");
        List<CampaignEntity> list = mongoTemplate.find(query, CampaignEntity.class, TBL_CAMPAIGN);

        List<Long> campaignIds = new ArrayList<>(list.size());
        for (CampaignEntity type : list)
            campaignIds.add(type.getCampaignId());
        return campaignIds;
    }

    public CampaignEntity findOne(Long campaignId) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        CampaignEntity campaignEntity = mongoTemplate.findOne(
                new Query(Criteria.where(CAMPAIGN_ID).is(campaignId)),
                CampaignEntity.class,
                TBL_CAMPAIGN);
        return campaignEntity;
    }


    //xj
    public  CampaignEntity findCampaignByName(String name){
        MongoTemplate mongoTemplate = getMongoTemplate();
       List<CampaignEntity> campaignEntityList = mongoTemplate.find(new Query(Criteria.where(EntityConstants.ACCOUNT_ID).is(AppContext.getAccountId()).and("name").is(name)),getEntityClass(),EntityConstants.TBL_CAMPAIGN);
        return campaignEntityList.size()==0?null:campaignEntityList.get(0);
    }

    public List<CampaignEntity> findAll() {
        MongoTemplate mongoTemplate = getMongoTemplate();
        List<CampaignEntity> list = mongoTemplate.find(Query.query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId())), getEntityClass());
        return list;
    }

    public List<CampaignEntity> find(Map<String, Object> params, int skip, int limit) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        Query query = new Query();
        if (params != null && params.size() > 0) {
            Criteria criteria = Criteria.where(CAMPAIGN_ID).ne(null);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                criteria.and(entry.getKey()).is(entry.getValue());
            }
            query.addCriteria(criteria);
        }
        query.with(new PageRequest(skip, limit));
        List<CampaignEntity> list = mongoTemplate.find(query, CampaignEntity.class, TBL_CAMPAIGN);
        return list;
    }

    //x
    public List<CampaignEntity> find(Query query) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        return mongoTemplate.find(query, CampaignEntity.class);
    }

    /**
     * 得到所有已经和百度同步的推广计划
     *
     * @return
     */
    public List<CampaignEntity> findAllDownloadCampaign() {
        MongoTemplate mongoTemplate = getMongoTemplate();
        Aggregation aggregation = newAggregation(
                match(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and(CAMPAIGN_ID).ne(null)),
                project(ACCOUNT_ID, CAMPAIGN_ID, NAME).andExclude(SYSTEM_ID)
        );
        AggregationResults<CampaignEntity> results = mongoTemplate.aggregate(aggregation, TBL_CAMPAIGN, getEntityClass());
        return Lists.newArrayList(results.iterator());
    }

    @Override
    public CampaignEntity findByObjectId(String oid) {
        return getMongoTemplate().findOne(Query.query(Criteria.where(SYSTEM_ID).is(oid)), getEntityClass());
    }


    public void insert(CampaignEntity campaignEntity) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        mongoTemplate.insert(campaignEntity, TBL_CAMPAIGN);
        DataOperationLogEntity log = LogUtils.getLog(campaignEntity.getCampaignId(), CampaignEntity.class, null, campaignEntity);
        logProcessingDAO.insert(log);
    }


    /**
     * 插入推广计划并返回该id
     * @param campaignEntity
     * @return
     */
    public String insertReturnId(CampaignEntity campaignEntity) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        mongoTemplate.insert(campaignEntity, TBL_CAMPAIGN);
        DataOperationLogEntity log = LogUtils.getLog(campaignEntity.getCampaignId(), CampaignEntity.class, null, campaignEntity);
        logProcessingDAO.insert(log);
        return campaignEntity.getId();
    }


    public void insertAll(List<CampaignEntity> entities) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        mongoTemplate.insertAll(entities);
        List<DataOperationLogEntity> logEntities = new LinkedList<>();
        for (CampaignEntity entity : entities) {
            DataOperationLogEntity log = LogUtils.getLog(entity.getCampaignId(), CampaignEntity.class, null, entity);
            logEntities.add(log);
        }
        logProcessingDAO.insertAll(logEntities);
    }

    /**
     * 推广计划的软删除
     * @param cid
     */
    public void updateLocalstatu(long cid){
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Update update = new Update();
        update.set("ls","");
        mongoTemplate.updateFirst(new Query(Criteria.where(EntityConstants.CAMPAIGN_ID).is(cid)),update,EntityConstants.TBL_CAMPAIGN);
    }

    @SuppressWarnings("unchecked")
    public void update(CampaignEntity campaignEntity) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        Long id = campaignEntity.getCampaignId();
        Query query = new Query();
        query.addCriteria(Criteria.where(CAMPAIGN_ID).is(id));
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
                    log = LogUtils.getLog(id, CampaignEntity.class,
                            new DataAttributeInfoEntity(field.getName(), before, after), null);
                    break;
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        getMongoTemplate().updateFirst(query, update, CampaignEntity.class, TBL_CAMPAIGN);
        logProcessingDAO.insert(log);
    }


    /**
     * 根据mongoID修改计划
     *
     * @param campaignEntity
     */
    @Override
    public void updateByMongoId(CampaignEntity newCampaign,CampaignEntity campaignEntity) {
        Long id = newCampaign.getCampaignId();
        Query query = new Query();
        query.addCriteria(Criteria.where(EntityConstants.SYSTEM_ID).is(newCampaign.getId()));
        Update update = new Update();
        try {
            Class _class = newCampaign.getClass();
            Field[] fields = _class.getDeclaredFields();//get object's fields by reflect
            for (Field field : fields) {
                String fieldName = field.getName();
                if ("id".equals(fieldName))
                    continue;
                StringBuilder fieldGetterName = new StringBuilder("get");
                fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
                Method method = _class.getDeclaredMethod(fieldGetterName.toString());
                Object after = method.invoke(newCampaign);
                if (after != null) {
                    update.set(field.getName(), after);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        getMongoTemplate().updateFirst(query, update, CampaignEntity.class, TBL_CAMPAIGN);

         CampaignBackUpEntity campaignBackUpEntityFind = campaignBackUpDAO.findByObjectId(newCampaign.getId());
        if (campaignBackUpEntityFind==null&&newCampaign.getLocalStatus()==2) {
            CampaignBackUpEntity backUpEntity = new CampaignBackUpEntity();
            BeanUtils.copyProperties(campaignEntity, backUpEntity);
            campaignBackUpDAO.insert(backUpEntity);
        }
        logDAO.insertLog(id, LogStatusConstant.ENTITY_CAMPAIGN, LogStatusConstant.OPT_UPDATE);
    }


    public void update(List<CampaignEntity> entities) {
        for (CampaignEntity entity : entities)
            update(entity);
    }

    public void deleteById(final Long campaignId) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        mongoTemplate.remove(
                new Query(Criteria.where(CAMPAIGN_ID).is(campaignId)), CampaignEntity.class, TBL_CAMPAIGN);
        deleteSub(new ArrayList<Long>(1) {{
            add(campaignId);
        }});
    }

    /**
     * 根据mongoid删除计划
     *
     * @param campaignId
     */
    public void deleteByMongoId(final String campaignId) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        mongoTemplate.remove(
                new Query(Criteria.where(EntityConstants.SYSTEM_ID).is(campaignId)), CampaignEntity.class, TBL_CAMPAIGN);
       /* deleteSub(new ArrayList<Long>(1) {{
            add(campaignId);
        }});*/
    }

    /**
     * 软删除计划
     * @param id
     */
    public void softDel(final String id) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        Update update = new Update();
        update.set("ls",3);
        mongoTemplate.updateFirst(new Query(Criteria.where(EntityConstants.SYSTEM_ID).is(id)),update,EntityConstants.TBL_CAMPAIGN);

    }

    public void deleteByIds(List<Long> campaignIds) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        mongoTemplate.remove(
                new Query(Criteria.where(CAMPAIGN_ID).in(campaignIds)), CampaignEntity.class, TBL_CAMPAIGN);
        deleteSub(campaignIds);
    }

    @Override
    public Class<CampaignEntity> getEntityClass() {
        return CampaignEntity.class;
    }

    @Override
    public String getId() {
        return CAMPAIGN_ID;
    }

    public void delete(CampaignEntity campaignEntity) {
        deleteById(campaignEntity.getCampaignId());
    }

    public void deleteAll() {
        MongoTemplate mongoTemplate = getMongoTemplate();
        mongoTemplate.dropCollection(TBL_CAMPAIGN);
        deleteSub(getAllCampaignId());
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }


    //xj
    @Override
    public PagerInfo findByPageInfo(Query q, int pageSize, int pageNo) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        int totalCount = getListTotalCount(q);

        PagerInfo p = new PagerInfo(pageNo, pageSize, totalCount);
        q.skip(p.getFirstStation());
        q.limit(p.getPageSize());
        q.with(new Sort("name"));
        if (totalCount<1) {
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
        return (int) mongoTemplate.count(q, EntityConstants.TBL_CAMPAIGN);
    }


    private List<Long> getAdgroupIdByCampaignId(List<Long> campaignIds) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        Query query = new BasicQuery("{}", "{" + ADGROUP_ID + " : 1}");
        query.addCriteria(Criteria.where(CAMPAIGN_ID).in(campaignIds));
        List<AdgroupEntity> list = mongoTemplate.find(query, AdgroupEntity.class, TBL_ADGROUP);
        List<Long> adgroupIds = new ArrayList<>(list.size());
        for (AdgroupEntity type : list)
            adgroupIds.add(type.getAdgroupId());
        return adgroupIds;
    }

    //删除下级内容
    private void deleteSub(List<Long> campaignIds) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        List<Long> adgroupIds = getAdgroupIdByCampaignId(campaignIds);
        mongoTemplate.remove(new Query(Criteria.where(ADGROUP_ID).in(adgroupIds)), AdgroupEntity.class, TBL_ADGROUP);
        mongoTemplate.remove(new Query(Criteria.where(ADGROUP_ID).in(adgroupIds)), KeywordEntity.class, TBL_KEYWORD);
        mongoTemplate.remove(new Query(Criteria.where(ADGROUP_ID).in(adgroupIds)), CreativeEntity.class, TBL_CREATIVE);

        List<DataOperationLogEntity> logEntities = new LinkedList<>();
        for (Long id : campaignIds) {
            DataOperationLogEntity log = LogUtils.getLog(id, CampaignEntity.class, null, null);
            logEntities.add(log);
        }
        logProcessingDAO.insertAll(logEntities);
    }

}
