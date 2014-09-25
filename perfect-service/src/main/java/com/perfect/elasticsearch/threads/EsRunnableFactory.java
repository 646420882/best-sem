package com.perfect.elasticsearch.threads;

import com.perfect.service.CreativeSourceService;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by vbzer_000 on 2014/9/16.
 */
public class EsRunnableFactory extends AbstractFactoryBean<EsRunnable> {

    @Override
    public Class<?> getObjectType() {
        return EsRunnable.class;
    }

    @Override
    protected EsRunnable createInstance() throws Exception {
        EsRunnable runnable = new EsRunnable();
        return runnable;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
