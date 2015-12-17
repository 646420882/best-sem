package com.perfect.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.perfect.dao.sys.SystemRoleDAO;
import com.perfect.dto.sys.SystemRoleDTO;
import com.perfect.service.SystemRoleService;
import com.perfect.utils.MD5;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by yousheng on 15/12/17.
 */
@Service
public class SystemRoleServiceImpl implements SystemRoleService {

    private final String pass_salt = "admin_password";
    @Resource
    private SystemRoleDAO systemRoleDAO;

    @Override
    public List<SystemRoleDTO> list(String queryName, Boolean superUser, Integer page, Integer size, String sort, Boolean asc) {
        Map<String, Object> paramMap = Maps.newHashMap();
        if (!Strings.isNullOrEmpty(queryName)) {
            paramMap.put("name", queryName);
        }

        if (superUser != null) {
            paramMap.put("superAdmin", superUser);
        }

        return systemRoleDAO.find(queryName, superUser, page, size, sort, asc);
    }

    @Override
    public void addSystemRole(SystemRoleDTO systemRoleDTO) {
        systemRoleDTO.setPassword(new MD5.Builder().password(systemRoleDTO.getPassword()).salt(pass_salt).build().getMD5());
        systemRoleDAO.save(systemRoleDTO);
    }

    @Override
    public boolean update(String roleid, SystemRoleDTO systemRoleDTO) {
        return systemRoleDAO.update(roleid, systemRoleDTO);
    }

    @Override
    public SystemRoleDTO login(String username, String password) {
        return systemRoleDAO.findByNameAndPasswd(username, new MD5.Builder().password(password).salt(pass_salt).build().getMD5());
    }

    @Override
    public boolean deleteSystemRole(String roleid) {
        return systemRoleDAO.delete(roleid);
    }
}
