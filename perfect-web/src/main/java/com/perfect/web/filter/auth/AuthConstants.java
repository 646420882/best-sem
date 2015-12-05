package com.perfect.web.filter.auth;

/**
 * Created on 2015-12-05.
 *
 * @author dolphineor
 */
public interface AuthConstants {

    String BASE_VERIFICATION_URL = "http://ucapi.best-ad.cn%s";

    // 用户认证登录中心
    String AUTHENTICATION_URL = "http://login.best-ad.cn";

    // 验证URL
    String USER_VERIFICATION_URL = String.format(BASE_VERIFICATION_URL, "/Users/GetUserByToken");

    // TODO 登出URL
    String USER_LOGINOUT_URL = String.format(BASE_VERIFICATION_URL, "/Users/OutLogin");

    // TODO Token定时刷新URL
    String SESSION_HEART_BEAT_URL = String.format(BASE_VERIFICATION_URL, "/Users/CheckToken");


    String SERVLET_CONTEXT_INIT_VALUE = "SERVLET_CONTEXT_INIT_VALUE";

    String USER_INFORMATION = "USER_INFORMATION";

    String TOKEN = "token";
}
