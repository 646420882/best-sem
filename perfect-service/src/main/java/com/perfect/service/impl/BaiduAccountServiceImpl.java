package com.perfect.service.impl;

import com.perfect.account.BaseBaiduAccountInfoVO;
import com.perfect.account.SystemUserInfoVO;
import com.perfect.service.BaiduAccountService;
import com.perfect.service.SystemUserInfoService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by john on 2014/11/7.
 */
@Repository("baiduAccountService")
public class BaiduAccountServiceImpl implements BaiduAccountService {


    @Resource
    private SystemUserInfoService systemUserInfoService;

    @Override
    public BaseBaiduAccountInfoVO getBaiduAccountInfoBySystemUserNameAndAcId(String systemUserName, Long accountId) {
        SystemUserInfoVO systemUserInfoVO = systemUserInfoService.findSystemUserInfoByUserName(systemUserName);
        if (systemUserInfoVO == null)
            return null;

        List<BaseBaiduAccountInfoVO> list = systemUserInfoVO.getBaiduAccounts();
        for (BaseBaiduAccountInfoVO baiduAccountInfoVO : list) {
            if (accountId.longValue() == baiduAccountInfoVO.getAccountId().longValue())
                return baiduAccountInfoVO;
        }

        return null;
    }
}
