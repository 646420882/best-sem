package com.perfect.web.support;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created on 2014-09-01.
 *
 * @author dolphineor
 */
public class ServletContextUtils {

    public static HttpServletRequest getRequest() {
        RequestAttributes ra = RequestContextHolder.currentRequestAttributes();
        return ((ServletRequestAttributes) ra).getRequest();
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static ServletContext getServletContext() {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext();
    }
}
