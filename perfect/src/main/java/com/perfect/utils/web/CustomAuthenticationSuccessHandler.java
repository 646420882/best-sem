package com.perfect.utils.web;

import com.perfect.commons.CustomUserDetailsService;
import com.perfect.entity.MD5;
import com.perfect.redis.JRedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Collection;

/**
 * Created by baizz on 2014-10-10.
 */
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    protected Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        String userName = CustomUserDetailsService.getUserName();
        MD5.Builder md5Builder = new MD5.Builder();
        MD5 md5 = md5Builder.password(userName).salt("passwd").build();
        String key = md5.getMD5();

        Jedis jedis = null;
        try {
            jedis = JRedisUtils.get();
            if (jedis.exists(key)) {
                Integer value = Integer.valueOf(jedis.get(key));
                if (value == 3) {
                    redirectStrategy.sendRedirect(request, response, "/login");
                    return;
                } else {
                    jedis.expire(key, 0);
                    CustomUserDetailsService.setPasswdBadCredentialsNum(0);
                }
            }
        } finally {
            if (jedis != null) {
                JRedisUtils.returnJedis(jedis);
            }
        }

        CustomUserDetailsService.setUsernameNotFound(false);

        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }


    protected String determineTargetUrl(Authentication authentication) {
        boolean isUser = false;
        boolean isAdmin = false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                isUser = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }
        }

        if (isUser) {
            return "/home";
        } else if (isAdmin) {
            return "/admin/index";
        } else {
            throw new IllegalStateException();
        }
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

}
