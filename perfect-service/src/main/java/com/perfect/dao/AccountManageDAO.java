package com.perfect.dao;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.perfect.entity.AccountReportEntity;
import com.perfect.entity.SystemUserEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by baizz on 2014-6-25.
 */
public interface AccountManageDAO<T> {

    /**
     * 获取账户树
     *
     * @return
     */
    ArrayNode getAccountTree();

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

    /**
     * 获取当前登陆的系统用户信息
     *
     * @return
     */
    SystemUserEntity getCurrUserInfo();

    /**
     * 上传用户头像
     *
     * @param bytes
     */
    void uploadImg(byte[] bytes);

    /**
     * 更新百度账户信息
     *
     * @param t
     */
    void updateBaiduAccountInfo(T t);

    /**
     * 根据当前登录的系统用户下指定的百度账号获取账户报告
     *
     * @param dates
     * @return
     */
    List<AccountReportEntity> getAccountReports(List<Date> dates);

    /**
     * 获取账户昨日消费
     *
     * @return
     */
    Double getYesterdayCost(Long accountId);

    /**
     * 获取账户的消费升降情况
     *
     * @return
     */
    Double getCostRate();
}