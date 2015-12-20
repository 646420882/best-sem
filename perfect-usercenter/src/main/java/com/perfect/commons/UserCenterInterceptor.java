package com.perfect.commons;

import com.perfect.commons.constants.UserConstants;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.usercenter.filters.UserInfoFilter;
import com.perfect.utils.RedisObtainedByToken;
import com.perfect.utils.redis.JRedisUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * Created by subdong on 15-12-16.
 */
public class UserCenterInterceptor implements HandlerInterceptor {

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
             * 不进行拦截url
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
                if (cookie.getName().equals(UserConstants.TOKEN_USER)) {
                    userToken = cookie.getValue().toString();
                }
            }

            //其他平台跳转到userCenter
            if (requestUrl.equals("/toUserCenter")) {
                try {
                    String token = request.getParameter(UserConstants.TOKEN_USER);
                    SystemUserDTO systemUserDTO = RedisObtainedByToken.getUserInfo(token);

                    if (Objects.isNull(systemUserDTO)) {
                        response.sendRedirect("/login");
                    }

                    Cookie cookie = new Cookie(UserConstants.TOKEN_USER, token);
                    cookie.setMaxAge(30 * 60 * 60);
                    response.addCookie(cookie);
                    request.getSession().setAttribute(UserConstants.SESSION_USER, systemUserDTO);
                }finally {
                    if(jedis != null){
                        jedis.close();
                    }
                }
                response.sendRedirect("/");
            }

            try {
                Object obj = request.getSession().getAttribute("user");
                if (obj != null && jedis.exists(userToken)) {
                    if (requestUrl.equals("/logout")) {
                        request.getSession().removeAttribute("user");
                        response.sendRedirect("/login");
                    }
                    return true;
                } else {
                    Cookie cookie = new Cookie(UserConstants.TOKEN_USER, null);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    response.sendRedirect("/login");
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
