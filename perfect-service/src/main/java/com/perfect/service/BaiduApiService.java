package com.perfect.service;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.dto.CreativeDTO;
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.entity.bidding.StrategyEntity;
import com.perfect.service.impl.HTMLAnalyseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by yousheng on 2014/8/12.
 *
 * @author yousheng
 */
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
            AccountInfoType infoType = response.getAccountInfoType();
            return infoType;
        } catch (ApiException e) {
            log.error("ERROR", e);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
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

    @SuppressWarnings("unchecked")
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

    @SuppressWarnings("unchecked")
    public List<Long> getAllCampaignId() {
        try {
            CampaignService campaignService = commonService.getService(CampaignService.class);

            GetAllCampaignIdRequest request = new GetAllCampaignIdRequest();
            GetAllCampaignIdResponse response = campaignService.getAllCampaignId(request);


            return response.getCampaignIds();
        } catch (Exception e) {
        }
        return Collections.EMPTY_LIST;
    }


    /**
     * 计划包含最大1000个单元
     *
     * @param campaignIds
     * @return
     */
    @SuppressWarnings("unchecked")
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

    @SuppressWarnings("unchecked")
    public List<KeywordType> getAllKeyword(List<Long> adgroupIds) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("推广单元总数: " + adgroupIds.size());
            }
            KeywordService keywordService = commonService.getService(KeywordService.class);


            //分批请求
            List<KeywordType> keywordTypeList = new ArrayList<>();
            List<Long> subList = new ArrayList<>(3);
            for (Long adgroupId : adgroupIds) {
                if (subList.size() == 4) {

                    GetKeywordIdByAdgroupIdRequest kwIdRequest = new GetKeywordIdByAdgroupIdRequest();
                    kwIdRequest.setAdgroupIds(subList);
                    GetKeywordIdByAdgroupIdResponse response = keywordService.getKeywordIdByAdgroupId(kwIdRequest);
                    if (response == null) {
                        Thread.sleep(5000);
                        response = keywordService.getKeywordIdByAdgroupId(kwIdRequest);
                        if (response == null) {
                            subList.clear();
                            continue;
                        }
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
                        Thread.sleep(5000);
                        response1 = keywordService.getKeywordByKeywordId(getKeywordByKeywordIdRequest);
                        if (response1 == null) {
                            subList.clear();
                            continue;
                        }
                    }

                    keywordTypeList.addAll(response1.getKeywordTypes());

                    subList.clear();
                    continue;
                }
                subList.add(adgroupId);
            }

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

    @SuppressWarnings("unchecked")
    public List<KeywordType> setKeywordPrice(List<KeywordType> list) {
        if (list == null || list.size() == 0) {
            return Collections.EMPTY_LIST;
        }
        UpdateKeywordRequest request = new UpdateKeywordRequest();
        request.setKeywordTypes(list);

        try {
            KeywordService keywordService = commonService.getService(KeywordService.class);
            UpdateKeywordResponse response = keywordService.updateKeyword(request);
            if (response == null) {
                return Collections.EMPTY_LIST;
            }
            return response.getKeywordTypes();
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Integer> checkKeywordRank(List<BiddingRuleEntity> keys, String host) {
        if (keys == null || keys.isEmpty()) {
            return Collections.EMPTY_MAP;
        }

        HTMLAnalyseService rankService = HTMLAnalyseServiceImpl.createService((com.perfect.autosdk.core.ServiceFactory) commonService);

        Map<String, Integer> resultMap = new HashMap<>();

        for (BiddingRuleEntity entity : keys) {

            GetPreviewRequest request = new GetPreviewRequest();
            request.setKeyWords(Arrays.asList(entity.getKeyword()));
            request.setRegion(entity.getStrategyEntity().getRegionTarget());
            request.setDevice(entity.getStrategyEntity().getType());
            request.setPage(0);

            List<HTMLAnalyseServiceImpl.PreviewData> previewDatas = rankService.getPageData(request);

            if (previewDatas != null) {
                HTMLAnalyseServiceImpl.PreviewData previewData = previewDatas.get(0);
                if ((previewData.getLeft() == null || previewData.getLeft().isEmpty()) && (previewData.getRight() == null || previewData.getRight().isEmpty())) {
                    continue;
                }

                StrategyEntity strategyEntity = entity.getStrategyEntity();

                int pos = strategyEntity.getPositionStrategy();
                int rank = 0;
                for (CreativeDTO leftEntity : previewData.getLeft()) {
                    rank++;
                    String url = leftEntity.getUrl();
                    if (url.equals(host)) {
                        resultMap.put(entity.getKeyword(), rank);
                        break;
                    }

                }
                if (!resultMap.isEmpty()) {
                    continue;
                }
                rank = 0;
                for (CreativeDTO rightEntity : previewData.getRight()) {
                    rank--;
                    String url = rightEntity.getUrl();
                    if (url.equals(host)) {
                        resultMap.put(entity.getKeyword(), rank);
                        break;
                    }
                }
                if (!resultMap.containsKey(entity.getKeyword())) {
                    resultMap.put(entity.getKeyword(), null);
                }
            }

        }

        return resultMap;
    }
//        int rt = entity.getStrategyEntity().getRegionTarget();
//        if (getPreviewRequests.containsKey(rt)) {
//            List<String> keywords = getPreviewRequests.get(rt);
//            keywords.add(entity.getKeyword());
//        } else {
//            List<String> keywords = new ArrayList<>();
//            keywords.add(entity.getKeyword());
//
//            getPreviewRequests.put(rt, keywords);
//        }

//        for (Map.Entry<Integer, List<String>> entry : getPreviewRequests.entrySet()) {
//            Integer key = entry.getKey();
//
//            List<String> keywords = entry.getValue();
//
//            if(keywords.size() <= 5){
//                GetPreviewRequest request = new GetPreviewRequest();
//                request.setKeyWords(keywords);
//                request.setRegion(key);
//                request.
//            }
//
//            do {
//
//                GetPreviewRequest getPreviewRequest = new GetPreviewRequest();
//                getPreviewRequest.setKeyWords();
//            } while (keywords.size() > 5);
//        }
}