package com.perfect.admin.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created on 2015-12-14.
 *
 * @author dolphineor
 */
@Controller
@Scope("prototype")
public class IndexController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/roles")
    public ModelAndView Role() {
        return new ModelAndView("/Role/Role");
    }

    @RequestMapping(value = "/system", method = RequestMethod.GET)
    public ModelAndView system() {
        return new ModelAndView("/system/system");
    }

    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public ModelAndView menus(@RequestParam(required = false) String userName, ModelMap map) {
        if (userName == null) userName = "";
        map.put("userName", userName);
        return new ModelAndView("/menus/menus", map);
    }

    @RequestMapping(value = "/logs", method = RequestMethod.GET)
    public ModelAndView log() {
        return new ModelAndView("/log/log");
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {
        return new ModelAndView("/lognOrReg/register");
    }

    @RequestMapping(value = "/forget", method = RequestMethod.GET)
    public ModelAndView forget() {
        return new ModelAndView("/lognOrReg/forget");
    }

    @RequestMapping(value = "/forgetPassword", method = RequestMethod.GET)
    public ModelAndView forgetPassword() {
        return new ModelAndView("/lognOrReg/forgetPassword");
    }
}


