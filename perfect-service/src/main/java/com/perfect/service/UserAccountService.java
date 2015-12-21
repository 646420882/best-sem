package com.perfect.service;

import com.perfect.api.baidu.BaiduApiService;
import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.autosdk.sms.v3.AccountInfoType;
import com.perfect.dto.sys.ModuleAccountInfoDTO;

import java.util.Objects;

/**
 * Created on 2015-12-20.
 * <p>系统用户中心接口
 *
 * @author dolphineor
 */
public interface UserAccountService extends SoukeAccountService, HuiyanAccountService {

    /**
     * <p>获取用户邮箱
     *
     * @param username
     * @return
     */
    String getUserEmail(String username);

    /**
     * <p>更新用户邮箱
     *
     * @param username
     * @param email
     */
    void updateEmail(String username, String email);


//    default long getBaiduAccountId(ModuleAccountInfoDTO dto) {
//        if (Objects.isNull(dto))
//            return -1L;
//
//        BaiduApiService baiduApiService = new BaiduApiService(
//                BaiduServiceSupport.getCommonService(dto.getBaiduUserName(), dto.getBaiduPassword(), dto.getToken()));
//
//        AccountInfoType accountInfoType = baiduApiService.getAccountInfo();
//        if (Objects.isNull(accountInfoType))
//            return -1L;
//
//        return accountInfoType.getUserid();
//    }
//
//    default AccountInfoType getBaiduAccount(ModuleAccountInfoDTO dto) {
//        if (Objects.isNull(dto))
//            return null;
//
//        BaiduApiService baiduApiService = new BaiduApiService(
//                BaiduServiceSupport.getCommonService(dto.getBaiduUserName(), dto.getBaiduPassword(), dto.getToken()));
//
//        AccountInfoType accountInfoType = baiduApiService.getAccountInfo();
//        if (Objects.isNull(accountInfoType))
//            return null;
//
//        return accountInfoType;
//    }
}
