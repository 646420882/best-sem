package com.perfect.db.mongodb.impl;

import com.mongodb.WriteResult;
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
import com.perfect.entity.backup.CreativeBackUpEntity;
import com.perfect.entity.backup.KeywordBackUpEntity;
import com.perfect.entity.campaign.CampaignEntity;
import com.perfect.entity.creative.CreativeEntity;
import com.perfect.entity.keyword.KeywordEntity;
import com.perfect.param.SearchFilterParam;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Created by vbzer_000 on 2014-07-02.
 */
@Repository("adgroupDAO")
public class AdgroupDAOImpl extends AbstractUserBaseDAOImpl<AdgroupDTO, Long> implements AdgroupDAO {

    @Override
    public String getId() {
        return ADGROUP_ID;
    }

    public String get_id() {
        return SYSTEM_ID;
    }

    @Resource
    private AdgroupBackUpDAO adgroupBackUpDAO;

    public List<Long> getAllAdgroupId() {
        Query query = new BasicQuery("{}", "{" + ADGROUP_ID + " : 1}");
        List<AdgroupEntity> list = getMongoTemplate().find(query, getEntityClass());
        List<Long> adgroupIds = new ArrayList<>(list.size());
        for (AdgroupEntity type : list)
            adgroupIds.add(type.getAdgroupId());
        return adgroupIds;
    }

    public List<Long> getAdgroupIdByCampaignId(Long campaignId) {
        Query query = new BasicQuery("{}", "{" + ADGROUP_ID + " : 1}");
        query.addCriteria(Criteria.where(CAMPAIGN_ID).is(campaignId));
        List<AdgroupEntity> list = getMongoTemplate().find(query, getEntityClass());
        List<Long> adgroupIds = new ArrayList<>(list.size());
        for (AdgroupEntity type : list)
            adgroupIds.add(type.getAdgroupId());
        return adgroupIds;
    }

    @Override
    public List<Long> getAdgroupIdByCampaignObj(String campaignId) {
        Query query = new BasicQuery("{}", "{" + ADGROUP_ID + " : 1}");
        query.addCriteria(Criteria.where(OBJ_CAMPAIGN_ID).is(campaignId));
        List<AdgroupEntity> list = getMongoTemplate().find(query, getEntityClass());
        List<Long> adgroupIds = new ArrayList<>(list.size());
        for (AdgroupEntity type : list)
            adgroupIds.add(type.getAdgroupId());
        return adgroupIds;
    }


