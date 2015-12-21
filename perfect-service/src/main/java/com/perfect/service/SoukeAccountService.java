package com.perfect.service;

import com.perfect.dto.sys.ModuleAccountInfoDTO;

/**
 * Created on 2015-12-20.
 * <p>搜客帐号接口
 *
 * @author dolphineor
 */
public interface SoukeAccountService {

    /**
     * <p>绑定SEM帐号
     *
     * @param username 系统用户名
     * @param dto      帐号内容
     */
    boolean bindAccountForSem(String username, ModuleAccountInfoDTO dto);

    /**
     * <p>取消绑定SEM帐号
     *
     * @param username          系统用户名
     * @param moduleAccountName 帐号名称
     */
    void unbindAccountForSem(String username, String moduleAccountName);

    /**
     * <p>重新激活SEM帐号
     *
     * @param username          系统用户名
     * @param moduleAccountName 帐号名称
     */
    void activeAccountForSem(String username, String moduleAccountName);

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
     * @param username          系统用户名
     * @param moduleAccountName 帐号名称
     */
    void deleteAccountForSem(String username, String moduleAccountName);
}
