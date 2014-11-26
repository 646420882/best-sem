package com.perfect.service.impl;

import com.perfect.dao.SystemUserDAO;
import com.perfect.dao.account.AccountManageDAO;
import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
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
    @Resource
    private AccountManageDAO accountManageDAO;

    @Override
    public SystemUserDTO getSystemUser(String userName) {
        return systemUserDAO.findByUserName(userName);
    }

    @Override
    public SystemUserDTO getSystemUser(long aid) {
        return systemUserDAO.findByAid(aid);
    }

    @Override
    public Iterable<SystemUserDTO> getAllUser() {
        return systemUserDAO.findAll();
    }

    @Override
    public void save(SystemUserDTO systemUserDTO) {
        systemUserDAO.save(systemUserDTO);
    }

    @Override
    public boolean removeAccount(Long id) {
        systemUserDAO.removeAccountInfo(id);
        return false;
    }

    @Override
    public void addAccount(String user, BaiduAccountInfoDTO baiduAccountInfoDTO) {
        systemUserDAO.insertAccountInfo(user, baiduAccountInfoDTO);
    }

    @Override
    public boolean updatePassword(String userName, String pwd) {
        return accountManageDAO.updatePwd(userName, pwd).isUpdateOfExisting();
    }
}
