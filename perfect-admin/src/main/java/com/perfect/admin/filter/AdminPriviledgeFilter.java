package com.perfect.admin.filter;

import com.perfect.admin.commons.ServletContextUtils;
import com.perfect.core.AppContext;
import com.perfect.core.SystemUserInfo;
import com.perfect.dto.sys.SystemRoleDTO;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户session信息校验和重定向管理
 *
 * Created by yousheng on 15/12/14.
 */
public class AdminPriviledgeFilter implements Filter {

    public static final String SESSION_USER = "user";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            if (ServletContextUtils.checkStaticResourcesRequest(httpServletRequest)) {
                chain.doFilter(request, response);
                return;
            }


            String url = ((HttpServletRequest) request).getRequestURI();
            if (url.equals("/login") || url.equals("/loginaction")) {
                if (httpServletRequest.getSession().getAttribute(SESSION_USER) != null) {
                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                    httpServletResponse.sendRedirect("/");
                    return;
                }

                chain.doFilter(request, response);
                return;

            } else if (url.equals("/logout")) {
                httpServletRequest.getSession().setAttribute(SESSION_USER, null);
            }

            if (httpServletRequest.getSession().getAttribute(SESSION_USER) == null) {
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.sendRedirect("/login");
                return;
            }

            SystemRoleDTO systemRoleDTO = (SystemRoleDTO) httpServletRequest.getSession().getAttribute(SESSION_USER);

            SystemUserInfo systemUserInfo = new SystemUserInfo();
            systemUserInfo.setIp(request.getRemoteHost());
            systemUserInfo.setUser(systemRoleDTO.getLoginName());
            systemUserInfo.setIsSuper(systemRoleDTO.isSuperAdmin());
            AppContext.setSystemUserInfo(systemUserInfo);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
