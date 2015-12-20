package com.perfect.web.suport;

import com.perfect.commons.constants.AuthConstants;
import com.perfect.core.AppContext;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.dto.sys.SystemUserDTO;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by vbzer_000 on 2014/8/27.
 */
public class WebUtils extends org.springframework.web.util.WebUtils implements AuthConstants {

    public static final String KEY_ACCOUNT = "_accountId";
    public static final String KEY_ACCOUNTLIST = "_acclist";


    public static void setModuleId(HttpServletRequest request, String moduleId) {
        request.getSession().setAttribute(MODULE, moduleId);
    }

    public static String getModuleId(HttpServletRequest request) {
        Object obj = request.getSession().getAttribute(MODULE);
        if (Objects.isNull(obj))
            return null;

        return obj.toString();
    }

    public static String getUserName(HttpServletRequest request) {
        Object userInfo = request.getSession().getAttribute(USER_INFORMATION);
        if (Objects.isNull(userInfo))
            return null;

        return ((SystemUserDTO) userInfo).getUserName();
    }


    public static void setAccountId(HttpServletRequest request, Long accountId) {
        request.getSession().setAttribute(KEY_ACCOUNT, accountId);
    }

    public static Long getAccountId(HttpServletRequest request) {
        Object accid = request.getSession().getAttribute(KEY_ACCOUNT);

        return (Long) ((accid == null) ? -1L : accid);
    }

    public static void setModuleAccounts(HttpServletRequest request, List<ModuleAccountInfoDTO> moduleAccountInfoDTOs) {
        request.getSession().setAttribute(MODULE_ACCOUNT_INFORMATION, moduleAccountInfoDTOs);
    }

    @SuppressWarnings("unchecked")
    public static List<ModuleAccountInfoDTO> getModuleAccounts(HttpServletRequest request) {
        Object moduleAccountInfo = request.getSession().getAttribute(MODULE_ACCOUNT_INFORMATION);
        if (Objects.isNull(moduleAccountInfo))
            return Collections.emptyList();

        return ((List<ModuleAccountInfoDTO>) moduleAccountInfo);
    }

    public static void setContext(HttpServletRequest request) {
        String userName = getUserName(request);
        Long accountId = getAccountId(request);

        AppContext.setUser(userName, accountId);
    }

    public static void setAccountList(HttpServletRequest request, List<BaiduAccountInfoDTO> baiduAccountInfoDTOList) {
        request.getSession().setAttribute(KEY_ACCOUNTLIST, baiduAccountInfoDTOList);
    }

    @SuppressWarnings("unchecked")
    public static List<BaiduAccountInfoDTO> getAccountList(HttpServletRequest request) {
        Object list = request.getSession().getAttribute(KEY_ACCOUNTLIST);

        if (list == null) {
            return null;
        } else {
            return (List<BaiduAccountInfoDTO>) list;
        }
    }


    public static ModelAndView getJsonView() {
        return new ModelAndView(new MappingJackson2JsonView());
    }
}
