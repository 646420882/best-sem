package com.perfect.db.mongodb.impl;

import com.google.common.collect.Lists;
import com.perfect.commons.constants.LogStatusConstant;
import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.core.AppContext;
import com.perfect.dao.keyword.KeywordBackUpDAO;
import com.perfect.dao.keyword.KeywordDAO;
import com.perfect.dao.sys.LogDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.db.mongodb.utils.PageParamUtils;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.backup.KeywordBackUpDTO;
import com.perfect.dto.keyword.KeywordAggsDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.entity.adgroup.AdgroupEntity;
import com.perfect.entity.backup.KeywordBackUpEntity;
import com.perfect.entity.campaign.CampaignEntity;
import com.perfect.entity.keyword.KeywordEntity;
import com.perfect.param.SearchFilterParam;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.paging.PagerInfo;
import com.perfect.utils.paging.PaginationParam;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
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
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by baizz on 2014-07-07.
 * 2014-12-2 refactor
 */
@SuppressWarnings("unchecked")
@Repository("keywordDAO")
public class KeywordDAOImpl extends AbstractUserBaseDAOImpl<KeywordDTO, Long> implements KeywordDAO {

    @Resource
    private LogDAO logDao;

    @Resource
    private KeywordBackUpDAO keywordBackUpDAO;

    @Override
    public String getId() {
        return MongoEntityConstants.KEYWORD_ID;
    }

    @Override
    public Class<KeywordDTO> getDTOClass() {
        return KeywordDTO.class;
    }

    @Override
    public Class<KeywordEntity> getEntityClass() {
        return KeywordEntity.class;
    }

    @Override
    public List<Long> getKeywordIdByAdgroupId(Long adgroupId) {
        Query query = new BasicQuery("{}", "{" + MongoEntityConstants.KEYWORD_ID + " : 1}");
        query.addCriteria(Criteria.where(MongoEntityConstants.ADGROUP_ID).is(adgroupId));
        List<KeywordEntity> list = getMongoTemplate().find(query, getEntityClass());
        List<Long> keywordIds = new ArrayList<>(list.size());
        list.forEach(e -> keywordIds.add(e.getKeywordId()));
        return keywordIds;
    }

    @Override
    public List<KeywordDTO> getKeywordByAdgroupId(Long adgroupId, Map<String, Object> params, int skip, int limit) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new Query();
        Criteria criteria = Criteria.where(MongoEntityConstants.ADGROUP_ID).is(adgroupId);
        if (params != null && params.size() > 0)
            params.forEach((k, v) -> criteria.and(k).is(v));

