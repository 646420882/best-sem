package com.perfect.commons.quartz;

import com.google.common.collect.Lists;
import com.perfect.commons.constants.JobStatus;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Created on 2015-10-09.
 *
 * @author dolphineor
 */
public class QuartzJobMemoryManager extends QuartzJobManager {

    private final ConcurrentHashMap<String, ScheduledJob> jobMap = new ConcurrentHashMap<>();

    public QuartzJobMemoryManager(Scheduler scheduler) {
        super(scheduler);
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
