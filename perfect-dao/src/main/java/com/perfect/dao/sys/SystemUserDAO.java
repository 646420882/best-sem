package com.perfect.dao.sys;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.sys.*;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by vbzer_000 on 2014/6/18.
 */
public interface SystemUserDAO extends HeyCrudRepository<SystemUserDTO, String> {

    /**
     * 根据用户名查询
     * <br>------------------------------<br>
     *
     * @param userName
     * @return
     */
    SystemUserDTO findByUserName(String userName);

    /**
     * 添加百度账户
     *
     * @param list
     * @param currSystemUserName
     * @see {@link com.perfect.dao.account.SystemAccountDAO#insertModuleAccount(ModuleAccountInfoDTO)}
     * @deprecated
     */
    void addBaiduAccount(List<ModuleAccountInfoDTO> list, String currSystemUserName);

    SystemUserDTO findByAid(long aid);

    Iterable<SystemUserDTO> getAllValidUser();

    void insertAccountInfo(String userName, ModuleAccountInfoDTO moduleAccountInfoDTO);

    int removeAccountInfo(List<ModuleAccountInfoDTO> baiduAccountInfoDTOs, String account);

    void clearAccountData(Long accountId);

    void clearCampaignData(Long accountId, List<Long> campaignIds);

    void clearAdgroupData(Long accountId, List<Long> adgroupIds);

    void clearKeywordData(Long accountId, List<Long> keywordIds);

    void clearCreativeData(Long accountId, List<Long> creativeIds);

    List<Long> getLocalAdgroupIds(Long accountId, List<Long> campaignIds);

    List<Long> getLocalKeywordIds(Long accountId, List<Long> adgroupIds);

    List<Long> getLocalCreativeIds(Long accountId, List<Long> adgroupIds);

    List<SystemUserDTO> findAll(int skip, int limit, String order, boolean asc);

    boolean updateAccountStatus(String id, Integer accountStatus);

    boolean updateAccountTime(String id, Date startDate, Date endDate);

    boolean updateAccountPayed(String id, Boolean payed);

    List<SystemUserModuleDTO> getUserModules(String name);

    SystemUserDTO findByUserId(String id);

    boolean updateModuleMenus(String id, String moduleid, UserModuleMenuDTO menus);

    boolean saveUserModule(String userid, SystemUserModuleDTO systemUserModuleDTO);

    boolean deleteModule(String userid, String moduleId);

    @Deprecated
    SystemUserModuleDTO getUserModuleByModuleId(String id, String moduleId);

    boolean existsModule(String userid, String moduleName);

    SystemUserModuleDTO getUserModuleById(String userid, String id);

    boolean addModuleAccount(String id, String moduleid, ModuleAccountInfoDTO moduleAccountInfoDTO);

    SystemModuleDTO findSystemModuleByModuleName(String username, String moduleName);

    /**
     * <p>更新用户头像</p>
     *
     * @param is
     * @param fileName 系统用户名.png(.jpg)
     * @param metaData 需要提供系统用户的MongoDB ID
     */
    void updateUserImage(InputStream is, String fileName, Map<String, Object> metaData);

    InputStream findUserImage(String sysUserId);

    boolean updateAccountPassword(String userid, String password);

    boolean updateUserMenus(String userid, UserModuleMenuDTO userModuleMenuDTO);

    boolean updateModuleInfo(SystemUserModuleDTO systemUserModuleDTO, String id);

    boolean updateUserBaseInfo(String userid, SystemUserDTO systemUserDTO);

    void updateUserEmail(String userId, String email);

    String getUserEmail(String username);

    long listCount(String companyName, String userName, Boolean accountStatus);
}
