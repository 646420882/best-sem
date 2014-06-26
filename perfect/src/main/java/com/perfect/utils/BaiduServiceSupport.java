package com.perfect.utils;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;

/**
 * Created by baizz on 2014-6-12.
 */
public class BaiduServiceSupport {
    private static CommonService commonService;

    private BaiduServiceSupport() {
    }

    public synchronized static CommonService getService() {
        if (commonService == null) {
            try {
                commonService = ServiceFactory.getInstance();
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
        return commonService;
    }
}
