/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/
package com.perfect.mongodb.dao;


import com.perfect.entity.CampaignEntity;

import java.util.List;

public interface CampaignDAO {

    public CampaignEntity getCampaignById(Object id);

    public List<CampaignEntity> getAllCampaign();

    public List<CampaignEntity> findCampaigns(CampaignEntity campaignEntity);

    public void addCampaign(CampaignEntity campaignEntity);

    public int addAllCampaign(List<CampaignEntity> campaignEntities);

    public int updateAllCampaign(List<CampaignEntity> campaignEntities);

    public int deleteAllCampaigns(List<CampaignEntity> campaignEntities);
}