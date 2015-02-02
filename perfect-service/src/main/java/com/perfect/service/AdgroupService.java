package com.perfect.service;

import com.perfect.autosdk.sms.v3.AdgroupType;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.utils.paging.PagerInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-11-26.
 */
public interface AdgroupService {

    List<AdgroupDTO> getAdgroupByCampaignId(Long campaignId, Map<String, Object> params, int skip, int limit);

    List<AdgroupDTO> getAdgroupByCampaignObjId(String campaignObjId);

    List<AdgroupDTO> getAdgroupByCampaignId(Long campaignObjId);

    Iterable<AdgroupDTO> findAll();

    List<AdgroupDTO> findHasLocalStatus();

    List<AdgroupDTO> findHasLocalStatusStr(List<CampaignDTO> campaignDTOStr);

    List<AdgroupDTO> findHasLocalStatusLong(List<CampaignDTO> campaignDTOLong);

    List<Long> getAdgroupIdByCampaignId(Long campaignId);

    List<Long> getAdgroupIdByCampaignObj(String campaignId);

    AdgroupDTO findOne(Long id);

    List<AdgroupDTO> find(Map<String, Object> params, int skip, int limit);

    void insertAll(List<AdgroupDTO> entities);

    void update(AdgroupDTO adgroupDTO);

    void delete(Long id);

    void deleteByIds(List<Long> ids);

    PagerInfo findByPagerInfo(Map<String, Object> params, Integer nowPage, Integer pageSize);

    Object insertOutId(AdgroupDTO adgroupEntity);

    void deleteByObjId(String oid);

    void deleteByObjId(Long adgroupId);

    AdgroupDTO findByObjId(String oid);

    void updateByObjId(AdgroupDTO adgroupEntity);

    void update(AdgroupDTO adgroupEntity, AdgroupDTO bakAdgroupEntity);

    void delBack(Long oid);

    AdgroupDTO fndEntity(Map<String, Object> params);

    void save(AdgroupDTO adgroupDTO);

    double findPriceRatio(Long cid);

    /**
     * 上传更新到百度 获取到
     * @param aids
     */
    List<AdgroupDTO> uploadAdd(List<String> aids);

    /**
     * 上传添加成功后 修改上传状态的修改方法，只适用于上传添加
     * @param  dto 上传成功后获取到的单元百度 获取到的dto对象，该对象有用的属性只有adgroupId，Pause，Stauts，Ls改为null,四个值，用于后台修改到本地
     * @param oid 本地id
     */
    void update(String oid,AdgroupDTO dto);

    /**
     * 上传本地的删除操作到百度
     * @param aid 要删除的单元id
     */
    String uploadDel(Long aid);

    /**
     * 上传本地的修改操作到百度
     * @param aid
     * @return
     */
    List<AdgroupDTO> uploadUpdate(List<Long> aid);

    /**
     * 上传单元时用户选择的级联上传计划
     * @param aid 要上传的单元id String
     * @return 返回上传成功后获取的aid Long，以及Status
     */
    List<AdgroupDTO> uploadAddByUp(String aid);

    /**
     * 上传修改操作成功后，修改本地的Pause和Stauts状态，这里可能有疑问，已经修改了为什么还不正确？因为凤巢有些限制可能我们本地限制不了，这里修改一次的意思就是
     * 为了保证本地的数据跟凤巢的更接近
     * @param aid  要修改的Long aid 百度id
     * @param dto 获取Pause，Status状态等值
     */
    void updateUpdate(Long aid,AdgroupDTO dto);

    /**
     * 上传删除操作成功后的删除单元，以及级联删除关键字和创意
     * @param aid 该单元的mongodbId，只能用该id来做级联删除，因为本地级联该单元的创意和关键字都是以mongodb来级联的
     */
    void deleteBubLinks(Long aid);
}