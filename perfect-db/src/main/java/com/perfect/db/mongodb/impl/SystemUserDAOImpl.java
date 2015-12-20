package com.perfect.db.mongodb.impl;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.gridfs.GridFSDBFile;
import com.perfect.commons.constants.SystemNameConstant;
import com.perfect.core.AppContext;
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dto.sys.*;
import com.perfect.entity.adgroup.AdgroupEntity;
import com.perfect.entity.campaign.CampaignEntity;
import com.perfect.entity.creative.CreativeEntity;
import com.perfect.entity.keyword.KeywordEntity;
import com.perfect.entity.sys.*;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.SystemUserUtils;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by vbzer_000 on 2014-6-19.
 * 2014-12-2 refactor
 */
@Repository("systemUserDAO")
public class SystemUserDAOImpl extends AbstractSysBaseDAOImpl<SystemUserDTO, String> implements SystemUserDAO {

    @Resource
    private GridFsTemplate gridFsTemplate;

    @Override
    @SuppressWarnings("unchecked")
    public Class<SystemUserEntity> getEntityClass() {
        return SystemUserEntity.class;
    }

    @Override
    public Class<SystemUserDTO> getDTOClass() {
        return SystemUserDTO.class;
    }

    @Override
    public void addBaiduAccount(List<ModuleAccountInfoDTO> list, String currSystemUserName) {
        List<ModuleAccountInfoDTO> _list = AppContext.getModuleAccounts();
        if (_list == null)
            _list = new ArrayList<>();

        _list.addAll(list);

//        getSysMongoTemplate().updateFirst(Query.query(Criteria.where("userName").is(currSystemUserName).and("modules.moduleId").is(AppContext.getModuleName())),
//                Update.update("modules.accounts", _list), "sys_user");
        getSysMongoTemplate().updateFirst(Query.query(Criteria.where("userName").is(currSystemUserName).and("modules.moduleId").is(AppContext.getModuleId())),
                Update.update("modules.accounts", _list), "sys_user");
    }

    @Override
    public SystemUserDTO findByAid(long aid) {
        Query query = Query.query(Criteria.where("modules.accounts.bid").is(aid));
        SystemUserEntity entity = getSysMongoTemplate().findOne(query, getEntityClass());

        return fromEntity(entity);
    }

    @Override
    public Iterable<SystemUserDTO> getAllValidUser() {
        List<SystemUserEntity> entityList = getSysMongoTemplate().find(
                Query.query(Criteria.where("access").is(2).and("state").is(1).and("acstate").is(1)),
                getEntityClass());
        List<SystemUserDTO> dtoList = new ArrayList<>(entityList.size());
        entityList.stream().forEach(e -> dtoList.add(fromEntity(e)));
        return dtoList;
    }

    @Override
    public void insertAccountInfo(String userName, ModuleAccountInfoDTO baiduAccountInfoDTO) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();

        // 获取moduleId
        SystemModuleDTO systemModuleDTO = findSystemModuleByModuleName(userName, SystemNameConstant.SOUKE_SYSTEM_NAME);
        if (Objects.isNull(systemModuleDTO))
            return;
        String moduleId = systemModuleDTO.getId();

        SystemUserDTO systemUserDTO = findByUserName(userName);

        boolean isEmpty;
        try {
            isEmpty = systemUserDTO.getSystemUserModules()
                    .stream()
                    .filter(o -> Objects.equals(SystemNameConstant.SOUKE_SYSTEM_NAME, o.getModuleName()))
                    .findFirst()
                    .get()
                    .getAccounts()
                    .isEmpty();
        } catch (NullPointerException e) {
            isEmpty = true;
        }

        if (isEmpty) {
            baiduAccountInfoDTO.setDfault(true);
        }

