package com.perfect.schedule.task.conf.impl;

import com.perfect.schedule.core.taskmanager.ScheduleTaskType;

/**
 * Created by yousheng on 2014/8/14.
 *
 * @author yousheng
 */
public class BiddingTaskConfig extends BaseTaskConfig {
    public BiddingTaskConfig(String baseTaskName, String ownSig, String dealBean, String cronExp, String[] taskDef) {
        super(baseTaskName, ownSig, dealBean, cronExp, taskDef);
    }


    @Override
    protected ScheduleTaskType createTaskType() {
        ScheduleTaskType scheduleTaskType = super.createTaskType();

        scheduleTaskType.setSleepTimeInterval(10 * 1000);

        scheduleTaskType.setPermitRunEndTime(null);

        return scheduleTaskType;
    }

}
