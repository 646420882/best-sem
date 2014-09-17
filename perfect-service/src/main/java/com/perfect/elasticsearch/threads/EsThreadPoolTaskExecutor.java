package com.perfect.elasticsearch.threads;

import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;

/**
 * Created by vbzer_000 on 2014/9/16.
 */
public class EsThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    protected ExecutorService initializeExecutor(ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        return super.initializeExecutor(threadFactory, rejectedExecutionHandler);
    }
}
