package com.perfect.service;

import com.perfect.dto.campaign.CampaignDTO;

import java.util.List;

/**
 * Created by yousheng on 2014/8/12.
 *
 * @author yousheng
 */
public interface AccountDataService {

    public void initAccountData(String userName, Long accountId);

    public void updateAccountData(String userName, Long accountId);

    public void updateAccountData(String userName, Long accountId, List<Long> camIds);

    public List<CampaignDTO> getCampaign(String userName, Long accountId);

}