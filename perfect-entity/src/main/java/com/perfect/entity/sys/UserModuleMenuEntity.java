package com.perfect.entity.sys;

import java.util.List;

/**
 * Created by yousheng on 15/12/19.
 */
public class UserModuleMenuEntity {
    private String moduleName;

    private String moduleUrl;

    // 格式采用 "一级菜单名|二级菜单名"
    // 比如: "网站概览|今日概览"
    private List<String> menus;

    public String getModuleName() {
        return moduleName;
    }

    public UserModuleMenuEntity setModuleName(String moduleName) {
        this.moduleName = moduleName;
        return this;
    }

    public String getModuleUrl() {
        return moduleUrl;
    }

    public UserModuleMenuEntity setModuleUrl(String moduleUrl) {
        this.moduleUrl = moduleUrl;
        return this;
    }

    public List<String> getMenus() {
        return menus;
    }

    public UserModuleMenuEntity setMenus(List<String> menus) {
        this.menus = menus;
        return this;
    }
}
