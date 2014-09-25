package com.perfect.bidding.core;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Component;

/**
 * Created by vbzer_000 on 2014/9/23.
 */

@Component
public class AccountRunnableFactory extends AbstractFactoryBean<AccountRunnable> {
    @Override
    public Class<?> getObjectType() {
        return AccountRunnable.class;
    }

    @Override
    protected AccountRunnable createInstance() throws Exception {

        return null;
    }
}
