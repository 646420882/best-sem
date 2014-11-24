package com.perfect.app.assistant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by XiaoWei on 2014/8/20.
 */
@Controller
@RequestMapping("/assistant")
public class AssistantManagerController {

    @RequestMapping(value = "/index")
    public ModelAndView convertIndex(){
        return new ModelAndView("promotionAssistant/assistantIndex");
    }
}
