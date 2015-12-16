package com.perfect.admin.filter;

import com.perfect.core.AppContext;
import com.perfect.core.SystemUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yousheng on 15/12/16.
 */
public class SystemLogHandler implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(SystemLogHandler.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (request.getUserPrincipal() == null) {
//            return false;
//        }
        SystemUserInfo systemUserInfo = new SystemUserInfo();
        systemUserInfo.setIp(request.getRemoteAddr());
        systemUserInfo.setUser("test");
        AppContext.setSystemUserInfo(systemUserInfo);
        if (logger.isDebugEnabled()) {
            logger.debug(systemUserInfo.toString());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
