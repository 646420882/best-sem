package com.perfect.commons.quartz;

import com.google.common.collect.Lists;
import com.perfect.commons.context.ApplicationContextHelper;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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


    public void run() throws SchedulerException {
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


    public void addJob(ScheduledJob scheduledJob) {
        jobMap.put(scheduledJob.getJobGroup() + "_" + scheduledJob.getJobName(), scheduledJob);
    }

    public List<ScheduledJob> getAllScheduledJob() {
        return Lists.newArrayList(jobMap.values());
    }
}
