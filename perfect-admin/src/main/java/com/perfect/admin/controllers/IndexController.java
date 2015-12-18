package com.perfect.admin.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created on 2015-12-14.
 *
 * @author dolphineor
 */
@RestController
@Scope("prototype")
public class IndexController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/admin/role")
    public ModelAndView Role() {
        return new ModelAndView("/role/role");
    }

    @RequestMapping(value = "/admin/system", method = RequestMethod.GET)
    public ModelAndView system() {
        return new ModelAndView("/system/system");
    }

    @RequestMapping(value = "/admin/jurisdiction", method = RequestMethod.GET)
    public ModelAndView jurisdiction() {
        return new ModelAndView("/Jurisdiction/jurisdiction");
    }

    @RequestMapping(value = "/admin/log", method = RequestMethod.GET)
    public ModelAndView log() {
        return new ModelAndView("/log/log");
    }

    @RequestMapping(value = "/admin/register", method = RequestMethod.GET)
    public ModelAndView register() {
        return new ModelAndView("/lognOrReg/register");
    }

    @RequestMapping(value = "/admin/forget", method = RequestMethod.GET)
    public ModelAndView forget() {
        return new ModelAndView("/lognOrReg/forget");
    }

    @RequestMapping(value = "/admin/forgetPassword", method = RequestMethod.GET)
    public ModelAndView forgetPassword() {
        return new ModelAndView("/lognOrReg/forgetPassword");
    }
}


