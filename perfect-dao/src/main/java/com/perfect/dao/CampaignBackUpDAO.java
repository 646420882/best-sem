package com.perfect.dao;

import com.perfect.entity.backup.CampaignBackUpEntity;

public interface CampaignBackUpDAO extends MongoCrudRepository<CampaignBackUpEntity,Long>  {


    CampaignBackUpEntity findByObjectId(String id);

    void deleteByObjectId(String id);

    void deleteByCid(long cid);

    CampaignBackUpEntity findOne(long cid);
}