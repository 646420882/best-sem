package com.perfect.bidding.master;

import com.perfect.bidding.redis.Constants;
import com.perfect.bidding.redis.JRedisPools;
import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yousheng on 15/1/5.
 */
class WorkerList implements Runnable, Constants {

    private static final ReentrantLock updateLock = new ReentrantLock();

    private static BlockingQueue<String> workerList = new LinkedBlockingQueue<>();
    private static Map<String, Boolean> usedWorkerMap = new ConcurrentHashMap<>();


    @Override
    public void run() {

        Jedis jedis = null;
        try {
            jedis = JRedisPools.getConnection();

            Set<String> workerStatusSets = jedis.keys(BIDDING_WORKER_STATUS_REGEX);

            for (String s : workerStatusSets) {
                String workerId = s.replace(BIDDING_WORKER_STATUS_SUFFIX, BIDDING_WORKER_SUFFIX);

                boolean exists = jedis.exists(workerId);

                if (!exists)
                    continue;

                if (workerList.contains(workerId))
                    continue;

                if (usedWorkerMap.containsKey(workerId))
                    continue;

                updateLock.lock();

                try {
                    workerList.add(workerId);
                    usedWorkerMap.put(workerId, false);
                } finally {
                    updateLock.unlock();
                }
            }
        } finally {
            if (jedis != null) {
                JRedisPools.returnJedis(jedis);
            }
        }

    }


    public static int len() {
        return workerList.size();
    }

    public static String take() {
        updateLock.lock();
        try {
            String workerId = workerList.poll();
            usedWorkerMap.put(workerId, true);
            return workerId;
        } finally {
            updateLock.unlock();
        }
    }

    public static void offer(String workerId) {
        updateLock.lock();
        try {
            workerList.offer(workerId);
            usedWorkerMap.put(workerId, false);
        } finally {
            updateLock.unlock();
        }

    }

    public static void remove(String workerId) {
        updateLock.lock();
        try {
            Iterator<String> workers = workerList.iterator();
            while (workers.hasNext()) {
                String object = workers.next();
                if (object.equals(workerId)) {
                    workers.remove();
                    usedWorkerMap.remove(workerId);
                    break;
                }
            }
        } finally {
            updateLock.unlock();
        }

    }
}
