package com.perfect.dto.sys;

import com.google.common.collect.Lists;
import com.perfect.dto.BaseDTO;

import java.util.List;

/**
 * modulename 以及 moduleUrl属性只用在显示
 * 保存时不用设置
 * Created by yousheng on 15/12/15.
 */
public class SystemUserModuleDTO extends BaseDTO {

    @Deprecated
    private String moduleId;

    private String moduleName;

    private String moduleUrl;

    private boolean isPayed;

    private boolean enabled;

    private long startTime;

    private long endTime;

//    private List<SystemMenuDTO> menus;

//    private List<UserModuleMenuDTO> moduleMenus = new ArrayList<>();

    private List<String> menus = Lists.newArrayList();

    private List<ModuleAccountInfoDTO> accounts;


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


    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getModuleUrl() {
        return moduleUrl;
    }

    public void setModuleUrl(String moduleUrl) {
        this.moduleUrl = moduleUrl;
    }

    public List<ModuleAccountInfoDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<ModuleAccountInfoDTO> accounts) {
        this.accounts = accounts;
    }

    @Deprecated
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    @Deprecated
    public String getModuleId() {
        return moduleId;
    }

//    public List<SystemMenuDTO> getMenus() {
//        return menus;
//    }

//    public SystemUserModuleDTO setMenus(List<SystemMenuDTO> menus) {
//        this.menus = menus;
//        return this;
//    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    public List<String> getMenus() {
        return menus;
    }

    public SystemUserModuleDTO setMenus(List<String> menus) {
        this.menus = menus;
        return this;
    }

//    public List<UserModuleMenuDTO> getModuleMenus() {
//        return moduleMenus;
//    }
//
//    public void setModuleMenus(List<UserModuleMenuDTO> moduleMenus) {
//        this.moduleMenus = moduleMenus;
//    }
}
