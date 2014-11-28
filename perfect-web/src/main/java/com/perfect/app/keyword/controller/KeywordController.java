package com.perfect.app.keyword.controller;

import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.json.JSONUtils;
import com.perfect.service.KeywordService;
import com.perfect.service.SysKeywordService;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-7-8.
 * 2014-11-28 refactor
 */
@RestController
@Scope("prototype")
@RequestMapping(value = "/keyword")
public class KeywordController {

    @Resource
    private KeywordService keywordService;

    @Resource
    private SysKeywordService sysKeywordService;


    @RequestMapping(value = "/getKeywordByAdgroupId/{adgroupId}", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getKeywordByAdgroupId(@PathVariable Long adgroupId,
                                              @RequestParam(value = "skip", required = false, defaultValue = "0") int skip,
                                              @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        List<KeywordDTO> list = keywordService.getKeywordByAdgroupId(adgroupId, null, skip, limit);
        return new ModelAndView(getJsonView(JSONUtils.getJsonMapData(list)));
    }

    @RequestMapping(value = "/all/{adgroupId}", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getAllKeywordsByAdgroupdId(@PathVariable Long adgroupId) {
        List<KeywordDTO> list = sysKeywordService.findByAdgroupId(adgroupId, null, null);
        return new ModelAndView(getJsonView(JSONUtils.getJsonMapData(list)));
    }

    @RequestMapping(value = "/getKeywordIdByAdgroupId/{adgroupId}", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getKeywordIdByAdgroupId(@PathVariable Long adgroupId) {
        List<Long> list = keywordService.getKeywordIdByAdgroupId(adgroupId);
        return new ModelAndView(getJsonView(JSONUtils.getJsonMapData(list)));
    }

    @RequestMapping(value = "/showAll", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView findByPage(@RequestParam(value = "skip", required = false, defaultValue = "0") int skip,
                                   @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        List<KeywordDTO> list = keywordService.find(null, skip, limit);
        return new ModelAndView(getJsonView(JSONUtils.getJsonMapData(list)));
    }

    @RequestMapping(value = "/getKeywordByKeywordId/{keywordId}", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getKeywordByKeywordId(@PathVariable Long keywordId) {
        KeywordDTO keywordDTO = keywordService.findOne(keywordId);
        return new ModelAndView(getJsonView(JSONUtils.getJsonMapData(new KeywordDTO[]{keywordDTO})));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView add(@RequestBody List<KeywordDTO> list) {
        keywordService.insertAll(list);
        return new ModelAndView(getJsonView(null));
    }

    @RequestMapping(value = "/{keywordId}/update", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView update(@PathVariable Long keywordId, @RequestParam(value = "keywordEntity") String keywordStr) {
        KeywordDTO keywordDTO = JSONUtils.getObjectByJson(keywordStr, KeywordDTO.class);
        keywordService.save(keywordDTO);
        return new ModelAndView(getJsonView(null));
    }

    @RequestMapping(value = "/update/{field}/{value}", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView updateMulti(@PathVariable String field,
                                    @PathVariable Object value,
                                    @RequestParam(value = "seedWord") String seedWord) {
        keywordService.updateMulti(field, seedWord, value);
        return new ModelAndView(getJsonView(null));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView updateMultiKeyword(@RequestParam(value = "ids") Long[] ids,
                                           @RequestParam(value = "price", required = false) Double price,
                                           @RequestParam(value = "pcUrl", required = false) String pcUrl) {
        if (price != null) {
            BigDecimal _price = new BigDecimal(price);
            keywordService.updateMultiKeyword(ids, _price, null);
        }
        if (pcUrl != null) {
            keywordService.updateMultiKeyword(ids, null, pcUrl);
        }
        return new ModelAndView(getJsonView(null));
    }

    @RequestMapping(value = "/{keywordId}/del", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView deleteById(@PathVariable Long keywordId) {
        keywordService.delete(keywordId);
        return new ModelAndView(getJsonView(null));
    }

    @RequestMapping(value = "/deleteMulti", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView deleteByIds(@RequestBody List<Long> keywordIds) {
        keywordService.deleteByIds(keywordIds);
        return new ModelAndView(getJsonView(null));
    }

    private View getJsonView(Map<String, Object> attributes) {
        AbstractView jsonView = new MappingJackson2JsonView();
        if (attributes == null) {
            jsonView.setAttributesMap(new HashMap<String, Object>(1) {{
                put("stat", true);
            }});
            return jsonView;
        }

        jsonView.setAttributesMap(attributes);
        return jsonView;
    }
}
