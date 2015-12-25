package com.perfect.commons.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2015-12-17.
 *
 * @author dolphineor
 */
public interface AuthConstants {

    String LOCATION = "Location";

    String HOST = "host";

    String USER_TOKEN = "SOUKE_USER_TOKEN";

    String USER_LOGIN_URL = "http://localhost:8088/login";

    String USER_LOGIN_REDIRECT_URL = USER_LOGIN_URL + "?url=%s";

    String USER_PRE_LOGIN_VISIT_URL = "SOUKE_USER_PRE_LOGIN_VISIT_URL";

    String USER_INFO = "SOUKE_USER_INFORMATION";

    String USER_MENU_INFO = "SOUKE_USER_MENU_INFORMATION";

    String MODULE_ACCOUNT_INFO = "SOUKE_USER_MODULE_ACCOUNT_INFORMATION";

    Map<String, String> SOUKE_MENU_INFO = new HashMap<String, String>() {{
        put("账户全景", "/");
        put("推广助手", "/assistant/index");
        put("智能结构", "/keyword_group");
        put("智能竞价", "/bidding/index");
        put("数据报告", "/reportIndex");
    }};

}
