package com.perfect.app.web;

import com.perfect.core.AppContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

/**
 * Created by yousheng on 2014/7/28.
 *
 * @author yousheng
 */
public class UserInfoFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String userName = getUserName(httpServletRequest);
        if (userName != null) {
            AppContext.setUser(userName);
        }
//                if (userName != null) {
//                    SystemUserEntity systemUserEntity = systemUserService.getSystemUser(userName);
//                    for (BaiduAccountInfoEntity baiduAccountInfoEntity : systemUserEntity.getBaiduAccountInfoEntities()) {
//                        if (baiduAccountInfoEntity.isDfault()) {
//                            SessionObject so = new SessionObject();
//                            so.setUserName(userName);
//                            so.setAccountId(baiduAccountInfoEntity.getId());
//
//                            AppContext.setSessionObject(sessionId, so);
//                            break;
//                        }
//                    }
//                }

        chain.doFilter(request, response);
    }

    private String getUserName(HttpServletRequest httpServletRequest) {
        Principal userPrincipal = httpServletRequest.getUserPrincipal();
        return (userPrincipal != null ? userPrincipal.getName() : null);
    }

    @Override
    public void destroy() {
        return;
    }
}
