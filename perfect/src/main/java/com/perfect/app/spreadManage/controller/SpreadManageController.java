package com.perfect.app.spreadManage.controller;

import com.perfect.app.spreadManage.dao.SpreadManageDAO;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.utils.JSONUtils;
import net.sf.json.JSONArray;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by baizz on 2014-6-4.
 */
@RestController
@Scope("prototype")
@RequestMapping(value = "/spreadManage")
public class SpreadManageController {

    @Resource(name = "spreadManageDAO")
    private SpreadManageDAO spreadManageDAO;

    //左侧账户树处理
    @RequestMapping(value = "/get_tree", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getTree(
            @RequestParam(value = "nodeId", required = false) String nodeId) {
        ModelAndView mav = new ModelAndView();
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attributes = new LinkedHashMap<>();
        attributes.put("tree", spreadManageDAO.getAccountTree());
        jsonView.setAttributesMap(attributes);
        mav.setView(jsonView);
        return mav;
    }

    //实时报告请求处理
    @RequestMapping(value = "/realTimeRequestType", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    public ModelAndView getRealTimeRequestType(@RequestParam(value = "number", required = false, defaultValue = "1000") Integer number,
                                               @RequestParam(value = "levelOfDetails", required = false, defaultValue = "2") Integer levelOfDetails,
                                               @RequestParam(value = "reportType", required = false, defaultValue = "2") Integer reportType,
                                               @RequestParam(value = "statRange", required = false, defaultValue = "2") Integer statRange,
                                               @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
                                               @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
                                               @RequestParam(value = "id", required = false) String id) {
        ModelAndView mav = new ModelAndView();
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        RealTimeResultType[] realTimeResultTypes = spreadManageDAO.getRealTimeResultType(startDate, endDate, levelOfDetails, reportType, statRange, number, id);
        Map<String, Object> attributes = JSONUtils.getJsonMapData(realTimeResultTypes);
        jsonView.setAttributesMap(attributes);
        mav.setView(jsonView);
        return mav;
    }

    //输入种子词, 获取推荐词
    @RequestMapping(value = "/getKRResult", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getKRResult(@RequestParam(value = "aSeedWord") String aSeedWord)
            throws UnsupportedEncodingException {
        ModelAndView mav = new ModelAndView();
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        KRResult[] krResult = spreadManageDAO.getKRResult(java.net.URLDecoder.decode(aSeedWord, "UTF-8"));
        Map<String, Object> attributes = JSONUtils.getJsonMapData(krResult);
        jsonView.setAttributesMap(attributes);
        mav.setView(jsonView);
        return mav;
    }

    //根据推广计划ID获取其下属的所有推广单元ID和名称
    @RequestMapping(value = "/getAdgroupByCampaignId", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getAdgroupByCampaignId(@RequestParam(value = "campaignId") Long campaignId) {
        ModelAndView mav = new ModelAndView();
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        AdgroupInfo[] adgroupInfos = spreadManageDAO.getAdgroupByCampaignId(campaignId);
        Map<String, Object> attributes = JSONUtils.getJsonMapData(adgroupInfos);
        jsonView.setAttributesMap(attributes);
        mav.setView(jsonView);
        return mav;
    }

    //自动分组
    @RequestMapping(value = "/autoGroup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView autoGroup(@RequestParam(value = "seedWords") String seedWords,
                                  @RequestParam(value = "campaignId", required = false) Long campaignId,
                                  @RequestParam(value = "adgroupId", required = false) Long adgroupId)
            throws UnsupportedEncodingException {
        String[] _seedWords = (java.net.URLDecoder.decode(seedWords, "UTF-8")).split(";");
        ModelAndView mav = new ModelAndView();
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        AutoAdGroupResult[] autoAdGroupResults = spreadManageDAO.autoGroup(_seedWords, campaignId, adgroupId);
        Map<String, Object> attributes = JSONUtils.getJsonMapData(autoAdGroupResults);
        jsonView.setAttributesMap(attributes);
        mav.setView(jsonView);
        return mav;
    }

    //添加关键词时没有指定推广计划, 则新建计划并返回新建计划的ID
    @RequestMapping(value = "/getNewCampaignId", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getNewCampaignId(@RequestParam(value = "campaignName", required = false) String campaignName)
            throws UnsupportedEncodingException {
        ModelAndView mav = new ModelAndView();
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        Long newCampaignId = spreadManageDAO.getNewCampaign(java.net.URLDecoder.decode(campaignName, "UTF-8"));
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("new_campaignId", newCampaignId);
        jsonView.setAttributesMap(attributes);
        mav.setView(jsonView);
        return mav;
    }

    //添加关键词时没有指定推广单元, 则新建单元并返回新建单元的ID
    @RequestMapping(value = "/getNewAdgroupId", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getNewAdgroupId(@RequestParam(value = "adgroupNames[]") String[] _adgroupNames,
                                        @RequestParam(value = "newCampaignId") Long newCampaignId)
            throws UnsupportedEncodingException {
        List<String> adgroupNames = new ArrayList<>();
        for (String str : _adgroupNames)
            adgroupNames.add(java.net.URLDecoder.decode(str, "UTF-8"));

        ModelAndView mav = new ModelAndView();
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        Long[] newAdgroupIds = spreadManageDAO.getNewAdgroupId(adgroupNames.toArray(new String[adgroupNames.size()]), newCampaignId);
        Map<String, Object> attributes = JSONUtils.getJsonMapData(newAdgroupIds);
        jsonView.setAttributesMap(attributes);
        mav.setView(jsonView);
        return mav;
    }

    //添加关键词
    @RequestMapping(value = "/addKeywords", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView addKeywords(@RequestParam(value = "keywords") String keywords)
            throws IOException {
        //数据预处理
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JSONArray jsonArray = JSONArray.fromObject(keywords);
        KeywordType keywordType = null;
        List<KeywordType> list = new ArrayList<>();
        for (Object obj : jsonArray) {
            keywordType = mapper.readValue(obj.toString(), KeywordType.class);
            list.add(keywordType);
        }

        ModelAndView mav = new ModelAndView();
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        spreadManageDAO.addKeywords(list);
        Map<String, Object> attributes = new ConcurrentHashMap<>();
        attributes.put("flag", true);
        jsonView.setAttributesMap(attributes);
        mav.setView(jsonView);
        return mav;
    }

}
