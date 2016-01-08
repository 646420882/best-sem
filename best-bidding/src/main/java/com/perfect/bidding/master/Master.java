package com.perfect.bidding.master;

import com.perfect.bidding.core.CookieHeartBeat;
import com.perfect.bidding.redis.Constants;
import com.perfect.bidding.redis.JRedisPools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by vbzer_000 on 2014/9/23.
 */
public class Master extends JedisPubSub implements Constants {

    private static final Logger LOGGER = LoggerFactory.getLogger(Master.class);

    private ReentrantLock restartLock = new ReentrantLock();


    public void start() {
        String masterId = "master-" + UUID.randomUUID().toString().replace("-", "");

        // master 启动流程
        while (true) {
            if (!masterIsExists(masterId)) {
                masterOn(masterId);
                break;
            } else {
                LOGGER.info("Master backup thread!");

                restartLock.lock();
                Condition restart = restartLock.newCondition();

                try {
                    Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new BackupMasterTask(restartLock, restart), 0, 1, TimeUnit.SECONDS);

                    try {
                        restart.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    LOGGER.info("Master job restart.");

                } finally {
                    restartLock.unlock();
                }
            }
        }
    }


    private void masterOn(String masterId) {

        LOGGER.info("Master on " + masterId);
        // initialize
        init(masterId);

        // master 心跳设置
        heartBeat(masterId);

        // 订阅消息
        subscribeExpiredMessage();
        subscribeFinishMessage();

        // 启动master任务
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new MasterTask(), 0, 5, TimeUnit.MINUTES);

        // 启动监听器
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(() -> {
            Jedis jedis = null;
            try {
                jedis = JRedisPools.getConnection();
                Set<String> keys = jedis.keys(BIDDING_WORKER_REGEX);
                for (String key : keys) {
                    if (isExpired(key)) {
                        LOGGER.info("the worker(" + key + ") is downtime.");
                        WorkerList.remove(key);
                        jedis.publish(BIDDING_EXPIRE_WORKER_CHANNEL, key);
                    }
                }
            } finally {
                if (jedis != null)
                    JRedisPools.returnJedis(jedis);
            }

        }, 0, 5, TimeUnit.SECONDS);
    }

    private boolean masterIsExists(String masterId) {
        Jedis jedis = null;
        try {
            jedis = JRedisPools.getConnection();
            long rev = jedis.setnx(BIDDING_MASTER_ID, masterId);
            return (rev == 0);
        } finally {
            if (jedis != null) {
                JRedisPools.returnJedis(jedis);
            }
        }
    }

    private void init(String masterId) {
        Jedis jedis = null;
        try {
            jedis = JRedisPools.getConnection();
            jedis.setex(BIDDING_MASTER_ID, 10, masterId);
        } finally {
            if (jedis != null) {
                JRedisPools.returnJedis(jedis);
            }
        }

        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new WorkerList(), 0, 1, TimeUnit.SECONDS);
    }

    private void heartBeat(String masterId) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            Jedis jedis = null;
            try {
                jedis = JRedisPools.getConnection();
                jedis.setex(BIDDING_MASTER_ID, 10, masterId);
            } finally {
                if (jedis != null) {
                    JRedisPools.returnJedis(jedis);
                }
            }
        }, 5, 1, TimeUnit.SECONDS);

        // cookie heartBeat
        new Thread(() -> new CookieHeartBeat().start()).start();

    }

    private void subscribeExpiredMessage() {
        Executors.newSingleThreadExecutor().execute(() -> {
            Jedis jedis = null;
            try {
                jedis = JRedisPools.getConnection();
                jedis.subscribe(new ExpireTask(), BIDDING_EXPIRE_WORKER_CHANNEL);
            } finally {
                if (jedis != null) {
                    JRedisPools.returnJedis(jedis);
                }
            }
        });
    }

    private void subscribeFinishMessage() {
        Executors.newSingleThreadExecutor().execute(() -> {
            Jedis jedis = null;
            try {
                jedis = JRedisPools.getConnection();
                jedis.psubscribe(new FinishTask(), BIDDING_WORKER_REGEX);
            } finally {
                if (jedis != null) {
                    JRedisPools.returnJedis(jedis);
                }
            }
        });
    }

    public static boolean isExpired(String workerId) {
        Jedis jedis = null;
        try {
            jedis = JRedisPools.getConnection();
            return !jedis.exists(workerId.replace(BIDDING_WORKER_SUFFIX, BIDDING_WORKER_STATUS_SUFFIX));
        } finally {
            if (jedis != null) {
                JRedisPools.returnJedis(jedis);
            }
        }
    }

}
