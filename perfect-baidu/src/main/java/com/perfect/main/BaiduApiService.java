package com.perfect.main;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yousheng on 2014/8/12.
 *
 * @author yousheng
 */
public class BaiduApiService {

    private final CommonService commonService;


    public BaiduApiService(CommonService commonService) {
        this.commonService = commonService;
    }

    public AccountInfoType getAccountInfo() {
        try {
            GetAccountInfoRequest request = new GetAccountInfoRequest();
            AccountService accountService = commonService.getService(AccountService.class);
            GetAccountInfoResponse response = accountService.getAccountInfo(request);

            AccountInfoType infoType = response.getAccountInfoType();
            return infoType;
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<CampaignType> getAllCampaign() {
        try {
            CampaignService campaignService = commonService.getService(CampaignService.class);

            GetAllCampaignRequest getAllCampaignRequest = new GetAllCampaignRequest();
            GetAllCampaignResponse response = campaignService.getAllCampaign(getAllCampaignRequest);

            return response.getCampaignTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }


    public List<Long> getAllCampaignId() {
        try {
            CampaignService campaignService = commonService.getService(CampaignService.class);

            GetAllCampaignIdRequest request = new GetAllCampaignIdRequest();
            GetAllCampaignIdResponse response = campaignService.getAllCampaignId(request);

            return response.getCampaignIds();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }


    public List<CampaignAdgroup> getAllAdGroup(List<Long> campaignIds) {
        try {
            AdgroupService adgroupService = commonService.getService(AdgroupService.class);

            GetAdgroupByCampaignIdRequest getAllAdgroupRequest = new GetAdgroupByCampaignIdRequest();
            getAllAdgroupRequest.setCampaignIds(campaignIds);

            GetAdgroupByCampaignIdResponse response = adgroupService.getAdgroupByCampaignId(getAllAdgroupRequest);

            return response.getCampaignAdgroups();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }


    public List<KeywordType> getAllKeyword(List<Long> adgroupIds) {
        try {
            KeywordService keywordService = commonService.getService(KeywordService.class);

            GetKeywordIdByAdgroupIdRequest kwIdRequest = new GetKeywordIdByAdgroupIdRequest();
            kwIdRequest.setAdgroupIds(adgroupIds);

            GetKeywordIdByAdgroupIdResponse response = keywordService.getKeywordIdByAdgroupId(kwIdRequest);

            List<GroupKeywordId> groupKeywordIds = response.getGroupKeywordIds();

            List<Long> kwIds = new ArrayList<>(groupKeywordIds.size() << 1);

            for (GroupKeywordId gkId : groupKeywordIds) {
                kwIds.addAll(gkId.getKeywordIds());
            }

            GetKeywordByKeywordIdRequest getKeywordByKeywordIdRequest = new GetKeywordByKeywordIdRequest();

            getKeywordByKeywordIdRequest.setKeywordIds(kwIds);

            GetKeywordByKeywordIdResponse response1 = keywordService.getKeywordByKeywordId(getKeywordByKeywordIdRequest);
            return response1.getKeywordTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }


    public List<KeywordType> setKeywordPrice(List<KeywordType> list) {
        if (list == null || list.size() == 0) {
            return Collections.EMPTY_LIST;
        }
        UpdateKeywordRequest request = new UpdateKeywordRequest();
        request.setKeywordTypes(list);

        try {
            KeywordService keywordService = commonService.getService(KeywordService.class);
            UpdateKeywordResponse response = keywordService.updateKeyword(request);
            return response.getKeywordTypes();
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }
}
