package com.perfect;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.Instant;

/**
 * Created by vbzer_000 on 2014/9/9.
 */


public class Scheduler {
    public static void main(String[] args) throws Exception {
        String xmlFile = "schedule.xml";

        if (args != null && args.length == 1) {
            xmlFile = args[0];
        } else if (args != null && args.length > 1) {
            System.err.println("can only support one spring context xml.");
            return;
        }

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlFile);

        while (true) {
            Thread.sleep(Instant.MAX.getEpochSecond());
        }
    }
}
