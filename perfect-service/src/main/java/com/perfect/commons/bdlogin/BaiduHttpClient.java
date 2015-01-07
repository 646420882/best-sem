package com.perfect.commons.bdlogin;

import org.apache.http.HttpRequest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by baizz on 2014-12-16.
 * refactor 2015-1-7
 */
public interface BaiduHttpClient {

    public static final String CAPTCHA_URL = "http://cas.baidu.com/?action=image2&appid=3&key=";

    public static final String LOGIN_URL = "https://cas.baidu.com?action=login";

    public static final String PREVIEW_URL = "http://fengchao.baidu.com/nirvana/request.ajax?path=nirvana/GET/Live";

    public static final String PREVIEW_HOST = "fengchao.baidu.com";

    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded; charset=UTF-8";

    public static final String USER_AGENT = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:34.0) Gecko/20100101 Firefox/34.0";

    public static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";

    public static final String ACCEPT_LANGUAGE = "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3";

    public static final String ACCEPT_ENCODING = "gzip, deflate";

    public static final Set<String> COOKIE_SET = new HashSet<String>() {{
        addAll(Arrays.asList("CASSSID", "GBIZSSID", "GIMGSSID", "LOGINAID", "LOGINUID", "__cas__id__", "__cas__st__", "bdsfuid"));
    }};

    public static final String __cas__id__3 = "__cas__id__3";

    public static final String __cas__st__3 = "__cas__st__3";

    public static final int MAX_TOTAL = 50;

    public static final int MAX_PER_ROUTE = 20;


    public static void headerWrap(HttpRequest request) {
        request.setHeader("User-Agent", USER_AGENT);
        request.setHeader("Accept", ACCEPT);
        request.setHeader("Accept-language", ACCEPT_LANGUAGE);
        request.setHeader("Accept-Encoding", ACCEPT_ENCODING);
    }

}