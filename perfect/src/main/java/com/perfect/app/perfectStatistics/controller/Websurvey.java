package com.perfect.app.perfectStatistics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by XiaoWei on 2014/11/19.
 */
@Controller
@RequestMapping("/pftstis")
public class Websurvey {

    /**
     * 调转到网站概况页面
     *
     * @return
     */
    @RequestMapping("/getIndex")
    public ModelAndView convertIndex(){
        return new ModelAndView("census/index");
    }

    @RequestMapping("/getVisitPage")
    public ModelAndView convertVisit() {
        return new ModelAndView("census/visitPage");
    }
}
