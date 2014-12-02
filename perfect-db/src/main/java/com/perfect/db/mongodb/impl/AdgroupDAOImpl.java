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
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.entity.adgroup.AdgroupEntity;
import com.perfect.entity.backup.AdgroupBackUpEntity;
import com.perfect.utils.ObjectUtils;
import com.perfect.entity.creative.CreativeEntity;
import com.perfect.entity.keyword.KeywordEntity;
import com.perfect.utils.paging.Pager;
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
import java.util.*;

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
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new BasicQuery("{}", "{" + MongoEntityConstants.ADGROUP_ID + " : 1}");
        List<AdgroupEntity> list = mongoTemplate.find(query, AdgroupEntity.class, MongoEntityConstants.TBL_ADGROUP);
        List<Long> adgroupIds = new ArrayList<>(list.size());
        for (AdgroupEntity type : list)
            adgroupIds.add(type.getAdgroupId());
        return adgroupIds;
    }

    public List<Long> getAdgroupIdByCampaignId(Long campaignId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new BasicQuery("{}", "{" + MongoEntityConstants.ADGROUP_ID + " : 1}");
        query.addCriteria(Criteria.where(MongoEntityConstants.CAMPAIGN_ID).is(campaignId));
        List<AdgroupEntity> list = mongoTemplate.find(query, AdgroupEntity.class, MongoEntityConstants.TBL_ADGROUP);
        List<Long> adgroupIds = new ArrayList<>(list.size());
        for (AdgroupEntity type : list)
            adgroupIds.add(type.getAdgroupId());
        return adgroupIds;
    }


    //xj根据单元
    public AdgroupDTO getByCampaignIdAndName(Long campaignId, String name) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<AdgroupEntity> adgroupEntityList = mongoTemplate.find(new Query(Criteria.where(MongoEntityConstants.ACCOUNT_ID).is(AppContext.getAccountId()).and(MongoEntityConstants.CAMPAIGN_ID).is(campaignId).and("name").is(name)), AdgroupEntity.class, MongoEntityConstants.TBL_ADGROUP);
        AdgroupEntity adgroupEntity = adgroupEntityList.size() == 0 ? null : adgroupEntityList.get(0);
        return wrapperObject(adgroupEntity);
    }

    @Override
    public List<String> getAdgroupIdByCampaignId(String campaignId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new BasicQuery("{}", "{_id : 1}");
        query.addCriteria(Criteria.where(MongoEntityConstants.OBJ_CAMPAIGN_ID).is(campaignId));
        List<AdgroupEntity> list = mongoTemplate.find(query, AdgroupEntity.class, MongoEntityConstants.TBL_ADGROUP);
        List<String> adgroupIds = new ArrayList<>(list.size());
        for (AdgroupEntity type : list)
            adgroupIds.add(type.getId());
        return adgroupIds;
    }

    @Override
    public List<AdgroupDTO> findByCampaignOId(String id) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        return mongoTemplate.find(new Query(Criteria.where(MongoEntityConstants.OBJ_CAMPAIGN_ID).is(id)), getEntityClass(), MongoEntityConstants.TBL_ADGROUP);
    }

    public List<String> getObjAdgroupIdByCampaignId(List<String> cids) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        Query query = new BasicQuery("{}", "{" + MongoEntityConstants.OBJ_ADGROUP_ID + " : 1}");
        query.addCriteria(Criteria.where(MongoEntityConstants.OBJ_CAMPAIGN_ID).in(cids));
        List<AdgroupEntity> list = mongoTemplate.find(query, AdgroupEntity.class, MongoEntityConstants.TBL_ADGROUP);
        List<String> adgroupIds = new ArrayList<>(list.size());
        for (AdgroupEntity type : list)
            adgroupIds.add(type.getId());
        return adgroupIds;
    }


    public List<AdgroupDTO> getAdgroupByCampaignId(Long campaignId, Map<String, Object> params, int skip, int limit) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new Query();
        Criteria criteria = Criteria.where(MongoEntityConstants.CAMPAIGN_ID).is(campaignId);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet())
                criteria.and(entry.getKey()).is(entry.getValue());
        }
        query.addCriteria(criteria);
        query.with(new PageRequest(skip, limit));
        List<AdgroupEntity> _list = mongoTemplate.find(query, AdgroupEntity.class, MongoEntityConstants.TBL_ADGROUP);
        return wrapperList(_list);
    }


    public List<AdgroupDTO> getAdgroupByCampaignObjId(String campaignObjId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        return mongoTemplate.find(Query.query(Criteria.where(MongoEntityConstants.OBJ_CAMPAIGN_ID).is(campaignObjId)), getEntityClass(), MongoEntityConstants.TBL_ADGROUP);
    }

    @Override
    public List<AdgroupDTO> getAdgroupByCampaignId(Long campaignId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<AdgroupEntity> adgroupDTOList= mongoTemplate.find(Query.query(Criteria.where(MongoEntityConstants.CAMPAIGN_ID).is(campaignId)), AdgroupEntity.class, MongoEntityConstants.TBL_ADGROUP);
        return wrapperList(adgroupDTOList);
    }

    @Override
    public Class<AdgroupDTO> getDTOClass() {
        return AdgroupDTO.class;
    }

    public AdgroupDTO findOne(Long adgroupId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        AdgroupEntity _adgroupEntity = mongoTemplate.findOne(
                new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).is(adgroupId)), AdgroupEntity.class, MongoEntityConstants.TBL_ADGROUP);
        return wrapperObject(_adgroupEntity);
    }

    public List<AdgroupDTO> findAll() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<AdgroupEntity> adgroupEntities = mongoTemplate.find(Query.query(Criteria.where(MongoEntityConstants.ACCOUNT_ID).is(AppContext.getAccountId())), AdgroupEntity.class);
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
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new Query();
        if (params != null && params.size() > 0) {
            Criteria criteria = new Criteria();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                criteria.and(entry.getKey()).is(entry.getValue());
            }
            query.addCriteria(criteria);
        }
        query.with(new PageRequest(skip, limit, new Sort(Sort.Direction.DESC, "price")));
        List<AdgroupEntity> list = mongoTemplate.find(query, AdgroupEntity.class, MongoEntityConstants.TBL_ADGROUP);
        return wrapperList(list);
    }

    /**
     * 条件查询
     *
     * @param query
     * @return
     */
    public List<AdgroupDTO> findByQuery(Query query) {
        List<AdgroupEntity> list = getMongoTemplate().find(query, AdgroupEntity.class, MongoEntityConstants.TBL_ADGROUP);
        return wrapperList(list);
    }

    @Override
    public List<AdgroupDTO> findByCampaignId(Long cid) {
        return getMongoTemplate().find(Query.query(Criteria.where(MongoEntityConstants.CAMPAIGN_ID).is(cid)), getEntityClass());
    }

    @Override
    public List<AdgroupDTO> findIdByCampaignId(Long cid) {
        Query query = new BasicQuery("{}", "{ " + MongoEntityConstants.ADGROUP_ID + " : 1 }");
        List<AdgroupEntity> list = getMongoTemplate().find(query.addCriteria(Criteria.where(MongoEntityConstants.CAMPAIGN_ID).is(cid)), AdgroupEntity.class);
        return wrapperList(list);
    }

    @Override
    public AdgroupDTO findByObjId(String oid) {
        AdgroupEntity adgroupEntity = getMongoTemplate().findOne(Query.query(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(oid)), AdgroupEntity.class);
        return wrapperObject(adgroupEntity);
    }

    @Override
    public AdgroupDTO fndEntity(Map<String, Object> params) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query q = new Query();
        Criteria c = new Criteria();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> par : params.entrySet()) {
                c.and(par.getKey()).is(par.getValue());
            }
        }
        q.addCriteria(c);
        AdgroupEntity adgroupEntity = mongoTemplate.findOne(q, AdgroupEntity.class, MongoEntityConstants.TBL_ADGROUP);
        return wrapperObject(adgroupEntity);
    }

    @Override
    public Object insertOutId(AdgroupDTO adgroupEntity) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        AdgroupEntity adgroupEntityInsert = new AdgroupEntity();
        BeanUtils.copyProperties(adgroupEntity, adgroupEntityInsert);
        mongoTemplate.insert(adgroupEntityInsert, MongoEntityConstants.TBL_ADGROUP);
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
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(Query.query(Criteria.where(get_id()).is(oid)), getEntityClass());

        deleteSubOid(Arrays.asList(oid));

    }

    @Override
    public void deleteByObjId(Long adgroupId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Update update = new Update();
        update.set("ls", "");
        mongoTemplate.updateFirst(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).is(adgroupId)), update, AdgroupEntity.class, MongoEntityConstants.TBL_ADGROUP);
        deleteLinked(adgroupId);
        //以前是直接删除拉取到本地的数据，是硬删除，现在改为软删除，以便以后还原操作
