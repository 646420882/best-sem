package com.perfect.service;

import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.utils.paging.PagerInfo;

import java.util.List;

/**
 * Created by SubDong on 2014/11/26.
 */
public interface CampaignService {

    public CampaignDTO findOne(Long campaignId);

    public Iterable<CampaignDTO> findAll();

    public Iterable<CampaignDTO> findAllDownloadCampaign();

    public void insertAll(List<CampaignDTO> list);

    public void update(CampaignDTO campaignEntity);

    public void delete(Long campaignId);

    public void deleteByIds(List<Long> campaignIds);

    PagerInfo findByPageInfo(Long accountId, int pageSize, int pageNo);

    CampaignDTO findByObjectId(String oid);

    void softDel(long id);

    void deleteByMongoId(String id);

    void save(CampaignDTO dto);

    void updateByMongoId(CampaignDTO newCampaign, CampaignDTO campaignEntity);

    String insertReturnId(CampaignDTO campaignEntity);
}
