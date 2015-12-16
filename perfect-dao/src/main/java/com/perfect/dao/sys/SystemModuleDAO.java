package com.perfect.dao.sys;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.sys.SystemMenuDTO;
import com.perfect.dto.sys.SystemModuleDTO;

/**
 * Created by yousheng on 15/12/16.
 */
public interface SystemModuleDAO extends HeyCrudRepository<SystemModuleDTO, String> {
    SystemModuleDTO findByModuleName(String moduleName);

    SystemModuleDTO findByModuleId(String id);

    SystemModuleDTO updateMenus(SystemModuleDTO systemModuleDTO);

    boolean updateMenus(String moduleName, String menuname);

    boolean deleteMenu(String moduleName, String menuId);

    SystemModuleDTO findByModuleno(int moduleno);

    boolean insertMenu(String moduleId, SystemMenuDTO systemMenuDTO);

    boolean updateMenus(String moduleId, String menuid, String menuname, Integer order);

    boolean updateModule(SystemModuleDTO systemModuleDTO);
}
