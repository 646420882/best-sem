package com.perfect.schedule.task.conf.impl;

import com.perfect.schedule.core.strategy.TBScheduleManagerFactory;
import com.perfect.schedule.task.conf.StrategyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by yousheng on 2014/7/29.
 *
 * @author yousheng
 */
public class ReportDataTaskConfig extends BaseTaskConfig {

    protected static transient Logger log = LoggerFactory
            .getLogger(UserUpdateTaskConfig.class);

    @Resource
    TBScheduleManagerFactory scheduleManagerFactory;

    public ReportDataTaskConfig(String baseTaskName, String ownSig, String dealBean, String cronExp, String[] taskDef) {
        super(baseTaskName, ownSig, dealBean, cronExp, taskDef);
    }

    public ReportDataTaskConfig(String baseTaskName, String ownSig, String dealBean, String cronExp, String[] taskDef, StrategyConfig strategyConfig) {
        super(baseTaskName, ownSig, dealBean, cronExp, taskDef, strategyConfig);
    }

    public void setScheduleManagerFactory(
            TBScheduleManagerFactory tbScheduleManagerFactory) {
        this.scheduleManagerFactory = tbScheduleManagerFactory;
    }

}
