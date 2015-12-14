package com.perfect.admin.filter;

import com.perfect.dto.admin.AdminUserDTO;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yousheng on 15/12/14.
 */
public class AdminPriviledgeHandler implements HandlerInterceptor {

    private final String SESSION_KEY = "USER_OBJ";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpServletRequest httpRequestServlet = (HttpServletRequest) request;
        Object obj = httpRequestServlet.getSession().getAttribute(SESSION_KEY);
        if (obj == null) {
            return true;
        }

        if (obj instanceof AdminUserDTO) {
            AdminUserDTO adminUserDTO = (AdminUserDTO) obj;

            if (adminUserDTO.isSuperAdmin()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
