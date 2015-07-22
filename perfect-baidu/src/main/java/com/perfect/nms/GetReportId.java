package com.perfect.nms;

import com.baidu.api.client.core.*;
import com.baidu.api.sem.common.v2.ResHeader;
import com.baidu.api.sem.nms.v2.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.perfect.utils.DateUtils;
import com.perfect.utils.redis.JRedisUtils;
import org.apache.wml.WMLStrongElement;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * Created by subdong on 15-7-17.
 */
public class GetReportId {
    private AccountService accountService;
    private CampaignService campaignService;
    private GroupService getGroupByCampaignId;
    private AdService adService;
    private static ReportService reportService;

    public GetReportId(String baiduAccount, String baiduPwd, String token, int apiType) {
        VersionService factory = ServiceFactory.getInstance(baiduAccount, baiduPwd, token, null);
        if (apiType == 0 || apiType == 1) {
            accountService = factory.getService(AccountService.class);
        }
        if (apiType == 0 || apiType == 2) {
            campaignService = factory.getService(CampaignService.class);
        }
        if (apiType == 0 || apiType == 3) {
            getGroupByCampaignId = factory.getService(GroupService.class);
        }
        if (apiType == 0 || apiType == 4) {
            adService = factory.getService(AdService.class);
        }
        reportService = factory.getService(ReportService.class);
    }

    //获取账户ID
    public List<Long> getAccountId() {


        GetAccountInfoRequest parameters = new GetAccountInfoRequest();

        GetAccountInfoResponse account = accountService.getAccountInfo(parameters);
        List<Long> longs = new ArrayList<>();
        if (account != null) {
            longs.add(account.getAccountInfo().getUserid());
        }
        return longs;

    }

    //获取计划ID
    public List<Long> getCampaignId() {

        GetCampaignRequest parameters = new GetCampaignRequest();

        GetCampaignResponse campaign = campaignService.getCampaign(parameters);

        List<Long> longs = new ArrayList<>();
        if (campaign != null) {
            campaign.getCampaignTypes().stream().filter(e -> e != null).forEach(e -> {
                longs.add(e.getCampaignId());
            });
        }
        return longs;
    }

    //获取推广组效果ID
    public List<Long> getGroupByGroupId(List<Long> compaignId) {
        List<Long> longs = new ArrayList<>();
        if (compaignId != null) {
            for (Long aLong : compaignId) {
                GetGroupByCampaignIdRequest parameters = new GetGroupByCampaignIdRequest();

                parameters.setCampaignId(aLong);

                GetGroupByCampaignIdResponse groupByCampaignId = getGroupByCampaignId.getGroupByCampaignId(parameters);

                if (groupByCampaignId != null) {
                    groupByCampaignId.getGroupTypes().stream().filter(e -> e != null).forEach(e -> {
                        if (!longs.contains(e.getGroupId())) longs.add(e.getGroupId());
                    });
                }
            }
        }

        return longs;
    }

    //通过groupID得到创意信息
    public List<Long> getAdbyGroupId(List<Long> groupId) {

        List<Long> longs = new ArrayList<>();
        if (groupId != null) {
            for (Long aLong : groupId) {
                GetAdByGroupIdRequest parameters = new GetAdByGroupIdRequest();
                parameters.setGroupId(aLong);

                GetAdByGroupIdResponse adByGroup = adService.getAdByGroupId(parameters);

                if (adByGroup != null) {
                    adByGroup.getAdTypes().stream().filter(e -> e != null).forEach(e -> {
                        if (!longs.contains(e.getAdId())) longs.add(e.getAdId());
                    });
                }
            }
        }

        return longs;
    }


