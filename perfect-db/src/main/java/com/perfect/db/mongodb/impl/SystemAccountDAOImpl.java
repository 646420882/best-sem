package com.perfect.db.mongodb.impl;

import com.mongodb.WriteResult;
import com.perfect.dao.account.SystemAccountDAO;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.dto.sys.SystemModuleDTO;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.dto.sys.SystemUserModuleDTO;
import com.perfect.entity.sys.ModuleAccountInfoEntity;
import com.perfect.entity.sys.SystemModuleEntity;
import com.perfect.entity.sys.SystemUserEntity;
import com.perfect.entity.sys.SystemUserModuleEntity;
import com.perfect.utils.MD5;
import com.perfect.utils.SystemUserUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.perfect.commons.constants.PasswordSalts.USER_SALT;

/**
 * Created on 2015-12-22.
 *
 * @author dolphineor
 */
@Repository("systemAccountDAO")
public class SystemAccountDAOImpl extends AbstractSysBaseDAOImpl<ModuleAccountInfoDTO, String> implements SystemAccountDAO {

    @Resource
    private MongoTemplate mongoTemplate;

    private final MD5.Builder md5Builder = new MD5.Builder();

    @Override
    public List<SystemUserDTO> findAllUser() {
        List<SystemUserDTO> systemUserDTOs = new ArrayList<>();

        for (SystemUserEntity systemUserEntity : mongoTemplate.findAll(getSystemUserEntityClass())) {
            systemUserDTOs.add(getDTOFromEntity(systemUserEntity));
        }

        return systemUserDTOs;
    }

    @Override
    public SystemUserDTO findByUserId(String id) {
        SystemUserEntity entity = mongoTemplate.findOne(Query.query(Criteria.where(SYSTEM_ID).is(new ObjectId(id))),
                getSystemUserEntityClass());
        if (Objects.isNull(entity))
            return null;

        return getDTOFromEntity(entity);
    }

    @Override
    public SystemUserDTO findByUserName(String userName) {
        SystemUserEntity entity = mongoTemplate.findOne(Query.query(Criteria.where(USER_NAME).is(userName)),
                getSystemUserEntityClass());
        if (Objects.isNull(entity))
            return null;

        return getDTOFromEntity(entity);
    }

    @Override
    public SystemUserDTO findUserByModuleAccountId(long moduleAccountId) {
        ModuleAccountInfoDTO moduleAccountInfoDTO = findByModuleAccountId(moduleAccountId);
        if (Objects.isNull(moduleAccountInfoDTO))
            return null;

        return findByUserId(moduleAccountInfoDTO.getUserId());
    }

    @Override
    public void insertUser(SystemUserDTO dto) {
        mongoTemplate.insert(getEntityFromDTO(dto));
    }

    @Override
    public boolean deleteByUserId(String id) {
        WriteResult result = mongoTemplate.remove(
                Query.query(Criteria.where(SYSTEM_ID).is(new ObjectId(id))),
                getSystemUserEntityClass());
        return result.isUpdateOfExisting();
    }

    @Override
    public boolean deleteByUserName(String userName) {
        WriteResult result = mongoTemplate.remove(
                Query.query(Criteria.where(USER_NAME).is(userName)),
                getSystemUserEntityClass());
        return result.isUpdateOfExisting();
    }

