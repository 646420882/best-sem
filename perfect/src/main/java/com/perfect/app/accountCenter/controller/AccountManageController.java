package com.perfect.app.accountCenter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.perfect.app.web.WebUtils;
import com.perfect.core.AppContext;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.CampaignEntity;
import com.perfect.service.AccountDataService;
import com.perfect.service.AccountManageService;
import com.perfect.service.LogService;
import com.perfect.utils.JSONUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-6-25.
 */
@RestController
@Scope("prototype")
@RequestMapping(value = "/account")
public class AccountManageController {

    @Resource
    private AccountManageService accountManageService;

    @Resource
    private AccountDataService accountDataService;

    @Resource
    private LogService logService;

    /**
     * 获取账户树
     *
     * @return
     */
    @RequestMapping(value = "/get_tree", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getAccountTree(HttpServletRequest request) {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        Map<String, Object> trees = accountManageService.getAccountTree();
        jsonView.setAttributesMap(trees);
        return new ModelAndView(jsonView);
    }

    /**
     * 获取当前登录的系统用户下的所有百度账号
     *
     * @return
     */
    @RequestMapping(value = "/getAllBaiduAccount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getAllBaiduAccount(HttpServletRequest request) {
        String currSystemUserName = WebUtils.getUserName(request);
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> values = accountManageService.getAllBaiduAccount(currSystemUserName);
        jsonView.setAttributesMap(values);
        return new ModelAndView(jsonView);
    }

    /**
     * 根据百度账户id获取其账户信息
     *
     * @return
     */
    @RequestMapping(value = "/getBaiduAccountInfoByUserId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getBaiduAccountInfoByUserId() {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        Long userId = AppContext.getAccountId();
        Map<String, Object> result = accountManageService.getBaiduAccountInfoByUserId(userId);
        jsonView.setAttributesMap(result);
        return new ModelAndView(jsonView);
    }

    /**
     * 百度账户切换
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/switchAccount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView switchAccount(HttpServletRequest request) {
        Long accountId = Long.valueOf(request.getParameter("accountId"));
        WebUtils.setAccountId(request, accountId);
        AppContext.setUser(WebUtils.getUserName(request), accountId);
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> result = new HashMap<String, Object>() {{
            put("status", true);
        }};
        jsonView.setAttributesMap(result);
        return new ModelAndView(jsonView);
    }

    /**
     * 获取百度账户报告
     *
     * @param number
     * @return
     */
    @RequestMapping(value = "/get_reports", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getBaiduAccountReports(@RequestParam(value = "number", required = false) Integer number) {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        Map<String, Object> results = accountManageService.getAccountReports(number);
        jsonView.setAttributesMap(results);
        return new ModelAndView(jsonView);
    }

    /**
     * 更新百度账户信息
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateBaiduAccount(@RequestBody BaiduAccountInfoEntity entity) {
        accountManageService.updateBaiduAccount(entity);
        ObjectNode json_string = new ObjectMapper().createObjectNode();
        json_string.put("status", true);
        return json_string.toString();
    }

    /**
     * 下载更新当前百度账户下的所有数据
     *
     * @param campaignIds
     * @return
     */
    @RequestMapping(value = "/updateAccountData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateAccountData(@RequestParam(value = "campaignIds", required = false) String campaignIds) {
        if (campaignIds == null || "".equals(campaignIds)) {
            //下载更新全部推广计划
            accountDataService.updateAccountData(AppContext.getUser(), AppContext.getAccountId());
        } else {
            //数据预处理
            List<Long> camIds = new ArrayList<>(campaignIds.split(",").length);
            for (String str : campaignIds.split(",")) {
                camIds.add(Long.valueOf(str));
            }

            accountDataService.updateAccountData(AppContext.getUser(), AppContext.getAccountId(), camIds);
        }

        ObjectNode json_string = new ObjectMapper().createObjectNode();
        json_string.put("status", true);
        return json_string.toString();
    }

    /**
     * 获取最新的推广计划
     *
     * @return
     */
    @RequestMapping(value = "/getNewCampaign", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getNewCampaign() {
        AbstractView jsonView = new MappingJackson2JsonView();
        List<CampaignEntity> list = accountDataService.getCampaign(AppContext.getUser(), AppContext.getAccountId());
        Map<String, Object> values = JSONUtils.getJsonMapData(list);
        jsonView.setAttributesMap(values);
        return new ModelAndView(jsonView);
    }

    /**
     * 添加百度账户
     *
     * @param name
     * @param passwd
     * @param token
     * @param currSystemUserName
     * @return
     */
    @RequestMapping(value = "/addBaiduAccount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView addBaiduAccount(@RequestParam(value = "baiduAccountName") String name,
                                        @RequestParam(value = "baiduAccountPasswd") String passwd,
                                        @RequestParam(value = "baiduAccountToken") String token,
                                        @RequestParam(value = "currSystemUserName") String currSystemUserName) {
        return null;
    }

    @RequestMapping(value = "/deleteBaiduAccount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView deleteBaiduAccount(@RequestParam(value = "userId") Long userId) {
        return null;
    }


    @RequestMapping(value = "/sync/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView info() {

        Map<String, Long> statics = logService.getStatiscs(AppContext.getUser(), AppContext.getAccountId());
        return new ModelAndView();
    }

}