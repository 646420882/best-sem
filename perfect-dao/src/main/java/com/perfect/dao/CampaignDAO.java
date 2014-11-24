/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/
package com.perfect.dao;

import com.perfect.entity.CampaignEntity;
import com.perfect.dao.mongodb.utils.PagerInfo;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface CampaignDAO extends MongoCrudRepository<CampaignEntity, Long> {

    List<Long> getAllCampaignId();

    List<CampaignEntity> find(Query query);

    List<CampaignEntity> findAllDownloadCampaign();

    CampaignEntity findByObjectId(String oid);

    void deleteByMongoId(String id);

    void updateByMongoId(CampaignEntity newCampaign,CampaignEntity campaignEntity);

    PagerInfo findByPageInfo(Query q,int pageSize, int pageNo);

    CampaignEntity findCampaignByName(String name);

    void updateLocalstatu(long cid);

    String insertReturnId(CampaignEntity campaignEntity);

    void softDel(long id);
}