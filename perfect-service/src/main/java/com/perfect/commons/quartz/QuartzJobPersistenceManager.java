package com.perfect.commons.quartz;

import com.perfect.dao.MaterialsScheduledDAO;
import com.perfect.dto.ScheduledJobDTO;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 2015-10-08.
 *
 * @author dolphineor
 */
@Component
public class QuartzJobPersistenceManager extends QuartzJobManager {

    @Autowired
    private MaterialsScheduledDAO msDAO;

    public void setMaterialsScheduledDAO(@Qualifier("materialsScheduledDAO") MaterialsScheduledDAO materialsScheduledDAO) {
        this.msDAO = materialsScheduledDAO;
    }


    @Override
    public void pauseJob(ScheduledJob scheduledJob) {
        msDAO.pauseJob(buildScheduledJobDTO(scheduledJob));

        JobKey jobKey = JobKey.jobKey(scheduledJob.getJobName(), scheduledJob.getJobGroup());
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resumeJob(ScheduledJob scheduledJob) {
        msDAO.resumeJob(buildScheduledJobDTO(scheduledJob));

        JobKey jobKey = JobKey.jobKey(scheduledJob.getJobName(), scheduledJob.getJobGroup());
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteJob(ScheduledJob scheduledJob) {
        msDAO.deleteJob(buildScheduledJobDTO(scheduledJob));

        JobKey jobKey = JobKey.jobKey(scheduledJob.getJobName(), scheduledJob.getJobGroup());
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addJob(ScheduledJob scheduledJob) {
        msDAO.addJob(buildScheduledJobDTO(scheduledJob));

        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(scheduledJob.getJobName(), scheduledJob.getJobGroup());

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            if (trigger == null) {
                JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class)
                        .withIdentity(scheduledJob.getJobName(), scheduledJob.getJobGroup()).build();
                jobDetail.getJobDataMap().put("scheduledJob", scheduledJob);

                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduledJob.getCronExpression());

                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(scheduledJob.getJobName(), scheduledJob.getJobGroup())
                        .withSchedule(scheduleBuilder).build();

                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduledJob.getCronExpression());

                trigger = trigger.getTriggerBuilder()
                        .withIdentity(triggerKey)
                        .withSchedule(scheduleBuilder).build();

                scheduler.rescheduleJob(triggerKey, trigger);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ScheduledJob> getAllScheduledJob() {
        List<ScheduledJobDTO> scheduledJobDTOList = msDAO.getAllScheduledJob();

        return scheduledJobDTOList.stream().map(o -> new ScheduledJob.Builder()
                .jobId(o.getJobId())
                .jobName(o.getJobName())
                .jobGroup(o.getJobGroup())
                .jobStatus(o.getJobStatus())
                .cronExpression(o.getCronExpression())
                .jobDescription(o.getJobDescription()).build()).collect(Collectors.toList());
    }
}
