package com.perfect.core;

import com.perfect.commons.constants.SystemNameConstant;
import com.perfect.dto.sys.ModuleAccountInfoDTO;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yousheng on 2014/7/24.
 *
 * @author yousheng
 */
public class AppContext {

    public static final String CTX_USER = "CTX_USER";
    public static final String CTX_ACCOUNT = "CTX_ACC";

    private static Map<String, SessionObject> sessionMap = new HashMap<>();

    private static ContextLocal contextMap = new ContextLocal();

    private static ThreadLocal<SystemUserInfo> systemUserInfoThreadLocal = new ThreadLocal<>();

    private static ThreadLocal<String> remoteIpThreadLocal = new ThreadLocal<>();

    private static ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal<>();
    private static Object accountId;

    public static void setSystemUserInfo(SystemUserInfo systemUserInfo) {
        systemUserInfoThreadLocal.set(systemUserInfo);
    }

    public static SystemUserInfo getSystemUserInfo() {
        return systemUserInfoThreadLocal.get();
    }

    public static boolean isAdminSystemLogin() {
        return systemUserInfoThreadLocal.get() != null;
    }

    public static SessionObject get() {
        return contextMap.get();
    }

    public static boolean contains(String sessionId) {
        return sessionMap.containsKey(sessionId);
    }


//    public static void setSessionObject(String sessionId, SessionObject so) {
//        if (sessionId == null || so == null) {
//            return;
//        }
//        sessionMap.put(sessionId, so);
//
//        contextMap.set(so);
//    }

    public static void remove(String id) {
        sessionMap.remove(id);
        contextMap.set(null);
    }

    public static SessionObject getObject(String id) {
        return sessionMap.get(id);
    }

//    public static void setLocal(String sessionId) {
//        SessionObject so = sessionMap.get(sessionId);
//        contextMap.set(so);
//    }

    public static void setModuleId(String moduleId) {
        SessionObject so = new SessionObject();
        so.setModuleId(moduleId);
        contextMap.set(so);
    }

    public static void setUser(String userName) {
        SessionObject so = new SessionObject();
        so.setUserName(userName);
        contextMap.set(so);
    }


    public static void setUser(String userName, Long accountId) {
        SessionObject so = new SessionObject();
        so.setUserName(userName);
        so.setAccountId(accountId);
        contextMap.set(so);
    }

    public static void setUser(String userName, Long accountId, List<ModuleAccountInfoDTO> moduleAccountInfoDTOs) {
        SessionObject so = new SessionObject();
        so.setUserName(userName);
        so.setAccountId(accountId);
        so.setModuleAccountInfoDTOs(moduleAccountInfoDTOs);
        contextMap.set(so);
    }

    public static String getModuleId() {
        SessionObject so = contextMap.get();

        if (so != null) {
            return so.getModuleId();
        } else {
            return null;
        }
    }

    public static String getModuleName() {
        return SystemNameConstant.SOUKE_SYSTEM_NAME;
    }

    public static String getUser() {
        SessionObject so = contextMap.get();

        if (so != null) {
            return so.getUserName();
        } else {
            return null;
        }
    }

    public static Long getAccountId() {
        SessionObject so = contextMap.get();

        if (so != null) {
            return so.getAccountId();
        } else {
            return -1L;
        }
    }

    public static String getRemote() {
        return remoteIpThreadLocal.get();
    }

    public static void setRemote(String remote) {
        remoteIpThreadLocal.set(remote);
    }

    public static List<ModuleAccountInfoDTO> getModuleAccounts() {
        SessionObject so = contextMap.get();

        if (so != null) {
            return so.getModuleAccountInfoDTOs();
        } else {
            return Collections.emptyList();
        }
    }

    public static UserInfo getUserInfo() {
        return userInfoThreadLocal.get();
    }

    public static void setUserInfo(UserInfo userInfo) {
        userInfoThreadLocal.set(userInfo);
    }
}
