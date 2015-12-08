package com.perfect.commons.constants;

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

    // Token刷新URL
    String TOKEN_HEART_BEAT_URL = String.format(BASE_VERIFICATION_URL, "/Users/CheckToken");


    String USER_INFORMATION = "BEST_SEM_USER_INFORMATION";

    String MENU_PERMISSION = "BEST_SEM_MENU_PERMISSION";

    String KEY_CURRENT_BAIDU_ACCOUNT_ID = "BEST_SEM_CURRENT_BAIDU_ACCOUNT_ID";

    String KEY_CURRENT_BAIDU_ACCOUNT = "BEST_SEM_CURRENT_BAIDU_ACCOUNT";

    String TOKEN = "25fb53fc0s03f407b8c45b399d05e2e";
}