//        mongoTemplate.remove(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).is(adgroupId)),AdgroupEntity.class,MongoEntityConstants.TBL_ADGROUP);
        logDAO.insertLog(adgroupId, LogStatusConstant.ENTITY_ADGROUP, LogStatusConstant.OPT_DELETE);
    }

    @Override
    public void updateCampaignIdByOid(String oid, Long campaignId) {
        WriteResult wr = getMongoTemplate().updateMulti(Query.query(Criteria.where(MongoEntityConstants.OBJ_CAMPAIGN_ID).is(oid)),
                Update.update(MongoEntityConstants.CAMPAIGN_ID, campaignId).set(MongoEntityConstants.OBJ_CAMPAIGN_ID, null), getEntityClass());
    }

    @Override

    public void updateByObjId(AdgroupDTO adgroupEntity) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Update up = new Update();
        up.set("name", adgroupEntity.getAdgroupName());
        up.set("max", adgroupEntity.getMaxPrice());
        up.set("neg", adgroupEntity.getNegativeWords());
        up.set("exneg", adgroupEntity.getExactNegativeWords());
        up.set("p", adgroupEntity.getPause());
        up.set("s", adgroupEntity.getStatus());
        up.set("m", adgroupEntity.getMib());
        mongoTemplate.updateFirst(new Query(Criteria.where(get_id()).is(adgroupEntity.getId())), up, AdgroupEntity.class, MongoEntityConstants.TBL_ADGROUP);
        logDAO.insertLog(adgroupEntity.getId(), LogStatusConstant.ENTITY_ADGROUP);
    }


    @Override
    public void update(AdgroupDTO adgroupDTO, AdgroupDTO bakadgroupDTO) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
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
        mongoTemplate.updateFirst(query, update, AdgroupEntity.class, MongoEntityConstants.TBL_ADGROUP);
        AdgroupBackupDTO adgroupBackupDTOFind = adgroupBackUpDAO.findOne(adgroupDTO.getId());
        if (adgroupBackupDTOFind == null) {
            AdgroupBackUpEntity adgroupBakcUpEntity = new AdgroupBackUpEntity();
            BeanUtils.copyProperties(adgroupBackupDTOFind, adgroupBakcUpEntity);
            getMongoTemplate().insert(adgroupBakcUpEntity, MongoEntityConstants.BAK_ADGROUP);
        }
        logDAO.insertLog(id, LogStatusConstant.ENTITY_ADGROUP, LogStatusConstant.OPT_UPDATE);
    }

    @Override
    public void insertReBack(AdgroupDTO adgroupDTO) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(new Query(Criteria.where(get_id()).is(adgroupDTO.getId())), AdgroupEntity.class, MongoEntityConstants.TBL_ADGROUP);
        AdgroupEntity adgroupEntity = new AdgroupEntity();
        BeanUtils.copyProperties(adgroupDTO, adgroupEntity);
        getMongoTemplate().insert(adgroupEntity, MongoEntityConstants.TBL_ADGROUP);
    }

    @Override
    public void delBack(Long oid) {
        Update up = new Update();
        up.set("ls", "");
        BaseMongoTemplate.getUserMongo().updateFirst(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).is(oid)), up, AdgroupEntity.class, MongoEntityConstants.TBL_ADGROUP);
        subdelBack(oid);
    }

    @Override
    public PagerInfo findByPagerInfo(Map<String, Object> params, Integer nowPage, Integer pageSize) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query q = new Query();
        Criteria c = new Criteria();
        if (params.size() > 0 || params != null) {
            for (Map.Entry<String, Object> adg : params.entrySet()) {
                c.and(adg.getKey()).is(adg.getValue());
            }
        }
        q.addCriteria(c);
        Integer totalCount = getTotalCount(q, AdgroupEntity.class);
        PagerInfo p = new PagerInfo(nowPage, pageSize, totalCount);
        q.skip(p.getFirstStation());
        q.limit(p.getPageSize());
        List<AdgroupEntity> adgroupEntityList = mongoTemplate.find(q, AdgroupEntity.class);
        p.setList(adgroupEntityList);
        return p;
    }

    private int getTotalCount(Query q, Class<?> cls) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        return (int) mongoTemplate.count(q, cls);
    }

    public void insert(AdgroupDTO adgroupDTO) {
        AdgroupEntity adgroupEntit = new AdgroupEntity();
        BeanUtils.copyProperties(adgroupDTO, adgroupEntit);
        getMongoTemplate().insert(adgroupEntit, MongoEntityConstants.TBL_ADGROUP);
    }

    @Override
    public List<AdgroupDTO> findByTwoParams(Long cid, Long accountId) {
        Query query=new Query();
        query.addCriteria(Criteria.where(CAMPAIGN_ID).is(cid).and(ACCOUNT_ID).is(accountId));
        return null;
    }

    public void insertAll(List<AdgroupDTO> adgroupDTOs) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<AdgroupEntity> insertList = ObjectUtils.convert(adgroupDTOs, AdgroupEntity.class);
        mongoTemplate.insertAll(insertList);
    }

    @SuppressWarnings("unchecked")
    public void update(AdgroupDTO adgroupDTO) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
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
                    Object before = method.invoke(findOne(id));
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        mongoTemplate.updateFirst(query, update, AdgroupEntity.class, MongoEntityConstants.TBL_ADGROUP);
    }

    public void update(List<AdgroupDTO> adgroupDTOs) {
        for (AdgroupDTO dtos : adgroupDTOs)
            update(dtos);
    }

    public void deleteById(final Long adgroupId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).is(adgroupId)), AdgroupEntity.class, MongoEntityConstants.TBL_ADGROUP);
        deleteSub(new ArrayList<Long>(1) {{
            add(adgroupId);
        }});
    }

    public int deleteByIds(List<Long> adgroupIds) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        WriteResult writeResult = mongoTemplate.remove(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).in(adgroupIds)), AdgroupEntity.class, MongoEntityConstants.TBL_ADGROUP);
        deleteSub(adgroupIds);
        return writeResult.getN();
    }

    @Override
    public Class<AdgroupDTO> getEntityClass() {
        return AdgroupDTO.class;
    }

    public void delete(AdgroupDTO adgroupDTO) {
        deleteById(adgroupDTO.getAdgroupId());
    }

    public void deleteAll() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.dropCollection(AdgroupEntity.class);
        deleteSub(getAllAdgroupId());
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
        mongoTemplate.updateMulti(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).in(agid)), up, AdgroupEntity.class, MongoEntityConstants.TBL_ADGROUP);
        mongoTemplate.updateMulti(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).in(agid)), up, KeywordEntity.class, MongoEntityConstants.TBL_KEYWORD);
        mongoTemplate.updateMulti(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).in(agid)), up, CreativeEntity.class, MongoEntityConstants.TBL_CREATIVE);
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
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
