package com.perfect.usercenter.controller;

import com.perfect.commons.SessionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created on 2015-12-14.
 *
 * @author dolphineor
 */
@RestController
@Scope("prototype")
public class IndexController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("/index");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        return new ModelAndView("/loginOrReg/login");
    }

    @RequestMapping(value = "/platformPage")
    public ModelAndView platform() {
        return new ModelAndView("/bestPage/bestIndex");
    }


    /**
     * 登出
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public void logout(HttpServletResponse response) {
        Cookie cookies = new Cookie("userToken", null);
        cookies.setMaxAge(0);
        response.addCookie(cookies);
    }

    @RequestMapping(value = "/toUserCenter", method = RequestMethod.GET)
    public void toUserCenter() {
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView reg() {
        return new ModelAndView("/loginOrReg/register");
    }

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public ModelAndView account(ModelMap modelMap) {
        modelMap.put("startTime", "2015-10-10");
        modelMap.put("endTime", "2016-10-10");

        return new ModelAndView("/account/account").addAllObjects(modelMap);
    }

    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public ModelAndView password() {
        return new ModelAndView("/password/password");
    }

    @RequestMapping(value = "/forget", method = RequestMethod.GET)
    public ModelAndView forget() {
        return new ModelAndView("/password/forget");
    }

    @RequestMapping(value = "/safetyTool", method = RequestMethod.GET)
    public ModelAndView safetyTool() {
        return new ModelAndView("/safe/safetool");
    }
}


