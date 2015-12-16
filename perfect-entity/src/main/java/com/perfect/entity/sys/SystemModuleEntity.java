package com.perfect.entity.sys;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * 系统模块实体类
 * 保存系统模块以及下属菜单
 * Created by yousheng on 15/12/15.
 */
@Document(collection = "sys_modules")
public class SystemModuleEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String moduleName;

    private int moduleno;

    private String moduleUrl;

    @Field("menus")
    private List<SystemMenuEntity> menus;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModuleUrl() {
        return moduleUrl;
    }

    public void setModuleUrl(String moduleUrl) {
        this.moduleUrl = moduleUrl;
    }

    public List<SystemMenuEntity> getMenus() {
        return menus;
    }

    public void setMenus(List<SystemMenuEntity> menus) {
        this.menus = menus;
    }

    public int getModuleno() {
        return moduleno;
    }

    public void setModuleno(int moduleno) {
        this.moduleno = moduleno;
    }
}
