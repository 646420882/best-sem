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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.perfect.commons.constants.MaterialsJobEnum.ACTIVE;
import static com.perfect.commons.deduplication.Md5Helper.MD5.getMD5;

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
    public void configureScheduler(int jobType, int jobLevel, String[] jobContent, String cronExpression) {
        String jobName = AppContext.getUser() + ":" + AppContext.getAccountId();
        String userId = systemUserDAO.findByUserName(AppContext.getUser()).getId();
        String jobId = getMD5(userId + ":" + jobType + ":" + jobLevel + ":" + Arrays.asList(jobContent).toString());

        logger.info("Configure {}'s Scheduler: {}", jobName, cronExpression);

        quartzJobManager.addJob(
                new ScheduledJob.Builder()
                        .jobId(jobId)
                        .jobName(jobName)
                        .jobGroup(MATERIALS_JOB_GROUP)
                        .jobType(jobType)
                        .jobLevel(jobLevel)
                        .jobContent(jobContent)
                        .jobStatus(ACTIVE.value())
                        .cronExpression(cronExpression).build());
    }

    @Override
    public boolean isExists(int jobType, int jobLevel, String[] jobContent) {
        String userId = systemUserDAO.findByUserName(AppContext.getUser()).getId();
        String jobId = getMD5(userId + ":" + jobType + ":" + jobLevel + ":" + Arrays.asList(jobContent).toString());

        return msDAO.isExists(jobId);
    }

    @Override
    @Deprecated
    public void pauseJob(String jobId) {
        quartzJobManager.pauseJob(getScheduledJob(jobId));
    }

    @Override
    @Deprecated
    public void resumeJob(String jobId) {
        quartzJobManager.resumeJob(getScheduledJob(jobId));
    }

    @Override
    @Deprecated
    public void deleteJob(String jobId) {
        quartzJobManager.deleteJob(getScheduledJob(jobId));
    }


    @Override
    public void uploadAndStartMaterials(int level, String[] ids) {
        ;
    }

    @Override
    public void pauseMaterials(int level, String[] ids) {
        List<Long> materialIds = Arrays.stream(ids).map(Long::parseLong).collect(Collectors.toList());
    }

    private ScheduledJob getScheduledJob(String jobId) {
        return new ScheduledJob.Builder()
                .jobId(jobId)
                .jobName(msDAO.findByJobId(jobId).getJobName())
                .jobGroup(MATERIALS_JOB_GROUP)
                .build();
    }
}
