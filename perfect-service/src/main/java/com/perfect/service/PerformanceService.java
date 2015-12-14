package com.perfect.service;

import com.perfect.dto.account.AccountReportDTO;
import com.perfect.dto.keyword.KeywordRealDTO;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by john on 2014/8/19.
 */
public interface PerformanceService {
    List<KeywordRealDTO> performance(String userTable, String[] date);

    List<AccountReportDTO> performanceUser(Date startDate, Date endDate, String sorted, int limit, int startPer, List<String> date);

    List<AccountReportDTO> performanceCurve(Date startDate, Date endDate);

    void downAccountCSV(OutputStream os, Date start, Date end, List<String> date);
}
