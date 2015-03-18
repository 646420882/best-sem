package com.perfect.app.count.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by XiaoWei on 2014/11/19.
 */
@Controller
@RequestMapping("/pftstis")
public class WebSurveyController {

    /**
     * 调转到“网站概况”页面
     *
     * @return
     */
    @RequestMapping("/getIndex")
    public ModelAndView convertIndex(HttpServletRequest request){

        return new ModelAndView("census/register");
    }

 /*   public ModelAndView convertRegister(){
        return new ModelAndView("census/register");
    }*/

    /**
     * 跳转到“受访页面”页面
     * @return
     */
    @RequestMapping("/getVisitPage")
    public ModelAndView convertVisit() {
        return new ModelAndView("census/visitPage");
    }

    /**
     * 跳转到“受访域名”页面
     * @return
     */
    @RequestMapping("/getVisitHost")
    public ModelAndView convertVisitHost() {
        return new ModelAndView("census/visitHost");
    }

    /**
     * 跳转到“入口页面”页面
     * @return
     */
    @RequestMapping("/getLandingPage")
    public ModelAndView getLandingPage() {
        return new ModelAndView("census/landingPage");
    }

    /**
     * 跳转到“页面点击图”页面
     * @return
     */
    @RequestMapping("/getPageClk")
    public ModelAndView getPageClick(){
        return new ModelAndView("census/pageClk");
    }

    @RequestMapping("/getConfigPag")
    public ModelAndView getConfigPage(){
        return new ModelAndView("census/config");
    }

}
