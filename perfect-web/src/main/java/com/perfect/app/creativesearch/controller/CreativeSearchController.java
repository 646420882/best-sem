package com.perfect.app.creativesearch.controller;

import com.perfect.dto.creative.EsSearchResultDTO;
import com.perfect.service.CreativeSourceService;
import com.perfect.utils.json.JSONUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;

/**
 * Created by vbzer_000 on 2014/9/19.
 */
@Component
@RequestMapping(value = "/creative")
public class CreativeSearchController {

    @Resource
    private CreativeSourceService esService;


    @RequestMapping(value = "/q", method = {RequestMethod.GET, RequestMethod.POST},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView search(@RequestParam(value = "q", required = true) String query,
                               @RequestParam(value = "p", required = false, defaultValue = "1") int page,
                               @RequestParam(value = "s", required = false, defaultValue = "20") int size,
                               @RequestParam(value = "r", required = true) int region) {

        EsSearchResultDTO entityList = esService.search(query, page, size, new int[]{region});

        AbstractView view = new MappingJackson2JsonView();

        view.setAttributesMap(JSONUtils.getJsonMapData(entityList));

        return new ModelAndView(view);
    }
}
