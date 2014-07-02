package com.perfect.schedule.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by vbzer_000 on 2014/7/1.
 */
public class WorkPool {

    private static Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2 + 1);

    public static void pushTask(Runnable runnable) {
        executor.execute(runnable);
    }

}
