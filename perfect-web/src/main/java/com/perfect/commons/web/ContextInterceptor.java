package com.perfect.commons.web;

import com.perfect.core.AppContext;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.service.AccountManageService;
import com.perfect.service.SystemUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by vbzer_000 on 2014/8/27.
 * 2014-12-2 refactor
 */
public class ContextInterceptor implements HandlerInterceptor {

    private boolean adminFlag;

    @Resource
    private SystemUserService systemUserService;

    @Resource
    private AccountManageService accountManageService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /**
         * 在经过Spring Security认证之后, Security会把一个SecurityContextImpl对象存储到session中, 这个对象中存有当前用户的信息
         * ((SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getName()
         */
        if (request.getSession().getAttribute("SPRING_SECURITY_CONTEXT") == null) {//判断session里是否有用户信息
            if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
                response.addHeader("sessionStatus", "timeout");
                return false;
            }

        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return true;
        }

        String userName = WebUtils.getUserName(request);
        if (userName == null) {
            return false;
        }

        Long accoundId = WebUtils.getAccountId(request);
        if (accoundId != null && accoundId > 0) {
            AppContext.setUser(userName, accoundId);
            return true;
        } else {

            AppContext.setUser(userName);
            SystemUserDTO systemUserDTO = systemUserService.getSystemUser(userName);
            if (systemUserDTO == null) {
                return false;
            }
            int size = systemUserDTO.getBaiduAccounts().size();
            if (systemUserDTO.getAccess() == 1) {
                adminFlag = true;
                return true;
            } else {
                adminFlag = false;
            }

//            if (systemUserDTO.getAccess() == 2 && size == 0) {
////                if ("/configuration/add".equals(request.getServletPath())) {
////                    return true;
////                }
//
//                if ("/configuration/save".equals(request.getServletPath())) {
//                    return true;
//                }
//
//
////                String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
////                response.sendRedirect("redirect:/configuration/add");
//                return false;
//            }

            if (systemUserDTO.getBaiduAccounts().size() == 1) {
                BaiduAccountInfoDTO infoDTO = systemUserDTO.getBaiduAccounts().get(0);
                WebUtils.setAccountId(request, infoDTO.getId());
                AppContext.setUser(userName, infoDTO.getId());
                return true;
            }

            for (BaiduAccountInfoDTO infoDTO : systemUserDTO.getBaiduAccounts()) {
                if (infoDTO.isDfault()) {
                    WebUtils.setAccountId(request, infoDTO.getId());
                    AppContext.setUser(userName, infoDTO.getId());
                    break;
                }
            }
        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null || (modelAndView.getView() != null && modelAndView.getView() instanceof MappingJackson2JsonView)) {
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return;

        if (isAdmin()) return;


        Long accountId = WebUtils.getAccountId(request);
        ModelMap modelMap = modelAndView.getModelMap();
        modelMap.put("currSystemUserName", WebUtils.getUserName(request));
        if (accountId == -1) {
            modelMap.put("accountBalance", 0);
            modelMap.put("accountBudget", 0);
            modelMap.put("remainderDays", 0);
            return;
        }

        Double datas[] = getBalanceAndBudget(accountId);

        if (datas[0] == null) {
            if (datas[1] == null) {
                modelMap.put("accountBalance", 0);
                modelMap.put("accountBudget", 0);
                modelMap.put("remainderDays", 0);
            } else {
                modelMap.put("accountBalance", 0);
                modelMap.put("accountBudget", datas[1]);
                modelMap.put("remainderDays", 0);
            }
        }
        if (datas[0] != null) {
            if (datas[1] != null) {
                if (datas[0] == 0 || datas[1] == 0) {
                    modelMap.put("remainderDays", 0);
                } else {
                    String vStr = Double.valueOf(datas[0] / datas[1]).toString();
                    modelMap.put("remainderDays", vStr.substring(0, vStr.indexOf(".")));
                }
                modelMap.put("accountBalance", datas[0]);
                modelMap.put("accountBudget", datas[1]);
            } else {
                modelMap.put("accountBalance", datas[0]);
                modelMap.put("accountBudget", 0);
                modelMap.put("remainderDays", 0);
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    //获取账户余额和账户余额
    private Double[] getBalanceAndBudget(Long accountId) {
        BaiduAccountInfoDTO accountInfo = accountManageService.getBaiduAccountInfoById(accountId);
        if (accountInfo == null) {
            return new Double[]{null, null};
        }
        Double balance = accountInfo.getBalance();
        Double yesterdayCost = accountInfo.getBudget();
        return new Double[]{balance, yesterdayCost};
    }

    boolean isAdmin() {
        return adminFlag;
    }
}
