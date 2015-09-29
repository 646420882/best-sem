package com.perfect.service.impl;

import com.perfect.commons.quartz.QuartzJobManager;
import com.perfect.commons.quartz.ScheduledJob;
import com.perfect.service.MaterialsScheduledService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created on 2015-09-29.
 *
 * @author dolphineor
 */
@Service("materialsScheduledService")
public class MaterialsScheduledServiceImpl implements MaterialsScheduledService {

    @Resource
    private QuartzJobManager quartzJobManager;

    @Override
    public void configureScheduler() {
        System.out.println("Configure Scheduler...");
        quartzJobManager.addJob(
                new ScheduledJob.Builder()
                        .jobId(UUID.randomUUID().toString())
                        .jobName("test")
                        .jobGroup("testGroup")
                        .jobStatus("1")
                        .cronExpression("0 0 23 * * ?")
                        .jobDescription("").build());
    }
}
