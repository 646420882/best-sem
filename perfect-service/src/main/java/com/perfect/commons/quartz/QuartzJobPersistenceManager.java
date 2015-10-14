package com.perfect.commons.quartz;

import com.perfect.dao.MaterialsScheduledDAO;
import com.perfect.dto.ScheduledJobDTO;
import org.quartz.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 2015-10-08.
 *
 * @author dolphineor
 */
public class QuartzJobPersistenceManager extends QuartzJobManager {

    @Resource(name = "materialsScheduledDAO")
    private MaterialsScheduledDAO msDAO;

    public QuartzJobPersistenceManager(Scheduler scheduler) {
        super(scheduler);
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
                JobDetail jobDetail = JobBuilder.newJob(QuartzJobExecutor.class)
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
                .cronExpression(o.getCronExpression()).build()).collect(Collectors.toList());
    }


    protected ScheduledJobDTO buildScheduledJobDTO(ScheduledJob scheduledJob) {
        ScheduledJobDTO scheduledJobDTO = new ScheduledJobDTO();

        scheduledJobDTO.setJobId(scheduledJob.getJobId());
        scheduledJobDTO.setJobName(scheduledJob.getJobName());
        scheduledJobDTO.setJobGroup(scheduledJob.getJobGroup());
        scheduledJobDTO.setJobStatus(scheduledJob.getJobStatus());
        scheduledJobDTO.setCronExpression(scheduledJob.getCronExpression());

        return scheduledJobDTO;
    }
}
