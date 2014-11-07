package com.perfect.service.impl;

import com.perfect.dao.SystemUserDAO;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.SystemUserEntity;
import com.perfect.service.BaiduAccountService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by john on 2014/11/7.
 */
@Repository("baiduAccountService")
public class BaiduAccountServiceImpl implements BaiduAccountService {


    @Resource
    private SystemUserDAO systemUserDAO;

    @Override
    public BaiduAccountInfoEntity getBaiduAccountInfoBySystemUserNameAndAcId(String systemUserName, Long accountId){
        BaiduAccountInfoEntity  baiduUser = null;

        SystemUserEntity systemUserEntity = systemUserDAO.findByUserName(systemUserName);
        List<BaiduAccountInfoEntity> list = systemUserEntity.getBaiduAccountInfoEntities();
        for(BaiduAccountInfoEntity baidu : list){
            if(baidu.getId().longValue()==accountId.longValue()){
                baiduUser = baidu;
                break;
            }
        }

        return baiduUser;
    }
}
