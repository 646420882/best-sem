package com.perfect.app.assistantAdgroup.controller;

import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.CampaignDAO;
import com.perfect.entity.AdgroupEntity;
import com.perfect.entity.CampaignEntity;
import com.perfect.mongodb.utils.EntityConstants;
import com.perfect.utils.web.WebContextSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/8/21.
 */
@Controller
@RequestMapping(value = "/assistantAdgroup")
public class AssistantAdgroupController extends WebContextSupport {
    @Resource
    AdgroupDAO adgroupDAO;
    @Resource
    CampaignDAO campaignDAO;

    @RequestMapping(value = "/getAdgroupList")
    public ModelAndView getList(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam(value = "cid", required = false) String cid) {
        List<AdgroupEntity> list = new ArrayList<>();
        if (cid!=""||!cid.equals("")) {
            Map<String,Object> parms=new HashMap<>();
            parms.put(EntityConstants.CAMPAIGN_ID,Long.parseLong(cid));
            list = adgroupDAO.find(parms, 0, 15);
        }else{
            list = adgroupDAO.find(null, 0, 15);
        }
        if(list.size()>0){
            for (AdgroupEntity a:list){
                a.setCampaignName(campaignDAO.findOne(a.getCampaignId()).getCampaignName());
            }
        }
        writeJson(list, response);
        return null;
    }

    @RequestMapping(value = "/getPlans", method = RequestMethod.GET)
    public ModelAndView getPlans(HttpServletRequest request, HttpServletResponse response) {
        List<CampaignEntity> campaignEntities = (List<CampaignEntity>) campaignDAO.findAll();
        writeJson(campaignEntities, response);
        return null;
    }
}
