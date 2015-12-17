package com.perfect.dto.sys;

import java.util.List;

/**
 * Created by yousheng on 15/12/16.
 */
public class UserMenusDTO {
    private List<MenuDTO> menuDTOs;

    public List<MenuDTO> getMenuDTOs() {
        return menuDTOs;
    }

    public UserMenusDTO setMenuDTOs(List<MenuDTO> menuDTOs) {
        this.menuDTOs = menuDTOs;
        return this;
    }



}



