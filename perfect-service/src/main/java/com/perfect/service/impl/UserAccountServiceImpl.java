package com.perfect.service.impl;

import com.perfect.commons.constants.SystemNameConstant;
import com.perfect.dao.account.SystemAccountDAO;
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.dto.sys.SystemUserModuleDTO;
import com.perfect.service.UserAccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * Created on 2015-12-20.
 *
 * @author dolphineor
 */
@Service("userAccountService")
public class UserAccountServiceImpl implements UserAccountService {


    @Resource
    private SystemUserDAO systemUserDAO;

    @Resource
    private SystemAccountDAO systemAccountDAO;


    @Override
    public List<ModuleAccountInfoDTO> getSemAccounts(String username) {
        String userId = systemAccountDAO.findByUserName(username).getId();
        String sysUserModuleId = systemAccountDAO.findSysUserModuleId(username, SystemNameConstant.SOUKE_SYSTEM_NAME);

        return systemAccountDAO.findByUserIdAndModuleId(userId, sysUserModuleId);
    }

    @Override
    public long[] getSemModuleServiceLife(String username) {
        SystemUserDTO systemUserDTO = systemAccountDAO.findByUserName(username);
        try {
            SystemUserModuleDTO systemUserModuleDTO = systemUserDTO.getSystemUserModules()
                    .stream()
                    .filter(o -> Objects.equals(SystemNameConstant.SOUKE_SYSTEM_NAME, o.getModuleName()))
                    .findFirst()
                    .get();

            long[] serviceLife = new long[2];
            serviceLife[0] = systemUserModuleDTO.getStartTime();
            serviceLife[1] = systemUserModuleDTO.getEndTime();

            return serviceLife;
        } catch (NullPointerException e) {
            return new long[2];
        }
    }

    @Override
    public boolean bindAccountForSem(String username, ModuleAccountInfoDTO dto) {
        // 设置帐号绑定时间
        dto.setAccountBindingTime(Calendar.getInstance().getTimeInMillis());
        // 设置帐号为可用状态
        dto.setState(1L);
        setModuleAccountBasicInfoForSem(username, dto);

        systemAccountDAO.insertModuleAccount(dto);

        return true;
    }

    @Override
    public void unbindAccountForSem(String username, String moduleAccountId) {
        ModuleAccountInfoDTO moduleAccountInfoDTO = new ModuleAccountInfoDTO();
        setModuleAccountBasicInfoForSem(username, moduleAccountInfoDTO);
        moduleAccountInfoDTO.setState(0L);
        systemAccountDAO.updateModuleAccount(moduleAccountInfoDTO);
    }

    @Override
    public void activeAccountForSem(String username, String moduleAccountId) {
        ModuleAccountInfoDTO moduleAccountInfoDTO = new ModuleAccountInfoDTO();
        setModuleAccountBasicInfoForSem(username, moduleAccountInfoDTO);
        moduleAccountInfoDTO.setId(moduleAccountId);
        moduleAccountInfoDTO.setState(1L);
        systemAccountDAO.updateModuleAccount(moduleAccountInfoDTO);
    }

    @Override
    public void updateAccountForSem(String username, ModuleAccountInfoDTO dto) {
        setModuleAccountBasicInfoForSem(username, dto);
        systemAccountDAO.updateModuleAccount(dto);
    }

    @Override
    public void deleteAccountForSem(String moduleAccountId) {
        systemAccountDAO.deleteByObjectId(moduleAccountId);
    }

    @Override
    public String getUserEmail(String username) {
        String email = systemUserDAO.getUserEmail(username);

        if (Objects.isNull(email))
            return "";

        return email;
    }

    @Override
    public void updateEmail(String username, String email) {
        String sysUserId = systemUserDAO.findByUserName(username).getId();
        systemUserDAO.updateUserEmail(sysUserId, email);
    }

    private void setModuleAccountBasicInfoForSem(String username, ModuleAccountInfoDTO dto) {
        SystemUserDTO systemUserDTO = systemAccountDAO.findByUserName(username);
        dto.setUserId(systemUserDTO.getId());

        String sysUserModuleId = systemAccountDAO.findSysUserModuleId(username, SystemNameConstant.SOUKE_SYSTEM_NAME);
        dto.setModuleId(sysUserModuleId);
    }
}
