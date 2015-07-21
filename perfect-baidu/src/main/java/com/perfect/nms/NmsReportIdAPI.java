package com.perfect.nms;

import com.baidu.api.sem.nms.v2.ReportService;
import com.perfect.utils.DateUtils;
import com.perfect.utils.redis.JRedisUtils;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

/**
 * Created by subdong on 15-7-21.
 */
public class NmsReportIdAPI {

    /**
     * 拉取网盟所有报告
     * @param baidAccount
     * @param baidPwd
     * @param token
     */
    public void getAllApi(String baidAccount,String baidPwd,String token){
        GetReportId example = new GetReportId(baidAccount, baidPwd, token);

        ReportFileUrlTask reportFileUrlTask = new ReportFileUrlTask();

        //账户报告
        List<Long> accountId = example.getAccountId();
        Map<String,ReportService> accountIdMap= example.getReportAllId(accountId, 1, 1, DateUtils.getYesterday(), DateUtils.getYesterday());
        reportFileUrlTask.add(accountIdMap);

        //计划报告
        List<Long> campaignId = example.getCampaignId();
        Map<String,ReportService> campaignMap = example.getReportAllId(campaignId, 2, 2, DateUtils.getYesterday(), DateUtils.getYesterday());
        reportFileUrlTask.add(campaignMap);

        //组报告
        List<Long> groupId = example.getGroupByGroupId(campaignId);
        Map<String,ReportService> groupMap = example.getReportAllId(groupId, 3, 3, DateUtils.getYesterday(), DateUtils.getYesterday());
        reportFileUrlTask.add(groupMap);

        //创意报告
        List<Long> adbyGroupId = example.getAdbyGroupId(groupId);
        Map<String, ReportService> adbyGroupMap = example.getReportAllId(adbyGroupId, 4, 4, DateUtils.getYesterday(), DateUtils.getYesterday());
        reportFileUrlTask.add(adbyGroupMap);


        Jedis jc = JRedisUtils.get();
        boolean b = jc.exists("nms-report-id-commit-status");
        if (!b) {
            jc.set("nms-report-id-commit-status", "1");
        }
        if (jc != null) {
            jc.close();
        }
    }

    /**
     * 拉取网盟账户报告
     * @param baidAccount
     * @param baidPwd
     * @param token
     */
    public void getAccountApi(String baidAccount,String baidPwd,String token){
        GetReportId example = new GetReportId(baidAccount, baidPwd, token);

        ReportFileUrlTask reportFileUrlTask = new ReportFileUrlTask();
        //账户报告
        List<Long> accountId = example.getAccountId();
        Map<String,ReportService> accountIdMap= example.getReportAllId(accountId, 1, 1, DateUtils.getYesterday(), DateUtils.getYesterday());
        reportFileUrlTask.add(accountIdMap);

        Jedis jc = JRedisUtils.get();
        boolean b = jc.exists("nms-report-id-commit-status");
        if (!b) {
            jc.set("nms-report-id-commit-status", "1");
        }
        if (jc != null) {
            jc.close();
        }
    }

    /**
     * 拉取网盟计划报告
     * @param baidAccount
     * @param baidPwd
     * @param token
     */
    public void getCampaignApi(String baidAccount,String baidPwd,String token){
        GetReportId example = new GetReportId(baidAccount, baidPwd, token);

        ReportFileUrlTask reportFileUrlTask = new ReportFileUrlTask();

        //计划报告
        List<Long> campaignId = example.getCampaignId();
        Map<String,ReportService> campaignMap = example.getReportAllId(campaignId, 2, 2, DateUtils.getYesterday(), DateUtils.getYesterday());
        reportFileUrlTask.add(campaignMap);

        Jedis jc = JRedisUtils.get();
        boolean b = jc.exists("nms-report-id-commit-status");
        if (!b) {
            jc.set("nms-report-id-commit-status", "1");
        }
        if (jc != null) {
            jc.close();
        }
    }

    /**
     * 拉取网盟推广组报告
     * @param baidAccount
     * @param baidPwd
     * @param token
     */
    public void getGroupApi(String baidAccount,String baidPwd,String token){
        GetReportId example = new GetReportId(baidAccount, baidPwd, token);

        ReportFileUrlTask reportFileUrlTask = new ReportFileUrlTask();

        //组报告
        List<Long> campaignId = example.getCampaignId();
        List<Long> groupId = example.getGroupByGroupId(campaignId);
        Map<String,ReportService> groupMap = example.getReportAllId(groupId, 3, 3, DateUtils.getYesterday(), DateUtils.getYesterday());
        reportFileUrlTask.add(groupMap);

        Jedis jc = JRedisUtils.get();
        boolean b = jc.exists("nms-report-id-commit-status");
        if (!b) {
            jc.set("nms-report-id-commit-status", "1");
        }
        if (jc != null) {
            jc.close();
        }
    }

    /**
     * 拉取网盟创意报告
     * @param baidAccount
     * @param baidPwd
     * @param token
     */
    public void getAdbyGroupApi(String baidAccount,String baidPwd,String token){
        GetReportId example = new GetReportId(baidAccount, baidPwd, token);

        ReportFileUrlTask reportFileUrlTask = new ReportFileUrlTask();

        List<Long> campaignId = example.getCampaignId();
        List<Long> groupId = example.getGroupByGroupId(campaignId);
        List<Long> adbyGroupId = example.getAdbyGroupId(groupId);
        Map<String, ReportService> adbyGroupMap = example.getReportAllId(adbyGroupId, 4, 4, DateUtils.getYesterday(), DateUtils.getYesterday());
        reportFileUrlTask.add(adbyGroupMap);

        Jedis jc = JRedisUtils.get();
        boolean b = jc.exists("nms-report-id-commit-status");
        if (!b) {
            jc.set("nms-report-id-commit-status", "1");
        }
        if (jc != null) {
            jc.close();
        }
    }
}
