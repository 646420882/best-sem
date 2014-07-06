package com.perfect.app.accountCenter.dao;

import net.sf.json.JSONArray;

import java.util.List;

/**
 * Created by baizz on 2014-6-25.
 */
public interface AccountManageDAO<T> {

    /**
     * 获取账户树
     *
     * @param t
     * @return
     */
    JSONArray getAccountTree(List<T> t);

    /**
     * 获取新增的百度账户信息
     *
     * @param username
     * @param password
     * @param token
     * @return
     */
    List<T> getBaiduAccountInfos(String username, String password, String token);

    /**
     * 获取百度账户列表
     *
     * @return
     */
    List<T> getBaiduAccountItems(String currUserName);

    /**
     * 根据百度账户ID获取用户信息
     *
     * @param baiduUserId
     * @return
     */
    T findByBaiduUserId(Long baiduUserId);


    void updateAccountData(Long baiduUserId);
}
