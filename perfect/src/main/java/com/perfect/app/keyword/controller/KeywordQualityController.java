package com.perfect.app.keyword.controller;

import com.perfect.service.KeywordQualityService;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by baizz on 2014-07-28.
 */
@RestController
@Scope("prototype")
public class KeywordQualityController {

    @Resource
    private KeywordQualityService keywordQualityService;

    @RequestMapping(value = "/keywordQuality/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView findKeywordQuality(@RequestParam(value = "fieldName", required = false, defaultValue = "impression") String fieldName,
                                           @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                           @RequestParam(value = "sort", required = false, defaultValue = "-1") Integer sort) {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        Map<String, Object> values = keywordQualityService.find(fieldName, limit, sort);
        jsonView.setAttributesMap(values);
        return new ModelAndView(jsonView);
    }
}
