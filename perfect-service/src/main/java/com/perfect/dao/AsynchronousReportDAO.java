package com.perfect.dao;

/**
 * Created by baizz on 2014-08-07.
 */
public interface AsynchronousReportDAO {

    void getAccountReportData(String dateStr, String userName);

    void getCampaignReportData(String dateStr, String userName);

    void getAdgroupReportData(String dateStr, String userName);

    void getCreativeReportData(String dateStr, String userName);

    void getKeywordReportData(String dateStr, String userName);

    void getRegionReportData(String dateStr, String userName);
}