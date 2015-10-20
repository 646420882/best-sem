package com.perfect.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import static com.perfect.commons.constants.MongoEntityConstants.TBL_MATERIALS_SCHEDULER;

/**
 * <p>物料上传调度实体类</p>
 *
 * @author dolphineor
 * @date 2015-10-08
 * @package com.perfect.entity
 * @see com.perfect.dto.ScheduledJobDTO
 */
@Document(collection = TBL_MATERIALS_SCHEDULER)
public class ScheduledJobEntity {

    @Id
    private String id;

    /**
     * 任务id
     */
    private String jobId;

    /**
     * 任务名称
     */
    @Field("name")
    private String jobName;

    /**
     * 任务分组
     */
    @Field("group")
    private String jobGroup;

    /**
     * 任务类型
     */
    @Field("type")
    private int jobType;

    /**
     * 0  暂停
     * 1  启用
     */
    @Field("status")
    private int jobStatus;

    /**
     * 任务运行时间表达式
     */
    @Field("cron")
    private String cronExpression;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public int getJobType() {
        return jobType;
    }

    public void setJobType(int jobType) {
        this.jobType = jobType;
    }

    public int getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(int jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
}
