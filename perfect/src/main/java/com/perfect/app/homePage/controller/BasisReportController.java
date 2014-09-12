package com.perfect.app.homePage.controller;

import com.google.gson.Gson;
import com.perfect.dto.AccountReportDTO;
import com.perfect.entity.StructureReportEntity;
import com.perfect.mongodb.utils.DateUtils;
import com.perfect.service.BasisReportService;
import org.springframework.context.annotation.Scope;
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
     * @param response
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param reportType 报告类型
     * @param start 报告显示开始数
     * @param sort 排序方式
     * @param limit 每页条数
     */
    @RequestMapping(value = "/account/structureReport", method = RequestMethod.GET)
    public void getPerformance(HttpServletResponse response,
                               @RequestParam(value = "startDate", required = false) String startDate,
                               @RequestParam(value = "endDate", required = false) String endDate,
                               @RequestParam(value = "reportType", required = false, defaultValue = "1") int reportType,
                               @RequestParam(value = "devices", required = false, defaultValue = "0") int devices,
                               @RequestParam(value = "dateType", required = false, defaultValue = "0") int dateType,
                               @RequestParam(value = "start", required = false, defaultValue = "0") int start,
                               @RequestParam(value = "sort", required = false, defaultValue = "-11") String sort,
                               @RequestParam(value = "limit", required = false, defaultValue = "30") int limit) {
         Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        if(startDate == null || startDate.equals("")){
            startDate = yesterday;
            endDate = yesterday;
        }else if(endDate == null || endDate.equals("")){
            endDate = yesterday;
        }
        List<String> list = DateUtils.getPeriod(startDate, endDate);
        String[] newDate = list.toArray(new String[list.size()]);
        Map<String, List<StructureReportEntity>> responseDate = basisReportService.getReportDate(newDate, devices, dateType, reportType,start,limit,sort);
        StructureReportEntity objEntity = new StructureReportEntity();

        int totalRows =0;
        for(List<StructureReportEntity> entity : responseDate.values()){
            totalRows = totalRows + ((entity==null)?0:entity.size());
        }
        String data=new Gson().toJson(responseDate);
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
     * @param response
     * @param Sorted 排序方式
     * @param fieldName 排序字段
     */
    @RequestMapping(value = "/account/accountReport", method = RequestMethod.GET)
    public void getAccountReport(HttpServletResponse response,
                                 @RequestParam(value = "Sorted", required = false,defaultValue = "1") int Sorted,
                                 @RequestParam(value = "fieldName", required = false,defaultValue = "date") String fieldName,
                                 @RequestParam(value = "startJC", required = false,defaultValue = "0") int startJC,
                                 @RequestParam(value = "limitJC", required = false,defaultValue = "9") int limitJC){

            Map<String, List<AccountReportDTO>> returnAccount = basisReportService.getAccountAll(Sorted, fieldName,startJC,limitJC);

        String data=new Gson().toJson(returnAccount);

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
                                 @RequestParam(value = "sortVS", required = false,defaultValue = "-1") String sortVS,
                                 @RequestParam(value = "startVS", required = false,defaultValue = "0") int startVS,
                                 @RequestParam(value = "limitVS", required = false,defaultValue = "9") int limitVS){

        Map<String, List<Object>> returnAccount = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar YesterdayCal = Calendar.getInstance();
        YesterdayCal.add(Calendar.DATE,   -1);
        String Yesterday = dateFormat.format(YesterdayCal.getTime());
        Date endDate1;
        Date endDate2;
        Date endDate3;
        Date endDate4 = null;
        try {
            if(date1 == null || date1.equals("")){
                endDate1 = dateFormat.parse(Yesterday);
            }else{
                endDate1 = dateFormat.parse(date1);
            }
            if(date2 == null || date2.equals("")){
                endDate2 = dateFormat.parse(Yesterday);
            }else{
                endDate2 = dateFormat.parse(date2);
            }

            if(date3 == null || date3.equals("")){
                endDate3 = null;
            }else{
                endDate3 = dateFormat.parse(date3);
                Calendar cal = Calendar.getInstance();
                long kk = endDate3.getTime() + (endDate2.getTime()-endDate1.getTime());
                cal.setTimeInMillis(kk);
                endDate4 = cal.getTime();
            }

            returnAccount = basisReportService.getAccountDateVS(endDate1,endDate2,endDate3,endDate4,dateType,devices,compare,sortVS,startVS,limitVS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String data=new Gson().toJson(returnAccount);

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

}
