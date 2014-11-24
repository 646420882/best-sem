package com.perfect.app.bdlogin.core;

import com.perfect.dao.CookieDAO;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicNameValuePair;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by baizz on 2014-11-10.
 * 2014-11-24 refactor
 */
public class PreviewHandler {

    @Resource
    private CookieService cookieService;

    public static HttpPost getPostRequest(String keyword, int area) {
        StringBuilder _cookies = new StringBuilder("");
        String userid = "";
        String token = "";

        for (Cookie cookie : getCookies().getCookies()) {
            if (BaiduHttpLogin.set.contains(cookie.getName())) {
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
        postData.add(new BasicNameValuePair("params", "{\"device\":1,\"keyword\":\"" + keyword + "\",\"area\":" + area + ",\"pageNo\":0}"));
        postData.add(new BasicNameValuePair("userid", userid));
        postData.add(new BasicNameValuePair("token", token));
        httpPost.setEntity(new UrlEncodedFormEntity(postData, StandardCharsets.UTF_8));
        BaiduHttpLogin.headerWrap(httpPost);

        return httpPost;
    }

    protected static CookieStore getCookies() {
        PreviewHandler previewHandler = new PreviewHandler();
        CookieStore sslCookies = JSONUtils.getObjectByJson(previewHandler.cookieService.takeOne().getCookie(), CookieEntity.class);
        return sslCookies;
    }
}
