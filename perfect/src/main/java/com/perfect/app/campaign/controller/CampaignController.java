package com.perfect.app.campaign.controller;

import com.perfect.dao.CampaignDAO;
import com.perfect.entity.CampaignEntity;
import com.perfect.utils.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-7-10.
 */
@RestController
@Scope("prototype")
@RequestMapping(value = "/campaign")
public class CampaignController {

    @Autowired
    private CampaignDAO campaignDAO;

    public void setCampaignDAO(@Qualifier("campaignDAO") CampaignDAO campaignDAO) {
        this.campaignDAO = campaignDAO;
    }

    @RequestMapping(value = "/getCampaignByCampaignId/{campaignId}", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getCampaignByCampaignId(@PathVariable Long campaignId) {
        AbstractView jsonView = new MappingJackson2JsonView();
        CampaignEntity campaignEntity = campaignDAO.findOne(campaignId);
        Map<String, Object> attributes = JSONUtils.getJsonMapData(new CampaignEntity[]{campaignEntity});
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/getAllCampaign", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getAllCampaign() {
        AbstractView jsonView = new MappingJackson2JsonView();
        List<CampaignEntity> list = campaignDAO.findAll();
        Map<String, Object> attributes = JSONUtils.getJsonMapData(list);
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView add(@RequestBody List<CampaignEntity> list) {
        campaignDAO.insertAll(list);
        return new ModelAndView(getJsonView());
    }

    @RequestMapping(value = "/{campaignId}/update", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView update(@PathVariable Long campaignId, @RequestParam(value = "campaignEntity") String campaignStr) {
        CampaignEntity campaignEntity = (CampaignEntity) JSONUtils.getObjectByJson(campaignStr, CampaignEntity.class);
        campaignDAO.update(campaignEntity);
        return new ModelAndView(getJsonView());
    }

    @RequestMapping(value = "/{campaignId}/del", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView deleteById(@PathVariable Long campaignId) {
        campaignDAO.deleteById(campaignId);
        return new ModelAndView(getJsonView());
    }

    @RequestMapping(value = "/deleteMulti", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView deleteByIds(@RequestBody List<Long> campaignIds) {
        campaignDAO.deleteByIds(campaignIds);
        return new ModelAndView(getJsonView());
    }

    private View getJsonView() {
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(new HashMap<String, Object>(1) {{
            put("stat", true);
        }});
        return jsonView;
    }
}
