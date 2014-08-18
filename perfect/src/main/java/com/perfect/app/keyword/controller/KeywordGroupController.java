package com.perfect.app.keyword.controller;

import com.perfect.entity.AdgroupEntity;
import com.perfect.entity.KeywordEntity;
import com.perfect.service.KeywordGroupService;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by baizz on 2014-08-08.
 */
@RestController
@Scope("prototype")
@RequestMapping(value = "/getKRWords")
public class KeywordGroupController {

    @Resource
    private KeywordGroupService keywordGroupService;

    /**
     * 从百度获取关键词
     *
     * @param seedWords
     * @param skip
     * @param limit
     * @param krFileId
     * @return
     */
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

    /**
     * 自动分组
     *
     * @param words
     * @return
     */
    @RequestMapping(value = "/group", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView autoGroup(@RequestParam(value = "words", required = false) String words) {
        List<String> wordList = new ArrayList<>(Arrays.asList(words.split(";")));
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attributes = keywordGroupService.autoGroupByBaidu(wordList);
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    //添加关键词
    @RequestMapping(value = "/addKeywords", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView addKeywords(@RequestBody List<KeywordEntity> list) {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        keywordGroupService.addKeywords(null, list);
        Map<String, Object> attributes = new HashMap<String, Object>() {{
            put("statusText", "保存成功!");
        }};
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    //添加推广单元
    @RequestMapping(value = "/addAdgroups", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView addAdgroups(@RequestBody List<AdgroupEntity> list) {
        keywordGroupService.addKeywords(list, null);
        return new ModelAndView();
    }

}
