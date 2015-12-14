package com.perfect.admin.commons;

import com.perfect.dto.admin.AdminUserDTO;
import com.perfect.service.AdminUserService;
import com.perfect.utils.MD5;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by yousheng on 15/12/14.
 */
@Component("adminUserDetailsService")
public class AdminUserDetailsService implements UserDetailsService {


    private final AdminUserDTO DEFAULT = new AdminUserDTO();

    {
        DEFAULT.setName("默认超级管理员");
        DEFAULT.setLoginName("ROOT");
        MD5 md5 = new MD5.Builder().password("perfectadmin").salt("123wqewq2134").build();
        DEFAULT.setPassword(md5.getMD5());
        DEFAULT.setSuperAdmin(true);
    }

    @Resource
    private AdminUserService adminUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails user;
        AdminUserDTO adminUserDTO = null;
        if (username.equals(DEFAULT.getLoginName())) {
            adminUserDTO = DEFAULT;
        } else {
            adminUserDTO = adminUserService.findByUserName(username);
        }

        if (adminUserDTO == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        user = new User(
                adminUserDTO.getLoginName(),
                adminUserDTO.getPassword().toLowerCase(),
                true, true, true, true,
                getAuthorities(adminUserDTO.isSuperAdmin()));
        return user;
    }


    private Collection<GrantedAuthority> getAuthorities(boolean superadmin) {
        List<GrantedAuthority> authList = new ArrayList<>(2);

        //所有的用户默认拥有ROLE_USER权限
        authList.add(new ComparableGrantedAuthority("ROLE_USER"));

        //如果参数access为1, 则拥有ROLE_ADMIN权限
        if (superadmin) {
            authList.add(new ComparableGrantedAuthority("ROLE_ADMIN"));
        }

        return authList;
    }
}
