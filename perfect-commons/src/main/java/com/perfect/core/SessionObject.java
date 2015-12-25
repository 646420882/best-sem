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

    private Long accountId;

    private List<ModuleAccountInfoDTO> moduleAccountInfoDTOs;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public List<ModuleAccountInfoDTO> getModuleAccountInfoDTOs() {
        return moduleAccountInfoDTOs;
    }

    public void setModuleAccountInfoDTOs(List<ModuleAccountInfoDTO> moduleAccountInfoDTOs) {
        this.moduleAccountInfoDTOs = moduleAccountInfoDTOs;
    }
}
