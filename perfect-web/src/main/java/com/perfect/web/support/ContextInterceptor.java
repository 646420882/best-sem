package com.perfect.web.support;

import com.perfect.account.BaseBaiduAccountInfoVO;
import com.perfect.account.SystemUserInfoVO;
import com.perfect.api.baidu.BaiduApiService;
import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.autosdk.sms.v3.AccountInfoType;
import com.perfect.commons.constants.AuthConstants;
import com.perfect.core.AppContext;
import com.perfect.web.auth.TokenHeartBeat;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static com.perfect.web.support.ServletContextUtils.checkStaticResourcesRequest;

/**
 * Created by vbzer_000 on 2014/8/27.
 * 2014-12-2 refactor
 */
public class ContextInterceptor implements HandlerInterceptor, AuthConstants {

    private final boolean[] adminFlag = new boolean[1];

    private final AccountInfoType[] baiduAccountInfo = new AccountInfoType[1];


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // 判断Session里是否有用户信息
//        if (request.getSession().getAttribute(USER_INFORMATION) == null) {
//            // 用于判断AJAX请求出现的Session超时
//            if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
//                response.addHeader("sessionStatus", "timeout");
//                return false;
//            }
//
//        }

        String username = WebUtils.getUserName(request);
        if (username == null) {
            return false;
        }

        Long accountId = WebUtils.getAccountId(request);
        if (accountId != null && accountId > 0) {
            Object systemUser = request.getSession().getAttribute(USER_INFORMATION);

            if (Objects.nonNull(systemUser))
                AppContext.setUser(username, accountId, ((SystemUserInfoVO) systemUser).getBaiduAccounts());
            else
                AppContext.setUser(username, accountId);

            return true;
        } else {
            handleRequest(username, request);
        }

        if (!checkStaticResourcesRequest(request)) {
            TokenHeartBeat.refreshToken(request);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null || (modelAndView.getView() != null && modelAndView.getView() instanceof MappingJackson2JsonView)) {
            return;
        }

        if (isAdmin()) return;

        setAccountOverview(request, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private void handleRequest(String username, HttpServletRequest request) {
        if (Objects.nonNull(request.getSession().getAttribute(USER_INFORMATION))) {
            adminFlag[0] = false;
            SystemUserInfoVO userInfo = (SystemUserInfoVO) request.getSession().getAttribute(USER_INFORMATION);

            for (BaseBaiduAccountInfoVO baseBaiduAccountInfoVO : userInfo.getBaiduAccounts()) {
                if (baseBaiduAccountInfoVO.isDefault()) {
                    BaiduApiService apiService = new BaiduApiService(BaiduServiceSupport.getCommonService(
                            baseBaiduAccountInfoVO.getAccountName(),
                            baseBaiduAccountInfoVO.getPassword(),
                            baseBaiduAccountInfoVO.getToken())
                    );

                    AccountInfoType accountInfoType = apiService.getAccountInfo();
                    if (Objects.isNull(accountInfoType)) {
                        AccountInfoType o = new AccountInfoType();
                        o.setUserid(-1L);
                        o.setBalance(0D);
                        o.setBudget(-1D);
                        baiduAccountInfo[0] = o;
                        WebUtils.setCurrentBaiduAccount(request, null);
                    } else {
                        baiduAccountInfo[0] = accountInfoType;
                        WebUtils.setCurrentBaiduAccount(request, baseBaiduAccountInfoVO);
                    }
                    WebUtils.setAccountId(request, baiduAccountInfo[0].getUserid());

                    break;
                }
            }

            AppContext.setUser(username, baiduAccountInfo[0].getUserid(), userInfo.getBaiduAccounts());
        } else {
            adminFlag[0] = true;
            AppContext.setUser(username);
        }
    }

    private void setAccountOverview(HttpServletRequest request, ModelAndView modelAndView) {
        ModelMap modelMap = modelAndView.getModelMap();
        modelMap.put("userImageUrl", ((SystemUserInfoVO) request.getSession().getAttribute(USER_INFORMATION)).getImageUrl());
        modelMap.put("currSystemUserName", WebUtils.getUserName(request));
        modelMap.put("accountBalance", baiduAccountInfo[0].getBalance());
        modelMap.put("accountBudget", baiduAccountInfo[0].getBudget());

        String vStr = Double.valueOf(baiduAccountInfo[0].getBalance() / baiduAccountInfo[0].getBudget()).toString();
        modelMap.put("remainderDays", vStr.substring(0, vStr.indexOf(".")));
    }

    private boolean isAdmin() {
        return adminFlag[0];
    }
}
