package com.perfect.dao.account;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.account.AccountReportDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.dto.sys.SystemUserDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by baizz on 2014-6-25.
 * 2014-12-2 refactor
 */
public interface AccountManageDAO extends HeyCrudRepository<SystemUserDTO, String> {

    /**
     * 获取帐号树
     *
     * @return
     */
    ArrayNode getAccountTree();

    /**
     * 获取百度帐户列表
     *
     * @return
     */
    List<ModuleAccountInfoDTO> getBaiduAccountItems(String currUserName);

    /**
     * 获取所有系统用户
     *
     * @return
     */
    List<SystemUserDTO> getAllSysUserAccount();

    /**
     * 根据百度帐号ID获取用户信息
     *
     * @param baiduUserId
     * @return
     */
    ModuleAccountInfoDTO findByBaiduUserId(Long baiduUserId);

    /**
     * 获取当前登陆的系统用户信息
     *
     * @return
     */
    SystemUserDTO getCurrUserInfo();

    /**
     * 修改密码
     *
     * @param pwd
     * @return
     */
    boolean updatePwd(String userName, String pwd);

    /**
     * 得到所有未审核的帐号
     *
     * @return
     */
    List<SystemUserDTO> getAccount();

    /**
     * 百度帐号启用/停用状态更改
     *
     * @param userName
     * @param moduleAccountId
     * @param status
     * @return
     */
    boolean updateBaiduAccountStatus(String userName, String moduleAccountId, Long status);

    /**
     * 更新百度账号备注名
     *
     * @param remarkName
     * @param moduleAccountName
     * @return
     */
    boolean updateBaiduRemarkName(String remarkName, String moduleAccountName);

    /**
     * 百度帐号启用/停用状态更改
     *
     * @return
     */
    boolean updateSysAccount(String userName, Long state);

    /**
     * 查询所有未帐号信息
     *
     * @return
     */
    List<SystemUserDTO> getAccountAll();

    /**
     * 审核帐号
     *
     * @param baiduAccount
     * @param baiduPassword
     * @param token
     * @return
     */
    /*public int auditAccount(String userNmae, String baiduAccount, String baiduPassword, String token);*/

    /**
     * 修改帐号状态
     *
     * @return
     */
    int updateAccountStruts(String userName);

    /**
     * 更新百度帐号信息
     *
     * @param t
     */
    void updateBaiduAccountInfo(BaiduAccountInfoDTO t);

    /**
     * 更新百度帐号信息
     *
     * @param userName
     * @param dto
     */
    void updateBaiduAccountInfo(String userName, ModuleAccountInfoDTO dto);

    /**
     * 更新百度帐号信息
     *
     * @param dto
     */
    void updateBaiduAccountBasicInfo(ModuleAccountInfoDTO dto);

    /**
     * 删除百度帐号
     *
     * @param username
     * @param moduleAccountId
     */
    boolean deleteBaiduAccount(String username, String moduleAccountId);

    /**
     * 根据当前登录的系统用户下指定的百度帐号获取帐户报告
     *
     * @param dates
     * @return
     */
    List<AccountReportDTO> getAccountReports(List<Date> dates);

    /**
     * 获取帐户昨日消费
     *
     * @return
     */
    Double getYesterdayCost(Long accountId);

    /**
     * 获取帐户的消费升降情况
     *
     * @return
     */
    Double getCostRate();
}