package com.perfect.service;

import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.baidu.BaiduAccountAllStateDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-8-21.
 * 2014-11-26 refactor
 */
public interface AccountManageService {

    /**
     * 修改密码
     *
     * @param password
     * @param newPwd
     * @return
     */
    public int updatePwd(String password, String newPwd);

    /**
     * 当前密码判断
     *
     * @param password
     * @return
     */
    public int JudgePwd(String password);

    /**
     * 得到所有未审核的帐号
     *
     * @return
     */
    public List<SystemUserDTO> getAccount();

    /**
     * 得到所有未审核的帐号
     *
     * @return
     */
    public int auditAccount(String userNmae);

    /**
     * 获得所有帐号信息
     *
     * @return
     */
    public List<BaiduAccountAllStateDTO> getAccountAll();

    /**
     * 修改百度帐号的启用状态
     *
     * @return
     */
    public int updateAccountAllState(String userName, Long baiduId, Long state);

    /**
     * 获取账户树
     *
     * @return
     */
    Map<String, Object> getAccountTree();

    /**
     * 获取当前登陆的系统用户信息
     *
     * @return
     */
    SystemUserDTO getCurrUserInfo();

    /**
     * 上传用户头像
     *
     * @param bytes
     */
    void uploadImg(byte[] bytes);

    /**
     * 获取当前登录的系统用户下的所有百度账号
     *
     * @return
     */
    Map<String, Object> getAllBaiduAccount(String currSystemUserName);

    /**
     * 获取所有系统用户的百度账号信息
     *
     * @return
     */
    List<BaiduAccountInfoDTO> getAllBaiduAccount();

    /**
     * 根据百度账户id获取其账户信息
     *
     * @param baiduUserId
     * @return
     */
    Map<String, Object> getBaiduAccountInfoByUserId(Long baiduUserId);

    /**
     * 根据百度账户id获取其账户信息
     *
     * @param baiduUserId
     * @return
     */
    BaiduAccountInfoDTO getBaiduAccountInfoById(Long baiduUserId);

    /**
     * 更新百度账户数据
     *
     * @param t
     */
    void updateBaiduAccount(BaiduAccountInfoDTO t);

    /**
     * 获取百度账户数据报告
     *
     * @param number
     * @return
     */
    Map<String, Object> getAccountReports(int number);

    /**
     * 获取账户昨日消费数据
     *
     * @param accountId
     * @return
     */
    Double getYesterdayCost(Long accountId);

    /**
     *  根据百度Id查询百度账号
     * @param baiduUserId
     * @return
     */
    BaiduAccountInfoDTO findByBaiduUserId(Long baiduUserId);

}