package com.perfect.api.baidu;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by yousheng on 2014/8/12.
 *
 * @author yousheng
 */
@SuppressWarnings("unchecked")
public class BaiduApiService {

    private static Logger log = LoggerFactory.getLogger(BaiduApiService.class);

    private final CommonService commonService;

    public BaiduApiService(CommonService commonService) {
        this.commonService = commonService;
    }

    public AccountInfoType getAccountInfo() {
        try {
            GetAccountInfoRequest request = new GetAccountInfoRequest();
            AccountService accountService = commonService.getService(AccountService.class);
            GetAccountInfoResponse response = accountService.getAccountInfo(request);

            if (response == null) {
                return null;
            }
            return response.getAccountInfoType();
        } catch (ApiException e) {
            log.error("ERROR", e);
        }

        return null;
    }

    public List<CampaignType> getAllCampaign() {
        try {
            CampaignService campaignService = commonService.getService(CampaignService.class);

            GetAllCampaignRequest getAllCampaignRequest = new GetAllCampaignRequest();
            GetAllCampaignResponse response = campaignService.getAllCampaign(getAllCampaignRequest);
            if (response == null) {
                return Collections.EMPTY_LIST;
            }
            return response.getCampaignTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }

    public List<CampaignType> getCampaignById(List<Long> camIds) {
        try {
            CampaignService campaignService = commonService.getService(CampaignService.class);
            GetCampaignByCampaignIdRequest request = new GetCampaignByCampaignIdRequest();
            request.setCampaignIds(camIds);
            GetCampaignByCampaignIdResponse response = campaignService.getCampaignByCampaignId(request);
            if (response == null) {
                return Collections.EMPTY_LIST;
            }
            return response.getCampaignTypes();
        } catch (final Exception e) {
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
        } catch (final Exception e) {
            return Collections.EMPTY_LIST;
        }
    }


    /**
     * 计划包含最大1000个单元
     *
     * @param campaignIds
     * @return
     */
    public List<AdgroupType> getAllAdGroup(List<Long> campaignIds) {
        try {
            AdgroupService adgroupService = commonService.getService(AdgroupService.class);

            List<Long> subList = new ArrayList<>(20);
            List<AdgroupType> adgroupTypes = new ArrayList<>();
            for (int i = 1; i <= campaignIds.size(); i++) {
                Long camId = campaignIds.get(i - 1);
                subList.add(camId);

                if (i % 20 == 0) {
                    GetAdgroupByCampaignIdRequest getAdgroupByCampaignIdRequest = new GetAdgroupByCampaignIdRequest();
                    getAdgroupByCampaignIdRequest.setCampaignIds(subList);
                    GetAdgroupByCampaignIdResponse response = adgroupService.getAdgroupByCampaignId(getAdgroupByCampaignIdRequest);

                    if (response == null) {
                        Thread.sleep(5000);

                        response = adgroupService.getAdgroupByCampaignId(getAdgroupByCampaignIdRequest);
                        if (response == null) {
                            subList.clear();
                            continue;
                        }
                    }

                    for (CampaignAdgroup campaignAdgroup : response.getCampaignAdgroups()) {
                        adgroupTypes.addAll(campaignAdgroup.getAdgroupTypes());
                    }
                    subList.clear();
                }
            }


            if (!subList.isEmpty()) {
                GetAdgroupByCampaignIdRequest getAdgroupByCampaignIdRequest = new GetAdgroupByCampaignIdRequest();
                getAdgroupByCampaignIdRequest.setCampaignIds(subList);
                GetAdgroupByCampaignIdResponse response = adgroupService.getAdgroupByCampaignId(getAdgroupByCampaignIdRequest);

                if (response == null) {
                    Thread.sleep(5000);

                    response = adgroupService.getAdgroupByCampaignId(getAdgroupByCampaignIdRequest);
                    if (response == null) {
                        subList.clear();
                    }
                }

                for (CampaignAdgroup campaignAdgroup : response.getCampaignAdgroups()) {
                    adgroupTypes.addAll(campaignAdgroup.getAdgroupTypes());
                }
            }

            return adgroupTypes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }

    public List<KeywordType> getAllKeyword(List<Long> adgroupIds) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("推广单元总数: " + adgroupIds.size());
            }
            KeywordService keywordService = commonService.getService(KeywordService.class);

            List<KeywordType> keywordTypeList = new ArrayList<>();
            GetKeywordIdByAdgroupIdRequest kwIdRequest = new GetKeywordIdByAdgroupIdRequest();
            kwIdRequest.setAdgroupIds(adgroupIds);
            GetKeywordIdByAdgroupIdResponse response = keywordService.getKeywordIdByAdgroupId(kwIdRequest);
            if (response == null) {
                Thread.sleep(3000);
                response = keywordService.getKeywordIdByAdgroupId(kwIdRequest);
            }
            List<GroupKeywordId> groupKeywordIds = response.getGroupKeywordIds();
            List<Long> kwIds = new ArrayList<>(groupKeywordIds.size() << 1);

            for (GroupKeywordId gkId : groupKeywordIds) {
                kwIds.addAll(gkId.getKeywordIds());
            }

            GetKeywordByKeywordIdRequest getKeywordByKeywordIdRequest = new GetKeywordByKeywordIdRequest();
            getKeywordByKeywordIdRequest.setKeywordIds(kwIds);

            GetKeywordByKeywordIdResponse response1 = keywordService.getKeywordByKeywordId(getKeywordByKeywordIdRequest);
            if (response1 == null) {
                Thread.sleep(3000);
                response1 = keywordService.getKeywordByKeywordId(getKeywordByKeywordIdRequest);
            }

            if (log.isDebugEnabled()) {
                log.debug("当前请求得到的关键词总数: " + response1.getKeywordTypes().size());
            }

            keywordTypeList.addAll(response1.getKeywordTypes());

            if (keywordTypeList.size() == 0) {
                return Collections.EMPTY_LIST;
            }
            return keywordTypeList;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * 单元下创意数不超过50个
     *
     * @param adgroupIds
     * @return
     */
    public List<CreativeType> getAllCreative(List<Long> adgroupIds) {
        List<CreativeType> creativeTypes = new ArrayList<>();

        if (log.isDebugEnabled()) {
            log.debug("推广单元总数: " + adgroupIds.size());
        }
        try {
            CreativeService creativeService = commonService.getService(CreativeService.class);

            GetCreativeByAdgroupIdRequest getCreativeByAdgroupIdRequest = new GetCreativeByAdgroupIdRequest();
            List<Long> subList = new ArrayList<>(2 << 5);

            for (int i = 1; i <= adgroupIds.size(); i++) {
                Long agid = adgroupIds.get(i - 1);

                subList.add(agid);
                if (i % 200 == 0) {
                    getCreativeByAdgroupIdRequest.setAdgroupIds(subList);
                    GetCreativeByAdgroupIdResponse response = creativeService.getCreativeByAdgroupId(getCreativeByAdgroupIdRequest);

                    if (response != null) {
                        List<GroupCreative> creatives = response.getGroupCreatives();
                        for (GroupCreative groupCreative : creatives) {
                            creativeTypes.addAll(groupCreative.getCreativeTypes());
                        }
                    }
                    subList.clear();
                }
            }

            if (subList.size() > 0) {
                getCreativeByAdgroupIdRequest.setAdgroupIds(subList);
                GetCreativeByAdgroupIdResponse response = creativeService.getCreativeByAdgroupId(getCreativeByAdgroupIdRequest);

                if (response != null) {
                    List<GroupCreative> creatives = response.getGroupCreatives();

                    for (GroupCreative groupCreative : creatives) {
                        List<CreativeType> types = groupCreative.getCreativeTypes();
                        creativeTypes.addAll(types);
                    }
                }
                subList.clear();
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return creativeTypes;
    }

    public List<QualityType> getKeywordQuality(List<Long> keywordIds) {
        try {
            KeywordService keywordService = commonService.getService(KeywordService.class);
            GetKeywordQualityRequest request = new GetKeywordQualityRequest();
            request.setIds(keywordIds);
            request.setType(11);//3: 表示指定id数组为计划id 5:表示指定id数组为单元id 11:表示指定id为关键词id

            GetKeywordQualityResponse response = keywordService.getKeywordQuality(request);

            if (response == null) {
                return Collections.EMPTY_LIST;
            }
            return response.getQualities();
        } catch (ApiException e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }

    public List<KeywordType> setKeywordPrice(List<KeywordType> list) throws ApiException {
        if (list == null || list.size() == 0) {
            return Collections.EMPTY_LIST;
        }
        UpdateKeywordRequest request = new UpdateKeywordRequest();
        request.setKeywordTypes(list);

        KeywordService keywordService = commonService.getService(KeywordService.class);
        UpdateKeywordResponse response = keywordService.updateKeyword(request);
        if (response == null) {
            return Collections.EMPTY_LIST;
        }
        return response.getKeywordTypes();

    }

    public KeywordType setKeywordPrice(KeywordType type) throws ApiException {
        List<KeywordType> keywordTypes = setKeywordPrice(Arrays.asList(type));
        if (keywordTypes == null || keywordTypes.isEmpty()) {
            return null;
        }
        return keywordTypes.get(0);
    }

    public List<KeywordType> updateKeyword(List<KeywordType> list) throws ApiException {
        if (list == null || list.size() == 0) {
            return Collections.EMPTY_LIST;
        }
        UpdateKeywordRequest request = new UpdateKeywordRequest();
        request.setKeywordTypes(list);

        KeywordService keywordService = commonService.getService(KeywordService.class);
        UpdateKeywordResponse response = keywordService.updateKeyword(request);
        if (response == null) {
            return Collections.EMPTY_LIST;
        }
        return response.getKeywordTypes();
    }

}