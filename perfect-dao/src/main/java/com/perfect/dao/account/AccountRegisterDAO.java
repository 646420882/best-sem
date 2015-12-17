package com.perfect.dao.account;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.param.RegisterParam;

/**
 * Created by SubDong on 2014/9/30.
 */
public interface AccountRegisterDAO extends HeyCrudRepository<SystemUserDTO,Long> {
    /**
     * 注册系统用户
     *
     * @param systemUserDTO
     */
    void addAccount(SystemUserDTO systemUserDTO);

    /**
     * 新版注册系统用户
     *
     * @param systemUserDTO
     */
    void regAccount(SystemUserDTO systemUserDTO);

    /**
     * 通过用户名查询用户
     *
     * @param
     */
    SystemUserDTO getAccount(String userName);
}
