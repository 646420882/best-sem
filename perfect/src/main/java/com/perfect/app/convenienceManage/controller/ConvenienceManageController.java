package com.perfect.app.convenienceManage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.perfect.app.convenienceManage.dao.ConvenienceManageDAO;
import com.perfect.app.convenienceManage.vo.AttentionReport;
import com.perfect.autosdk.sms.v3.KeywordInfo;
import com.perfect.utils.JSONUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by baizz on 2014-6-12.
 */
@RestController
@Scope("prototype")
@RequestMapping(value = "/convenienceManage")
public class ConvenienceManageController {

    private static ObjectMapper mapper;

    static {
        mapper = (mapper == null) ? new ObjectMapper() : mapper;
    }

    @Resource(name = "convenienceManageDAO")
    private ConvenienceManageDAO convenienceManageDAO;

    //添加新关注 --- 账户内关键词搜索处理
    @RequestMapping(value = "/searchWord", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getAllKeywordsFromAccount(@RequestParam(value = "searchWord", required = false, defaultValue = "") String searchWord)
            throws Exception {
        ModelAndView mav = new ModelAndView();
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        KeywordInfo[] keywordInfos = convenienceManageDAO.getMatchKeywordInfoFromAccount(java.net.URLDecoder.decode(searchWord, "UTF-8"));
        Map<String, Object> attributes = JSONUtils.getJsonMapData(keywordInfos);
        jsonView.setAttributesMap(attributes);
        mav.setView(jsonView);
        return mav;
    }

    //添加新关注 --- 将新关注存储到MongoDB
    @RequestMapping(value = "/addAttention", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addAttention(@RequestBody KeywordInfo[] keywordInfos) {
        convenienceManageDAO.addAttention(keywordInfos);
        ObjectNode json_string = mapper.createObjectNode();
        json_string.put("flag", true);
        return json_string.toString();
    }

    //获取关键词监控报告
    @RequestMapping(value = "/{keywordReport}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getKeywordReport(@PathVariable String keywordReport) {
        ModelAndView mav = new ModelAndView();
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        AttentionReport[] attentionReports = convenienceManageDAO.getKeywordReport();
        Map<String, Object> attributes = JSONUtils.getJsonMapData(attentionReports);
        jsonView.setAttributesMap(attributes);
        mav.setView(jsonView);
        return mav;
    }

    //手动修改关键词价格
    @RequestMapping(value = "/modifyKeywordPrice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String modifyKeywordPrice(@RequestParam(value = "keywordId") String keywordId,
                                     @RequestParam(value = "keywordPrice") String keywordPrice) {
        convenienceManageDAO.modifyKeywordPrice(keywordId, keywordPrice);
        ObjectNode json_string = mapper.createObjectNode();
        json_string.put("flag", true);
        return json_string.toString();
    }

    //将关键词价格恢复为单元出价
    @RequestMapping(value = "/revertToUnitPrice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String revertToUnitPrice(@RequestParam(value = "keywordId") String keywordId) {
        convenienceManageDAO.revertToUnitPrice(keywordId);
        ObjectNode json_string = mapper.createObjectNode();
        json_string.put("flag", true);
        return json_string.toString();
    }

    //取消关注
    @RequestMapping(value = "/cancelAttention", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String cancelAttention(@RequestParam(value = "keywordIds") String keywordIds) {
        String[] _keywordIds = keywordIds.split(",");
        convenienceManageDAO.cancelAttention(_keywordIds);
        ObjectNode json_string = mapper.createObjectNode();
        json_string.put("flag", true);
        return json_string.toString();
    }
}
