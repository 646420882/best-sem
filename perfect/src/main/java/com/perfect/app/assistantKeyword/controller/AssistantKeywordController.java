package com.perfect.app.assistantKeyword.controller;

import com.perfect.app.assistantKeyword.service.AssistantKeywordService;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by john on 2014/8/14.
 */
@RestController
@Scope("prototype")
public class AssistantKeywordController {


    @Resource
    private AssistantKeywordService assistantKeywordService;

    /**
     * 得到所有的关键词
     * @return
     */
    @RequestMapping(value = "assistantKeyword/list",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView getAllKeywordList(ModelMap model){
        model.addAttribute("list",assistantKeywordService.getAllKeywordList());
        return new ModelAndView("");
    }

    /**
     * 根据一个或者多个关键词id删除关键词
     * @param kwids
     * @return
     */
    @RequestMapping(value = "assistantKeyword/delete" ,method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView deleteKeywordById(Long[] kwids){
        assistantKeywordService.deleteKeywordById(kwids);
        return new ModelAndView();
    }
}
