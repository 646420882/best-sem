package com.perfect.dao.campaign;


import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.backup.CampaignBackUpDTO;

import java.util.List;

public interface CampaignBackUpDAO extends HeyCrudRepository<CampaignBackUpDTO, Long> {


    CampaignBackUpDTO findByObjectId(String id);

//    void deleteByObjectId(String id);

    void deleteByCid(long cid);

    CampaignBackUpDTO findOne(long cid);

    void deleteByOId(List<String> strings);
}