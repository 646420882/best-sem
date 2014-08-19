package com.perfect.api.baidu;

import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.dao.AccountDAO;
import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.CampaignDAO;
import com.perfect.entity.AccountInfoEntity;
import com.perfect.entity.AdgroupEntity;
import com.perfect.entity.CampaignEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vbzer_000 on 2014/7/2.
 */
@Component
public class BaiduService {

    @Resource(name = "campaignDAO")
    private CampaignDAO campaignDAO;

    @Resource(name = "adgroupDAO")
    private AdgroupDAO adgroupDAO;

    /**
     * 初始化账户信息
     *
     * @param serviceFactory
     * @throws ApiException
     */
    public void init(ServiceFactory serviceFactory) throws ApiException {
        AccountService accountService = serviceFactory.getService(AccountService.class);

        GetAccountInfoRequest getAccountInfoRequest = new GetAccountInfoRequest();
        GetAccountInfoResponse getAccountInfoResponse = accountService.getAccountInfo(getAccountInfoRequest);

        if (ResultUtils.isFailed(accountService)) {
            return;
        }
        AccountInfoType accountInfoType = getAccountInfoResponse.getAccountInfoType();
        AccountInfoEntity accountInfoEntity = new AccountInfoEntity();
        BeanUtils.copyProperties(accountInfoType, accountInfoEntity);

        // 检查账户是否已经存在，如果存在就取消初始化
//        if (!accountDAO.isExists(accountInfoEntity.getUserid().toString())) {
//            accountDAO.insert(accountInfoEntity);
//            initCampagin(serviceFactory);
//        }
    }

    private void initCampagin(ServiceFactory serviceFactory) throws ApiException {
        CampaignService campaignService = serviceFactory.getService(CampaignService.class);
        GetAllCampaignResponse getAllCampaignResponse = campaignService.getAllCampaign(new GetAllCampaignRequest());
        if (getAllCampaignResponse == null) {
            int retry = 3;
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getAllCampaignResponse = campaignService.getAllCampaign(new GetAllCampaignRequest());
                if (getAllCampaignResponse != null) {
                    break;
                } else {
                    retry--;
                    if (retry == 0)
                        return;
                }

            }

        }
        List<CampaignEntity> campaignEntityList = new ArrayList<CampaignEntity>(getAllCampaignResponse.numberOfCampaignTypes());
        List<Long> campaginIds = new ArrayList<Long>(getAllCampaignResponse.numberOfCampaignTypes());

        for (CampaignType campaignType : getAllCampaignResponse.getCampaignTypes()) {
            CampaignEntity campaignEntity = new CampaignEntity();
            BeanUtils.copyProperties(campaignType, campaignEntity);
            campaignEntityList.add(campaignEntity);
            campaginIds.add(campaignType.getCampaignId());
        }
        campaignDAO.insertAll(campaignEntityList);

        initAdGroup(serviceFactory, campaginIds);

    }

    private void initAdGroup(ServiceFactory serviceFactory, List<Long> campaignIds) throws ApiException {

        AdgroupService adgroupService = serviceFactory.getService(AdgroupService.class);
        GetAdgroupByCampaignIdRequest getAdgroupByCampaignIdRequest = new GetAdgroupByCampaignIdRequest();
        getAdgroupByCampaignIdRequest.setCampaignIds(campaignIds);
        GetAdgroupByCampaignIdResponse getAdgroupByCampaignIdResponse = adgroupService.getAdgroupByCampaignId(getAdgroupByCampaignIdRequest);

        if (getAdgroupByCampaignIdResponse == null) {
            int retry = 3;
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getAdgroupByCampaignIdResponse = adgroupService.getAdgroupByCampaignId(getAdgroupByCampaignIdRequest);
                if (getAdgroupByCampaignIdResponse != null) {
                    break;
                } else {
                    retry--;
                    if (retry == 0) {
                        return;
                    }
                }
            }
        }

        List<CampaignAdgroup> campaignAdgroups = getAdgroupByCampaignIdResponse.getCampaignAdgroups();

        List<AdgroupType> newList = new ArrayList<>();
        for (CampaignAdgroup campaignAdgroup : campaignAdgroups) {
            List<AdgroupType> adgroupTypes = campaignAdgroup.getAdgroupTypes();
            newList.addAll(adgroupTypes);
        }


        List<AdgroupEntity> adgroupEntityList = new ArrayList<>(newList.size());
        for (AdgroupType adgroupType : newList) {
            AdgroupEntity adgroupEntity = new AdgroupEntity();
            BeanUtils.copyProperties(adgroupType, adgroupEntity);
            adgroupEntityList.add(adgroupEntity);
        }
        adgroupDAO.insertAll(adgroupEntityList);
    }

    public static void main(String args[]) {
        AccountInfoType accountInfoType = new AccountInfoType();

        accountInfoType.setBudget(0.11);

        AccountInfoEntity accountInfoEntity = new AccountInfoEntity();

        BeanUtils.copyProperties(accountInfoType, accountInfoEntity);

        System.out.println(accountInfoEntity.getBudget());
    }
}
