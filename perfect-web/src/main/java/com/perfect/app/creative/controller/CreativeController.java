package com.perfect.app.creative.controller;

import com.perfect.dto.creative.CreativeDTO;
import com.perfect.service.CreativeService;
import com.perfect.utils.json.JSONUtils;
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
@RequestMapping(value = "/creative")
public class CreativeController {

    @Autowired
    CreativeService creativeService;

    public void setCreativeDAO(@Qualifier("creativeDAO") CreativeService creativeService) {
        this.creativeService = creativeService;
    }

    @RequestMapping(value = "/getCreativeIdByAdgroupId/{adgroupId}", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getCreativeIdByAdgroupId(@PathVariable Long adgroupId) {
        AbstractView jsonView = new MappingJackson2JsonView();
        List<Long> creativeIds = creativeService.getCreativeIdByAdgroupId(adgroupId);
        Map<String, Object> attributes = JSONUtils.getJsonMapData(creativeIds);
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/getCreativeByAdgroupId/{adgroupId}", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getCreativeByAdgroupId(@PathVariable Long adgroupId,
                                               @RequestParam(value = "skip", required = false, defaultValue = "0") int skip,
                                               @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        AbstractView jsonView = new MappingJackson2JsonView();
        List<CreativeDTO> list = creativeService.getCreativeByAdgroupId(adgroupId, null, skip, limit);
        Map<String, Object> attributes = JSONUtils.getJsonMapData(list);
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/getCreativeByCreativeId/{creativeId}", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getCreativeByCreativeId(@PathVariable Long creativeId) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attributes = JSONUtils.getJsonMapData(new CreativeDTO[]{creativeService.findOne(creativeId)});
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/showAll", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView findByPage(@RequestParam(value = "skip", required = false, defaultValue = "0") int skip,
                                   @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        AbstractView jsonView = new MappingJackson2JsonView();
        List<CreativeDTO> _list = creativeService.find(null, skip, limit);
        Map<String, Object> attributes = JSONUtils.getJsonMapData(_list);
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView add(@RequestBody List<CreativeDTO> list) {
        creativeService.insertAll(list);
        return new ModelAndView(getJsonView());
    }

    @RequestMapping(value = "/{creativeId}/update", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView update(@PathVariable Long creativeId, @RequestParam(value = "creativeEntity") String creativeStr) {
        CreativeDTO creativeDTO = (CreativeDTO) JSONUtils.getObjectByJson(creativeStr, CreativeDTO.class);
        creativeService.update(creativeDTO);
        return new ModelAndView(getJsonView());
    }

    @RequestMapping(value = "/{creativeId}/del", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView deleteById(@PathVariable Long creativeId) {
        creativeService.delete(creativeId);
        return new ModelAndView(getJsonView());
    }

    @RequestMapping(value = "/deleteMulti", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView deleteByIds(@RequestBody List<Long> creativeIds) {
        creativeService.deleteByIds(creativeIds);
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
