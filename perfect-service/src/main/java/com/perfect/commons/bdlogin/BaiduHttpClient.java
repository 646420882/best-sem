package com.perfect.commons.bdlogin;

import org.apache.http.HttpRequest;
import org.apache.http.impl.client.CloseableHttpClient;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by baizz on 2014-12-16.
 */
public interface BaiduHttpClient {

    public static final String captchaURL = "http://cas.baidu.com/?action=image2&appid=3&key=";

    public static final String baiduLoginURL = "https://cas.baidu.com?action=login";

    public static final String baiduPreviewURL = "http://fengchao.baidu.com/nirvana/request.ajax?path=nirvana/GET/Live";

    public static final String baiduPreviewHost = "fengchao.baidu.com";

    public static final String contentType = "application/x-www-form-urlencoded; charset=UTF-8";

    public static final String userAgent = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:34.0) Gecko/20100101 Firefox/34.0";

    public static final String Accept = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";

    public static final String acceptLanguage = "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3";

    public static final String acceptEncoding = "gzip, deflate";

    public static final Set<String> set = new HashSet<String>() {{
        addAll(Arrays.asList("CASSSID", "GBIZSSID", "GIMGSSID", "LOGINAID", "LOGINUID", "__cas__id__", "__cas__st__", "bdsfuid"));
    }};

    public static final String __cas__id__3 = "__cas__id__3";

    public static final String __cas__st__3 = "__cas__st__3";

    CloseableHttpClient createSSLClientDefault(boolean isSSL);

    default void headerWrap(HttpRequest request) {
        request.setHeader("User-Agent", userAgent);
        request.setHeader("Accept", Accept);
        request.setHeader("Accept-language", acceptLanguage);
        request.setHeader("Accept-Encoding", acceptEncoding);
    }

}
