package com.perfect.admin.utils;

import com.perfect.core.AppContext;
import com.perfect.core.SystemRoleInfo;

/**
 * Created by yousheng on 15/12/18.
 */
public class SuperUserUtils {

    public static boolean isLoginSuper() {
        SystemRoleInfo systemRoleInfo = AppContext.getSystemRoleInfo();

        if (systemRoleInfo == null) {
            return false;
        }
        return systemRoleInfo.isSuper();
    }
}
