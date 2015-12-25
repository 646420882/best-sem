package com.perfect.web.suport;

import com.perfect.commons.constants.AuthConstants;
import com.perfect.core.AppContext;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.dto.sys.SystemUserModuleDTO;
import com.perfect.service.AccountManageService;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by vbzer_000 on 2014/8/27.
 * 2014-12-2 refactor
 */
public class ContextInterceptor implements HandlerInterceptor, AuthConstants {

    private final boolean adminFlag[] = new boolean[1];

    @Resource
    private AccountManageService accountManageService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (Objects.isNull(request.getSession().getAttribute(USER_INFO))
                || Objects.isNull(request.getSession().getAttribute(USER_TOKEN))) {
            if (Objects.nonNull(request.getHeader("x-requested-with"))
                    && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
                response.addHeader("sessionStatus", "timeout");

                return false;
            }
        }

        String userName = WebUtils.getUserName(request);
        if (userName == null) {
            return false;
        }

        Long accoundId = WebUtils.getAccountId(request);
        if (accoundId != null && accoundId > 0) {
            AppContext.setUser(userName, accoundId, WebUtils.getModuleAccounts(request));
            return true;
        } else {
            AppContext.setUser(userName);
            SystemUserDTO systemUserDTO = (SystemUserDTO) request.getSession().getAttribute(USER_INFO);
            if (systemUserDTO == null) {
                return false;
            }
            if (systemUserDTO.getAccess() == 1) {
                adminFlag[0] = true;
                return true;
            } else {
                adminFlag[0] = false;
            }

            return handleRequest(request, userName);
        }

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

    private void setAccountOverview(HttpServletRequest request, ModelAndView modelAndView) {
        Long accountId = WebUtils.getAccountId(request);
        ModelMap modelMap = modelAndView.getModelMap();
        modelMap.put("currSystemUserName", WebUtils.getUserName(request));
        if (accountId == -1) {
            modelMap.put("accountBalance", 0);
            modelMap.put("accountBudget", 0);
            modelMap.put("remainderDays", 0);
            return;
        }

        Double data[] = getBalanceAndBudget(accountId);
        if (data[0] == null) {
            if (data[1] == null) {
                modelMap.put("accountBalance", 0);
                modelMap.put("accountBudget", 0);
                modelMap.put("remainderDays", 0);
            } else {
                modelMap.put("accountBalance", 0);
                modelMap.put("accountBudget", data[1]);
                modelMap.put("remainderDays", 0);
            }
        }
        if (data[0] != null) {
            if (data[1] != null) {
                if (data[0] == 0 || data[1] == 0) {
                    modelMap.put("remainderDays", 0);
                } else {
                    String vStr = Double.valueOf(data[0] / data[1]).toString();
                    modelMap.put("remainderDays", vStr.substring(0, vStr.indexOf(".")));
                }
                modelMap.put("accountBalance", data[0]);
                modelMap.put("accountBudget", data[1]);
            } else {
                modelMap.put("accountBalance", data[0]);
                modelMap.put("accountBudget", 0);
                modelMap.put("remainderDays", 0);
            }
        }
    }

    // 获取账户余额和账户余额
    private Double[] getBalanceAndBudget(Long accountId) {
        ModuleAccountInfoDTO accountInfo = accountManageService.getBaiduAccountInfoById(accountId);
        if (accountInfo == null) {
            return new Double[]{null, null};
        }

        Double balance = accountInfo.getBalance();
        Double yesterdayCost = accountInfo.getBudget();
        return new Double[]{balance, yesterdayCost};
    }

    private boolean handleRequest(HttpServletRequest request, String userName) {
        SystemUserDTO systemUserDTO = (SystemUserDTO) request.getSession().getAttribute(USER_INFO);
        List<SystemUserModuleDTO> userModuleDTOs = systemUserDTO.getSystemUserModules();

        for (SystemUserModuleDTO userModuleDTO : userModuleDTOs) {
            if (Objects.equals(AppContext.getModuleName(), userModuleDTO.getModuleName())) {
                List<ModuleAccountInfoDTO> moduleAccountInfoDTOs = userModuleDTO.getAccounts();
                WebUtils.setModuleAccounts(request, moduleAccountInfoDTOs);

                List<String> sysUserModuleMenus = userModuleDTO.getMenus()
                        .stream()
                        .map(menu -> menu + "," + SOUKE_MENU_INFO.get(menu))
                        .collect(Collectors.toList());
                request.getSession().setAttribute(USER_MENU_INFO, sysUserModuleMenus);

                if (moduleAccountInfoDTOs.size() == 1) {
                    ModuleAccountInfoDTO moduleAccountInfoDTO = moduleAccountInfoDTOs.get(0);
                    WebUtils.setAccountId(request, moduleAccountInfoDTO.getBaiduAccountId());

                    // 将模块帐号信息写入AppContext
                    AppContext.setUser(userName, moduleAccountInfoDTO.getBaiduAccountId(), moduleAccountInfoDTOs);
                } else {
                    for (ModuleAccountInfoDTO moduleAccountInfoDTO : moduleAccountInfoDTOs) {
                        if (moduleAccountInfoDTO.isDfault()) {
                            WebUtils.setAccountId(request, moduleAccountInfoDTO.getBaiduAccountId());
                            // 将模块帐号信息写入AppContext
                            AppContext.setUser(userName, moduleAccountInfoDTO.getBaiduAccountId(), moduleAccountInfoDTOs);
                            break;
                        }
                    }
                }

                return true;
            }
        }

        return false;
    }

    private boolean isAdmin() {
        return adminFlag[0];
    }
}
