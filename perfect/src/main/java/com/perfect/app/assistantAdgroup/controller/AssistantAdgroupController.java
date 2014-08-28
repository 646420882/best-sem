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

    /**
     * 默认加载单元数据
     * @param request
     * @param response
     * @param cid
     * @return
     */
    @RequestMapping(value = "/getAdgroupList")
    public ModelAndView getList(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam(value = "cid", required = false) String cid) {
        List<AdgroupEntity> list = new ArrayList<>();
        if (cid != "" || !cid.equals("")) {
            Map<String, Object> parms = new HashMap<>();
            parms.put(EntityConstants.CAMPAIGN_ID, Long.parseLong(cid));
            list = adgroupDAO.find(parms, 0, 15);
        } else {
            list = adgroupDAO.find(null, 0, 15);
        }
        if (list.size() > 0) {
            List<CampaignEntity> campaignEntity = (List<CampaignEntity>) campaignDAO.findAll();
            for (int i = 0; i < campaignEntity.size(); i++) {
                for (AdgroupEntity a : list) {
                    if (a.getCampaignId().equals(campaignEntity.get(i).getCampaignId())) {
                        a.setCampaignName(campaignEntity.get(i).getCampaignName());
                    }
                }
            }
        }
        writeJson(list, response);
        return null;
    }

    /**
     * 获取全部的计划供添加选择使用
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getPlans", method = RequestMethod.GET)
    public ModelAndView getPlans(HttpServletRequest request, HttpServletResponse response) {
        List<CampaignEntity> campaignEntities = (List<CampaignEntity>) campaignDAO.findAll();
        writeJson(campaignEntities, response);
        return null;
    }
    @RequestMapping(value = "/adAdd",method = RequestMethod.POST)
    public ModelAndView adAdd(HttpServletRequest request,HttpServletResponse response,
                              @RequestParam(value = "oid",required = true)String agid,
                              @RequestParam(value = "cid",required = true)String cid,
                              @RequestParam(value = "name")String name,
                              @RequestParam(value = "maxPrice")Double maxPrice,
                              @RequestParam(value="nn")String[] nn,
                              @RequestParam(value = "ne")String[] ne,
                              @RequestParam(value = "pause")Boolean p,
                              @RequestParam(value="status")Integer s){
        return null;
    }
}
