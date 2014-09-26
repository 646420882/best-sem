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

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHelper.context = applicationContext;
    }

    public static org.springframework.context.ApplicationContext getCurrentApplicationContext() {
        if (context == null)
            context = ContextLoader.getCurrentWebApplicationContext();

        return context;
    }


    public static Object getBeanByName(String name) {
        return context.getBean(name);
    }

    public static Object getBeanByClass(Class<?> clz) {
        return context.getBean(clz);
    }
}
