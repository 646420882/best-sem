package com.perfect.entity.sys;

import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * 系统菜单类
 * 模块下的各个菜单实体
 * Created by yousheng on 15/12/16.
 */
public class SystemSubMenuEntity {
    private String id;

    private String menuName;

    private String menuUrl;

    private int order;

    private String parentMenu;

    private int menuno;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getMenuno() {
        return menuno;
    }

    public void setMenuno(int menuno) {
        this.menuno = menuno;
    }
}
