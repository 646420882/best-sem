package com.perfect.tasks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.perfect.service.AsynchronousReportService;
import com.perfect.utils.DateUtils;
import com.perfect.utils.redis.JRedisUtils;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by john on 2014/8/23.
 * 定时拉取数据任务
 */
public class ReportDataPull {

    protected static transient Logger log = LoggerFactory.getLogger(ReportDataPull.class);
    @Resource
    private AsynchronousReportService asynchronousReportService;

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public boolean execute(List<String> task) throws Exception {
        Jedis jc = JRedisUtils.get();
        Long jedisKey = jc.ttl("_administrator_PullLog");
        if (jedisKey == -1 || jedisKey == -2) {
            for (String dateStr : task) {
                asynchronousReportService.getAccountReportData(dateStr, null);

                log.info(dateFormat.format(new Date()) + ":=============>>>>>   User Report Pull success!!!");

                asynchronousReportService.getCampaignReportData(dateStr, null);

                log.info(dateFormat.format(new Date()) + ":=============>>>>>   Promotion Scheme Pull success!!!");

                asynchronousReportService.getAdgroupReportData(dateStr, null);

                log.info(dateFormat.format(new Date()) + ":=============>>>>>   Promotion Unit Pull success!!!");

                asynchronousReportService.getCreativeReportData(dateStr, null);

                log.info(dateFormat.format(new Date()) + ":=============>>>>>   Creative  Pull success!!!");

                asynchronousReportService.getKeywordReportData(dateStr, null);

                log.info(dateFormat.format(new Date()) + ":=============>>>>>   Keyword  Pull success!!!");

                asynchronousReportService.getRegionReportData(dateStr, null);

                log.info(dateFormat.format(new Date()) + ":=============>>>>>   Promotion Region Pull success!!!");

                log.info(dateFormat.format(new Date()) + ":=============>>>>>   Timing pull data task execution is completed!");
            }
            String data = jc.get("_administrator_PullLog");
            List<String> strings = new ArrayList<>();
            List<String> lists = new Gson().fromJson(data, new TypeToken<List<String>>() {
            }.getType());
            strings.addAll(lists);
            strings.add("数据拉取完毕");
            String jsonData = new Gson().toJson(strings);
            jc.set("_administrator_PullLog", jsonData);
            jc.expire("_administrator_PullLog", 15);
        }

        return true;
    }

    public List<String> selectTasks() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = df.format(cal.getTime());
        List<String> list = DateUtils.getPeriod(yesterday, yesterday);
        return list;
    }

    public void pullStart() throws JobExecutionException {
        try {
            List<String> taskObjects = selectTasks();

            execute(taskObjects);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
