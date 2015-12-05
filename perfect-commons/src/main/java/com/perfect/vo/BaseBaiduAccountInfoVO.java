package com.perfect.vo;

/**
 * Created on 2015-12-05.
 *
 * @author dolphineor
 */
public class BaseBaiduAccountInfoVO {

    private String accountName;

    private String password;

    private String token;

    private boolean isDefault;


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
