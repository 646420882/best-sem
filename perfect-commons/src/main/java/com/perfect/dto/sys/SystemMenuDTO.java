package com.perfect.dto.sys;

import com.perfect.dto.BaseDTO;

import java.util.List;

/**
 * 暂停使用
 * Created by yousheng on 15/12/16.
 */
@Deprecated
public class SystemMenuDTO extends BaseDTO {

    private String menuId;

    private String menuName;

    private String menuUrl;

    private int order;

    private List<SystemMenuDTO> subMenus;

    public List<SystemMenuDTO> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<SystemMenuDTO> subMenus) {
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

    public String getMenuId() {
        return menuId;
    }

    public SystemMenuDTO setMenuId(String menuId) {
        this.menuId = menuId;
        return this;
    }
}
