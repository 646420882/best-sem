package com.perfect.schedule.task.conf.impl;

import com.perfect.schedule.core.taskmanager.ScheduleTaskType;

/**
 * Created by john on 2014/9/2.
 */
public class CountYesterdayCostTaskConfig extends BaseTaskConfig{

    public CountYesterdayCostTaskConfig(String baseTaskName, String ownSig, String dealBean, String cronExp, String[] taskDef) {
        super(baseTaskName, ownSig, dealBean, cronExp, taskDef);
    }


    @Override
    protected ScheduleTaskType createTaskType() {
        ScheduleTaskType scheduleTaskType = super.createTaskType();
        scheduleTaskType.setSleepTimeInterval(1000*60*60*24);
        scheduleTaskType.setPermitRunEndTime(null);

        return scheduleTaskType;
    }
}
