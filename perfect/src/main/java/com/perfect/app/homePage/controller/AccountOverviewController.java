package com.perfect.app.homePage.controller;

import com.perfect.app.homePage.service.AccountOverviewService;
import com.perfect.core.AppContext;
import com.perfect.mongodb.utils.DateUtil;
import com.perfect.utils.web.WebContext;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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

    @Resource
    private WebContext webContext;

    //当前登录用户名
    private static String currLoginUserName;

    static {
        currLoginUserName = (currLoginUserName == null) ? AppContext.getUser().toString() : currLoginUserName;
    }


    /**
     * 账户概览(获取汇总数据)
     *
     * @param response
     * @param startDate
     * @param endDate
     */
    @RequestMapping(value = "/account/getAccountOverviewData", method = {RequestMethod.GET, RequestMethod.POST})
    public void getAccountOverviewData(HttpServletResponse response, String startDate, String endDate) {
        List<String> dates = DateUtil.getPeriod(startDate, endDate);
        Map<String, Object> map = accountOverviewService.getKeyWordSum(currLoginUserName, dates);
        webContext.wirteJson(map, response);
    }
}
