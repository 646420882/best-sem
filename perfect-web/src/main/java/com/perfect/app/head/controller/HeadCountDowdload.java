package com.perfect.app.head.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 文楷 on 2015/10/28.
 */
@RestController
@Scope("prototype")
public class HeadCountDowdload {
    /**
     * 显示搜索词报告弹出窗口
     *
     * @return
     */
//    @RequestMapping(value = "assistantKeyword/showTimingPauseDialog", method = {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView showTimingPauseDialog() {
//        return new ModelAndView("promotionAssistant/alert/countDownload");
//    }
    @RequestMapping(value = "homePage/showCountDownload", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView showCountDownload() {
        return new ModelAndView("homePage/pageBlock/countDownload");
    }

}
