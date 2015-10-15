package com.perfect.db.mongodb.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.adgroup.AdgroupDAO;
import com.perfect.dao.campaign.CampaignBackUpDAO;
import com.perfect.dao.campaign.CampaignDAO;
import com.perfect.dao.sys.LogDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.backup.CampaignBackUpDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.entity.adgroup.AdgroupEntity;
import com.perfect.entity.backup.AdgroupBackUpEntity;
import com.perfect.entity.backup.CampaignBackUpEntity;
import com.perfect.entity.backup.CreativeBackUpEntity;
import com.perfect.entity.backup.KeywordBackUpEntity;
import com.perfect.entity.campaign.CampaignEntity;
import com.perfect.entity.creative.CreativeEntity;
import com.perfect.entity.keyword.KeywordEntity;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.paging.PagerInfo;
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
        Query query = new BasicQuery("{}", "{" + CAMPAIGN_ID + " : 1}");
        List<CampaignEntity> list = getMongoTemplate().find(query, CampaignEntity.class, TBL_CAMPAIGN);

        List<Long> campaignIds = new ArrayList<>(list.size());
        for (CampaignEntity type : list)
            campaignIds.add(type.getCampaignId());
        return campaignIds;
    }

    @Override
    public Class<CampaignDTO> getDTOClass() {
        return null;
    }

    public CampaignDTO findOne(Long campaignId) {
        CampaignEntity campaignEntity = getMongoTemplate().findOne(
                new Query(Criteria.where(CAMPAIGN_ID).is(campaignId)),
                CampaignEntity.class,
                TBL_CAMPAIGN);

        CampaignDTO campaignDTO = new CampaignDTO();
        BeanUtils.copyProperties(campaignEntity, campaignDTO);
        return campaignDTO;
    }


    //xj
    public CampaignDTO findCampaignByName(String name) {
        List<CampaignEntity> campaignEntityList = getMongoTemplate().find(new Query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and("name").is(name)), getEntityClass(), TBL_CAMPAIGN);

        CampaignEntity campaignEntity = campaignEntityList.size() == 0 ? null : campaignEntityList.get(0);

        CampaignDTO campaignDTO = new CampaignDTO();
        BeanUtils.copyProperties(campaignEntity, campaignDTO);
        return campaignDTO;
    }

    public List<CampaignDTO> findAll() {
        List<CampaignEntity> list = getMongoTemplate().find(Query.query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId())), getEntityClass());

        List<CampaignDTO> campaignDTOs = ObjectUtils.convert(list, CampaignDTO.class);
        return campaignDTOs;
    }

    @Override
    public List<CampaignDTO> find(Map<String, Object> params, int skip, int limit, String sort, boolean asc) {
        return null;
    }

    public List<CampaignDTO> find(Map<String, Object> params, int skip, int limit) {
        Query query = new Query();
        if (params != null && params.size() > 0) {
            Criteria criteria = Criteria.where(CAMPAIGN_ID).ne(null);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                criteria.and(entry.getKey()).is(entry.getValue());
            }
            query.addCriteria(criteria);
        }
        query.with(new PageRequest(skip, limit));
        List<CampaignEntity> list = getMongoTemplate().find(query, CampaignEntity.class, TBL_CAMPAIGN);

        List<CampaignDTO> campaignDTOs = ObjectUtils.convert(list, CampaignDTO.class);
        return campaignDTOs;
    }

    //x
    public List<CampaignDTO> find(Query query) {

        List<CampaignEntity> campaignEntities = getMongoTemplate().find(query, CampaignEntity.class);

        List<CampaignDTO> campaignDTOs = ObjectUtils.convert(campaignEntities, CampaignDTO.class);
        return campaignDTOs;
    }

    /**
     * 得到所有已经和百度同步的推广计划
     *
     * @return
     */
    public List<CampaignDTO> findAllDownloadCampaign() {
        Aggregation aggregation = newAggregation(
                match(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and(CAMPAIGN_ID).ne(null)),
                project(ACCOUNT_ID, CAMPAIGN_ID, NAME).andExclude(SYSTEM_ID)
        );
        AggregationResults<CampaignEntity> results = getMongoTemplate().aggregate(aggregation, TBL_CAMPAIGN, getEntityClass());

        List<CampaignDTO> campaignDTOs = ObjectUtils.convert(results.getMappedResults(), CampaignDTO.class);
        return campaignDTOs;
    }

    @Override
    public List<CampaignDTO> findDownloadCampaignsByBaiduAccountId(Long baiduAccountId) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where(ACCOUNT_ID).is(baiduAccountId).and(CAMPAIGN_ID).ne(null)),
                project(ACCOUNT_ID, CAMPAIGN_ID, NAME).andExclude(SYSTEM_ID)
        );
        AggregationResults<CampaignEntity> results = getMongoTemplate().aggregate(aggregation, TBL_CAMPAIGN, getEntityClass());

        return ObjectUtils.convert(results.getMappedResults(), CampaignDTO.class);
    }

    @Override
    public List<CampaignDTO> findHasLocalStatus() {
        List<CampaignEntity> campaignEntityList = getMongoTemplate().find(new Query(Criteria.where("ls").ne(null).and(ACCOUNT_ID).is(AppContext.getAccountId())), getEntityClass());
        return ObjectUtils.convert(campaignEntityList, CampaignDTO.class);
    }

    @Override
    public List<CampaignDTO> findLocalChangedCampaigns(Long baiduAccountId, int type) {
        List<CampaignEntity> campaignEntityList = getMongoTemplate()
                .find(Query.query(Criteria.where("ls").is(type).and(ACCOUNT_ID).is(baiduAccountId)), getEntityClass());
        return ObjectUtils.convert(campaignEntityList, CampaignDTO.class);
    }

    @Override
    public List<CampaignDTO> findHasLocalStatusByStrings(List<String> cids) {
        List<CampaignEntity> campaignEntityList = getMongoTemplate().find(new Query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and(SYSTEM_ID).in(cids)), getEntityClass());
        return ObjectUtils.convert(campaignEntityList, CampaignDTO.class);
    }

    @Override
    public List<CampaignDTO> findHasLocalStatusByLongs(List<Long> cids) {
        List<CampaignEntity> campaignEntityList = getMongoTemplate().find(new Query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and(CAMPAIGN_ID).in(cids)), getEntityClass());
        return ObjectUtils.convert(campaignEntityList, CampaignDTO.class);
    }

    @Override
    public CampaignDTO findByLongId(Long cid) {
        CampaignEntity campaignEntity = getMongoTemplate().findOne(Query.query(Criteria.where(CAMPAIGN_ID).is(cid)), getEntityClass());
        CampaignDTO campaignDTO = new CampaignDTO();
        if (campaignEntity != null) {
            BeanUtils.copyProperties(campaignEntity, campaignDTO);
        }
        return campaignDTO;
    }

    @Override
    public CampaignDTO findByObjectId(String oid) {
        CampaignEntity campaignEntity = getMongoTemplate().findOne(Query.query(Criteria.where(SYSTEM_ID).is(oid)), getEntityClass());
        CampaignDTO campaignDTO = new CampaignDTO();
        if (campaignEntity != null) {
            BeanUtils.copyProperties(campaignEntity, campaignDTO);
        }
        return campaignDTO;
    }

    @Override
    public void pause(Long baiduAccountId) {
        // TODO 暂停投放
//        getMongoTemplate()
//                .updateMulti(
//                        Query.query(Criteria.where("_id").in(baiduAccountId)),
//                        Update.update("p", true),
//                        getEntityClass());
    }


    public void insert(CampaignDTO campaignDTO) {
        CampaignEntity campaignEntity = new CampaignBackUpEntity();
        if (campaignDTO != null) {
            BeanUtils.copyProperties(campaignDTO, campaignEntity);
        }
        getMongoTemplate().insert(campaignEntity, TBL_CAMPAIGN);
    }


    /**
     * 插入推广计划并返回该id
     *
     * @param campaignDTO
     * @return
     */
    public String insertReturnId(CampaignDTO campaignDTO) {

        CampaignEntity campaignEntity = new CampaignBackUpEntity();
        BeanUtils.copyProperties(campaignDTO, campaignEntity);

        if (!getMongoTemplate().exists(new Query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and(NAME).is(campaignDTO.getCampaignName())), getEntityClass())) {
            getMongoTemplate().insert(campaignEntity, TBL_CAMPAIGN);
        }
        return campaignEntity.getId();
    }


    public void insertAll(List<CampaignDTO> entities) {
        List<CampaignEntity> campaignEntities = ObjectUtils.convert(entities, CampaignEntity.class);
        getMongoTemplate().insertAll(campaignEntities);
    }

    /**
     * 推广计划的软删除
     *
     * @param cid
     */
    public void updateLocalstatu(long cid) {
        getMongoTemplate().updateFirst(new Query(Criteria.where(CAMPAIGN_ID).is(cid)), Update.update("ls", ""), TBL_CAMPAIGN);
        List<AdgroupDTO> list = adgroupDAO.findByCampaignId(cid);
        for (AdgroupDTO adgroupDTO : list) {
            adgroupDAO.delBack(adgroupDTO.getAdgroupId());
        }

    }

    @SuppressWarnings("unchecked")
    public void update(CampaignDTO campaignDTO) {
        Long id = campaignDTO.getCampaignId();
        Query query = new Query();
        query.addCriteria(Criteria.where(CAMPAIGN_ID).is(id));
        Update update = new Update();
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
        getMongoTemplate().updateFirst(query, update, getEntityClass(), TBL_CAMPAIGN);
    }

    @Override
    public void update(CampaignDTO dto, String objId) {
        Update update = new Update();
        update.set("cid", dto.getCampaignId());
        update.set("ls", null);
        update.set("p", dto.getPause());
        update.set("s", dto.getStatus());
        Query q = new Query(Criteria.where(SYSTEM_ID).is(objId));
        getMongoTemplate().updateFirst(q, update, CampaignEntity.class);
        // 计划更新后，计划下的单元的ocid 中的值要去掉，再将该单元的cid设置为从百度获取到的id
        updateSub(dto.getCampaignId(), objId);
    }

    //54bcd1e3593f6a25cfe4e2da
    @Override
    public void deleteByCampaignId(Long campaginId) {

        Query q = new Query(Criteria.where(CAMPAIGN_ID).is(campaginId));
        getMongoTemplate().remove(q, CampaignEntity.class);
        getMongoTemplate().remove(q, CampaignBackUpEntity.class);
        deleteSubByUpload(new ArrayList<Long>() {{
            add(campaginId);
        }});

    }

    @Override
    public void updateRemoveLs(List<String> afterUpdateStr) {
        afterUpdateStr.stream().forEach(s -> {
            Update up = new Update();
            up.set("ls", null);
            getMongoTemplate().updateFirst(new Query(Criteria.where(SYSTEM_ID).is(s)), up, CampaignEntity.class);
        });
    }

    @Override
    public List<CampaignDTO> getOperateCamp() {
        Query query = new BasicQuery("{}", "{" + CAMPAIGN_ID + " : 1," + NAME + " : 1," + SYSTEM_ID + ":1}");
        query.addCriteria(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()));
        List<CampaignEntity> list = getMongoTemplate().find(query, CampaignEntity.class);
        List<CampaignDTO> campaignDTOs = ObjectUtils.convert(list, CampaignDTO.class);
        return campaignDTOs;

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
        query.addCriteria(Criteria.where(SYSTEM_ID).is(newCampaign.getId()));
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
                update.set(field.getName(), after);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        if (newCampaign.getCampaignName().equals(campaignEntity.getCampaignName())) {
            getMongoTemplate().updateFirst(query, update, CampaignEntity.class, TBL_CAMPAIGN);
            CampaignBackUpDTO campaignBackUpDTOFind = campaignBackUpDAO.findByObjectId(newCampaign.getId());
            if (campaignBackUpDTOFind == null && newCampaign.getLocalStatus() == 2) {
                CampaignBackUpEntity backUpEntity = new CampaignBackUpEntity();
                BeanUtils.copyProperties(campaignEntity, backUpEntity);
                getMongoTemplate().insert(backUpEntity);
            }
        } else {
            if (!getMongoTemplate().exists(new Query(Criteria.where(NAME).is(newCampaign.getCampaignName()).and(ACCOUNT_ID).is(AppContext.getAccountId())), getEntityClass())) {
                getMongoTemplate().updateFirst(query, update, CampaignEntity.class, TBL_CAMPAIGN);
                CampaignBackUpDTO campaignBackUpDTOFind = campaignBackUpDAO.findByObjectId(newCampaign.getId());
                if (campaignBackUpDTOFind == null && newCampaign.getLocalStatus() == 2) {
                    CampaignBackUpEntity backUpEntity = new CampaignBackUpEntity();
                    BeanUtils.copyProperties(campaignEntity, backUpEntity);
                    getMongoTemplate().insert(backUpEntity);
                }
            }
        }
    }

    @Override
    public PagerInfo findByPageInfo(Long accountId, int pageSize, int pageNo) {
        Query q = new Query().addCriteria(Criteria.where(ACCOUNT_ID).is(accountId));
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


    public void update(List<CampaignDTO> entities) {
        for (CampaignDTO entity : entities)
            update(entity);
    }

    public void deleteById(final Long campaignId) {
        getMongoTemplate().remove(
                new Query(Criteria.where(CAMPAIGN_ID).is(campaignId)), CampaignEntity.class, TBL_CAMPAIGN);
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
                new Query(Criteria.where(SYSTEM_ID).is(campaignId)), CampaignEntity.class, TBL_CAMPAIGN);
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
        getMongoTemplate().updateFirst(new Query(Criteria.where(CAMPAIGN_ID).is(cid)), Update.update("localStatus", 3), getEntityClass(), TBL_CAMPAIGN);

        List<AdgroupDTO> list = adgroupDAO.findByCampaignId(cid);
        List<Long> ids = new ArrayList<>();
        for (AdgroupDTO adgroupDTO : list) {
            ids.add(adgroupDTO.getAdgroupId());
        }
        adgroupDAO.deleteLinkedByAgid(ids);
    }


    public int deleteByIds(List<Long> campaignIds) {
//        getMongoTemplate().remove(
//                new Query(Criteria.where(CAMPAIGN_ID).in(campaignIds)), CampaignEntity.class, TBL_CAMPAIGN);
//
        deleteSub(campaignIds);
        return super.deleteByIds(campaignIds);

    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<CampaignEntity> getEntityClass() {
        return CampaignEntity.class;
    }

    @Override
    public String getId() {
        return CAMPAIGN_ID;
    }

    public boolean delete(CampaignDTO campaignDTO) {
        deleteById(campaignDTO.getCampaignId());
        return false;
    }

    public boolean deleteAll() {
        getMongoTemplate().dropCollection(TBL_CAMPAIGN);
        deleteSub(getAllCampaignId());
        return false;
    }


    //xj
    public int getListTotalCount(Query q) {
        return (int) getMongoTemplate().count(q, (TBL_CAMPAIGN));
    }


    private List<Long> getAdgroupIdByCampaignId(List<Long> campaignIds) {
        Query query = new BasicQuery("{}", "{" + ADGROUP_ID + " : 1}");
        query.addCriteria(Criteria.where(CAMPAIGN_ID).in(campaignIds));
        List<AdgroupEntity> list = getMongoTemplate().find(query, AdgroupEntity.class, TBL_ADGROUP);
        List<Long> adgroupIds = new ArrayList<>(list.size());
        for (AdgroupEntity type : list)
            adgroupIds.add(type.getAdgroupId());
        return adgroupIds;
    }

    private List<String> getAdgroupIdByCampaignStrId(List<Long> campaignIds) {
        Query query = new BasicQuery("{}", "{" + SYSTEM_ID + " : 1}");
        query.addCriteria(Criteria.where(CAMPAIGN_ID).in(campaignIds));
        List<AdgroupEntity> list = getMongoTemplate().find(query, AdgroupEntity.class, TBL_ADGROUP);
        List<String> adgroupIds = new ArrayList<>(list.size());
        for (AdgroupEntity type : list)
            adgroupIds.add(type.getId());
        return adgroupIds;
    }

    private List<Long> getAdgroupByCampaingLongId(List<Long> campaignIds) {
        Query query = new BasicQuery("{}", "{" + ADGROUP_ID + " : 1}");
        query.addCriteria(Criteria.where(CAMPAIGN_ID).in(campaignIds));
        List<AdgroupEntity> list = getMongoTemplate().find(query, AdgroupEntity.class, TBL_ADGROUP);
        List<Long> adgroupIds = new ArrayList<>(list.size());
        for (AdgroupEntity type : list)
            adgroupIds.add(type.getAdgroupId());
        return adgroupIds;
    }

    public List<String> getCampaignStrIdByCampaignLongId(List<Long> campaignIds) {
        Query query = new BasicQuery("{}", "{" + SYSTEM_ID + " : 1}");
        query.addCriteria(Criteria.where(CAMPAIGN_ID).in(campaignIds));
        List<CampaignEntity> list = getMongoTemplate().find(query, CampaignEntity.class);
        List<String> campaginIds = new ArrayList<>(list.size());
        for (CampaignEntity type : list)
            campaginIds.add(type.getId());
        return campaginIds;
    }

    //删除下级内容
    private void deleteSub(List<Long> campaignIds) {
        List<Long> adgroupIds = getAdgroupIdByCampaignId(campaignIds);
        getMongoTemplate().remove(new Query(Criteria.where(ADGROUP_ID).in(adgroupIds)), AdgroupEntity.class, TBL_ADGROUP);
        getMongoTemplate().remove(new Query(Criteria.where(ADGROUP_ID).in(adgroupIds)), KeywordEntity.class, TBL_KEYWORD);
        getMongoTemplate().remove(new Query(Criteria.where(ADGROUP_ID).in(adgroupIds)), CreativeEntity.class, TBL_CREATIVE);
    }

    //删除下级内容
    private void deleteSubByUpload(List<Long> campaignIds) {
        List<String> adgroupStrIds = getAdgroupIdByCampaignStrId(campaignIds);
        List<Long> adgroupLongIds = getAdgroupByCampaingLongId(campaignIds);

        //先删除与本地id相关的下级条目
        getMongoTemplate().remove(new Query(Criteria.where(SYSTEM_ID).in(adgroupStrIds)), AdgroupEntity.class, TBL_ADGROUP);
        getMongoTemplate().remove(new Query(Criteria.where(OBJ_ADGROUP_ID).in(adgroupStrIds)), KeywordEntity.class, TBL_KEYWORD);
        getMongoTemplate().remove(new Query(Criteria.where(OBJ_ADGROUP_ID).in(adgroupStrIds)), CreativeEntity.class, TBL_CREATIVE);

        //再删除与百度id相关的下级条目
        getMongoTemplate().remove(new Query(Criteria.where(ADGROUP_ID).in(adgroupLongIds)), AdgroupEntity.class);
        getMongoTemplate().remove(new Query(Criteria.where(ADGROUP_ID).in(adgroupLongIds)), AdgroupBackUpEntity.class);
        getMongoTemplate().remove(new Query(Criteria.where(ADGROUP_ID).in(adgroupLongIds)), KeywordEntity.class);
        getMongoTemplate().remove(new Query(Criteria.where(ADGROUP_ID).in(adgroupLongIds)), KeywordBackUpEntity.class);
        getMongoTemplate().remove(new Query(Criteria.where(ADGROUP_ID).in(adgroupLongIds)), CreativeEntity.class);
        getMongoTemplate().remove(new Query(Criteria.where(ADGROUP_ID).in(adgroupLongIds)), CreativeBackUpEntity.class);

    }


    //根据mongodbId删除下级内容
    private void deleteSubByObjectId(List<String> ids) {
        List<String> adgroupIds = adgroupDAO.getObjAdgroupIdByCampaignId(ids);
        getMongoTemplate().remove(new Query(Criteria.where(SYSTEM_ID).in(adgroupIds)), AdgroupEntity.class, TBL_ADGROUP);
        getMongoTemplate().remove(new Query(Criteria.where(OBJ_ADGROUP_ID).in(adgroupIds)), KeywordEntity.class, TBL_KEYWORD);
        getMongoTemplate().remove(new Query(Criteria.where(OBJ_ADGROUP_ID).in(adgroupIds)), CreativeEntity.class, TBL_CREATIVE);
    }

    /**
     * 要更新到本地有关联的单元，单元修改需修改关键字和创意的关联
     *
     * @param campaignId 从百度获取到的id
     * @param objId      计划的mongodb Id
     */
    private void updateSub(Long campaignId, String objId) {
        Update up = new Update();
        up.set(CAMPAIGN_ID, campaignId);
        Query query = new Query(Criteria.where(OBJ_CAMPAIGN_ID).in(objId));
        getMongoTemplate().updateFirst(query, up, AdgroupEntity.class);


//        getMongoTemplate().updateFirst(query,up,CreativeEntity.class);
//        //根据计划的本地id获取到本地关联到的单元id列表
//        List<AdgroupEntity> adgroupEntities=getMongoTemplate().find(query,AdgroupEntity.class);
//        List<String> adgroupIds=new ArrayList<>();
//        adgroupEntities.parallelStream().forEach(s->adgroupIds.add(s.getId()));

    }

}