        query.addCriteria(criteria);
        query.with(new PageRequest(skip, limit, new Sort(Sort.Direction.DESC, "price")));
        List<KeywordEntity> _list = mongoTemplate.find(query, getEntityClass(), MongoEntityConstants.TBL_KEYWORD);
        return ObjectUtils.convert(_list, getDTOClass());
    }


    //根据mongoID查询
    @Override
    public List<KeywordDTO> getKeywordByAdgroupId(String adgroupId, Map<String, Object> params, int skip, int limit) {
        Query query = new Query();
        Criteria criteria = Criteria.where(MongoEntityConstants.OBJ_ADGROUP_ID).is(adgroupId);
        if (params != null && params.size() > 0)
            params.forEach((k, v) -> criteria.and(k).is(v));

        query.addCriteria(criteria);
        query.with(new PageRequest(skip, limit, new Sort(Sort.Direction.DESC, "price")));
        List<KeywordEntity> _list = getMongoTemplate().find(query, getEntityClass(), MongoEntityConstants.TBL_KEYWORD);
        return ObjectUtils.convert(_list, getDTOClass());
    }

    @Override
    public List<KeywordDTO> findByAgroupId(Long oid) {
        List<KeywordEntity> keywordEntityList = getMongoTemplate().find(new Query(Criteria.where(MongoEntityConstants.KEYWORD_ID)), getEntityClass(), MongoEntityConstants.TBL_KEYWORD);
        return ObjectUtils.convert(keywordEntityList, getDTOClass());
    }

    @Override
    public List<KeywordDTO> getKeywordByIds(List<Long> ids) {
        List<KeywordEntity> list = getMongoTemplate().find(new Query(Criteria.where(MongoEntityConstants.KEYWORD_ID).in(ids)), getEntityClass(), MongoEntityConstants.TBL_KEYWORD);
        return ObjectUtils.convert(list, getDTOClass());
    }

    @Override
    public List<KeywordDTO> findByNames(String[] query, boolean fullMatch, PaginationParam param, Map<String, Object> queryParams) {
        Query mongoQuery = new Query();
        Criteria criteria;
        if (fullMatch) {
            criteria = Criteria.where(MongoEntityConstants.NAME).in(query);
        } else {
            String prefix = ".*(";
            String suffix = ").*";
            String reg = "";
            for (String name : query) {
                reg = reg + name + "|";
            }
            reg = reg.substring(0, reg.length() - 1);

            criteria = Criteria.where(MongoEntityConstants.NAME).regex(prefix + reg + suffix);

        }

        if (queryParams != null && !queryParams.isEmpty() && queryParams.size() > 0) {
            for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
                if ("matchType".equals(entry.getKey())) {
                    List<Integer> matchTypes = new ArrayList<>();
                    List<Integer> ptTypes = new ArrayList<>();
                    Arrays.stream(entry.getValue().toString().split(",")).forEach(matchType -> {
                        Integer _matchType = Integer.parseInt(matchType.trim());
                        if (_matchType == 1) {
                            matchTypes.add(1);
//                            criteria.and("mt").is(1);
                        } else if (_matchType == 2) {
                            matchTypes.add(2);
                            ptTypes.add(3);
//                            criteria.and("mt").is(2).and("pt").is(3);
                        } else if (_matchType == 3) {
                            matchTypes.add(2);
                            ptTypes.add(2);
//                            criteria.and("mt").is(2).and("pt").is(2);
                        } else if (_matchType == 4) {
                            matchTypes.add(2);
                            ptTypes.add(1);
//                            criteria.and("mt").is(2).and("pt").is(1);
                        } else if (_matchType == 5) {
                            matchTypes.add(3);
//                            criteria.and("mt").is(3);
                        }
                    });

                    criteria.and("mt").in(matchTypes).and("pt").in(ptTypes);
                }

                if ("adgroupIds".equals(entry.getKey())) {
                    criteria.and(MongoEntityConstants.ADGROUP_ID).in((ArrayList<Long>) entry.getValue());
                }

                if ("status".equals(entry.getKey())) {
                    criteria.and("s").is((Integer) entry.getValue());
                }

                if ("price".equals(entry.getKey())) {
                    BigDecimal[] bigDecimals = (BigDecimal[]) entry.getValue();
                    criteria.and("pr").gte(bigDecimals[0]).lte(bigDecimals[1]);
                }
            }
        }
        mongoQuery.addCriteria(criteria);

        return ObjectUtils.convert(getMongoTemplate().find(PageParamUtils.withParam(param, mongoQuery), getEntityClass()), getDTOClass());
    }

    @Override
    public List<KeywordDTO> findByIds(List<Long> ids, PaginationParam... param) {
        if (param.length > 0)
            return ObjectUtils.convert(getMongoTemplate().find(PageParamUtils.withParam(param[0], Query.query(Criteria.where(MongoEntityConstants.KEYWORD_ID).in(ids))), getEntityClass()), getDTOClass());

        return ObjectUtils.convert(getMongoTemplate().find(Query.query(Criteria.where(MongoEntityConstants.KEYWORD_ID).in(ids)), getEntityClass()), getDTOClass());
    }

    @Override
    public List<KeywordDTO> getKeywordInfo() {
        return ObjectUtils.convert(getMongoTemplate().findAll(getEntityClass(), "keywordInfo"), getDTOClass());
    }

    @Override
    public List<KeywordDTO> findHasLocalStatus() {
        List<KeywordEntity> keywordEntities = getMongoTemplate().find(new Query(Criteria.where("ls").ne(null).and(ACCOUNT_ID).is(AppContext.getAccountId())), getEntityClass());
        return ObjectUtils.convert(keywordEntities, KeywordDTO.class);
    }

    @Override
    public List<KeywordDTO> findHasLocalStatusStr(List<String> strs) {
        List<KeywordEntity> keywordEntities = getMongoTemplate().find(new Query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and(OBJ_ADGROUP_ID).in(strs)), getEntityClass());
        return ObjectUtils.convert(keywordEntities, KeywordDTO.class);
    }

    @Override
    public List<KeywordDTO> findHasLocalStatusLong(List<Long> longs) {
        List<KeywordEntity> keywordEntities = getMongoTemplate().find(new Query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and(ADGROUP_ID).in(longs)), getEntityClass());
        return ObjectUtils.convert(keywordEntities, KeywordDTO.class);
    }

    @Override
    public List<KeywordAggsDTO> findAllKeywordFromBaiduByAccountId(Long baiduAccountId) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where(ACCOUNT_ID).is(baiduAccountId).and("ls").is(null)),
                project("kwid", "name", "agid")
        ).withOptions(new AggregationOptions.Builder().allowDiskUse(true).build());

        AggregationResults<KeywordAggsDTO> aggregationResults = getMongoTemplate().aggregate(aggregation, TBL_KEYWORD, KeywordAggsDTO.class);

        return aggregationResults.getMappedResults();
    }

    @Override
    public List<KeywordDTO> findAllKeywordFromBaiduByAdgroupId(Long baiduAccountId, Long adgroupId) {
        List<KeywordEntity> keywordEntities = getMongoTemplate().find(
                Query.query(Criteria.where(ACCOUNT_ID).is(baiduAccountId).and(ADGROUP_ID).is(adgroupId).and("ls").is(null)), getEntityClass());
        return ObjectUtils.convert(keywordEntities, getDTOClass());
    }

    @Override
    public Long keywordCount(List<Long> adgroupIds) {
        return getMongoTemplate().count(Query.query(
                        Criteria.where(MongoEntityConstants.ACCOUNT_ID).is(AppContext.getAccountId()).and(MongoEntityConstants.ADGROUP_ID).in(adgroupIds)),
                getEntityClass());
    }

    @Override
    public void insertAndQuery(List<KeywordDTO> keywordDTOList) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        keywordDTOList.forEach(e -> {
            Query q = new Query(Criteria.where(getId()).is(e.getKeywordId()));
            if (!mongoTemplate.exists(q, getEntityClass()))
                mongoTemplate.insert(ObjectUtils.convert(e, getEntityClass()));
        });
    }

    @Override
    public KeywordDTO findByName(String name, Long accountId) {
        List<KeywordDTO> list = findByQuery(Query.query(Criteria.where("kw").is(name).and(MongoEntityConstants.ACCOUNT_ID).is(accountId)));
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KeywordDTO> findLocalChangedKeywords(Long baiduAccountId, int type) {
        List<KeywordEntity> keywordEntities = getMongoTemplate().find(new Query(Criteria.where("ls").is(type).and(ACCOUNT_ID).is(baiduAccountId)), getEntityClass());
        return ObjectUtils.convert(keywordEntities, KeywordDTO.class);
    }

    @Override
    public KeywordDTO findOne(Long keywordId) {
        KeywordEntity entity = getMongoTemplate().findOne(Query.query(Criteria.where(getId()).is(keywordId)), getEntityClass(), MongoEntityConstants.TBL_KEYWORD);
        return ObjectUtils.convert(entity, getDTOClass());
    }

    @Override
    public List<KeywordDTO> findAll() {
        return ObjectUtils.convert(getMongoTemplate().find(Query.query(Criteria.where(MongoEntityConstants.ACCOUNT_ID).is(AppContext.getAccountId())), getEntityClass()), getDTOClass());
    }

    @Override
    public List<KeywordDTO> find(Map<String, Object> params, int skip, int limit, String order) {
        Query query = new Query();
        if (params != null && params.size() > 0) {
            Criteria criteria = Criteria.where(getId()).ne(null);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                criteria.and(entry.getKey()).is(entry.getValue());
            }
            query.addCriteria(criteria);
        }
        query.with(new PageRequest(skip, limit, new Sort(Sort.Direction.DESC, order)));
        return ObjectUtils.convert(getMongoTemplate().find(query, getEntityClass(), MongoEntityConstants.TBL_KEYWORD), getDTOClass());
    }


    //xj
    public List<KeywordDTO> findByQuery(Query query) {
        return ObjectUtils.convert(getMongoTemplate().find(query, getEntityClass()), getDTOClass());
    }

    @Override
    public List<KeywordDTO> findByAdgroupId(Long adgroupId, PaginationParam param, Map<String, Object> queryParams) {
        Query query = new Query();
        Criteria criteria = Criteria.where(MongoEntityConstants.ADGROUP_ID).is(adgroupId);
        if (queryParams != null && !queryParams.isEmpty()) {
            queryParams.entrySet().stream().filter(entry -> "status".equals(entry.getKey())).forEach(entry -> criteria.and("s").is(entry.getValue()));
        }

        if (param == null) {
            query.addCriteria(criteria);
            return ObjectUtils.convert(getMongoTemplate().find(query, getEntityClass()), getDTOClass());
        } else {
            query.addCriteria(criteria);
            return ObjectUtils.convert(getMongoTemplate().find(PageParamUtils.withParam(param, query), getEntityClass()), getDTOClass());
        }
    }

    /**
     * 根据mongoID查询
     *
     * @param adgroupId
     * @param param
     * @return
     */
    @Override
    public List<KeywordDTO> findByAdgroupId(String adgroupId, PaginationParam param) {
        return ObjectUtils.convert(getMongoTemplate().find(PageParamUtils.withParam(param, Query.query(Criteria.where(MongoEntityConstants.OBJ_ADGROUP_ID).is(adgroupId))), getEntityClass()), getDTOClass());
    }

    @Override
    public List<KeywordDTO> findByAdgroupIds(List<Long> adgroupIds, PaginationParam param, Map<String, Object> queryParams) {
        Query query = new Query();
        Criteria criteria = Criteria.where(MongoEntityConstants.ADGROUP_ID).in(adgroupIds);
        if (queryParams != null && !queryParams.isEmpty())
            queryParams.entrySet().stream().filter(entry -> "status".equals(entry.getKey())).forEach(entry -> criteria.and("s").is(entry.getValue()));

        if (param != null) {
            query.addCriteria(criteria);
            return ObjectUtils.convert(getMongoTemplate().find(PageParamUtils.withParam(param, query), getEntityClass()), getDTOClass());
        } else {
            query.addCriteria(criteria);
            return ObjectUtils.convert(getMongoTemplate().find(query, getEntityClass()), getDTOClass());
        }
    }

    @Override
    public KeywordDTO findByObjectId(String oid) {
        KeywordEntity entity = getMongoTemplate().findOne(Query.query(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(oid)), getEntityClass());
        return ObjectUtils.convert(entity, getDTOClass());
    }

    @Override
    public KeywordDTO findByLongId(Long oid) {
        KeywordEntity entity = getMongoTemplate().findOne(Query.query(Criteria.where(MongoEntityConstants.KEYWORD_ID).is(oid)), getEntityClass());
        return ObjectUtils.convert(entity, getDTOClass());
    }

    @Override
    public PagerInfo findByPageInfoForAcctounId(int pageSize, int pageNo, SearchFilterParam sp) {
        Query query = new Query();
        query.addCriteria(Criteria.where(MongoEntityConstants.ACCOUNT_ID).is(AppContext.getAccountId()));
        int totalCount = getListTotalCount(query);
        PagerInfo p = new PagerInfo(pageNo, pageSize, totalCount);
        query.skip(p.getFirstStation());
        query.limit(p.getPageSize());
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        searchFilterQueryOperate(query, sp);
        List<KeywordEntity> list = getMongoTemplate().find(query, getEntityClass());
        List<KeywordDTO> dtos = ObjectUtils.convert(list, KeywordDTO.class);
        p.setList(dtos);
        return p;
    }

    private int getListTotalCount(Query query) {
        return (int) getMongoTemplate().count(query, MongoEntityConstants.TBL_KEYWORD);
    }

    @Override
    public PagerInfo findByPageInfoForLongId(Long aid, int pageSize, int pageNo, SearchFilterParam sp) {
        Query query = new Query();
        query.addCriteria(Criteria.where(MongoEntityConstants.ACCOUNT_ID).is(AppContext.getAccountId()));
        query.addCriteria(Criteria.where(MongoEntityConstants.ADGROUP_ID).is(aid));
        int totalCount = getListTotalCount(query);
        PagerInfo p = new PagerInfo(pageNo, pageSize, totalCount);
        query.skip(p.getFirstStation());
        query.limit(p.getPageSize());
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        searchFilterQueryOperate(query, sp);
        List<KeywordEntity> list = getMongoTemplate().find(query, getEntityClass());
        List<KeywordDTO> dtos = ObjectUtils.convert(list, KeywordDTO.class);
        p.setList(dtos);
        return p;
    }

    @Override
    public PagerInfo findByPageInfoForStringId(String aid, int pageSize, int pageNo, SearchFilterParam sp) {
        Query query = new Query();
        query.addCriteria(Criteria.where(MongoEntityConstants.ACCOUNT_ID).is(AppContext.getAccountId()));
        query.addCriteria(Criteria.where(MongoEntityConstants.OBJ_ADGROUP_ID).is(aid));
        int totalCount = getListTotalCount(query);
        PagerInfo p = new PagerInfo(pageNo, pageSize, totalCount);
        query.skip(p.getFirstStation());
        query.limit(p.getPageSize());
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        searchFilterQueryOperate(query, sp);
        List<KeywordEntity> list = getMongoTemplate().find(query, getEntityClass());
        List<KeywordDTO> dtos = ObjectUtils.convert(list, getDTOClass());
        p.setList(dtos);
        return p;
    }

    @Override
    public PagerInfo findByPageInfoForLongIds(List<Long> adis, int pageSize, int pageNo, SearchFilterParam sp) {
        Query query = new Query();
        query.addCriteria(Criteria.where(MongoEntityConstants.ACCOUNT_ID).is(AppContext.getAccountId()));
        query.addCriteria(Criteria.where(MongoEntityConstants.ADGROUP_ID).in(adis));
        int totalCount = getListTotalCount(query);
        PagerInfo p = new PagerInfo(pageNo, pageSize, totalCount);
        query.skip(p.getFirstStation());
        query.limit(p.getPageSize());
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        searchFilterQueryOperate(query, sp);
        List<KeywordEntity> list = getMongoTemplate().find(query, getEntityClass());
        List<KeywordDTO> dtos = ObjectUtils.convert(list, getDTOClass());
        p.setList(dtos);
        return p;
    }

    @Override
    public PagerInfo findByPageInfoForStringIds(List<String> aids, int pageSize, int pageNo, SearchFilterParam sp) {
        Query query = new Query();
        query.addCriteria(Criteria.where(MongoEntityConstants.ACCOUNT_ID).is(AppContext.getAccountId()));
        query.addCriteria(Criteria.where(MongoEntityConstants.OBJ_ADGROUP_ID).in(aids));
        int totalCount = getListTotalCount(query);
        PagerInfo p = new PagerInfo(pageNo, pageSize, totalCount);
        query.skip(p.getFirstStation());
        query.limit(p.getPageSize());
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        searchFilterQueryOperate(query, sp);
        List<KeywordEntity> list = getMongoTemplate().find(query, getEntityClass());
        List<KeywordDTO> dtos = ObjectUtils.convert(list, getDTOClass());
        p.setList(dtos);
        return p;
    }

    @Override
    public void updateAdgroupIdByOid(String id, Long adgroupId) {
        getMongoTemplate().updateMulti(
                Query.query(Criteria.where(MongoEntityConstants.OBJ_ADGROUP_ID).is(id)),
                Update.update(MongoEntityConstants.ADGROUP_ID, adgroupId).set(MongoEntityConstants.OBJ_ADGROUP_ID, null),
                getEntityClass());
    }

    public void insert(KeywordDTO keywordDTO) {
        KeywordEntity keywordEntity = ObjectUtils.convert(keywordDTO, getEntityClass());
        getMongoTemplate().insert(keywordEntity, MongoEntityConstants.TBL_KEYWORD);
    }

    @Override
    public Iterable<KeywordDTO> save(Iterable<KeywordDTO> keywordDTOs) {
        List<KeywordDTO> dtoList = Lists.newArrayList(keywordDTOs);
        List<KeywordEntity> entityList = ObjectUtils.convert(dtoList, getEntityClass());
        entityList.stream().forEach(s -> {
            if (!getMongoTemplate().exists(new Query(Criteria.where(NAME).is(s.getKeyword())), getEntityClass())) {
                getMongoTemplate().insert(s);
            }
        });
        dtoList = ObjectUtils.convert(entityList, getDTOClass());
        return dtoList;
    }

