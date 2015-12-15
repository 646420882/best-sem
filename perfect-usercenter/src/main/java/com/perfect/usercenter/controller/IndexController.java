package com.perfect.usercenter.controller;

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
        return new ModelAndView("index");
    }


    @RequestMapping(value = "/userCenter/account", method = RequestMethod.GET)
    public ModelAndView account() {
        return new ModelAndView("/account/account");
    }

    @RequestMapping(value = "/userCenter/password", method = RequestMethod.GET)
    public ModelAndView password() {
        return new ModelAndView("/password/password");
    }

    @RequestMapping(value = "/userCenter/safetyTool", method = RequestMethod.GET)
    public ModelAndView safetyTool() {
        return new ModelAndView("/safe/safetool");
    }
}


