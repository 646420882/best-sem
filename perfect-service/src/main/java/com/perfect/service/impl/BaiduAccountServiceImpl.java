package com.perfect.service.impl;

import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.service.BaiduAccountService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by john on 2014/11/7.
 *
 */
@Repository("baiduAccountService")
public class BaiduAccountServiceImpl implements BaiduAccountService {


    @Resource
    private SystemUserDAO systemUserDAO;

    @Override
    public BaiduAccountInfoDTO getBaiduAccountInfoBySystemUserNameAndAcId(String systemUserName, Long accountId){
        BaiduAccountInfoDTO  baiduUser = null;

        SystemUserDTO systemUserEntity = systemUserDAO.findByUserName(systemUserName);
        List<BaiduAccountInfoDTO> list = systemUserEntity.getBaiduAccountInfoDTOs();
        for(BaiduAccountInfoDTO baidu : list){
            if(baidu.getId().longValue()==accountId.longValue()){
                baiduUser = baidu;
                break;
            }
        }
        return baiduUser;
    }
}
