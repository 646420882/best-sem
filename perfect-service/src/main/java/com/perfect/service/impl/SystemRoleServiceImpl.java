package com.perfect.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.perfect.dao.sys.SystemRoleDAO;
import com.perfect.dto.sys.SystemRoleDTO;
import com.perfect.service.SystemRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by yousheng on 15/12/17.
 */
@Service
public class SystemRoleServiceImpl implements SystemRoleService {

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
        systemRoleDAO.save(systemRoleDTO);
    }

    @Override
    public boolean update(String roleid, SystemRoleDTO systemRoleDTO) {
        return systemRoleDAO.update(roleid, systemRoleDTO);
    }
}
