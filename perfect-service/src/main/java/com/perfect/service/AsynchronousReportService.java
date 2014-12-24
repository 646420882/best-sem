package com.perfect.service;

/**
 * Created by SubDong on 2014/12/2.
 */
public interface AsynchronousReportService {
    //拉取账户报告
    void getAccountReportData(String dateStr, String userName);

    //拉取计划报告
    void getCampaignReportData(String dateStr, String userName);

    //拉取单元报告
    void getAdgroupReportData(String dateStr, String userName);

    //拉取创意报告
    void getCreativeReportData(String dateStr, String userName);

    //拉取关键字报告
    void getKeywordReportData(String dateStr, String userName);

    //拉取地域报告
    void getRegionReportData(String dateStr, String userName);
}
