package com.perfect.commons.bdlogin;

import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by baizz on 2014-12-16.
 * refactor 2015-1-8
 */
public abstract class AbstractBaiduHttpClient implements BaiduHttp {

    protected static CookieStore cookies = new BasicCookieStore();
    protected static CookieStore sslCookies = new BasicCookieStore();

    protected HttpClientBuilder httpClientBuilder;


    protected AbstractBaiduHttpClient() {
        if (httpClientBuilder == null)
            httpClientBuilder = sslHttpClientBuilder();
    }

    protected CloseableHttpClient createHttpClient() {
        return httpClientBuilder.build();
    }

    private HttpClientBuilder sslHttpClientBuilder() {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        RequestConfig requestConfig = RequestConfig.custom().setCircularRedirectsAllowed(true).build();
        SSLContext sslContext;
        try {
            sslContext = new SSLContextBuilder().useTLS().loadTrustMaterial(null, (chain, authType) -> true).build();
            if (sslContext != null) {
                SSLConnectionSocketFactory sslcsf = new SSLConnectionSocketFactory(sslContext);
                httpClientBuilder.setSSLSocketFactory(sslcsf).setDefaultCookieStore(sslCookies);
            } else {
                httpClientBuilder.setDefaultCookieStore(cookies);
            }
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }


        return httpClientBuilder.setDefaultRequestConfig(requestConfig);
    }


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
