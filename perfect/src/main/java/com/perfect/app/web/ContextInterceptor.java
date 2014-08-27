package com.perfect.app.web;

import com.perfect.core.AppContext;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.SystemUserEntity;
import com.perfect.service.SystemUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by vbzer_000 on 2014/8/27.
 */
public class ContextInterceptor implements HandlerInterceptor {

    @Resource
    SystemUserService systemUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

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
            SystemUserEntity entity = systemUserService.getSystemUser(userName);
            if (entity == null) {
                return false;
            }

            for (BaiduAccountInfoEntity infoEntity : entity.getBaiduAccountInfoEntities()) {
                if (infoEntity.isDfault()) {
                    WebUtils.setAccountId(request, infoEntity.getId());
                    AppContext.setUser(userName, infoEntity.getId());
                    break;
                }
            }
        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
