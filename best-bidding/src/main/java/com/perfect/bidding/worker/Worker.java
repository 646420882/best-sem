package com.perfect.bidding.worker;

import com.perfect.bidding.redis.Constants;
import com.perfect.bidding.redis.JRedisPools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.UUID;
import java.util.concurrent.*;

/**
 * Created by yousheng on 14/12/26.
 */
public class Worker extends JedisPubSub implements Constants {

    private static final Logger LOGGER = LoggerFactory.getLogger(Worker.class);

    private final String workerId;
    private final BlockingQueue<String> taskList = new LinkedBlockingQueue<>();

    private int workerNumber = 8;

    public Worker() {
        workerId = UUID.randomUUID().toString().replace("-", "") + BIDDING_WORKER_SUFFIX;
        init();
        heartBeat();
        subscribeMessage();
    }


    public void start() {
        ExecutorService executor = Executors.newFixedThreadPool(getWorkerNumber(), Executors.defaultThreadFactory());
        for (int i = 0; i < getWorkerNumber(); i++) {
            executor.execute(new WorkerTask(this));
        }
    }

    @Override
    public void onMessage(String channel, String message) {
        if (!channel.equals(workerId)) {
            return;
        }

        Jedis jedis = null;
        try {
            jedis = JRedisPools.getConnection();
            jedis.hsetnx(workerId, message.split(JOB_SEP)[2], message);

            taskList.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (jedis != null)
                JRedisPools.returnJedis(jedis);
        }

    }

    public String popTask() {
        try {
            return taskList.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void init() {
        Jedis jedis = null;
        try {
            jedis = JRedisPools.getConnection();
            jedis.hsetnx(workerId, workerId, BIDDING_WORKER_INIT_VALUE);
//            jedis.lpush(BIDDING_WORKER_LIST, workerId);
//            jedis.setex(workerId.replace(BIDDING_WORKER_PREFIX, BIDDING_WORKER_STATUS_PREFIX), 30, HEARTBEAT_VALUE);
        } finally {
            if (jedis != null)
                JRedisPools.returnJedis(jedis);
        }

    }

    private void heartBeat() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            Jedis jedis = null;
            try {
                jedis = JRedisPools.getConnection();
                jedis.setex(workerId.replace(BIDDING_WORKER_SUFFIX, BIDDING_WORKER_STATUS_SUFFIX), 5, HEARTBEAT_VALUE);
            } finally {
                if (jedis != null)
                    JRedisPools.returnJedis(jedis);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void subscribeMessage() {
        Executors.newSingleThreadExecutor().execute(() -> {
            Jedis jedis = null;
            try {
                jedis = JRedisPools.getConnection();
                jedis.subscribe(this, workerId);
            } finally {
                if (jedis != null) {
                    JRedisPools.returnJedis(jedis);
                }
            }
        });
    }

    public String getWorkerId() {
        return workerId;
    }

    public int getWorkerNumber() {
        return workerNumber;
    }

    public void setWorkerNumber(int workerNumber) {
        this.workerNumber = workerNumber;
    }
}
