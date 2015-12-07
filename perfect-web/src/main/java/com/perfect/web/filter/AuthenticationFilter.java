package com.perfect.web.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.perfect.account.BaseBaiduAccountInfoVO;
import com.perfect.account.SystemUserInfoVO;
import com.perfect.utils.http.HttpClientUtils;
import com.perfect.web.filter.auth.AuthConstants;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created on 2015-12-04.
 * <p>权限认证过滤, 登录成功后获取token并放入Cookie, 若Cookie中没有token则重定向至用户认证中心.
 *
 * @author dolphineor
 */
public class AuthenticationFilter extends OncePerRequestFilter implements AuthConstants {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
        if (Objects.isNull(servletContext.getAttribute(SERVLET_CONTEXT_INIT_VALUE))) {
            // ServletContext Initialize
            servletContext.setAttribute(SERVLET_CONTEXT_INIT_VALUE, 1);
        } else {
            // 检测是否执行登出操作
            if (Objects.equals("/logout", request.getRequestURI())) {
                // 重定向至用户认证页面
                response.setStatus(HttpServletResponse.SC_FOUND);
                response.setHeader("Location", USER_LOGINOUT_URL);
            } else {
                // 检测Cookie中是否带有token
                if (Optional.ofNullable(request.getCookies()).isPresent()) {
                    Optional<Cookie> cookieOptional = Arrays
                            .stream(request.getCookies())
                            .filter(c -> Objects.equals(TOKEN, c.getName()))
                            .findFirst();

                    if (!cookieOptional.isPresent()) {
                        // Cookie中没有token
                        // 检测request中是否带有token信息
                        retrieveTokenFromRequest(request, response);
                    }
                } else {
                    // 当前请求中没有任何Cookie信息
                    // 检测request中是否带有token信息
                    retrieveTokenFromRequest(request, response);
                }
            }
        }


        filterChain.doFilter(request, response);
    }


    /**
     * <p>从HTTP请求中获取token，
     * 1. 如果存在, 将token写入Cookie, 做token校验获取用户信息
     * 2. 如果不存在, 重定向至用户认证页面</p>
     *
     * @param request
     * @param response
     */
    private void retrieveTokenFromRequest(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getParameter("t");
        if (Objects.isNull(token)) {
            // 请求信息中也没有token
            // 重定向至用户认证页面
            response.setStatus(HttpServletResponse.SC_FOUND);
            response.setHeader("Location", USER_LOGIN_URL);
        } else {
            // 第一次登录认证
            // 将token写入Cookie
            Cookie tokenCookie = new Cookie(TOKEN, token);
            tokenCookie.setMaxAge(request.getSession().getMaxInactiveInterval());
            tokenCookie.setPath("/");
            response.addCookie(tokenCookie);

            // 获取用户信息
            Map<String, Object> params = Maps.newHashMap();
            params.put(TOKEN, token);
            try {
                String userInformation = HttpClientUtils.postRequest(USER_VERIFICATION_URL, params);
                if (Objects.nonNull(userInformation)) {
                    // 解析JSON数据并将用户信息写入Session
                    parse(userInformation, request, response);
                }
            } catch (IOException ignored) {

            }
        }
    }

    /**
     * 解析JSON数据, 需要提取的内容有:
     * 1. 用户名{@code UserName}
     * 2. 当前用户下的凤巢账号{@code baiduAccounts}
     * 3. 权限菜单
     *
     * @param message  认证中心返回的JSON数据
     * @param request
     * @param response
     */
    private void parse(String message, HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = JSON.parseObject(JSON.parseObject(message).getString("msg"));
        String username = jsonObject.getJSONObject("data").getString("userName");
        int accessStatus = jsonObject.getJSONObject("data").getInteger("access");
        JSONArray bdAccountArray = jsonObject.getJSONArray("baiduAccounts");

        if (Objects.isNull(bdAccountArray) || bdAccountArray.isEmpty()) {
            if (accessStatus == 1)
                response.sendRedirect("redirect:/backendManage/index");
            else
                throw new IllegalAccessError("Illegal access for " + username);
        } else {
            SystemUserInfoVO userInfo = new SystemUserInfoVO();
            userInfo.setUsername(username);

            List<BaseBaiduAccountInfoVO> baseBaiduAccountInfoVOs = new ArrayList<>();

            for (int i = 0, s = bdAccountArray.size(); i < s; i++) {
                baseBaiduAccountInfoVOs.add(new BaseBaiduAccountInfoVO(
                        bdAccountArray.getJSONObject(i).getString("bdfcName"),
                        bdAccountArray.getJSONObject(i).getString("bdrmName"),
                        bdAccountArray.getJSONObject(i).getString("bdfcPwd"),
                        bdAccountArray.getJSONObject(i).getString("bdToken"),
                        bdAccountArray.getJSONObject(i).getBoolean("bddefault")
                ));
            }
            userInfo.setBaiduAccounts(baseBaiduAccountInfoVOs);

            request.getSession().setAttribute(USER_INFORMATION, userInfo);
        }
    }
}
