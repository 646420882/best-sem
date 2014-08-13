package com.perfect.utils;

import com.google.common.base.Strings;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.entity.BaiduAccountInfoEntity;

/**
 * Created by baizz on 2014-6-12.
 */
public class BaiduServiceSupport {
    private static CommonService commonService;

    private BaiduServiceSupport() {
    }

    public synchronized static CommonService getCommonService() {
        if (commonService == null) {
            try {
                commonService = ServiceFactory.getInstance();
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
        return commonService;
    }


    public static CommonService getCommonService(String username, String pwd, String tokenId) {

        if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(pwd) || Strings.isNullOrEmpty(tokenId)) {
            return null;
        }

        try {
            return ServiceFactory.getInstance(username, pwd, tokenId, null);
        } catch (ApiException e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }

    public static CommonService getCommonService(BaiduAccountInfoEntity baiduAccountInfoEntity) {

        if (baiduAccountInfoEntity == null) {
            return null;
        }

        try {
            return ServiceFactory.getInstance(baiduAccountInfoEntity.getBaiduUserName(), baiduAccountInfoEntity.getBaiduPassword(), baiduAccountInfoEntity.getToken(), null);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }
}
