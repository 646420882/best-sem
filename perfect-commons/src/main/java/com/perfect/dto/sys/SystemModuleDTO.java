package com.perfect.dto.sys;

import com.perfect.dto.BaseDTO;

import java.util.List;

/**
 * Created by yousheng on 15/12/16.
 */
public class SystemModuleDTO extends BaseDTO {

    private String moduleName;

    private String moduleUrl;

    private List<SystemMenuDTO> menus;
    private int moduleno;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleUrl() {
        return moduleUrl;
    }

    public void setModuleUrl(String moduleUrl) {
        this.moduleUrl = moduleUrl;
    }

    public List<SystemMenuDTO> getMenus() {
        return menus;
    }

    public void setMenus(List<SystemMenuDTO> menus) {
        this.menus = menus;
    }

    public int getModuleno() {
        return moduleno;
    }

    public void setModuleno(int moduleno) {
        this.moduleno = moduleno;
    }
}
