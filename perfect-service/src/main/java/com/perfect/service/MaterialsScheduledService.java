package com.perfect.service;

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
     * <p>判断定时任务是否已经存在
     *
     * @param jobName
     * @param jobGroup
     * @param jobType
     * @return
     */
    boolean isExists(String jobName, String jobGroup, int jobType);

    /**
     * 暂停任务
     */
    void pauseJob(String jobId);

    /**
     * 恢复任务
     */
    void resumeJob(String jobId);

    /**
     * 删除任务
     */
    void deleteJob(String jobId);
}
