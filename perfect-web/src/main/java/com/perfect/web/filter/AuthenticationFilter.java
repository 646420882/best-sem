package com.perfect.web.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.perfect.api.baidu.BaiduApiService;
import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.autosdk.sms.v3.AccountInfoType;
import com.perfect.core.AppContext;
import com.perfect.web.auth.AuthConstants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
            response.setHeader("Location", AUTHENTICATION_URL);
        } else {
            // 第一次登录认证
            // 将token写入Cookie
            Cookie tokenCookie = new Cookie(TOKEN, token);
            tokenCookie.setMaxAge(request.getSession().getMaxInactiveInterval() * 2);
            tokenCookie.setPath("/");
            response.addCookie(tokenCookie);

            // 获取用户信息
            try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                HttpPost httpPost = new HttpPost(USER_VERIFICATION_URL);

                List<NameValuePair> params = Lists.<NameValuePair>newArrayList(new BasicNameValuePair(TOKEN, token));
                httpPost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));
                CloseableHttpResponse response1 = httpClient.execute(httpPost);

                if (response1.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity entity = response1.getEntity();
                    String userInformation = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                    // 解析JSON数据并将用户信息写入Session
                    parse(userInformation, request, response);
//                    request.getSession().setAttribute(USER_INFORMATION, userInformation);
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
    private void parse(String message, HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = JSON.parseObject(JSON.parseObject(message).getString("msg"));
        String username = jsonObject.getJSONObject("data").getString("userName");
        JSONArray bdAccountArray = jsonObject.getJSONArray("baiduAccounts");
        AppContext.setUser(username);

        for (int i = 0, s = bdAccountArray.size(); i < s; i++) {
            if (bdAccountArray.getJSONObject(i).getBoolean("bddefault")) {
                // 提取默认显示的凤巢账号
                String bdAccountName = bdAccountArray.getJSONObject(i).getString("bdfcName");
                String passwd = bdAccountArray.getJSONObject(i).getString("bdfcPwd");
                String bdToken = bdAccountArray.getJSONObject(i).getString("bdToken");

                BaiduApiService apiService = new BaiduApiService(BaiduServiceSupport.getCommonService(bdAccountName, passwd, bdToken));
                AccountInfoType accountInfoType = apiService.getAccountInfo();

                break;
            }
        }

        // TODO 调用百度API获取凤巢账号的基础信息
    }
}
