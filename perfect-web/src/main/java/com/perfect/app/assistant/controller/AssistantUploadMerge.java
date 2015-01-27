package com.perfect.app.assistant.controller;

import com.perfect.commons.web.WebContextSupport;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.service.CampaignService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by XiaoWei on 2015/1/19.
 */
@Controller
@RequestMapping(value = "/uploadMerge")
public class AssistantUploadMerge extends WebContextSupport {
    @Resource
    private CampaignService campaignService;

    @RequestMapping(value = "/upload")
    public ModelAndView uploadMerge(){

        return writeMapObject(MSG,SUCCESS);
    }

    @RequestMapping(value = "/getCampList")
    public ModelAndView getUploadOperate(){
       List<CampaignDTO> campaignDTOs= campaignService.getOperateCamp();
        return writeMapObject(DATA,campaignDTOs);
    }

}
