package com.perfect.app.conf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by vbzer_000 on 2014/9/2.
 */
@Controller
@RequestMapping("/configuration")
public class ConfigurationController {

    @RequestMapping(value = "/")
    public ModelAndView index() {
        return new ModelAndView("configuration/configure");
    }
}
