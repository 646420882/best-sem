package com.perfect.bidding.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * Created by vbzer_000 on 2014/9/24.
 */
public class BiddingWorkerExecutor {

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void execute(Runnable runnable) {
        executorService.execute(runnable);
    }

    public static Future submit(BiddingSubTask spiderSubTask) {
        return executorService.submit(spiderSubTask);
    }
}
