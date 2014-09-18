package com.perfect.commons;

import com.perfect.api.baidu.BaiduApiServiceFactory;
import com.perfect.api.baidu.BaiduPreviewHelperFactory;
import com.perfect.commons.context.ApplicationContextHelper;
import com.perfect.elasticsearch.threads.EsThreadPoolTaskExecutor;
import com.perfect.schedule.task.execute.BiddingSubTask;
import com.perfect.service.BiddingLogService;
import com.perfect.service.BiddingRuleService;
import com.perfect.service.SysAdgroupService;
import com.perfect.service.SysCampaignService;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by vbzer_000 on 2014/9/16.
 */
@Component("biddingSubTaskFactory")
public class BiddingSubTaskFactory extends AbstractFactoryBean<BiddingSubTask> {

    @Resource
    private EsThreadPoolTaskExecutor executor;

    @Resource
    private BiddingLogService biddingLogService;

    @Resource
    private BiddingRuleService biddingRuleService;

    @Resource
    private SysAdgroupService sysAdgroupService;

    @Resource
    private ApplicationContextHelper applicationContextHelper;

    @Resource
    private BaiduPreviewHelperFactory baiduPreviewHelperFactory;

    @Resource
    private BaiduApiServiceFactory baiduApiServiceFactory;

    @Override
    public Class<?> getObjectType() {
        return BiddingSubTask.class;
    }

    @Override
    protected BiddingSubTask createInstance() throws Exception {
        BiddingSubTask task = new BiddingSubTask();

        task.setBiddingLogService(biddingLogService);
        task.setExecutor(executor);
        task.setBiddingRuleService(biddingRuleService);
        task.setAdgroupService(sysAdgroupService);
        task.setApplicationContextHelper(applicationContextHelper);
        task.setBaiduPreviewHelperFactory(baiduPreviewHelperFactory);
        task.setBaiduApiServiceFactory(baiduApiServiceFactory);
        return task;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
