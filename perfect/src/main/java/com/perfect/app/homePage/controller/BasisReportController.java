package com.perfect.app.homePage.controller;

import com.google.gson.Gson;
import com.perfect.entity.AccountReportEntity;
import com.perfect.entity.AccountReportResponse;
import com.perfect.entity.StructureReportEntity;
import com.perfect.mongodb.utils.DateUtil;
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
import java.util.ArrayList;
import java.util.HashMap;
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
     * @param reportPageType 报告类型
     * @param reportNumber 每页条数
     */
    @RequestMapping(value = "/account/structureReport", method = RequestMethod.GET)
    public void getPerformance(HttpServletResponse response,
                               @RequestParam(value = "startDate", required = false) String startDate,
                               @RequestParam(value = "endDate", required = false) String endDate,
                               @RequestParam(value = "reportType", required = false, defaultValue = "1") int reportPageType,
                               @RequestParam(value = "devices", required = false, defaultValue = "0") int devices,
                               @RequestParam(value = "dateType", required = false, defaultValue = "0") int dateType,
                               @RequestParam(value = "reportNumber", required = false, defaultValue = "10") int reportNumber) {
        List<String> list = DateUtil.getPeriod(startDate, endDate);
        String[] newDate = list.toArray(new String[list.size()]);
        Map<String, List<StructureReportEntity>> responseDate = basisReportService.getUnitReportDate(newDate, devices, dateType, reportPageType,reportNumber);
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
                                 @RequestParam(value = "Sorted", required = false,defaultValue = "0") int Sorted,
                                 @RequestParam(value = "fieldName", required = false,defaultValue = "date") String fieldName){

        Map<String, List<AccountReportResponse>> returnAccount = basisReportService.getAccountAll(Sorted, fieldName);

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
