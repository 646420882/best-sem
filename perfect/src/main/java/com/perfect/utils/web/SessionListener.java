package com.perfect.utils.web;

import com.perfect.core.AppContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by XiaoWei on 2014/8/18.
 */
public class SessionListener implements HttpSessionListener {
    private SessionContext sessionContext = SessionContext.getInstence();

    @Override
    public void sessionCreated(HttpSessionEvent se) {

        Object ctx = se.getSession().getAttribute("SPRING_SECURITY_CONTEXT");

        System.out.println("ctx = " + ctx);
        System.out.println("Session created : id is " + se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        AppContext.remove(se.getSession().getId());
    }
}
