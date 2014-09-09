package com.perfect;

import com.perfect.schedule.core.strategy.TBScheduleManagerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by vbzer_000 on 2014/9/9.
 */


public class Main {
    public static void main(String[] args) throws Exception {
        String xmlFile = "schedule.xml";

        if (args != null && args.length == 1) {
            xmlFile = args[0];
        } else if (args.length > 1) {
            System.err.println("can only support one spring context xml.");
            return;
        }

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("schedule.xml");

        TBScheduleManagerFactory factory = (TBScheduleManagerFactory) applicationContext.getBean("scheduleManagerFactory");

        Thread.sleep(60 * 1000);
        if (factory == null || !factory.isZookeeperInitialSucess()) {
            System.out.println("Zookeeper initial failed!...exit");
            return;
        }

        while (true) {
            Thread.sleep(Long.MAX_VALUE);
        }
    }
}
