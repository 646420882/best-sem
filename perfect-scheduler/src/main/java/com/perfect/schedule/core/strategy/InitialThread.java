package com.perfect.schedule.core.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by vbzer_000 on 2014/6/18.
 */
public class InitialThread extends Thread {
    private static transient Logger log = LoggerFactory.getLogger(InitialThread.class);
    TBScheduleManagerFactory factory;
    boolean isStop = false;
    public InitialThread(TBScheduleManagerFactory aFactory){
        this.factory = aFactory;
    }
    public void stopThread(){
        this.isStop = true;
    }
    @Override
    public void run() {
        factory.lock.lock();
        try {
            int count =0;
            while(factory.zkManager.checkZookeeperState() == false){
                count = count + 1;
                if(count % 50 == 0){
                    factory.errorMessage = "Zookeeper connecting ......" + factory.zkManager.getConnectStr() + " spendTime:" + count * 20 +"(ms)";
                    log.error(factory.errorMessage);
                }
                Thread.sleep(20);
                if(this.isStop ==true){
                    return;
                }
            }
            factory.initialData();
        } catch (Throwable e) {
            log.error(e.getMessage(),e);
        }finally{
            factory.lock.unlock();
        }

    }

}