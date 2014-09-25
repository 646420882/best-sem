package com.perfect.bidding.core;

import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * Created by vbzer_000 on 2014/9/23.
 */

public class AccountRunnableFactory extends AbstractFactoryBean<AccountWorker> {
    @Override
    public Class<?> getObjectType() {
        return AccountWorker.class;
    }

    @Override
    protected AccountWorker createInstance() throws Exception {

        return null;
    }
}
