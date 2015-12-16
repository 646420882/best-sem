package com.perfect.entity.sys;

import java.util.List;

/**
 * 系统菜单类
 * 模块下的各个菜单实体
 * Created by yousheng on 15/12/16.
 */
public class SystemMenuEntity {
    private String id;

    private String menuName;

    private String menuUrl;

    private int order;

    private List<SystemSubMenuEntity> subMenus;

    private String parentMenu;

    public List<SystemSubMenuEntity> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<SystemSubMenuEntity> subMenus) {
        this.subMenus = subMenus;
    }

    public String getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(String parentMenu) {
        this.parentMenu = parentMenu;
    }

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

//    public int getMenuno() {
//        return menuno;
//    }
//
//    public void setMenuno(int menuno) {
//        this.menuno = menuno;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
