package com.perfect.db.mongodb.impl;

import com.perfect.commons.constants.LogStatusConstant;
import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.core.AppContext;
import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.CampaignBackUpDAO;
import com.perfect.dao.CampaignDAO;
import com.perfect.dao.LogDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.backup.CampaignBackUpDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.entity.*;
import com.perfect.entity.backup.CampaignBackUpEntity;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.Pager;
import com.perfect.utils.PagerInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import java.util.List;
import java.util.Map;

import static com.perfect.commons.constants.MongoEntityConstants.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by baizz on 2014-07-03.
 */
@Repository("campaignDAO")
public class CampaignDAOImpl extends AbstractUserBaseDAOImpl<CampaignDTO, Long> implements CampaignDAO {

    @Resource
    private CampaignBackUpDAO campaignBackUpDAO;

    @Resource
    private AdgroupDAO adgroupDAO;

    @Resource
    private LogDAO logDAO;

    public List<Long> getAllCampaignId() {
        Query query = new BasicQuery("{}", "{" + MongoEntityConstants.CAMPAIGN_ID + " : 1}");
        List<CampaignEntity> list = getMongoTemplate().find(query, CampaignEntity.class, MongoEntityConstants.TBL_CAMPAIGN);

        List<Long> campaignIds = new ArrayList<>(list.size());
        for (CampaignEntity type : list)
            campaignIds.add(type.getCampaignId());
        return campaignIds;
    }

    public CampaignDTO findOne(Long campaignId) {
        CampaignEntity campaignEntity = getMongoTemplate().findOne(
                new Query(Criteria.where(MongoEntityConstants.CAMPAIGN_ID).is(campaignId)),
                CampaignEntity.class,
                MongoEntityConstants.TBL_CAMPAIGN);

        CampaignDTO campaignDTO = new CampaignDTO();
        BeanUtils.copyProperties(campaignEntity,campaignDTO);
        return campaignDTO;
    }


    //xj
    public CampaignDTO findCampaignByName(String name) {
        List<CampaignEntity> campaignEntityList = getMongoTemplate().find(new Query(Criteria.where(MongoEntityConstants.ACCOUNT_ID).is(AppContext.getAccountId()).and("name").is(name)), getCampaignEntityClass(), MongoEntityConstants.TBL_CAMPAIGN);

        CampaignEntity campaignEntity = campaignEntityList.size() == 0 ? null : campaignEntityList.get(0);

        CampaignDTO campaignDTO = new CampaignDTO();
        BeanUtils.copyProperties(campaignEntity,campaignDTO);
        return campaignDTO;
    }

    public List<CampaignDTO> findAll() {
        List<CampaignEntity> list = getMongoTemplate().find(Query.query(Criteria.where(MongoEntityConstants.ACCOUNT_ID).is(AppContext.getAccountId())), getCampaignEntityClass());

        List<CampaignDTO> campaignDTOs = ObjectUtils.convert(list,CampaignDTO.class);
        return campaignDTOs;
    }

    public List<CampaignDTO> find(Map<String, Object> params, int skip, int limit) {
        Query query = new Query();
        if (params != null && params.size() > 0) {
            Criteria criteria = Criteria.where(MongoEntityConstants.CAMPAIGN_ID).ne(null);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                criteria.and(entry.getKey()).is(entry.getValue());
            }
            query.addCriteria(criteria);
        }
        query.with(new PageRequest(skip, limit));
        List<CampaignEntity> list = getMongoTemplate().find(query, CampaignEntity.class, MongoEntityConstants.TBL_CAMPAIGN);

