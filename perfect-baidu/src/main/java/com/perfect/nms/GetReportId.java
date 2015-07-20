package com.perfect.nms;

import com.baidu.api.client.core.*;
import com.baidu.api.sem.common.v2.ResHeader;
import com.baidu.api.sem.nms.v2.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by subdong on 15-7-17.
 */
public class GetReportId {
    private VersionService factory;

    public GetReportId() {
        factory = ServiceFactory.getInstance("baidu-perfect2151880", "Ab1234890", "2c5fb53fc0003f407bc495b391d05e2e", null);
    }

    //获取账户ID
    public Long getAccountId(){
        AccountService service = factory.getService(AccountService.class);

        GetAccountInfoRequest parameters = new GetAccountInfoRequest();

        GetAccountInfoResponse account = service.getAccountInfo(parameters);

        return account.getAccountInfo().getUserid();

    }

    //获取计划ID
    public List<Long> getCampaignId() {
        CampaignService service = factory.getService(CampaignService.class);

        GetCampaignRequest parameters = new GetCampaignRequest();

        GetCampaignResponse campaign = service.getCampaign(parameters);

        List<Long> longs = new ArrayList<>();
        if(campaign != null){
            campaign.getCampaignTypes().stream().filter(e -> e != null).forEach(e -> {
                longs.add(e.getCampaignId());
            });
        }
        return longs;
    }

    //获取推广组效果ID
    public List<Long> getGroupByGroupId(long compaignId){
        GroupService service = factory.getService(GroupService.class);

        GetGroupByCampaignIdRequest parameters = new GetGroupByCampaignIdRequest();

        parameters.setCampaignId(compaignId);

        GetGroupByCampaignIdResponse groupByCampaignId = service.getGroupByCampaignId(parameters);
        List<Long> longs = new ArrayList<>();
        if(groupByCampaignId != null){
            groupByCampaignId.getGroupTypes().stream().filter(e -> e != null).forEach(e -> {
                longs.add(e.getGroupId());
            });
        }
        return longs;
    }

    //通过groupID得到创意信息
    public List<Long> getAdbyGroupId(long groupId){
        AdService service = factory.getService(AdService.class);

        GetAdByGroupIdRequest parameters = new GetAdByGroupIdRequest();
        parameters.setGroupId(groupId);

        GetAdByGroupIdResponse adByGroup = service.getAdByGroupId(parameters);

        ResHeader rheader = ResHeaderUtil.getResHeader(service, true);
        List<Long> longs = new ArrayList<>();
        if(adByGroup != null){
            adByGroup.getAdTypes().stream().filter(e -> e != null).forEach(e -> {
                longs.add(e.getAdId());
            });
        }
        return longs;
    }
    public static void main(String[] args) {
        GetReportId example = new GetReportId();
        //推广组ID   20657783
        //推广计划ID  4222159  4222135  4219295  4073559
        //int s = example.getAdbyGroupId(20657783);
        System.out.println();
    }

}
