package com.perfect.web.filter;

import com.alibaba.fastjson.JSON;
import com.perfect.commons.constants.AuthConstants;
import com.perfect.core.AppContext;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.dto.sys.SystemUserModuleDTO;
import com.perfect.utils.redis.JRedisUtils;
import com.perfect.web.suport.ServletContextUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import redis.clients.jedis.Jedis;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

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

        // 检测是否执行登出操作
        if (Objects.equals("/logout", request.getRequestURI())) {
            redirect(request, response, USER_LOGIN_URL);
        } else {
            // Token检测
            Object obj = request.getSession().getAttribute(USER_TOKEN);
            if (Objects.isNull(obj)) {
                // Session中没有token信息, 检测request中是否带有token信息
                String token = request.getParameter("tokenid");
                if (Objects.isNull(token) || token.isEmpty()) {
                    // 跳转至登录页面
                    redirectToLogin(request, response);
                } else {
                    // 根据token在Redis获取相应的用户信息
                    token = StringUtils.reverse(token);
                    SystemUserDTO systemUserDTO = retrieveUserInfoWithToken(token, request, response);
                    if (Objects.isNull(systemUserDTO)) {
                        redirectToLogin(request, response);
                    } else {
                        // 将token写入Session
                        request.getSession().setAttribute(USER_TOKEN, token);

                        // 检测当前登录用户是否有菜单访问权限
                        boolean hasMenus = checkSoukeMenus(systemUserDTO);
                        if (hasMenus) {
                            // 将用户信息写入Session
                            request.getSession().setAttribute(USER_INFO, systemUserDTO);

                            // 重定向至登录之前访问的页面
                            Object _url = request.getSession().getAttribute(USER_PRE_LOGIN_VISIT_URL);
                            response.setStatus(SC_FOUND);
                            response.sendRedirect(Objects.isNull(_url) ? "/" : _url.toString());
                        } else {
                            if (systemUserDTO.getAccess() == 1) {
                                // 搜客的管理员帐户
                                response.sendRedirect("/admin/index");
                            } else {
                                // 没有搜客的菜单访问权限, 跳转至百思平台页面
                                redirect(request, response, USER_CENTER_URL + "/toUserCenter?userToken=" + token);
                            }
                        }
                    }

                    // 清除Session中用户在未登录时访问的URL信息
                    request.getSession().setAttribute(USER_PRE_LOGIN_VISIT_URL, null);
                }
            }
        }

        filterChain.doFilter(request, response);
    }


    private void redirect(HttpServletRequest request, HttpServletResponse response, String url) {
        // 清除Session中的token信息
        request.getSession().setAttribute(USER_TOKEN, null);
        // 清除Session中的用户信息
        request.getSession().setAttribute(USER_INFO, null);

        response.setStatus(SC_FOUND);
        response.setHeader(LOCATION, url);
    }

    private void redirectToLogin(HttpServletRequest request, HttpServletResponse response) {
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
     * @return 系统用户信息
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
                request.getSession().setAttribute(USER_INFO, systemUserDTO);
                return systemUserDTO;
            }
        } finally {
            if (Objects.nonNull(jedis)) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * <p>检测搜客菜单权限信息
     *
     * @param systemUserDTO
     * @return
     */
    private boolean checkSoukeMenus(SystemUserDTO systemUserDTO) {
        boolean hasMenus = true;
        Optional<SystemUserModuleDTO> optional = systemUserDTO.getSystemUserModules()
                .stream()
                .filter(o -> Objects.equals(AppContext.getModuleName(), o.getModuleName()))
                .findFirst();

        if (optional.isPresent()) {
            if (Objects.isNull(optional.get().getMenus()) || optional.get().getMenus().isEmpty()) {
                hasMenus = false;
            }
        } else {
            hasMenus = false;
        }

        return hasMenus;
    }
}
