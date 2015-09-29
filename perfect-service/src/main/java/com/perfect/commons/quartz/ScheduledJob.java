package com.perfect.commons.quartz;

/**
 * Created on 2015-09-29.
 *
 * @author dolphineor
 */
public class ScheduledJob {

    /**
     * 任务id
     */
    private final String jobId;

    /**
     * 任务名称
     */
    private final String jobName;

    /**
     * 任务分组
     */
    private final String jobGroup;

    /**
     * 0 禁用
     * 1 启用
     * 2 删除
     */
    private final String jobStatus;

    /**
     * 任务运行时间表达式
     */
    private final String cronExpression;

    /**
     * 任务描述
     */
    private final String jobDescription;


    private ScheduledJob(String jobId, String jobName, String jobGroup, String jobStatus, String cronExpression, String jobDescription) {
        this.jobId = jobId;
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.jobStatus = jobStatus;
        this.cronExpression = cronExpression;
        this.jobDescription = jobDescription;
    }

    public static class Builder {
        private String jobId;
        private String jobName;
        private String jobGroup;
        private String jobStatus;
        private String cronExpression;
        private String jobDescription;

        public Builder jobId(String jobId) {
            this.jobId = jobId;
            return this;
        }

        public Builder jobName(String jobName) {
            this.jobName = jobName;
            return this;
        }

        public Builder jobGroup(String jobGroup) {
            this.jobGroup = jobGroup;
            return this;
        }

        public Builder jobStatus(String jobStatus) {
            this.jobStatus = jobStatus;
            return this;
        }

        public Builder cronExpression(String cronExpression) {
            this.cronExpression = cronExpression;
            return this;
        }

        public Builder jobDescription(String jobDescription) {
            this.jobDescription = jobDescription;
            return this;
        }

        public ScheduledJob build() {
            return new ScheduledJob(jobId, jobName, jobGroup, jobStatus, cronExpression, jobDescription);
        }
    }

    public String getJobId() {
        return jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public String getJobDescription() {
        return jobDescription;
    }
}
