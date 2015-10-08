/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 * work 2014-2-14
 */
package com.perfect.autosdk_v4.core;

/**
 * @title AccessToken
 * @description TODO 
 * @author wangbin
 * @date 2014-2-14
 * @version 1.0
 */
public class AccessToken {

    private String accessToken;

    //过期时间，单位s
    private int expiredIn;
    private String refreshToken;
    private String username;
    private String sessionKey;

    public AccessToken() {
    }

    /**
     * @param accessToken
     * @param expiredIn
     * @param refreshToken
     * @param username
     * @param sessionKey
     */
    public AccessToken(String accessToken, int expiredIn, String refreshToken, String username, String sessionKey) {
        super();
        this.accessToken = accessToken;
        this.expiredIn = expiredIn;
        this.refreshToken = refreshToken;
        this.username = username;
        this.sessionKey = sessionKey;
    }

    /**
     * @return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessToken the accessToken to set
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return the expiredIn
     */
    public long getExpiredIn() {
        return expiredIn;
    }

    /**
     * @param expiredIn the expiredIn to set
     */
    public void setExpiredIn(int expiredIn) {
        this.expiredIn = expiredIn;
    }

    /**
     * @return the refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * @param refreshToken the refreshToken to set
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the sessionKey
     */
    public String getSessionKey() {
        return sessionKey;
    }

    /**
     * @param sessionKey the sessionKey to set
     */
    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

}
