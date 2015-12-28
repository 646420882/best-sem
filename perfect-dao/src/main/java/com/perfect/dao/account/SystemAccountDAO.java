package com.perfect.dao.account;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.dto.sys.SystemModuleDTO;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.entity.sys.ModuleAccountInfoEntity;
import com.perfect.entity.sys.SystemUserEntity;

import java.util.List;

/**
 * Created on 2015-12-22.
 * <p>
 * 百思模块帐号查询接口
 * </p>
 *
 * @author dolphineor
 */
public interface SystemAccountDAO extends MongoEntityConstants {

    String ACCOUNT_STATUS = "acstate";
    String USER_ID = "userId";
    String USER_NAME = "userName";
    String MODULE_ID = "moduleId";
    String MODULE_NAME = "moduleName";
    String BAIDU_ACCOUNT_ID = "bid";


    /**
     * 获取所有系统用户
     *
     * @return
     */
    List<SystemUserDTO> findAllUser();

    /**
     * 根据Mongo ID查询用户
     *
     * @param id
     * @return
     */
    SystemUserDTO findByUserId(String id);

    /**
     * 根据系统用户名查询用户
     *
     * @param userName
     * @return
     */
    SystemUserDTO findByUserName(String userName);

    /**
     * 根据模块帐号查询系统用户
     *
     * @param moduleAccountId
     * @return
     */
    SystemUserDTO findUserByModuleAccountId(long moduleAccountId);

    /**
     * 新增用户
     *
     * @param dto
     */
    void insertUser(SystemUserDTO dto);

    /**
     * 根据Mongo ID删除用户
     *
     * @param id
     * @return
     */
    boolean deleteByUserId(String id);

    /**
     * 根据系统用户名删除用户
     *
     * @param userName
     * @return
     */
    boolean deleteByUserName(String userName);

    /**
     * 更新系统用户信息
     *
     * @param systemUserDTO
     * @return
     */
    boolean updateSystemUserInfo(SystemUserDTO systemUserDTO);

    /**
     * 密码校验
     *
     * @param userName
     * @param password
     * @return
     */
    boolean passwordCheck(String userName, String password);


    /**
     * 根据模块名称查询模块详细信息
     *
     * @param moduleName
     * @return
     */
    SystemModuleDTO findByModuleName(String moduleName);

    /**
     * 根据系统用户名和模块名称获取用户的模块ID{@link com.perfect.entity.sys.ModuleAccountInfoEntity#id}
     *
     * @param userName
     * @param moduleName
     * @return
     */
    String findSysUserModuleId(String userName, String moduleName);

    /**
     * 获取所有的模块帐号
     *
     * @return
     */
    List<ModuleAccountInfoDTO> findAllModuleAccount();

    /**
     * 根据系统用户名获取模块帐号
     *
     * @param userName
     * @return
     */
    List<ModuleAccountInfoDTO> findAllModuleAccountByUserName(String userName);

    /**
     * 根据系统用户ID(Mongo ID)和模块ID(Mongo ID)获取模块帐号
     *
     * @param userId
     * @param moduleId
     * @return
     */
    List<ModuleAccountInfoDTO> findByUserIdAndModuleId(String userId, String moduleId);

    /**
     * 根据模块帐号ID(not mongo ObjectId)获取模块帐号详细信息
     *
     * @param moduleAccountId
     * @return
     */
    ModuleAccountInfoDTO findByModuleAccountId(long moduleAccountId);

    /**
     * 根据Mongo Object ID查询模块帐号详细信息
     *
     * @param id
     * @return
     */
    ModuleAccountInfoDTO findModuleAccountById(String id);

    /**
     * 新增模块帐号
     *
     * @param moduleAccount
     */
    void insertModuleAccount(ModuleAccountInfoDTO moduleAccount);

    /**
     * 更新模块帐号信息
     *
     * @param moduleAccount
     * @return
     */
    boolean updateModuleAccount(ModuleAccountInfoDTO moduleAccount);

    /**
     * 根据Mongo ID删除模块帐号
     *
     * @param id
     * @return
     */
    boolean deleteByObjectId(String id);

    /**
     * 根据模块帐号ID删除模块帐号信息
     *
     * @param moduleAccountId
     * @return
     */
    boolean deleteByModuleAccountId(long moduleAccountId);


    default Class<SystemUserEntity> getSystemUserEntityClass() {
        return SystemUserEntity.class;
    }

    default Class<ModuleAccountInfoEntity> getModuleAccountInfoEntityClass() {
        return ModuleAccountInfoEntity.class;
    }

    /**
     * @param userId
     * @param moduleAccountObjectId
     * @param token
     * @return
     * @see {@link #updateModuleAccount(ModuleAccountInfoDTO)}
     */
    @Deprecated
    boolean updateAccountToken(String userId, String moduleAccountObjectId, String token);
}
