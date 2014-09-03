package com.perfect.service;

import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.SystemUserEntity;

import java.util.List;

/**
 * Created by yousheng on 2014/8/12.
 *
 * @author yousheng
 */
public interface SystemUserService {

    public SystemUserEntity getSystemUser(String userName);

    public SystemUserEntity getSystemUser(long aid);

    Iterable<SystemUserEntity> getAllUser();

    public void save(SystemUserEntity systemUserEntity);

    boolean removeAccount(Long id);

    void addAccount(String user, BaiduAccountInfoEntity baiduAccountInfoEntity);
}
