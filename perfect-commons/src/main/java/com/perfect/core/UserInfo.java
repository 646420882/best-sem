package com.perfect.core;

/**
 * Created by yousheng on 15/12/19.
 */
public class UserInfo {

    private String userId;

    private String userName;

    public String getUserId() {
        return userId;
    }

    public UserInfo setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserInfo setUserName(String userName) {
        this.userName = userName;
        return this;
    }
}
