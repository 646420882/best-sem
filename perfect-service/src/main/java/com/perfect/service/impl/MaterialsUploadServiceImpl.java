package com.perfect.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.perfect.api.baidu.BaiduApiService;
import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.dao.account.AccountManageDAO;
import com.perfect.dao.adgroup.AdgroupDAO;
import com.perfect.dao.campaign.CampaignDAO;
import com.perfect.dao.creative.CreativeDAO;
import com.perfect.dao.keyword.KeywordDAO;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.service.MaterialsUploadService;
import com.perfect.utils.ObjectUtils;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created on 2015-10-14.
 *
 * @author dolphineor
 */
@Service("materialsUploadService")
public class MaterialsUploadServiceImpl implements MaterialsUploadService {

    @Resource
    private AccountManageDAO accountManageDAO;

    @Resource
    private CampaignDAO campaignDAO;

    @Resource
    private AdgroupDAO adgroupDAO;

    @Resource
    private KeywordDAO keywordDAO;

    @Resource
    private CreativeDAO creativeDAO;


    private final Function<Long, Map<Integer, Long>> uploadFunc1 = baiduUserId -> {
        Map<Integer, Long> result = Maps.newHashMap();

        if (!this.updateFunc.apply(baiduUserId)) {
            result.put(MODIFIED, baiduUserId);
        }

        if (!this.addFunc.apply(baiduUserId)) {
            result.put(NEW, baiduUserId);
        }

        if (!this.deleteFunc.apply(baiduUserId)) {
            result.put(DELETED, baiduUserId);
        }

        return result;
    };

    private final Function<String, Map<Integer, Set<Long>>> uploadFunc2 = sysUser -> {
        final Map<Integer, Set<Long>> result = Maps.newHashMap();
        result.put(NEW, Sets.newHashSet());
        result.put(MODIFIED, Sets.newHashSet());
        result.put(DELETED, Sets.newHashSet());

        accountManageDAO.getBaiduAccountItems(sysUser)
                .stream()
                .map(BaiduAccountInfoDTO::getId)
                .collect(Collectors.toList())
                .stream()
                .forEach(id -> uploadFunc1.apply(id).forEach((k, v) -> result.get(k).add(v)));

        return result;
    };

