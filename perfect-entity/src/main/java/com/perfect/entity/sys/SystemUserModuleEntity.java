package com.perfect.entity.sys;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * Created by yousheng on 15/12/15.
 */
public class SystemUserModuleEntity {

    private String moduleName;

    private String moduleUrl;

    private boolean isPayed;

    private boolean isOpened;

    private long startTime;

    private long endTime;

    @Field("menus")
    private List<String> systemMenuInfoEntityList;

    @Field("accounts")
    private List<ModuleAccountInfoEntity> accountInfoEntityList;

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

    public List<String> getSystemMenuInfoEntityList() {
        return systemMenuInfoEntityList;
    }

    public void setSystemMenuInfoEntityList(List<String> systemMenuInfoEntityList) {
        this.systemMenuInfoEntityList = systemMenuInfoEntityList;
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

    public void setIsPayed(boolean isPayed) {
        this.isPayed = isPayed;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setIsOpened(boolean isOpened) {
        this.isOpened = isOpened;
    }

    public String getModuleUrl() {
        return moduleUrl;
    }

    public void setModuleUrl(String moduleUrl) {
        this.moduleUrl = moduleUrl;
    }
}
