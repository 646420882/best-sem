package com.perfect.commons;

import com.perfect.utils.MD5;
import com.perfect.utils.redis.JRedisUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by baizz on 2014-10-11.
 */
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public CustomAuthenticationFailureHandler(String defaultFailureUrl) {
        super(defaultFailureUrl);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String redirect = request.getParameter("redirect");
        if (redirect != null && !redirect.isEmpty()) {
            getRedirectStrategy().sendRedirect(request, response, "/login?error=true&url=" + redirect);
            return;
        }
        super.onAuthenticationFailure(request, response, exception);
        if (exception instanceof BadCredentialsException) {
            //密码验证失败
            CustomUserDetailsService.setUsernameNotFound(false);
            String userName = CustomUserDetailsService.getUserName();
            MD5.Builder md5Builder = new MD5.Builder();
            MD5 md5 = md5Builder.password(userName).salt("passwd").build();
            String key = md5.getMD5();

            Jedis jedis = null;
            try {
                jedis = JRedisUtils.get();
                if (!jedis.exists(key)) {
                    jedis.incr(key);
                    CustomUserDetailsService.setPasswdBadCredentialsNum(1);
                    jedis.expire(key, 10800);
                } else {
                    Integer oldValue = Integer.valueOf(jedis.get(key));
                    if (oldValue < 3) {
                        Integer newValue = oldValue + 1;
                        jedis.incr(key);
                        CustomUserDetailsService.setPasswdBadCredentialsNum(newValue);
                        jedis.expire(key, 10800);
                    }
                    if (oldValue == 3) {
                        CustomUserDetailsService.setPasswdBadCredentialsNum(3);
                    }
                }
            } finally {
                if (jedis != null) {
                    JRedisUtils.returnJedis(jedis);
                }
            }
        } else if (exception instanceof UsernameNotFoundException) {
            //用户名没有找到
            CustomUserDetailsService.setUsernameNotFound(true);
        }
    }
}
