package com.perfect.schedule.task.execute;

import com.perfect.dao.AsynchronousReportDAO;
import com.perfect.mongodb.utils.DateUtils;
import com.perfect.schedule.core.IScheduleTaskDealSingle;
import com.perfect.schedule.core.TaskItemDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by john on 2014/8/23.
 * 定时拉取数据任务
 */
public class DataPullTask implements IScheduleTaskDealSingle<String> {

    protected static transient Logger log = LoggerFactory.getLogger(DataPullTask.class);
    @Resource
    private AsynchronousReportDAO asynchronousReportDAO;

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public boolean execute(String task, String ownSign) throws Exception {
        asynchronousReportDAO.getAccountReportData(task);
        log.info(dateFormat.format(new Date())+":账户报告 pull success!!!");
        asynchronousReportDAO.getCampaignReportData(task);
        log.info(dateFormat.format(new Date())+":推广计划 pull success!!!");
        asynchronousReportDAO.getAdgroupReportData(task);
        log.info(dateFormat.format(new Date())+":推广单元 pull success!!!");
        asynchronousReportDAO.getCreativeReportData(task);
        log.info(dateFormat.format(new Date())+":创意  pull success!!!");
        asynchronousReportDAO.getKeywordReportData(task);
        log.info(dateFormat.format(new Date())+":关键词  pull success!!!");
        asynchronousReportDAO.getRegionReportData(task);
        log.info(dateFormat.format(new Date())+":推广地域 pull success!!!");

        log.info(dateFormat.format(new Date())+":定时拉取数据任务执行任务完毕!");
        return true;
    }

    @Override
    public List<String> selectTasks(String taskParameter, String ownSign, int taskItemNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = df.format(cal.getTime());
        List<String> list = DateUtils.getPeriod(yesterday, yesterday);
        return list;
    }

    @Override
    public Comparator<String> getComparator() {
        return null;
    }
}
