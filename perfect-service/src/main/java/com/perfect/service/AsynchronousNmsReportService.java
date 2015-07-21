package com.perfect.service;

import java.util.Date;

/**
 * Created by subdong on 15-7-21.
 */
public interface AsynchronousNmsReportService {

    void generateReportId(Date[] dates, String... args);

    void readReportFileUrlFromRedis();

}
