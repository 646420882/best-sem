package com.perfect.service.impl;

import com.alibaba.fastjson.JSON;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 2015-10-14.
 *
 * @author dolphineor
 */
@Service("materialsUploadService")
public class MaterialsUploadServiceImpl implements MaterialsUploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaterialsUploadService.class);


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


    /**
     * <p>新增的物料上传操作成功之后需要将返回的凤巢id设置到本地.
     *
     * @param baiduUserId 百度用户id
     * @return
     */
    @Override
    public boolean uploadAdditions(Long baiduUserId) {
        BaiduApiService baiduApiService = getBaiduApiService(baiduUserId);
        try {
            // 是否有新增的推广计划
            List<CampaignDTO> campaignDTOList = campaignDAO.findLocalChangedCampaigns(baiduUserId, 1);
            if (!campaignDTOList.isEmpty()) {
                List<CampaignType> campaignTypeList = ObjectUtils.convert(campaignDTOList, CampaignType.class);
                List<CampaignType> result = baiduApiService.addCampaign(campaignTypeList);
            }

            // 是否有新增的推广单元
            List<AdgroupDTO> adgroupDTOList = adgroupDAO.findLocalChangedAdgroups(baiduUserId, 1);
            if (!adgroupDTOList.isEmpty()) {
                List<AdgroupType> adgroupTypeList = ObjectUtils.convert(adgroupDTOList, AdgroupType.class);
                List<AdgroupType> result = baiduApiService.addAdgroup(adgroupTypeList);
            }

            // 是否有新增的关键词
            List<KeywordDTO> keywordDTOList = keywordDAO.findLocalChangedKeywords(baiduUserId, 1);
            if (!keywordDTOList.isEmpty()) {
                List<KeywordType> keywordTypeList = ObjectUtils.convert(keywordDTOList, KeywordType.class);
                List<KeywordType> result = baiduApiService.addKeyword(keywordTypeList);
            }

            // 是否有新增的创意
            List<CreativeDTO> creativeDTOList = creativeDAO.findLocalChangedCreative(baiduUserId, 1);
            if (!creativeDTOList.isEmpty()) {
                List<CreativeType> creativeTypeList = ObjectUtils.convert(creativeDTOList, CreativeType.class);
                List<CreativeType> result = baiduApiService.addCreative(creativeTypeList);
            }

            return true;
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean uploadModifications(Long baiduUserId) {
        BaiduAccountInfoDTO baiduAccount = accountManageDAO.findByBaiduUserId(baiduUserId);
        CommonService commonService = BaiduServiceSupport
                .getCommonService(baiduAccount.getBaiduUserName(), baiduAccount.getBaiduPassword(), baiduAccount.getToken());

        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        try {
            // 更新账户信息
            AccountInfoType accountInfoType = new AccountInfoType();
            BeanUtils.copyProperties(baiduAccount, accountInfoType);
            accountInfoType.setUserid(baiduAccount.getId());
            System.out.println(JSON.toJSONString(accountInfoType));
            AccountInfoType aResult = baiduApiService.updateAccount(accountInfoType);
            System.out.println(JSON.toJSONString(aResult));

            // 是否有修改的推广计划
            List<CampaignDTO> campaignDTOList = campaignDAO.findLocalChangedCampaigns(baiduUserId, 2);
            if (!campaignDTOList.isEmpty()) {
                List<CampaignType> campaignTypeList = ObjectUtils.convert(campaignDTOList, CampaignType.class);
                List<CampaignType> result = baiduApiService.updateCampaign(campaignTypeList);
            }

            // 是否有修改的推广单元
            List<AdgroupDTO> adgroupDTOList = adgroupDAO.findLocalChangedAdgroups(baiduUserId, 2);
            if (!adgroupDTOList.isEmpty()) {
                List<AdgroupType> adgroupTypeList = ObjectUtils.convert(adgroupDTOList, AdgroupType.class);
                List<AdgroupType> result = baiduApiService.updateAdgroup(adgroupTypeList);
            }

            // 是否有修改的关键词
            List<KeywordDTO> keywordDTOList = keywordDAO.findLocalChangedKeywords(baiduUserId, 2);
            if (!keywordDTOList.isEmpty()) {
                List<KeywordType> keywordTypeList = ObjectUtils.convert(keywordDTOList, KeywordType.class);
                List<KeywordType> result = baiduApiService.updateKeyword(keywordTypeList);
            }

            // 是否有修改的创意
            List<CreativeDTO> creativeDTOList = creativeDAO.findLocalChangedCreative(baiduUserId, 2);
            if (!creativeDTOList.isEmpty()) {
                List<CreativeType> creativeTypeList = ObjectUtils.convert(creativeDTOList, CreativeType.class);
                List<CreativeType> result = baiduApiService.updateCreative(creativeTypeList);
            }

            return true;
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean uploadDeletions(Long baiduUserId) {
        BaiduApiService baiduApiService = getBaiduApiService(baiduUserId);
        try {
            // 是否有删除的推广计划
            List<CampaignDTO> campaignDTOList = campaignDAO.findLocalChangedCampaigns(baiduUserId, 3);
            if (!campaignDTOList.isEmpty()) {
                List<Long> campaignIds = campaignDTOList.stream().map(CampaignDTO::getCampaignId).collect(Collectors.toList());
                Integer result = baiduApiService.deleteCampaign(campaignIds);
            }

            // 是否有删除的推广单元
            List<AdgroupDTO> adgroupDTOList = adgroupDAO.findLocalChangedAdgroups(baiduUserId, 3);
            if (!adgroupDTOList.isEmpty()) {
                List<Long> adgroupIds = adgroupDTOList.stream().map(AdgroupDTO::getAdgroupId).collect(Collectors.toList());
                String result = baiduApiService.deleteAdgroup(adgroupIds);
            }

            // 是否有删除的关键词
            List<KeywordDTO> keywordDTOList = keywordDAO.findLocalChangedKeywords(baiduUserId, 3);
            if (!keywordDTOList.isEmpty()) {
                List<Long> keywordIds = keywordDTOList.stream().map(KeywordDTO::getKeywordId).collect(Collectors.toList());
                Integer result = baiduApiService.deleteKeyword(keywordIds);
            }

            // 是否有删除的创意
            List<CreativeDTO> creativeDTOList = creativeDAO.findLocalChangedCreative(baiduUserId, 3);
            if (!creativeDTOList.isEmpty()) {
                List<Long> creativeIds = creativeDTOList.stream().map(CreativeDTO::getCreativeId).collect(Collectors.toList());
                Integer result = baiduApiService.deleteCreative(creativeIds);
            }

            return true;
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void pause(Long baiduUserId) {
        BaiduApiService baiduApiService = getBaiduApiService(baiduUserId);
        List<CampaignType> campaignTypeList = new ArrayList<>();
        // 获取当前百度账号下的所有推广计划, 并对其进行暂停投放的设置, 然后更新到凤巢
        campaignDAO.pause(baiduUserId);

        campaignDAO.findDownloadCampaignsByBaiduAccountId(baiduUserId).forEach(e -> {
            CampaignType campaignType = new CampaignType();
            BeanUtils.copyProperties(e, campaignType);
            campaignTypeList.add(campaignType);
        });

        try {
            baiduApiService.updateCampaign(campaignTypeList);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }


    private BaiduApiService getBaiduApiService(Long baiduUserId) {
        BaiduAccountInfoDTO baiduAccount = accountManageDAO.findByBaiduUserId(baiduUserId);
        CommonService commonService = BaiduServiceSupport
                .getCommonService(baiduAccount.getBaiduUserName(), baiduAccount.getBaiduPassword(), baiduAccount.getToken());

        return new BaiduApiService(commonService);
    }
}
