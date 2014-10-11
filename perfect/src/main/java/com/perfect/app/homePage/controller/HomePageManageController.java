package com.perfect.app.homePage.controller;

import com.perfect.app.homePage.service.CustomUserDetailsService;
import com.perfect.app.web.WebUtils;
import com.perfect.entity.SystemUserEntity;
import com.perfect.service.AccountRegisterService;
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
import javax.servlet.http.HttpServletResponse;

/**
 * Created by baizz on 2014-6-23.
 */
@RestController
@Scope("prototype")
public class HomePageManageController {

    @Resource
    private SystemUserService systemUserService;
    @Resource
    private AccountRegisterService accountRegisterService;

    /**
     * 登陆页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getLoginPage(HttpServletRequest request,
                                     ModelMap model,
                                     @RequestParam(value = "error", required = false) boolean error) {
        if (error) {
            int badCredentialsNum = CustomUserDetailsService.getPasswdBadCredentialsNum();
            if (CustomUserDetailsService.isUsernameNotFound())
                model.put("invalidUserName", "用户名不对");
            else if (badCredentialsNum > 0) {
                if (badCredentialsNum == 3)
                    model.put("invalidPassword", "账户已被锁定");
                else
                    model.put("invalidPassword", "密码不对");
            }
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

    /**
     * 注册页面
     *
     * @return
     */
    @RequestMapping(value = "/register/page", method = RequestMethod.GET)
    public ModelAndView register(ModelMap model) {
        return new ModelAndView("homePage/pageBlock/register", model);
    }

    /**
     * 注册帐号
     *
     * @return
     */
    @RequestMapping(value = "/register/add", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView registerData(ModelMap model, HttpServletResponse response, HttpServletRequest request,
                                     @RequestParam(value = "username", required = false) String account,
                                     @RequestParam(value = "password", required = false) String pwd,
                                     @RequestParam(value = "companyname", required = false) String company) {
        if (account == null || "".equals(account)) {
            model.addAttribute("state", -1);
            return new ModelAndView("homePage/pageBlock/register", model);
        }
        if (pwd == null || "".equals(pwd)) {
            model.addAttribute("state", -1);
            return new ModelAndView("homePage/pageBlock/register", model);
        }
        if (company == null || "".equals(company)) {
            model.addAttribute("state", -1);
            return new ModelAndView("homePage/pageBlock/register", model);
        }

        int flag = accountRegisterService.addAccount(account, pwd, company);
        model.addAttribute("state", flag);
        return new ModelAndView("homePage/pageBlock/register", model);
    }
}
