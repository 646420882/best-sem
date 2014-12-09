package com.perfect.app.campaign.controller;

import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.service.CampaignService;
import com.perfect.utils.json.JSONUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
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

    @Resource
    CampaignService campaignService;

    public void setCampaignDAO(@Qualifier("campaignDAO") CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @RequestMapping(value = "/getCampaignByCampaignId/{campaignId}", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getCampaignByCampaignId(@PathVariable Long campaignId) {
        AbstractView jsonView = new MappingJackson2JsonView();
        CampaignDTO campaignDTO = campaignService.findOne(campaignId);
        Map<String, Object> attributes = JSONUtils.getJsonMapData(new CampaignDTO[]{campaignDTO});
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/getAllCampaign", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getAllCampaign() {
        AbstractView jsonView = new MappingJackson2JsonView();
        Iterable<CampaignDTO> list = campaignService.findAll();
        Map<String, Object> attributes = JSONUtils.getJsonMapData(list);
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/getAllDownloadCampaign", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getAllDownloadCampaign() {
        AbstractView jsonView = new MappingJackson2JsonView();
        Iterable<CampaignDTO> list = campaignService.findAllDownloadCampaign();
        Map<String, Object> attributes = JSONUtils.getJsonMapData(list);
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView add(@RequestBody List<CampaignDTO> list) {
        campaignService.insertAll(list);
        return new ModelAndView(getJsonView());
    }

    @RequestMapping(value = "/{campaignId}/update", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView update(@PathVariable Long campaignId, @RequestParam(value = "campaignEntity") String campaignStr) {
        CampaignDTO campaignDTO = (CampaignDTO) JSONUtils.getObjectByJson(campaignStr, CampaignDTO.class);
        campaignService.update(campaignDTO);
        return new ModelAndView(getJsonView());
    }

    @RequestMapping(value = "/{campaignId}/del", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView deleteById(@PathVariable Long campaignId) {
        campaignService.delete(campaignId);
        return new ModelAndView(getJsonView());
    }

    @RequestMapping(value = "/deleteMulti", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView deleteByIds(@RequestBody List<Long> campaignIds) {
        campaignService.deleteByIds(campaignIds);
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
