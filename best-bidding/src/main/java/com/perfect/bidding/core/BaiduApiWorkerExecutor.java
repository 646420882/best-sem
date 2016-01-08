package com.perfect.bidding.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by vbzer_000 on 2014/9/26.
 */
public class BaiduApiWorkerExecutor {

    private static ExecutorService executorService = Executors.newCachedThreadPool();


    public static void execute(BaiduApiWorker baiduApiWorker) {
        executorService.execute(baiduApiWorker);
    }

}
