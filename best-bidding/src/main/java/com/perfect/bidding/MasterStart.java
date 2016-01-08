package com.perfect.bidding;

import com.perfect.bidding.core.ApplicationConfig;
import com.perfect.bidding.master.Master;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by baizz on 2015-1-6.
 */
public class MasterStart {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(ApplicationConfig.class);

        // start master
        new Master().start();

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
