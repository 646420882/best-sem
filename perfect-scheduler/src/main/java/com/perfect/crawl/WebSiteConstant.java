package com.perfect.crawl;

/**
 * Created by baizz on 2014-11-5.
 */
public enum WebSiteConstant {

    TAOBAO(101, "s.taobao.com"), AMAZON(102, "www.amazon.cn"), DANGDANG(103, "");

    private final int code;
    private final String domain;

    private WebSiteConstant(int code, String domain) {
        this.code = code;
        this.domain = domain;
    }

    public int getCode() {
        return code;
    }

    public static String getDomain(int code) {
        for (WebSiteConstant siteEnum : WebSiteConstant.values()) {
            if (siteEnum.code == code) {
                return siteEnum.domain;
            }
        }
        return null;
    }

}
