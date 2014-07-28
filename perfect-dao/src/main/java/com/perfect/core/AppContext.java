package com.perfect.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yousheng on 2014/7/24.
 *
 * @author yousheng
 */
public class AppContext {

    public static final String CTX_USER = "CTX_USER";

    private static ContextLocal contextMap = new ContextLocal();


    public static Map<String, Object> get() {
        return contextMap.get();
    }


    public static void setUser(Object user) {
        Map<String, Object> threadMap = contextMap.get();
        if (threadMap == null) {
            threadMap = new HashMap<>();
        }

        threadMap.put(CTX_USER, user);

        contextMap.set(threadMap);
    }

    public static Object getUser() {
        Map<String, Object> threadMap = contextMap.get();
        if (threadMap != null) {
            return threadMap.get(CTX_USER);
        }

        return null;
    }
}
