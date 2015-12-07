package com.perfect.core;

import com.perfect.account.BaseBaiduAccountInfoVO;

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

    private static final String CTX_USER = "CTX_USER";
    private static final String CTX_ACCOUNT = "CTX_ACCOUNT";

    private static Map<String, SessionObject> sessionMap = new HashMap<>();

    private static ContextLocal contextMap = new ContextLocal();
    private static Object accountId;


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

    public static void setUser(String username) {
        SessionObject so = new SessionObject();
        so.setUserName(username);
        contextMap.set(so);
    }


    public static void setUser(String username, Long accountId) {
        SessionObject so = new SessionObject();
        so.setUserName(username);
        so.setCurrentAccountId(accountId);
        contextMap.set(so);
    }

    public static void setUser(String username, Long accountId, List<BaseBaiduAccountInfoVO> baiduAccounts) {
        SessionObject so = new SessionObject();
        so.setUserName(username);
        so.setCurrentAccountId(accountId);
        so.setBaiduAccounts(baiduAccounts);
        contextMap.set(so);
    }

    public static String getUser() {
        if (contextMap.get() == null)
            return null;

        return contextMap.get().getUserName();
    }

    public static Long getAccountId() {
        SessionObject sessionObject = contextMap.get();

        if (sessionObject != null) {
            return sessionObject.getCurrentAccountId();
        } else {
            return -1L;
        }
    }

    public static List<BaseBaiduAccountInfoVO> getBaiduAccounts() {
        SessionObject sessionObject = contextMap.get();

        if (sessionObject != null) {
            if (sessionObject.getBaiduAccounts() == null)
                return Collections.emptyList();

            return sessionObject.getBaiduAccounts();
        } else {
            return Collections.emptyList();
        }
    }
}
