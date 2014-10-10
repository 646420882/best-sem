package com.perfect.app.homePage.service;

import com.perfect.dao.SystemUserDAO;
import com.perfect.entity.MD5;
import com.perfect.entity.SystemUserEntity;
import com.perfect.redis.JRedisUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by baizz on 2014-6-23.
 */
public class CustomUserDetailsService implements UserDetailsService {

    private static String userName;

    @Resource(name = "systemUserDAO")
    private SystemUserDAO systemUserDAO;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetails user;
        SystemUserEntity systemUser = systemUserDAO.findByUserName(s);
        if (systemUser == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        userName = systemUser.getUserName();
        String key = new MD5(userName).getMD5();

        Jedis jedis = null;
        try {
            jedis = JRedisUtils.get();
            if (jedis.ttl(key) != -1) {
                if (Integer.valueOf(jedis.get(key)) == 3) {
                    throw new BadCredentialsException("badCredentials");
                }
            }
        } finally {
            if (jedis != null) {
                JRedisUtils.returnJedis(jedis);
            }
        }

        user = new User(
                systemUser.getUserName(),
                systemUser.getPassword().toLowerCase(),
                true, true, true, true,
                getAuthorities(systemUser.getAccess()));
        return user;
    }

    private Collection<GrantedAuthority> getAuthorities(Integer access) {
        List<GrantedAuthority> authList = new ArrayList<>(2);

        //所有的用户默认拥有ROLE_USER权限
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));

        //如果参数access为1, 则拥有ROLE_ADMIN权限
        if (access.compareTo(1) == 0)
            authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return authList;
    }

    public static String getUserName() {
        return userName;
    }
}