        List<CampaignDTO> campaignDTOs = ObjectUtils.convert(list,CampaignDTO.class);
        return campaignDTOs;
    }

    //x
    public List<CampaignDTO> find(Query query) {

        List<CampaignEntity> campaignEntities = getMongoTemplate().find(query, CampaignEntity.class);

        List<CampaignDTO> campaignDTOs = ObjectUtils.convert(campaignEntities,CampaignDTO.class);
        return campaignDTOs;
    }

    /**
     * 得到所有已经和百度同步的推广计划
     *
     * @return
     */
    public List<CampaignDTO> findAllDownloadCampaign() {
        Aggregation aggregation = newAggregation(
                match(Criteria.where(MongoEntityConstants.ACCOUNT_ID).is(AppContext.getAccountId()).and(CAMPAIGN_ID).ne(null)),
                project(MongoEntityConstants.ACCOUNT_ID, CAMPAIGN_ID, NAME).andExclude(SYSTEM_ID)
        );
        AggregationResults<CampaignEntity> results = getMongoTemplate().aggregate(aggregation, MongoEntityConstants.TBL_CAMPAIGN, getCampaignEntityClass());

        List<CampaignDTO> campaignDTOs = ObjectUtils.convert(results.getMappedResults(),CampaignDTO.class);
        return campaignDTOs;
    }

    @Override
    public CampaignDTO findByObjectId(String oid) {
        CampaignEntity campaignEntity = getMongoTemplate().findOne(Query.query(Criteria.where(SYSTEM_ID).is(oid)), getCampaignEntityClass());
        CampaignDTO campaignDTO = new CampaignDTO();
        BeanUtils.copyProperties(campaignEntity,campaignDTO);
        return campaignDTO;
    }


    public void insert(CampaignDTO campaignDTO) {
        CampaignEntity campaignEntity = new CampaignBackUpEntity();
        BeanUtils.copyProperties(campaignDTO, campaignEntity);
        getMongoTemplate().insert(campaignEntity, MongoEntityConstants.TBL_CAMPAIGN);
    }


    /**
     * 插入推广计划并返回该id
     *
     * @param campaignDTO
     * @return
     */
    public String insertReturnId(CampaignDTO campaignDTO) {

        CampaignEntity campaignEntity = new CampaignBackUpEntity();
        BeanUtils.copyProperties(campaignDTO,campaignEntity);

        getMongoTemplate().insert(campaignEntity, MongoEntityConstants.TBL_CAMPAIGN);
        return campaignEntity.getId();
    }


    public void insertAll(List<CampaignDTO> entities) {

        List<CampaignEntity> campaignEntities = ObjectUtils.convert(entities,CampaignEntity.class);
        getMongoTemplate().insertAll(campaignEntities);
    }

    /**
     * 推广计划的软删除
     *
     * @param cid
     */
    public void updateLocalstatu(long cid) {
        getMongoTemplate().updateFirst(new Query(Criteria.where(MongoEntityConstants.CAMPAIGN_ID).is(cid)), Update.update("ls",""), MongoEntityConstants.TBL_CAMPAIGN);
        List<AdgroupDTO> list = adgroupDAO.findByCampaignId(cid);
        for (AdgroupDTO adgroupDTO : list) {
            adgroupDAO.delBack(adgroupDTO.getAdgroupId());
        }

    }

    @SuppressWarnings("unchecked")
    public void update(CampaignDTO campaignDTO) {
        Long id = campaignDTO.getCampaignId();
        Query query = new Query();
        query.addCriteria(Criteria.where(MongoEntityConstants.CAMPAIGN_ID).is(id));
        Update update = new Update();
        DataOperationLogEntity log = null;
        try {
            Class _class = campaignDTO.getClass();
            Field[] fields = _class.getDeclaredFields();//get object's fields by reflect
            for (Field field : fields) {
                String fieldName = field.getName();
                if ("campaignId".equals(fieldName))
                    continue;
                StringBuilder fieldGetterName = new StringBuilder("get");
                fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
                Method method = _class.getDeclaredMethod(fieldGetterName.toString());
                Object after = method.invoke(campaignDTO);
                if (after != null) {
                    update.set(field.getName(), after);
                    Object before = method.invoke(findOne(id));
                    break;
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        getMongoTemplate().updateFirst(query, update, getCampaignEntityClass(), MongoEntityConstants.TBL_CAMPAIGN);
    }


    /**
     * 根据mongoID修改计划
     *
     * @param campaignEntity
     */
    @Override
    public void updateByMongoId(CampaignDTO newCampaign, CampaignDTO campaignEntity) {
        Long id = newCampaign.getCampaignId();
        Query query = new Query();
        query.addCriteria(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(newCampaign.getId()));
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
        getMongoTemplate().updateFirst(query, update, CampaignEntity.class, MongoEntityConstants.TBL_CAMPAIGN);

        CampaignBackUpDTO campaignBackUpDTOFind = campaignBackUpDAO.findByObjectId(newCampaign.getId());
        if (campaignBackUpDTOFind == null && newCampaign.getLocalStatus() == 2) {
            CampaignBackUpEntity backUpEntity = new CampaignBackUpEntity();
            BeanUtils.copyProperties(campaignEntity, backUpEntity);
            getMongoTemplate().insert(backUpEntity);
        }
        logDAO.insertLog(id, LogStatusConstant.ENTITY_CAMPAIGN, LogStatusConstant.OPT_UPDATE);
    }


    public void update(List<CampaignDTO> entities) {
        for (CampaignDTO entity : entities)
            update(entity);
    }

    public void deleteById(final Long campaignId) {
        getMongoTemplate().remove(
                new Query(Criteria.where(MongoEntityConstants.CAMPAIGN_ID).is(campaignId)), CampaignEntity.class, MongoEntityConstants.TBL_CAMPAIGN);
        deleteSub(new ArrayList<Long>(1) {{
            add(campaignId);
        }});
    }

    /**
     * 根据mongoid硬删除计划
     *
     * @param campaignId
     */
    public void deleteByMongoId(final String campaignId) {
        getMongoTemplate().remove(
                new Query(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(campaignId)), CampaignEntity.class, MongoEntityConstants.TBL_CAMPAIGN);
        deleteSubByObjectId(new ArrayList<String>(1) {{
            add(campaignId);
        }});
    }

    /**
     * 根据计划id软删除
     *
     * @param cid
     */
    public void softDel(long cid) {
        getMongoTemplate().updateFirst(new Query(Criteria.where(MongoEntityConstants.CAMPAIGN_ID).is(cid)), Update.update("localStatus", 3), getEntityClass(), MongoEntityConstants.TBL_CAMPAIGN);

        List<AdgroupDTO> list = adgroupDAO.findByCampaignId(cid);
        List<Long> ids = new ArrayList<>();
        for (AdgroupDTO adgroupDTO : list) {
            ids.add(adgroupDTO.getAdgroupId());
        }
        adgroupDAO.deleteLinkedByAgid(ids);
    }


    public void deleteByIds(List<Long> campaignIds) {
        getMongoTemplate().remove(
                new Query(Criteria.where(MongoEntityConstants.CAMPAIGN_ID).in(campaignIds)), CampaignEntity.class, MongoEntityConstants.TBL_CAMPAIGN);
        deleteSub(campaignIds);
    }

    @Override
    public Class<CampaignDTO> getEntityClass() {
        return CampaignDTO.class;
    }

    public Class<CampaignEntity> getCampaignEntityClass() {
        return CampaignEntity.class;
    }

    @Override
    public String getId() {
        return MongoEntityConstants.CAMPAIGN_ID;
    }

    public void delete(CampaignDTO campaignDTO) {
        deleteById(campaignDTO.getCampaignId());
    }

    public void deleteAll() {
        getMongoTemplate().dropCollection(MongoEntityConstants.TBL_CAMPAIGN);
        deleteSub(getAllCampaignId());
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }


    //xj
    @Override
    public PagerInfo findByPageInfo(Query q, int pageSize, int pageNo) {
        int totalCount = getListTotalCount(q);

        PagerInfo p = new PagerInfo(pageNo, pageSize, totalCount);
        q.skip(p.getFirstStation());
        q.limit(p.getPageSize());
        q.with(new Sort("name"));
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        List list = getMongoTemplate().find(q, getEntityClass());
        p.setList(list);
        return p;
    }

    //xj
    public int getListTotalCount(Query q) {
        return (int) getMongoTemplate().count(q, (MongoEntityConstants.TBL_CAMPAIGN));
    }


    private List<Long> getAdgroupIdByCampaignId(List<Long> campaignIds) {
        Query query = new BasicQuery("{}", "{" + MongoEntityConstants.ADGROUP_ID + " : 1}");
        query.addCriteria(Criteria.where(MongoEntityConstants.CAMPAIGN_ID).in(campaignIds));
        List<AdgroupEntity> list = getMongoTemplate().find(query, AdgroupEntity.class, MongoEntityConstants.TBL_ADGROUP);
        List<Long> adgroupIds = new ArrayList<>(list.size());
        for (AdgroupEntity type : list)
            adgroupIds.add(type.getAdgroupId());
        return adgroupIds;
    }

    //删除下级内容
    private void deleteSub(List<Long> campaignIds) {
        List<Long> adgroupIds = getAdgroupIdByCampaignId(campaignIds);
        getMongoTemplate().remove(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).in(adgroupIds)), AdgroupEntity.class, MongoEntityConstants.TBL_ADGROUP);
        getMongoTemplate().remove(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).in(adgroupIds)), KeywordEntity.class, MongoEntityConstants.TBL_KEYWORD);
        getMongoTemplate().remove(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).in(adgroupIds)), CreativeEntity.class, MongoEntityConstants.TBL_CREATIVE);

    }


    //根据mongodbId删除下级内容
    private void deleteSubByObjectId(List<String> ids) {
        List<String> adgroupIds = adgroupDAO.getObjAdgroupIdByCampaignId(ids);
        getMongoTemplate().remove(new Query(Criteria.where(MongoEntityConstants.SYSTEM_ID).in(adgroupIds)), AdgroupEntity.class, MongoEntityConstants.TBL_ADGROUP);
        getMongoTemplate().remove(new Query(Criteria.where(MongoEntityConstants.OBJ_ADGROUP_ID).in(adgroupIds)), KeywordEntity.class, MongoEntityConstants.TBL_KEYWORD);
        getMongoTemplate().remove(new Query(Criteria.where(MongoEntityConstants.OBJ_ADGROUP_ID).in(adgroupIds)), CreativeEntity.class, MongoEntityConstants.TBL_CREATIVE);
    }

}
