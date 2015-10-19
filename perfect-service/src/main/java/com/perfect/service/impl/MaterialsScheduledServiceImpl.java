package com.perfect.service.impl;

import com.perfect.commons.context.ApplicationContextHelper;
import com.perfect.commons.quartz.QuartzJobManager;
import com.perfect.commons.quartz.QuartzJobPersistenceManager;
import com.perfect.commons.quartz.ScheduledJob;
import com.perfect.core.AppContext;
import com.perfect.dao.MaterialsScheduledDAO;
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.service.MaterialsScheduledService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

import static com.perfect.commons.constants.MaterialsJobEnum.ACTIVE;

/**
 * Created on 2015-09-29.
 *
 * @author dolphineor
 */
@Service("materialsScheduledService")
public class MaterialsScheduledServiceImpl implements MaterialsScheduledService {

    private static final Logger logger = LoggerFactory.getLogger(MaterialsScheduledServiceImpl.class);

    private final QuartzJobManager quartzJobManager =
            (QuartzJobPersistenceManager) ApplicationContextHelper.getBeanByName("quartzJobManager");

    @Resource
    private SystemUserDAO systemUserDAO;

    @Resource(name = "materialsScheduledDAO")
    private MaterialsScheduledDAO msDAO;


    @Override
    public void configureScheduler(int jobType, String cronExpression) {
        String userName = AppContext.getUser();
        String userId = systemUserDAO.findByUserName(userName).getId();

        logger.info("Configure {}'s Scheduler: {}", userName, cronExpression);

        quartzJobManager.addJob(
                new ScheduledJob.Builder()
                        .jobId(userId)
                        .jobName(userName)
                        .jobGroup(MATERIALS_JOB_GROUP)
                        .jobType(jobType)
                        .jobStatus(ACTIVE.value())
                        .cronExpression(cronExpression).build());
    }

    @Override
    public boolean isExists(String jobName, String jobGroup, int jobType) {
        if (Objects.isNull(jobGroup) || jobGroup.isEmpty())
            jobGroup = MATERIALS_JOB_GROUP;

        return msDAO.isExists(jobName, jobGroup, jobType);
    }

    @Override
    public void pauseJob(String jobId) {
        quartzJobManager.pauseJob(getScheduledJob(jobId));
    }

    @Override
    public void resumeJob(String jobId) {
        quartzJobManager.resumeJob(getScheduledJob(jobId));
    }

    @Override
    public void deleteJob(String jobId) {
        quartzJobManager.deleteJob(getScheduledJob(jobId));
    }


    private ScheduledJob getScheduledJob(String jobId) {
        return new ScheduledJob.Builder()
                .jobId(jobId)
                .jobName(msDAO.findByJobId(jobId).getJobName())
                .jobGroup(MATERIALS_JOB_GROUP)
                .build();
    }
}
