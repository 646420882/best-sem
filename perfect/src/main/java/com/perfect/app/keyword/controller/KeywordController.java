package com.perfect.app.keyword.controller;

import com.perfect.dao.KeywordDAO;
import com.perfect.entity.KeywordEntity;
import com.perfect.utils.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-7-8.
 */
@RestController
@Scope("prototype")
@RequestMapping(value = "/keyword")
public class KeywordController {

    @Autowired
    private KeywordDAO keywordDAO;

    public void setKeywordDAO(@Qualifier("keywordDAO") KeywordDAO keywordDAO) {
        this.keywordDAO = keywordDAO;
    }

    @RequestMapping(value = "/getKeywordByAdgroupId/{adgroupId}", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getKeywordByAdgroupId(@PathVariable Long adgroupId,
                                              @RequestParam(value = "skip", required = false, defaultValue = "0") int skip,
                                              @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        AbstractView jsonView = new MappingJackson2JsonView();
        List<KeywordEntity> list = keywordDAO.getKeywordByAdgroupId(adgroupId, null, skip, limit);
        Map<String, Object> attributes = JSONUtils.getJsonMapData(list.toArray(new KeywordEntity[list.size()]));
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/getKeywordIdByAdgroupId/{adgroupId}", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getKeywordIdByAdgroupId(@PathVariable Long adgroupId) {
        AbstractView jsonView = new MappingJackson2JsonView();
        List<Long> list = keywordDAO.getKeywordIdByAdgroupId(adgroupId);
        Map<String, Object> attributes = JSONUtils.getJsonMapData(list.toArray(new Long[list.size()]));
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/showAll", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView findByPage(@RequestParam(value = "skip", required = false, defaultValue = "0") int skip,
                                   @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        AbstractView jsonView = new MappingJackson2JsonView();
        List<KeywordEntity> list = keywordDAO.find(null, skip, limit);
        Map<String, Object> attributes = JSONUtils.getJsonMapData(list.toArray(new KeywordEntity[list.size()]));
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/getKeywordByKeywordId/{keywordId}", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getKeywordByKeywordId(@PathVariable Long keywordId) {
        AbstractView jsonView = new MappingJackson2JsonView();
        KeywordEntity keywordEntity = keywordDAO.findOne(keywordId);
        Map<String, Object> attributes = JSONUtils.getJsonMapData(new KeywordEntity[]{keywordEntity});
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView add(@RequestBody List<KeywordEntity> list) {
        keywordDAO.insertAll(list);
        return new ModelAndView(getJsonView());
    }

    @RequestMapping(value = "/{keywordId}/update", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView update(@PathVariable Long keywordId, @RequestParam(value = "keywordEntity") String keywordStr) {
        KeywordEntity keywordEntity = (KeywordEntity) JSONUtils.getObjectByJSON(keywordStr, KeywordEntity.class);
        keywordDAO.update(keywordEntity);
        return new ModelAndView(getJsonView());
    }

    @RequestMapping(value = "/update/{field}/{value}", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView updateMulti(@PathVariable String field,
                                    @PathVariable Object value,
                                    @RequestParam(value = "seedWord") String seedWord) {
        keywordDAO.updateMulti(field, seedWord, value);
        return new ModelAndView(getJsonView());
    }

    @RequestMapping(value = "/{keywordId}/del", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView deleteById(@PathVariable Long keywordId) {
        keywordDAO.deleteById(keywordId);
        return new ModelAndView(getJsonView());
    }

    @RequestMapping(value = "/deleteMulti", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView deleteByIds(@RequestBody List<Long> keywordIds) {
        keywordDAO.deleteByIds(keywordIds);
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