//    public void update(KeywordDTO keywordDTO) {
//        Long id = keywordDTO.getKeywordId();
//        Query query = new Query();
//        query.addCriteria(Criteria.where(getId()).is(id));
//        Update update = new Update();
//        try {
//            Class _class = keywordDTO.getClass();
//            Field[] fields = _class.getDeclaredFields();
//            for (Field field : fields) {
//                String fieldName = field.getName();
//                if ("keywordId".equals(fieldName))
//                    continue;
//                StringBuilder fieldGetterName = new StringBuilder("get");
//                fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
//                Method method = _class.getDeclaredMethod(fieldGetterName.toString());
//                if (method == null)
//                    continue;
//
//                Object after = method.invoke(keywordDTO);
//                if (after != null) {
//                    update.set(field.getName(), after);
//                    Object before = method.invoke(findOne(id));
//                    break;
//                }
//            }
//        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        getMongoTemplate().updateFirst(query, update, getEntityClass(), MongoEntityConstants.TBL_KEYWORD);
//    }

//    public void update(List<KeywordDTO> keywordDTOList) {
//        for (KeywordDTO dto : keywordDTOList)
//            update(dto);
//    }

    //xj
    public void update(KeywordDTO keywordDTO, KeywordBackUpDTO keywordBackUpDTO) {
        Long id = keywordDTO.getKeywordId();
        Query query = new Query();
        query.addCriteria(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(keywordDTO.getId()));
        Update update = new Update();
        try {
            Class _class = keywordDTO.getClass();
            Field[] fields = _class.getDeclaredFields();//get object's fields by reflect
            for (Field field : fields) {
                String fieldName = field.getName();
                if (MongoEntityConstants.SYSTEM_ID.equals(fieldName))
                    continue;
                StringBuilder fieldGetterName = new StringBuilder("get");
                fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
                Method method = _class.getDeclaredMethod(fieldGetterName.toString());
                if (method == null)
                    continue;

                Object after = method.invoke(keywordDTO);
                if (after != null) {
                    update.set(field.getName(), after);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        getMongoTemplate().updateFirst(query, update, getEntityClass(), MongoEntityConstants.TBL_KEYWORD);
        KeywordBackUpDTO _keywordBackUpDTO = keywordBackUpDAO.findByObjectId(keywordDTO.getId());
        if (_keywordBackUpDTO == null && keywordDTO.getLocalStatus() == 2) {
            KeywordBackUpEntity backUpEntity = ObjectUtils.convert(keywordBackUpDTO, KeywordBackUpEntity.class);
            getMongoTemplate().insert(backUpEntity);
        }
        logDao.insertLog(id, LogStatusConstant.ENTITY_KEYWORD, LogStatusConstant.OPT_UPDATE);
    }

    @Override
    public void update(KeywordDTO keywordDTO, KeywordDTO keywordBackUpDTO) {
        Long id = keywordDTO.getKeywordId();
        Query query = new Query();
        query.addCriteria(Criteria.where(KEYWORD_ID).is(id));
        Update update = new Update();
        try {
            Class _class = keywordDTO.getClass();
            Field[] fields = _class.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                if ("creativeId".equals(fieldName))
                    continue;
                StringBuilder fieldGetterName = new StringBuilder("get");
                fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
                Method method = _class.getDeclaredMethod(fieldGetterName.toString());
                Object after = method.invoke(keywordDTO);
                if (after != null) {
                    update.set(field.getName(), after);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        getMongoTemplate().updateFirst(query, update, getEntityClass());
        KeywordBackUpDTO keywordBackUpDTOFind = keywordBackUpDAO.findByObjectId(keywordDTO.getId());
        if (keywordBackUpDTOFind == null) {
            KeywordBackUpEntity backUpEntity = new KeywordBackUpEntity();
            BeanUtils.copyProperties(keywordBackUpDTO, backUpEntity);
            getMongoTemplate().insert(backUpEntity);
        }
    }

    /**
     * 还原功能的软删除
     *
     * @param id
     */
    public void updateLocalstatu(long id) {
        getMongoTemplate().updateFirst(
                new Query(Criteria.where(MongoEntityConstants.KEYWORD_ID).is(id)),
                Update.update("ls", ""),
                MongoEntityConstants.TBL_KEYWORD);
    }


    /**
     * xj
     * 根据推广单元的mongodb ID 删除该单元下的所有与关键词
     *
     * @param agids
     */
    public void deleteByObjectAdgroupIds(List<String> agids) {
        getMongoTemplate().remove(new Query(Criteria.where(MongoEntityConstants.OBJ_ADGROUP_ID).in(agids)), MongoEntityConstants.TBL_KEYWORD);
    }


    /**
     * xj
     * 根据推广单元Long id 软删除该单元下的所有关键词(实则是修改localStaut 为 3);
     *
     * @param longSet
     */
    public void softDeleteByLongAdgroupIds(List<Long> longSet) {
        getMongoTemplate().updateMulti(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).in(longSet)), Update.update("ls", 3), MongoEntityConstants.TBL_KEYWORD);
    }


    /**
     * 根据关键词的多个mongdb id得到关键词
     */
    public List<KeywordDTO> findByObjectIds(List<String> strIds) {
        return ObjectUtils.convert(
                getMongoTemplate().find(
                        new Query(Criteria.where(MongoEntityConstants.SYSTEM_ID).in(strIds)),
                        getEntityClass(),
                        MongoEntityConstants.TBL_KEYWORD),
                getDTOClass());
    }


    /**
     * 根据传过来的关键词的long id 查询
     *
     * @param ids
     * @return
     */
    public List<KeywordDTO> findKeywordByIds(List<Long> ids) {
        return ObjectUtils.convert(getMongoTemplate().find(new Query(Criteria.where(MongoEntityConstants.KEYWORD_ID).in(ids)), getEntityClass(), MongoEntityConstants.TBL_KEYWORD), getDTOClass());
    }

    @Override
    public void insertAll(List<KeywordDTO> dtos) {
        getMongoTemplate().insertAll(ObjectUtils.convert(dtos, getEntityClass()));
    }

    @Override
    public List<KeywordDTO> findByParams(Map<String, Object> params) {
        Query query = new Query();
        if (params != null && !params.isEmpty()) {
            Criteria c = new Criteria();
            for (Map.Entry<String, Object> map : params.entrySet()) {
                c.and(map.getKey()).is(map.getValue());
            }
            query.addCriteria(c);
        }
        List<KeywordEntity> list = getMongoTemplate().find(query, getEntityClass());
        return ObjectUtils.convert(list, getDTOClass());
    }

    @Override
    public KeywordDTO findByParamsObject(Map<String, Object> mapParams) {
        Query q = new Query();
        Criteria c = new Criteria();
        if (mapParams != null && mapParams.size() > 0) {
            for (Map.Entry<String, Object> entry : mapParams.entrySet()) {
                c.and(entry.getKey()).is(entry.getValue());
            }
        }
        q.addCriteria(c);
        KeywordEntity entity = getMongoTemplate().findOne(q, getEntityClass());
        KeywordDTO dto = ObjectUtils.convert(entity, KeywordDTO.class);
        return dto;
    }

    @Override
    public void updateByObjId(KeywordDTO dto) {
        Update update = new Update();
        update.set("name", dto.getKeyword());
        update.set("pr", dto.getPrice());
        update.set("pc", dto.getPcDestinationUrl());
        update.set("mt", dto.getMatchType());
        update.set("p", dto.getPause());
        update.set("mobile", dto.getMobileDestinationUrl());
        getMongoTemplate().updateFirst(new Query(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(dto.getId())), update, getEntityClass());
    }

    @Override
    public void update(String oid, KeywordDTO dto) {
        Update update = new Update();
        update.set("ls", null);
        update.set(KEYWORD_ID, dto.getKeywordId());
        update.set("s", dto.getStatus());
        getMongoTemplate().updateFirst(new Query(Criteria.where(SYSTEM_ID).is(oid)), update, getEntityClass());
    }

    @Override
    public void updateLs(KeywordDTO dto) {
        Update up = new Update();
        up.set("ls", null);
        up.set("s", dto.getStatus());
        getMongoTemplate().updateFirst(new Query(Criteria.where(KEYWORD_ID).is(dto.getKeywordId())), up, getEntityClass());//执行修改ls状态后，还需要将备份的keywor对应的删掉
        getMongoTemplate().remove(new Query(Criteria.where(KEYWORD_ID).is(dto.getKeywordId())), KeywordBackUpEntity.class);//删除备份的keyword
    }

    @Override
    public Map<String, Map<String, List<String>>> getNoKeywords(Long aid) {
        Map<String, Map<String, List<String>>> map = new HashMap<>();
        Query query = new BasicQuery("{}", "{neg: 1,exneg:1," + CAMPAIGN_ID + ":1}");
        query.addCriteria(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and(ADGROUP_ID).is(aid));
        AdgroupEntity adgroupEntity = getMongoTemplate().findOne(query, AdgroupEntity.class);
        Map<String, List<String>> adnokeyword = new HashMap<>();
        adnokeyword.put("neg", adgroupEntity.getNegativeWords());
        adnokeyword.put("exneg", adgroupEntity.getExactNegativeWords());
        map.put("ad", adnokeyword);
        Query campQ = new BasicQuery("{}", "{neg:1,exneg:1}");
        campQ.addCriteria(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and(CAMPAIGN_ID).is(adgroupEntity.getCampaignId()));
        CampaignEntity campaignEntity = getMongoTemplate().findOne(campQ, CampaignEntity.class);
        Map<String, List<String>> canoKeyowrd = new HashMap<>();
        canoKeyowrd.put("neg", campaignEntity.getNegativeWords());
        canoKeyowrd.put("exneg", campaignEntity.getExactNegativeWords());
        map.put("ca", canoKeyowrd);
        return map;
    }

    @Override
    public Map<String, Map<String, List<String>>> getNoKeywords(String aid) {
        Map<String, Map<String, List<String>>> map = new HashMap<>();
        Query queryNoObj = new BasicQuery("{}", "{neg: 1,exneg:1," + SYSTEM_ID + ":1," + CAMPAIGN_ID + ":1}");
        queryNoObj.addCriteria(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and(SYSTEM_ID).is(aid));
        AdgroupEntity adgroupEntity = getMongoTemplate().findOne(queryNoObj, AdgroupEntity.class);
        Map<String, List<String>> adnokeyword = new HashMap<>();
        adnokeyword.put("neg", adgroupEntity.getNegativeWords());
        adnokeyword.put("exneg", adgroupEntity.getExactNegativeWords());
        map.put("ad", adnokeyword);
        Query campQ = new BasicQuery("{}", "{neg:1,exneg:1}");
        campQ.addCriteria(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and(SYSTEM_ID).is(adgroupEntity.getCampaignObjId()));
        CampaignEntity campaignEntity = getMongoTemplate().findOne(campQ, CampaignEntity.class);
        Map<String, List<String>> canoKeyowrd = new HashMap<>();
        if (campaignEntity != null) {
            canoKeyowrd.put("neg", campaignEntity.getNegativeWords());
            canoKeyowrd.put("exneg", campaignEntity.getExactNegativeWords());
            map.put("ca", canoKeyowrd);
        } else {
            Query campNoObj = new BasicQuery("{}", "{neg:1,exneg:1}");
            campNoObj.addCriteria(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and(CAMPAIGN_ID).is(adgroupEntity.getCampaignId()));
            campaignEntity = getMongoTemplate().findOne(campNoObj, CampaignEntity.class);
            canoKeyowrd.put("neg", campaignEntity.getNegativeWords());
            canoKeyowrd.put("exneg", campaignEntity.getExactNegativeWords());
            map.put("ca", canoKeyowrd);
        }
        return map;
    }

    @Override
    public List<KeywordDTO> findKeywordByAdgroupIdsStr(List<String> adgroupIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where(MongoEntityConstants.ACCOUNT_ID).is(AppContext.getAccountId()));
        query.addCriteria(Criteria.where(MongoEntityConstants.OBJ_ADGROUP_ID).in(adgroupIds));
        List<KeywordEntity> list = getMongoTemplate().find(query, getEntityClass());
        List<KeywordDTO> dtos = ObjectUtils.convert(list, getDTOClass());
        return dtos;
    }

    @Override
    public List<KeywordDTO> findKeywordByAdgroupIdsLong(List<Long> adgroupIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where(MongoEntityConstants.ACCOUNT_ID).is(AppContext.getAccountId()));
        query.addCriteria(Criteria.where(MongoEntityConstants.ADGROUP_ID).in(adgroupIds));
        List<KeywordEntity> list = getMongoTemplate().find(query, getEntityClass());
        List<KeywordDTO> dtos = ObjectUtils.convert(list, getDTOClass());
        return dtos;
    }

    @Override
    public void batchDelete(List<String> strings) {
        strings.forEach(e -> {
            if (e.length() < 24) {
                Update update = new Update();
                update.set("ls", 3);
                getMongoTemplate().updateFirst(new Query(Criteria.where(MongoEntityConstants.KEYWORD_ID).is(Long.valueOf(e))), update, getEntityClass());
            } else {
                getMongoTemplate().remove(new Query(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(e)), getEntityClass());
            }
        });
    }

    /**
     * 根据mongodbID修改
     *
     * @param keywordDTO
     */
    public void updateByMongoId(KeywordDTO keywordDTO) {
        String id = keywordDTO.getId();
        Query query = new Query();
        query.addCriteria(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(id));
        Update update = new Update();

        try {
            Class _class = keywordDTO.getClass();
            Field[] fields = _class.getDeclaredFields();//get object's fields by reflect
            for (Field field : fields) {
                String fieldName = field.getName();
                if ("id".equals(fieldName))
                    continue;
                StringBuilder fieldGetterName = new StringBuilder("get");
                fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
                Method method = _class.getDeclaredMethod(fieldGetterName.toString());
                if (method == null)
                    continue;

                Object after = method.invoke(keywordDTO);
                if (after != null) {
                    update.set(field.getName(), after);
                    logDao.insertLog(id, LogStatusConstant.ENTITY_KEYWORD);
                    break;
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        getMongoTemplate().updateFirst(query, update, getEntityClass(), MongoEntityConstants.TBL_KEYWORD);
    }


    public void updateMulti(String fieldName, String seedWord, Object value) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        Class _class = getEntityClass();
        Query query = new Query();
        query.addCriteria(Criteria.where(MongoEntityConstants.TBL_KEYWORD).
                regex(Pattern.compile("^.*?" + seedWord + ".*$", Pattern.CASE_INSENSITIVE)));
        List<KeywordEntity> keywordEntities = mongoTemplate.find(query, _class, MongoEntityConstants.TBL_KEYWORD);
        Update update = new Update();
        update.set(fieldName, value);
        mongoTemplate.updateMulti(query, update, MongoEntityConstants.TBL_KEYWORD);
        try {
            for (KeywordEntity entity : keywordEntities) {
                StringBuilder fieldGetterName = new StringBuilder("get");
                fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
                Method method = _class.getDeclaredMethod(fieldGetterName.toString());
                Object before = method.invoke(entity);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateMultiKeyword(Long[] ids, BigDecimal price, String pcUrl) {
        //do nothing
    }

    @Override
    public AdgroupDTO findByKeywordId(Long keywordId) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        KeywordEntity keywordEntity = mongoTemplate.findOne(Query.query(Criteria.where(MongoEntityConstants.KEYWORD_ID).is(keywordId)), getEntityClass());
        Long adgroupId = keywordEntity.getAdgroupId();
        AdgroupEntity entity = mongoTemplate.findOne(Query.query(Criteria.where(MongoEntityConstants.ADGROUP_ID).is(adgroupId)), AdgroupEntity.class, MongoEntityConstants.TBL_ADGROUP);
        return ObjectUtils.convert(entity, AdgroupDTO.class);
    }

    public void deleteById(Long id) {
        getMongoTemplate().remove(new Query(Criteria.where(MongoEntityConstants.KEYWORD_ID).is(id)), getEntityClass(), MongoEntityConstants.TBL_KEYWORD);
    }

    /**
     * 根据mongoId硬删除
     *
     * @param id
     */
    public void deleteById(String id) {
        getMongoTemplate().remove(new Query(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(id)), getEntityClass(), MongoEntityConstants.TBL_KEYWORD);
        logDao.insertLog(id, LogStatusConstant.ENTITY_KEYWORD);
    }

    /**
     * 根据Long类型id软删除
     *
     * @param id
     */
    public void softDelete(Long id) {
        getMongoTemplate().updateFirst(new Query(Criteria.where(MongoEntityConstants.KEYWORD_ID).is(id)), Update.update("ls", 3), MongoEntityConstants.TBL_KEYWORD);
    }


    @Override
    public int deleteByIds(List<Long> ids) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        for (Long id : ids) {
            mongoTemplate.remove(new Query(Criteria.where(MongoEntityConstants.KEYWORD_ID).is(id)), KeywordEntity.class);
            mongoTemplate.remove(new Query(Criteria.where(MongoEntityConstants.KEYWORD_ID).is(id)), KeywordBackUpEntity.class);
        }
        return 0;
    }

    @Override
    public List<KeywordDTO> find(Map<String, Object> params, int skip, int limit) {
        return null;
    }

    @Override
    public boolean delete(KeywordDTO keywordDTO) {
        deleteById(keywordDTO.getKeywordId());
        return false;
    }

    @Override
    public boolean deleteAll() {
        getMongoTemplate().dropCollection(MongoEntityConstants.TBL_KEYWORD);
        return true;
    }

    protected void remove(Query query) {
        getMongoTemplate().remove(query, getEntityClass(), MongoEntityConstants.TBL_KEYWORD);
    }

    private Query searchFilterQueryOperate(Query q, SearchFilterParam sp) {

        if (sp != null) {
            if (Objects.equals(sp.getFilterType(), "Keyword")) {

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
                        q.addCriteria(Criteria.where("pr").gt(starPrice).lt(endPrice));
                        break;
                    case "matchType":
                        if (sp.getFilterValue().contains(",")) {
                            List<Integer> matchType = new ArrayList<>();
                            List<Integer> phraseType = new ArrayList<>();
                            String[] ids = sp.getFilterValue().split(",");
                            for (int i = 0; i < ids.length; i++) {
                                if (ids[i].length() == 2) {
                                    phraseType.add(Integer.valueOf(ids[i].substring(0, 1)));
                                } else {
                                    matchType.add(Integer.valueOf(ids[i]));
                                }
                            }
                            if (matchType.size() > 0) {
                                q.addCriteria(Criteria.where("mt").in(matchType));
                            }
                            if (phraseType.size() > 0)
                                q.addCriteria(Criteria.where("pt").in(phraseType).and("mt").is(2));

                        } else {
                            if (sp.getFilterValue().length() == 1) {
                                q.addCriteria(Criteria.where("mt").is(Integer.valueOf(sp.getFilterValue())));
                            } else {
                                q.addCriteria(Criteria.where("pt").is(Integer.valueOf(sp.getFilterValue().substring(0, 1))).and("mt").is(2));
                            }
                        }
                        break;
                    case "pcUrl":
                        getNormalQuery(q, "pc", sp.getSelected(), sp.getFilterValue());
                        break;
                    case "mibUrl":
                        getNormalQuery(q, "mobile", sp.getSelected(), sp.getFilterValue());
                        break;
                }
                return q;
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
