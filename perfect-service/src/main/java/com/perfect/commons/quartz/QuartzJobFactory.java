package com.perfect.commons.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created on 2015-09-29.
 *
 * @author dolphineor
 */
@DisallowConcurrentExecution
public class QuartzJobFactory implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ScheduledJob scheduledJob = (ScheduledJob) context.getMergedJobDataMap().get("scheduledJob");
        System.out.println("==================================================");
    }
}
