package com.perfect.app.accountCenter.controller;

import com.perfect.app.accountCenter.dao.AccountManageDAO;
import com.perfect.app.homePage.service.CustomUserDetailsService;
import com.perfect.dao.SystemUserDAO;
import com.perfect.entity.BaiduAccountInfoEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by baizz on 2014-6-25.
 */
@RestController
@Scope("prototype")
@RequestMapping(value = "/account")
public class AccountManageController {

    @Resource(name = "systemUserDAO")
    private SystemUserDAO systemUserDAO;

    @Resource(name = "accountManageDAO")
    private AccountManageDAO<BaiduAccountInfoEntity> accountManageDAO;

    @RequestMapping(value = "/getAllBaiduAccount", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getAllBaiduAccount() {
        ModelAndView mav = new ModelAndView();
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        List<BaiduAccountInfoEntity> baiduAccountList = systemUserDAO.findByUserName(CustomUserDetailsService.getUserName()).getBaiduAccountInfos();
        Map<String, Object> attributes = new LinkedHashMap<>();
        attributes.put("tree", accountManageDAO.getAccountTree(baiduAccountList));
        jsonView.setAttributesMap(attributes);
        mav.setView(jsonView);
        return mav;
    }

    @RequestMapping(value = "/deleteBaiduAccount", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView deleteBaiduAccount() {
        return null;
    }

    @RequestMapping(value = "/addBaiduAccount", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView addBaiduAccount(@RequestParam(value = "baiduAccountName") String name,
                                        @RequestParam(value = "baiduAccountPasswd") String passwd,
                                        @RequestParam(value = "baiduAccountToken") String token,
                                        @RequestParam(value = "currSystemUserName") String currSystemUserName) {
        ModelAndView mav = new ModelAndView();
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attributes = new TreeMap<>();
        systemUserDAO.addBaiduAccount(accountManageDAO.getBaiduAccountInfos(name, passwd, token), currSystemUserName);
        attributes.put("status", true);
        jsonView.setAttributesMap(attributes);
        mav.setView(jsonView);
        return mav;
    }

    @RequestMapping(value = "/updateBaiduAccount", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView updateBaiduAccount() {
        return null;
    }
}
