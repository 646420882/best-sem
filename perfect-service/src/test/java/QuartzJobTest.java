import com.google.common.collect.Lists;
import com.perfect.commons.constants.MaterialsJobEnum;
import com.perfect.commons.quartz.QuartzJobExecutor;
import com.perfect.commons.quartz.ScheduledJob;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.Thread.currentThread;

/**
 * Created on 2015-09-30.
 *
 * @author dolphineor
 */
@RunWith(JUnit4.class)
public class QuartzJobTest {

    private final SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private final ConcurrentHashMap<String, ScheduledJob> jobMap = new ConcurrentHashMap<>();

    private Scheduler scheduler;


    @Before
    public void init() {
        try {
            scheduler = schedulerFactory.getScheduler();
            if (!scheduler.isShutdown())
                scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
            return;
        }

        ScheduledJob scheduledJob = new ScheduledJob.Builder()
                .jobId(UUID.randomUUID().toString())
                .jobName("test")
                .jobGroup("SEM_MATERIALS")
                .jobStatus(MaterialsJobEnum.ACTIVE.value())
                .cronExpression("*/3 * * * * ?").build();

        jobMap.put(scheduledJob.getJobGroup() + "_" + scheduledJob.getJobName(), scheduledJob);
    }

    @Test
    public void executeScheduler() {
        try {
            for (ScheduledJob job : Lists.newArrayList(jobMap.values())) {
                TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

                // 获取trigger, 即在spring配置文件中定义的bean id="myTrigger"
                CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

                if (trigger == null) {
                    JobDetail jobDetail = JobBuilder.newJob(QuartzJobExecutor.class)
                            .withIdentity(job.getJobName(), job.getJobGroup()).build();
                    jobDetail.getJobDataMap().put("scheduledJob", job);

                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

                    trigger = TriggerBuilder.newTrigger()
                            .withIdentity(job.getJobName(), job.getJobGroup())
                            .withSchedule(scheduleBuilder).build();

                    scheduler.scheduleJob(jobDetail, trigger);

//                    JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
//                    scheduler.triggerJob(jobKey);
                } else {
                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

                    trigger = trigger.getTriggerBuilder()
                            .withIdentity(triggerKey)
                            .withSchedule(scheduleBuilder).build();

                    scheduler.rescheduleJob(triggerKey, trigger);
                }
            }

            currentThread().join();
        } catch (SchedulerException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
