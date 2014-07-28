package com.perfect.app.web;

import com.perfect.core.AppContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by yousheng on 2014/7/28.
 *
 * @author yousheng
 */
public class UserInfoFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null){
            AppContext.setUser(authentication.getName());
        }

        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        // do nothing
        return;
    }
}
