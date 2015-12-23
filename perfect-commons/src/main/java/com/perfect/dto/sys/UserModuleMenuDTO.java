package com.perfect.dto.sys;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单模块均采用当前类
 * Created by yousheng on 15/12/19.
 */
public class UserModuleMenuDTO {

    private String moduleName;

    private String moduleUrl;

    // 格式采用 "一级菜单名|二级菜单名"
    // 比如: "网站概览|今日概览"
    private List<String> menus = new ArrayList<>();

    public String getModuleName() {
        return moduleName;
    }

    public UserModuleMenuDTO setModuleName(String moduleName) {
        this.moduleName = moduleName;
        return this;
    }

    public String getModuleUrl() {
        return moduleUrl;
    }

    public UserModuleMenuDTO setModuleUrl(String moduleUrl) {
        this.moduleUrl = moduleUrl;
        return this;
    }

    public List<String> getMenus() {
        return menus;
    }

    public UserModuleMenuDTO setMenus(List<String> menus) {
        this.menus = menus;
        return this;
    }
}
