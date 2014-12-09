package com.perfect.app.index.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by XiaoWei on 2014/9/26.
 */
@Controller
@RequestMapping("/ext")
public class ExtController {

    @RequestMapping("/demo")
    public ModelAndView returnExtDemo(){
        return new ModelAndView("homePage/pageBlock/index");
    }
}
