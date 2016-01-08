package com.perfect.bidding.threadFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by baizz on 2014-12-31.
 */
public class MyThread extends Thread {

    public static final String DEFAULT_NAME = "WorkerThread";

    private static final AtomicInteger created = new AtomicInteger();

    private static final AtomicInteger alive = new AtomicInteger();


    public MyThread(Runnable target) {
        this(target, DEFAULT_NAME);
    }

    public MyThread(Runnable target, String name) {
        super(target, name + "-" + created.incrementAndGet());
    }

    @Override
    public void run() {
        try {
            alive.incrementAndGet();
            super.run();
        } finally {
            alive.decrementAndGet();
        }
    }

    public static int getThreadsCreated() {
        return created.get();
    }

    public static int getThreadsAlive() {
        return alive.get();
    }
}
