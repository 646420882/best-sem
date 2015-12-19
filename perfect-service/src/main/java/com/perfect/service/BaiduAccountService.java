package com.perfect.service;

import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.sys.ModuleAccountInfoDTO;

/**
 * Created by john on 2014/11/7.
 * 2014-11-28 refactor
 */
public interface BaiduAccountService {

    /**
     * 根据系统账户名和百度账户id得到百度账户
     *
     * @param systemUserName
     * @param accountId
     * @return
     */
    ModuleAccountInfoDTO getBaiduAccountInfoBySystemUserNameAndAcId(String systemUserName, Long accountId);
}
