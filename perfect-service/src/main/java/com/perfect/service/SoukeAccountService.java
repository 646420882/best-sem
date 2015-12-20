package com.perfect.service;

import com.perfect.dto.sys.ModuleAccountInfoDTO;

/**
 * Created on 2015-12-20.
 * <p>SEM帐号接口
 *
 * @author dolphineor
 */
public interface SoukeAccountService {

    // 搜客帐号的平台类型
    String BAIDU = "百度";
    String GOOGLE = "Google";
    String _360 = "360";

    /**
     * <p>绑定SEM帐号
     *
     * @param username            系统用户名
     * @param dto                 帐号内容
     * @param accountPlatformType 帐号所属平台
     */
    boolean bindAccountForSem(String username, ModuleAccountInfoDTO dto, String accountPlatformType);

    /**
     * <p>取消绑定SEM帐号
     *
     * @param username        系统用户名
     * @param moduleAccountId 帐号ID
     */
    void unbindAccountForSem(String username, Long moduleAccountId);

    /**
     * <p>重新激活SEM帐号
     *
     * @param username        系统用户名
     * @param moduleAccountId 帐号ID
     */
    void activeAccountForSem(String username, Long moduleAccountId);

    /**
     * <p>更新帐号信息
     *
     * @param username 系统用户名
     * @param dto      帐号内容
     */
    void updateAccountForSem(String username, ModuleAccountInfoDTO dto);

    /**
     * <p>删除帐号
     *
     * @param username        系统用户名
     * @param moduleAccountId 帐号ID
     */
    void deleteAccountForSem(String username, Long moduleAccountId);
}
