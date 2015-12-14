package com.perfect.dto.baidu;

/**
 * Created by SubDong on 2014/10/30.
 */
public class AccountAllStateDTO {

    //百度帐号ID
    private Long idObj;

    //系统帐号
    private String userName;

    //系统帐号审核状态
    private Integer userState;

    //百度帐号
    private String baiduUserName;

    //百度帐号状态
    private Long baiduState;

    //系统帐号禁用状态
    private Integer accountState;


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

    public Long getIdObj() {
        return idObj;
    }

    public void setIdObj(Long idObj) {
        this.idObj = idObj;
    }

    public Integer getAccountState() {
        return accountState;
    }

    public void setAccountState(Integer accountState) {
        this.accountState = accountState;
    }
}
