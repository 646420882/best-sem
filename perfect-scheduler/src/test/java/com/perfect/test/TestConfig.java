package com.perfect.test;

import com.perfect.schedule.core.strategy.TBScheduleManagerFactory;
import com.perfect.schedule.task.conf.impl.BaseTaskConfig;
import com.perfect.schedule.task.core.CronExpression;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

/**
 * Created by vbzer_000 on 2014/6/19.
 */
@SpringApplicationContext({ "schedule.xml"})
public class TestConfig extends UnitilsJUnit4 {
    protected static transient Logger log = LoggerFactory
            .getLogger(TestConfig.class);

    @SpringBeanByName
    TBScheduleManagerFactory scheduleManagerFactory;

    public void setScheduleManagerFactory(
            TBScheduleManagerFactory tbScheduleManagerFactory) {
        this.scheduleManagerFactory = tbScheduleManagerFactory;
    }

    @Test
    public void init() throws Exception {
        String taskDefItem = "0:{NAME=Jason},1:{NAME=Daisy}";
//        BaseTaskConfig baseTaskConfig = new BaseTaskConfig("BaseTaskConfig","TEST","empty", CronExpression.CRON_EVERY_MINUTE,taskDefItem.split(","));
        BaseTaskConfig baseTaskConfig = new BaseTaskConfig("BaseTaskConfig","test","countYesterdayCostTask", CronExpression.CRON_EVERY_MINUTE,taskDefItem.split(","));
        baseTaskConfig.setScheduleManagerFactory(scheduleManagerFactory);
        baseTaskConfig.createTask();
    }
}
