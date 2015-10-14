package com.perfect.service.impl;

import com.alibaba.fastjson.JSON;
import com.perfect.api.baidu.BaiduApiService;
import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.AccountInfoType;
import com.perfect.autosdk.sms.v3.CampaignType;
import com.perfect.dao.account.AccountManageDAO;
import com.perfect.dao.adgroup.AdgroupDAO;
import com.perfect.dao.campaign.CampaignDAO;
import com.perfect.dao.creative.CreativeDAO;
import com.perfect.dao.keyword.KeywordDAO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.service.MaterialsUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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


    @Override
    public boolean uploadAdditions(Long baiduUserId) {
        // TODO 上传新增
        // 是否有新增的推广计划

        // 是否有新增的推广单元

        // 是否有新增的关键词

        // 是否有新增的创意

        return false;
    }

    @Override
    public boolean uploadModifications(Long baiduUserId) {
        // TODO 上传修改
        // 更新账户信息
        BaiduAccountInfoDTO baiduAccount = accountManageDAO.findByBaiduUserId(baiduUserId);
        CommonService commonService = BaiduServiceSupport
                .getCommonService(baiduAccount.getBaiduUserName(), baiduAccount.getBaiduPassword(), baiduAccount.getToken());

        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        AccountInfoType accountInfoType = new AccountInfoType();
        BeanUtils.copyProperties(baiduAccount, accountInfoType);
        accountInfoType.setUserid(baiduAccount.getId());
        System.out.println(JSON.toJSONString(accountInfoType));
        try {
            AccountInfoType result = baiduApiService.updateAccount(accountInfoType);
            System.out.println(JSON.toJSONString(result));
        } catch (ApiException e) {
            e.printStackTrace();
        }

        // 是否有修改的推广计划

        // 是否有修改的推广单元

        // 是否有修改的关键词

        // 是否有修改的创意

        return false;
    }

    @Override
    public boolean uploadDeletions(Long baiduUserId) {
        // TODO 上传删除
        // 是否有删除的推广计划

        // 是否有删除的推广单元

        // 是否有删除的关键词

        // 是否有删除的创意

        return false;
    }

    @Override
    public void pause(Long baiduUserId) {
        BaiduAccountInfoDTO baiduAccount = accountManageDAO.findByBaiduUserId(baiduUserId);
        CommonService commonService = BaiduServiceSupport
                .getCommonService(baiduAccount.getBaiduUserName(), baiduAccount.getBaiduPassword(), baiduAccount.getToken());

        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        List<CampaignType> campaignTypeList = new ArrayList<>();
        // 获取当前百度账号下的所有推广计划, 并对其进行暂停投放的设置, 然后更新到凤巢
        campaignDAO.pause(baiduAccount.getId());

        campaignDAO.findDownloadCampaignsByBaiduAccountId(baiduAccount.getId()).forEach(e -> {
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
}
