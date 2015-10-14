/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/
package com.perfect.dao.campaign;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.utils.paging.PagerInfo;

import java.util.List;

public interface CampaignDAO extends HeyCrudRepository<CampaignDTO, Long> {

    List<Long> getAllCampaignId();

    List<CampaignDTO> findAllDownloadCampaign();

    List<CampaignDTO> findDownloadCampaignsByBaiduAccountId(Long baiduAccountId);

    List<CampaignDTO> findHasLocalStatus();

    /**
     * <p>获取指定百度账号下在本地新增 修改 删除的推广计划
     *
     * @param baiduAccountId
     * @return
     */
    List<CampaignDTO> findLocalChangedCampaigns(Long baiduAccountId);

    public List<CampaignDTO> findHasLocalStatusByStrings(List<String> cids);

    public List<CampaignDTO> findHasLocalStatusByLongs(List<Long> cids);

    CampaignDTO findByLongId(Long cid);

    CampaignDTO findByObjectId(String oid);

    /**
     * <p>对给定百度账号下的所有推广计划暂停投放
     *
     * @param baiduAccountId
     */
    void pause(Long baiduAccountId);

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

    void update(CampaignDTO dto, String objId);

    void deleteByCampaignId(Long campaginId);

    void updateRemoveLs(List<String> afterUpdateStr);

    List<CampaignDTO> getOperateCamp();

}