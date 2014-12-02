package com.perfect.service;

/**
 * Created by SubDong on 2014/12/2.
 */
public interface AsynchronousReportService {
    void getAccountReportData(String dateStr, String userName);

    void getCampaignReportData(String dateStr, String userName);

    void getAdgroupReportData(String dateStr, String userName);

    void getCreativeReportData(String dateStr, String userName);

    void getKeywordReportData(String dateStr, String userName);

    void getRegionReportData(String dateStr, String userName);
}
