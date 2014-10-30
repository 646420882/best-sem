package com.perfect.dto;

/**
 * Created by SubDong on 2014/10/30.
 */
public class BaiduAccountAllState {

    //百度帐号ID
    private Long id;
    //系统帐号
    private String userName;
    //系统帐号状态
    private Integer userState;
    //百度帐号
    private String baiduUserName;
    //百度帐号状态
    private Long baiduState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }

    public String getBaiduUserName() {
        return baiduUserName;
    }

    public void setBaiduUserName(String baiduUserName) {
        this.baiduUserName = baiduUserName;
    }

    public Long getBaiduState() {
        return baiduState;
    }

    public void setBaiduState(Long baiduState) {
        this.baiduState = baiduState;
    }
}
