package com.perfect.app.keyword.controller;

import com.perfect.service.KeywordDeduplicateService;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created on 2015-10-29.
 * <p>关键词去重.
 *
 * @author dolphineor
 */
@RestController
@Scope("prototype")
@RequestMapping("/keyword")
public class KeywordDeduplicateController {

    @Resource
    private KeywordDeduplicateService keywordDeduplicateService;

    @RequestMapping(value = "/deduplicate/account/{id}", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView accountDeduplicate(@PathVariable(value = "id") Long accountId) {
        Map<String, Map<Integer, List<String>>> duplicateKeywordMap = keywordDeduplicateService.deduplicate(accountId);

        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(duplicateKeywordMap);

        return new ModelAndView(jsonView);
    }
}
