package com.perfect.elasticsearch.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by vbzer_000 on 2014/9/16.
 */
public class EsThreadPoolTaskExecutor {

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void execute(Runnable runnable) {
        executorService.execute(runnable);
    }
}
