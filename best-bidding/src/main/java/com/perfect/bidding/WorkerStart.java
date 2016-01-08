package com.perfect.bidding;

import com.perfect.bidding.core.ApplicationConfig;
import com.perfect.bidding.worker.Worker;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by baizz on 2015-1-6.
 */
public class WorkerStart {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(ApplicationConfig.class);

        // start worker
        new Worker().start();

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
