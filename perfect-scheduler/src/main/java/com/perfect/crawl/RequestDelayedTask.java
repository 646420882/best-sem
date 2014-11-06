package com.perfect.crawl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Request;

import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by baizz on 2014-10-29.
 */
@SuppressWarnings("unchecked")
public class RequestDelayedTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private DelayQueue<DelayedTask> queue = new DelayQueue<>();

    private List<Request> requestList = new ArrayList<>();

    public void addTask(DelayedTask e) {
        queue.put(e);
    }

    public void removeTask() {
        queue.poll();
    }

    public Collection<DelayedTask> getAllTasks() {
        return Collections.unmodifiableCollection(queue);
    }

    public int getTaskQuantity() {
        return queue.size();
    }

    public boolean isDone() {
        return queue.size() == 0;
    }

    public List<Request> getRequestList() {
        return requestList;
    }

    @Override
    public void run() {
        while (!queue.isEmpty()) {
            try {
                Map<String, Object> keywordMap = queue.take().keywordMap;    //此处会阻塞, 没有过期时不会取出
                List<String> keywordList = (List<String>) keywordMap.get(HttpURLHandler.keyword);
                List<Request> tmpList = HttpURLHandler.getURLRequests(keywordList, (int) (keywordMap.get(HttpURLHandler.siteCode)));
                requestList.addAll(tmpList);
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("剩余任务数: " + getTaskQuantity());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected static class DelayedTask implements Delayed {

        private int delay;      //second
        private long expireTime;     //nanoseconds
        private Map<String, Object> keywordMap;

        /**
         * @param delay      second
         * @param keywordMap
         */
        public DelayedTask(int delay, Map<String, Object> keywordMap) {
            this.delay = delay;
            this.expireTime = System.nanoTime() + NANOSECONDS.convert(delay, SECONDS);   //convert to nanoseconds
            this.keywordMap = keywordMap;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(expireTime - System.nanoTime(), NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            DelayedTask task = (DelayedTask) o;
            long l = this.expireTime - task.expireTime;
            if (l > 0)  //过期时刻越靠后, 越排在队尾
                return 1;
            if (l < 0)
                return -1;
            return 0;
        }
    }
}
