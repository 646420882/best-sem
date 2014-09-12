package com.perfect.app.accountCenter.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by baizz on 2014-9-12.
 */
@RestController
@Scope("prototype")
//@RequestMapping(value = "/admin")
public class AdminController {

    @RequestMapping(value = "/admin", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView toAdmin() {
        return new ModelAndView("keywordGroup/keyword");
    }
}
