package com.perfect.service;

/**
 * Created by subdong on 15-7-21.
 */
public interface AsynchronousNmsReportService {
    //拉取网盟账户报告
    void getNmsAccountReportData(String dateStr, String userName);

    //拉取网盟计划报告
    void getNmsCampaignReportData(String dateStr, String userName);

    //拉取网盟 组 报告
    void getNmsGroupReportData(String dateStr, String userName);

    //拉取创意报告
    void getNmsAdReportData(String dateStr, String userName);
}
