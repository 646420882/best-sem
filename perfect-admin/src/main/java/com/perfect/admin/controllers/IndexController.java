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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("/WEB-INF/index.jsp");
    }

    @RequestMapping(value = "/admin/role")
    public ModelAndView role(){
        return new ModelAndView("/role/role");
    }

    @RequestMapping(value = "/admin/system", method = RequestMethod.GET)
    public ModelAndView system() {
        return new ModelAndView("/system/system");
    }

    @RequestMapping(value = "/admin/jurisdiction", method = RequestMethod.GET)
    public ModelAndView jurisdiction() {
        return new ModelAndView("/jurisdiction/jurisdiction");
    }

    @RequestMapping(value = "/admin/log", method = RequestMethod.GET)
    public ModelAndView log() {
        return new ModelAndView("/log/log");
    }
}


