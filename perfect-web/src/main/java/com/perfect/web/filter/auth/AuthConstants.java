package com.perfect.web.filter.auth;

/**
 * Created on 2015-12-05.
 *
 * @author dolphineor
 */
public interface AuthConstants {

    String BASE_VERIFICATION_URL = "http://ucapi.best-ad.cn%s";

    // 登录URL
    String USER_LOGIN_URL = "http://login.best-ad.cn";

    // 登出URL
    String USER_LOGINOUT_URL = String.format(BASE_VERIFICATION_URL, "/Users/OutLogin");

    // 获取用户信息URL
    String USER_VERIFICATION_URL = String.format(BASE_VERIFICATION_URL, "/Users/GetUserByToken");

    String SESSION_HEART_BEAT_URL = String.format(BASE_VERIFICATION_URL, "/Users/CheckToken");


    String SERVLET_CONTEXT_INIT_VALUE = "SERVLET_CONTEXT_INIT_VALUE";

    String USER_INFORMATION = "USER_INFORMATION";

    String KEY_BAIDU_ACCOUNT_ID = "BEST_SEM_BAIDU_ACCOUNT_ID";

    String KEY_BAIDU_ACCOUNT_LIST = "BEST_SEM_BAIDU_ACCOUNT_LIST";

    String TOKEN = "token";
}
