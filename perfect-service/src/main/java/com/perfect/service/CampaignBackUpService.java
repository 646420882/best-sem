package com.perfect.service;

import com.perfect.dto.campaign.CampaignDTO;

import java.util.List;

/**
 * Created by john on 2014/9/16.
 */
public interface CampaignBackUpService {
    CampaignDTO reducUpdate(String id);
    void reduceDel(String id);
    void deleteByOId(List<String> obj);
}
