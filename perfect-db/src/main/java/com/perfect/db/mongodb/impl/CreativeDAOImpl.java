package com.perfect.db.mongodb.impl;

import com.perfect.commons.constants.LogStatusConstant;
import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.core.AppContext;
import com.perfect.dao.creative.CreativeBackUpDAO;
import com.perfect.dao.creative.CreativeDAO;
import com.perfect.dao.sys.LogDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.backup.CreativeBackUpDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.entity.creative.CreativeEntity;
import com.perfect.entity.backup.CreativeBackUpEntity;
import com.perfect.ObjectUtils;
import com.perfect.paging.Pager;
import com.perfect.paging.PagerInfo;
import org.springframework.beans.BeanUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.perfect.commons.constants.MongoEntityConstants.*;

/**
 * Created by baizz on 2014-07-10.
 */
@Repository("creativeDAO")
public class CreativeDAOImpl extends AbstractUserBaseDAOImpl<CreativeDTO, Long> implements CreativeDAO {

    @Resource
    private LogDAO logDAO;
    @Resource
    private CreativeBackUpDAO bakcUpDAO;

    public List<Long> getCreativeIdByAdgroupId(Long adgroupId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new BasicQuery("{}", "{" + CREATIVE_ID + " : 1}");
        query.addCriteria(Criteria.where(ADGROUP_ID).is(adgroupId));
        List<CreativeEntity> types = mongoTemplate.find(query, CreativeEntity.class, TBL_CREATIVE);
        List<Long> creativeIds = new ArrayList<>(types.size());
        for (CreativeEntity type : types)
            creativeIds.add(type.getCreativeId());
        return creativeIds;
    }

    @Override
    public List<CreativeDTO> findByAgroupId(Long adgroupId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<CreativeEntity> creativeEntityList = mongoTemplate.find(new Query(Criteria.where(ADGROUP_ID).is(adgroupId)), CreativeEntity.class, TBL_CREATIVE);
        List<CreativeDTO> returnList = ObjectUtils.convert(creativeEntityList, CreativeDTO.class);
        return returnList;
    }

