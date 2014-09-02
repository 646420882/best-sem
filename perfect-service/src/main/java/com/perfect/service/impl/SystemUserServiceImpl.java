package com.perfect.service.impl;

import com.perfect.dao.SystemUserDAO;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.SystemUserEntity;
import com.perfect.service.SystemUserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by yousheng on 2014/8/12.
 *
 * @author yousheng
 */
@Component
public class SystemUserServiceImpl implements SystemUserService {

    @Resource
    private SystemUserDAO systemUserDAO;

    @Override
    public SystemUserEntity getSystemUser(String userName) {
        return systemUserDAO.findByUserName(userName);
    }

    @Override
    public SystemUserEntity getSystemUser(long aid) {
        return systemUserDAO.findByAid(aid);
    }

    @Override
    public Iterable<SystemUserEntity> getAllUser() {
        return systemUserDAO.findAll();
    }

    @Override
    public void save(SystemUserEntity systemUserEntity) {
        systemUserDAO.save(systemUserEntity);
    }

    @Override
    public boolean removeAccount(Long id) {
        systemUserDAO.removeAccountInfo(id);
        return false;
    }

    @Override
    public void addAccount(String user, BaiduAccountInfoEntity baiduAccountInfoEntity) {
        systemUserDAO.insertAccountInfo(user, baiduAccountInfoEntity);
    }
}
