package com.perfect.account;

/**
 * Created on 2015-12-05.
 *
 * @author dolphineor
 */
public class BaseBaiduAccountInfoVO {

    // 凤巢账号ID
    private final Long accountId;

    // 凤巢账号名
    private final String accountName;

    // 凤巢账号备注名
    private final String remarkName;

    // 密码
    private final String password;

    // Token
    private final String token;

    // 是否默认显示
    private final boolean isDefault;


    public BaseBaiduAccountInfoVO(Long accountId, String accountName, String remarkName, String password, String token, boolean isDefault) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.remarkName = remarkName;
        this.password = password;
        this.token = token;
        this.isDefault = isDefault;
    }


    public Long getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getRemarkName() {
        return remarkName;
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