    public Map<String, ReportService> getReportAllId(List<Long> reid, int reportType, int statRange, Date startDate, Date endDate) {

        int i = 1;
        while (true) {
            //创建报告拉去设置容器
            ReportRequestType reportRequestType = new ReportRequestType();

            //取值范围为srch,click,cost,ctr,cpm,acp任意组合,报表按照顺序输出绩效数据。
            /*srch: 展现次数  click：点击次数  cost：消费（￥，精确到小数点后两位）  ctr：点击率（0.XXXXXX，1表示100%，精确到小数点后6位）
            cpm：千次展现成本（￥，精确到小数点后两位）  acp：平均点击价格（￥，精确到小数点后两位）  srchuv：展现独立访客
            clickuv：点击独立访客  srsur：展现频次  cusur：独立访客点击率  cocur：平均独立访客点击价格
            arrivalRate：到达率  hopRate：二跳率  avgResTime：平均访问时间  directTrans：直接转换  indirectTrans：间接转换*/
            //方法
            //reportRequestTypeData
            reportRequestType.getPerformanceData().add("srch");
            reportRequestType.getPerformanceData().add("click");
            reportRequestType.getPerformanceData().add("cost");
            reportRequestType.getPerformanceData().add("ctr");
            reportRequestType.getPerformanceData().add("cpm");
            reportRequestType.getPerformanceData().add("acp");

            reportRequestType.getPerformanceData().add("srchuv");
            reportRequestType.getPerformanceData().add("clickuv");
            reportRequestType.getPerformanceData().add("srsur");
            reportRequestType.getPerformanceData().add("cusur");
            reportRequestType.getPerformanceData().add("cocur");
            reportRequestType.getPerformanceData().add("arrivalRate");

            reportRequestType.getPerformanceData().add("hopRate");
            reportRequestType.getPerformanceData().add("avgResTime");
            reportRequestType.getPerformanceData().add("directTrans");
            reportRequestType.getPerformanceData().add("indirectTrans");

            //报告开始时间
            reportRequestType.setStartDate(startDate);
            //报告结束时间
            reportRequestType.setEndDate(endDate);

            //报告类型 1. 账户报告 2. 推广计划报告 3. 推广组报告 4. 创意报告
            reportRequestType.setReportType(reportType);

            //统计范围 1. 账户 2. 推广计划3. 推广组 4. 创意
            reportRequestType.setStatRange(statRange);

            //报告文件格式  0：zip压缩包格式  1：csv格式
            reportRequestType.setFormat(1);

            for (long id : reid) {
                reportRequestType.getStatIds().add(id);
            }
            //是否只需要id  false：既获取id也获取字面   true：只获取id
            reportRequestType.setIdOnly(false);

            GetReportIdRequest parameter = new GetReportIdRequest();

            ReportRequestType request = new ReportRequestType();

            parameter.setReportRequestType(reportRequestType);

            GetReportIdResponse reportId = reportService.getReportId(parameter);
            ResHeader rheader = ResHeaderUtil.getResHeader(reportService, true);
            if (reportId == null || reportId.getReportId().equals("")) {
                i++;
                if (i <= 3) {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            } else {
                Map<String, ReportService> accountMap = new HashMap<>();
                accountMap.put(reportType + "|" + reportId.getReportId(), reportService);
                return accountMap;
            }
        }
        return null;
    }


    public static void main(String[] args) {

        GetReportId example = new GetReportId("baidu-perfect2151880", "Ab1234890", "2c5fb53fc0003f407bc495b391d05e2e",1);
        //推广组ID   20657783
        //推广计划ID  4222159  4222135  4219295  4073559
        //int s = example.getAdbyGroupId(20657783);

        ReportFileUrlTask reportFileUrlTask = new ReportFileUrlTask();

        //账户报告
        List<Long> accountId = example.getAccountId();
        Map<String, ReportService> accountMap = example.getReportAllId(accountId, 1, 1, DateUtils.getYesterday(), DateUtils.getYesterday());
        reportFileUrlTask.add(accountMap);

        /*//计划报告
        List<Long> campaignId = example.getCampaignId();
        String campaignIdString = example.getReportAllId(campaignId, 2, 2, DateUtils.getYesterday(), DateUtils.getYesterday());
        Map<String,ReportService> campaignMap = new HashMap<>();
        campaignMap.put("2|" + campaignIdString, reportService);
        reportFileUrlTask.add(campaignMap);*/

        /*//组报告
        List<Long> campaignId = example.getCampaignId();
        List<Long> groupId = example.getGroupByGroupId(campaignId);
        String groupIdString = example.getReportAllId(groupId, 3, 3, DateUtils.getYesterday(), DateUtils.getYesterday());
        Map<String,ReportService> groupMap = new HashMap<>();
        groupMap.put("3|" + groupIdString, reportService);
        reportFileUrlTask.add(groupMap);*/

        /*//创意报告
        List<Long> campaignId = example.getCampaignId();
        List<Long> groupId = example.getGroupByGroupId(campaignId);
        List<Long> adbyGroupId = example.getAdbyGroupId(groupId);
        Map<String, ReportService> adbyGroupMap = example.getReportAllId(adbyGroupId, 4, 4, DateUtils.getYesterday(), DateUtils.getYesterday());
        reportFileUrlTask.add(adbyGroupMap);*/


        Jedis jc = JRedisUtils.get();
        boolean b = jc.exists("nms-report-id-commit-status");
        if (!b) {
            jc.set("nms-report-id-commit-status", "1");
        }
        if (jc != null) {
            jc.close();
        }
        System.out.println();
    }

}
