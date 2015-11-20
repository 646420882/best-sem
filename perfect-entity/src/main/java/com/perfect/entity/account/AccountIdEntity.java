package com.perfect.entity.account;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by yousheng on 2014/8/13.
 *
 * @author yousheng
 */
public class AccountIdEntity {

    @Field("acid")
    private long accountId; // 百度账户ID


    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
