package com.perfect.service;

import com.perfect.account.BaseBaiduAccountInfoVO;
import com.perfect.account.SystemUserInfoVO;
import com.perfect.commons.constants.AuthConstants;

import java.util.List;

/**
 * Created on 2015-12-07.
 *
 * @author dolphineor
 */
public interface SystemUserInfoService extends AuthConstants {

    /**
     * <p>获取全部系统用户
     *
     * @return
     */
    List<SystemUserInfoVO> findAllSystemUserAccount();

    /**
     * <p>根据系统用户名查询该用户的全部信息
     *
     * @param username 系统用户名
     * @return
     */
    SystemUserInfoVO findSystemUserInfoByUserName(String username);

    /**
     * <p>根据百度ID查询系统用户
     *
     * @param baiduUserId
     * @return
     */
    SystemUserInfoVO findSystemUserInfoByBaiduAccountId(Long baiduUserId);

    /**
     * <p>在当前登录用户中, 查询指定百度账号的基础信息{@link com.perfect.account.BaseBaiduAccountInfoVO}
     *
     * @param baiduUserId 百度账号ID
     * @return
     */
    BaseBaiduAccountInfoVO findByBaiduUserId(Long baiduUserId);

    /**
     * <p>根据系统用户名查询该用户下的所有百度账号信息
     *
     * @param username 系统用户名
     * @return
     */
    List<BaseBaiduAccountInfoVO> findBaiduAccountsByUserName(String username);

    /**
     * <p>查询系统中所有的百度账号
     *
     * @return
     */
    List<BaseBaiduAccountInfoVO> findAllBaiduAccounts();
}
