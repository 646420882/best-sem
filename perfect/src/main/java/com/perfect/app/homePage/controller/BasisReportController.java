package com.perfect.app.homePage.controller;

import com.perfect.entity.StructureReportEntity;
import com.perfect.mongodb.utils.DateUtil;
import com.perfect.service.BasisReportService;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping(value = "/account/basisReport", method = RequestMethod.GET)
    public void getPerformance(HttpServletResponse response,
                               @RequestParam(value = "startDate", required = false) String startDate,
                               @RequestParam(value = "endDate", required = false) String endDate) {
        List<String> list = DateUtil.getPeriod("2014-08-01", "2014-08-02");
        String[] newDate = (String[])list.toArray(new String[list.size()]);;
        Map<String, List<StructureReportEntity>> fff = basisReportService.getUnitReportDate(newDate, 0, 0);
        System.out.println(fff);
    }
}
