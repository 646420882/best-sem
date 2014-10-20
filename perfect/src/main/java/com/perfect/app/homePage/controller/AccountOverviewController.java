package com.perfect.app.homePage.controller;

import com.perfect.core.AppContext;
import com.perfect.service.AccountOverviewService;
import com.perfect.utils.web.WebContext;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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

    /**
     * 账户概览(获取汇总数据)
     *
     * @param response
     * @param startDate
     * @param endDate
     */
    @RequestMapping(value = "/account/getAccountOverviewData", method = {RequestMethod.GET, RequestMethod.POST})
    public void getAccountOverviewData(HttpServletResponse response, String startDate, String endDate) {
        Map<String, Object> map = accountOverviewService.getKeyWordSum(startDate, endDate);
        webContext.writeJson(map, response);
    }
}
