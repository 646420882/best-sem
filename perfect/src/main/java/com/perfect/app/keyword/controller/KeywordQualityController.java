package com.perfect.app.keyword.controller;

import com.perfect.dao.KeywordQualityDAO;
import com.perfect.entity.KCRealTimeDataEntity;
import com.perfect.utils.JSONUtils;
import org.springframework.context.annotation.Scope;
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
    private KeywordQualityDAO keywordQualityDAO;

    @RequestMapping(value = "/keywordQuality/list", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView findKeywordQuality(@RequestParam(value = "startDate", required = false) String startDate,
                                           @RequestParam(value = "endDate", required = false) String endDate,
                                           @RequestParam(value = "fieldName", required = false, defaultValue = "impression") String fieldName,
                                           @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                           @RequestParam(value = "sort", required = false, defaultValue = "-1") Integer sort) {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        KCRealTimeDataEntity[] voEntities = keywordQualityDAO.find(startDate, endDate, fieldName, limit, sort);
        Map<String, Object> attributes = null;
        if (voEntities != null)
            attributes = JSONUtils.getJsonMapData(voEntities);
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }
}
