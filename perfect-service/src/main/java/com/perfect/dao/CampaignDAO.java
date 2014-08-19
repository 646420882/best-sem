/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/
package com.perfect.dao;

import com.perfect.entity.CampaignEntity;

import java.util.List;

public interface CampaignDAO extends MongoCrudRepository<CampaignEntity, Long> {

    List<Long> getAllCampaignId();
}