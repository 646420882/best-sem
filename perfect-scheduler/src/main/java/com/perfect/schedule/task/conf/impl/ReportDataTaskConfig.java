package com.perfect.schedule.task.conf.impl;

import com.perfect.schedule.core.strategy.TBScheduleManagerFactory;
import com.perfect.schedule.task.conf.TaskConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by yousheng on 2014/7/29.
 *
 * @author yousheng
 */
public class ReportDataTaskConfig implements TaskConfig {
    protected static transient Logger log = LoggerFactory
            .getLogger(UserUpdateTaskConfig.class);

    @Resource
    TBScheduleManagerFactory scheduleManagerFactory;

    public void setScheduleManagerFactory(
            TBScheduleManagerFactory tbScheduleManagerFactory) {
        this.scheduleManagerFactory = tbScheduleManagerFactory;
    }


    @Override
    public void createTask() {

    }
}
