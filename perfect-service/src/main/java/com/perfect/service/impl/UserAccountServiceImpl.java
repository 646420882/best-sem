package com.perfect.service.impl;

import com.perfect.dao.account.AccountManageDAO;
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.service.UserAccountService;
import org.bson.types.ObjectId;
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
    private AccountManageDAO accountManageDAO;


    @Override
    public List<ModuleAccountInfoDTO> getSemAccounts(String username) {
        return accountManageDAO.getBaiduAccountItems(username);
    }

    @Override
    public boolean bindAccountForSem(String username, ModuleAccountInfoDTO dto) {
        if (Objects.isNull(dto.getId())) {
            dto.setId(new ObjectId().toString());
        }

        // 设置帐号绑定时间
        dto.setAccountBindingTime(Calendar.getInstance().getTimeInMillis());
        // 设置帐号为可用状态
        dto.setState(1L);

        systemUserDAO.insertAccountInfo(username, dto);

        return true;
    }

    @Override
    public void unbindAccountForSem(String username, String moduleAccountId) {
        accountManageDAO.updateBaiduAccountStatus(username, moduleAccountId, 0L);
    }

    @Override
    public void activeAccountForSem(String username, String moduleAccountId) {
        accountManageDAO.updateBaiduAccountStatus(username, moduleAccountId, 1L);
    }

    @Override
    public void updateAccountForSem(ModuleAccountInfoDTO dto) {
        accountManageDAO.updateBaiduAccountBasicInfo(dto);
    }

    @Override
    public void deleteAccountForSem(String username, String moduleAccountId) {
        accountManageDAO.deleteBaiduAccount(username, moduleAccountId);
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
}
