package com.perfect.bidding;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by vbzer_000 on 2014/9/24.
 */
public class Main {

    public static void main(String[] args) {
        String xml = "bidding.xml";

        if (args.length == 1) {
            xml = args[0];
        }

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(xml);

        while (true) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