    //xj根据单元
    public AdgroupDTO getByCampaignIdAndName(Long campaignId, String name) {
        List<AdgroupEntity> adgroupEntityList = getMongoTemplate().find(new Query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and(CAMPAIGN_ID).is(campaignId).and("name").is(name)), getEntityClass());
        AdgroupEntity adgroupEntity = adgroupEntityList.size() == 0 ? null : adgroupEntityList.get(0);
        return wrapperObject(adgroupEntity);
    }

    @Override
    public List<String> getAdgroupIdByCampaignId(String campaignId) {
        Query query = new BasicQuery("{}", "{_id : 1}");
        query.addCriteria(Criteria.where(OBJ_CAMPAIGN_ID).is(campaignId));
        List<AdgroupEntity> list = getMongoTemplate().find(query, getEntityClass());
        List<String> adgroupIds = new ArrayList<>(list.size());
        for (AdgroupEntity type : list)
            adgroupIds.add(type.getId());
        return adgroupIds;
    }

    @Override
    public List<AdgroupDTO> findByCampaignOId(String id) {
        List<AdgroupEntity> adgroupEntities = getMongoTemplate().find(new Query(Criteria.where(OBJ_CAMPAIGN_ID).is(id)), getEntityClass());
        return wrapperList(adgroupEntities);
    }

    public List<String> getObjAdgroupIdByCampaignId(List<String> cids) {
        Query query = new BasicQuery("{}", "{" + OBJ_ADGROUP_ID + " : 1}");
        query.addCriteria(Criteria.where(OBJ_CAMPAIGN_ID).in(cids));
        List<AdgroupEntity> list = getMongoTemplate().find(query, getEntityClass());
        List<String> adgroupIds = new ArrayList<>(list.size());
        for (AdgroupEntity type : list)
            adgroupIds.add(type.getId());
        return adgroupIds;
    }


    public List<AdgroupDTO> getAdgroupByCampaignId(Long campaignId, Map<String, Object> params, int skip, int limit) {
        Query query = new Query();
        Criteria criteria = Criteria.where(CAMPAIGN_ID).is(campaignId);
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
        List<AdgroupEntity> adgroupEntities = getMongoTemplate().find(Query.query(Criteria.where(OBJ_CAMPAIGN_ID).is(campaignObjId)), getEntityClass());
        return wrapperList(adgroupEntities);
    }

    @Override
    public List<AdgroupDTO> getAdgroupByCampaignId(Long campaignId) {
        List<AdgroupEntity> adgroupDTOList = getMongoTemplate().find(Query.query(Criteria.where(CAMPAIGN_ID).is(campaignId)), getEntityClass());
        return wrapperList(adgroupDTOList);
    }

    @Override
    public List<AdgroupDTO> findHasLocalStatus() {
        List<AdgroupEntity> adgroupEntities = getMongoTemplate().find(new Query(Criteria.where("ls").ne(null).and(ACCOUNT_ID).is(AppContext.getAccountId())), getEntityClass());
        return ObjectUtils.convert(adgroupEntities, AdgroupDTO.class);
    }

    @Override
    public List<AdgroupDTO> findHasLocalStatusStr(List<String> str) {
        List<AdgroupEntity> adgroupEntities = getMongoTemplate().find(new Query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and(OBJ_CAMPAIGN_ID).in(str)), getEntityClass());
        return ObjectUtils.convert(adgroupEntities, AdgroupDTO.class);
    }

    @Override
    public List<AdgroupDTO> findHasLocalStatusLong(List<Long> longs) {
        List<AdgroupEntity> adgroupEntities = getMongoTemplate().find(new Query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and(CAMPAIGN_ID).in(longs)), getEntityClass());
        return ObjectUtils.convert(adgroupEntities, AdgroupDTO.class);
    }

    @Override
    public List<AdgroupDTO> findDownloadAdgroup(Long baiduAccountId, List<Long> adgroupIds) {
        List<AdgroupEntity> adgroupEntities = getMongoTemplate()
                .find(Query.query(Criteria.where(ACCOUNT_ID).is(baiduAccountId).and(ADGROUP_ID).in(adgroupIds)), getEntityClass());

        return ObjectUtils.convert(adgroupEntities, AdgroupDTO.class);
    }

    @Override
    public List<AdgroupDTO> findLocalChangedAdgroups(Long baiduAccountId, int type) {
        List<AdgroupEntity> adgroupEntities = getMongoTemplate().find(new Query(Criteria.where("ls").is(type).and(ACCOUNT_ID).is(baiduAccountId)), getEntityClass());
        return ObjectUtils.convert(adgroupEntities, AdgroupDTO.class);
    }

    public AdgroupDTO findOne(Long adgroupId) {
        AdgroupEntity _adgroupEntity = getMongoTemplate().findOne(new Query(Criteria.where(ADGROUP_ID).is(adgroupId)), getEntityClass());
        return wrapperObject(_adgroupEntity);
    }

    public List<AdgroupDTO> findAll() {
        List<AdgroupEntity> adgroupEntities = getMongoTemplate().find(Query.query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId())), getEntityClass());
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
        List<AdgroupEntity> adgroupEntities = getMongoTemplate().find(Query.query(Criteria.where(CAMPAIGN_ID).is(cid)), getEntityClass());
        return wrapperList(adgroupEntities);
    }

    @Override
    public List<AdgroupDTO> findIdByCampaignId(Long cid) {
        Query query = new BasicQuery("{}", "{ " + ADGROUP_ID + " : 1 }");
        List<AdgroupEntity> list = getMongoTemplate().find(query.addCriteria(Criteria.where(CAMPAIGN_ID).is(cid)), getEntityClass());
        return wrapperList(list);
    }

    @Override
    public AdgroupDTO findByObjId(String oid) {
        AdgroupEntity adgroupEntity = getMongoTemplate().findOne(Query.query(Criteria.where(SYSTEM_ID).is(oid)), getEntityClass());
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
        return ObjectUtils.convert(adgroupEntity, AdgroupDTO.class);
    }

    @Override
    public Object insertOutId(AdgroupDTO adgroupEntity) {
        AdgroupEntity adgroupEntityInsert = new AdgroupEntity();
        BeanUtils.copyProperties(adgroupEntity, adgroupEntityInsert);
        if (!Objects.equals("edit", adgroupEntity.getId())) {
            if (!getMongoTemplate().exists(new Query(Criteria.where(NAME).is(adgroupEntity.getAdgroupName())), getEntityClass())) {
                getMongoTemplate().insert(adgroupEntityInsert);
            }
        } else {
            adgroupEntityInsert.setId(null);
            getMongoTemplate().insert(adgroupEntityInsert);
        }
        return adgroupEntityInsert.getId();
    }

    /**
     * 连到单元下的关键字和创意一起删了
     *
     * @param oid
     */
    @Override
    public void deleteByObjId(final String oid) {
        getMongoTemplate().remove(Query.query(Criteria.where(SYSTEM_ID).is(oid)), getEntityClass());
        deleteSubOid(oid);
    }

    @Override
    public void deleteByObjId(Long adgroupId) {
        Update update = new Update();
        update.set("ls", "3");
        getMongoTemplate().updateFirst(new Query(Criteria.where(ADGROUP_ID).is(adgroupId)), update, getEntityClass());
        //以前是直接删除拉取到本地的数据，是硬删除，现在改为软删除，以便以后还原操作
//        mongoTemplate.remove(new Query(Criteria.where(ADGROUP_ID).is(adgroupId)),getEntityClass(),TBL_ADGROUP);
//        logDAO.insertLog(adgroupId, LogStatusConstant.ENTITY_ADGROUP, LogStatusConstant.OPT_DELETE);
    }

    @Override
    public void updateCampaignIdByOid(String oid, Long campaignId) {
        WriteResult wr = getMongoTemplate().updateMulti(Query.query(Criteria.where(OBJ_CAMPAIGN_ID).is(oid)),
                Update.update(CAMPAIGN_ID, campaignId).set(OBJ_CAMPAIGN_ID, null), getEntityClass());
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
        if (!getMongoTemplate().exists(new Query(Criteria.where(NAME).is(adgroupEntity.getAdgroupName()).and(ACCOUNT_ID).is(AppContext.getAccountId())), getEntityClass())) {
            getMongoTemplate().updateFirst(new Query(Criteria.where(get_id()).is(adgroupEntity.getId())), up, getEntityClass());
        } else {
            Update up2 = new Update();
            up2.set("max", adgroupEntity.getMaxPrice());
            up2.set("neg", adgroupEntity.getNegativeWords());
            up2.set("exneg", adgroupEntity.getExactNegativeWords());
            up2.set("p", adgroupEntity.getPause());
            up2.set("s", adgroupEntity.getStatus());
            getMongoTemplate().updateFirst(new Query(Criteria.where(get_id()).is(adgroupEntity.getId())), up2, getEntityClass());
        }
    }


    @Override
    public void update(AdgroupDTO adgroupDTO, AdgroupDTO bakadgroupDTO) {
        Long id = adgroupDTO.getAdgroupId();
        Query query = new Query();
        query.addCriteria(Criteria.where(ADGROUP_ID).is(id));
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
//        if(!getMongoTemplate().exists(new Query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and(NAME).is(adgroupDTO.getAdgroupName())),getEntityClass())){
        getMongoTemplate().updateFirst(query, update, getEntityClass());
        AdgroupBackupDTO adgroupBackupDTOFind = adgroupBackUpDAO.findOne(adgroupDTO.getId());
        if (adgroupBackupDTOFind.getId() == null) {
            AdgroupBackUpEntity adgroupBakcUpEntity = new AdgroupBackUpEntity();
            BeanUtils.copyProperties(bakadgroupDTO, adgroupBakcUpEntity);
            getMongoTemplate().insert(adgroupBakcUpEntity);
        }
//        }

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
        getMongoTemplate().updateFirst(new Query(Criteria.where(ADGROUP_ID).is(oid)), up, getEntityClass());
        subdelBack(oid);
    }

    @Override
    public PagerInfo findByPagerInfo(Map<String, Object> params, Integer nowPage, Integer pageSize, SearchFilterParam sp) {
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
        searchFilterQueryOperate(q, sp);
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
        if (!getMongoTemplate().exists(new Query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and(NAME).is(adgroupDTO.getAdgroupName())), getEntityClass())) {
            getMongoTemplate().insert(adgroupEntit, TBL_ADGROUP);
        }
    }

    @Override
    public List<AdgroupDTO> findByTwoParams(Long cid, Long accountId) {
        Query query = new Query();
        query.addCriteria(Criteria.where(CAMPAIGN_ID).is(cid).and(ACCOUNT_ID).is(accountId));
        List<AdgroupEntity> list = getMongoTemplate().find(query, getEntityClass());
        return wrapperList(list);
    }

    @Override
    public double findPriceRatio(Long cid) {
        Query query = new BasicQuery("{}", "{pr: 1}");
        query.addCriteria(Criteria.where(CAMPAIGN_ID).is(cid));
        CampaignEntity entity = getMongoTemplate().findOne(query, CampaignEntity.class);
        if (entity != null) {
            return entity.getPriceRatio();
        }
        return 0;
    }

    @Override
    public void update(String oid, AdgroupDTO dto) {
        Update up = new Update();
        up.set("ls", null);
        up.set("s", dto.getStatus());
        up.set("p", dto.getPause());
        up.set(ADGROUP_ID, dto.getAdgroupId());
        getMongoTemplate().updateFirst(new Query(Criteria.where(SYSTEM_ID).is(oid)), up, AdgroupEntity.class);
        updateSub(dto.getAdgroupId(), oid);
    }

    @Override
    public void deleteBubLinks(Long aid) {
        getMongoTemplate().remove(new Query(Criteria.where(ADGROUP_ID).is(aid)), AdgroupEntity.class);
        getMongoTemplate().remove(new Query(Criteria.where(ADGROUP_ID).is(aid)), AdgroupBackUpEntity.class);
        getMongoTemplate().remove(new Query(Criteria.where(ADGROUP_ID).is(aid)), CreativeEntity.class);
        getMongoTemplate().remove(new Query(Criteria.where(ADGROUP_ID).is(aid)), CreativeBackUpEntity.class);
        getMongoTemplate().remove(new Query(Criteria.where(ADGROUP_ID).is(aid)), KeywordEntity.class);
        getMongoTemplate().remove(new Query(Criteria.where(ADGROUP_ID).is(aid)), KeywordBackUpEntity.class);
    }

    @Override
    public void pdateUpdate(Long aid, AdgroupDTO dto) {
        Update up = new Update();
        up.set("ls", null);
        up.set("s", dto.getStatus());
        up.set("p", dto.getPause());
        getMongoTemplate().updateFirst(new Query(Criteria.where(ADGROUP_ID).is(aid)), up, AdgroupEntity.class);
        getMongoTemplate().remove(new Query(Criteria.where(ADGROUP_ID).is(aid)), AdgroupBackUpEntity.class);
    }

    @Override
    public double getCampBgt(String cid) {
        Query query = new BasicQuery("{}", "{bd: 1}");
        query.addCriteria(Criteria.where(CAMPAIGN_ID).is(cid));
        CampaignEntity entity = getMongoTemplate().findOne(query, CampaignEntity.class);
        if (entity != null) {
            return entity.getBudget();
        }
        return 0;
    }

    @Override
    public double getCampBgt(Long cid) {
        Query query = new BasicQuery("{}", "{bd: 1}");
        query.addCriteria(Criteria.where(CAMPAIGN_ID).is(cid));
        CampaignEntity entity = getMongoTemplate().findOne(query, CampaignEntity.class);
        if (entity.getBudget() != null) {
            return entity.getBudget();
        }
        return 0;
    }

    @Override
    public void batchDelete(List<String> asList, List<String> keywordDatas, List<String> creativeDatas) {
        asList.forEach(e -> {
            if (e.length() < 24) {
                Update update = new Update();
                update.set("ls", 3);
                getMongoTemplate().updateFirst(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).is(Long.valueOf(e))), update, getEntityClass());
            } else {
                getMongoTemplate().remove(new Query(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(e)), getEntityClass());
            }
        });
        keywordDatas.forEach(e -> {
            if (e.length() < 24) {
                Update update = new Update();
                update.set("ls", 4);
                getMongoTemplate().updateFirst(new Query(Criteria.where(MongoEntityConstants.KEYWORD_ID).is(Long.valueOf(e))), update, KeywordEntity.class);
            } else {
                getMongoTemplate().remove(new Query(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(e)), KeywordEntity.class);
            }
        });
        creativeDatas.forEach(e -> {
            if (e.length() < 24) {
                Update update = new Update();
                update.set("ls", 4);
                getMongoTemplate().updateFirst(new Query(Criteria.where(MongoEntityConstants.CREATIVE_ID).is(Long.valueOf(e))), update, CreativeEntity.class);
            } else {
                getMongoTemplate().remove(new Query(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(e)), CreativeEntity.class);
            }
        });
    }

    public void insertAll(List<AdgroupDTO> adgroupDTOs) {
        List<AdgroupEntity> insertList = ObjectUtils.convert(adgroupDTOs, getEntityClass());
        getMongoTemplate().insertAll(insertList);
    }

    @SuppressWarnings("unchecked")
    public void update(AdgroupDTO adgroupDTO) {
        Long id = adgroupDTO.getAdgroupId();
        Query query = new Query();
        query.addCriteria(Criteria.where(ADGROUP_ID).is(id));
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
        getMongoTemplate().remove(new Query(Criteria.where(ADGROUP_ID).is(adgroupId)), getEntityClass());
        deleteSub(new ArrayList<Long>(1) {{
            add(adgroupId);
        }});
    }

    public int deleteByIds(List<Long> adgroupIds) {
        WriteResult writeResult = getMongoTemplate().remove(new Query(Criteria.where(ADGROUP_ID).in(adgroupIds)), getEntityClass());
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
        up.set("ls", 3);
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.updateMulti(new Query(Criteria.where(ADGROUP_ID).in(agid)), up, getEntityClass());
        mongoTemplate.updateMulti(new Query(Criteria.where(ADGROUP_ID).in(agid)), up, KeywordEntity.class);
        mongoTemplate.updateMulti(new Query(Criteria.where(ADGROUP_ID).in(agid)), up, CreativeEntity.class);
    }

    private void deleteSub(List<Long> adgroupIds) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(new Query(Criteria.where(ADGROUP_ID).in(adgroupIds)), KeywordEntity.class);
        mongoTemplate.remove(new Query(Criteria.where(ADGROUP_ID).in(adgroupIds)), CreativeEntity.class);
    }

    /**
     * 级联删除，删除单元下的创意和关键字
     *
     * @param oid
     */
    private void deleteSubOid(String oid) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(new Query(Criteria.where(OBJ_ADGROUP_ID).is(oid)), KeywordEntity.class);
        mongoTemplate.remove(new Query(Criteria.where(OBJ_ADGROUP_ID).is(oid)), CreativeEntity.class);
    }

