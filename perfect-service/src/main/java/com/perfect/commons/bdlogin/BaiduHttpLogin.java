package com.perfect.commons.bdlogin;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by baizz on 2014-11-5.
 */
public class BaiduHttpLogin extends AbstractBaiduHttpClient {

    private static final BaiduHttpLogin instance = new BaiduHttpLogin();

    private static String baiduLoginJSPath = null;
    private static CloseableHttpClient sslHttpClient;
    private static String redirectUrl;
    private static String castk;
    private static String loginUrl;

    static {
        URL url = BaiduHttpLogin.class.getClassLoader().getResource("phantomJs" + System.getProperty("file.separator") + "baiduLogin.js");
        Objects.requireNonNull(url);
        baiduLoginJSPath = url.getPath();

        loginUrl = baiduLoginURL;
//        url = BaiduHttpLogin.class.getClassLoader().getResource("bdlogin.properties");
//        Objects.requireNonNull(url);
//        String path = url.getPath();
//
//        try {
//            InputStream is = new BufferedInputStream(new FileInputStream(path));
//            Properties properties = new Properties();
//            properties.load(is);
//            loginUrl = properties.getProperty("bd.login-url");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private static BaiduHttpLogin getInstance() {
        return instance;
    }

    protected static boolean login(String username, String password, String imagecode, String cookies) throws IOException {
        sslHttpClient = getInstance().createSSLClientDefault(true);

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
        httpPost.setHeader("Accept", Accept);
        httpPost.setHeader("Content-Type", contentType);
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
        getInstance().headerWrap(redirectRequest);
        HttpResponse redirectResponse = sslHttpClient.execute(redirectRequest);
        return redirectResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
    }

    public static String getBaiduLoginJSPath() {
        return baiduLoginJSPath;
    }

    public static void main(String[] args) throws IOException {
        CaptchaHandler.handle(baiduLoginJSPath);
        String cookies = CaptchaHandler.getCaptchaCookies();

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

            if (__cas__st__3.equals(cookie.getName())) {
                token = cookie.getValue();
            } else if (__cas__id__3.equals(cookie.getName())) {
                userid = cookie.getValue();
            }

            _cookies.append(cookie.getName()).append("=").append(cookie.getValue()).append("; ");
        }
        _cookies = _cookies.delete(_cookies.length() - 2, _cookies.length());

        HttpPost httpPost = new HttpPost(baiduPreviewURL);
        httpPost.addHeader("Host", baiduPreviewHost);
        httpPost.addHeader("Cookie", _cookies.toString());
        httpPost.addHeader("Content-Type", contentType);

        List<NameValuePair> postData = new ArrayList<>();
        postData.add(new BasicNameValuePair("params", "{\"device\":1,\"keyword\":\"test\",\"area\":1,\"pageNo\":0}"));
        postData.add(new BasicNameValuePair("userid", userid));
        postData.add(new BasicNameValuePair("token", token));
        httpPost.setEntity(new UrlEncodedFormEntity(postData, StandardCharsets.UTF_8));
        getInstance().headerWrap(httpPost);

        HttpResponse httpResponse = sslHttpClient.execute(httpPost);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            System.out.println(EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8));
        }
        if (sslHttpClient != null) {
            sslHttpClient.close();
        }
    }

    public static String getCastk() {
        return castk;
    }
}