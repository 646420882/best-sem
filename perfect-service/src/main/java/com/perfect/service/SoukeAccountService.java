package com.perfect.service;

import com.perfect.dto.sys.ModuleAccountInfoDTO;

import java.util.List;

/**
 * Created on 2015-12-20.
 * <p>搜客帐号接口
 *
 * @author dolphineor
 */
public interface SoukeAccountService {

    /**
     * <p>获取指定系统用户的所有搜客帐户
     *
     * @param username 系统用户名
     * @return
     */
    List<ModuleAccountInfoDTO> getSemAccounts(String username);

    /**
     * <p>获取搜客的使用期限
     *
     * @param username
     * @return
     */
    long[] getSemModuleServiceLife(String username);

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
     * @param username        系统用户名
     * @param moduleAccountId 帐号ID
     */
    void unbindAccountForSem(String username, String moduleAccountId);

    /**
     * <p>重新激活SEM帐号
     *
     * @param username        系统用户名
     * @param moduleAccountId 帐号ID
     */
    void activeAccountForSem(String username, String moduleAccountId);

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
     * @param moduleAccountId 帐号ID(Mongo ID)
     */
    void deleteAccountForSem(String moduleAccountId);
}
