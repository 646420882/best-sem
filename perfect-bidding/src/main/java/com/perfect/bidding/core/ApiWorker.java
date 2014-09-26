package com.perfect.bidding.core;

import com.perfect.api.baidu.BaiduApiService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.KeywordType;

/**
 * Created by vbzer_000 on 2014/9/26.
 */
public class ApiWorker implements Runnable {
    private final BaiduApiService apiService;
    private final KeywordType keywordType;

    public ApiWorker(BaiduApiService apiService, KeywordType keywordType) {

        this.apiService = apiService;
        this.keywordType = keywordType;
    }

    @Override
    public void run() {
        int retry = 3;
        while (retry > 0) {
            try {
                apiService.setKeywordPrice(keywordType);
                break;
            } catch (ApiException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

            } finally {
                retry--;
            }
        }
    }
}