    private final Function<Long, Boolean> addFunc = baiduUserId -> {
        BaiduApiService baiduApiService = this.baiduApiServiceFunction.apply(baiduUserId);
        try {
            // 是否有新增的推广计划
            List<CampaignDTO> campaignDTOList = campaignDAO.findLocalChangedCampaigns(baiduUserId, NEW);
            if (!campaignDTOList.isEmpty()) {
                List<CampaignType> campaignTypeList = ObjectUtils.convert(campaignDTOList, CampaignType.class);
                // key: 推广计划名称, value: 推广计划凤巢id
                Map<String, Long> result = baiduApiService.addCampaign(campaignTypeList)
                        .stream()
                        .collect(Collectors.toMap(CampaignType::getCampaignName, CampaignType::getCampaignId));

                campaignDTOList.forEach(o -> {
                    Long campaignId = result.get(o.getCampaignName());
                    o.setCampaignId(campaignId);
                    campaignDAO.update(o, o.getId());
                });
            }

            /**
             * KEY: adgroupId
             * VALUE: campaignId
             */
            final Map<Long, Long> adgroupIdMap = new HashMap<>();

            // 是否有新增的推广单元
            List<AdgroupDTO> adgroupDTOList = adgroupDAO.findLocalChangedAdgroups(baiduUserId, NEW);
            if (!adgroupDTOList.isEmpty()) {
                // group by campaignId
                Map<Long, List<AdgroupDTO>> groupedAdgroupDTOMap = adgroupDTOList.stream()
                        .collect(Collectors.groupingBy(AdgroupDTO::getCampaignId));
                for (Map.Entry<Long, List<AdgroupDTO>> entry : groupedAdgroupDTOMap.entrySet()) {
                    List<AdgroupDTO> _list = entry.getValue();

                    _list.forEach(o -> adgroupIdMap.put(o.getAdgroupId(), entry.getKey()));

                    List<AdgroupType> adgroupTypeList = ObjectUtils.convert(_list, AdgroupType.class);
                    Map<String, Long> result = baiduApiService.addAdgroup(adgroupTypeList)
                            .stream()
                            .collect(Collectors.toMap(AdgroupType::getAdgroupName, AdgroupType::getAdgroupId));

                    _list.forEach(o -> {
                        Long adgroupId = result.get(o.getAdgroupName());
                        o.setAdgroupId(adgroupId);
                        adgroupDAO.update(o.getId(), o);
                    });
                }
            }

            // 是否有新增的关键词
            List<KeywordDTO> keywordDTOList = keywordDAO.findLocalChangedKeywords(baiduUserId, NEW);
            if (!keywordDTOList.isEmpty()) {
                // group by campaignId & adgroupId
                Map<String, List<KeywordDTO>> groupedKeywordMap = keywordDTOList.stream()
                        .collect(Collectors.groupingBy(keywordDTO -> adgroupIdMap.get(keywordDTO.getAdgroupId()) + "-" + keywordDTO.getAdgroupId()));
                for (Map.Entry<String, List<KeywordDTO>> entry : groupedKeywordMap.entrySet()) {
                    KeywordDTO firstElem = entry.getValue().get(0);

                    // ==================== 去重 ====================
                    final Map<String, KeywordDTO> sameAdgroupKeywordMap = keywordDAO
                            .findAllKeywordFromBaiduByAdgroupId(firstElem.getAccountId(), firstElem.getAdgroupId())
                            .stream()
                            .collect(Collectors.toMap(k -> k.getKeyword().trim().toUpperCase(), v -> v));

                    List<KeywordDTO> _list = entry.getValue()
                            .stream()
                            .collect(Collectors.groupingBy(k -> k.getKeyword().trim().toUpperCase()))
                            .values()
                            .stream()
                                    // 1. 找出新增关键词中本身重复的
                            .map(sourceList -> sourceList
                                    .stream()
                                    .sorted((o1, o2) -> {
                                        ObjectId oId1 = new ObjectId(o1.getId());
                                        ObjectId oId2 = new ObjectId(o2.getId());
                                        return Integer.compare(oId1.getTimestamp(), oId2.getTimestamp());
                                    })
                                    .findFirst()
                                    .orElse(null))
                            .filter(Objects::nonNull)
                                    // 2. 找出和本单元已经存在的百度关键词重复的
                            .filter(keywordDTO -> !sameAdgroupKeywordMap.containsKey(keywordDTO.getKeyword().trim().toUpperCase()))
                            .collect(Collectors.toList());


                    List<KeywordType> keywordTypeList = ObjectUtils.convert(_list, KeywordType.class);
                    Map<String, Long> result = baiduApiService.addKeyword(keywordTypeList)
                            .stream()
                            .collect(Collectors.toMap(KeywordType::getKeyword, KeywordType::getKeywordId));

                    _list.forEach(o -> {
                        Long keywordId = result.get(o.getKeyword());
                        o.setKeywordId(keywordId);
                        keywordDAO.update(o.getId(), o);
                    });
                }
            }

            // 是否有新增的创意
            List<CreativeDTO> creativeDTOList = creativeDAO.findLocalChangedCreative(baiduUserId, NEW);
            if (!creativeDTOList.isEmpty()) {
                for (CreativeDTO creativeDTO : creativeDTOList) {

                    // 去重
                    List<CreativeDTO> sameAdgroupCreativeList = creativeDAO
                            .findAllCreativeFromBaiduByAdgroupId(creativeDTO.getAccountId(), creativeDTO.getAdgroupId());

                    boolean isExists = isDuplicate(creativeDTO, sameAdgroupCreativeList);
                    if (isExists)
                        continue;

                    List<CreativeType> creativeTypeList = Collections.singletonList(ObjectUtils.convert(creativeDTO, CreativeType.class));

                    Long creativeId = baiduApiService.addCreative(creativeTypeList).remove(0).getCreativeId();
                    creativeDTO.setCreativeId(creativeId);

                    creativeDAO.update(creativeDTO.getId(), creativeDTO);
                }
            }

            return true;
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return false;
    };

    private final Function<Long, Boolean> updateFunc = baiduUserId -> {
        BaiduAccountInfoDTO baiduAccount = accountManageDAO.findByBaiduUserId(baiduUserId);
        CommonService commonService = BaiduServiceSupport
                .getCommonService(baiduAccount.getBaiduUserName(), baiduAccount.getBaiduPassword(), baiduAccount.getToken());

        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        try {
            // 更新账户信息
            AccountInfoType accountInfoType = ObjectUtils.convert(baiduAccount, AccountInfoType.class);
            accountInfoType.setUserid(baiduAccount.getId());
//            System.out.printf("Before: %s\n", JSON.toJSONString(accountInfoType));
            AccountInfoType aResult = baiduApiService.updateAccount(accountInfoType);
            System.out.printf("After: %s\n", JSON.toJSONString(aResult));

            // 是否有修改的推广计划
            List<CampaignDTO> campaignDTOList = campaignDAO.findLocalChangedCampaigns(baiduUserId, MODIFIED);
            if (!campaignDTOList.isEmpty()) {
                List<CampaignType> campaignTypeList = ObjectUtils.convert(campaignDTOList, CampaignType.class);
                // key: 推广计划名称, value: 推广计划凤巢id
                Map<String, Long> result = baiduApiService.updateCampaign(campaignTypeList)
                        .stream()
                        .collect(Collectors.toMap(CampaignType::getCampaignName, CampaignType::getCampaignId));

                campaignDTOList.forEach(o -> {
                    Long campaignId = result.get(o.getCampaignName());
                    o.setCampaignId(campaignId);
                    campaignDAO.update(o, o.getId());
                });
            }

            // 是否有修改的推广单元
            List<AdgroupDTO> adgroupDTOList = adgroupDAO.findLocalChangedAdgroups(baiduUserId, MODIFIED);
            if (!adgroupDTOList.isEmpty()) {
                // group by campaignId
                Map<Long, List<AdgroupDTO>> groupedAdgroupDTOMap = adgroupDTOList.stream()
                        .collect(Collectors.groupingBy(AdgroupDTO::getCampaignId));
                for (Map.Entry<Long, List<AdgroupDTO>> entry : groupedAdgroupDTOMap.entrySet()) {
                    List<AdgroupDTO> _list = entry.getValue();

                    List<AdgroupType> adgroupTypeList = ObjectUtils.convert(_list, AdgroupType.class);
                    Map<String, Long> result = baiduApiService.updateAdgroup(adgroupTypeList)
                            .stream()
                            .collect(Collectors.toMap(AdgroupType::getAdgroupName, AdgroupType::getAdgroupId));

                    _list.forEach(o -> {
                        Long adgroupId = result.get(o.getAdgroupName());
                        o.setAdgroupId(adgroupId);
                        adgroupDAO.update(o.getId(), o);
                    });
                }
            }

            /**
             * KEY: adgroupId
             * VALUE: campaignId
             */
            Map<Long, Long> adgroupIdMap = adgroupDAO.getAllAdgroupIdByBaiduAccountId(baiduUserId);

            // 是否有修改的关键词
            List<KeywordDTO> keywordDTOList = keywordDAO.findLocalChangedKeywords(baiduUserId, MODIFIED);
            if (!keywordDTOList.isEmpty()) {
                // group by campaignId & adgroupId
                Map<String, List<KeywordDTO>> groupedKeywordMap = keywordDTOList.stream()
                        .collect(Collectors.groupingBy(keywordDTO -> adgroupIdMap.get(keywordDTO.getAdgroupId()) + "-" + keywordDTO.getAdgroupId()));
                for (Map.Entry<String, List<KeywordDTO>> entry : groupedKeywordMap.entrySet()) {
                    KeywordDTO firstElem = entry.getValue().get(0);

                    // ==================== 去重 ====================
                    final Map<String, KeywordDTO> sameAdgroupKeywordMap = keywordDAO
                            .findAllKeywordFromBaiduByAdgroupId(firstElem.getAccountId(), firstElem.getAdgroupId())
                            .stream()
                            .collect(Collectors.toMap(k -> k.getKeyword().trim().toUpperCase(), v -> v));

                    List<KeywordDTO> _list = entry.getValue()
                            .stream()
                                    // 找出和本单元已经存在的百度关键词重复的
                            .filter(keywordDTO -> !sameAdgroupKeywordMap.containsKey(keywordDTO.getKeyword().trim().toUpperCase()))
                            .collect(Collectors.toList());

                    List<KeywordType> keywordTypeList = ObjectUtils.convert(_list, KeywordType.class);
                    Map<String, Long> result = baiduApiService.updateKeyword(keywordTypeList)
                            .stream()
                            .collect(Collectors.toMap(KeywordType::getKeyword, KeywordType::getKeywordId));

                    _list.forEach(o -> {
                        Long keywordId = result.get(o.getKeyword());
                        o.setKeywordId(keywordId);
                        keywordDAO.update(o.getId(), o);
                    });
                }
            }

            // 是否有修改的创意
            List<CreativeDTO> creativeDTOList = creativeDAO.findLocalChangedCreative(baiduUserId, MODIFIED);
            if (!creativeDTOList.isEmpty()) {
                for (CreativeDTO creativeDTO : creativeDTOList) {
                    // 去重
                    List<CreativeDTO> sameAdgroupCreativeList = creativeDAO
                            .findAllCreativeFromBaiduByAdgroupId(creativeDTO.getAccountId(), creativeDTO.getAdgroupId());

                    boolean isExists = isDuplicate(creativeDTO, sameAdgroupCreativeList);
                    if (isExists)
                        continue;

                    List<CreativeType> creativeTypeList = Collections.singletonList(ObjectUtils.convert(creativeDTO, CreativeType.class));

                    Long creativeId = baiduApiService.updateCreative(creativeTypeList).remove(0).getCreativeId();
                    creativeDTO.setCreativeId(creativeId);

                    creativeDAO.update(creativeDTO.getId(), creativeDTO);
                }
            }

            return true;
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return false;
    };

    private final Function<Long, Boolean> deleteFunc = baiduUserId -> {
        BaiduApiService baiduApiService = this.baiduApiServiceFunction.apply(baiduUserId);
        try {
            // 是否有删除的推广计划
            List<CampaignDTO> campaignDTOList = campaignDAO.findLocalChangedCampaigns(baiduUserId, DELETED);
            if (!campaignDTOList.isEmpty()) {
                List<Long> campaignIds = campaignDTOList.stream().map(CampaignDTO::getCampaignId).collect(Collectors.toList());
                Integer result = baiduApiService.deleteCampaign(campaignIds);
            }

            // 是否有删除的推广单元
            List<AdgroupDTO> adgroupDTOList = adgroupDAO.findLocalChangedAdgroups(baiduUserId, DELETED);
            if (!adgroupDTOList.isEmpty()) {
                List<Long> adgroupIds = adgroupDTOList.stream().map(AdgroupDTO::getAdgroupId).collect(Collectors.toList());
                String result = baiduApiService.deleteAdgroup(adgroupIds);
            }

            // 是否有删除的关键词
            List<KeywordDTO> keywordDTOList = keywordDAO.findLocalChangedKeywords(baiduUserId, DELETED);
            if (!keywordDTOList.isEmpty()) {
                List<Long> keywordIds = keywordDTOList.stream().map(KeywordDTO::getKeywordId).collect(Collectors.toList());
                Integer result = baiduApiService.deleteKeyword(keywordIds);
            }

            // 是否有删除的创意
            List<CreativeDTO> creativeDTOList = creativeDAO.findLocalChangedCreative(baiduUserId, DELETED);
            if (!creativeDTOList.isEmpty()) {
                List<Long> creativeIds = creativeDTOList.stream().map(CreativeDTO::getCreativeId).collect(Collectors.toList());
                Integer result = baiduApiService.deleteCreative(creativeIds);
            }

            return true;
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return false;
    };

    private final Function<Long, Boolean> pauseFunc1 = baiduUserId -> {
        BaiduApiService baiduApiService = this.baiduApiServiceFunction.apply(baiduUserId);
        // 获取当前百度账号下的所有推广计划, 并对其进行暂停投放的设置, 然后更新到凤巢
        campaignDAO.pause(baiduUserId);

        List<CampaignType> campaignTypeList = campaignDAO.findDownloadCampaignsByBaiduAccountId(baiduUserId)
                .stream()
                .map(o -> ObjectUtils.convert(o, CampaignType.class))
                .collect(Collectors.toList());

        try {
            baiduApiService.updateCampaign(campaignTypeList);

            return true;
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return false;
    };

    private final Function<String, List<Long>> pauseFunc2 = sysUser ->
            accountManageDAO.getBaiduAccountItems(sysUser)
                    .stream()
                    .map(BaiduAccountInfoDTO::getId)
                    .collect(Collectors.toList())
                    .stream()
                    .map(id -> Maps.immutableEntry(id, pauseFunc1.apply(id)))
                    .filter(e -> !e.getValue())
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

    private final Function<Long, BaiduApiService> baiduApiServiceFunction = baiduUserId -> {
        BaiduAccountInfoDTO baiduAccount = accountManageDAO.findByBaiduUserId(baiduUserId);
        CommonService commonService = BaiduServiceSupport
                .getCommonService(baiduAccount.getBaiduUserName(), baiduAccount.getBaiduPassword(), baiduAccount.getToken());

        return new BaiduApiService(commonService);
    };


//    private BaiduApiService getBaiduApiService(Long baiduUserId) {
//        BaiduAccountInfoDTO baiduAccount = accountManageDAO.findByBaiduUserId(baiduUserId);
//        CommonService commonService = BaiduServiceSupport
//                .getCommonService(baiduAccount.getBaiduUserName(), baiduAccount.getBaiduPassword(), baiduAccount.getToken());
//
//        return new BaiduApiService(commonService);
//    }

    /**
     * <p>新增的物料上传操作成功之后需要将返回的凤巢id设置到本地.
     *
     * @param baiduUserId 百度用户id
     * @return
     */
    @Override
    public boolean add(Long baiduUserId) {
        return addFunc.apply(baiduUserId);
    }

    @Override
    public boolean update(Long baiduUserId) {
        return updateFunc.apply(baiduUserId);
    }

    @Override
    public boolean delete(Long baiduUserId) {
        return deleteFunc.apply(baiduUserId);
    }

    @Override
    public Map<Integer, Long> upload(Long baiduUserId) {
        return uploadFunc1.apply(baiduUserId);
    }

    @Override
    public Map<Integer, Set<Long>> upload(String sysUser) {
        return uploadFunc2.apply(sysUser);
    }

    @Override
    public boolean pause(Long baiduUserId) {
        return pauseFunc1.apply(baiduUserId);
    }

    @Override
    public List<Long> pause(String sysUser) {
        return pauseFunc2.apply(sysUser);
    }
}
