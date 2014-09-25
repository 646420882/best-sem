package com.perfect.api.baidu;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.commons.context.ApplicationContextHelper;
import com.perfect.dto.CreativeDTO;
import com.perfect.entity.KeywordEntity;
import com.perfect.entity.bidding.KeywordRankEntity;
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
    private ApplicationContextHelper context;
    private BaiduPreviewHelperFactory baiduPreviewHelperFactory;

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

    public KeywordType setKeywordPrice(KeywordType type) {
        List<KeywordType> keywordTypes = setKeywordPrice(Arrays.asList(type));
        if(keywordTypes == null || keywordTypes.isEmpty()){
            return null;
        }
        return keywordTypes.get(0);
    }

    public List<BaiduSpiderHelper.PreviewData> getPreviewData(int region, List<String> keyList, BaiduSpiderHelper helper) {
        GetPreviewRequest request = new GetPreviewRequest();
        request.setKeyWords(keyList);
        request.setRegion(region);
        request.setDevice(0);
        request.setPage(0);
        List<BaiduSpiderHelper.PreviewData> previewDatas = helper.getPageData(keyList.toArray(new
                String[]{}), region);
        return previewDatas;
    }


    @SuppressWarnings("unchecked")
    public Map<String, KeywordRankEntity> getKeywordRank(Map<KeywordEntity, List<Integer>> keys, String host) {
        if (keys == null || keys.isEmpty()) {
            return Collections.EMPTY_MAP;
        }

        Map<Integer, List<KeywordEntity>> regionDataMap = new HashMap<>();
        Map<String, KeywordEntity> keywordEntityMap = new HashMap<>();

        initIdMap(keys.keySet(), keywordEntityMap);
        convertMap(keys, regionDataMap);
        BaiduSpiderHelper baiduSpiderHelper = baiduPreviewHelperFactory.createInstance(commonService);

        List<BaiduSpiderHelper.PreviewData> resultDataList = new ArrayList<>();
        for (Map.Entry<Integer, List<KeywordEntity>> entry : regionDataMap.entrySet()) {
            Integer region = entry.getKey();

            List<KeywordEntity> keyEntityList = entry.getValue();

            if (keyEntityList.size() <= 5) {
                List<String> keyList = new ArrayList<>();
                for (KeywordEntity keywordEntity : keyEntityList) {
                    keyList.add(keywordEntity.getKeyword());
                }
                resultDataList.addAll(getPreviewData(region, keyList, baiduSpiderHelper));
            } else {
                List<String> temp = new ArrayList<>();
                for (KeywordEntity key : keyEntityList) {
                    temp.add(key.getKeyword());

                    if (temp.size() == 5) {
                        resultDataList.addAll(getPreviewData(region, temp, baiduSpiderHelper));
                        temp.clear();
                    }
                }

                if (!temp.isEmpty()) {
                    resultDataList.addAll(getPreviewData(region, temp, baiduSpiderHelper));
                }
            }
        }

        Map<String, KeywordRankEntity> keywordRankEntityMap = new HashMap<>();

        if (resultDataList != null && !resultDataList.isEmpty()) {
            for (BaiduSpiderHelper.PreviewData previewData : resultDataList) {
                if ((previewData.getLeft() == null || previewData.getLeft().isEmpty()) && (previewData.getRight() == null || previewData.getRight().isEmpty())) {
                    continue;
                }

                String keyword = previewData.getKeyword();
                int region = previewData.getRegion();
                int device = previewData.getDevice();

                KeywordEntity kwid = keywordEntityMap.get(keyword);

                KeywordRankEntity entity = null;
                if (keywordRankEntityMap.containsKey(keyword)) {
                    entity = keywordRankEntityMap.get(keyword);
                } else {
                    entity = new KeywordRankEntity();
                    if (kwid.getKeywordId() == null) {
                        entity.setKwid(kwid.getId());
                    } else {
                        entity.setKwid(kwid.getKeywordId().toString());
                    }
                    entity.setName(keyword);
                    entity.setTargetRank(new HashMap<Integer, Integer>());
                    entity.setDevice(device);
                    entity.setTime(System.currentTimeMillis());

                    keywordRankEntityMap.put(keyword, entity);
                }


                int rank = 0;
                boolean found = false;
                for (CreativeDTO leftEntity : previewData.getLeft()) {
                    rank++;
                    String url = leftEntity.getUrl();
                    if (url.contains(host)) {
                        entity.getTargetRank().put(region, rank);
                        found = true;
                        break;
                    }

                }
                if (found) {
                    continue;
                }
                rank = 0;
                for (CreativeDTO rightEntity : previewData.getRight()) {
                    rank--;
                    String url = rightEntity.getUrl();
                    if (url.contains(host)) {
                        entity.getTargetRank().put(region, rank);
                        break;
                    }
                }
            }
        }


        return keywordRankEntityMap;
    }

    private void initIdMap(Set<KeywordEntity> keywordEntities, Map<String, KeywordEntity> keywordIdMap) {
        for (KeywordEntity keywordEntity : keywordEntities) {
            keywordIdMap.put(keywordEntity.getKeyword(), keywordEntity);
        }
    }

    private void convertMap(Map<KeywordEntity, List<Integer>> keys, Map<Integer, List<KeywordEntity>> regionDataMap) {
        if (keys.isEmpty())
            return;

        for (Map.Entry<KeywordEntity, List<Integer>> entry : keys.entrySet()) {
            List<Integer> regionList = entry.getValue();

            KeywordEntity keyword = entry.getKey();

            for (Integer integer : regionList) {
                if (regionDataMap.containsKey(integer)) {
                    regionDataMap.get(integer).add(keyword);
                } else {
                    List<KeywordEntity> keyList = new ArrayList<>();
                    keyList.add(keyword);
                    regionDataMap.put(integer, keyList);
                }
            }
        }
    }

    public void setContext(ApplicationContextHelper context) {
        this.context = context;
    }

    public ApplicationContextHelper getContext() {
        return context;
    }

    public void setBaiduPreviewHelperFactory(BaiduPreviewHelperFactory baiduPreviewHelperFactory) {
        this.baiduPreviewHelperFactory = baiduPreviewHelperFactory;
    }

    public BaiduPreviewHelperFactory getBaiduPreviewHelperFactory() {
        return baiduPreviewHelperFactory;
    }
}