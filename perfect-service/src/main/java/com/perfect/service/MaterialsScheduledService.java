package com.perfect.service;

import com.perfect.commons.quartz.ScheduledJob;
import com.perfect.core.AppContext;

/**
 * Created on 2015-09-29.
 * <p>物料定时任务接口.
 *
 * @author dolphineor
 */
public interface MaterialsScheduledService {

    String MATERIALS_JOB_GROUP = "SEM_MATERIALS";


    /**
     * <p>配置定时任务
     *
     * @param jobType
     * @param cronExpression
     */
    void configureScheduler(int jobType, String cronExpression);

    /**
     * 暂停任务
     */
    void pauseJob();

    /**
     * 恢复任务
     */
    void resumeJob();

    /**
     * 删除任务
     */
    void deleteJob();


    default ScheduledJob getScheduledJob(String jobId) {
        return new ScheduledJob.Builder()
                .jobId(jobId)
                .jobName(AppContext.getUser())
                .jobGroup(MATERIALS_JOB_GROUP)
                .build();
    }
}
