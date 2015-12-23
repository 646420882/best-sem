package com.perfect.service;

import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.sys.*;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by yousheng on 2014/8/12.
 *
 * @author yousheng
 */
public interface SystemUserService {

    void initAccount(String uname, Long accountId);

    void updateAccountData(String userName, long accountId);

    boolean updateBaiDuName(String name, Long baiduId);

    void updateAccountData(String userName, long accountId, List<Long> camIds);

    void updateBaiduAccountInfo(String userName, Long accountId, ModuleAccountInfoDTO moduleAccountInfoDTO);

    List<CampaignDTO> getCampaign(String userName, long accountId);

    SystemUserDTO getSystemUser(String userName);

    SystemUserDTO getSystemUser(long aid);

    Iterable<SystemUserDTO> getAllUser();

    List<SystemUserDTO> getAllValidUser();

    void save(SystemUserDTO systemUserDTO);

    boolean removeAccount(Long id, String account);

//    void addAccount(String user, BaiduAccountInfoDTO baiduAccountInfoDTO);

    boolean updatePassword(String userName, String pwd);

    SystemUserDTO findByAid(long aid);

    void clearAccountData(Long accountId);

    void clearCampaignData(Long accountId, List<Long> campaignIds);

    void clearAdgroupData(Long accountId, List<Long> adgroupIds);

    void clearKeywordData(Long accountId, List<Long> keywordIds);

    void clearCreativeData(Long accountId, List<Long> creativeIds);

    List<Long> getLocalAdgroupIds(Long accountId, List<Long> campaignIds);

    List<Long> getLocalKeywordIds(Long accountId, List<Long> adgroupIds);

    List<Long> getLocalCreativeIds(Long accountId, List<Long> adgroupIds);

    SystemUserDTO findByUserName(String userName);

    Iterable<SystemUserDTO> findAll();

    List<SystemUserDTO> findUsers(String companyName, String userName, Boolean accountStatus, int skip, int
            limit, String order, boolean asc);

    boolean updateAccountStatus(String id, Integer accountStatus);

    boolean updateAccountTime(String id, Date startDate, Date endDate);

    boolean updateAccountPayed(String id, Boolean payed);

    List<SystemUserModuleDTO> getUserModules(String id);

    /**
     * @param id
     * @param modulename
     * @param menus
     * @return
     * @see SystemUserService.updateUserModuleMenus(String userid, UserModuleMenuDTO userModuleMenuDTO);
     */
    @Deprecated
    boolean updateUserModuleMenus(String id, String modulename, List<SystemMenuDTO> menus);

    boolean addModule(String userid, String moduleId);

    boolean deleteModule(String userid, String moduleId);

    List<SystemMenuDTO> getUserSubMenu(String id, String modulename);

    boolean addModuleAccount(String id, String moduleid, ModuleAccountInfoDTO moduleAccountInfoDTO);

    void updateUserImage(InputStream is, String fileSuffix);

    InputStream findUserImage(String sysUserName);

    boolean updateUserPassword(String userid, String password);

    boolean updateUserModuleMenus(String userid, UserModuleMenuDTO userModuleMenuDTO);

    SystemUserDTO findByUserId(String userid);

    boolean updateUserBaseInfo(String userid, SystemUserDTO systemUserDTO);
}
