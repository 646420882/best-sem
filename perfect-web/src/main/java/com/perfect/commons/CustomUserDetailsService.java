package com.perfect.commons;

import com.perfect.dto.SystemUserDTO;
import com.perfect.service.SystemUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by baizz on 2014-6-23.
 */
public class CustomUserDetailsService implements UserDetailsService {

    private static String userName;

    private static boolean usernameNotFound = false;

    private static boolean verifyNotPass = false;

    private static boolean forbidden = false;

    private static int passwdBadCredentialsNum = 0;

    private static boolean hasBaiduAccount = false;

    @Resource
    private SystemUserService systemUserService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetails user;
        SystemUserDTO systemUser = systemUserService.findByUserName(s);
        if (systemUser == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        if (systemUser.getState() == 0) {
            verifyNotPass = true;
            throw new UsernameNotFoundException("Username not found");
        }
        if (systemUser.getAccountState() == 0) {
            forbidden = true;
            throw new UsernameNotFoundException("Username not found");
        }
        userName = systemUser.getUserName();

        hasBaiduAccount = systemUser.getBaiduAccounts().size() > 0;

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
        authList.add(new ComparableGrantedAuthority("ROLE_USER"));

        //如果参数access为1, 则拥有ROLE_ADMIN权限
        if (access.compareTo(1) == 0)
            authList.add(new ComparableGrantedAuthority("ROLE_ADMIN"));

        return authList;
    }


    public static String getUserName() {
        return userName;
    }

    public static boolean isUsernameNotFound() {
        return usernameNotFound;
    }

    public static void setUsernameNotFound(boolean usernameNotFound) {
        CustomUserDetailsService.usernameNotFound = usernameNotFound;
    }

    public static boolean isVerifyNotPass() {
        return verifyNotPass;
    }

    public static void setVerifyNotPass(boolean verifyNotPass) {
        CustomUserDetailsService.verifyNotPass = verifyNotPass;
    }

    public static int getPasswdBadCredentialsNum() {
        return passwdBadCredentialsNum;
    }

    public static void setPasswdBadCredentialsNum(int passwdBadCredentialsNum) {
        CustomUserDetailsService.passwdBadCredentialsNum = passwdBadCredentialsNum;
    }

    public static boolean isForbidden() {
        return forbidden;
    }

    public static void setForbidden(boolean forbidden) {
        CustomUserDetailsService.forbidden = forbidden;
    }

    public static boolean hasBaiduAccount() {
        return hasBaiduAccount;
    }
}
