package com.perfect.commons;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        if(requestUrl != null){
            /**
             * 登录页login.do不进行拦截
             */
            for(String url : allowUrls) {
                if(requestUrl.endsWith(url)) {
                    return true;
                }
            }

            Object obj = request.getSession().getAttribute("user");
            if(obj != null) {
                if(requestUrl.equals("/logout")){
                    request.getSession().removeAttribute("user");
                    response.sendRedirect("/");
                }
                return true;
            }else {
                Cookie cookies = new Cookie("semToken", null);
                cookies.setMaxAge(0);
                response.addCookie(cookies);
                response.sendRedirect("/");
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {}
}
