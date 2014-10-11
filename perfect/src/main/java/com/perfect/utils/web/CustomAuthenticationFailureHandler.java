package com.perfect.utils.web;

import com.perfect.app.homePage.service.CustomUserDetailsService;
import com.perfect.entity.MD5;
import com.perfect.redis.JRedisUtils;
import org.springframework.security.core.AuthenticationException;
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

    private static final String usernameNotFound = "org.springframework.security.core.userdetails.UsernameNotFoundException";
    private static final String badCredentials = "org.springframework.security.authentication.BadCredentialsException";

    public CustomAuthenticationFailureHandler(String defaultFailureUrl) {
        super(defaultFailureUrl);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        super.onAuthenticationFailure(request, response, exception);
        if (badCredentials.equals(exception.getClass().getName())) {
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
                    jedis.set(key, 1 + "");
                    CustomUserDetailsService.setPasswdBadCredentialsNum(1);
                } else {
                    Integer oldValue = Integer.valueOf(jedis.get(key));
                    if (oldValue < 3) {
                        Integer newValue = oldValue + 1;
                        jedis.set(key, newValue.toString());
                        CustomUserDetailsService.setPasswdBadCredentialsNum(newValue);
                    }
                }
                jedis.expire(key, 600);
            } finally {
                if (jedis != null) {
                    JRedisUtils.returnJedis(jedis);
                }
            }
        } else if (usernameNotFound.equals(exception.getClass().getName())) {
            //用户名没有找到
            CustomUserDetailsService.setUsernameNotFound(true);
        }
    }
}
