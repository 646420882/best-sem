package com.perfect.service.impl;

import com.perfect.autosdk.sms.v3.AccountInfoType;
import com.perfect.dao.account.AccountManageDAO;
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.service.UserAccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
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
    public boolean bindAccountForSem(String username, ModuleAccountInfoDTO dto, String accountPlatformType) {
        if (Objects.isNull(accountPlatformType)) {
            // 设置帐号的默认平台
            dto.setAccountPlatformType(BAIDU);
        }

        // 设置帐号绑定时间
        dto.setAccountBindingTime(Calendar.getInstance().getTimeInMillis());

        // 调用百度API获取凤巢帐号ID
        AccountInfoType accountInfoType = getBaiduAccount(dto);
        if (Objects.isNull(accountInfoType)) {
            return false;
        }

        BeanUtils.copyProperties(dto, accountInfoType);
        dto.setBaiduAccountId(accountInfoType.getUserid());

        systemUserDAO.insertAccountInfo(username, dto);

        return true;
    }

    @Override
    public void unbindAccountForSem(String username, Long moduleAccountId) {
        accountManageDAO.updateBaiduAccountStatus(username, moduleAccountId, 0L);
    }

    @Override
    public void activeAccountForSem(String username, Long moduleAccountId) {
        accountManageDAO.updateBaiduAccountStatus(username, moduleAccountId, 1L);
    }

    @Override
    public void updateAccountForSem(String username, ModuleAccountInfoDTO dto) {
        long baiduAccountId = getBaiduAccountId(dto);
        if (baiduAccountId == -1) {
            return;
        }

        accountManageDAO.updateBaiduAccountInfo(username, baiduAccountId, dto);
    }

    @Override
    public void deleteAccountForSem(String username, Long moduleAccountId) {
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
