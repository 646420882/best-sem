package com.perfect.db.mongodb.proxy;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by vbzer_000 on 2014/6/26.
 */
public class MongoProxy<T> implements InvocationHandler, ApplicationContextAware {

    private final Class<T> interfaceClass;
    private ApplicationContext applicationContext;

    public MongoProxy(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public static <T> T createProxy(Class<T> interfaceClass) {
        MongoProxy<T> proxy = new MongoProxy<T>(interfaceClass);
        return (T) Proxy.newProxyInstance(MongoProxy.class.getClassLoader(), new Class<?>[]{interfaceClass}, proxy);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String daoName = MongoCollectionUtils.getDaoName(proxy.getClass());
        Object bean = applicationContext.getBean(daoName);

        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
