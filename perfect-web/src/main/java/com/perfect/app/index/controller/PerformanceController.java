package com.perfect.app.index.controller;

import com.perfect.utils.DateUtils;
import com.perfect.dto.account.AccountReportDTO;
import com.perfect.utils.json.JSONUtils;
import com.perfect.service.PerformanceService;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
     *
     * @param response
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param limit     排序字段
     * @param Sorted    排序方式（0,降序， 1升序）
     */
    @RequestMapping(value = "/account/getPerformanceUser", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getPerformanceUser(HttpServletResponse response,
                                           @RequestParam(value = "startDate", required = false) String startDate,
                                           @RequestParam(value = "endDate", required = false) String endDate,
                                           @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                                           @RequestParam(value = "startPer", required = false, defaultValue = "0") int startPer,
                                           @RequestParam(value = "sort", required = false, defaultValue = "0") String Sorted) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDates = null;
        Date endDates = null;
        List<String> date = null;
        try {
            if (startDate == null || "".equals(startDate)) {
                startDates = dateFormat.parse(dateFormat.format(new Date()));
            } else {
                startDates = dateFormat.parse(startDate);
            }
            if (endDate == null || "".equals(endDate)) {
                endDates = dateFormat.parse(dateFormat.format(new Date()));
            } else {
                endDates = dateFormat.parse(endDate);
            }
            date = DateUtils.getPeriod(startDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        List<AccountReportDTO> jsonMapList = performanceService.performanceUser(startDates, endDates, Sorted, limit, startPer, date);

        Map<String, Object> attributes = null;
        if (jsonMapList != null)
            attributes = JSONUtils.getJsonMapData(jsonMapList.toArray());
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);

    }

    @RequestMapping(value = "/account/getPerformanceCurve", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getPerformanceCurve(HttpServletResponse response,
                                            @RequestParam(value = "startDate", required = false) String startDate,
                                            @RequestParam(value = "endDate", required = false) String endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDates = null;
        Date endDates = null;
        try {
            if (startDate == null || "".equals(startDate)) {
                startDates = dateFormat.parse(dateFormat.format(new Date()));
            } else {
                startDates = dateFormat.parse(startDate);
            }
            if (endDate == null || "".equals(endDate)) {
                endDates = dateFormat.parse(dateFormat.format(new Date()));
            } else {
                endDates = dateFormat.parse(endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        List<AccountReportDTO> jsonMapList = performanceService.performanceCurve(startDates, endDates);

        Map<String, Object> attributes = null;
        if (jsonMapList != null)
            attributes = JSONUtils.getJsonMapData(jsonMapList.toArray());
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/account/downAccountCSV", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ModelAndView downAccountCSV(HttpServletResponse response,
                                       @RequestParam(value = "startDate", required = false) String startDates,
                                       @RequestParam(value = "endDate", required = false) String endDates) {
        Date start = null;
        Date end = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (startDates == null || "".equals(startDates)) {

                start = dateFormat.parse(dateFormat.format(new Date()));

            } else {
                start = dateFormat.parse(startDates);
            }
            if (endDates == null || "".equals(endDates)) {
                end = dateFormat.parse(dateFormat.format(new Date()));
            } else {
                end = dateFormat.parse(endDates);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<String> dates = DateUtils.getPeriod(startDates, endDates);
        String filename = UUID.randomUUID().toString().replace("-", "") + ".csv";
        OutputStream os = null;
        try {
            response.addHeader("Content-Disposition", "attachment;filename=" + new String((filename).getBytes("UTF-8"), "ISO8859-1"));
            os = response.getOutputStream();
            performanceService.downAccountCSV(os, start, end, dates);
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
