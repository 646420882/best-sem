package com.perfect.nms;

import com.baidu.api.sem.nms.v2.ReportService;
import com.perfect.utils.DateUtils;
import com.perfect.utils.redis.JRedisUtils;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.perfect.commons.constants.RedisConstants.REPORT_ID_COMMIT_STATUS;

/**
 * Created by subdong on 15-7-21.
 */
public class NmsReportIdAPI {

    private final ReportFileUrlTask reportFileUrlTask;

    public NmsReportIdAPI(ReportFileUrlTask reportFileUrlTask) {
        this.reportFileUrlTask = reportFileUrlTask;
    }

    /**
     * 拉取网盟所有报告
     *
     * @param baidAccount
     * @param baidPwd
     * @param token
     */
    public void getAllApi(String baidAccount, String baidPwd, String token, Date[] dates) {
        if (dates[0] == null && dates[1] == null) {
            dates = new Date[]{DateUtils.getYesterday(), DateUtils.getYesterday()};
        }

        GetReportId example = new GetReportId(baidAccount, baidPwd, token);


        //账户报告
        List<Long> accountId = example.getAccountId();
        Map<String, ReportService> accountIdMap = example.getReportAllId(accountId, 1, 1, dates[0], dates[1]);
        reportFileUrlTask.add(accountIdMap);

        //计划报告
        List<Long> campaignId = example.getCampaignId();
        Map<String, ReportService> campaignMap = example.getReportAllId(campaignId, 2, 2, dates[0], dates[1]);
        reportFileUrlTask.add(campaignMap);

        //组报告
        List<Long> groupId = example.getGroupByGroupId(campaignId);
        Map<String, ReportService> groupMap = example.getReportAllId(groupId, 3, 3, dates[0], dates[1]);
        reportFileUrlTask.add(groupMap);

        //创意报告
        List<Long> adbyGroupId = example.getAdbyGroupId(groupId);
        Map<String, ReportService> adbyGroupMap = example.getReportAllId(adbyGroupId, 4, 4, dates[0], dates[1]);
        reportFileUrlTask.add(adbyGroupMap);


        Jedis jc = JRedisUtils.get();
        boolean b = jc.exists(REPORT_ID_COMMIT_STATUS);
        if (!b) {
            jc.set(REPORT_ID_COMMIT_STATUS, "1");
        }
        if (jc != null) {
            jc.close();
        }
    }

    /**
     * 拉取网盟账户报告
     *
     * @param baidAccount
     * @param baidPwd
     * @param token
     */
    public void getAccountApi(String baidAccount, String baidPwd, String token, Date[] dates) {
        if (dates[0] == null && dates[1] == null) {
            dates = new Date[]{DateUtils.getYesterday(), DateUtils.getYesterday()};
        }
        GetReportId example = new GetReportId(baidAccount, baidPwd, token);

        //账户报告
        List<Long> accountId = example.getAccountId();
        Map<String, ReportService> accountIdMap = example.getReportAllId(accountId, 1, 1, dates[0], dates[1]);
        reportFileUrlTask.add(accountIdMap);

        Jedis jc = JRedisUtils.get();
        boolean b = jc.exists(REPORT_ID_COMMIT_STATUS);
        if (!b) {
            jc.set(REPORT_ID_COMMIT_STATUS, "1");
        }
        if (jc != null) {
            jc.close();
        }
    }

    /**
     * 拉取网盟计划报告
     *
     * @param baidAccount
     * @param baidPwd
     * @param token
     */
    public void getCampaignApi(String baidAccount, String baidPwd, String token, Date[] dates) {
        if (dates[0] == null && dates[1] == null) {
            dates = new Date[]{DateUtils.getYesterday(), DateUtils.getYesterday()};
        }
        GetReportId example = new GetReportId(baidAccount, baidPwd, token);


        //计划报告
        List<Long> campaignId = example.getCampaignId();
        Map<String, ReportService> campaignMap = example.getReportAllId(campaignId, 2, 2, dates[0], dates[1]);
        reportFileUrlTask.add(campaignMap);

        Jedis jc = JRedisUtils.get();
        boolean b = jc.exists(REPORT_ID_COMMIT_STATUS);
        if (!b) {
            jc.set(REPORT_ID_COMMIT_STATUS, "1");
        }
        if (jc != null) {
            jc.close();
        }
    }

    /**
     * 拉取网盟推广组报告
     *
     * @param baidAccount
     * @param baidPwd
     * @param token
     */
    public void getGroupApi(String baidAccount, String baidPwd, String token, Date[] dates) {
        if (dates[0] == null && dates[1] == null) {
            dates = new Date[]{DateUtils.getYesterday(), DateUtils.getYesterday()};
        }
        GetReportId example = new GetReportId(baidAccount, baidPwd, token);


        //组报告
        List<Long> campaignId = example.getCampaignId();
        List<Long> groupId = example.getGroupByGroupId(campaignId);
        Map<String, ReportService> groupMap = example.getReportAllId(groupId, 3, 3, dates[0], dates[1]);
        reportFileUrlTask.add(groupMap);

        Jedis jc = JRedisUtils.get();
        boolean b = jc.exists(REPORT_ID_COMMIT_STATUS);
        if (!b) {
            jc.set(REPORT_ID_COMMIT_STATUS, "1");
        }
        if (jc != null) {
            jc.close();
        }
    }

    /**
     * 拉取网盟创意报告
     *
     * @param baidAccount
     * @param baidPwd
     * @param token
     */
    public void getAdbyGroupApi(String baidAccount, String baidPwd, String token, Date[] dates) {
        if (dates[0] == null && dates[1] == null) {
            dates = new Date[]{DateUtils.getYesterday(), DateUtils.getYesterday()};
        }
        GetReportId example = new GetReportId(baidAccount, baidPwd, token);


        List<Long> campaignId = example.getCampaignId();
        List<Long> groupId = example.getGroupByGroupId(campaignId);
        List<Long> adbyGroupId = example.getAdbyGroupId(groupId);
        Map<String, ReportService> adbyGroupMap = example.getReportAllId(adbyGroupId, 4, 4, dates[0], dates[1]);
        reportFileUrlTask.add(adbyGroupMap);

        Jedis jc = JRedisUtils.get();
        boolean b = jc.exists(REPORT_ID_COMMIT_STATUS);
        if (!b) {
            jc.set(REPORT_ID_COMMIT_STATUS, "1");
        }
        if (jc != null) {
            jc.close();
        }
    }
}
