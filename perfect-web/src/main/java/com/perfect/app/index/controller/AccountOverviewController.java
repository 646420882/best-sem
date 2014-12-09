package com.perfect.app.index.controller;

import com.perfect.commons.web.WebContextSupport;
import com.perfect.service.AccountOverviewService;
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
public class AccountOverviewController extends WebContextSupport{

    @Resource
    private AccountOverviewService accountOverviewService;


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
        writeJson(map, response);
    }
}
