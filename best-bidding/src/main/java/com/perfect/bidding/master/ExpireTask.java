package com.perfect.bidding.master;

import com.perfect.bidding.redis.Constants;
import com.perfect.bidding.redis.JRedisPools;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by baizz on 2015-1-4.
 */
class ExpireTask extends JedisPubSub implements Constants {

    @Override
    public void onMessage(String channel, String message) {
        // message: the key of expired worker
        Jedis jedis = null;
        try {
            jedis = JRedisPools.getConnection();
            Deque<String> taskList = new LinkedList<>();
            List<String> rest = jedis.hvals(message);
            if (rest.isEmpty() || (rest.size() == 1 && BIDDING_WORKER_INIT_VALUE.equals(rest.get(0)))) {
                jedis.del(message);
                return;
            }

            rest.stream().filter(Objects::nonNull).forEach(taskList::add);
            // 分发遗留的任务给其它worker
            while (true) {

                if (WorkerList.len() == 0) {
                    try {
                        Thread.sleep(1_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

//                WorkerObject workerObject = WorkerList.take();

                String workerId = WorkerList.take();

                if (message.equals(workerId) || Master.isExpired(workerId)) {
                    continue;
                }

                try {
                    String task;
                    if ((task = taskList.poll()) == null) {
                        // 已经全部取出挂掉的worker遗留的任务
                        jedis.del(message);
                        break;
                    }

                    if (BIDDING_WORKER_INIT_VALUE.equals(task)) {
                        if (taskList.isEmpty()) {
                            jedis.del(message);
                        }
                        continue;
                    }

                    jedis.publish(workerId, task);
                } finally {
                    // 重新放入工作队列
//                    workerObject.inc();
                    WorkerList.offer(workerId);
                }

            }

        } finally {
            if (jedis != null) {
                JRedisPools.returnJedis(jedis);
            }
        }

    }
}
