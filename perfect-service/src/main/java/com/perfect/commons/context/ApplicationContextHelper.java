package com.perfect.commons.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

/**
 * Created by vbzer_000 on 2014/9/17.
 */
@Component
public class ApplicationContextHelper implements ApplicationContextAware {

    private static volatile ApplicationContext ctx;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHelper.ctx = applicationContext;
    }

    public static org.springframework.context.ApplicationContext getCurrentApplicationContext() {
        if (ctx == null) {
            synchronized (ApplicationContextHelper.class) {
                if (ctx == null)
                    ctx = ContextLoader.getCurrentWebApplicationContext();
            }
        }

        return ctx;
    }


    public static Object getBeanByName(String name) {
        return getCurrentApplicationContext().getBean(name);
    }

    public static <T> T getBeanByClass(Class<T> clz) {
        return getCurrentApplicationContext().getBean(clz);
    }

    public static <T> T getBean(String name, Class<T> clz) {
        return getCurrentApplicationContext().getBean(name, clz);
    }
}
