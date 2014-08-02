package com.perfect.schedule.utils;

import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.dao.AccountDAO;
import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.CampaignDAO;
import com.perfect.entity.AccountInfoEntity;
import org.springframework.beans.BeanUtils;

/**
 * Created by vbzer_000 on 2014/7/1.
 */
public class AccountDataUpdateTask implements Runnable {

    private final AccountDAO accountDAO;
    private final CampaignDAO campaignDAO;
    private final AdgroupDAO adgroupDAO;

    private ServiceFactory serviceFactory;

    public AccountDataUpdateTask(ServiceFactory serviceFactory, AccountDAO accountDAO, CampaignDAO campaignDAO, AdgroupDAO adgroupDAO) {
        this.serviceFactory = serviceFactory;
        this.accountDAO = accountDAO;
        this.campaignDAO = campaignDAO;
        this.adgroupDAO = adgroupDAO;
    }

    @Override
    public void run() {
        try {
            AccountService accountService = serviceFactory.getService(AccountService.class);

            GetAccountInfoRequest getAccountInfoRequest = new GetAccountInfoRequest();
            GetAccountInfoResponse getAccountInfoResponse = accountService.getAccountInfo(getAccountInfoRequest);

            AccountInfoType accountInfoType = getAccountInfoResponse.getAccountInfoType();

            AccountInfoEntity accountInfoEntity = new AccountInfoEntity();
            BeanUtils.copyProperties(accountInfoType,accountInfoEntity);
            accountDAO.updateById(accountInfoEntity);

            CampaignService campaignService = serviceFactory.getService(CampaignService.class);

            GetAllCampaignRequest getAllCampaignRequest = new GetAllCampaignRequest();

            campaignService.getAllCampaign(getAllCampaignRequest);

        } catch (ApiException e) {
            e.printStackTrace();
        }

    }
}
