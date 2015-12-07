package com.perfect.core;

import com.perfect.account.BaseBaiduAccountInfoVO;

import java.util.List;

/**
 * Created by yousheng on 2014/8/23.
 *
 * @author yousheng
 */
public class SessionObject {

    private String username;

    private Long currentAccountId;

    private List<BaseBaiduAccountInfoVO> baiduAccounts;


    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public Long getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(Long currentAccountId) {
        this.currentAccountId = currentAccountId;
    }

    public List<BaseBaiduAccountInfoVO> getBaiduAccounts() {
        return baiduAccounts;
    }

    public void setBaiduAccounts(List<BaseBaiduAccountInfoVO> baiduAccounts) {
        this.baiduAccounts = baiduAccounts;
    }
}
