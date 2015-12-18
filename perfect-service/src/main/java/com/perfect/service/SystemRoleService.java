package com.perfect.service;

import com.perfect.dto.sys.SystemRoleDTO;
import com.perfect.dto.sys.SystemUserDTO;

import java.util.List;

/**
 * Created by yousheng on 15/12/17.
 */
public interface SystemRoleService {
    List<SystemRoleDTO> list(String queryName, Boolean superUser, Integer page, Integer size, String sort, Boolean asc);

    boolean addSystemRole(SystemRoleDTO systemRoleDTO);

    boolean update(String roleid, SystemRoleDTO systemRoleDTO);

    SystemRoleDTO login(String username, String password);

    boolean deleteSystemRole(String roleid);

    SystemRoleDTO findByUserName(String user);

}
