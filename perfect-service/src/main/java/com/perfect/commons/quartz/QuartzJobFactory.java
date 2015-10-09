package com.perfect.commons.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2015-09-29.
 * <p>物料上传任务执行器.
 *
 * @author dolphineor
 */
@DisallowConcurrentExecution
public class QuartzJobFactory implements Job {

    private static final Logger logger = LoggerFactory.getLogger(QuartzJobFactory.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ScheduledJob scheduledJob = (ScheduledJob) context.getMergedJobDataMap().get("scheduledJob");
        // TODO 物料定时上传
//        logger.info("scheduledJob'name: {}, cronExpression: {}", scheduledJob.getJobName(), scheduledJob.getCronExpression());
    }
}
