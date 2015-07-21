package com.perfect.nms;

import com.baidu.api.client.core.ReportUtil;
import com.baidu.api.sem.nms.v2.ReportService;
import com.perfect.utils.redis.JRedisUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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

    /**
     * Report Id Queue
     * e.g.
     * 1|reportId
     */
    private final BlockingQueue<Map<String, ReportService>> queue = new LinkedBlockingQueue<>();

    private final ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUMBER, new FileUrlThreadFactory());


    public ReportFileUrlTask() {
        handle();
    }

    public void add(Map<String, ReportService> map) {
        queue.add(map);
    }

    public void shutdown() {
        if (!executor.isShutdown()) {
            executor.shutdown();
        }
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
                Map<String, ReportService> reportServiceMap = queue.poll();
                try {
                    Jedis jedis = JRedisUtils.get();

                    String status = jedis.get(REPORT_ID_COMMIT_STATUS);
                    if ("1".equals(status) && reportServiceMap == null) {
                        jedis.close();
                        LOGGER.info("Nms report id handle finished.");
                        break;
                    }

                    if (reportServiceMap == null) {
                        jedis.close();
                        TimeUnit.SECONDS.sleep(1);
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
                        jedis.close();
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
                            jedis.close();
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

                    jedis.close();
                    TimeUnit.SECONDS.sleep(30);
                } catch (InterruptedException e) {
                    LOGGER.info("java.lang.InterruptedException");
                }
            }

        }
    }

}
