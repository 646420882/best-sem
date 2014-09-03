package com.perfect.test;

import com.perfect.schedule.core.strategy.TBScheduleManagerFactory;
import com.perfect.schedule.task.conf.impl.CountYesterdayCostTaskConfig;
import com.perfect.schedule.task.conf.impl.DataPullTaskConfig;
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
/*        String taskDefItem = "0:{NAME=Jason},1:{NAME=Daisy}";
        BaseTaskConfig baseTaskConfig = new BaseTaskConfig("BaseTaskConfig","TEST","empty", CronExpression.CRON_EVERY_MINUTE,taskDefItem.split(","));
        baseTaskConfig.setScheduleManagerFactory(scheduleManagerFactory);
        baseTaskConfig.createTask();*/
    }


    @Test
    public void initPullDataConfig(){
        DataPullTaskConfig dataPullTaskConfig = new DataPullTaskConfig("DataPullTaskConfig","dataPull","dataPullTask","* * 1 * * ?",new String[]{"A","B"});
        dataPullTaskConfig.setScheduleManagerFactory(scheduleManagerFactory);
        dataPullTaskConfig.createTask();
    }


    @Test
    public void initCountYesterdayCostConfig(){
        CountYesterdayCostTaskConfig countYesterdayCostTaskConfig = new CountYesterdayCostTaskConfig("CountYesterdayCostTaskConfig","accountWarningByEveryDay","countYesterdayCostTask","* 30 0 * * ?",new String[]{"A","B"});
        countYesterdayCostTaskConfig.setScheduleManagerFactory(scheduleManagerFactory);
        countYesterdayCostTaskConfig.createTask();
    }
}
