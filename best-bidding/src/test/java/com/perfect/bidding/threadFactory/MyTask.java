package com.perfect.bidding.threadFactory;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * Created by baizz on 2014-12-31.
 */
public class MyTask implements Runnable {


    @Override
    public void run() {
        try {
            System.out.println("The current thread's name: " + Thread.currentThread().getName() + ", DateTime: " + LocalDateTime.now());
            TimeUnit.SECONDS.sleep(3);
            System.out.println("-DateTime: " + LocalDateTime.now());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}