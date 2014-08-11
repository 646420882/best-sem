package com.perfect.mongodb.utils;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;

/**
 * Created by baizz on 14-7-23.
 */
public class BaseBaiduService {
    private static CommonService commonService;

    private BaseBaiduService() {
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


    public synchronized static CommonService getCommonServiceUser(String userName,String pwd,String toked,String target) {
        if (commonService == null) {
            String targetString = "";
            try {
                if(target.equals("") || target == null){
                    targetString = null;
                }else{
                    targetString = target;
                }
                commonService = ServiceFactory.getInstance(userName,pwd,toked,targetString);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
        return commonService;
    }
}
