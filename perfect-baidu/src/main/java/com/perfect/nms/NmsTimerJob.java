package com.perfect.nms;

import com.baidu.api.client.core.ReportUtil;
import org.apache.commons.lang.StringUtils;
import rx.Observable;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dolphineor on 2015-7-17.
 */
public class NmsTimerJob {
    private static final int RETRY_NUM = 3;
    private static final int REPORT_SUCCESS = 3;
    private static final long PERIOD = 960_000;

    private final AtomicInteger atomicInteger = new AtomicInteger(0);
    private final String reportId;


    public NmsTimerJob(String reportId) {
        this.reportId = reportId;
    }

    public void execJob() {
        Timer timer = new Timer("Timer-nms");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (ReportUtil.getReportState(reportId) == REPORT_SUCCESS) {
                    String fileUrl = ReportUtil.getReportFileUrl(reportId);
                    if (StringUtils.isNotEmpty(fileUrl)) {
                        timer.cancel();
                        Observable<String> observable = Observable.just(fileUrl);
                        observable.subscribe(new NmsAction());
                    }
                }

                if (atomicInteger.incrementAndGet() == RETRY_NUM)
                    timer.cancel();
            }
        }, PERIOD, PERIOD);
    }

    public static NmsTimerJob create(String reportId) {
        return new NmsTimerJob(reportId);
    }
}
