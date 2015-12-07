package com.perfect.account;

import java.util.List;

/**
 * Created on 2015-12-05.
 *
 * @author dolphineor
 */
public class SystemUserInfoVO {

    // 系统用户名
    private String username;

    // 用户头像URL
    private String imageUrl;

    // 帐号审核状态: 1 -> 审核通过, 0 -> 审核未通过
    private int status;

    // 帐号可用状态: 1 -> 启用, 0 -> 禁用
    private int accountStatus;

    // 用户权限: 1 -> 管理员, 2 -> 普通用户
    private int access;

    private List<BaseBaiduAccountInfoVO> baiduAccounts;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public List<BaseBaiduAccountInfoVO> getBaiduAccounts() {
        return baiduAccounts;
    }

    public void setBaiduAccounts(List<BaseBaiduAccountInfoVO> baiduAccounts) {
        this.baiduAccounts = baiduAccounts;
    }
}
