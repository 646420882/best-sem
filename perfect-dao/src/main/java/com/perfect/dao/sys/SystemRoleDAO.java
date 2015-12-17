package com.perfect.dao.sys;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.sys.SystemRoleDTO;

import java.util.List;

/**
 * Created by yousheng on 15/12/17.
 */
public interface SystemRoleDAO extends HeyCrudRepository<SystemRoleDTO, String> {


    List<SystemRoleDTO> find(String name, Boolean superAdmin, int page, int size, String sort, boolean asc);


    boolean update(String roleid, SystemRoleDTO systemRoleDTO);

    SystemRoleDTO findByNameAndPasswd(String username, String password);
}