    @Override
    public boolean updateSystemUserInfo(SystemUserDTO systemUserDTO) {
        Update update = new Update();
        if (Objects.nonNull(systemUserDTO.getPassword())) {
            update.set("password", systemUserDTO.getPassword());
        }
        if (Objects.nonNull(systemUserDTO.getEmail())) {
            update.set("email", systemUserDTO.getEmail());
        }
        if (Objects.nonNull(systemUserDTO.getAccess())) {
            update.set("access", systemUserDTO.getAccess());
        }
        if (Objects.nonNull(systemUserDTO.getAccountState())) {
            update.set(ACCOUNT_STATUS, systemUserDTO.getAccountState());
        }
        if (Objects.nonNull(systemUserDTO.getAddress())) {
            update.set("address", systemUserDTO.getAddress());
        }
        if (Objects.nonNull(systemUserDTO.getCompanyName())) {
            update.set("companyName", systemUserDTO.getCompanyName());
        }
        if (Objects.nonNull(systemUserDTO.getContactName())) {
            update.set("contactName", systemUserDTO.getContactName());
        }
        if (systemUserDTO.getCtime() > 0) {
            update.set("ctime", systemUserDTO.getCtime());
        }
        if (Objects.nonNull(systemUserDTO.getMobilePhone())) {
            update.set("mobilePhone", systemUserDTO.getMobilePhone());
        }
        if (Objects.nonNull(systemUserDTO.getState())) {
            update.set("state", systemUserDTO.getState());
        }
        if (Objects.nonNull(systemUserDTO.getTelephone())) {
            update.set("telephone", systemUserDTO.getTelephone());
        }

        WriteResult result = mongoTemplate.updateFirst(
                Query.query(Criteria.where(USER_NAME).is(systemUserDTO.getUserName())),
                update,
                getSystemUserEntityClass());

        return result.isUpdateOfExisting();
    }

    @Override
    public boolean passwordCheck(String userName, String password) {
        SystemUserDTO systemUserDTO = findByUserName(userName);

        return Objects.equals(md5Builder.source(password).salt(USER_SALT).build().getMD5(), systemUserDTO.getPassword());
    }

    @Override
    public SystemModuleDTO findByModuleName(String moduleName) {
        SystemModuleEntity entity = mongoTemplate.findOne(
                Query.query(Criteria.where(MODULE_NAME).is(moduleName)),
                SystemModuleEntity.class);
        if (Objects.isNull(entity))
            return null;

        SystemModuleDTO dto = new SystemModuleDTO();
        BeanUtils.copyProperties(entity, dto);

        return dto;
    }

