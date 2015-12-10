package com.perfect.web.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.perfect.account.BaseBaiduAccountInfoVO;
import com.perfect.account.SystemUserInfoVO;
import com.perfect.commons.constants.AuthConstants;
import com.perfect.utils.http.HttpClientUtils;
import com.perfect.web.support.ServletContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created on 2015-12-04.
 * <p>权限认证过滤, 登录成功后根据token获取用户信息并存入Session, 若Cookie中没有token则重定向至用户登录页面.
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
            logout(request, response);
        } else {
            // 如果Session中没有用户信息
            if (Objects.isNull(request.getSession().getAttribute(USER_INFORMATION))) {
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
     * <p>根据token向用户认证中心发送token校验以获取用户信息.
     *
     * @param token
     * @param request
     * @param response
     */
    private void retrieveUserInfoWithToken(String token, HttpServletRequest request, HttpServletResponse response) {
        // 获取用户信息
        Map<String, Object> params = Maps.newHashMap();
        params.put("token", token);
        try {
            String userInformation = HttpClientUtils.postRequest(USER_VERIFICATION_URL, params);
            if (Objects.nonNull(userInformation)) {
                parse(userInformation, request, response);
            }
        } catch (IOException ignored) {

        }
    }

    /**
     * <p>解析JSON数据, 需要提取的内容有:
     * 1. 用户名{@code userName}
     * 2. 当前用户下的凤巢帐号{@code baiduAccounts}
     * 3. 菜单权限</p>
     *
     * @param message  认证中心返回的JSON数据
     * @param request
     * @param response
     */
    private void parse(String message, HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean isSuccess = JSON.parseObject(JSON.parseObject(message).getString("msg")).getBooleanValue("success");
        if (!isSuccess) {
            redirectToLogin(response);
            return;
        }

        // 解析JSON数据并将用户信息写入Session
        JSONObject jsonObject = JSON.parseObject(JSON.parseObject(message).getString("msg")).getJSONObject("data");
        String username = jsonObject.getString("userName");
        String imageUrl = jsonObject.getString("img");
        int status = jsonObject.getIntValue("state");
        int accountStatus = jsonObject.getIntValue("accountState");
        int access = jsonObject.getIntValue("access");
        JSONArray bdAccountArr = jsonObject.getJSONArray("baiduAccounts");
        JSONArray menuPerssionArr = jsonObject.getJSONArray("bestMenu");

        if (Objects.isNull(bdAccountArr) || bdAccountArr.isEmpty()) {
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

            List<BaseBaiduAccountInfoVO> baseBaiduAccounts = new ArrayList<>();
            // 解析凤巢帐号信息
            for (int i = 0, s = bdAccountArr.size(); i < s; i++) {
                baseBaiduAccounts.add(new BaseBaiduAccountInfoVO(
                        bdAccountArr.getJSONObject(i).getLong("bdAccountID"),
                        bdAccountArr.getJSONObject(i).getString("bdfcName"),
                        bdAccountArr.getJSONObject(i).getString("defaultName"),
                        bdAccountArr.getJSONObject(i).getString("bdfcPwd"),
                        bdAccountArr.getJSONObject(i).getString("bdToken"),
                        bdAccountArr.getJSONObject(i).getBoolean("bddefault")
                ));
            }
            userInfo.setBaiduAccounts(baseBaiduAccounts);

            List<String> menuPerssions = new ArrayList<>();
            // 解析菜单权限信息
            for (int i = 0, s = menuPerssionArr.size(); i < s; i++) {
                JSONObject tmpJsonObj = menuPerssionArr.getJSONObject(i);
                menuPerssions.add(tmpJsonObj.getString("menuName") + "," + tmpJsonObj.getString("menuUrl"));
            }
            userInfo.setMenuPermissions(menuPerssions);

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

    /**
     * <p>登出操作
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Optional<Cookie> expiredCookieOptional = Arrays
                .stream(request.getCookies())
                .filter(c -> Objects.equals(TOKEN, c.getName()))
                .findFirst();

        if (expiredCookieOptional.isPresent()) {
            // 清除关于token的Cookie信息
            Cookie expiredCookie = new Cookie(TOKEN, expiredCookieOptional.get().getValue());
            expiredCookie.setMaxAge(0);
            expiredCookie.setPath("/");
            response.addCookie(expiredCookie);

            Map<String, Object> params = Maps.newHashMap();
            params.put("token", expiredCookieOptional.get().getValue());
            // 发送登出请求
            HttpClientUtils.postRequest(USER_LOGINOUT_URL, params);
        }

        // 跳转至登录页面
        redirectToLogin(response);
    }
}
