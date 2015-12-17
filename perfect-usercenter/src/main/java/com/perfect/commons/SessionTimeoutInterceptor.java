package com.perfect.commons;

import com.perfect.utils.redis.JRedisUtils;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * Created by subdong on 15-12-16.
 */
public class SessionTimeoutInterceptor implements HandlerInterceptor {

    private List<String> allowUrls;


    public List<String> getAllowUrls() {
        return allowUrls;
    }

    public void setAllowUrls(List<String> allowUrls) {
        this.allowUrls = allowUrls;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestUrl = request.getRequestURI();
        //对所有的请求,进行拦截
        if (requestUrl != null) {
            /**
             * 登录页login.do不进行拦截
             */
            for (String url : allowUrls) {
                if (requestUrl.endsWith(url)) {
                    return true;
                }
            }
            Jedis jedis = JRedisUtils.get();
            Cookie[] cookies = request.getCookies();
            String userToken = "";
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userToken")) {
                    userToken = cookie.getValue().toString();
                }
            }

            try {
                Object obj = request.getSession().getAttribute("user");
                if (obj != null && jedis.exists(userToken)) {
                    if (requestUrl.equals("/logout")) {
                        request.getSession().removeAttribute("user");
                        response.sendRedirect("/");
                    }
                    return true;
                } else {
                    Cookie cookie = new Cookie("userToken", null);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    response.sendRedirect("/");
                    return false;
                }
            } finally {
                if (jedis != null) {
                    JRedisUtils.returnJedis(jedis);
                }
            }
        } else {
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