    public List<CreativeDTO> getCreativeByAdgroupId(Long adgroupId, Map<String, Object> params, int skip, int limit) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new Query();
        Criteria criteria = Criteria.where(ADGROUP_ID).is(adgroupId);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet())
                criteria.and(entry.getKey()).is(entry.getValue());
        }
        query.addCriteria(criteria);
        query.with(new PageRequest(skip, limit));
        List<CreativeEntity> list = mongoTemplate.find(query, CreativeEntity.class, TBL_CREATIVE);
        return wrapperList(list);
    }

    @Override
    public List<CreativeDTO> getCreativeByAdgroupId(String adgroupId, Map<String, Object> params, int skip, int limit) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new Query();
        Criteria criteria = Criteria.where(OBJ_ADGROUP_ID).is(adgroupId);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet())
                criteria.and(entry.getKey()).is(entry.getValue());
        }
        query.addCriteria(criteria);
        query.with(new PageRequest(skip, limit));
        List<CreativeEntity> list = mongoTemplate.find(query, CreativeEntity.class, TBL_CREATIVE);
        return wrapperList(list);
    }

    @Override
    public List<CreativeDTO> getAllsByAdgroupIds(List<Long> l) {
        List<CreativeEntity> list= BaseMongoTemplate.getUserMongo().find(new Query(Criteria.where(ADGROUP_ID).in(l)), CreativeEntity.class, TBL_CREATIVE);
        return wrapperList(list);
    }

    @Override
    public List<CreativeDTO> getAllsByAdgroupIdsForString(List<String> l) {
        List<CreativeEntity> list= BaseMongoTemplate.getUserMongo().find(new Query(Criteria.where(OBJ_ADGROUP_ID).in(l)), CreativeEntity.class, TBL_CREATIVE);
        return wrapperList(list);
    }

    @Override
    public CreativeDTO getAllsBySomeParams(Map<String, Object> params) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query q = new Query();
        Criteria c = new Criteria();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                c.and(entry.getKey()).is(entry.getValue());
            }
        }
        q.addCriteria(c);
        CreativeEntity creativeEntity = mongoTemplate.findOne(q, CreativeEntity.class, TBL_CREATIVE);
        return wrapperObject(creativeEntity);
    }

    @Override
    public void deleteByCacheId(Long objectId) {
        Update update = new Update();
        update.set("ls", 3);
        BaseMongoTemplate.getUserMongo().updateFirst(new Query(Criteria.where(CREATIVE_ID).is(objectId)), update, CreativeEntity.class, TBL_CREATIVE);
        //以前是直接删除拉取到本地的数据，是硬删除，现在改为软删除，以便以后还原操作
//        BaseMongoTemplate.getUserMongo().remove(new Query(Criteria.where(CREATIVE_ID).is(objectId)), CreativeEntity.class, TBL_CREATIVE);
        logDAO.insertLog(objectId, LogStatusConstant.ENTITY_CREATIVE, LogStatusConstant.OPT_DELETE);
    }

    @Override
    public void deleteByCacheId(String cacheCreativeId) {
        BaseMongoTemplate.getUserMongo().remove(new Query(Criteria.where(getId()).is(cacheCreativeId)), CreativeEntity.class, TBL_CREATIVE);
        BaseMongoTemplate.getUserMongo().remove(new Query(Criteria.where(getId()).is(cacheCreativeId)), CreativeBackUpEntity.class, BAK_CREATIVE);
        logDAO.insertLog(cacheCreativeId, LogStatusConstant.ENTITY_CREATIVE);
    }

    @Override
    public String insertOutId(CreativeDTO creativeDTO) {
        CreativeEntity creativeEntity=new CreativeEntity();
        BeanUtils.copyProperties(creativeDTO,creativeEntity);
        getMongoTemplate().insert(creativeEntity,MongoEntityConstants.TBL_CREATIVE);
        CreativeBackUpEntity backUpEntity = new CreativeBackUpEntity();
        BeanUtils.copyProperties(creativeDTO, backUpEntity);
        getMongoTemplate().insert(backUpEntity, MongoEntityConstants.BAK_CREATIVE);
        logDAO.insertLog(creativeDTO.getId(), LogStatusConstant.ENTITY_CREATIVE);
        return creativeDTO.getId();
    }

    @Override
    public void insertByReBack(CreativeDTO creativeDTO) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(new Query(Criteria.where(getId()).is(creativeDTO.getId())), CreativeEntity.class, TBL_CREATIVE);
        CreativeEntity creativeEntity=new CreativeEntity();
        BeanUtils.copyProperties(creativeDTO,creativeEntity);
        getMongoTemplate().insert(creativeEntity, TBL_CREATIVE);
    }

    @Override
    public CreativeDTO findByObjId(String obj) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        CreativeEntity entity = mongoTemplate.findOne(
                new Query(Criteria.where(getId()).is(obj)), CreativeEntity.class, TBL_CREATIVE);
        CreativeDTO creativeDTO=new CreativeDTO();
        BeanUtils.copyProperties(entity,creativeDTO);
        return creativeDTO;
    }

    @Override
    public void updateByObjId(CreativeDTO creativeDTO) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Update up = new Update();
        up.set("t", creativeDTO.getTitle());
        up.set("desc1", creativeDTO.getDescription1());
        up.set("desc2", creativeDTO.getDescription2());
        up.set("pc", creativeDTO.getPcDestinationUrl());
        up.set("pcd", creativeDTO.getPcDisplayUrl());
        up.set("p", creativeDTO.getPause());
        up.set("s", creativeDTO.getStatus());
        up.set("m", creativeDTO.getMobileDestinationUrl());
        up.set("d", creativeDTO.getDevicePreference());
        up.set("md", creativeDTO.getMobileDisplayUrl());
        mongoTemplate.updateFirst(new Query(Criteria.where(getId()).is(creativeDTO.getId())), up, CreativeEntity.class, TBL_CREATIVE);
        logDAO.insertLog(creativeDTO.getId(), LogStatusConstant.ENTITY_CREATIVE);
    }

    @Override
    public void update(CreativeDTO newCreativeDTO, CreativeDTO creativeBackUpDTO) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Long id = newCreativeDTO.getCreativeId();
        Query query = new Query();
        query.addCriteria(Criteria.where(CREATIVE_ID).is(id));
        Update update = new Update();
        try {
            Class _class = newCreativeDTO.getClass();
            Field[] fields = _class.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                if ("creativeId".equals(fieldName))
                    continue;
                StringBuilder fieldGetterName = new StringBuilder("get");
                fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
                Method method = _class.getDeclaredMethod(fieldGetterName.toString());
                Object after = method.invoke(newCreativeDTO);
                if (after != null) {
                    update.set(field.getName(), after);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        mongoTemplate.updateFirst(query, update, CreativeEntity.class, TBL_CREATIVE);
        CreativeBackUpDTO creativeBackUpDTOFind = bakcUpDAO.findByStringId(newCreativeDTO.getId());
        if (creativeBackUpDTOFind == null) {
            CreativeBackUpEntity backUpEntity = new CreativeBackUpEntity();
            BeanUtils.copyProperties(creativeBackUpDTO, backUpEntity);
            getMongoTemplate().insert(backUpEntity, BAK_CREATIVE);
        }
        logDAO.insertLog(id, LogStatusConstant.ENTITY_CREATIVE, LogStatusConstant.OPT_UPDATE);
    }


    @Override
    public void updateAdgroupIdByOid(String id, Long adgroupId) {
        getMongoTemplate().updateMulti(Query.query(Criteria.where(OBJ_ADGROUP_ID).is(id)), Update.update(ADGROUP_ID, adgroupId).set(OBJ_ADGROUP_ID, null), getEntityClass());
    }


    @Override
    public void delBack(Long oid) {
        Update update = new Update();
        update.set("ls", "");
        BaseMongoTemplate.getUserMongo().updateFirst(new Query(Criteria.where(CREATIVE_ID).is(oid)), update, CreativeEntity.class, TBL_CREATIVE);
    }

    @Override
    public PagerInfo findByPagerInfo(Map<String, Object> params, Integer nowPage, Integer pageSize) {
        Query q = new Query();
        Criteria c = new Criteria();
        if (params.size() > 0 || params != null) {
            for (Map.Entry<String, Object> cri : params.entrySet()) {
                c.and(cri.getKey()).is(cri.getValue());
            }
            q.addCriteria(c);
        }
        Integer totalCount = getTotalCount(q, CreativeEntity.class);
        PagerInfo p = new PagerInfo(nowPage, pageSize, totalCount);
        q.skip(p.getFirstStation());
        q.limit(p.getPageSize());
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        List<CreativeEntity> creativeEntityList = getMongoTemplate().find(q, CreativeEntity.class);
        p.setList(creativeEntityList);
        return p;
    }

    @Override
    public PagerInfo findByPagerInfoForString(List<String> l, Integer nowPage, Integer pageSize) {
        Query q = new Query(Criteria.where(OBJ_ADGROUP_ID).in(l));
        Integer totalCount = getTotalCount(q, CreativeEntity.class);
        PagerInfo p = new PagerInfo(nowPage, pageSize, totalCount);
        q.skip(p.getFirstStation());
        q.limit(p.getPageSize());
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        List<CreativeEntity> creativeEntityList = getMongoTemplate().find(q, CreativeEntity.class);
        p.setList(creativeEntityList);
        return p;
    }

    @Override
    public PagerInfo findByPagerInfoForLong(List<Long> l, Integer nowPage, Integer pageSize) {
        Query q = new Query(Criteria.where(ADGROUP_ID).in(l));
        Integer totalCount = getTotalCount(q, CreativeEntity.class);
        PagerInfo p = new PagerInfo(nowPage, pageSize, totalCount);
        q.skip(p.getFirstStation());
        q.limit(p.getPageSize());
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        List<CreativeEntity> creativeEntityList = getMongoTemplate().find(q, CreativeEntity.class);
        p.setList(creativeEntityList);
        return p;
    }

    @Override
    public PagerInfo findByPagerInfo(Long l, Integer nowPage, Integer pageSize) {
        Query q = new Query(Criteria.where(ADGROUP_ID).in(l));
        Integer totalCount = getTotalCount(q, CreativeEntity.class);
        PagerInfo p = new PagerInfo(nowPage, pageSize, totalCount);
        q.skip(p.getFirstStation());
        q.limit(p.getPageSize());
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        List<CreativeEntity> creativeEntityList = getMongoTemplate().find(q, CreativeEntity.class);
        p.setList(creativeEntityList);
        return p;
    }

    private Integer getTotalCount(Query q, Class<?> cls) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        return (int) mongoTemplate.count(q, cls);
    }

    @Override
    public Class<CreativeDTO> getDTOClass() {
        return CreativeDTO.class;
    }

    public CreativeDTO findOne(Long creativeId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        CreativeEntity entity = mongoTemplate.findOne(new Query(Criteria.where(CREATIVE_ID).is(creativeId)), CreativeEntity.class, TBL_CREATIVE);
        return  wrapperObject(entity);
    }

    public List<CreativeDTO> findAll() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<CreativeEntity> entityList = mongoTemplate.find(Query.query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId())), CreativeEntity.class);
        return wrapperList(entityList);
    }

    @Override
    public List<CreativeDTO> find(Map<String, Object> params, int skip, int limit, String sort, boolean asc) {
        return null;
    }

    public List<CreativeDTO> find(Map<String, Object> params, int skip, int limit) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new Query();
        if (params != null && params.size() > 0) {
            Criteria criteria = Criteria.where(CREATIVE_ID).ne(null);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                criteria.and(entry.getKey()).is(entry.getValue());
            }
            query.addCriteria(criteria);
        }
        query.with(new PageRequest(skip, limit));
        List<CreativeEntity> list = mongoTemplate.find(query, CreativeEntity.class, TBL_CREATIVE);
        return wrapperList(list);
    }

    public void insert(CreativeDTO creativeDTO) {
        CreativeEntity creativeEntity=new CreativeEntity();
        BeanUtils.copyProperties(creativeDTO,creativeEntity);
        getMongoTemplate().insert(creativeEntity,MongoEntityConstants.TBL_CREATIVE);
    }

    public void insertAll(List<CreativeDTO> creativeDTOList) {
        List<CreativeEntity> creativeEntityList=ObjectUtils.convert(creativeDTOList,CreativeEntity.class);
        getMongoTemplate().insertAll(creativeEntityList);
    }

    @SuppressWarnings("unchecked")
    public void update(CreativeDTO creativeDTO) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Long id = creativeDTO.getCreativeId();
        Query query = new Query();
        query.addCriteria(Criteria.where(CREATIVE_ID).is(id));
        Update update = new Update();
        try {
            Class _class = creativeDTO.getClass();
            Field[] fields = _class.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                if ("creativeId".equals(fieldName))
                    continue;
                StringBuilder fieldGetterName = new StringBuilder("get");
                fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
                Method method = _class.getDeclaredMethod(fieldGetterName.toString());
                Object after = method.invoke(creativeDTO);
                if (after != null) {
                    update.set(field.getName(), after);
                    Object before = method.invoke(findOne(id));
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        mongoTemplate.updateFirst(query, update, CreativeEntity.class, TBL_CREATIVE);
    }

    public void deleteById(Long creativeId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(new Query(Criteria.where(CREATIVE_ID).is(creativeId)), CreativeEntity.class, TBL_CREATIVE);
    }

    @Override
    public Class<CreativeDTO> getEntityClass() {
        return CreativeDTO.class;
    }

    public void delete(CreativeDTO creativeDTO) {
        deleteById(creativeDTO.getCreativeId());
    }


    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public List<CreativeDTO> find(Map<String, Object> params, String fieldName, String q, int skip, int limit, String sort, boolean asc) {
        return null;
    }


    private List<CreativeDTO> wrapperList(List<CreativeEntity> list) {
        return ObjectUtils.convert(list, CreativeDTO.class);
    }

    private CreativeDTO wrapperObject(CreativeEntity entity) {
        CreativeDTO dto = new CreativeDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
