package com.perfect.bidding.threadFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by baizz on 2014-12-31.
 */
public class MyThreadFactory implements ThreadFactory {

    public MyThreadFactory() {

    }

    @Override
    public Thread newThread(Runnable r) {   // r为要执行的任务
        return new MyThread(r);
    }

    public static void main(String[] args) {
        Executors.newFixedThreadPool(4, new MyThreadFactory()).execute(new MyTask());
    }

}
