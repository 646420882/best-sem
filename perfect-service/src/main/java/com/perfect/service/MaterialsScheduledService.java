package com.perfect.service;

import com.perfect.commons.quartz.ScheduledJob;
import com.perfect.core.AppContext;

/**
 * Created on 2015-09-29.
 *
 * @author dolphineor
 */
public interface MaterialsScheduledService {

    String MATERIALS_JOB_GROUP = "SEM_MATERIALS";


    void configureScheduler(String cronExpression, String jobDescription);

    void pauseJob();

    void resumeJob();

    void deleteJob();


    default ScheduledJob getScheduledJob(String jobId) {
        return new ScheduledJob.Builder()
                .jobId(jobId)
                .jobName(AppContext.getUser())
                .jobGroup(MATERIALS_JOB_GROUP)
                .build();
    }
}
