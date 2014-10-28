package com.perfect.tasks;

import com.perfect.dao.AsynchronousReportDAO;
import com.perfect.mongodb.utils.DateUtils;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private AsynchronousReportDAO asynchronousReportDAO;

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public boolean execute(List<String> task) throws Exception {
        for (String dateStr:task){
            asynchronousReportDAO.getAccountReportData(dateStr,null);

            log.info(dateFormat.format(new Date())+":账户报告 pull success!!!");

            asynchronousReportDAO.getCampaignReportData(dateStr,null);

            log.info(dateFormat.format(new Date())+":推广计划 pull success!!!");

            asynchronousReportDAO.getAdgroupReportData(dateStr,null);

            log.info(dateFormat.format(new Date())+":推广单元 pull success!!!");

            asynchronousReportDAO.getCreativeReportData(dateStr,null);

            log.info(dateFormat.format(new Date())+":创意  pull success!!!");

            asynchronousReportDAO.getKeywordReportData(dateStr,null);

            log.info(dateFormat.format(new Date())+":关键词  pull success!!!");

            asynchronousReportDAO.getRegionReportData(dateStr,null);

            log.info(dateFormat.format(new Date())+":推广地域 pull success!!!");

            log.info(dateFormat.format(new Date())+":定时拉取数据任务执行任务完毕!");
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
