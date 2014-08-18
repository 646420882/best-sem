package com.perfect.utils.web;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by XiaoWei on 2014/8/18.
 */
public class SessionListener implements HttpSessionListener {
    private SessionContext sessionContext = SessionContext.getInstence();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        if (se != null) {
            sessionContext.setSession(se.getSession());
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        if (se != null) {
            sessionContext.delSession(se.getSession());
        }

    }
}
