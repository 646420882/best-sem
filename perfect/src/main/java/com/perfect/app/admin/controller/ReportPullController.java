package com.perfect.app.admin.controller;

import com.google.gson.Gson;
import com.perfect.core.AppContext;
import com.perfect.dao.AsynchronousReportDAO;
import com.perfect.mongodb.utils.DateUtils;
import com.perfect.service.AccountManageService;
import com.perfect.utils.web.WebContextSupport;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by SubDong on 2014/10/8.
 */
@RestController
public class ReportPullController  extends WebContextSupport{

    @Resource
    private AsynchronousReportDAO asynchronousReportDAO;

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
                            @RequestParam(value = "endDate", required = false) String endDate) {
        Gson gson = new Gson();
        List<String> list = null;
        if (startDate == null || endDate == null || startDate.equals("") || startDate.equals("")) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            list = DateUtils.getPeriod(yesterday, yesterday);
        } else {
            list = DateUtils.getPeriod(startDate, endDate);
        }
        int flag = 0;
        try {
            for (String dateStr : list) {
                if (pullObj == 0) {
                    asynchronousReportDAO.getAccountReportData(dateStr);
                    asynchronousReportDAO.getCampaignReportData(dateStr);
                    asynchronousReportDAO.getAdgroupReportData(dateStr);
                    asynchronousReportDAO.getCreativeReportData(dateStr);
                    asynchronousReportDAO.getKeywordReportData(dateStr);
                    asynchronousReportDAO.getRegionReportData(dateStr);
                }
                if (pullObj == 1) {
                    asynchronousReportDAO.getAccountReportData(dateStr);
                }
                if (pullObj == 2) {
                    asynchronousReportDAO.getCampaignReportData(dateStr);
                }
                if (pullObj == 3) {
                    asynchronousReportDAO.getAdgroupReportData(dateStr);
                }
                if (pullObj == 4) {
                    asynchronousReportDAO.getCreativeReportData(dateStr);
                }
                if (pullObj == 5) {
                    asynchronousReportDAO.getKeywordReportData(dateStr);
                }
                if (pullObj == 6) {
                    asynchronousReportDAO.getRegionReportData(dateStr);
                }
            }
            flag = 1;
            writeData(SUCCESS,response,null);
        } catch (Exception e) {
            writeData(EXCEPTION,response,null);
            flag = -1;
        }
        if(flag==0){
        writeData(FAIL,response,null);
        }
//        try {
//            String s = gson.toJson(flag);
//            response.setContentType("text/html;charset=UTF-8");
//            response.setHeader("Pragma", "No-cache");
//            response.setHeader("Cache-Control", "no-cache");
//            response.setDateHeader("Expires", 0);
//            response.getWriter().write(s);
//            response.getWriter().flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
