package com.perfect.test;

import com.perfect.schedule.core.strategy.TBScheduleManagerFactory;
import com.perfect.schedule.task.conf.impl.BiddingTaskConfig;
import com.perfect.schedule.task.conf.impl.CountYesterdayCostTaskConfig;
import com.perfect.schedule.task.conf.impl.DataPullTaskConfig;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

/**
 * Created by vbzer_000 on 2014/6/19.
 */
//@SpringApplicationContext({"schedule.xml"})
public class TestConfig {
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
    public void initPullDataConfig() {
        DataPullTaskConfig dataPullTaskConfig = new DataPullTaskConfig("DataPullTaskConfig", "dataPull", "dataPullTask", "* * 1 * * ?", new String[]{"A", "B"});
        dataPullTaskConfig.setScheduleManagerFactory(scheduleManagerFactory);
        dataPullTaskConfig.createTask();
    }


    @Test
    public void initCountYesterdayCostConfig() {
        CountYesterdayCostTaskConfig countYesterdayCostTaskConfig = new CountYesterdayCostTaskConfig("CountYesterdayCostTaskConfig", "accountWarningByEveryDay", "countYesterdayCostTask", "* 30 0 * * ?", new String[]{"A", "B"});
        countYesterdayCostTaskConfig.setScheduleManagerFactory(scheduleManagerFactory);
        countYesterdayCostTaskConfig.createTask();
    }

    @Test
    public void initAutoBiddingConfig() {
        BiddingTaskConfig biddingTaskConfig = new BiddingTaskConfig("BiddingTaskConfig", "TEST", "biddingTask", "startrun:0 * * * * ?", new String[]{"A,B"});
        biddingTaskConfig.setScheduleManagerFactory(scheduleManagerFactory);
        biddingTaskConfig.createTask();
    }

    public static void main(String args[]) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("schedule.xml");

        TBScheduleManagerFactory tbScheduleManagerFactory = (TBScheduleManagerFactory) applicationContext.getBean("scheduleManagerFactory");

        BiddingTaskConfig biddingTaskConfig = new BiddingTaskConfig("BiddingTaskConfig", "TEST", "biddingTask", null, new String[]{"A,B"});
        biddingTaskConfig.setScheduleManagerFactory(tbScheduleManagerFactory);

        try {
            while (!tbScheduleManagerFactory.isZookeeperInitialSucess()) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        biddingTaskConfig.createTask();

    }
}
