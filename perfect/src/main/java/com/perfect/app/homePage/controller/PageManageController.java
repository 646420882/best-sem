package com.perfect.app.homePage.controller;


import com.perfect.app.accountCenter.dao.AccountManageDAO;
import com.perfect.app.homePage.service.CustomUserDetailsService;
import com.perfect.entity.BaiduAccountInfoEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;


/**
 * Created by baizz on 2014-6-23.
 */
@RestController
@Scope("prototype")
public class PageManageController {


    private static String currLoginUserName;

    static {
        currLoginUserName = (currLoginUserName == null) ? CustomUserDetailsService.getUserName() : currLoginUserName;
    }

    @Resource(name = "accountManageDAO")
    private AccountManageDAO<BaiduAccountInfoEntity> accountManageDAO;

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
        if (error == true) {
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
    public ModelAndView getHomePage(ModelMap modelMap) {
        modelMap.put("currSystemUserName", currLoginUserName);
        return new ModelAndView("homePage/home");
    }

    /**
     * 便捷管理页面
     *
     * @return
     */
    @RequestMapping(value = "/main/convenienceManage", method = RequestMethod.GET)
    public ModelAndView getConvenienceManagePage(ModelMap modelMap) {
        modelMap.put("currSystemUserName", currLoginUserName);
        return new ModelAndView("convenienceManage/convenienceManage");
    }

    /**
     * 推广管理页面
     *
     * @return
     */
    @RequestMapping(value = "/main/spreadManage", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getSpreadManagePage(@RequestParam(value = "baiduUserId") Long baiduUserId,
                                            ModelMap modelMap) {
        BaiduAccountInfoEntity entity = accountManageDAO.findByBaiduUserId(baiduUserId);
        modelMap.put("currSystemUserName", currLoginUserName);
        modelMap.put("baiduAccountId", baiduUserId);
        modelMap.put("baiduAccountName", entity.getBaiduUserName());
        return new ModelAndView("spreadManage/spreadManage");
    }

}
