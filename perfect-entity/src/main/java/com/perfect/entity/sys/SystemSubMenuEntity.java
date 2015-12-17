package com.perfect.entity.sys;

/**
 * 系统菜单类
 * 模块下的各个菜单实体
 * Created by yousheng on 15/12/16.
 */
public class SystemSubMenuEntity {
    private String id;

    private String menuId;

    private String menuName;

    private String menuUrl;

    private int order;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public SystemSubMenuEntity setId(String id) {
        this.id = id;
        return this;
    }
}
