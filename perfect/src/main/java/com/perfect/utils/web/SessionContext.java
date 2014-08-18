package com.perfect.utils.web;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * Created by XiaoWei on 2014/8/18.
 */
public class SessionContext {
    private static SessionContext instence;
    private HashMap<String, HttpSession> sessionMap;

    private SessionContext() {
        sessionMap = new HashMap<>();
    }

    public static synchronized SessionContext getInstence() {
        if (instence!=null){
            instence=new SessionContext();
        }
        return instence;
    }

    public synchronized void setSession(HttpSession session){
        if (session!=null){
            sessionMap.put(session.getId(),session);
        }
    }

    public synchronized  void delSession(HttpSession session){
        if (session!=null){
            sessionMap.remove(session.getId());
        }
    }

    public synchronized HttpSession getSession(String sessionId){
        if (sessionId==null||sessionId.equals(null))
            return null;
        return sessionMap.get(sessionId);
    }
}
