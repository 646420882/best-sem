package com.perfect.schedule.task.execute;

import com.perfect.dao.AsynchronousReportDAO;
import com.perfect.mongodb.utils.DateUtil;
import com.perfect.schedule.core.IScheduleTaskDealSingle;
import com.perfect.schedule.core.TaskItemDefine;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by john on 2014/8/23.
 * 定时拉取数据任务
 */
@Component("dataPullTask")
public class DataPullTask implements IScheduleTaskDealSingle<String>{


    @Resource
    private AsynchronousReportDAO asynchronousReportDAO;

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private  Date date;

    {
        try {
             date = df.parse(df.format(new Date().getTime()-(1000*60*60*24)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean execute(String task, String ownSign) throws Exception {
        asynchronousReportDAO.getAccountReportData(task);
        asynchronousReportDAO.getCampaignReportData(task);
        asynchronousReportDAO.getAdgroupReportData(task);
        asynchronousReportDAO.getCreativeReportData(task);
        asynchronousReportDAO.getKeywordReportData(task);
        asynchronousReportDAO.getRegionReportData(task);

        System.out.println("该次执行任务完毕!!!!!!!!!!!!!!!!!!");
        return true;
    }

    @Override
    public List<String> selectTasks(String taskParameter, String ownSign, int taskItemNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
        List<String> list = new ArrayList<>();
        Date today = df.parse(df.format(new Date()));

        if(today.getTime()>date.getTime()){
            String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(date.getTime());
            list.addAll(DateUtil.getPeriod(yesterday, yesterday));
            date = df.parse(df.format(new Date()));//标记为今天,代表着当天已经拉取过
        }
        return list;
    }

    @Override
    public Comparator<String> getComparator() {
        return null;
    }
}
