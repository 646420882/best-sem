package com.perfect.bidding.master;

import com.perfect.bidding.redis.Constants;
import com.perfect.bidding.redis.JRedisPools;
import redis.clients.jedis.Jedis;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yousheng on 15/1/4.
 */
public class BackupMasterTask implements Runnable, Constants {

    private final ReentrantLock restartLock;
    private final Condition restartCondition;

    public BackupMasterTask(ReentrantLock restartLock, Condition restartCondition) {
        this.restartLock = restartLock;
        this.restartCondition = restartCondition;
    }

    @Override
    public void run() {
        restartLock.lock();

        try {
            while (true) {
                Jedis jedis = null;
                try {
                    jedis = JRedisPools.getConnection();
                    if (jedis.exists(BIDDING_MASTER_ID)) {
                        Thread.sleep(1_000);
                    } else {
                        restartCondition.signalAll();
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (jedis != null) {
                        JRedisPools.returnJedis(jedis);
                    }
                }
            }
        } finally {
            restartLock.unlock();
        }
    }
}
