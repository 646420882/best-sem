package com.perfect.commons.quartz;

import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Created on 2015-10-09.
 * <p>物料上传调度管理器, 在web应用启动时自动运行.
 *
 * @author dolphineor
 * @since 1.1
 */
//@Configuration
public class MaterialsScheduler {

//    @Resource
    private Scheduler scheduler;

//    @Bean(initMethod = "start")
    public QuartzJobManager quartzJobManager() {
        return new QuartzJobPersistenceManager(scheduler);
    }
}
