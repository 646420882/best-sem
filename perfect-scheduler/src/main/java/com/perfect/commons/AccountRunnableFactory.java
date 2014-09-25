package com.perfect.commons;

import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * Created by vbzer_000 on 2014/9/23.
 */

@Deprecated
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
