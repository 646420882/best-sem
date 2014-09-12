package com.perfect.service;

import com.perfect.entity.AccountReportEntity;
import com.perfect.entity.KeywordRealTimeDataVOEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by john on 2014/8/19.
 */
public interface PerformanceService {
    List<KeywordRealTimeDataVOEntity> performance(String userTable, String[] date);

    List<AccountReportEntity> performanceUser(Date startDate, Date endDate,String sorted,int limit,int startPer, List<String> date);

    List<AccountReportEntity> performanceCurve(Date startDate,Date endDate,List<String> date);
}
