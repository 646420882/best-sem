package com.perfect.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.perfect.commons.constants.PasswordSalts;
import com.perfect.core.AppContext;
import com.perfect.core.SystemRoleInfo;
import com.perfect.dao.sys.SystemLogDAO;
import com.perfect.dao.sys.SystemRoleDAO;
import com.perfect.dto.sys.SystemRoleDTO;
import com.perfect.service.SystemRoleService;
import com.perfect.utils.MD5;
import com.perfect.utils.paging.BootStrapPagerInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by yousheng on 15/12/17.
 */
@Service
public class SystemRoleServiceImpl implements SystemRoleService {

    private final String pass_salt = PasswordSalts.ADMIN_SALT;

    @Resource
    private SystemRoleDAO systemRoleDAO;

    @Resource
    private SystemLogDAO systemLogDAO;

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
    public boolean addSystemRole(SystemRoleDTO systemRoleDTO) {
        systemRoleDTO.setPassword(new MD5.Builder().source(systemRoleDTO.getPassword()).salt(pass_salt).build().getMD5());
        systemRoleDAO.save(systemRoleDTO);

        SystemRoleDTO newSystemRoleDTO = systemRoleDAO.findByUserLoginName(systemRoleDTO.getLoginName());
        if (newSystemRoleDTO != null) {
            if (systemRoleDTO.isSuperAdmin()) {
                systemLogDAO.log("新增超级系统管理员:" + systemRoleDTO.getName() + ", 登陆名:" + systemRoleDTO.getLoginName());
            } else {
                systemLogDAO.log("新增系统管理员:" + systemRoleDTO.getName() + ", 登陆名:" + systemRoleDTO.getLoginName());
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean update(String roleid, SystemRoleDTO systemRoleDTO) {

        if (!Strings.isNullOrEmpty(systemRoleDTO.getPassword())) {
            systemRoleDTO.setPassword(new MD5.Builder().source(systemRoleDTO.getPassword()).salt(pass_salt).build().getMD5());
        }

        boolean updated = systemRoleDAO.update(roleid, systemRoleDTO);

        if (updated) {
            systemLogDAO.log("更新管理员信息: " + systemRoleDTO.getName());
        }

        return updated;
    }

    @Override
    public SystemRoleDTO login(String username, String password) {
        SystemRoleDTO loginUser = systemRoleDAO.findByNameAndPasswd(username, new MD5.Builder().source(password).salt(pass_salt).build().getMD5());
        if (loginUser != null) {

            SystemRoleInfo systemRoleInfo = new SystemRoleInfo();
            systemRoleInfo.setRoleName(loginUser.getLoginName());
            systemRoleInfo.setIsSuper(loginUser.isSuperAdmin());
            AppContext.setSystemUserInfo(systemRoleInfo);

            systemLogDAO.log("管理员登陆: " + loginUser.getLoginName());
        }
        return loginUser;
    }

    @Override
    public boolean deleteSystemRole(String roleid) {
        SystemRoleDTO systemRoleDTO = systemRoleDAO.findById(roleid);

        if (systemRoleDTO == null) {
            return false;
        }

        boolean deleted = systemRoleDAO.delete(roleid);
        if (deleted) {
            systemLogDAO.log("删除管理员: " + systemRoleDTO.getLoginName());
        }

        return deleted;
    }

    @Override
    public SystemRoleDTO findByUserName(String user) {
        return systemRoleDAO.findByUserLoginName(user);
    }

    @Override
    public BootStrapPagerInfo listPagable(String queryName, Boolean superUser, Integer page, Integer size, String sort, Boolean asc) {
        List<SystemRoleDTO> systemUserDTOs = list(queryName, superUser, page, size, sort, asc);

        long totalCount = listCount(queryName, superUser);

        BootStrapPagerInfo bootStrapPagerInfo = new BootStrapPagerInfo();
        bootStrapPagerInfo.setTotal(totalCount);
        bootStrapPagerInfo.setRows(systemUserDTOs);
        return bootStrapPagerInfo;
    }

    @Override
    public boolean updateRolePassword(String roleid, String password) {
        boolean success = systemRoleDAO.updateUserPassword(roleid, new MD5.Builder().source(password).salt(pass_salt).build().getMD5());

        if (success) {
            SystemRoleDTO systemRoleDTO = systemRoleDAO.findById(roleid);
            systemLogDAO.log("更新管理员" + systemRoleDTO.getLoginName() + "密码");
        }

        return success;

    }

    private long listCount(String queryName, Boolean superUser) {
        return systemRoleDAO.countByQuery(queryName, superUser);
    }
}
