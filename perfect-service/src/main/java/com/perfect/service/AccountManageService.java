package com.perfect.service;

import java.util.Map;

/**
 * Created by baizz on 2014-8-21.
 */
public interface AccountManageService<T> {

    /**
     * 获取账户树
     *
     * @param t
     * @return
     */
    Map<String, Object> getAccountTree(T t);

    /**
     * 根据百度账户id获取其账户信息
     *
     * @param baiduUserId
     * @return
     */
    Map<String, Object> getBaiduAccountInfoByUserId(Long baiduUserId);

    /**
     * 更新百度账户数据
     *
     * @param t
     */
    void updateBaiduAccount(T t);

    /**
     * 获取百度账户数据报告
     *
     * @param number
     * @return
     */
    Map<String, Object> getAccountReports(int number);
}
