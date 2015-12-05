package com.perfect.vo;

/**
 * Created on 2015-12-05.
 *
 * @author dolphineor
 */
public class BaseBaiduAccountInfoVO {

    private final String accountName;

    private final String password;

    private final String token;

    private final boolean isDefault;


    public BaseBaiduAccountInfoVO(String accountName, String password, String token, boolean isDefault) {
        this.accountName = accountName;
        this.password = password;
        this.token = token;
        this.isDefault = isDefault;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public boolean isDefault() {
        return isDefault;
    }
}
