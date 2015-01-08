package com.perfect.commons.bdlogin;

import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Created by baizz on 2015-1-8.
 */
public class BaiduHttpConnFactory extends AbstractBaiduHttpClient {

    private static final BaiduHttpConnFactory httpConnFactory = new BaiduHttpConnFactory();


    public static CloseableHttpClient build() {
        return httpConnFactory.createHttpClient();
    }
}
