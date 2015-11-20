package com.perfect.api.baidu;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by yousheng on 2014/8/12.
 *
 * @author yousheng
 */
public class BaiduApiService {

    private static final Logger logger = LoggerFactory.getLogger(BaiduApiService.class);

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
            logger.error("ERROR", e);
        }

        return null;
    }

    public List<CampaignType> getAllCampaign() {
        try {
            CampaignService campaignService = commonService.getService(CampaignService.class);

            GetAllCampaignRequest getAllCampaignRequest = new GetAllCampaignRequest();
            GetAllCampaignResponse response = campaignService.getAllCampaign(getAllCampaignRequest);
            if (response == null) {
                return Collections.emptyList();
            }
            return response.getCampaignTypes();
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public List<CampaignType> getCampaignById(List<Long> camIds) {
        try {
            CampaignService campaignService = commonService.getService(CampaignService.class);
            GetCampaignByCampaignIdRequest request = new GetCampaignByCampaignIdRequest();
            request.setCampaignIds(camIds);
            GetCampaignByCampaignIdResponse response = campaignService.getCampaignByCampaignId(request);
            if (response == null) {
                return Collections.emptyList();
            }
            return response.getCampaignTypes();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<Long> getAllCampaignId() {
        try {
            CampaignService campaignService = commonService.getService(CampaignService.class);

            GetAllCampaignIdRequest request = new GetAllCampaignIdRequest();
            GetAllCampaignIdResponse response = campaignService.getAllCampaignId(request);

            return response.getCampaignIds();
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
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
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public List<KeywordType> getAllKeyword(List<Long> adgroupIds) {
        if (logger.isDebugEnabled()) {
            logger.debug("推广单元总数: " + adgroupIds.size());
        }

        try {
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

            if (logger.isDebugEnabled()) {
                logger.debug("当前请求得到的关键词总数: " + response1.getKeywordTypes().size());
            }

            keywordTypeList.addAll(response1.getKeywordTypes());

            if (keywordTypeList.size() == 0) {
                return Collections.emptyList();
            }

            return keywordTypeList;
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    /**
     * 单元下创意数不超过50个
     *
     * @param adgroupIds
     * @return
     */
    public List<CreativeType> getAllCreative(List<Long> adgroupIds) {
        List<CreativeType> creativeTypes = new ArrayList<>();

        if (logger.isDebugEnabled()) {
            logger.debug("推广单元总数: " + adgroupIds.size());
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
            request.setType(11);    // 3: 表示指定id数组为计划id 5:表示指定id数组为单元id 11:表示指定id为关键词id

            GetKeywordQualityResponse response = keywordService.getKeywordQuality(request);

            if (response == null) {
                return Collections.emptyList();
            }
            return response.getQualities();
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public List<KeywordType> setKeywordPrice(List<KeywordType> list) throws ApiException {
        if (list == null || list.size() == 0) {
            return Collections.emptyList();
        }

        UpdateKeywordRequest request = new UpdateKeywordRequest();
        request.setKeywordTypes(list);

        KeywordService keywordService = commonService.getService(KeywordService.class);
        UpdateKeywordResponse response = keywordService.updateKeyword(request);
        if (response == null) {
            return Collections.emptyList();
        }

        return response.getKeywordTypes();

    }

    // ============================== ADD ==============================
    public List<CampaignType> addCampaign(List<CampaignType> list) throws ApiException {
        AddCampaignRequest request = new AddCampaignRequest();
        request.setCampaignTypes(list);

        CampaignService campaignService = commonService.getService(CampaignService.class);
        AddCampaignResponse response = campaignService.addCampaign(request);
        if (response == null) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (response != null)
                return response.getCampaignTypes();
        }

        return Collections.emptyList();
    }

    public List<AdgroupType> addAdgroup(List<AdgroupType> list) throws ApiException {
        AddAdgroupRequest request = new AddAdgroupRequest();
        request.setAdgroupTypes(list);

        AdgroupService adgroupService = commonService.getService(AdgroupService.class);
        AddAdgroupResponse response = adgroupService.addAdgroup(request);
        if (response == null) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (response != null)
                return response.getAdgroupTypes();
        }

        return Collections.emptyList();
    }

    public List<KeywordType> addKeyword(List<KeywordType> list) throws ApiException {
        AddKeywordRequest request = new AddKeywordRequest();
        request.setKeywordTypes(list);

        KeywordService keywordService = commonService.getService(KeywordService.class);
        AddKeywordResponse response = keywordService.addKeyword(request);
        if (response == null) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (response != null)
                return response.getKeywordTypes();
        }

        return Collections.emptyList();
    }

    public List<CreativeType> addCreative(List<CreativeType> list) throws ApiException {
        AddCreativeRequest request = new AddCreativeRequest();
        request.setCreativeTypes(list);

        CreativeService creativeService = commonService.getService(CreativeService.class);
        AddCreativeResponse response = creativeService.addCreative(request);
        if (response == null) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (response != null)
                return response.getCreativeTypes();
        }

        return Collections.emptyList();
    }

    // ============================== UPDATE ==============================

    /**
     * <p>账户更新
     *
     * @param accountInfo
     * @return
     * @throws ApiException
     */
    public AccountInfoType updateAccount(AccountInfoType accountInfo) throws ApiException {
        if (accountInfo == null)
            return null;

        AccountInfoType accountInfoType = null;

        UpdateAccountInfoRequest request = new UpdateAccountInfoRequest();
        request.setAccountInfoType(accountInfo);

        AccountService accountService = commonService.getService(AccountService.class);
        UpdateAccountInfoResponse response = accountService.updateAccountInfo(request);
        if (response != null)
            accountInfoType = response.getAccountInfoType();

        return accountInfoType;
    }

    /**
     * <p>推广计划更新
     *
     * @param list 推广计划列表
     * @return
     * @throws ApiException
     */
    public List<CampaignType> updateCampaign(List<CampaignType> list) throws ApiException {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }

        UpdateCampaignRequest request = new UpdateCampaignRequest();
        request.setCampaignTypes(list);

        CampaignService campaignService = commonService.getService(CampaignService.class);
        UpdateCampaignResponse response = campaignService.updateCampaign(request);
        if (response == null)
            return Collections.emptyList();

        return response.getCampaignTypes();
    }

    /**
     * <p>推广单元更新
     *
     * @param list 推广单元列表
     * @return
     * @throws ApiException
     */
    public List<AdgroupType> updateAdgroup(List<AdgroupType> list) throws ApiException {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }

        UpdateAdgroupRequest request = new UpdateAdgroupRequest();
        request.setAdgroupTypes(list);

        AdgroupService adgroupService = commonService.getService(AdgroupService.class);
        UpdateAdgroupResponse response = adgroupService.updateAdgroup(request);
        if (response == null)
            return Collections.emptyList();

        return response.getAdgroupTypes();
    }

    /**
     * <p>关键词更新
     *
     * @param list 关键词列表
     * @return
     * @throws ApiException
     */
    public List<KeywordType> updateKeyword(List<KeywordType> list) throws ApiException {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }

        UpdateKeywordRequest request = new UpdateKeywordRequest();
        request.setKeywordTypes(list);

        KeywordService keywordService = commonService.getService(KeywordService.class);
        UpdateKeywordResponse response = keywordService.updateKeyword(request);
        if (response == null) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (response != null)
                return response.getKeywordTypes();
        }

        return Collections.emptyList();
    }

    /**
     * <p>创意更新
     *
     * @param list 创意列表
     * @return
     * @throws ApiException
     */
    public List<CreativeType> updateCreative(List<CreativeType> list) throws ApiException {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }

        UpdateCreativeRequest request = new UpdateCreativeRequest();
        request.setCreativeTypes(list);

        CreativeService creativeService = commonService.getService(CreativeService.class);
        UpdateCreativeResponse response = creativeService.updateCreative(request);
        if (response == null)
            return Collections.emptyList();

        return response.getCreativeTypes();
    }

    // ============================== DELETE ==============================
    public Integer deleteCampaign(List<Long> campaignIds) throws ApiException {
        DeleteCampaignRequest request = new DeleteCampaignRequest();
        request.setCampaignIds(campaignIds);

        CampaignService campaignService = commonService.getService(CampaignService.class);
        DeleteCampaignResponse response = campaignService.deleteCampaign(request);
        if (response == null) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (response != null)
                return response.getResult();
        }

        return -1;

    }

    public String deleteAdgroup(List<Long> adgroupIds) throws ApiException {
        DeleteAdgroupRequest request = new DeleteAdgroupRequest();
        request.setAdgroupIds(adgroupIds);

        AdgroupService adgroupService = commonService.getService(AdgroupService.class);
        DeleteAdgroupResponse response = adgroupService.deleteAdgroup(request);
        if (response == null) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (response != null)
                return response.getResponse();
        }

        return "";

    }

    public Integer deleteKeyword(List<Long> keywordIds) throws ApiException {
        DeleteKeywordRequest request = new DeleteKeywordRequest();
        request.setKeywordIds(keywordIds);

        KeywordService keywordService = commonService.getService(KeywordService.class);
        DeleteKeywordResponse response = keywordService.deleteKeyword(request);
        if (response == null) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (response != null)
                return response.getResult();
        }

        return -1;

    }

    public Integer deleteCreative(List<Long> creativeIds) throws ApiException {
        DeleteCreativeRequest request = new DeleteCreativeRequest();
        request.setCreativeIds(creativeIds);

        CreativeService creativeService = commonService.getService(CreativeService.class);
        DeleteCreativeResponse response = creativeService.deleteCreative(request);
        if (response == null) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (response != null)
                return response.getResult();
        }

        return -1;

    }
}