        ModuleAccountInfoEntity moduleAccountInfoEntity = ObjectUtils.convert(baiduAccountInfoDTO, ModuleAccountInfoEntity.class);
        Update update = new Update();
        update.addToSet("modules.accounts", moduleAccountInfoEntity);
        mongoTemplate.upsert(Query.query(Criteria.where("userName").is(userName).and("modules.moduleId").is(moduleId)), update, getEntityClass());
    }

    @Override
    public int removeAccountInfo(List<ModuleAccountInfoDTO> baiduAccountInfoDTOs, String account) {
        List<ModuleAccountInfoEntity> baiduAccountInfoEntities = ObjectUtils.convert(baiduAccountInfoDTOs, ModuleAccountInfoEntity.class);

        WriteResult writeResult = getSysMongoTemplate().updateFirst(Query.query(Criteria.where("userName").is(account)),
                Update.update("$.modules.accounts", baiduAccountInfoEntities), getEntityClass());
        return writeResult.getN();
    }

    @Override
    public void clearAccountData(Long accountId) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        if (mongoTemplate.collectionExists(CampaignEntity.class))
            mongoTemplate.remove(Query.query(Criteria.where(ACCOUNT_ID).is(accountId)), CampaignEntity.class);

        if (mongoTemplate.collectionExists(AdgroupEntity.class))
            mongoTemplate.remove(Query.query(Criteria.where(ACCOUNT_ID).is(accountId)), AdgroupEntity.class);

        if (mongoTemplate.collectionExists(KeywordEntity.class))
            mongoTemplate.remove(Query.query(Criteria.where(ACCOUNT_ID).is(accountId)), KeywordEntity.class);

        if (mongoTemplate.collectionExists(CreativeEntity.class))
            mongoTemplate.remove(Query.query(Criteria.where(ACCOUNT_ID).is(accountId)), CreativeEntity.class);
    }

    @Override
    public void clearCampaignData(Long accountId, List<Long> campaignIds) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        Query query = new Query(Criteria.where(ACCOUNT_ID).is(accountId).and(CAMPAIGN_ID).in(campaignIds));
        if (mongoTemplate.collectionExists(CampaignEntity.class))
            mongoTemplate.remove(query, TBL_CAMPAIGN);
    }

    @Override
    public void clearAdgroupData(Long accountId, List<Long> adgroupIds) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        Query query = new Query(Criteria.where(ACCOUNT_ID).is(accountId).and(ADGROUP_ID).in(adgroupIds));
        if (mongoTemplate.collectionExists(AdgroupEntity.class))
            mongoTemplate.remove(query, TBL_ADGROUP);
    }

    @Override
    public void clearKeywordData(Long accountId, List<Long> keywordIds) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        Query query = new Query(Criteria.where(ACCOUNT_ID).is(accountId).and(KEYWORD_ID).in(keywordIds));
        if (mongoTemplate.collectionExists(KeywordEntity.class))
            mongoTemplate.remove(query, TBL_KEYWORD);
    }

    @Override
    public void clearCreativeData(Long accountId, List<Long> creativeIds) {
        MongoTemplate mongoTemplate = getMongoTemplate();
        Query query = new Query(Criteria.where(ACCOUNT_ID).is(accountId).and(CREATIVE_ID).in(creativeIds));
        if (mongoTemplate.collectionExists(CreativeEntity.class))
            mongoTemplate.remove(query, TBL_CREATIVE);
    }

    @Override
    public List<Long> getLocalAdgroupIds(Long accountId, List<Long> campaignIds) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where(ACCOUNT_ID).is(accountId).and(CAMPAIGN_ID).in(campaignIds)),
                project(ADGROUP_ID).andExclude(SYSTEM_ID)
        );
        AggregationResults<AdgroupEntity> results = getMongoTemplate().aggregate(aggregation, TBL_ADGROUP, AdgroupEntity.class);
        List<Long> ids = new ArrayList<>();
        results.getMappedResults().parallelStream().forEach(e -> ids.add(e.getAdgroupId()));
        return ids;
    }

    @Override
    public List<Long> getLocalKeywordIds(Long accountId, List<Long> adgroupIds) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where(ACCOUNT_ID).is(accountId).and(ADGROUP_ID).in(adgroupIds)),
                project(KEYWORD_ID).andExclude(SYSTEM_ID)
        );
        AggregationResults<KeywordEntity> results = getMongoTemplate().aggregate(aggregation, TBL_KEYWORD, KeywordEntity.class);
        List<Long> ids = new ArrayList<>();
        results.getMappedResults().parallelStream().forEach(e -> ids.add(e.getKeywordId()));
        return ids;
    }

    @Override
    public List<Long> getLocalCreativeIds(Long accountId, List<Long> adgroupIds) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where(ACCOUNT_ID).is(accountId).and(ADGROUP_ID).in(adgroupIds)),
                project(CREATIVE_ID).andExclude(SYSTEM_ID)
        );
        AggregationResults<CreativeEntity> results = getMongoTemplate().aggregate(aggregation, TBL_CREATIVE, CreativeEntity.class);
        List<Long> ids = new ArrayList<>();
        results.getMappedResults().parallelStream().forEach(e -> ids.add(e.getCreativeId()));
        return ids;
    }

    @Override
    public List<SystemUserDTO> findAll(int skip, int limit, String order, boolean asc) {
        Query query = new Query();
        query.skip(skip).limit(limit).with(new Sort((asc) ? Sort.Direction.ASC : Sort.Direction.DESC, order));

        List<SystemUserDTO> list = new ArrayList<>();

        for (SystemUserEntity systemUserEntity : getSysMongoTemplate().find(query, getEntityClass())) {
            SystemUserDTO systemUserDTO = fromEntity(systemUserEntity);
            if (systemUserDTO != null)
                list.add(systemUserDTO);
        }

        return list;
    }

    @Override
    public boolean updateAccountStatus(String id, Boolean accountStatus) {
        WriteResult wr = getSysMongoTemplate().updateFirst(
                Query.query(Criteria.where(SYSTEM_ID).is(id)),
                Update.update("state", accountStatus),
                getEntityClass());

        return wr.getN() == 1;
    }

    @Override
    public boolean updateAccountTime(String id, Date startDate, Date endDate) {

        Update update = new Update();
        if (startDate != null) {
            update = update.set("startTime", startDate.getTime());
        }

        if (endDate != null) {
            update = update.set("endTime", endDate.getTime());
        }

        WriteResult wr = getSysMongoTemplate().updateFirst(Query.query(Criteria.where(SYSTEM_ID).is(id)), update,
                getEntityClass());

        return wr.getN() == 1;
    }

    @Override
    public boolean updateAccountPayed(String id, Boolean payed) {
        Update update = new Update();
        update.set("payed", payed);
        WriteResult wr = getSysMongoTemplate().updateFirst(Query.query(Criteria.where(SYSTEM_ID).is(id)), update,
                getEntityClass());

        return wr.getN() == 1;
    }

    @Override
    public List<SystemUserModuleDTO> getUserModules(String userId) {
        SystemUserDTO systemUserDTO = findByUserId(userId);
        return systemUserDTO.getSystemUserModules();
    }

    @Override
    public SystemUserDTO findByUserName(String userName) {
        SystemUserEntity systemUserEntity = getSysMongoTemplate().findOne(
                Query.query(Criteria.where("userName").is(userName)),
                getEntityClass(),
                "sys_user");

        if (systemUserEntity == null)
            return null;

        return fromEntity(systemUserEntity);
    }

    @Override
    public SystemUserDTO findByUserId(String id) {
        SystemUserEntity systemUserEntity = getSysMongoTemplate().findOne(
                Query.query(Criteria.where(SYSTEM_ID).is(id)),
                getEntityClass()
        );

        if (systemUserEntity == null)
            return null;

        return fromEntity(systemUserEntity);
    }

    /**
     * 更新模块菜单权限
     *
     * @param id
     * @param usermoduleid
     * @param menus
     * @return
     */
    @Override
    public boolean updateModuleMenus(String id, String usermoduleid, List<SystemMenuDTO> menus) {
        WriteResult writeResult = getSysMongoTemplate().updateFirst(Query.query(Criteria.where(SYSTEM_ID).is(id).and
                ("modules._id").is
                (new ObjectId(usermoduleid))), Update.update("modules.$.menus", fromMenuDTO(menus)), getEntityClass());
        return updateSuccess(writeResult);
    }

    /**
     * 嵌套list必须循环拷贝
     *
     * @param menus
     * @return
     */
    public List<SystemMenuEntity> fromMenuDTO(List<SystemMenuDTO> menus) {

        if (menus == null) {
            return null;
        }
        if (menus.isEmpty()) {
            return Collections.emptyList();
        }

        List<SystemMenuEntity> resultList = Lists.newArrayList();

        menus.forEach((systemMenuDTO -> {
            // 拷贝本层目录属性
            SystemMenuEntity systemMenuEntity = ObjectUtils.convert(systemMenuDTO, SystemMenuEntity.class);
            systemMenuEntity.setId(new ObjectId(Calendar.getInstance().getTime()).toString());

            // 拷贝下一层目录属性
            List<SystemMenuDTO> subMenus = systemMenuDTO.getSubMenus();

            if (subMenus != null) {
                List<SystemSubMenuEntity> systemSubMenuEntities = fromSubMenuDTO(subMenus);

                systemMenuEntity.setSubMenus(systemSubMenuEntities);
            }

            resultList.add(systemMenuEntity);
        }));


        return resultList;
    }

    private List<SystemSubMenuEntity> fromSubMenuDTO(List<SystemMenuDTO> subMenus) {


        if (subMenus == null) {
            return null;
        }
        if (subMenus.isEmpty()) {
            return Collections.emptyList();
        }

        List<SystemSubMenuEntity> resultList = Lists.newArrayList();

        subMenus.forEach((systemMenuDTO -> {
            // 拷贝本层目录属性
            SystemSubMenuEntity systemMenuEntity = ObjectUtils.convert(systemMenuDTO, SystemSubMenuEntity.class);

            resultList.add(systemMenuEntity);
        }));


        return resultList;
    }


    @Override
    public boolean saveUserModule(String userid, SystemUserModuleDTO systemUserModuleDTO) {
        SystemUserDTO systemUserDTO = findByUserId(userid);

        systemUserModuleDTO.setId(new ObjectId(Calendar.getInstance().getTime()).toString());

//        List<SystemUserModuleDTO> systemUserModuleDTOs = systemUserDTO.getSystemUserModules();
//        if (systemUserModuleDTOs == null) {
//            WriteResult wr = getSysMongoTemplate().updateFirst(Query.query(Criteria.where(SYSTEM_ID).is(userid)),
//                    Update.update("modules", ObjectUtils.convert(systemUserModuleDTO, SystemUserModuleEntity.class)),
//                    getEntityClass());
//            return wr.getN() == 1;
//        } else {
//            boolean removed = systemUserModuleDTOs.removeIf((dto) -> dto.getModuleName().equals(systemUserModuleDTO.getModuleName()));
//            if (removed) {
//                return false;
//            } else {
        Update update = new Update();
        update.addToSet("modules", ObjectUtils.convert(systemUserModuleDTO, SystemUserModuleEntity.class));
        WriteResult wr = getSysMongoTemplate().updateFirst(Query.query(Criteria.where(SYSTEM_ID).is(userid)),
                update, getEntityClass());
        return wr.getN() == 1;
//            }
//        }
    }

    @Override
    public boolean deleteModule(String userid, String id) {

        SystemUserDTO systemUserDTO = findByUserId(userid);

        if (systemUserDTO == null) {
            return false;
        }

        Update update = new Update();

//        if (systemUserDTO.getSystemUserModules().size() == 1) {
//
//            update = update.unset("modules");
//        } else {

        update = update.pull("modules", new BasicDBObject("_id", new ObjectId(id)));

        // 如果模块数据为空, 则删除该内嵌文档属性
//            if (wr.getN() == 1) {
//                systemUserDTO = findByUserId(userid);
//                if (systemUserDTO.getSystemUserModules().isEmpty()) {
//                    update = new Update();
//                    update.unset("modules");
//                    getSysMongoTemplate().updateFirst(Query.query(Criteria.where(SYSTEM_ID).is(userid).and("modules.moduleId").is(moduleId)),
//                            update, getEntityClass());
//                }
//            }
//        }

        WriteResult wr = getSysMongoTemplate().updateFirst(Query.query(Criteria.where(SYSTEM_ID).is(userid)),
                update, getEntityClass());
        return updateSuccess(wr);

    }

    @Override
    public SystemUserModuleDTO getUserModuleByModuleId(String id, String moduleid) {
        SystemUserEntity systemUserEntity = getSysMongoTemplate().findOne(Query.query(Criteria.where(SYSTEM_ID).is(id).and("modules.moduleId").is(moduleid)), getEntityClass());

        if (systemUserEntity == null) {
            return null;
        }

        SystemUserModuleEntity systemUserModuleEntity = systemUserEntity.getSystemUserModules().stream().findFirst()
                .filter((entity) -> moduleid.equals(entity.getModuleId())).get();

        if (systemUserModuleEntity == null) {
            return null;
        }


        return fromEntity(systemUserModuleEntity);
    }

    @Override
    public boolean existsModule(String userid, String moduleId) {
        return getSysMongoTemplate().exists(Query.query(Criteria.where(SYSTEM_ID).is(userid).and("modules.moduleId").is(moduleId)), getEntityClass());
    }

    @Override
    public SystemUserModuleDTO getUserModuleById(String userid, String id) {
        SystemUserEntity systemUserEntity = getSysMongoTemplate()
                .findOne(Query.query(Criteria.where(SYSTEM_ID).is(userid).and("modules._id").is(new ObjectId(id))), getEntityClass());

        Optional<SystemUserModuleEntity> optional = systemUserEntity.getSystemUserModules().stream().findFirst().filter((entity -> entity.getId().equals(id)));
        if (optional.isPresent()) {
            return fromEntity(optional.get());
        }

        return null;
    }

    @Override
    public boolean addModuleAccount(String id, String moduleid, ModuleAccountInfoDTO moduleAccountInfoDTO) {

        boolean exists = accountExistsByAccountName(id, moduleAccountInfoDTO.getBaiduUserName());

        ModuleAccountInfoEntity moduleAccountInfoEntity = ObjectUtils.convert(moduleAccountInfoDTO, ModuleAccountInfoEntity.class);
        moduleAccountInfoEntity.setId(new ObjectId(Calendar.getInstance().getTime()).toString());

        Update update = new Update();
        update.addToSet("modules.$.accounts", ObjectUtils.convert(moduleAccountInfoDTO, ModuleAccountInfoEntity.class));

        WriteResult wr = getSysMongoTemplate().updateFirst(Query.query(Criteria.where(SYSTEM_ID).is(id).and("modules._id").is(new ObjectId(moduleid))), update, getEntityClass());

        return updateSuccess(wr);
    }

    @Override
    public SystemModuleDTO findSystemModuleByModuleName(String username, String moduleName) {
        SystemModuleEntity systemModuleEntity = getSysMongoTemplate().findOne(
                Query.query(Criteria.where("moduleName").is(moduleName)),
                SystemModuleEntity.class);
        if (Objects.isNull(systemModuleEntity))
            return null;

        return ObjectUtils.convert(systemModuleEntity, SystemModuleDTO.class);
    }

    @Override
    public void updateUserImage(InputStream is, String fileName, Map<String, Object> metaData) {
        gridFsTemplate.delete(Query.query(Criteria.where("userId").is(metaData.get("userId").toString())));

        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        DBObject _metaData = new BasicDBObject();
        for (Map.Entry<String, Object> entry : metaData.entrySet()) {
            _metaData.put(entry.getKey(), entry.getValue());
        }

        gridFsTemplate.store(is, fileName, "image/" + fileType, _metaData);
    }

    @Override
    public InputStream findUserImage(String sysUserId) {
        GridFSDBFile gridFSDBFile = gridFsTemplate.findOne(Query.query(Criteria.where("metadata.userId").is(sysUserId)));
        return Objects.nonNull(gridFSDBFile) ? gridFSDBFile.getInputStream() : null;
    }

    @Override
    public boolean updateAccountPassword(String userid, String password) {
        WriteResult wr = getSysMongoTemplate().updateFirst(Query.query(Criteria.where(SYSTEM_ID).is(userid)), Update.update("password", password), getEntityClass());


        return updateSuccess(wr);
    }

    @Override
    public boolean updateUserMenus(String userid, UserModuleMenuDTO userModuleMenuDTO) {

        Update update = new Update();
        update.addToSet("menus", ObjectUtils.convert(userModuleMenuDTO, UserModuleMenuEntity.class));

        WriteResult wr = getSysMongoTemplate().updateFirst(Query.query(Criteria.where(SYSTEM_ID).is(userid)), update, getEntityClass());


        return updateSuccess(wr);
    }

    /**
     * 更新模块信息
     *
     * @param systemUserModuleDTO
     * @param id
     * @return
     */
    @Override
    public boolean updateModuleInfo(SystemUserModuleDTO systemUserModuleDTO, String id) {
        Update update = new Update();

        update.addToSet("$.modules", systemUserModuleDTO);

        WriteResult wr = getSysMongoTemplate().updateFirst(Query.query(Criteria.where(SYSTEM_ID).is(id).and("modules.moduleName").is(SystemNameConstant.SOUKE_SYSTEM_NAME)),
                update, getEntityClass());
        return wr.getN() == 1;
    }

    @Override
    public boolean updateUserBaseInfo(String userid, SystemUserDTO systemUserDTO) {
        Update update = new Update();

        update.set("userName", systemUserDTO.getUserName());
        update.set("companyName", systemUserDTO.getCompanyName());
        update.set("address", systemUserDTO.getAddress());
        update.set("contactName", systemUserDTO.getContactName());
        update.set("email", systemUserDTO.getEmail());
        update.set("telephone", systemUserDTO.getTelephone());


        WriteResult wr = getSysMongoTemplate().updateFirst(Query.query(Criteria.where(SYSTEM_ID).is(userid)), update, getEntityClass());

        return wr.getN() == 1;
    }

    @Override
    public void updateUserEmail(String userId, String email) {
        getSysMongoTemplate().updateFirst(
                Query.query(Criteria.where(SYSTEM_ID).is(new ObjectId(userId))), Update.update("email", email),
                getEntityClass());
    }

    @Override
    public String getUserEmail(String username) {
        SystemUserEntity systemUserEntity = getSysMongoTemplate().findOne(Query.query(Criteria.where("userName").is(username)), getEntityClass());
        if (Objects.isNull(systemUserEntity))
            return null;

        return systemUserEntity.getEmail();
    }

    private boolean accountExistsByAccountName(String userid, String baiduUserName) {

        return getSysMongoTemplate().exists(Query.query(Criteria.where("modules.accounts.bname").is(baiduUserName)), getEntityClass());
    }

    private SystemUserModuleDTO fromEntity(SystemUserModuleEntity systemUserModuleEntity) {
        if (systemUserModuleEntity == null) {
            return null;
        }

        SystemUserModuleDTO systemUserModuleDTO = ObjectUtils.convert(systemUserModuleEntity, SystemUserModuleDTO.class);

        List<SystemMenuEntity> menuEntities = systemUserModuleEntity.getMenus();

        if (menuEntities != null) {
            List<SystemMenuDTO> systemMenuDTOs = fromMenuEntity(menuEntities, SystemMenuDTO.class);
            systemUserModuleDTO.setMenus(systemMenuDTOs);
        }

        return systemUserModuleDTO;
    }

    private List<SystemMenuDTO> fromMenuEntity(List<SystemMenuEntity> menuEntities, Class<SystemMenuDTO> systemMenuDTOClass) {
        if (menuEntities.isEmpty()) {
            return Collections.emptyList();
        }

        List<SystemMenuDTO> resultList = Lists.newArrayList();
        menuEntities.stream().forEach(systemMenuEntity -> {
            SystemMenuDTO systemMenuDTO = ObjectUtils.convert(systemMenuEntity, systemMenuDTOClass);

            List<SystemSubMenuEntity> subMenus = systemMenuEntity.getSubMenus();
            if (subMenus != null) {
                systemMenuDTO.setSubMenus(fromSubMenuEntity(subMenus, SystemMenuDTO.class));
            }

            resultList.add(systemMenuDTO);
        });

        return resultList;
    }

    private List<SystemMenuDTO> fromSubMenuEntity(List<SystemSubMenuEntity> menuEntities, Class<SystemMenuDTO> systemMenuDTOClass) {
        if (menuEntities.isEmpty()) {
            return Collections.emptyList();
        }

        List<SystemMenuDTO> resultList = Lists.newArrayList();
        menuEntities.stream().forEach(systemMenuEntity -> {
            resultList.add(ObjectUtils.convert(systemMenuEntity, systemMenuDTOClass));

        });
        return resultList;
    }

    private boolean updateSuccess(WriteResult writeResult) {
        return writeResult.getN() == 1;
    }

    @Override
    public SystemUserDTO save(SystemUserDTO dto) {
        getSysMongoTemplate().save(toEntity(dto));
        return dto;
    }

    @Override
    public List<SystemUserDTO> find(Map<String, Object> params, int skip, int limit) {
        return Lists.newArrayList();
    }

    protected SystemUserDTO fromEntity(SystemUserEntity systemUserEntity) {
//        List<ModuleAccountInfoEntity> moduleAccountInfoEntityList = systemUserEntity
//                .getSystemUserModules()
//                .stream()
//                .filter(o -> Objects.equals(AppContext.getModuleName(), o.getModuleName()))
//                .findFirst()
//                .get()
//                .getAccounts();
//
//        SystemUserDTO user = ObjectUtils.convert(systemUserEntity, getDTOClass());
//
//        List<BaiduAccountInfoDTO> dtoList = convertByClass(moduleAccountInfoEntityList, BaiduAccountInfoDTO.class);
//        user.setBaiduAccounts(dtoList);
//
//
//        List<SystemUserModuleEntity> systemUserModuleEntities = systemUserEntity.getSystemUserModules();
//        if (systemUserModuleEntities != null) {
//            user.setSystemUserModules(ObjectUtils.convertToList(systemUserModuleEntities, SystemUserModuleDTO.class));
//        }
//        return user;
        SystemUserDTO systemUserDTO = SystemUserUtils.retrieveDTOFromEntity(systemUserEntity);
        List<SystemUserModuleDTO> systemUserModuleDTOs = systemUserDTO.getSystemUserModules();
        for (SystemUserModuleDTO systemUserModuleDTO : systemUserModuleDTOs) {
            SystemModuleEntity systemModuleEntity = getSysMongoTemplate().findOne(
                    Query.query(Criteria.where(SYSTEM_ID).is(systemUserModuleDTO.getModuleId())),
                    SystemModuleEntity.class);
            if (Objects.isNull(systemModuleEntity))
                continue;

            systemUserModuleDTO.setModuleName(systemModuleEntity.getModuleName());
            systemUserModuleDTO.setModuleUrl(systemModuleEntity.getModuleUrl());
        }

        return systemUserDTO;
    }


    protected SystemUserEntity toEntity(SystemUserDTO systemUserDTO) {
//        List<BaiduAccountInfoDTO> baiduAccountInfoDTOList = systemUserDTO.getBaiduAccounts();
//        SystemUserEntity user = ObjectUtils.convert(systemUserDTO, getEntityClass());
//
//        List<ModuleAccountInfoEntity> moduleAccountInfoEntityList = convertByClass(baiduAccountInfoDTOList, ModuleAccountInfoEntity.class);
//        user.setBaiduAccounts(moduleAccountInfoEntityList);
//        return user;

        return SystemUserUtils.retrieveEntityFromDTO(systemUserDTO);
    }
}