    @Override
    public String findSysUserModuleId(String userName, String moduleName) {
        SystemUserDTO systemUserDTO = findByUserName(userName);

        try {
            return systemUserDTO.getSystemUserModules()
                    .stream()
                    .filter(o -> Objects.equals(moduleName, o.getModuleName()))
                    .findFirst()
                    .get()
                    .getId();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public List<ModuleAccountInfoDTO> findAllModuleAccount() {
        List<ModuleAccountInfoDTO> moduleAccountInfoDTOs = new ArrayList<>();
        for (ModuleAccountInfoEntity entity : mongoTemplate.findAll(getModuleAccountInfoEntityClass())) {
            moduleAccountInfoDTOs.add(getDTOFromEndity(entity));
        }

        return moduleAccountInfoDTOs;
    }

    @Override
    public List<ModuleAccountInfoDTO> findAllModuleAccountByUserName(String userName) {
        SystemUserDTO systemUserDTO = findByUserName(userName);
        if (Objects.isNull(systemUserDTO))
            return Collections.emptyList();

        List<ModuleAccountInfoDTO> moduleAccountInfoDTOs = new ArrayList<>();
        for (ModuleAccountInfoEntity entity : mongoTemplate.find(Query.query(Criteria.where(USER_ID).is(systemUserDTO.getId())),
                getModuleAccountInfoEntityClass())) {
            moduleAccountInfoDTOs.add(getDTOFromEndity(entity));
        }

        return moduleAccountInfoDTOs;
    }

    @Override
    public List<ModuleAccountInfoDTO> findByUserIdAndModuleId(String userId, String moduleId) {
        List<ModuleAccountInfoEntity> entities = mongoTemplate.find(
                Query.query(Criteria.where(USER_ID).is(userId).and(MODULE_ID).is(moduleId)),
                getModuleAccountInfoEntityClass());

        List<ModuleAccountInfoDTO> dtos = new ArrayList<>();
        for (ModuleAccountInfoEntity entity : entities) {
            dtos.add(getDTOFromEndity(entity));
        }

        return dtos;
    }

    @Override
    public ModuleAccountInfoDTO findByModuleAccountId(long moduleAccountId) {
        ModuleAccountInfoEntity entity = mongoTemplate.findOne(
                Query.query(Criteria.where(BAIDU_ACCOUNT_ID).is(moduleAccountId)),
                getModuleAccountInfoEntityClass());
        if (Objects.isNull(entity))
            return null;

        return getDTOFromEndity(entity);
    }

    @Override
    public ModuleAccountInfoDTO findModuleAccountById(String id) {
        ModuleAccountInfoEntity entity = mongoTemplate.findOne(
                Query.query(Criteria.where(SYSTEM_ID).is(new ObjectId(id))),
                getModuleAccountInfoEntityClass());
        if (Objects.isNull(entity))
            return null;

        return getDTOFromEndity(entity);
    }

    @Override
    public void insertModuleAccount(ModuleAccountInfoDTO moduleAccount) {
        long count = mongoTemplate.count(
                Query.query(Criteria.where(USER_ID).is(moduleAccount.getUserId()).and(MODULE_ID).is(moduleAccount.getModuleId())),
                getModuleAccountInfoEntityClass());

        if (count == 0) {
            moduleAccount.setDfault(true);
        }

        ModuleAccountInfoEntity entity = getEntityFromDTO(moduleAccount);
        mongoTemplate.insert(entity);
        moduleAccount.setId(entity.getId());
    }

    @Override
    public boolean updateModuleAccount(ModuleAccountInfoDTO moduleAccount) {
        Criteria criteria = Criteria.where(SYSTEM_ID).is(new ObjectId(moduleAccount.getId()));
        if (Objects.nonNull(moduleAccount.getUserId())) {
            criteria.and("userId").is(moduleAccount.getUserId());
        }
        Query query = Query.query(criteria);

        Update update = new Update();
        if (Objects.nonNull(moduleAccount.getBaiduAccountId())) {
            update.set("bid", moduleAccount.getBaiduAccountId());
        }
        if (Objects.nonNull(moduleAccount.getBaiduUserName())) {
            update.set("bname", moduleAccount.getBaiduUserName());
        }
        if (Objects.nonNull(moduleAccount.getBaiduPassword())) {
            update.set("bpasswd", moduleAccount.getBaiduPassword());
        }
        if (Objects.nonNull(moduleAccount.getToken())) {
            update.set("btoken", moduleAccount.getToken());
        }
        if (Objects.nonNull(moduleAccount.getBaiduRemarkName())) {
            update.set("rname", moduleAccount.getBaiduRemarkName());
        }
        if (Objects.nonNull(moduleAccount.getBestRegDomain())) {
            update.set("brd", moduleAccount.getBestRegDomain());
        }
        if (Objects.nonNull(moduleAccount.getBudget())) {
            update.set("bgt", moduleAccount.getBudget());
        }
        if (Objects.nonNull(moduleAccount.isDynamicCreative())) {
            update.set("dc", moduleAccount.isDynamicCreative());
        }
        if (Objects.nonNull(moduleAccount.getExcludeIp())) {
            update.set("exIp", moduleAccount.getExcludeIp());
        }
        if (Objects.nonNull(moduleAccount.getBalance())) {
            update.set("b", moduleAccount.getBalance());
        }
        if (Objects.nonNull(moduleAccount.getCost())) {
            update.set("c", moduleAccount.getCost());
        }
        if (Objects.nonNull(moduleAccount.getPayment())) {
            update.set("pay", moduleAccount.getPayment());
        }
        if (Objects.nonNull(moduleAccount.getBudgetType())) {
            update.set("bgtt", moduleAccount.getBudgetType());
        }
        if (Objects.nonNull(moduleAccount.getRegionTarget())) {
            update.set("rt", moduleAccount.getRegionTarget());
        }
        if (Objects.nonNull(moduleAccount.getOpenDomains())) {
            update.set("od", moduleAccount.getOpenDomains());
        }
        if (Objects.nonNull(moduleAccount.getRegDomain())) {
            update.set("rd", moduleAccount.getRegDomain());
        }
        if (Objects.nonNull(moduleAccount.getBudgetOfflineTime())) {
            update.set("bot", moduleAccount.getBudgetOfflineTime());
        }
        if (Objects.nonNull(moduleAccount.getWeeklyBudget())) {
            update.set("wb", moduleAccount.getWeeklyBudget());
        }
        if (Objects.nonNull(moduleAccount.getUserStat())) {
            update.set("us", moduleAccount.getUserStat());
        }
        if (Objects.nonNull(moduleAccount.isDynamicCreative())) {
            update.set("dcp", moduleAccount.isDynamicCreative());
        }
        if (Objects.nonNull(moduleAccount.getOpt())) {
            update.set("o", moduleAccount.getOpt());
        }
        if (Objects.nonNull(moduleAccount.getState())) {
            update.set("state", moduleAccount.getState());
        }

        WriteResult result = mongoTemplate.updateFirst(query, update, getModuleAccountInfoEntityClass());
        return result.isUpdateOfExisting();
    }

    @Override
    public boolean deleteByObjectId(String id) {
        WriteResult result = mongoTemplate.remove(
                Query.query(Criteria.where(SYSTEM_ID).is(new ObjectId(id))),
                getModuleAccountInfoEntityClass());
        return result.isUpdateOfExisting();
    }

    @Override
    public boolean deleteByModuleAccountId(long moduleAccountId) {
        WriteResult result = mongoTemplate.remove(
                Query.query(Criteria.where(BAIDU_ACCOUNT_ID).is(moduleAccountId)),
                getModuleAccountInfoEntityClass());
        return result.isUpdateOfExisting();
    }

    @Override
    public boolean updateAccountToken(String userId, String moduleAccountObjectId, String token) {
        WriteResult result = mongoTemplate.updateFirst(
                Query.query(Criteria.where("userId").is(userId).and(SYSTEM_ID).is(new ObjectId(moduleAccountObjectId))),
                Update.update("btoken", token), getModuleAccountInfoEntityClass());
        return result.isUpdateOfExisting();
    }


    private SystemUserEntity getEntityFromDTO(SystemUserDTO dto) {
        return SystemUserUtils.retrieveEntityFromDTO(dto);
    }

    private SystemUserDTO getDTOFromEntity(SystemUserEntity entity) {
        SystemUserDTO systemUserDTO = new SystemUserDTO();
        BeanUtils.copyProperties(entity, systemUserDTO);

        List<SystemUserModuleDTO> systemUserModuleDTOs = new ArrayList<>();
        for (SystemUserModuleEntity systemUserModule : entity.getSystemUserModules()) {
            SystemUserModuleDTO systemUserModuleDTO = new SystemUserModuleDTO();
            BeanUtils.copyProperties(systemUserModule, systemUserModuleDTO);
//            systemUserModuleDTO.setModuleUrl(findByModuleName(systemUserModule.getModuleName()).getModuleUrl());

            List<ModuleAccountInfoDTO> moduleAccountDTOs = findByUserIdAndModuleId(entity.getId(), systemUserModule.getId());
            systemUserModuleDTO.setAccounts(moduleAccountDTOs);

            systemUserModuleDTOs.add(systemUserModuleDTO);
        }
        systemUserDTO.setSystemUserModules(systemUserModuleDTOs);

        return systemUserDTO;
    }

    private ModuleAccountInfoEntity getEntityFromDTO(ModuleAccountInfoDTO dto) {
        ModuleAccountInfoEntity entity = new ModuleAccountInfoEntity();
        BeanUtils.copyProperties(dto, entity);

        return entity;
    }

    private ModuleAccountInfoDTO getDTOFromEndity(ModuleAccountInfoEntity entity) {
        ModuleAccountInfoDTO dto = new ModuleAccountInfoDTO();
        BeanUtils.copyProperties(entity, dto);

        return dto;
    }

    @Override
    public Class<ModuleAccountInfoEntity> getEntityClass() {
        return ModuleAccountInfoEntity.class;
    }

    @Override
    public Class<ModuleAccountInfoDTO> getDTOClass() {
        return ModuleAccountInfoDTO.class;
    }
}
