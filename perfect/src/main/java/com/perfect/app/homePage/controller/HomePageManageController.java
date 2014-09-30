package com.perfect.app.homePage.controller;

import com.perfect.app.web.WebUtils;
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

    private static final String usernameNotFound = "org.springframework.security.core.userdetails.UsernameNotFoundException";
    private static final String badCredentials = "org.springframework.security.authentication.BadCredentialsException";

    @Resource
    private SystemUserService systemUserService;

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
        Object _exception = request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        if (error) {
            if (_exception != null) {
                String exceptionName = _exception.toString();
                exceptionName = exceptionName.substring(0, exceptionName.indexOf(":"));
                if (badCredentials.equals(exceptionName)) {
                    model.put("invalidPassword", "密码不对");
                } else if (usernameNotFound.equals(exceptionName)) {
                    model.put("invalidUserName", "用户名不对");
                }
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
     * 智能竞价后台页面
     *
     * @return
     */
    @RequestMapping(value = "/biddingConsole", method = RequestMethod.GET)
    public ModelAndView biddingConsole() {
        return new ModelAndView("bidding/biddingConsole");
    }

    /**
     * 注册页面
     *
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {
        return new ModelAndView("homePage/pageBlock/register");
    }

}
