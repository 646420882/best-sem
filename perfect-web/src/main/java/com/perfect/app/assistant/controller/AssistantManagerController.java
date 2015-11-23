package com.perfect.app.assistant.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by XiaoWei on 2014/8/20.
 */
@RestController
@RequestMapping("/assistant")
public class AssistantManagerController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView convertIndex(@RequestParam(value = "kwid", required = false, defaultValue = "0") Long keywordId,
                                     @RequestParam(value = "agid", required = false, defaultValue = "0") Long adgroupId,
                                     @RequestParam(value = "cid", required = false, defaultValue = "0") Long campaignId,
                                     Model model) {
        model.addAttribute("keywordId", keywordId);
        model.addAttribute("adgroupId", adgroupId);
        model.addAttribute("campaignId", campaignId);

        return new ModelAndView("promotionAssistant/assistantIndex").addAllObjects(model.asMap());
    }
}
