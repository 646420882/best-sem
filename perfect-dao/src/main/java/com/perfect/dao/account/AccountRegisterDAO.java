package com.perfect.dao.account;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.sys.SystemUserDTO;

/**
 * Created by SubDong on 2014/9/30.
 */
public interface AccountRegisterDAO extends HeyCrudRepository<SystemUserDTO,Long> {
    /**
     * 注册系统用户
     *
     * @param systemUserEntity
     */
    public void addAccount(SystemUserDTO systemUserEntity);

    /**
     * 通过用户名查询用户
     *
     * @param
     */
    public SystemUserDTO getAccount(String userName);
}
