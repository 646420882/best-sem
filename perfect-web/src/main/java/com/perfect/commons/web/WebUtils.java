package com.perfect.commons.web;

import com.perfect.core.AppContext;
import com.perfect.entity.BaiduAccountInfoEntity;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

/**
 * Created by vbzer_000 on 2014/8/27.
 */
public class WebUtils extends org.springframework.web.util.WebUtils {

    public static final String KEY_ACCOUNT = "_accountId";
    public static final String KEY_ACCOUNTLIST = "_acclist";


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

    public static void setAccountList(HttpServletRequest request, List<BaiduAccountInfoEntity> baiduAccountInfoEntities) {
        request.getSession().setAttribute(KEY_ACCOUNTLIST, baiduAccountInfoEntities);
    }

    public static List<BaiduAccountInfoEntity> getAccountList(HttpServletRequest request) {
        Object list = request.getSession().getAttribute(KEY_ACCOUNTLIST);

        if(list == null){
            return null;
        }else{
            return (List<BaiduAccountInfoEntity>)list;
        }
    }


    public static ModelAndView getJsonView(){
        return new ModelAndView(new MappingJackson2JsonView());
    }
}
