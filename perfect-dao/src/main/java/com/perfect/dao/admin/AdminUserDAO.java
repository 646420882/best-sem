package com.perfect.dao.admin;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.admin.AdminUserDTO;

/**
 * Created by yousheng on 15/12/15.
 */
public interface AdminUserDAO extends HeyCrudRepository<AdminUserDTO, String> {
    AdminUserDTO findByUserName(String username);
}
