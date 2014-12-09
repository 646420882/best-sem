package com.perfect.db.mongodb.impl;

import com.mongodb.WriteResult;
import com.perfect.commons.constants.LogStatusConstant;
import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.core.AppContext;
import com.perfect.dao.adgroup.AdgroupBackUpDAO;
import com.perfect.dao.adgroup.AdgroupDAO;
import com.perfect.dao.sys.LogDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.backup.AdgroupBackupDTO;
import com.perfect.entity.adgroup.AdgroupEntity;
import com.perfect.entity.backup.AdgroupBackUpEntity;
import com.perfect.entity.creative.CreativeEntity;
import com.perfect.entity.keyword.KeywordEntity;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.paging.PagerInfo;
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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by vbzer_000 on 2014-07-02.
 */
@Repository("adgroupDAO")
public class AdgroupDAOImpl extends AbstractUserBaseDAOImpl<AdgroupDTO, Long> implements AdgroupDAO {

    @Override
    public String getId() {
        return MongoEntityConstants.ADGROUP_ID;
    }

    public String get_id() {
        return MongoEntityConstants.SYSTEM_ID;
    }

    @Resource
    private AdgroupBackUpDAO adgroupBackUpDAO;

    public List<Long> getAllAdgroupId() {
        Query query = new BasicQuery("{}", "{" + MongoEntityConstants.ADGROUP_ID + " : 1}");
        List<AdgroupEntity> list = getMongoTemplate().find(query, getEntityClass());
        List<Long> adgroupIds = new ArrayList<>(list.size());
        for (AdgroupEntity type : list)
            adgroupIds.add(type.getAdgroupId());
        return adgroupIds;
    }

    public List<Long> getAdgroupIdByCampaignId(Long campaignId) {
        Query query = new BasicQuery("{}", "{" + MongoEntityConstants.ADGROUP_ID + " : 1}");
        query.addCriteria(Criteria.where(MongoEntityConstants.CAMPAIGN_ID).is(campaignId));
        List<AdgroupEntity> list = getMongoTemplate().find(query, getEntityClass());
        List<Long> adgroupIds = new ArrayList<>(list.size());
        for (AdgroupEntity type : list)
            adgroupIds.add(type.getAdgroupId());
        return adgroupIds;
    }


    //xj根据单元
    public AdgroupDTO getByCampaignIdAndName(Long campaignId, String name) {
        List<AdgroupEntity> adgroupEntityList = getMongoTemplate().find(new Query(Criteria.where(MongoEntityConstants.ACCOUNT_ID).is(AppContext.getAccountId()).and(MongoEntityConstants.CAMPAIGN_ID).is(campaignId).and("name").is(name)), getEntityClass());
        AdgroupEntity adgroupEntity = adgroupEntityList.size() == 0 ? null : adgroupEntityList.get(0);
        return wrapperObject(adgroupEntity);
    }

    @Override
    public List<String> getAdgroupIdByCampaignId(String campaignId) {
        Query query = new BasicQuery("{}", "{_id : 1}");
        query.addCriteria(Criteria.where(MongoEntityConstants.OBJ_CAMPAIGN_ID).is(campaignId));
        List<AdgroupEntity> list = getMongoTemplate().find(query, getEntityClass());
        List<String> adgroupIds = new ArrayList<>(list.size());
        for (AdgroupEntity type : list)
            adgroupIds.add(type.getId());
        return adgroupIds;
    }

    @Override
    public List<AdgroupDTO> findByCampaignOId(String id) {
        List<AdgroupEntity> adgroupEntities= getMongoTemplate().find(new Query(Criteria.where(MongoEntityConstants.OBJ_CAMPAIGN_ID).is(id)), getEntityClass());
        return wrapperList(adgroupEntities);
    }

    public List<String> getObjAdgroupIdByCampaignId(List<String> cids) {
        Query query = new BasicQuery("{}", "{" + MongoEntityConstants.OBJ_ADGROUP_ID + " : 1}");
        query.addCriteria(Criteria.where(MongoEntityConstants.OBJ_CAMPAIGN_ID).in(cids));
        List<AdgroupEntity> list = getMongoTemplate().find(query, getEntityClass());
        List<String> adgroupIds = new ArrayList<>(list.size());
        for (AdgroupEntity type : list)
            adgroupIds.add(type.getId());
        return adgroupIds;
    }


