package com.perfect.app.adgroup.controller;

import com.perfect.dao.AdgroupDAO;
import com.perfect.entity.AdgroupEntity;
import com.perfect.utils.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
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
@RequestMapping(value = "/adgroup")
public class AdgroupController {

    @Autowired
    private AdgroupDAO adgroupDAO;

    public void setAdgroupDAO(@Qualifier("adgroupDAO") AdgroupDAO adgroupDAO) {
        this.adgroupDAO = adgroupDAO;
    }

    @RequestMapping(value = "/getAdgroupByCampaignId/{campaignId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getAdgroupByCampaignId(@PathVariable Long campaignId,
                                               @RequestParam(value = "skip", required = false, defaultValue = "0") int skip,
                                               @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        AbstractView jsonView = new MappingJackson2JsonView();
        List<AdgroupEntity> list = adgroupDAO.getAdgroupByCampaignId(campaignId, null, skip, limit);
        Map<String, Object> attributes = JSONUtils.getJsonMapData(list);
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/getAdgroupIdByCampaignId/{campaignId}", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getAdgroupIdByCampaignId(@PathVariable Long campaignId) {
        AbstractView jsonView = new MappingJackson2JsonView();
        List<Long> list = adgroupDAO.getAdgroupIdByCampaignId(campaignId);
        Map<String, Object> attributes = JSONUtils.getJsonMapData(list);
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/{adgroupId}", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getAdgroupByAdgroupId(@PathVariable Long adgroupId) {
        AbstractView jsonView = new MappingJackson2JsonView();
        AdgroupEntity adgroupEntity = adgroupDAO.findOne(adgroupId);
        Map<String, Object> attributes = JSONUtils.getJsonMapData(new AdgroupEntity[]{adgroupEntity});
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/showAll", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView findByPage(@RequestParam(value = "skip", required = false, defaultValue = "0") int skip,
                                   @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        AbstractView jsonView = new MappingJackson2JsonView();
        List<AdgroupEntity> list = adgroupDAO.find(null, skip, limit);
        Map<String, Object> attributes = JSONUtils.getJsonMapData(list);
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView add(@RequestBody List<AdgroupEntity> list) {
        adgroupDAO.insertAll(list);
        return new ModelAndView(getJsonView());
    }

    @RequestMapping(value = "/{adgroupId}/update", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView update(@PathVariable Long adgroupId, @RequestParam(value = "adgroupEntity") String adgroupStr) {
        AdgroupEntity adgroupEntity = (AdgroupEntity) JSONUtils.getObjectByJson(adgroupStr, AdgroupEntity.class);
        adgroupDAO.update(adgroupEntity);
        return new ModelAndView(getJsonView());
    }

    @RequestMapping(value = "/{adgroupId}/del", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView deleteById(@PathVariable Long adgroupId) {
        adgroupDAO.deleteById(adgroupId);
        return new ModelAndView(getJsonView());
    }

    @RequestMapping(value = "/deleteMulti", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView deleteByIds(@RequestBody List<Long> adgroupIds) {
        adgroupDAO.deleteByIds(adgroupIds);
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
