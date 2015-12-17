package com.perfect.service.impl;

import com.google.common.collect.Lists;
import com.perfect.dao.sys.SystemLogDAO;
import com.perfect.dao.sys.SystemModuleDAO;
import com.perfect.dto.sys.SystemMenuDTO;
import com.perfect.dto.sys.SystemModuleDTO;
import com.perfect.service.SystemModuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yousheng on 15/12/16.
 */
@Service
public class SystemModuleServiceImpl implements SystemModuleService {

    @Resource
    private SystemLogDAO systemLogDAO;

    @Resource
    private SystemModuleDAO systemModuleDAO;

    @Override
    public List<SystemModuleDTO> list() {
        return Lists.newArrayList(systemModuleDAO.findAll());
    }

    @Override
    public SystemModuleDTO findByName(String moduleName) {
        return systemModuleDAO.findByModuleName(moduleName);
    }

    @Override
    public boolean updateMenu(String moduleName, String menuname) {
        boolean success = systemModuleDAO.updateMenus(moduleName, menuname);
        if (success) {
            systemLogDAO.log("更新模块 " + moduleName + " 下的菜单名称 " + menuname);
        }
        return success;
    }

    @Override
    public boolean deleteMenu(String moduleName, String menuname) {


        boolean success = systemModuleDAO.deleteMenu(moduleName, menuname);
        if (success) {
            systemLogDAO.log("删除模块 " + moduleName + " 下的菜单 " + menuname);
        }
        return success;
    }

    @Override
    public SystemModuleDTO createModule(String modulename, String moduleurl) {
        SystemModuleDTO systemModuleDTO = new SystemModuleDTO();
        systemModuleDTO.setModuleName(modulename);
        systemModuleDTO.setModuleUrl(moduleurl);
        systemModuleDAO.save(systemModuleDTO);

        systemLogDAO.log("创建模块 " + modulename);
        return systemModuleDTO;
    }

    @Override
    public SystemModuleDTO createModule(SystemModuleDTO systemModuleDTO) {

        systemLogDAO.log("创建模块 " + systemModuleDTO.getModuleName());

        return systemModuleDAO.save(systemModuleDTO);
    }

    @Override
    public SystemModuleDTO findByModuleNo(int moduleno) {
        return systemModuleDAO.findByModuleno(moduleno);
    }

    @Override
    public boolean insertMenu(String moduleId, SystemMenuDTO systemMenuDTO) {
        boolean exits = systemModuleDAO.existsMenu(moduleId, systemMenuDTO.getMenuName());


        if (exits) {
            return false;
        }

        boolean success = systemModuleDAO.insertMenu(moduleId, systemMenuDTO);

        if (success) {
            SystemModuleDTO systemModuleDTO = systemModuleDAO.findByModuleId(moduleId);
            if (systemModuleDTO != null) {
                systemLogDAO.log("创建模块 " + systemModuleDTO.getModuleName() + " 下的菜单 " + systemMenuDTO.getMenuName());
            }
        }

        return success;
    }

    @Override
    public SystemModuleDTO findByModuleId(String moduleid) {
        return systemModuleDAO.findByModuleId(moduleid);
    }

    @Override
    public boolean updateMenu(String moduleId, String menuid, String menuname, Integer order, String menuUrl) {
        return systemModuleDAO.updateMenus(moduleId, menuid, menuname, order, menuUrl);
    }

    @Override
    public boolean updateModule(SystemModuleDTO systemModuleDTO) {

        SystemModuleDTO oldDto = systemModuleDAO.findByModuleId(systemModuleDTO.getId());

        if (oldDto == null) {
            return false;
        }

        boolean success = systemModuleDAO.updateModule(systemModuleDTO);

        if (success) {
            if (!systemModuleDTO.getModuleName().equals(oldDto.getModuleName())) {
                systemLogDAO.log("更新模块名称:" + oldDto.getModuleName() + " -> " + systemModuleDTO.getModuleName());
            }

            if (!systemModuleDTO.getModuleUrl().equals(oldDto.getModuleUrl())) {
                systemLogDAO.log("更新模块名称:" + oldDto.getModuleUrl() + " -> " + systemModuleDTO.getModuleUrl());
            }
        }
        return success;
    }

    @Override
    public boolean deleteModule(String moduleId) {
        SystemModuleDTO systemModuleDTO = systemModuleDAO.findByModuleId(moduleId);
        if (systemModuleDTO == null) {
            return false;
        }

        boolean success = systemModuleDAO.delete(moduleId);
        if (success) {
            systemLogDAO.log("删除模块 " + systemModuleDTO.getModuleName());
        }
        return success;
    }
}
