package com.perfect.web.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.perfect.account.BaseBaiduAccountInfoVO;
import com.perfect.account.SystemUserInfoVO;
import com.perfect.commons.constants.AuthConstants;
import com.perfect.utils.http.HttpClientUtils;
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
 * <p>权限认证过滤, 登录成功后根据token获取用户信息并存入Session, 若Cookie中没有token则重定向至用户认证中心.
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

                    if (cookieOptional.isPresent()) {
                        retrieveUserInfoWithToken(cookieOptional.get().getValue(), request, response);
                    } else {
                        // Cookie中没有token
                        // 重定向至登录页面
                        redirectToLogin(response);
                    }
                } else {
                    // 当前请求中没有任何Cookie信息
                    // 重定向至登录页面
                    redirectToLogin(response);
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
     * @param token
     * @param request
     * @param response
     */
    private void retrieveUserInfoWithToken(String token, HttpServletRequest request, HttpServletResponse response) {
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

    /**
     * <p>解析JSON数据, 需要提取的内容有:
     * 1. 用户名{@code UserName}
     * 2. 当前用户下的凤巢账号{@code baiduAccounts}
     * 3. 权限菜单</p>
     *
     * @param message  认证中心返回的JSON数据
     * @param request
     * @param response
     */
    private void parse(String message, HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = JSON.parseObject(JSON.parseObject(message).getString("msg"));
        JSONObject sysUserJsonObj = jsonObject.getJSONObject("data");
        String username = sysUserJsonObj.getString("un");
        String imageUrl = sysUserJsonObj.getString("iu");
        int status = sysUserJsonObj.getInteger("st");
        int accountStatus = sysUserJsonObj.getInteger("ast");
        int access = sysUserJsonObj.getInteger("ae");
        JSONArray bdAccountArray = jsonObject.getJSONArray("bdAccounts");

        if (Objects.isNull(bdAccountArray) || bdAccountArray.isEmpty()) {
            if (access == 1)
                response.sendRedirect("redirect:/backendManage/index");
            else
                throw new IllegalAccessError("Illegal access for " + username);
        } else {
            SystemUserInfoVO userInfo = new SystemUserInfoVO();
            userInfo.setUsername(username);
            userInfo.setImageUrl(imageUrl);
            userInfo.setStatus(status);
            userInfo.setAccountStatus(accountStatus);
            userInfo.setAccess(access);

            List<BaseBaiduAccountInfoVO> baseBaiduAccountInfoVOs = new ArrayList<>();

            for (int i = 0, s = bdAccountArray.size(); i < s; i++) {
                baseBaiduAccountInfoVOs.add(new BaseBaiduAccountInfoVO(
                        bdAccountArray.getJSONObject(i).getLong("bdid"),
                        bdAccountArray.getJSONObject(i).getString("bdan"),
                        bdAccountArray.getJSONObject(i).getString("bdrn"),
                        bdAccountArray.getJSONObject(i).getString("bdpw"),
                        bdAccountArray.getJSONObject(i).getString("bdt"),
                        bdAccountArray.getJSONObject(i).getBoolean("isdft")
                ));
            }
            userInfo.setBaiduAccounts(baseBaiduAccountInfoVOs);

            request.getSession().setAttribute(USER_INFORMATION, userInfo);
        }
    }

    /**
     * <p>重定向至登录页面
     *
     * @param response
     */
    private void redirectToLogin(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_FOUND);
        response.setHeader("Location", USER_LOGIN_URL);
    }
}
