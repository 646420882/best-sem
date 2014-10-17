package com.perfect.dao;

import com.perfect.entity.SystemUserEntity;

/**
 * Created by SubDong on 2014/9/30.
 */
public interface AccountRegisterDAO {
    /**
     * 注册系统用户
     *
     * @param systemUserEntity
     */
    public void addAccount(SystemUserEntity systemUserEntity);

    /**
     * 通过用户名查询用户
     *
     * @param
     */
    public SystemUserEntity getAccount(String userName);
}
