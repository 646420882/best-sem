package com.perfect.service.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.dto.sys.SystemUserModuleDTO;
import com.perfect.service.BaiduAccountService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by john on 2014/11/7.
 */
@Repository("baiduAccountService")
public class BaiduAccountServiceImpl implements BaiduAccountService {


    @Resource
    private SystemUserDAO systemUserDAO;

    @Override
    public ModuleAccountInfoDTO getBaiduAccountInfoBySystemUserNameAndAcId(String systemUserName, Long accountId) {
        ModuleAccountInfoDTO baiduUser = null;

        SystemUserDTO systemUserEntity = systemUserDAO.findByUserName(systemUserName);

        SystemUserModuleDTO systemUserModuleDTO = null;

        try {
            systemUserModuleDTO = systemUserEntity.getModuleDTOList().stream().filter((tmp -> tmp.getModuleName().equals(AppContext.getModuleName()))).findFirst().get();
        } catch (Exception ex) {
            return null;
        }

        ModuleAccountInfoDTO moduleAccountInfoDTO = null;
        try {
            systemUserModuleDTO.getAccounts().stream().filter(tmp -> tmp.getBaiduAccountId().equals(accountId)).findFirst().get();
        } catch (Exception ex) {
            return null;
        }
        return moduleAccountInfoDTO;
    }
}
