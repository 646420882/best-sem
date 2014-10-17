package com.perfect.app.homePage.controller;

import com.google.gson.Gson;
import com.perfect.core.AppContext;
import com.perfect.dto.AccountReportDTO;
import com.perfect.entity.StructureReportEntity;
import com.perfect.mongodb.utils.DateUtils;
import com.perfect.service.BasisReportService;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by SubDong on 2014/8/12.
 */
@RestController
@Scope("prototype")
public class BasisReportController {
    @Resource
    BasisReportService basisReportService;

    @RequestMapping(value = "/reportIndex")
    public ModelAndView getbasisReportPage() {
        return new ModelAndView("foundationReport/basisReport");
    }

    /**
     * 报告生成
     *
     * @param response
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @param reportType 报告类型
     * @param start      报告显示开始数
     * @param sort       排序方式
     * @param limit      每页条数
     */
    @RequestMapping(value = "/account/structureReport", method = RequestMethod.GET)
    public void getPerformance(HttpServletResponse response,
                               @RequestParam(value = "startDate", required = false) String startDate,
                               @RequestParam(value = "endDate", required = false) String endDate,
                               @RequestParam(value = "reportType", required = false, defaultValue = "1") int reportType,
                               @RequestParam(value = "devices", required = false, defaultValue = "0") int devices,
                               @RequestParam(value = "dateType", required = false, defaultValue = "0") int dateType,
                               @RequestParam(value = "start", required = false, defaultValue = "0") int start,
                               @RequestParam(value = "sort", required = false, defaultValue = "-1") String sort,
                               @RequestParam(value = "limit", required = false, defaultValue = "30") int limit,
                               @RequestParam(value = "dataId", required = false, defaultValue = "0") Long dataId,
                               @RequestParam(value = "dataName", required = false, defaultValue = "0") String dateName) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        if (startDate == null || startDate.equals("")) {
            startDate = yesterday;
            endDate = yesterday;
        } else if (endDate == null || endDate.equals("")) {
            endDate = yesterday;
        }
        List<String> list = DateUtils.getPeriod(startDate, endDate);
        String[] newDate = list.toArray(new String[list.size()]);
        Map<String, List<StructureReportEntity>> responseDate = basisReportService.getReportDate(newDate, devices, dateType, reportType, start, limit, sort, dataId, dateName);

