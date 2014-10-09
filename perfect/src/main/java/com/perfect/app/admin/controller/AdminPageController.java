package com.perfect.app.admin.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by baizz on 2014-10-9.
 */
@RestController
@Scope("prototype")
@RequestMapping("/admin")
public class AdminPageController {

    /**
     * 智能竞价后台页面
     *
     * @return
     */
    @RequestMapping(value = "/biddingConsole", method = RequestMethod.GET)
    public ModelAndView biddingConsole() {
        return new ModelAndView("bidding/biddingConsole");
    }

    /**
     * 词库管理后台页面
     *
     * @return
     */
    @RequestMapping(value = "/lexiconConsole", method = RequestMethod.GET)
    public ModelAndView lexiconConsole() {
        return new ModelAndView("/keywordGroup/lexicon");
    }

    /**
     * 数据拉取后台页面
     * @return
     */
    @RequestMapping(value = "/pullPage", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView pullPage() {
        return new ModelAndView("foundationReport/report");
    }
}
