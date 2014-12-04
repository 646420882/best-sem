package com.perfect.app.bdlogin.core;

import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by baizz on 2014-11-5.
 */
public class BaiduHttpLogin {
    public static final String phantomJSPath =
            new File(BaiduHttpLogin.class.getResource("/").getPath()).getPath() + System.getProperty("file.separator") + "phantomJs" + System.getProperty("file.separator") + "baiduLogin.js ";

    public static final Set<String> set = new HashSet<String>() {{
        addAll(Arrays.asList("CASSSID", "GBIZSSID", "GIMGSSID", "LOGINAID", "LOGINUID", "__cas__id__", "__cas__st__", "bdsfuid"));
    }};

    private static CookieStore cookies = new BasicCookieStore();
    private static CookieStore sslCookies = new BasicCookieStore();
    private static CloseableHttpClient sslHttpClient;
    private static String redirectUrl;  //check success before redirect
    private static String castk;

    private static String loginUrl;

    static {
        String path = new File(BaiduHttpLogin.class.getResource("/").getPath()).getPath() + System.getProperty("file.separator") + "bdlogin.properties";
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(path));
            Properties properties = new Properties();
            properties.load(is);
            loginUrl = properties.getProperty("bd.login-url");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CloseableHttpClient createSSLClientDefault(boolean isSSL) {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        RequestConfig requestConfig = RequestConfig.custom().setCircularRedirectsAllowed(true).build();
        if (isSSL) {
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
        } else {
            httpClientBuilder.setDefaultCookieStore(cookies);
        }

        return httpClientBuilder.setDefaultRequestConfig(requestConfig).build();
    }

    protected static void headerWrap(HttpRequest request) {
        request.setHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:33.0) Gecko/20100101 Firefox/33.0");
        request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        request.setHeader("Accept-language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
        request.setHeader("Accept-Encoding", "gzip, deflate");
    }

    protected static boolean login(String username, String password, String imagecode, String cookies) throws IOException {
        sslHttpClient = createSSLClientDefault(true);

        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("appid", "3"));
        formParams.add(new BasicNameValuePair("button2", "登录"));
        formParams.add(new BasicNameValuePair("entered_login", username));
        formParams.add(new BasicNameValuePair("entered_password", password));
        formParams.add(new BasicNameValuePair("entered_imagecode", imagecode));
        formParams.add(new BasicNameValuePair("tpl", "www2"));
        formParams.add(new BasicNameValuePair("fromu", "http://www2.baidu.com/"));

        HttpPost httpPost = new HttpPost(loginUrl);
        httpPost.addHeader("Cookie", cookies);
        httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpPost.setEntity(new UrlEncodedFormEntity(formParams, StandardCharsets.UTF_8));

        HttpResponse loginResponse = sslHttpClient.execute(httpPost);
        if (loginResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = loginResponse.getEntity();
            String body = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            String data = Jsoup.parse(body).body().data();
            String url = data.substring(data.indexOf("\"") + 1, data.lastIndexOf("\""));
            boolean isSuccess = url.contains("action=check");
            if (isSuccess) {
                redirectUrl = url;
                int first = url.indexOf("castk") + 8;
                int last = url.lastIndexOf("&");
                castk = url.substring(first, last);
            }

            return isSuccess;
        }

        return false;
    }

    public static boolean execute(String username, String password, String imagecode, String cookies) throws IOException {
        return login(username, password, imagecode, cookies) && redirect(redirectUrl);
    }

    protected static boolean redirect(String url) throws IOException {
        HttpGet redirectRequest = new HttpGet(url);
        headerWrap(redirectRequest);
        HttpResponse redirectResponse = sslHttpClient.execute(redirectRequest);
        return redirectResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
    }

    public static void main(String[] args) throws IOException {
        CaptchaHandler.handle(phantomJSPath);
        String cookies = CaptchaHandler.getCookies();

        Scanner sc = new Scanner(System.in);
        System.out.println("请输入验证码: ");
        String imagecode = sc.next();

        if (login("baidu-bjtthunbohui2134115", "Bjhunbohui7", imagecode, cookies)) {
            if (redirect(redirectUrl)) {
                System.out.println("======================success========================");
                System.out.println("castk: " + castk);
                for (Cookie cookie : sslCookies.getCookies()) {
                    System.out.println(cookie.toString());
                }
                System.out.println();
            }
        }

        //推广实况
        StringBuilder _cookies = new StringBuilder("");
        String userid = "";
        String token = "";

        for (Cookie cookie : sslCookies.getCookies()) {
            if (set.contains(cookie.getName())) {
                continue;
            }

            if ("__cas__st__3".equals(cookie.getName())) {
                token = cookie.getValue();
            } else if ("__cas__id__3".equals(cookie.getName())) {
                userid = cookie.getValue();
            }

            _cookies.append(cookie.getName()).append("=").append(cookie.getValue()).append("; ");
        }
        _cookies = _cookies.delete(_cookies.length() - 2, _cookies.length());

        HttpPost httpPost = new HttpPost("http://fengchao.baidu.com/nirvana/request.ajax?path=nirvana/GET/Live");
        httpPost.addHeader("Host", "fengchao.baidu.com");
        httpPost.addHeader("Cookie", _cookies.toString());
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> postData = new ArrayList<>();
        postData.add(new BasicNameValuePair("params", "{\"device\":1,\"keyword\":\"test\",\"area\":1,\"pageNo\":0}"));
        postData.add(new BasicNameValuePair("userid", userid));
        postData.add(new BasicNameValuePair("token", token));
        httpPost.setEntity(new UrlEncodedFormEntity(postData, StandardCharsets.UTF_8));
        headerWrap(httpPost);

        HttpResponse httpResponse = sslHttpClient.execute(httpPost);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            System.out.println(EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8));
        }
        if (sslHttpClient != null) {
            sslHttpClient.close();
        }
    }

    public static CookieStore getSSLCookies() {
        return sslCookies;
    }

    public static CookieStore getCookies() {
        return cookies;
    }

    public static String getCastk() {
        return castk;
    }
}