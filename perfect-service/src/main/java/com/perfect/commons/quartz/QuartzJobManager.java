package com.perfect.commons.quartz;

import com.google.common.collect.Lists;
import com.perfect.commons.context.ApplicationContextHelper;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Created on 2015-09-29.
 *
 * @author dolphineor
 */
@Component
public class QuartzJobManager {

    private final ConcurrentHashMap<String, ScheduledJob> jobMap = new ConcurrentHashMap<>();

    private final SchedulerFactoryBean schedulerFactoryBean =
            (SchedulerFactoryBean) ApplicationContextHelper.getBeanByName("schedulerFactoryBean");

    private final Scheduler scheduler = schedulerFactoryBean.getScheduler();


    public void start() throws SchedulerException {
        if (!scheduler.isShutdown())
            scheduler.start();

        for (ScheduledJob job : getAllScheduledJob()) {
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

            // 获取trigger, 即在spring配置文件中定义的bean id="myTrigger"
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 不存在, 创建一个
            if (trigger == null) {
                JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class)
                        .withIdentity(job.getJobName(), job.getJobGroup()).build();
                jobDetail.getJobDataMap().put("scheduledJob", job);

                // 表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

                // 按新的cronExpression表达式构建一个新的trigger
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(job.getJobName(), job.getJobGroup())
                        .withSchedule(scheduleBuilder).build();

                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                // Trigger已存在, 那么更新相应的定时设置
                // 表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

                // 按新的cronExpression表达式重新构建trigger
                trigger = trigger.getTriggerBuilder()
                        .withIdentity(triggerKey)
                        .withSchedule(scheduleBuilder).build();

                // 按新的trigger重新设置job执行
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        }
    }


    public void pauseJob(ScheduledJob scheduledJob) {
        JobKey jobKey = JobKey.jobKey(scheduledJob.getJobName(), scheduledJob.getJobGroup());
        try {
            scheduler.pauseJob(jobKey);

            jobMap.searchValues(1, new Function<ScheduledJob, Optional<ScheduledJob>>() {
                @Override
                public Optional<ScheduledJob> apply(ScheduledJob job) {
                    if ((Objects.equals(scheduledJob.getJobName(), job.getJobName())) &&
                            (Objects.equals(scheduledJob.getJobGroup(), scheduledJob.getJobName())))
                        return Optional.of(job);

                    return Optional.empty();
                }
            }).ifPresent(job ->
                    jobMap.put(job.getJobId(), new ScheduledJob.Builder()
                            .jobId(job.getJobId())
                            .jobName(job.getJobName())
                            .jobGroup(job.getJobGroup())
                            .jobStatus(JobStatus.PAUSE.value())
                            .cronExpression(job.getCronExpression())
                            .jobDescription(job.getJobDescription())
                            .build()));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void resumeJob(ScheduledJob scheduledJob) {
        JobKey jobKey = JobKey.jobKey(scheduledJob.getJobName(), scheduledJob.getJobGroup());
        try {
            scheduler.resumeJob(jobKey);

            jobMap.searchValues(1, new Function<ScheduledJob, Optional<ScheduledJob>>() {
                @Override
                public Optional<ScheduledJob> apply(ScheduledJob job) {
                    if ((Objects.equals(scheduledJob.getJobName(), job.getJobName())) &&
                            (Objects.equals(scheduledJob.getJobGroup(), scheduledJob.getJobName())))
                        return Optional.of(job);

                    return Optional.empty();
                }
            }).ifPresent(job ->
                    jobMap.put(job.getJobId(), new ScheduledJob.Builder()
                            .jobId(job.getJobId())
                            .jobName(job.getJobName())
                            .jobGroup(job.getJobGroup())
                            .jobStatus(JobStatus.ACTIVE.value())
                            .cronExpression(job.getCronExpression())
                            .jobDescription(job.getJobDescription())
                            .build()));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void deleteJob(ScheduledJob scheduledJob) {
        JobKey jobKey = JobKey.jobKey(scheduledJob.getJobName(), scheduledJob.getJobGroup());
        try {
            scheduler.deleteJob(jobKey);

            /**
             * Remove job from {@link #jobMap}
             */
            jobMap.searchValues(1, job -> {
                if ((Objects.equals(scheduledJob.getJobName(), job.getJobName())) &&
                        (Objects.equals(scheduledJob.getJobGroup(), scheduledJob.getJobName())))
                    return Optional.of(job.getJobId());

                return Optional.empty();
            }).ifPresent(jobMap::remove);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void addJob(ScheduledJob scheduledJob) {
        jobMap.put(scheduledJob.getJobId(), scheduledJob);
    }

    public List<ScheduledJob> getAllScheduledJob() {
        return Lists.newArrayList(jobMap.values());
    }
}
