package com.perfect.dao.sys;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.dto.sys.SystemMenuDTO;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.dto.sys.SystemUserModuleDTO;

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
     */
    void addBaiduAccount(List<BaiduAccountInfoDTO> list, String currSystemUserName);

    SystemUserDTO findByAid(long aid);

    Iterable<SystemUserDTO> getAllValidUser();

    void insertAccountInfo(String userName, BaiduAccountInfoDTO baiduAccountInfoDTO);

    int removeAccountInfo(List<BaiduAccountInfoDTO> baiduAccountInfoDTOs, String account);

    void clearAccountData(Long accountId);

    void clearCampaignData(Long accountId, List<Long> campaignIds);

    void clearAdgroupData(Long accountId, List<Long> adgroupIds);

    void clearKeywordData(Long accountId, List<Long> keywordIds);

    void clearCreativeData(Long accountId, List<Long> creativeIds);

    List<Long> getLocalAdgroupIds(Long accountId, List<Long> campaignIds);

    List<Long> getLocalKeywordIds(Long accountId, List<Long> adgroupIds);

    List<Long> getLocalCreativeIds(Long accountId, List<Long> adgroupIds);

    List<SystemUserDTO> findAll(int skip, int limit, String order, boolean asc);

    boolean updateAccountStatus(String id, Boolean accountStatus);

    boolean updateAccountTime(String id, Date startDate, Date endDate);

    boolean updateAccountPayed(String id, Boolean payed);

    List<SystemUserModuleDTO> getUserModules(String name);

    SystemUserDTO findByUserId(String id);

    boolean updateModuleMenus(String id, String moduleid, List<SystemMenuDTO> menus);

    boolean saveUserModule(String userid, SystemUserModuleDTO systemUserModuleDTO);

    boolean deleteModule(String userid, String moduleId);

    SystemUserModuleDTO getUserModuleByModuleId(String id, String moduleid);

    boolean existsModule(String userid, String moduleId);

    SystemUserModuleDTO getUserModuleById(String userid, String id);

    boolean addModuleAccount(String id, String moduleid, ModuleAccountInfoDTO moduleAccountInfoDTO);

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
}
