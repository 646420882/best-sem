package com.perfect.crawl;

import us.codecraft.webmagic.Request;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by baizz on 2014-10-29.
 */
public class RequestDelayedTask implements Runnable {

    private DelayQueue<DelayedTask> queue = new DelayQueue<DelayedTask>();

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
                List<String> keywordList = queue.take().keywordList;    //此处会阻塞, 没有过期时不会取出
                List<Request> tmpList = HttpURLHandler.getURLRequests(keywordList);
                requestList.addAll(tmpList);
                System.out.println("剩余任务数: " + getTaskQuantity());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected static class DelayedTask implements Delayed {

        private int delay;      //second
        private long expireTime;     //nanoseconds
        private List<String> keywordList;

        /**
         * @param delay       second
         * @param keywordList
         */
        public DelayedTask(int delay, List<String> keywordList) {
            this.delay = delay;
            this.expireTime = System.nanoTime() + NANOSECONDS.convert(delay, SECONDS);   //convert to nanoseconds
            this.keywordList = keywordList;
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