    public List<AdgroupDTO> getAdgroupByCampaignId(Long campaignId, Map<String, Object> params, int skip, int limit) {
        Query query = new Query();
        Criteria criteria = Criteria.where(MongoEntityConstants.CAMPAIGN_ID).is(campaignId);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet())
                criteria.and(entry.getKey()).is(entry.getValue());
        }
        query.addCriteria(criteria);
        query.with(new PageRequest(skip, limit));
        List<AdgroupEntity> _list = getMongoTemplate().find(query, getEntityClass());
        return wrapperList(_list);
    }


    public List<AdgroupDTO> getAdgroupByCampaignObjId(String campaignObjId) {
        List<AdgroupEntity> adgroupEntities= getMongoTemplate().find(Query.query(Criteria.where(MongoEntityConstants.OBJ_CAMPAIGN_ID).is(campaignObjId)), getEntityClass());
        return wrapperList(adgroupEntities);
    }

    @Override
    public List<AdgroupDTO> getAdgroupByCampaignId(Long campaignId) {
        List<AdgroupEntity> adgroupDTOList = getMongoTemplate().find(Query.query(Criteria.where(MongoEntityConstants.CAMPAIGN_ID).is(campaignId)), getEntityClass());
        return wrapperList(adgroupDTOList);
    }

    public AdgroupDTO findOne(Long adgroupId) {
        AdgroupEntity _adgroupEntity = getMongoTemplate().findOne(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).is(adgroupId)), getEntityClass());
        return wrapperObject(_adgroupEntity);
    }

    public List<AdgroupDTO> findAll() {
        List<AdgroupEntity> adgroupEntities = getMongoTemplate().find(Query.query(Criteria.where(MongoEntityConstants.ACCOUNT_ID).is(AppContext.getAccountId())), getEntityClass());
        return wrapperList(adgroupEntities);
    }

    @Override
    public List<AdgroupDTO> find(Map<String, Object> params, int skip, int limit, String sort, boolean asc) {
        return null;
    }

    /**
     * 条件查询, 分页
     *
     * @param params
     * @param skip
     * @param limit
     * @return
     */
    public List<AdgroupDTO> find(Map<String, Object> params, int skip, int limit) {
        Query query = new Query();
        if (params != null && params.size() > 0) {
            Criteria criteria = new Criteria();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                criteria.and(entry.getKey()).is(entry.getValue());
            }
            query.addCriteria(criteria);
        }
        query.with(new PageRequest(skip, limit, new Sort(Sort.Direction.DESC, "price")));
        List<AdgroupEntity> list = getMongoTemplate().find(query, getEntityClass());
        return wrapperList(list);
    }


    @Override
    public List<AdgroupDTO> findByCampaignId(Long cid) {
        List<AdgroupEntity> adgroupEntities= getMongoTemplate().find(Query.query(Criteria.where(MongoEntityConstants.CAMPAIGN_ID).is(cid)), getEntityClass());
        return wrapperList(adgroupEntities);
    }

    @Override
    public List<AdgroupDTO> findIdByCampaignId(Long cid) {
        Query query = new BasicQuery("{}", "{ " + MongoEntityConstants.ADGROUP_ID + " : 1 }");
        List<AdgroupEntity> list = getMongoTemplate().find(query.addCriteria(Criteria.where(MongoEntityConstants.CAMPAIGN_ID).is(cid)), getEntityClass());
        return wrapperList(list);
    }

    @Override
    public AdgroupDTO findByObjId(String oid) {
        AdgroupEntity adgroupEntity = getMongoTemplate().findOne(Query.query(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(oid)), getEntityClass());
        return wrapperObject(adgroupEntity);
    }

    @Override
    public AdgroupDTO fndEntity(Map<String, Object> params) {
        Query q = new Query();
        Criteria c = new Criteria();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> par : params.entrySet()) {
                c.and(par.getKey()).is(par.getValue());
            }
        }
        q.addCriteria(c);
        AdgroupEntity adgroupEntity = getMongoTemplate().findOne(q, getEntityClass());
        return ObjectUtils.convert(adgroupEntity,AdgroupDTO.class);
    }

    @Override
    public Object insertOutId(AdgroupDTO adgroupEntity) {
        AdgroupEntity adgroupEntityInsert = new AdgroupEntity();
        BeanUtils.copyProperties(adgroupEntity, adgroupEntityInsert);
        getMongoTemplate().insert(adgroupEntityInsert);
        logDAO.insertLog(adgroupEntity.getId(), LogStatusConstant.ENTITY_ADGROUP);
        return adgroupEntityInsert.getId();
    }

    /**
     * 连到单元下的关键字和创意一起删了
     *
     * @param oid
     */
    @Override
    public void deleteByObjId(final String oid) {
        getMongoTemplate().remove(Query.query(Criteria.where(get_id()).is(oid)), getEntityClass());
        deleteSubOid(Arrays.asList(oid));

    }

    @Override
    public void deleteByObjId(Long adgroupId) {
        Update update = new Update();
        update.set("ls", "");
        getMongoTemplate().updateFirst(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).is(adgroupId)), update, getEntityClass());
        deleteLinked(adgroupId);
        //以前是直接删除拉取到本地的数据，是硬删除，现在改为软删除，以便以后还原操作
