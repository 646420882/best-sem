package com.perfect.schedule.core.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by vbzer_000 on 2014/6/18.
 */

public class ManagerFactoryTimerTask extends java.util.TimerTask {
    private static transient Logger log = LoggerFactory.getLogger(ManagerFactoryTimerTask.class);
    TBScheduleManagerFactory factory;
    int count =0;

    public ManagerFactoryTimerTask(TBScheduleManagerFactory aFactory) {
        this.factory = aFactory;
    }

    public void run() {
        try {
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
            if(this.factory.zkManager.checkZookeeperState() == false){
                if(count > 5){
                    log.error("Zookeeper连接失败，关闭所有的任务后，重新连接Zookeeper服务器......");
                    this.factory.reStart();

                }else{
                    count = count + 1;
                }
            }else{
                count = 0;
                this.factory.refresh();
            }
        } catch (Throwable ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}