package com.perfect.dto.sys;

import com.perfect.dto.BaseDTO;

import java.util.List;

/**
 * Created by yousheng on 15/12/16.
 */
public class SystemMenuDTO extends BaseDTO {
    private int menuno;

    private String menuName;

    private String menuUrl;

    private int order;

    private List<SystemMenuDTO> subMenus;

    private String parentMenu;


    public List<SystemMenuDTO> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<SystemMenuDTO> subMenus) {
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

    public int getMenuno() {
        return menuno;
    }

    public void setMenuno(int menuno) {
        this.menuno = menuno;
    }
}