//    /**
//     * 根据删除的单元删除其下的关键词和创意，该删除可以拥有还原功能，实际上是将创意和关键字放入备份数据库中，如果还原创单元，则级联的
//     *
//     * @param agid
//     */
//    private void deleteLinked(Long agid) {
//        Update up = new Update();
//        up.set("ls", 3);
//        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
//        mongoTemplate.updateMulti(new Query(Criteria.where(ADGROUP_ID).in(agid)), up, KeywordEntity.class, TBL_KEYWORD);
//        mongoTemplate.updateMulti(new Query(Criteria.where(ADGROUP_ID).in(agid)), up, CreativeEntity.class, TBL_CREATIVE);
//    }

    private void subdelBack(Long oid) {
        Update up = new Update();
        up.set("ls", "");
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.updateMulti(new Query(Criteria.where(ADGROUP_ID).in(oid)), up, KeywordEntity.class, TBL_KEYWORD);
        mongoTemplate.updateMulti(new Query(Criteria.where(ADGROUP_ID).in(oid)), up, CreativeEntity.class, TBL_CREATIVE);
    }

    private void updateSub(Long aid, String oid) {
        Update up = new Update();
        up.set(ADGROUP_ID, aid);
        Query query = new Query(Criteria.where(OBJ_ADGROUP_ID).in(oid));
        getMongoTemplate().updateFirst(query, up, AdgroupEntity.class);
        getMongoTemplate().updateFirst(query, up, CreativeEntity.class);
        getMongoTemplate().updateFirst(query, up, KeywordEntity.class);
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

    private Query searchFilterQueryOperate(Query q, SearchFilterParam sp) {
        if (sp != null) {
            switch (sp.getFilterField()) {
                case "name":
                    getNormalQuery(q, sp.getFilterField(), sp.getSelected(), sp.getFilterValue());
                    break;
                case "state":
                    if (sp.getFilterValue().contains(",")) {
                        String[] status = sp.getFilterValue().split(",");
                        Integer[] integers = new Integer[status.length];
                        for (int i = 0; i < integers.length; i++) {
                            integers[i] = Integer.parseInt(status[i]);
                        }
                        q.addCriteria(Criteria.where("s").in(integers));
                    } else {
                        q.addCriteria(Criteria.where("s").is(Integer.valueOf(sp.getFilterValue())));
                    }
                    break;
                case "pause":
                    if (Integer.valueOf(sp.getFilterValue()) != -1) {
                        if (Integer.valueOf(sp.getFilterValue()) == 0) {
                            q.addCriteria(Criteria.where("p").is(false));
                        } else {
                            q.addCriteria(Criteria.where("p").is(true));
                        }
                    }
                    break;
                case "price":
                    String[] prs = sp.getFilterValue().split(",");
                    double starPrice = Double.parseDouble(prs[0]);
                    double endPrice = Double.parseDouble(prs[1]);
                    q.addCriteria(Criteria.where("max").gte(starPrice).lte(endPrice));
                    break;
            }
        }
        return q;
    }

    private void getNormalQuery(Query q, String field, Integer selected, String filterValue) {
        switch (selected) {
            case 1:
                q.addCriteria(Criteria.where(field).
                        regex(Pattern.compile("^.*?" + filterValue + ".*$", Pattern.CASE_INSENSITIVE)));
                break;
            case 11:
                q.addCriteria(Criteria.where(field).
                        regex(Pattern.compile("^(?!.*(" + filterValue + ")).*$", Pattern.CASE_INSENSITIVE)));
                break;
            case 2:
                q.addCriteria(Criteria.where(field).is(filterValue));
                break;
            case 22:
                q.addCriteria(Criteria.where(field).ne(filterValue));
                break;
            case 3:
                q.addCriteria(Criteria.where(field).
                        regex(Pattern.compile("^" + filterValue + ".*$", Pattern.CASE_INSENSITIVE)));
                break;
            case 33:
                q.addCriteria(Criteria.where(field).
                        regex(Pattern.compile(".*" + filterValue + "$", Pattern.CASE_INSENSITIVE)));
                break;
        }
    }
}
