package com.perfect.entity.sys;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * Created by yousheng on 15/12/15.
 */
public class SystemUserModuleEntity {

    private String id;

    private String moduleId;    //模块id
//    private String moduleName;
//
//    private String moduleUrl;

    private boolean isPayed;    //是否购买当前模块   true 购买  flase未购买

    private boolean enabled;    //是否启用模块 true启用  flase 不启用

    private long startTime;     //模块使用期限  开始时间

    private long endTime;       //模块使用期限  结束时间

    @Field("menus")
    private List<SystemMenuEntity> menus;

    @Field("accounts")
    private List<ModuleAccountInfoEntity> accounts;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public List<SystemMenuEntity> getMenus() {
        return menus;
    }

    public void setMenus(List<SystemMenuEntity> menus) {
        this.menus = menus;
    }

//    public String getModuleName() {
//        return moduleName;
//    }
//
//    public void setModuleName(String moduleName) {
//        this.moduleName = moduleName;
//    }

    public boolean isPayed() {
        return isPayed;
    }

    public void setIsPayed(boolean isPayed) {
        this.isPayed = isPayed;
    }


//    public String getModuleUrl() {
//        return moduleUrl;
//    }
//
//    public void setModuleUrl(String moduleUrl) {
//        this.moduleUrl = moduleUrl;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public SystemUserModuleEntity setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public String getModuleId() {
        return moduleId;
    }

    public SystemUserModuleEntity setModuleId(String moduleId) {
        this.moduleId = moduleId;
        return this;
    }

    public List<ModuleAccountInfoEntity> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<ModuleAccountInfoEntity> accounts) {
        this.accounts = accounts;
    }
}
