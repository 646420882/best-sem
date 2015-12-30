package com.perfect.usercenter.filters;

import com.perfect.commons.ServletContextUtils;
import com.perfect.commons.constants.UserConstants;
import com.perfect.core.AppContext;
import com.perfect.core.UserInfo;
import com.perfect.dto.sys.SystemUserDTO;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TODO 核对用户中心需要过滤的url地址
 * Created by yousheng on 15/12/19.
 */
public class UserInfoFilter implements Filter {

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
            // 登陆 注册页面
            if (url.equals("/login") || url.equals("/loginaction")) {
                // 已经登陆直接跳转到根目录
                if (httpServletRequest.getSession().getAttribute(UserConstants.SESSION_USER) != null) {
                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                    httpServletResponse.sendRedirect("/");
                    return;
                }

                chain.doFilter(request, response);
                return;
                // 注销操作
            } else if (url.equals("/logout")) {
                httpServletRequest.getSession().setAttribute(UserConstants.SESSION_USER, null);
            } else if (url.equals("/register") || url.equals("/getPlatform") || url.equals("/userAdd") || url.equals("/toUserCenter") || url.equals("/forget") || url.equals("/reset") ){
                chain.doFilter(request, response);
                return;
            }

            if (httpServletRequest.getSession().getAttribute(UserConstants.SESSION_USER) == null) {
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.sendRedirect("/login");
                return;
            }

            SystemUserDTO systemUserDTO = (SystemUserDTO) httpServletRequest.getSession().getAttribute(UserConstants.SESSION_USER);

            UserInfo userInfo = new UserInfo();
//            systemUserInfo.setIp(request.getRemoteHost());
            userInfo.setUserName(systemUserDTO.getUserName());
            userInfo.setUserId(systemUserDTO.getId());
            AppContext.setUserInfo(userInfo);
        }

        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }
}
