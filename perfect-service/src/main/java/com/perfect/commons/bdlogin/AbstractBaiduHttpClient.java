package com.perfect.commons.bdlogin;

import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by baizz on 2014-12-16.
 * refactor 2015-1-7
 */
public abstract class AbstractBaiduHttpClient implements BaiduHttpClient {

    protected static CookieStore cookies = new BasicCookieStore();
    protected static CookieStore sslCookies = new BasicCookieStore();

    protected CloseableHttpClient httpClient;

    protected AbstractBaiduHttpClient() {
        initHttpClient();
    }

    private void initHttpClient() {
        // 创建httpClient连接池
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();
        // 设置连接池最大线程数量
        httpClientConnectionManager.setMaxTotal(MAX_TOTAL);
        // 设置单个路由的最大连接线程数量
        httpClientConnectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
        // 创建http request的配置信息
        RequestConfig requestConfig = RequestConfig.custom().setCircularRedirectsAllowed(true).build();
        // 设置重定向策略
        LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();
        // 初始化httpClient客户端
        httpClient = createSSLHttpClientBuilder()
                .setConnectionManager(httpClientConnectionManager)
                .setDefaultRequestConfig(requestConfig)
                .setRedirectStrategy(redirectStrategy)
                .setUserAgent(USER_AGENT)
                .build();
    }

    private HttpClientBuilder createSSLHttpClientBuilder() {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        SSLContext sslContext = null;
        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }

        if (sslContext != null) {
            SSLConnectionSocketFactory sslcsf = new SSLConnectionSocketFactory(sslContext);
            httpClientBuilder.setSSLSocketFactory(sslcsf).setDefaultCookieStore(sslCookies);
        } else {
            httpClientBuilder.setDefaultCookieStore(cookies);
        }

        return httpClientBuilder;
    }

    private static final AbstractBaiduHttpClient baiduHttpClient = new AbstractBaiduHttpClient() {
        @Override
        public String toString() {
            return super.toString();
        }
    };

//    public static AbstractBaiduHttpClient getBaiduHttpClient(){
//        return baiduHttpClient;
//    }

//    @Override
//    public CloseableHttpClient createSSLClientDefault(boolean isSSL) {
//        HttpClientBuilder httpClientBuilder = HttpClients.custom();
//        RequestConfig requestConfig = RequestConfig.custom().setCircularRedirectsAllowed(true).build();
//        if (isSSL) {
//            SSLContext sslContext = null;
//            try {
//                sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();
//            } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
//                e.printStackTrace();
//            }
//            if (sslContext != null) {
//                SSLConnectionSocketFactory sslcsf = new SSLConnectionSocketFactory(sslContext);
//                httpClientBuilder.setSSLSocketFactory(sslcsf).setDefaultCookieStore(sslCookies);
//            } else {
//                httpClientBuilder.setDefaultCookieStore(cookies);
//            }
//        } else {
//            httpClientBuilder.setDefaultCookieStore(cookies);
//        }
//
//        return httpClientBuilder.setDefaultRequestConfig(requestConfig).build();
//    }

    public static CookieStore getSSLCookies() {
        return sslCookies;
    }

    public static CookieStore getCookies() {
        return cookies;
    }

}