        int totalRows = 0;
        for (List<StructureReportEntity> entity : responseDate.values()) {
            totalRows = totalRows + ((entity == null) ? 0 : entity.size());
        }
        String data = new Gson().toJson(responseDate);
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(data);
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取用户基础统计信息
     *
     * @param response
     * @param Sorted    排序方式
     * @param fieldName 排序字段
     */
    @RequestMapping(value = "/account/accountReport", method = RequestMethod.GET)
    public void getAccountReport(HttpServletResponse response,
                                 @RequestParam(value = "Sorted", required = false, defaultValue = "1") int Sorted,
                                 @RequestParam(value = "fieldName", required = false, defaultValue = "date") String fieldName,
                                 @RequestParam(value = "startJC", required = false, defaultValue = "0") int startJC,
                                 @RequestParam(value = "limitJC", required = false, defaultValue = "9") int limitJC) {

        Map<String, List<AccountReportDTO>> returnAccount = basisReportService.getAccountAll(Sorted, fieldName, startJC, limitJC);

        String data = new Gson().toJson(returnAccount);

        try {
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(data);
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 账户报告以及比较数据
     *
     * @param response
     * @param date1
     * @param date2
     * @param date3
     * @param dateType
     * @param devices
     * @param compare
     * @param sortVS
     * @param startVS
     * @param limitVS
     */
    @RequestMapping(value = "/account/accountDateVs", method = RequestMethod.GET)
    public void getAccountDateVs(HttpServletResponse response,
                                 @RequestParam(value = "date1", required = true) String date1,
                                 @RequestParam(value = "date2", required = true) String date2,
                                 @RequestParam(value = "date3", required = false) String date3,
                                 @RequestParam(value = "dateType", required = false) int dateType,
                                 @RequestParam(value = "devices", required = false) int devices,
                                 @RequestParam(value = "compare", required = false) int compare,
                                 @RequestParam(value = "sortVS", required = false, defaultValue = "-1") String sortVS,
                                 @RequestParam(value = "startVS", required = false, defaultValue = "0") int startVS,
                                 @RequestParam(value = "limitVS", required = false, defaultValue = "9") int limitVS) {

        Map<String, List<Object>> returnAccount = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar YesterdayCal = Calendar.getInstance();
        YesterdayCal.add(Calendar.DATE, -1);
        String Yesterday = dateFormat.format(YesterdayCal.getTime());
        Date endDate1;
        Date endDate2;
        Date endDate3;
        Date endDate4 = null;
        try {
            if (date1 == null || date1.equals("")) {
                endDate1 = dateFormat.parse(Yesterday);
            } else {
                endDate1 = dateFormat.parse(date1);
            }
            if (date2 == null || date2.equals("")) {
                endDate2 = dateFormat.parse(Yesterday);
            } else {
                endDate2 = dateFormat.parse(date2);
            }

            if (date3 == null || date3.equals("")) {
                endDate3 = null;
            } else {
                endDate3 = dateFormat.parse(date3);
                Calendar cal = Calendar.getInstance();
                long kk = endDate3.getTime() + (endDate2.getTime() - endDate1.getTime());
                cal.setTimeInMillis(kk);
                endDate4 = cal.getTime();
            }
            returnAccount = basisReportService.getAccountDateVS(endDate1, endDate2, endDate3, endDate4, dateType, devices, compare, sortVS, startVS, limitVS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String data = new Gson().toJson(returnAccount);

        try {
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(data);
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载报告
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "/report/downReportCSV", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ModelAndView downReportCSV(HttpServletResponse response,
                                      @RequestParam(value = "startDate", required = false) String startDate,
                                      @RequestParam(value = "endDate", required = false) String endDate,
                                      @RequestParam(value = "reportType", required = false, defaultValue = "1") int reportType,
                                      @RequestParam(value = "devices", required = false, defaultValue = "0") int devices,
                                      @RequestParam(value = "dateType", required = false, defaultValue = "0") int dateType,
                                      @RequestParam(value = "dataId", required = false, defaultValue = "0") Long dataId) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        if (startDate == null || startDate.equals("") || startDate.equals("null")) {
            startDate = yesterday;
            endDate = yesterday;
        } else if (endDate == null || endDate.equals("") || startDate.equals("null")) {
            endDate = yesterday;
        }
        String redisKey = (startDate + "|" + endDate + "|" + devices + "|" + dateType + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId);
        String dateHead = startDate + " 至 " + endDate;
        String filename = DateUtils.getYesterdayStr() + "-ReportDetails.csv";
        OutputStream os = null;
        try {
            response.addHeader("Content-Disposition", "attachment;filename=" + new String((filename).getBytes("UTF-8"), "ISO8859-1"));
            os = response.getOutputStream();
            basisReportService.downReportCSV(os, redisKey, dateType, devices, reportType, dateHead);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 下载账户报告
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "/report/downAccoutReportCSV", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ModelAndView downAccoutReportCSV(HttpServletResponse response,
                                            @RequestParam(value = "date1", required = true) String date1,
                                            @RequestParam(value = "date2", required = true) String date2,
                                            @RequestParam(value = "date3", required = false) String date3,
                                            @RequestParam(value = "dateType", required = false) int dateType,
                                            @RequestParam(value = "devices", required = false) int devices,
                                            @RequestParam(value = "sortVS", required = false, defaultValue = "-1") String sortVS,
                                            @RequestParam(value = "startVS", required = false, defaultValue = "0") int startVS,
                                            @RequestParam(value = "limitVS", required = false, defaultValue = "9") int limitVS) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar YesterdayCal = Calendar.getInstance();
        YesterdayCal.add(Calendar.DATE, -1);
        String Yesterday = dateFormat.format(YesterdayCal.getTime());
        Date endDate1 = null;
        Date endDate2 = null;
        Date endDate3 = null;
        Date endDate4 = null;
        try {
            if (date1 == null || date1.equals("") || date1.equals("null")) {
                endDate1 = dateFormat.parse(Yesterday);
            } else {
                endDate1 = dateFormat.parse(date1);
            }
            if (date2 == null || date2.equals("") || date2.equals("null")) {
                endDate2 = dateFormat.parse(Yesterday);
            } else {
                endDate2 = dateFormat.parse(date2);
            }

            if (date3 == null || date3.equals("") || date3.equals("null")) {
                endDate3 = null;
            } else {
                endDate3 = dateFormat.parse(date3);
                Calendar cal1 = Calendar.getInstance();
                long kk = endDate3.getTime() + (endDate2.getTime() - endDate1.getTime());
                cal1.setTimeInMillis(kk);
                endDate4 = cal1.getTime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        String filename = DateUtils.getYesterdayStr() + "-AccountReport.csv";
        OutputStream os = null;
        try {
            response.addHeader("Content-Disposition", "attachment;filename=" + new String((filename).getBytes("UTF-8"), "ISO8859-1"));
            os = response.getOutputStream();
            basisReportService.downAccountReportCSV(os, endDate1, endDate2, endDate3, endDate4, dateType, devices, sortVS, startVS, limitVS);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
