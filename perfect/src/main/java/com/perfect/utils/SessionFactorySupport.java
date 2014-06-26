package com.perfect.utils;

/**
 * Created by baizz on 2014-6-10.
 */
public class SessionFactorySupport {
    private static org.hibernate.SessionFactory sessionFactory = null;

    private SessionFactorySupport() {
    }

    public synchronized static org.hibernate.SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = com.perfect.utils.ApplicationContext.getCurrentApplicationContext().getBean(org.hibernate.SessionFactory.class);
        }
        return sessionFactory;
    }
}
