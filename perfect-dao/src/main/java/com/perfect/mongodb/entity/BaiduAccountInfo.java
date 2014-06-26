package com.perfect.mongodb.entity;

/**
 * Created by vbzer_000 on 2014/6/18.
 */
public class BaiduAccountInfo {

    private Long id;

    private String baiduUserName;

    private String baiduPassword;

    private String token;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBaiduUserName() {
        return baiduUserName;
    }

    public void setBaiduUserName(String baiduUserName) {
        this.baiduUserName = baiduUserName;
    }

    public String getBaiduPassword() {
        return baiduPassword;
    }

    public void setBaiduPassword(String baiduPassword) {
        this.baiduPassword = baiduPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
