package com.perfect.app.adgroup.controller;

import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.service.AdgroupService;
import com.perfect.utils.json.JSONUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
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
 * 2014-11-26 refactor
 */
@RestController
@Scope("prototype")
@RequestMapping(value = "/adgroup")
public class AdgroupController {

    @Resource
    private AdgroupService adgroupService;

    @RequestMapping(value = "/getAdgroupByCampaignId/{campaignId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getAdgroupByCampaignId(@PathVariable Long campaignId,
                                               @RequestParam(value = "skip", required = false, defaultValue = "0") int skip,
                                               @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        AbstractView jsonView = new MappingJackson2JsonView();
        List<AdgroupDTO> list = adgroupService.getAdgroupByCampaignId(campaignId, null, skip, limit);
        Map<String, Object> attributes = JSONUtils.getJsonMapData(list);
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/getAdgroupByCampaignObjId/{campaignObjId}", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getAdgroupIdByCampaignObjId(@PathVariable String campaignObjId) {
        AbstractView jsonView = new MappingJackson2JsonView();
        List<AdgroupDTO> list = adgroupService.getAdgroupByCampaignObjId(campaignObjId);
        Map<String, Object> attributes = JSONUtils.getJsonMapData(list);
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/getAdgroupIdByCampaignId/{campaignId}", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getAdgroupIdByCampaignId(@PathVariable Long campaignId) {
        AbstractView jsonView = new MappingJackson2JsonView();
        List<Long> list = adgroupService.getAdgroupIdByCampaignId(campaignId);
        Map<String, Object> attributes = JSONUtils.getJsonMapData(list);
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/{adgroupId}", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getAdgroupByAdgroupId(@PathVariable Long adgroupId) {
        AbstractView jsonView = new MappingJackson2JsonView();
        AdgroupDTO adgroupDTO = adgroupService.findOne(adgroupId);
        Map<String, Object> attributes = JSONUtils.getJsonMapData(new AdgroupDTO[]{adgroupDTO});
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/showAll", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView findByPage(@RequestParam(value = "skip", required = false, defaultValue = "0") int skip,
                                   @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        AbstractView jsonView = new MappingJackson2JsonView();
        List<AdgroupDTO> list = adgroupService.find(null, skip, limit);
        Map<String, Object> attributes = JSONUtils.getJsonMapData(list);
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView add(@RequestBody List<AdgroupDTO> list) {
        adgroupService.insertAll(list);
        return new ModelAndView(getJsonView());
    }

    @RequestMapping(value = "/{adgroupId}/update", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView update(@PathVariable Long adgroupId, @RequestParam(value = "adgroupEntity") String adgroupStr) {
        AdgroupDTO adgroupDTO = (AdgroupDTO) JSONUtils.getObjectByJson(adgroupStr, AdgroupDTO.class);
        adgroupService.update(adgroupDTO);
        return new ModelAndView(getJsonView());
    }

    @RequestMapping(value = "/{adgroupId}/del", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView deleteById(@PathVariable Long adgroupId) {
        adgroupService.delete(adgroupId);
        return new ModelAndView(getJsonView());
    }

    @RequestMapping(value = "/deleteMulti", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView deleteByIds(@RequestBody List<Long> adgroupIds) {
        adgroupService.deleteByIds(adgroupIds);
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
