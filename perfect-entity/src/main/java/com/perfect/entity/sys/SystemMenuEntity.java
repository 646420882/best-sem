package com.perfect.entity.sys;

import java.util.List;

/**
 * 系统菜单类
 * 模块下的各个菜单实体
 * Created by yousheng on 15/12/16.
 */
public class SystemMenuEntity {

    private String id;

    // 系统菜单外键ID
    private String menuId;

    private String menuName;

    private String menuUrl;

    private int order;

    private List<SystemSubMenuEntity> subMenus;

    public List<SystemSubMenuEntity> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<SystemSubMenuEntity> subMenus) {
        this.subMenus = subMenus;
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

    public SystemMenuEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getMenuId() {
        return menuId;
    }

    public SystemMenuEntity setMenuId(String menuId) {
        this.menuId = menuId;
        return this;
    }
}
