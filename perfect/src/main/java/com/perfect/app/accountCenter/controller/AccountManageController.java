package com.perfect.app.accountCenter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.perfect.dao.AccountManageDAO;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.service.AccountManageService;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by baizz on 2014-6-25.
 */
@RestController
@Scope("prototype")
@RequestMapping(value = "/account")
public class AccountManageController {

    @Resource
    private AccountManageService<BaiduAccountInfoEntity> service;

    @Resource
    private AccountManageDAO<BaiduAccountInfoEntity> accountManageDAO;

    /**
     * 获取账户树
     *
     * @return
     */
    @RequestMapping(value = "/get_tree", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getAccountTree() {
        BaiduAccountInfoEntity entity = accountManageDAO.findByBaiduUserId(null);
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        Map<String, Object> trees = service.getAccountTree(entity);
        jsonView.setAttributesMap(trees);
        return new ModelAndView(jsonView);
    }

    /**
     * 根据百度账户id获取其账户信息
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getBaiduAccountInfoByUserId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getBaiduAccountInfoByUserId(@RequestParam(value = "userId", required = false) Long userId) {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        Map<String, Object> result = service.getBaiduAccountInfoByUserId(userId);
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
        Map<String, Object> results = service.getAccountReports(number);
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
        service.updateBaiduAccount(entity);
        ObjectNode json_string = new ObjectMapper().createObjectNode();
        json_string.put("status", true);
        return json_string.toString();
    }

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

}
