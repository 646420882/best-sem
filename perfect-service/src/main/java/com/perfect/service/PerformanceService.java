package com.perfect.service;

import com.perfect.entity.AccountReportEntity;
import com.perfect.entity.KeywordRealTimeDataVOEntity;

import java.io.File;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by john on 2014/8/19.
 */
public interface PerformanceService {
    public List<KeywordRealTimeDataVOEntity> performance(String userTable, String[] date);

    public List<AccountReportEntity> performanceUser(Date startDate, Date endDate,String sorted,int limit,int startPer, List<String> date);

    public List<AccountReportEntity> performanceCurve(Date startDate,Date endDate,List<String> date);

    public void downAccountCSV(OutputStream os);
}
