package com.perfect.app.homePage.controller;

import com.google.gson.Gson;
import com.perfect.entity.AccountReportEntity;
import com.perfect.entity.KeywordRealTimeDataVOEntity;
import com.perfect.service.PerformanceService;
import com.perfect.utils.JSONUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/7/25.
 */
@RestController
@Scope("prototype")
public class PerformanceController {
    @Resource
    public PerformanceService performanceService;

    /**
     * 账户表现
     * （暂未使用）
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "/account/performance", method = RequestMethod.GET)
    public void getPerformance(HttpServletResponse response) {
        String[] date = {"2014-01-25", "2014-01-26"};
        List<KeywordRealTimeDataVOEntity> jsonMapList = performanceService.performance("Perfect", date);
        Gson gson = new Gson();
        String ddd = gson.toJson(jsonMapList);

        try {
            response.setContentType("text/json; charset=UTF-8");
            response.getWriter().print(ddd);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 账户表现
     * @param response
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param limit 排序字段
     * @param Sorted 排序方式（0,降序， 1升序）
     */
    @RequestMapping(value = "/account/getPerformanceUser", method = RequestMethod.GET,produces = "application/json")
    public ModelAndView getPerformanceUser(HttpServletResponse response,
                                   @RequestParam(value = "startDate", required = false) String startDate,
                                   @RequestParam(value = "endDate", required = false) String endDate,
                                   @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                                   @RequestParam(value = "sort", required = false, defaultValue = "0") int Sorted,
                                   @RequestParam(value = "fieldName", required = false, defaultValue = "date") String fieldName) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDates = null;
        Date endDates = null;
        try {
            startDates = dateFormat.parse(startDate);
            endDates = dateFormat.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        List<AccountReportEntity> jsonMapList = performanceService.performanceUser(startDates, endDates, fieldName, Sorted,limit);

        Map<String, Object> attributes = null;
        if (jsonMapList != null)
            attributes = JSONUtils.getJsonMapData(jsonMapList.toArray());
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);

    }

    @RequestMapping(value = "/account/getPerformanceCurve", method = RequestMethod.GET,produces = "application/json")
    public ModelAndView getPerformanceCurve(HttpServletResponse response,
                                    @RequestParam(value = "startDate", required = false) String startDate,
                                    @RequestParam(value = "endDate", required = false) String endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDates = null;
        Date endDates = null;
        try {
            startDates = dateFormat.parse(startDate);
            endDates = dateFormat.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        List<AccountReportEntity> jsonMapList = performanceService.performanceCurve(startDates, endDates);

        Map<String, Object> attributes = null;
        if (jsonMapList != null)
            attributes = JSONUtils.getJsonMapData(jsonMapList.toArray()/*, "MM-dd"*/);
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }
}
