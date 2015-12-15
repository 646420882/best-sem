package com.perfect.service.impl;

import com.perfect.dao.admin.AdminUserDAO;
import com.perfect.dto.admin.AdminUserDTO;
import com.perfect.service.AdminUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yousheng on 15/12/15.
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {


    @Resource
    private AdminUserDAO adminUserDAO;
    @Override
    public AdminUserDTO findByUserName(String username) {
        return adminUserDAO.findByUserName(username);
    }
}
