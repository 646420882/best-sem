package com.perfect.bidding.core;

import com.google.common.collect.Lists;
import com.perfect.api.baidu.BaiduApiService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.KeywordType;

/**
 * Created by vbzer_000 on 2014/9/26.
 */
public class BaiduApiWorker implements Runnable {

    private final BaiduApiService apiService;
    private final KeywordType keywordType;


    public BaiduApiWorker(BaiduApiService apiService, KeywordType keywordType) {
        this.apiService = apiService;
        this.keywordType = keywordType;
    }


    @Override
    public void run() {
        int retry = 3;
        while (retry > 0) {
            try {
                apiService.setKeywordPrice(Lists.newArrayList(keywordType));
                break;
            } catch (ApiException e) {
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

            } finally {
                retry--;
            }
        }
    }

}
