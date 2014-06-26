package com.perfect;


import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ResHeader;
import com.perfect.autosdk.core.ResHeaderUtil;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vbzer_000 on 2014-5-16.
 */
public class Main {

    public static void main(String args[]) throws ApiException {
        CommonService commonService = ServiceFactory.getInstance();

        CampaignService campaignService = commonService.getService(CampaignService.class);

        AddCampaignRequest addCampaignRequest = new AddCampaignRequest();
        addCampaignRequest.setExtended(4);
        CampaignType campaignType = new CampaignType();
        campaignType.setPause(true);
        campaignType.setBudget(0.01);
        campaignType.setCampaignName("test1");

        List<CampaignType> list = new ArrayList<CampaignType>();
        list.add(campaignType);
        addCampaignRequest.setCampaignTypes(list);


        AddCampaignResponse response = campaignService.addCampaign(addCampaignRequest);

        ResHeader header = ResHeaderUtil.getResHeader(response, true);

        System.out.println(header.getFailures());
        print(response.getCampaignTypes());

        GetAllCampaignRequest request = new GetAllCampaignRequest();


        GetAllCampaignResponse allCampaignResponse = campaignService.getAllCampaign(request);
        print(allCampaignResponse.getCampaignTypes());
    }


    public static void print(List object) {
        for (Object type : object)
            System.out.println("===========" + type.toString());
    }
}
