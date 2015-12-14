package com.perfect.service;

import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.admin.AdminUserDTO;

/**
 * Created by yousheng on 15/12/14.
 */
public interface AdminUserService {
    AdminUserDTO findByUserName(String username);
}
