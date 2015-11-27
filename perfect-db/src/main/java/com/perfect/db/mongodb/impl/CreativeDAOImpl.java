package com.perfect.db.mongodb.impl;

import com.perfect.commons.constants.LogStatusConstant;
import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.core.AppContext;
import com.perfect.dao.creative.CreativeBackUpDAO;
import com.perfect.dao.creative.CreativeDAO;
import com.perfect.dao.sys.LogDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dto.backup.CreativeBackUpDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.entity.backup.CreativeBackUpEntity;
import com.perfect.entity.backup.KeywordBackUpEntity;
import com.perfect.entity.creative.CreativeEntity;
import com.perfect.entity.keyword.KeywordEntity;
import com.perfect.param.SearchFilterParam;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.paging.PagerInfo;
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
import java.util.Objects;
import java.util.regex.Pattern;

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
        Query query = new BasicQuery("{}", "{" + CREATIVE_ID + " : 1}");
        query.addCriteria(Criteria.where(ADGROUP_ID).is(adgroupId));
        List<CreativeEntity> creativeEntityList = getMongoTemplate().find(query, getEntityClass());
        List<Long> creativeIds = new ArrayList<>(creativeEntityList.size());
        for (CreativeEntity type : creativeEntityList)
            creativeIds.add(type.getCreativeId());
        return creativeIds;
    }

    @Override
    public List<CreativeDTO> findByAgroupId(Long adgroupId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<CreativeEntity> creativeEntityList = mongoTemplate.find(new Query(Criteria.where(ADGROUP_ID).is(adgroupId)), getEntityClass());
        List<CreativeDTO> returnList = ObjectUtils.convert(creativeEntityList, CreativeDTO.class);
        return returnList;
    }

    @Override
    public List<CreativeDTO> findByAdgroupId(Long baiduAccountId, Long adgroupId) {
        List<CreativeEntity> creativeEntityList = getMongoTemplate().find(Query.query(Criteria.where(ACCOUNT_ID).is(baiduAccountId).and(ADGROUP_ID).is(adgroupId)), getEntityClass());
        return ObjectUtils.convert(creativeEntityList, CreativeDTO.class);
    }

    @Override
    public List<CreativeDTO> findByAdgroupId(Long baiduAccountId, String adgroupId) {
        List<CreativeEntity> creativeEntityList = getMongoTemplate().find(Query.query(Criteria.where(ACCOUNT_ID).is(baiduAccountId).and(OBJ_ADGROUP_ID).is(adgroupId)), getEntityClass());
        return ObjectUtils.convert(creativeEntityList, CreativeDTO.class);
    }

    public List<CreativeDTO> getCreativeByAdgroupId(Long adgroupId, Map<String, Object> params, int skip, int limit) {
        Query query = new Query();
        Criteria criteria = Criteria.where(ADGROUP_ID).is(adgroupId);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet())
                criteria.and(entry.getKey()).is(entry.getValue());
        }
        query.addCriteria(criteria);
        query.with(new PageRequest(skip, limit));
        List<CreativeEntity> list = getMongoTemplate().find(query, getEntityClass());
        return wrapperList(list);
    }

    @Override
    public List<CreativeDTO> getCreativeByAdgroupId(String adgroupId, Map<String, Object> params, int skip, int limit) {
        Query query = new Query();
        Criteria criteria = Criteria.where(OBJ_ADGROUP_ID).is(adgroupId);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet())
                criteria.and(entry.getKey()).is(entry.getValue());
        }
        query.addCriteria(criteria);
        query.with(new PageRequest(skip, limit));
        List<CreativeEntity> list = getMongoTemplate().find(query, getEntityClass());
        return wrapperList(list);
    }

    @Override
    public List<CreativeDTO> getAllsByAdgroupIds(List<Long> l) {
        List<CreativeEntity> list = BaseMongoTemplate.getUserMongo().find(new Query(Criteria.where(ADGROUP_ID).in(l)), getEntityClass());
        return wrapperList(list);
    }

    @Override
    public List<CreativeDTO> getAllsByAdgroupIdsForString(List<String> l) {
        List<CreativeEntity> list = BaseMongoTemplate.getUserMongo().find(new Query(Criteria.where(OBJ_ADGROUP_ID).in(l)), getEntityClass());
        return wrapperList(list);
    }

    @Override
    public List<CreativeDTO> findHasLocalStatus() {
        List<CreativeEntity> creativeEntities = getMongoTemplate().find(new Query(Criteria.where("ls").ne(null).and(ACCOUNT_ID).is(AppContext.getAccountId())), getEntityClass());
        return ObjectUtils.convert(creativeEntities, CreativeDTO.class);
    }

    @Override
    public List<CreativeDTO> findHasLocalStatusStr(List<String> strs) {
        List<CreativeEntity> creativeEntities = getMongoTemplate().find(new Query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and(OBJ_ADGROUP_ID).in(strs)), getEntityClass());
        return ObjectUtils.convert(creativeEntities, CreativeDTO.class);
    }

    @Override
    public List<CreativeDTO> findHasLocalStatusLong(List<Long> longs) {
        List<CreativeEntity> creativeEntities = getMongoTemplate().find(new Query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and(ADGROUP_ID).in(longs)), getEntityClass());
        return ObjectUtils.convert(creativeEntities, CreativeDTO.class);
    }

    @Override
    public List<CreativeDTO> findAllCreativeFromBaiduByAdgroupId(Long baiduAccountId, Long adgroupId) {
        List<CreativeEntity> creativeEntities = getMongoTemplate().find(
                Query.query(Criteria.where(ACCOUNT_ID).is(baiduAccountId).and(ADGROUP_ID).is(adgroupId).and("ls").is(null)), getEntityClass());
        return ObjectUtils.convert(creativeEntities, getDTOClass());
    }

    @Override
    public List<CreativeDTO> findLocalChangedCreative(Long baiduAccountId, int type) {
        List<CreativeEntity> creativeEntities = getMongoTemplate().find(new Query(Criteria.where("ls").is(type).and(ACCOUNT_ID).is(baiduAccountId)), getEntityClass());
        return ObjectUtils.convert(creativeEntities, CreativeDTO.class);
    }

    @Override
    public CreativeDTO getAllsBySomeParams(Map<String, Object> params) {
        Query q = new Query();
        Criteria c = new Criteria();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                c.and(entry.getKey()).is(entry.getValue());
            }
        }
        q.addCriteria(c);
        CreativeEntity creativeEntity = getMongoTemplate().findOne(q, getEntityClass());
        CreativeDTO creativeDTO = ObjectUtils.convert(creativeEntity, CreativeDTO.class);
        return creativeDTO;
    }

    @Override
    public void deleteByCacheId(Long objectId) {
        Update update = new Update();
        update.set("ls", 3);
        BaseMongoTemplate.getUserMongo().updateFirst(new Query(Criteria.where(CREATIVE_ID).is(objectId)), update, getEntityClass());
        //以前是直接删除拉取到本地的数据，是硬删除，现在改为软删除，以便以后还原操作
//        BaseMongoTemplate.getUserMongo().remove(new Query(Criteria.where(CREATIVE_ID).is(objectId)), getEntityClass(), TBL_CREATIVE);
        logDAO.insertLog(objectId, LogStatusConstant.ENTITY_CREATIVE, LogStatusConstant.OPT_DELETE);
    }

    @Override
    public void deleteByCacheId(String cacheCreativeId) {
        BaseMongoTemplate.getUserMongo().remove(new Query(Criteria.where(getId()).is(cacheCreativeId)), getEntityClass(), TBL_CREATIVE);
        BaseMongoTemplate.getUserMongo().remove(new Query(Criteria.where(getId()).is(cacheCreativeId)), CreativeBackUpEntity.class, BAK_CREATIVE);
        logDAO.insertLog(cacheCreativeId, LogStatusConstant.ENTITY_CREATIVE);
    }

    @Override
    public String insertOutId(CreativeDTO creativeDTO) {
        CreativeEntity creativeEntity = new CreativeEntity();
        BeanUtils.copyProperties(creativeDTO, creativeEntity);
        getMongoTemplate().insert(creativeEntity);
//        CreativeBackUpEntity backUpEntity = new CreativeBackUpEntity();
//        BeanUtils.copyProperties(creativeDTO, backUpEntity);
//        getMongoTemplate().insert(backUpEntity);
        return creativeEntity.getId();
    }

    @Override
    public void insertByReBack(CreativeDTO creativeDTO) {
        getMongoTemplate().remove(new Query(Criteria.where(getId()).is(creativeDTO.getId())), getEntityClass());
        CreativeEntity creativeEntity = new CreativeEntity();
        BeanUtils.copyProperties(creativeDTO, creativeEntity);
        getMongoTemplate().insert(creativeEntity);
    }

    @Override
    public CreativeDTO findByObjId(String obj) {
        CreativeEntity entity = getMongoTemplate().findOne(new Query(Criteria.where(getId()).is(obj)), getEntityClass());
        CreativeDTO creativeDTO = new CreativeDTO();
        if (entity != null) {
            BeanUtils.copyProperties(entity, creativeDTO);
        }
        return creativeDTO;
    }

    @Override
    public void updateByObjId(CreativeDTO creativeDTO) {
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
        getMongoTemplate().updateFirst(new Query(Criteria.where(getId()).is(creativeDTO.getId())), up, getEntityClass());
        logDAO.insertLog(creativeDTO.getId(), LogStatusConstant.ENTITY_CREATIVE);
    }

    @Override
    public void update(CreativeDTO newCreativeDTO, CreativeDTO creativeBackUpDTO) {
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
        getMongoTemplate().updateFirst(query, update, getEntityClass());
        CreativeBackUpDTO creativeBackUpDTOFind = bakcUpDAO.findByStringId(newCreativeDTO.getId());
        if (creativeBackUpDTOFind.getId() == null) {
            CreativeBackUpEntity backUpEntity = new CreativeBackUpEntity();
            BeanUtils.copyProperties(creativeBackUpDTO, backUpEntity);
            getMongoTemplate().insert(backUpEntity);
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
        BaseMongoTemplate.getUserMongo().updateFirst(new Query(Criteria.where(CREATIVE_ID).is(oid)), update, getEntityClass());
    }

    @Override
    public PagerInfo findByPagerInfo(Map<String, Object> params, Integer nowPage, Integer pageSize, SearchFilterParam sp) {
        Query q = new Query();
        Criteria c = new Criteria();
        if (params.size() > 0 || params != null) {
            for (Map.Entry<String, Object> cri : params.entrySet()) {
                c.and(cri.getKey()).is(cri.getValue());
            }
            q.addCriteria(c);
        }
        Integer totalCount = getTotalCount(q, getEntityClass());
        PagerInfo p = new PagerInfo(nowPage, pageSize, totalCount);
        q.skip(p.getFirstStation());
        q.limit(p.getPageSize());
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        searchFilterQueryOperate(q, sp);
        List<CreativeEntity> creativeEntityList = getMongoTemplate().find(q, getEntityClass());
        p.setList(creativeEntityList);
        return p;
    }

    @Override
    public PagerInfo findByPagerInfoForString(List<String> l, Integer nowPage, Integer pageSize) {
        Query q = new Query(Criteria.where(OBJ_ADGROUP_ID).in(l));
        Integer totalCount = getTotalCount(q, getEntityClass());
        PagerInfo p = new PagerInfo(nowPage, pageSize, totalCount);
        q.skip(p.getFirstStation());
        q.limit(p.getPageSize());
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        List<CreativeEntity> creativeEntityList = getMongoTemplate().find(q, getEntityClass());
        p.setList(creativeEntityList);
        return p;
    }

    @Override
    public PagerInfo findByPagerInfoForLong(List<Long> l, Integer nowPage, Integer pageSize, SearchFilterParam sp) {
        Query q = new Query(Criteria.where(ADGROUP_ID).in(l));
        Integer totalCount = getTotalCount(q, getEntityClass());
        PagerInfo p = new PagerInfo(nowPage, pageSize, totalCount);
        q.skip(p.getFirstStation());
        q.limit(p.getPageSize());
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        searchFilterQueryOperate(q, sp);
        List<CreativeEntity> creativeEntityList = getMongoTemplate().find(q, getEntityClass());
        p.setList(creativeEntityList);
        return p;
    }

    @Override
    public PagerInfo findByPagerInfo(Long l, Integer nowPage, Integer pageSize, SearchFilterParam sp) {
        Query q = new Query(Criteria.where(ADGROUP_ID).in(l));
        Integer totalCount = getTotalCount(q, getEntityClass());
        PagerInfo p = new PagerInfo(nowPage, pageSize, totalCount);
        q.skip(p.getFirstStation());
        q.limit(p.getPageSize());
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        searchFilterQueryOperate(q, sp);
        List<CreativeEntity> creativeEntityList = getMongoTemplate().find(q, getEntityClass());
        p.setList(creativeEntityList);
        return p;
    }

    @Override
    public void update(String crid, CreativeDTO dto) {
        Update up = new Update();
        up.set("ls", null);
        up.set(CREATIVE_ID, dto.getCreativeId());
        up.set("s", dto.getStatus());
        getMongoTemplate().updateFirst(new Query(Criteria.where(SYSTEM_ID).is(crid)), up, CreativeEntity.class);
    }

    @Override
    public void deleteByLongId(Long crid) {
        getMongoTemplate().remove(new Query(Criteria.where(CREATIVE_ID).is(crid)), CreativeEntity.class);
        getMongoTemplate().remove(new Query(Criteria.where(CREATIVE_ID).is(crid)), CreativeBackUpEntity.class);
    }

    @Override
    public void updateLs(Long crid, CreativeDTO dto) {
        Update up = new Update();
        up.set("ls", null);
        up.set("s", dto.getStatus());
        getMongoTemplate().updateFirst(new Query(Criteria.where(CREATIVE_ID).is(crid)), up, CreativeEntity.class);//修改掉本地状态的ls
        getMongoTemplate().remove(new Query(Criteria.where(CREATIVE_ID).is(crid)), CreativeBackUpEntity.class);//删除备份的数据
    }

    @Override
    public void batchDelete(List<String> param) {
        param.forEach(e -> {
            if (e.length() < 24) {
                Update update = new Update();
                update.set("ls", 3);
                getMongoTemplate().updateFirst(new Query(Criteria.where(MongoEntityConstants.CREATIVE_ID).is(Long.valueOf(e))), update, getEntityClass());
            } else {
                getMongoTemplate().remove(new Query(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(e)), getEntityClass());
            }
        });
    }

    @Override
    public void enableOrPauseCreative(List<String> strings, boolean status) {
        if(!strings.isEmpty()){
            strings.forEach(e -> {
                Update update = new Update();
                update.set("p", status);
                update.set("ls", 2);
                if (e.length() < 24) {
                    //查询是否存在备份数据
                    boolean exists = getMongoTemplate().exists(new Query(Criteria.where(CREATIVE_ID).is(Long.parseLong(e))), CreativeBackUpEntity.class);
                    if (exists) {
                        //如果备份表里面存在当前数据则直接修改
                        getMongoTemplate().updateFirst(new Query(Criteria.where(CREATIVE_ID).is(Long.parseLong(e))), update, getEntityClass());
                    } else {
                        //查询创意需要备份的数据
                        CreativeEntity creative = getMongoTemplate().findOne(new Query(Criteria.where(CREATIVE_ID).is(Long.parseLong(e))), getEntityClass());
                        CreativeBackUpEntity creativeBackUpEntity = new CreativeBackUpEntity();
                        BeanUtils.copyProperties(creative, creativeBackUpEntity);
                        //数据进行备份
                        getMongoTemplate().save(creativeBackUpEntity);
                        //修改当前数据
                        getMongoTemplate().updateFirst(new Query(Criteria.where(CREATIVE_ID).is(Long.parseLong(e))), update, getEntityClass());
                    }
                } else {
                    //如果是本地数据直接修改启用状态
                    getMongoTemplate().updateFirst(new Query(Criteria.where(SYSTEM_ID).is(e)), update, getEntityClass());
                }

            });
        }
    }

    @Override
    public CreativeDTO existDTO(Map<String, Object> params) {
        Query q = new Query();
        Criteria c = new Criteria();
        if (params.size() > 0 || params != null) {
            for (Map.Entry<String, Object> cri : params.entrySet()) {
                c.and(cri.getKey()).is(cri.getValue());
            }
            q.addCriteria(c);
        }
        CreativeEntity creativeEntity = getMongoTemplate().findOne(q, getEntityClass());
        if (creativeEntity != null)
            return wrapperObject(creativeEntity);

        return null;
    }

    private Integer getTotalCount(Query q, Class<?> cls) {
        return (int) getMongoTemplate().count(q, cls);
    }


    public CreativeDTO findOne(Long creativeId) {
        CreativeEntity entity = getMongoTemplate().findOne(new Query(Criteria.where(CREATIVE_ID).is(creativeId)), getEntityClass());
        return wrapperObject(entity);
    }

    public List<CreativeDTO> findAll() {
        List<CreativeEntity> entityList = getMongoTemplate().find(Query.query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId())), getEntityClass());
        return wrapperList(entityList);
    }

    @Override
    public List<CreativeDTO> find(Map<String, Object> params, int skip, int limit, String sort, boolean asc) {
        return null;
    }

    public List<CreativeDTO> find(Map<String, Object> params, int skip, int limit) {
        Query query = new Query();
        if (params != null && params.size() > 0) {
            Criteria criteria = Criteria.where(CREATIVE_ID).ne(null);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                criteria.and(entry.getKey()).is(entry.getValue());
            }
            query.addCriteria(criteria);
        }
        query.with(new PageRequest(skip, limit));
        List<CreativeEntity> list = getMongoTemplate().find(query, getEntityClass());
        return wrapperList(list);
    }


    public void insertAll(List<CreativeDTO> creativeDTOList) {
        List<CreativeEntity> creativeEntityList = ObjectUtils.convert(creativeDTOList, getEntityClass());
        getMongoTemplate().insertAll(creativeEntityList);
    }

    @SuppressWarnings("unchecked")
    public void update(CreativeDTO creativeDTO) {
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
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        getMongoTemplate().updateFirst(query, update, getEntityClass());
    }

    public void deleteById(Long creativeId) {
        getMongoTemplate().remove(new Query(Criteria.where(CREATIVE_ID).is(creativeId)), getEntityClass());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<CreativeEntity> getEntityClass() {
        return CreativeEntity.class;
    }

    @Override
    public Class<CreativeDTO> getDTOClass() {
        return CreativeDTO.class;
    }

    private List<CreativeDTO> wrapperList(List<CreativeEntity> list) {
        return ObjectUtils.convert(list, CreativeDTO.class);
    }

    private CreativeDTO wrapperObject(CreativeEntity entity) {
        CreativeDTO dto = ObjectUtils.convert(entity, CreativeDTO.class);
        return dto;
    }

    private Query searchFilterQueryOperate(Query q, SearchFilterParam sp) {
        if (sp != null) {
            if (Objects.equals(sp.getFilterType(), "Creative")) {
                switch (sp.getFilterField()) {
                    case "title":
                        getNormalQuery(q, "t", sp.getSelected(), sp.getFilterValue());
                        break;
                    case "desc1":
                        getNormalQuery(q, sp.getFilterField(), sp.getSelected(), sp.getFilterValue());
                        break;
                    case "desc2":
                        getNormalQuery(q, sp.getFilterField(), sp.getSelected(), sp.getFilterValue());
                        break;
                    case "pcUrl":
                        getNormalQuery(q, "pc", sp.getSelected(), sp.getFilterValue());
                        break;
                    case "pcsUrl":
                        getNormalQuery(q, "pcd", sp.getSelected(), sp.getFilterValue());
                        break;
                    case "mibUrl":
                        getNormalQuery(q, "m", sp.getSelected(), sp.getFilterValue());
                        break;
                    case "mibsUrl":
                        getNormalQuery(q, "md", sp.getSelected(), sp.getFilterValue());
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
                    case "quipment":
                        if (Integer.valueOf(sp.getFilterValue()) != -1) {
                            q.addCriteria(Criteria.where("d").is(Integer.valueOf(sp.getFilterValue())));
                        }
                        break;
                }
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
