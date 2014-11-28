package com.perfect.service;

import com.perfect.entity.sys.BaiduAccountInfoEntity;

/**
 * Created by john on 2014/11/7.
 */
public interface BaiduAccountService {

    /**
     * 根据系统账户名和百度账户id得到百度账户
     * @param systemUserName
     * @param accountId
     * @return
     */

    BaiduAccountInfoEntity getBaiduAccountInfoBySystemUserNameAndAcId(String systemUserName, Long accountId);
}
