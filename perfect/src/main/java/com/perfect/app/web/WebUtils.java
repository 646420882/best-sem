package com.perfect.app.web;

import com.perfect.core.AppContext;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created by vbzer_000 on 2014/8/27.
 */
public class WebUtils extends org.springframework.web.util.WebUtils {

    public static final String KEY_ACCOUNT = "_accountId";


    public static String getUserName(HttpServletRequest request) {
        Principal userPrincipal = request.getUserPrincipal();
        return (userPrincipal != null ? userPrincipal.getName() : null);
    }


    public static void setAccountId(HttpServletRequest request, Long accountId) {
        request.getSession().setAttribute(KEY_ACCOUNT, accountId);
    }

    public static Long getAccountId(HttpServletRequest request) {

        Object accid = request.getSession().getAttribute(KEY_ACCOUNT);

        return (Long) ((accid == null) ? -1l : accid);
    }


    public static void setContext(HttpServletRequest request) {
        String userName = getUserName(request);
        Long accountId = getAccountId(request);

        AppContext.setUser(userName, accountId);
    }
}
