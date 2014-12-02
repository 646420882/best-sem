package com.perfect.app.admin.controller;

import com.perfect.commons.web.WebContextSupport;
import com.perfect.service.AccountManageService;
import com.perfect.service.AsynchronousReportService;
import com.perfect.utils.DateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by SubDong on 2014/10/8.
 */
@RestController
public class ReportPullController extends WebContextSupport {

    @Resource
    private AsynchronousReportService asynchronousReportService;

    @Resource
    private AccountManageService accountManageService;


    /**
     * @param response
     * @param pullObj  (0:拉取全部   1：拉取账户报告  2：拉取计划报告  3：拉取)
     */
    @RequestMapping(value = "/admin/reportPull/getPullDatas", method = {RequestMethod.GET, RequestMethod.POST})
    public void getPullData(HttpServletResponse response,
                            @RequestParam(value = "pullObj", required = false) int pullObj,
                            @RequestParam(value = "startDate", required = false) String startDate,
                            @RequestParam(value = "endDate", required = false) String endDate,
                            @RequestParam(value = "userName", required = false) String userName) {
        List<String> list = null;
        if (startDate == null || endDate == null || startDate.equals("") || startDate.equals("")) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            list = DateUtils.getPeriod(yesterday, yesterday);
        } else {
            list = DateUtils.getPeriod(startDate, endDate);
        }
        if ("".equals(userName)) {
            userName = null;
        }
        int flag = 0;
        try {
            for (String dateStr : list) {
                if (pullObj == 0) {
                    asynchronousReportService.getAccountReportData(dateStr, userName);
                    asynchronousReportService.getCampaignReportData(dateStr, userName);
                    asynchronousReportService.getAdgroupReportData(dateStr, userName);
                    asynchronousReportService.getCreativeReportData(dateStr, userName);
                    asynchronousReportService.getKeywordReportData(dateStr, userName);
                    asynchronousReportService.getRegionReportData(dateStr, userName);
                }
                if (pullObj == 1) {
                    asynchronousReportService.getAccountReportData(dateStr, userName);
                }
                if (pullObj == 2) {
                    asynchronousReportService.getCampaignReportData(dateStr, userName);
                }
                if (pullObj == 3) {
                    asynchronousReportService.getAdgroupReportData(dateStr, userName);
                }
                if (pullObj == 4) {
                    asynchronousReportService.getCreativeReportData(dateStr, userName);
                }
                if (pullObj == 5) {
                    asynchronousReportService.getKeywordReportData(dateStr, userName);
                }
                if (pullObj == 6) {
                    asynchronousReportService.getKeywordReportData(dateStr, userName);
                    asynchronousReportService.getRegionReportData(dateStr, userName);
                }
            }
            flag = 1;
            writeData(SUCCESS, response, null);
        } catch (Exception e) {
            writeData(EXCEPTION, response, null);
            flag = -1;
        }
        if (flag == 0) {
            writeData(FAIL, response, null);
        }
    }
}
