/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/
package com.perfect.dao.campaign;

import com.perfect.dao.MongoCrudRepository;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.paging.PagerInfo;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface CampaignDAO extends MongoCrudRepository<CampaignDTO, Long> {

    List<Long> getAllCampaignId();

    List<CampaignDTO> find(Query query);

    List<CampaignDTO> findAllDownloadCampaign();

    CampaignDTO findByObjectId(String oid);

    void deleteByMongoId(String id);

    void updateByMongoId(CampaignDTO newCampaign, CampaignDTO campaignEntity);

    PagerInfo findByPageInfo(Query q, int pageSize, int pageNo);

    CampaignDTO findCampaignByName(String name);

    void updateLocalstatu(long cid);

    String insertReturnId(CampaignDTO campaignEntity);

    void softDel(long id);
}