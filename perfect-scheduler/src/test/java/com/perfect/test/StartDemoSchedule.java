package com.perfect.test;


import com.perfect.schedule.core.strategy.TBScheduleManagerFactory;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

import javax.annotation.Resource;

/**
 * @author xuannan
 */
public class StartDemoSchedule {
    TBScheduleManagerFactory scheduleManagerFactory;

    public void setScheduleManagerFactory(
            TBScheduleManagerFactory tbScheduleManagerFactory) {
        this.scheduleManagerFactory = tbScheduleManagerFactory;
    }

    public static void main(String args[]) throws InterruptedException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("schedule.xml");
        StartDemoSchedule startDemoSchedule = new StartDemoSchedule();
        startDemoSchedule.setScheduleManagerFactory((TBScheduleManagerFactory) applicationContext.getBean("scheduleManagerFactory"));
        Thread.sleep(100000000000000L);
    }
}
