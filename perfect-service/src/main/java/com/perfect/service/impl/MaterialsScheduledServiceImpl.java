package com.perfect.service.impl;

import com.perfect.commons.quartz.QuartzJobManager;
import com.perfect.commons.quartz.ScheduledJob;
import com.perfect.core.AppContext;
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.service.MaterialsScheduledService;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.perfect.commons.quartz.JobStatus.ACTIVE;

/**
 * Created on 2015-09-29.
 *
 * @author dolphineor
 */
@Service("materialsScheduledService")
public class MaterialsScheduledServiceImpl implements MaterialsScheduledService {

    private static final Logger logger = LoggerFactory.getLogger(MaterialsScheduledServiceImpl.class);

    @Resource
    private SystemUserDAO systemUserDAO;

    @Resource
    private QuartzJobManager quartzJobManager;


    @Override
    public void configureScheduler(String cronExpression, String jobDescription) {
        String userName = AppContext.getUser();
        String userId = systemUserDAO.findByUserName(userName).getId();

        logger.info("Configure {}'s Scheduler: {}", userName, cronExpression);

        quartzJobManager.addJob(
                new ScheduledJob.Builder()
                        .jobId(userId)
                        .jobName(userName)
                        .jobGroup(MATERIALS_JOB_GROUP)
                        .jobStatus(ACTIVE.value())
                        .cronExpression(cronExpression)
                        .jobDescription(jobDescription).build());

        try {
            quartzJobManager.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pauseJob() {
        //
    }

    @Override
    public void resumeJob() {
        //
    }

    @Override
    public void deleteJob() {
        //
    }
}
