package com.perfect.api.baidu;

import com.google.common.base.Strings;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;

/**
 * Created by baizz on 2014-6-12.
 * 2014-11-24 refactor
 */
public class BaiduServiceSupport {

    private BaiduServiceSupport() {
    }

    public static CommonService getCommonService(String username, String pwd, String tokenId) {

        if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(pwd) || Strings.isNullOrEmpty(tokenId)) {
            return null;
        }

        try {
            return ServiceFactory.getInstance(username, pwd, tokenId, null);
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return null;
    }

}
