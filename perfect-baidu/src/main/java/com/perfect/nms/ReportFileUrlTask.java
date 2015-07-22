package com.perfect.nms;

import com.baidu.api.client.core.ReportUtil;
import com.baidu.api.sem.nms.v2.ReportService;
import com.perfect.utils.redis.JRedisUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import static com.perfect.commons.constants.RedisConstants.*;

/**
 * Created by dolphineor on 2015-7-20.
 * <p>
 * 网盟推广数据报告下载地址处理器
 */
public class ReportFileUrlTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportFileUrlTask.class);

    private static final int RETRY_NUM = 20;

    private static final int REPORT_GENERATE_SUCCESS = 3;

    private static final int THREAD_NUMBER = Runtime.getRuntime().availableProcessors() * 2;

    private final JedisPool pool = JRedisUtils.getPool();

    private final BlockingQueue<Map<String, ReportService>> queue = new LinkedBlockingQueue<>();

    private final ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUMBER, new FileUrlThreadFactory());

    private final ReentrantLock lock = new ReentrantLock();


    public ReportFileUrlTask() {
        handle();
    }

    public void add(Map<String, ReportService> map) {
        queue.add(map);
    }

    public void shutdown() {
        if (!executor.isShutdown())
            executor.shutdown();
    }

    private void handle() {
        for (int i = 0; i < THREAD_NUMBER; i++) {
            executor.execute(new FileUrlWorker());
        }
    }


    class FileUrlThreadFactory implements ThreadFactory {

        private final AtomicInteger counter = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            FileUrlThread thread = new FileUrlThread(r);
            thread.setName("thread-report-file-url-" + counter.incrementAndGet());
            return thread;
        }
    }

    class FileUrlThread extends Thread {
        public FileUrlThread(Runnable target) {
            super(target);
        }
    }


    /**
     * 每30秒获取一次报告生成状态, 在尝试20次之后仍然没有生成,
     * 将其放入Redis的fail队列 {@link com.perfect.commons.constants.RedisConstants#REPORT_FILE_URL_FAILED}
     */
    class FileUrlWorker implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    lock.lock();

                    Map<String, ReportService> reportServiceMap = queue.poll();

                    Jedis jedis = pool.getResource();
                    String status = jedis.get(REPORT_ID_COMMIT_STATUS);
                    if ("1".equals(status) && reportServiceMap == null) {
                        closeRedis(jedis);
                        LOGGER.info("Nms report id handle finished.");
                        lock.unlock();
                        break;
                    }

                    if (reportServiceMap == null) {
                        closeRedis(jedis);
                        sleep(1, TimeUnit.SECONDS);
                        lock.unlock();
                        continue;
                    }

                    String value = null;
                    ReportService service = null;
                    for (Map.Entry<String, ReportService> entry : reportServiceMap.entrySet()) {
                        value = entry.getKey();
                        service = entry.getValue();
                        break;
                    }

                    if (value == null) {
                        closeRedis(jedis);
                        lock.unlock();
                        continue;
                    }

                    String[] rt = value.split("\\|");
                    String type = rt[0];
                    String reportId = rt[1];
                    int counter = 0;
                    if (rt.length == 3) {
                        counter += Integer.parseInt(rt[2]);
                    }

                    if (ReportUtil.getReportState(reportId, service) == REPORT_GENERATE_SUCCESS) {
                        String fileUrl = ReportUtil.getReportFileUrl(reportId, service);
                        if (StringUtils.isNotEmpty(fileUrl)) {
                            jedis.lpush(REPORT_FILE_URL_SUCCEED, type + "|" + fileUrl);
                            closeRedis(jedis);
                            lock.unlock();
                            continue;
                        } else {
                            // 报告生成成功, 获取报告的url下载地址失败
                            jedis.lpush(REPORT_FILE_URL_FAILED, type + "|" + reportId);
                        }
                    } else {
                        if (counter > RETRY_NUM) {
                            // 超出尝试次数
                            jedis.lpush(REPORT_FILE_URL_FAILED, type + "|" + reportId);
                        } else {
                            // 重新放入queue
                            counter++;
                            Map<String, ReportService> m = new HashMap<>();
                            m.put(value + "|" + counter, service);
                            queue.add(m);
                        }
                    }

                    closeRedis(jedis);
                    sleep(30, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    LOGGER.info("java.lang.InterruptedException");
                } finally {
                    if (lock.isHeldByCurrentThread()) {
                        lock.unlock();
                    }
                }
            }

        }
    }

    private void closeRedis(Jedis jedis) {
        if (jedis != null && pool.getNumActive() > 0) {
            jedis.close();
        }
    }

    private void sleep(long timeout, TimeUnit unit) throws InterruptedException {
        unit.sleep(timeout);
    }

}
