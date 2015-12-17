package com.perfect.dto.sys;

import java.util.List;

/**
 * Created by yousheng on 15/12/16.
 */
public class MenuDTO {
    private String menuId;


    private List<String> subMenus;

    public List<String> getSubMenus() {
        return subMenus;
    }

    public MenuDTO setSubMenus(List<String> subMenus) {
        this.subMenus = subMenus;
        return this;
    }

    public String getMenuId() {
        return menuId;
    }

    public MenuDTO setMenuId(String menuId) {
        this.menuId = menuId;
        return this;
    }
}
