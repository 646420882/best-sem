package com.perfect.dao;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.ScheduledJobDTO;

import java.util.List;

/**
 * Created on 2015-10-08.
 *
 * @author dolphineor
 */
public interface MaterialsScheduledDAO extends HeyCrudRepository<ScheduledJobDTO, String> {

    String JOB_ID = "jobId";
    String JOB_NAME = "name";
    String JOB_GROUP = "group";
    String JOB_STATUS = "status";


    /**
     * <p>添加任务.
     *
     * @param scheduledJob
     */
    void addJob(ScheduledJobDTO scheduledJob);

    /**
     * <p>暂停任务.
     *
     * @param scheduledJob
     */
    void pauseJob(ScheduledJobDTO scheduledJob);

    /**
     * <p>重新恢复任务.
     *
     * @param scheduledJob
     */
    void resumeJob(ScheduledJobDTO scheduledJob);

    /**
     * <p>删除任务.
     *
     * @param scheduledJob
     */
    void deleteJob(ScheduledJobDTO scheduledJob);

    /**
     * <p>获取所有任务.
     *
     * @return {@code List<ScheduledJobDTO>}
     */
    List<ScheduledJobDTO> getAllScheduledJob();


    @Override
    default Class<ScheduledJobDTO> getDTOClass() {
        return ScheduledJobDTO.class;
    }
}
