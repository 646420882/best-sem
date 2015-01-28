/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/
package com.perfect.dao.campaign;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.utils.paging.PagerInfo;

import java.util.List;

public interface CampaignDAO extends HeyCrudRepository<CampaignDTO, Long> {

    List<Long> getAllCampaignId();

//    List<CampaignDTO> find(Query query);

    List<CampaignDTO> findAllDownloadCampaign();

    List<CampaignDTO> findHasLocalStatus();

    CampaignDTO findByLongId(Long cid);

    CampaignDTO findByObjectId(String oid);

    void deleteByMongoId(String id);

    void updateByMongoId(CampaignDTO newCampaign, CampaignDTO campaignEntity);

    PagerInfo findByPageInfo(Long accountId, int pageSize, int pageNo);

    CampaignDTO findCampaignByName(String name);

    List<String> getCampaignStrIdByCampaignLongId(List<Long> campaignIds);

    void updateLocalstatu(long cid);

    String insertReturnId(CampaignDTO campaignEntity);

    void softDel(long id);

     void insertAll(List<CampaignDTO> dtos);

    void update(CampaignDTO campaignDTO);

    void update(CampaignDTO dto,String objId);

    void deleteByCampaignId(Long campaginId);

    void updateRemoveLs(List<String> afterUpdateStr);

    List<CampaignDTO> getOperateCamp();

}