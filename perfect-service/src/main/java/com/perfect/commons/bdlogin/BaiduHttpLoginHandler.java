package com.perfect.commons.bdlogin;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by baizz on 2014-11-5.
 * refactor 2015-1-7
 */
@Component("baiduLoginHandler")
public class BaiduHttpLoginHandler extends AbstractBaiduHttpClient {

    private static String baiduLoginJSPath = null;
    private static String redirectUrl;
    private static String castk;

    static {
        URL url = BaiduHttpLoginHandler.class.getClassLoader().getResource("phantomJs" + System.getProperty("file.separator") + "baiduLogin.js");
        Objects.requireNonNull(url);
        baiduLoginJSPath = url.getPath();
    }


    public boolean login(String username, String password, String imagecode, String cookies) throws IOException {
        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("appid", "3"));
        formParams.add(new BasicNameValuePair("button2", "登录"));
        formParams.add(new BasicNameValuePair("entered_login", username));
        formParams.add(new BasicNameValuePair("entered_password", password));
        formParams.add(new BasicNameValuePair("entered_imagecode", imagecode));
        formParams.add(new BasicNameValuePair("tpl", "www2"));
        formParams.add(new BasicNameValuePair("fromu", "http://www2.baidu.com/"));

        HttpPost httpPost = new HttpPost(LOGIN_URL);
        httpPost.addHeader("Cookie", cookies);
        httpPost.setHeader("Accept", ACCEPT);
        httpPost.setHeader("Content-Type", CONTENT_TYPE);
        httpPost.setEntity(new UrlEncodedFormEntity(formParams, StandardCharsets.UTF_8));

        CloseableHttpResponse loginResponse = httpClient.execute(httpPost);
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

    public boolean execute(String username, String password, String imagecode, String cookies) throws IOException {
        return login(username, password, imagecode, cookies) && redirect(redirectUrl);
    }

    public boolean redirect(String url) throws IOException {
        HttpGet redirectRequest = new HttpGet(url);
        BaiduHttpClient.headerWrap(redirectRequest);
        CloseableHttpResponse redirectResponse = httpClient.execute(redirectRequest);
        return redirectResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
    }


    public static String getBaiduLoginJSPath() {
        return baiduLoginJSPath;
    }

    public static String getRedirectUrl() {
        return redirectUrl;
    }

    public static String getCastk() {
        return castk;
    }
}