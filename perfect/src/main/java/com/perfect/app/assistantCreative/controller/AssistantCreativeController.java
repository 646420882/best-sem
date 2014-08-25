package com.perfect.app.assistantCreative.controller;

import com.perfect.dao.CreativeDAO;
import com.perfect.entity.CreativeEntity;
import com.perfect.utils.web.WebContextSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by XiaoWei on 2014/8/21.
 */
@Controller
@RequestMapping(value = "/assistantCreative")
public class AssistantCreativeController  extends WebContextSupport{



    @Resource
    CreativeDAO creativeDAO;
    @RequestMapping(value = "/getList")
    public ModelAndView getCreativeList(HttpServletRequest request, HttpServletResponse response, ModelMap map){
        List<CreativeEntity> creativeEntityList=creativeDAO.find(null,0,10);
//        CampaignEntity
//        writeJson(creativeEntityList,response);
        ModelAndView mv=new ModelAndView("promotionAssistant/data/creativeData");
        mv.addObject("list",creativeEntityList);
        return mv;
    }
}
