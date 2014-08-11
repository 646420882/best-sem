package com.perfect.app.keyword.controller;

import com.perfect.service.KeywordGroupService;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-08-08.
 */
@RestController
@Scope("prototype")
@RequestMapping(value = "/getKRWords")
public class KeywordGroupController {

    @Resource
    private KeywordGroupService keywordGroupService;

    @RequestMapping(value = "/bd", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getKeywordFromBaidu(@RequestParam(value = "seedWords", required = false) String seedWords,
                                            @RequestParam(value = "skip", required = false, defaultValue = "0") int skip,
                                            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                                            @RequestParam(value = "krFileId", required = false) String krFileId) {
        List<String> seedWordList = new ArrayList<>(Arrays.asList(seedWords.split(",")));
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attributes = keywordGroupService.getKeywordFromBaidu(seedWordList, skip, limit, krFileId);
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }
}
