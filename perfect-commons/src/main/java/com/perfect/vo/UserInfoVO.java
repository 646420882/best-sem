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
}
