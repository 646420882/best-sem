package com.perfect.vo;

import java.util.List;

/**
 * Created on 2015-12-05.
 *
 * @author dolphineor
 */
public class UserInfoVO {

    // 系统用户名
    private String username;

    // 帐号审核状态: 1 -> 审核通过, 0 -> 审核未通过
    private int status;

    // 帐号可用状态: 1 -> 启用, 0 -> 禁用
    private int accountStatus;

    private List<BaseBaiduAccountInfoVO> baiduAccounts;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<BaseBaiduAccountInfoVO> getBaiduAccounts() {
        return baiduAccounts;
    }

    public void setBaiduAccounts(List<BaseBaiduAccountInfoVO> baiduAccounts) {
        this.baiduAccounts = baiduAccounts;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(int accountStatus) {
        this.accountStatus = accountStatus;
    }
}
