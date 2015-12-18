package com.perfect.db.mongodb.impl;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.gridfs.GridFSDBFile;
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.dto.sys.SystemMenuDTO;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.dto.sys.SystemUserModuleDTO;
import com.perfect.entity.adgroup.AdgroupEntity;
import com.perfect.entity.campaign.CampaignEntity;
import com.perfect.entity.creative.CreativeEntity;
import com.perfect.entity.keyword.KeywordEntity;
import com.perfect.entity.sys.*;
import com.perfect.utils.ObjectUtils;
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
    public void addBaiduAccount(List<BaiduAccountInfoDTO> list, String currSystemUserName) {
        SystemUserDTO currSystemUserDTO = findByUserName(currSystemUserName);
        List<BaiduAccountInfoDTO> _list = currSystemUserDTO.getBaiduAccounts();
        if (_list == null)
            _list = new ArrayList<>();

        _list.addAll(list);
        getSysMongoTemplate().updateFirst(Query.query(Criteria.where("userName").is(currSystemUserName)), Update.update("baiduAccountInfos", _list), "sys_user");
    }

    @Override
    public SystemUserDTO findByAid(long aid) {
        Query query = Query.query(Criteria.where("bdAccounts._id").is(aid));
        SystemUserEntity entity = getSysMongoTemplate().findOne(query, getEntityClass());
        SystemUserDTO dto = ObjectUtils.convertToObject(entity, getDTOClass());
        return dto;
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
    public void insertAccountInfo(String userName, BaiduAccountInfoDTO baiduAccountInfoDTO) {
        SystemUserDTO systemUserDTO = findByUserName(userName);
        if (systemUserDTO.getBaiduAccounts().isEmpty())
            baiduAccountInfoDTO.setDfault(true);

        ModuleAccountInfoEntity moduleAccountInfoEntity = ObjectUtils.convert(baiduAccountInfoDTO, ModuleAccountInfoEntity.class);
        Update update = new Update();
        update.addToSet("bdAccounts", moduleAccountInfoEntity);
        getSysMongoTemplate().upsert(Query.query(Criteria.where("userName").is(userName)), update, getEntityClass());
    }

    @Override
    public int removeAccountInfo(List<BaiduAccountInfoDTO> baiduAccountInfoDTOs, String account) {

        List<ModuleAccountInfoEntity> baiduAccountInfoEntities = ObjectUtils.convert(baiduAccountInfoDTOs, ModuleAccountInfoEntity.class);


        WriteResult writeResult = getSysMongoTemplate().updateFirst(Query.query(Criteria.where("userName").is(account)),
                Update.update("bdAccounts", baiduAccountInfoEntities), getEntityClass());
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
        return convert(getSysMongoTemplate().find(query, getEntityClass()));
    }

    @Override
    public boolean updateAccountStatus(String id, Boolean accountStatus) {

        WriteResult wr = getSysMongoTemplate().updateFirst(Query.query(Criteria.where(SYSTEM_ID).is(id)), Update.update
                ("state",
                        accountStatus), getEntityClass());

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
        return systemUserDTO.getModuleDTOList();
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
            return Collections.EMPTY_LIST;
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
            return Collections.EMPTY_LIST;
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

//        List<SystemUserModuleDTO> systemUserModuleDTOs = systemUserDTO.getModuleDTOList();
//        if (systemUserModuleDTOs == null) {
//            WriteResult wr = getSysMongoTemplate().updateFirst(Query.query(Criteria.where(SYSTEM_ID).is(userid)),
//                    Update.update("modules", ObjectUtils.convert(systemUserModuleDTO, SystemUserModuleEntity.class)),
//                    getEntityClass());
//            return wr.getN() == 1;
//        } else {
//            boolean removed = systemUserModuleDTOs.removeIf((dto) -> dto.getModuleId().equals(systemUserModuleDTO.getModuleId()));
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

//        if (systemUserDTO.getModuleDTOList().size() == 1) {
//
//            update = update.unset("modules");
//        } else {

        update = update.pull("modules", new BasicDBObject("_id", new ObjectId(id)));

        // 如果模块数据为空, 则删除该内嵌文档属性
//            if (wr.getN() == 1) {
//                systemUserDTO = findByUserId(userid);
//                if (systemUserDTO.getModuleDTOList().isEmpty()) {
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

        SystemUserModuleEntity systemUserModuleEntity = systemUserEntity.getSystemUserModuleEntities().stream().findFirst()
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

        Optional<SystemUserModuleEntity> optional = systemUserEntity.getSystemUserModuleEntities().stream().findFirst().filter((entity -> entity.getId().equals(id)));
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
            return Collections.EMPTY_LIST;
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
            return Collections.EMPTY_LIST;
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
        List<ModuleAccountInfoEntity> moduleAccountInfoEntityList = systemUserEntity.getBaiduAccounts();
        SystemUserDTO user = ObjectUtils.convert(systemUserEntity, getDTOClass());

        List<BaiduAccountInfoDTO> dtoList = convertByClass(moduleAccountInfoEntityList, BaiduAccountInfoDTO.class);
        user.setBaiduAccounts(dtoList);


        List<SystemUserModuleEntity> systemUserModuleEntities = systemUserEntity.getSystemUserModuleEntities();
        if (systemUserModuleEntities != null) {
            user.setModuleDTOList(ObjectUtils.convertToList(systemUserModuleEntities, SystemUserModuleDTO.class));
        }
        return user;
    }


    protected SystemUserEntity toEntity(SystemUserDTO systemUserDTO) {
        List<BaiduAccountInfoDTO> baiduAccountInfoDTOList = systemUserDTO.getBaiduAccounts();
        SystemUserEntity user = ObjectUtils.convert(systemUserDTO, getEntityClass());

        List<ModuleAccountInfoEntity> moduleAccountInfoEntityList = convertByClass(baiduAccountInfoDTOList, ModuleAccountInfoEntity.class);
        user.setBaiduAccounts(moduleAccountInfoEntityList);
        return user;
    }
}
