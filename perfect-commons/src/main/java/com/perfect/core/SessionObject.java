package com.perfect.core;

import com.perfect.dto.sys.ModuleAccountInfoDTO;

import java.util.List;

/**
 * Created by yousheng on 2014/8/23.
 *
 * @author yousheng
 */
public class SessionObject {

    private String userName;

    private String moduleId;

    private long accountId;

    private List<ModuleAccountInfoDTO> moduleAccountInfoDTOs;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public List<ModuleAccountInfoDTO> getModuleAccountInfoDTOs() {
        return moduleAccountInfoDTOs;
    }

    public void setModuleAccountInfoDTOs(List<ModuleAccountInfoDTO> moduleAccountInfoDTOs) {
        this.moduleAccountInfoDTOs = moduleAccountInfoDTOs;
    }
}
