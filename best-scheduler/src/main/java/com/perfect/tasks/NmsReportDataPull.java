package com.perfect.tasks;

import com.perfect.service.AsynchronousNmsReportService;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by subdong on 15-7-22.
 */
public class NmsReportDataPull {
    protected static transient Logger log = LoggerFactory.getLogger(NmsReportDataPull.class);
    @Resource
    private AsynchronousNmsReportService asynchronousNmsReportService;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public boolean execute(Date[] task) throws Exception {
        asynchronousNmsReportService.retrieveReport(task);

        log.info(dateFormat.format(new Date()) + ":=============>>>>>   Timing pull NmsData task execution is completed!");

        return true;
    }

    public Date[] selectTasks() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date[] date = new Date[]{cal.getTime(),cal.getTime()};
        return date;
    }

    public void pullNmsStart() throws JobExecutionException {
        try {
            Date[] taskObjects = selectTasks();
            System.out.println("=====================");
            execute(taskObjects);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
