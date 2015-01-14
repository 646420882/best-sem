package com.perfect.app.admin.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.perfect.commons.web.WebContextSupport;
import com.perfect.service.AccountManageService;
import com.perfect.service.AsynchronousReportService;
import com.perfect.utils.DateUtils;
import com.perfect.utils.json.JSONUtils;
import com.perfect.utils.redis.JRedisUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

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
                    asynchronousReportService.getRegionReportData(dateStr, userName);
                }
            }
            flag = 1;
            writeData(SUCCESS, response, null);
        } catch (Exception e) {
            e.printStackTrace();
            writeData(EXCEPTION, response, null);
            flag = -1;
        }
        List<String> strings = new ArrayList<>();
        Jedis jc = JRedisUtils.get();
        String data = jc.get("_administrator_PullLog");
        List<String> lists = new Gson().fromJson(data, new TypeToken<List<String>>() {
        }.getType());
        strings.addAll(lists);
        strings.add("数据拉取完毕");
        String jsonData = new Gson().toJson(strings);
        jc.set("_administrator_PullLog", jsonData);
        jc.expire("_administrator_PullLog", 10);
        if (jc != null) {
            JRedisUtils.returnJedis(jc);
        }
    }


    @RequestMapping(value = "/admin/reportPull/getPullLog", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getPullLog() {
        Jedis jc = JRedisUtils.get();
        String data = jc.get("_administrator_PullLog");
        List<String> list = new Gson().fromJson(data, new TypeToken<List<String>>() {
        }.getType());
        if (list == null || list.size() == 0) {
            list = new ArrayList<>();
            list.add("-1");
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attributes = JSONUtils.getJsonMapData(list);
        jsonView.setAttributesMap(attributes);
        if (jc != null) {
            JRedisUtils.returnJedis(jc);
        }
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/admin/reportPull/delRedisKey", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView delRedisKey(@RequestParam(value = "keyValues", required = false) String keyValues) {
        AbstractView jsonView = new MappingJackson2JsonView();
        if (keyValues != null || !keyValues.equals("")) {
            Jedis jc = JRedisUtils.get();
            Boolean keyState = jc.exists(keyValues);
            Long aLong = 0l;
            if (keyState) {
                aLong = jc.del(keyValues);
                System.out.println();
            }
            if (jc != null) {
                JRedisUtils.returnJedis(jc);
            }

            Map<String, Object> values = JSONUtils.getJsonMapData(aLong);

            jsonView.setAttributesMap(values);
        }
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/admin/reportPull/getRedisKey", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getRedisKey() {
        AbstractView jsonView = new MappingJackson2JsonView();

        Jedis jc = JRedisUtils.get();
        Set<String> keys = jc.keys("*");

        Map<String, Object> values = JSONUtils.getJsonMapData(keys);

        jsonView.setAttributesMap(values);

        if (jc != null) {
            JRedisUtils.returnJedis(jc);
        }

        return new ModelAndView(jsonView);
    }

}
