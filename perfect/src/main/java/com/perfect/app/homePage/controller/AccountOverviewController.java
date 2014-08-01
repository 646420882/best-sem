package com.perfect.app.homePage.controller;

import com.google.gson.Gson;
import com.perfect.app.homePage.service.AccountOverviewService;
import com.perfect.app.homePage.service.CustomUserDetailsService;
import com.perfect.mongodb.utils.DateUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by john on 2014/7/25.
 */
@RestController
@Scope("prototype")
public class AccountOverviewController {

    @Resource
    private AccountOverviewService accountOverviewService;

    //当前登录用户名
    private static String currLoginUserName;
    static {
        currLoginUserName = (currLoginUserName == null) ? CustomUserDetailsService.getUserName() : currLoginUserName;
    }


    /**
     * 账户概览(获取汇总数据)
     * @param response
     * @param startDate
     * @param endDate
     */
    @RequestMapping(value = "/account/getAccountOverviewData", method = {RequestMethod.GET, RequestMethod.POST})
    public void getAccountOverviewData(HttpServletResponse response,String startDate,String endDate){

        List<String> dates = DateUtil.getPeriod(startDate,endDate);

        Map<String,Object> map = accountOverviewService.getKeyWordSum(currLoginUserName,dates);
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter out = response.getWriter();
            String data = new Gson().toJson(map);
            out.print(data);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
