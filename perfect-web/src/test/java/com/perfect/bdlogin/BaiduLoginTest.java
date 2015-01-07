package com.perfect.bdlogin;

import com.perfect.commons.bdlogin.AbstractBaiduHttpClient;
import com.perfect.commons.bdlogin.BaiduHttpClient;
import com.perfect.commons.bdlogin.BaiduHttpLoginHandler;
import com.perfect.commons.bdlogin.CaptchaHandler;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by baizz on 2015-1-7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration(name = "parent", locations = "classpath:applicationContext.xml"),
        @ContextConfiguration(name = "child", locations = "classpath:applicationContext-mvc.xml")
})
public class BaiduLoginTest extends AbstractBaiduHttpClient {

    @Autowired
    private WebApplicationContext wac;
    protected MockMvc mockMvc;

    @Autowired
    private BaiduHttpLoginHandler baiduLoginHandler;

    public void setBaiduLoginHandler(@Qualifier("baiduLoginHandler") BaiduHttpLoginHandler baiduLoginHandler) {
        this.baiduLoginHandler = baiduLoginHandler;
    }

    @Autowired
    private CaptchaHandler captchaHandler;

    public void setCaptchaHandler(@Qualifier("captchaHandler") CaptchaHandler captchaHandler) {
        this.captchaHandler = captchaHandler;
    }

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    public void login() throws IOException {
        captchaHandler.handle(BaiduHttpLoginHandler.getBaiduLoginJSPath());
        String cookies = captchaHandler.getCaptchaCookies();

        Scanner sc = new Scanner(System.in);
        System.out.println("请输入验证码: ");
        String imagecode = sc.next();

        if (baiduLoginHandler.login("baidu-bjtthunbohui2134115", "Bjhunbohui7", imagecode, cookies)) {
            if (baiduLoginHandler.redirect(BaiduHttpLoginHandler.getRedirectUrl())) {
                System.out.println("======================success========================");
                System.out.println("castk: " + BaiduHttpLoginHandler.getCastk());
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

        HttpPost httpPost = new HttpPost(PREVIEW_URL);
        httpPost.addHeader("Host", PREVIEW_HOST);
        httpPost.addHeader("Cookie", _cookies.toString());
        httpPost.addHeader("Content-Type", CONTENT_TYPE);

        List<NameValuePair> postData = new ArrayList<>();
        postData.add(new BasicNameValuePair("params", "{\"device\":1,\"keyword\":\"北京婚博会免费门票\",\"area\":1,\"pageNo\":0}"));
        postData.add(new BasicNameValuePair("userid", userid));
        postData.add(new BasicNameValuePair("token", token));
        httpPost.setEntity(new UrlEncodedFormEntity(postData, StandardCharsets.UTF_8));
        BaiduHttpClient.headerWrap(httpPost);

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                System.out.println(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
        }

    }

}
