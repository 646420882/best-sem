package com.perfect.service.impl;

import com.perfect.dao.account.AccountRegisterDAO;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.service.AccountRegisterService;
import com.perfect.utils.MD5;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Created by SubDong on 2014/9/30.
 */
@Service("accountRegisterService")
public class AccountRegisterServiceImpl implements AccountRegisterService {

    @Resource
    private AccountRegisterDAO accountRegisterDAO;

    @Override
    public int addAccount(String account, String pwd, String company, String email) {

        SystemUserDTO systemUserDTO = new SystemUserDTO();
        MD5.Builder md5Builder = new MD5.Builder();
        MD5 md5 = md5Builder.password(pwd).salt(account).build();
        SystemUserDTO administrator = accountRegisterDAO.getAccount("administrator");
        systemUserDTO.setAccess(2);
        systemUserDTO.setBaiduAccounts(new ArrayList<BaiduAccountInfoDTO>());
        systemUserDTO.setUserName(account);
        systemUserDTO.setPassword(md5.getMD5());
        systemUserDTO.setCompanyName(company);
        systemUserDTO.setEmail(email);
        systemUserDTO.setState(0);
        systemUserDTO.setImg(administrator.getImg());

        systemUserDTO.setAccountState(1);
        int returnState;
        SystemUserDTO user = accountRegisterDAO.getAccount(account);
        if (user == null) {
            accountRegisterDAO.addAccount(systemUserDTO);
            returnState = 1;
        } else {
            returnState = -1;
        }
        return returnState;
    }
}
