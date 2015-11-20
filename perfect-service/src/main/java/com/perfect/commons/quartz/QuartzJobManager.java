package com.perfect.commons.quartz;

import com.google.common.collect.Maps;
import org.quartz.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created on 2015-09-29.
 *
 * @author dolphineor
 */
public abstract class QuartzJobManager {

    protected final Scheduler scheduler;

    protected QuartzJobManager(Scheduler scheduler) {
        this.scheduler = scheduler;
    }


    public void start() {
        CronTrigger trigger = null;
        Map<String, ScheduledJob> tempJobMap = Maps.newHashMap();
        try {
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }

            for (ScheduledJob job : getAllScheduledJob()) {
                TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

                // 获取trigger, 即在spring配置文件中定义的bean id="myTrigger"
                trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

                // 不存在, 创建一个
                if (Objects.isNull(trigger)) {
                    JobDetail jobDetail = JobBuilder.newJob(QuartzJobExecutor.class)
                            .withIdentity(job.getJobName(), job.getJobGroup()).build();
                    jobDetail.getJobDataMap().put("scheduledJob", job);

                    // 表达式调度构建器
                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

                    // 按新的cronExpression表达式构建一个新的trigger
                    trigger = TriggerBuilder.newTrigger()
                            .withIdentity(job.getJobName(), job.getJobGroup())
                            .withSchedule(scheduleBuilder).build();

                    tempJobMap.put(trigger.getKey().getGroup() + "-" + trigger.getKey().getName(), job);

                    scheduler.scheduleJob(jobDetail, trigger);
                } else {
                    // Trigger已存在, 那么更新相应的定时设置
                    // 表达式调度构建器
                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

                    // 按新的cronExpression表达式重新构建trigger
                    trigger = trigger.getTriggerBuilder()
                            .withIdentity(triggerKey)
                            .withSchedule(scheduleBuilder).build();

                    tempJobMap.put(trigger.getKey().getGroup() + "-" + trigger.getKey().getName(), job);

                    // 按新的trigger重新设置job执行
                    scheduler.rescheduleJob(triggerKey, trigger);
                }
            }
        } catch (SchedulerException e) {
            if (Objects.nonNull(trigger)
                    && Objects.equals("Based on configured schedule, the given trigger '" + trigger.getKey() + "' will never fire.", e.getMessage())) {
                deleteJob(tempJobMap.get(trigger.getKey().getGroup() + "-" + trigger.getKey().getName()));
            }
        } finally {
            tempJobMap.clear();
        }
    }


    public abstract void addJob(ScheduledJob scheduledJob);

    public abstract void pauseJob(ScheduledJob scheduledJob);

    public abstract void resumeJob(ScheduledJob scheduledJob);

    public abstract void deleteJob(ScheduledJob scheduledJob);

    public abstract List<ScheduledJob> getAllScheduledJob();
}
