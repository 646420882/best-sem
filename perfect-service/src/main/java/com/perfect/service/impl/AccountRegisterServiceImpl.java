package com.perfect.service.impl;

import com.perfect.dao.AccountRegisterDAO;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.MD5;
import com.perfect.entity.SystemUserEntity;
import com.perfect.service.AccountRegisterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Created by SubDong on 2014/9/30.
 */
@Service("accountRegisterService")
public class AccountRegisterServiceImpl implements AccountRegisterService{

    @Resource
    private AccountRegisterDAO accountRegisterDAO;

    @Override
    public int addAccount(String account, String pwd, String company) {

        SystemUserEntity userEntity= new SystemUserEntity();
        MD5.Builder md5Builder = new MD5.Builder();
        MD5 md5 = md5Builder.password(pwd).salt(account).build();

        userEntity.setAccess(2);
        userEntity.setBaiduAccountInfoEntities(new ArrayList<BaiduAccountInfoEntity>());
        userEntity.setUserName(account);
        userEntity.setPassword(md5.getMD5());
        userEntity.setCompanyName(company);
        userEntity.setState(0);
        int returnState;
        SystemUserEntity user = accountRegisterDAO.getAccount(account);
        if(user == null){
            accountRegisterDAO.addAccount(userEntity);
            returnState = 1;
        }else{
            returnState = -1;
        }
        return returnState;
    }
}
