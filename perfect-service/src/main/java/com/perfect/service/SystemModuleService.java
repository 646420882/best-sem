package com.perfect.service;

import com.perfect.dto.sys.SystemMenuDTO;
import com.perfect.dto.sys.SystemModuleDTO;

import java.util.List;

/**
 * Created by yousheng on 15/12/16.
 */
public interface SystemModuleService {

    List<SystemModuleDTO> list();

    SystemModuleDTO findByName(String moduleName);

    boolean updateMenu(String moduleName, String menuname);

    boolean deleteMenu(String moduleName, String menuname);

    public SystemModuleDTO createModule(String modulename, String moduleurl);

    SystemModuleDTO createModule(SystemModuleDTO systemModuleDTO);

    SystemModuleDTO findByModuleNo(int moduleno);

    boolean insertMenu(String moduleid, SystemMenuDTO systemMenuDTO);

    SystemModuleDTO findByModuleId(String moduleid);

    boolean updateMenu(String moduleId, String menuid, String menuname, Integer order);

    boolean updateModule(SystemModuleDTO systemModuleDTO);

    boolean deleteModule(String moduleId);
}
