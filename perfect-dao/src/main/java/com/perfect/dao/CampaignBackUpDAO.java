package com.perfect.dao;


import com.perfect.dto.backup.CampaignBackUpDTO;

public interface CampaignBackUpDAO extends MongoCrudRepository<CampaignBackUpDTO,Long>  {


    CampaignBackUpDTO findByObjectId(String id);

    void deleteByObjectId(String id);

    void deleteByCid(long cid);

    CampaignBackUpDTO findOne(long cid);
}