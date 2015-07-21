package com.perfect.service;

import com.baidu.api.sem.nms.v2.ReportService;
import com.perfect.nms.GetReportId;
import com.perfect.nms.ReportFileUrlTask;
import com.perfect.utils.DateUtils;
import com.perfect.utils.redis.JRedisUtils;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by subdong on 15-7-21.
 */
public interface AsynchronousNmsReportService {
    //拉取网盟账户报告
    void getNmsAccountReportData(Date dateStr, String userName);

    //拉取网盟计划报告
    void getNmsCampaignReportData(Date dateStr, String userName);

    //拉取网盟 组 报告
    void getNmsGroupReportData(Date dateStr, String userName);

    //拉取创意报告
    void getNmsAdReportData(Date dateStr, String userName);

}
