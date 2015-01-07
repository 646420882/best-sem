package com.perfect.commons.bdlogin;

import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.PKIXRevocationChecker;
import java.security.cert.X509Certificate;

/**
 * Created by baizz on 2014-12-16.
 * refactor 2015-1-7
 */
public abstract class AbstractBaiduHttpClient implements BaiduHttpClient {

    protected static CookieStore cookies = new BasicCookieStore();
    protected static CookieStore sslCookies = new BasicCookieStore();

    protected CloseableHttpClient httpClient;

    protected AbstractBaiduHttpClient() {
        if (httpClient == null)
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

        // 初始化httpClient客户端
        httpClient = createSSLHttpClientBuilder()
                .setConnectionManager(httpClientConnectionManager)
//                .setDefaultRequestConfig(requestConfig)
                .setUserAgent(USER_AGENT)
                .build();
    }

    private HttpClientBuilder createSSLHttpClientBuilder() {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        RequestConfig requestConfig = RequestConfig.custom().setCircularRedirectsAllowed(true).build();
        SSLContext sslContext = null;
        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();
            if (sslContext != null) {
                //
                X509TrustManager xtm = new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                };
                //
                sslContext.init(null, new TrustManager[]{xtm}, null);
                javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
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

//    private static final AbstractBaiduHttpClient baiduHttpClient = new AbstractBaiduHttpClient() {
//        @Override
//        public String toString() {
//            return super.toString();
//        }
//    };

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
