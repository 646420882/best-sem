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

    List<String> getCampaignStrIdByCampaignLongId(List<Long> campaignIds);

    String insertReturnId(CampaignDTO campaignEntity);

    /**
     * 上传到百度更新方法，慎用！
     * @param dto
     * @return
     */
    Long uploadAdd(CampaignDTO dto);

    /**
     * 更新本地删除操作到百度
     * @param campaignIds 要删除的计划id
     * @return
     */
    int uploadDel(List<Long> campaignIds);

    /**
     * 更新本地修改操作到百度
     * @param campaignIds 要更新的计划id
     * @return
     */
    public List<Long> uploadUpdate(List<Long> campaignIds);

    /**
     * 上传成功后本地修改方法，此方法只能修改计划的百度Id，其他修改不能修改，用者需看清后台代码
     * @param campaignId 从百度得到计划id
     * @param objId 本地id，用于查询
     */
    void update(Long campaignId,String objId);

    void updateRemoveLs(List<String> afterUpdateStr);
}
