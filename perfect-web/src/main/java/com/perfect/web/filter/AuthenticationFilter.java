package com.perfect.web.filter;

import com.alibaba.fastjson.JSON;
import com.perfect.commons.constants.AuthConstants;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.utils.redis.JRedisUtils;
import com.perfect.web.suport.ServletContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import redis.clients.jedis.Jedis;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static javax.servlet.http.HttpServletResponse.SC_FOUND;

/**
 * Created on 2015-12-17.
 *
 * @author dolphineor
 */
public class AuthenticationFilter extends OncePerRequestFilter implements AuthConstants {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 过滤静态资源
        boolean isStaticResourcesRequest = ServletContextUtils.checkStaticResourcesRequest(request);
        if (isStaticResourcesRequest) {
            filterChain.doFilter(request, response);
            return;
        }
        if(request.getRequestURI().equals("/admin/index")){
            filterChain.doFilter(request, response);
            return;
        }

        // 检测是否执行登出操作
        if (Objects.equals("/logout", request.getRequestURI())) {
            logout(request, response);
        } else {
            // Token检测
            Object obj = request.getSession().getAttribute(USER_TOKEN);
            if (Objects.isNull(obj)) {
                // Session中没有token信息, 检测request中是否带有token信息
                String token = request.getParameter("tokenid");
                if (Objects.isNull(token) || token.isEmpty()) {
                    // 跳转至登录页面
                    redirectForLogin(request, response);
                } else {
                    // 将token并写入Session
                    request.getSession().setAttribute(USER_TOKEN, token);

                    // 根据token在Redis获取相应的用户信息
                    SystemUserDTO systemUserDTO = retrieveUserInfoWithToken(token, request, response);
                    if (Objects.isNull(systemUserDTO)) {
                        redirectForLogin(request, response);
                    } else {
                        request.getSession().setAttribute(USER_INFORMATION, systemUserDTO);
                    }

                    // 重定向至登录之前访问的页面
                    Object _url = request.getSession().getAttribute(USER_PRE_LOGIN_VISIT_URL);
                    response.setStatus(SC_FOUND);
                    response.sendRedirect(Objects.isNull(_url) ? "/" : _url.toString());

                    // 清除Session中用户在未登录时访问的URL信息
                    request.getSession().setAttribute(USER_PRE_LOGIN_VISIT_URL, null);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * <p>登出操作
     *
     * @param request
     * @param response
     */
    private void logout(HttpServletRequest request, HttpServletResponse response) {
        // 清除Session中的token信息
        request.getSession().setAttribute(USER_TOKEN, null);
        // 清除Session中的用户信息
        request.getSession().setAttribute(USER_INFORMATION, null);

        response.setStatus(SC_FOUND);
        response.setHeader(LOCATION, USER_LOGIN_URL);
    }

    /**
     * <p>登录重定向
     *
     * @param request
     * @param response
     */
    private void redirectForLogin(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(SC_FOUND);
        response.setHeader(LOCATION, String.format(USER_LOGIN_REDIRECT_URL, request.getHeader(HOST)));
        if (Objects.isNull(request.getSession().getAttribute(USER_PRE_LOGIN_VISIT_URL))) {
            request.getSession().setAttribute(USER_PRE_LOGIN_VISIT_URL, request.getRequestURI());
        }
    }

    /**
     * <p>根据token从Redis中获取用户信息
     *
     * @param token
     * @param request
     * @param response
     * @return
     */
    private SystemUserDTO retrieveUserInfoWithToken(String token, HttpServletRequest request, HttpServletResponse response) {
        Jedis jedis = null;

        try {
            jedis = JRedisUtils.get();
            String userInfoMsg = jedis.get(token);
            if (Objects.isNull(userInfoMsg)) {
                response.setStatus(SC_FOUND);
                response.setHeader(LOCATION, USER_LOGIN_URL);
                return null;
            }

            SystemUserDTO systemUserDTO = JSON.parseObject(userInfoMsg, SystemUserDTO.class);
            if (Objects.nonNull(systemUserDTO)) {
                request.getSession().setAttribute(USER_INFORMATION, systemUserDTO);
                return systemUserDTO;
            }
        } finally {
            if (Objects.nonNull(jedis)) {
                jedis.close();
            }
        }

        return null;
    }
}
