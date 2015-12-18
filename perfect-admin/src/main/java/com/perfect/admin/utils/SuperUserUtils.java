package com.perfect.admin.utils;

import com.perfect.core.AppContext;
import com.perfect.core.SystemUserInfo;

/**
 * Created by yousheng on 15/12/18.
 */
public class SuperUserUtils {

    public static boolean isLoginSuper() {
        SystemUserInfo systemUserInfo = AppContext.getSystemUserInfo();

        if (systemUserInfo == null) {
            return false;
        }
        return systemUserInfo.isSuper();
    }
}
