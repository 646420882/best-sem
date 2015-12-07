package com.perfect.web.support;

import com.perfect.core.AppContext;
import com.perfect.account.BaseBaiduAccountInfoVO;
import com.perfect.account.SystemUserInfoVO;
import com.perfect.web.filter.auth.AuthConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by vbzer_000 on 2014/8/27.
 */
public class WebUtils extends org.springframework.web.util.WebUtils implements AuthConstants {

    public static String getUserName(HttpServletRequest request) {
        Object userInfo = request.getSession().getAttribute(USER_INFORMATION);
        if (Objects.isNull(userInfo))
            return null;

        return ((SystemUserInfoVO) userInfo).getUsername();
    }


    public static void setAccountId(HttpServletRequest request, Long accountId) {
        request.getSession().setAttribute(KEY_BAIDU_ACCOUNT_ID, accountId);
    }

    public static Long getAccountId(HttpServletRequest request) {
        Object accountId = request.getSession().getAttribute(KEY_BAIDU_ACCOUNT_ID);

        return (Long) ((accountId == null) ? -1L : accountId);
    }


    public static void setContext(HttpServletRequest request) {
        String userName = getUserName(request);
        Long accountId = getAccountId(request);

        AppContext.setUser(userName, accountId);
    }

    public static void setBaiduAccounts(HttpServletRequest request, List<BaseBaiduAccountInfoVO> baiduAccounts) {
        request.getSession().setAttribute(KEY_BAIDU_ACCOUNT_LIST, baiduAccounts);
    }

    @SuppressWarnings("unchecked")
    public static List<BaseBaiduAccountInfoVO> getBaiduAccounts(HttpServletRequest request) {
        Object list = request.getSession().getAttribute(KEY_BAIDU_ACCOUNT_LIST);

        if (Objects.isNull(list)) {
            return Collections.<BaseBaiduAccountInfoVO>emptyList();
        } else {
            return (List<BaseBaiduAccountInfoVO>) list;
        }
    }

}
