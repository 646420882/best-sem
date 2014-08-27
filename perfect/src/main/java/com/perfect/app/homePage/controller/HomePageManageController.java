package com.perfect.app.homePage.controller;

import com.perfect.app.web.WebUtils;
import com.perfect.core.AppContext;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.SystemUserEntity;
import com.perfect.service.SystemUserService;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by baizz on 2014-6-23.
 */
@RestController
@Scope("prototype")
public class HomePageManageController {
    @Resource
    private SystemUserService systemUserService;

    /**
     * 登录页面
     *
     * @param error
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getLoginPage(@RequestParam(value = "error", required = false) boolean error,
                                     ModelMap model) {
        if (error) {
            model.put("error", "invalid userName or password!");
        } else {
            model.put("error", "");
        }
        return new ModelAndView("homePage/login", model);
    }

    /**
     * 登出
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView logout() {
        return null;
    }

    /**
     * 登录成功, 跳转至首页
     *
     * @return
     */
    @RequestMapping(value = "/home", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getHomePage(HttpServletRequest request, ModelMap modelMap) {
        String userName = WebUtils.getUserName(request);
        SystemUserEntity entity = systemUserService.getSystemUser(userName);
        if (entity == null) {
            return new ModelAndView("redirect:/logout");
        }

        modelMap.put("currSystemUserName", userName);
        modelMap.put("accountList", entity.getBaiduAccountInfoEntities());
        return new ModelAndView("homePage/home");
    }

    /**
     * 智能分组页面
     *
     * @return
     */
    @RequestMapping(value = "/keyword_group", method = RequestMethod.GET)
    public ModelAndView getKeywordGroupPage() {
        return new ModelAndView("keywordGroup/keyword");
    }

}