//        mongoTemplate.remove(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).is(adgroupId)),getEntityClass(),MongoEntityConstants.TBL_ADGROUP);
        logDAO.insertLog(adgroupId, LogStatusConstant.ENTITY_ADGROUP, LogStatusConstant.OPT_DELETE);
    }

    @Override
    public void updateCampaignIdByOid(String oid, Long campaignId) {
        WriteResult wr = getMongoTemplate().updateMulti(Query.query(Criteria.where(MongoEntityConstants.OBJ_CAMPAIGN_ID).is(oid)),
                Update.update(MongoEntityConstants.CAMPAIGN_ID, campaignId).set(MongoEntityConstants.OBJ_CAMPAIGN_ID, null), getEntityClass());
    }

    @Override

    public void updateByObjId(AdgroupDTO adgroupEntity) {
        Update up = new Update();
        up.set("name", adgroupEntity.getAdgroupName());
        up.set("max", adgroupEntity.getMaxPrice());
        up.set("neg", adgroupEntity.getNegativeWords());
        up.set("exneg", adgroupEntity.getExactNegativeWords());
        up.set("p", adgroupEntity.getPause());
        up.set("s", adgroupEntity.getStatus());
        up.set("m", adgroupEntity.getMib());
        getMongoTemplate().updateFirst(new Query(Criteria.where(get_id()).is(adgroupEntity.getId())), up, getEntityClass());
        logDAO.insertLog(adgroupEntity.getId(), LogStatusConstant.ENTITY_ADGROUP);
    }


    @Override
    public void update(AdgroupDTO adgroupDTO, AdgroupDTO bakadgroupDTO) {
        Long id = adgroupDTO.getAdgroupId();
        Query query = new Query();
        query.addCriteria(Criteria.where(MongoEntityConstants.ADGROUP_ID).is(id));
        Update update = new Update();
        try {
            Class _class = adgroupDTO.getClass();
            Field[] fields = _class.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                if ("adgroupId".equals(fieldName))
                    continue;
                StringBuilder fieldGetterName = new StringBuilder("get");
                fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
                Method method = _class.getDeclaredMethod(fieldGetterName.toString());
                Object after = method.invoke(adgroupDTO);
                if (after != null) {
                    update.set(field.getName(), after);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        getMongoTemplate().updateFirst(query, update, getEntityClass());
        AdgroupBackupDTO adgroupBackupDTOFind = adgroupBackUpDAO.findOne(adgroupDTO.getId());
        if (adgroupBackupDTOFind.getId() == null) {
            AdgroupBackUpEntity adgroupBakcUpEntity = new AdgroupBackUpEntity();
            BeanUtils.copyProperties(bakadgroupDTO, adgroupBakcUpEntity);
            getMongoTemplate().insert(adgroupBakcUpEntity);
        }
        logDAO.insertLog(id, LogStatusConstant.ENTITY_ADGROUP, LogStatusConstant.OPT_UPDATE);
    }

    @Override
    public void insertReBack(AdgroupDTO adgroupDTO) {
        getMongoTemplate().remove(new Query(Criteria.where(get_id()).is(adgroupDTO.getId())), getEntityClass());
        AdgroupEntity adgroupEntity = new AdgroupEntity();
        BeanUtils.copyProperties(adgroupDTO, adgroupEntity);
        getMongoTemplate().insert(adgroupEntity);
    }

    @Override
    public void delBack(Long oid) {
        Update up = new Update();
        up.set("ls", "");
        getMongoTemplate().updateFirst(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).is(oid)), up, getEntityClass());
        subdelBack(oid);
    }

    @Override
    public PagerInfo findByPagerInfo(Map<String, Object> params, Integer nowPage, Integer pageSize) {
        Query q = new Query();
        Criteria c = new Criteria();
        if (params.size() > 0 || params != null) {
            for (Map.Entry<String, Object> adg : params.entrySet()) {
                c.and(adg.getKey()).is(adg.getValue());
            }
        }
        q.addCriteria(c);
        Integer totalCount = getTotalCount(q, getEntityClass());
        PagerInfo p = new PagerInfo(nowPage, pageSize, totalCount);
        q.skip(p.getFirstStation());
        q.limit(p.getPageSize());
        List<AdgroupEntity> adgroupEntityList = getMongoTemplate().find(q, getEntityClass());
        List<AdgroupDTO> returnList = wrapperList(adgroupEntityList);
        p.setList(returnList);
        return p;
    }

    private int getTotalCount(Query q, Class<?> cls) {
        return (int) getMongoTemplate().count(q, cls);
    }

    public void insert(AdgroupDTO adgroupDTO) {
        AdgroupEntity adgroupEntit = new AdgroupEntity();
        BeanUtils.copyProperties(adgroupDTO, adgroupEntit);
        getMongoTemplate().insert(adgroupEntit, MongoEntityConstants.TBL_ADGROUP);
    }

    @Override
    public List<AdgroupDTO> findByTwoParams(Long cid, Long accountId) {
        Query query = new Query();
        query.addCriteria(Criteria.where(CAMPAIGN_ID).is(cid).and(ACCOUNT_ID).is(accountId));
        List<AdgroupEntity> list = getMongoTemplate().find(query, getEntityClass());
        return wrapperList(list);
    }

    public void insertAll(List<AdgroupDTO> adgroupDTOs) {
        List<AdgroupEntity> insertList = ObjectUtils.convert(adgroupDTOs, getEntityClass());
        getMongoTemplate().insertAll(insertList);
    }

    @SuppressWarnings("unchecked")
    public void update(AdgroupDTO adgroupDTO) {
        Long id = adgroupDTO.getAdgroupId();
        Query query = new Query();
        query.addCriteria(Criteria.where(MongoEntityConstants.ADGROUP_ID).is(id));
        Update update = new Update();
        try {
            Class _class = adgroupDTO.getClass();
            Field[] fields = _class.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                if ("adgroupId".equals(fieldName))
                    continue;
                StringBuilder fieldGetterName = new StringBuilder("get");
                fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
                Method method = _class.getDeclaredMethod(fieldGetterName.toString());
                Object after = method.invoke(adgroupDTO);
                if (after != null) {
                    update.set(field.getName(), after);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        getMongoTemplate().updateFirst(query, update, getEntityClass());
    }


    public void deleteById(final Long adgroupId) {
        getMongoTemplate().remove(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).is(adgroupId)), getEntityClass());
        deleteSub(new ArrayList<Long>(1) {{
            add(adgroupId);
        }});
    }

    public int deleteByIds(List<Long> adgroupIds) {
        WriteResult writeResult = getMongoTemplate().remove(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).in(adgroupIds)), getEntityClass());
        deleteSub(adgroupIds);
        return writeResult.getN();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<AdgroupEntity> getEntityClass() {
        return AdgroupEntity.class;
    }

    @Override
    public Class<AdgroupDTO> getDTOClass() {
        return AdgroupDTO.class;
    }

    /**
     * 根据计划id级联软删除
     *
     * @param agid
     */
    @Override
    public void deleteLinkedByAgid(List<Long> agid) {
        Update up = new Update();
        up.set("ls", 4);
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.updateMulti(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).in(agid)), up, getEntityClass());
        mongoTemplate.updateMulti(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).in(agid)), up, KeywordEntity.class);
        mongoTemplate.updateMulti(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).in(agid)), up, CreativeEntity.class);
    }

    private void deleteSub(List<Long> adgroupIds) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).in(adgroupIds)), KeywordEntity.class);
        mongoTemplate.remove(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).in(adgroupIds)), CreativeEntity.class);
    }

    /**
     * 级联删除，删除单元下的创意和关键字
     *
     * @param oids
     */
    private void deleteSubOid(List<String> oids) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(new Query(Criteria.where(get_id()).in(oids)), KeywordEntity.class);
        mongoTemplate.remove(new Query(Criteria.where(get_id()).in(oids)), CreativeEntity.class);
    }

    /**
     * 根据删除的单元删除其下的关键词和创意，该删除可以拥有还原功能，实际上是将创意和关键字放入备份数据库中，如果还原创单元，则级联的
     *
     * @param agid
     */
    private void deleteLinked(Long agid) {
        Update up = new Update();
        up.set("ls", 4);
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.updateMulti(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).in(agid)), up, KeywordEntity.class, MongoEntityConstants.TBL_KEYWORD);
        mongoTemplate.updateMulti(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).in(agid)), up, CreativeEntity.class, MongoEntityConstants.TBL_CREATIVE);
    }

    private void subdelBack(Long oid) {
        Update up = new Update();
        up.set("ls", "");
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.updateMulti(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).in(oid)), up, KeywordEntity.class, MongoEntityConstants.TBL_KEYWORD);
        mongoTemplate.updateMulti(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).in(oid)), up, CreativeEntity.class, MongoEntityConstants.TBL_CREATIVE);
    }

    @Resource
    LogDAO logDAO;

    private List<AdgroupDTO> wrapperList(List<AdgroupEntity> list) {
        return ObjectUtils.convert(list, AdgroupDTO.class);
    }

    private AdgroupDTO wrapperObject(AdgroupEntity entity) {
        AdgroupDTO dto = new AdgroupDTO();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto);
        }
        return dto;
    }
}
