package com.perfect.service;

import com.perfect.autosdk.sms.v3.AdgroupType;
import com.perfect.autosdk.sms.v3.CampaignType;
import com.perfect.autosdk.sms.v3.CreativeType;
import com.perfect.autosdk.sms.v3.KeywordType;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.dto.log.SystemLogDTO;
import com.perfect.utils.SystemLogDTOBuilder;

import java.util.List;


/**
 * @author xiaowei
 * @title LogSaveService
 * @package com.perfect.service
 * @description
 * @update 2015年12月08日. 下午6:44
 */
public interface LogSaveService {

    SystemLogDTO saveKeywordLog(KeywordType newWord);

    SystemLogDTO updateKeywordLog(KeywordType findKeyWord, Object newVal, Object oldVal, String optObj, Integer contentId);

    SystemLogDTO deleteKeywordLog(KeywordDTO newWord);

    SystemLogDTO uploadLogWordUpdate(KeywordType newWord);


    /**
     * <p> 添加 计划 操作日志保存</p>
     *
     * @param campaignType
     * @return 操作日志数据
     */
    SystemLogDTO addCampaign(CampaignType campaignType);

    /**
     * <p> 删除 计划 操作日志</p>
     *
     * @param campaignType
     * @return 操作日志数据
     */
    SystemLogDTO removeCampaign(CampaignDTO campaignType);

    /**
     * <p> 更新 计划 操作日志</p>
     *
     * @param campaignType
     * @return 操作日志数据
     */
    SystemLogDTO updateCampaign(CampaignType campaignType, String newvalue, String oldvalue, String optObj, int contentid);

    /**
     * <p> 添加 单元 操作日志保存</p>
     *
     * @param adgroupType
     * @return 操作日志数据
     */
    SystemLogDTO addAdgroup(AdgroupType adgroupType);

    /**
     * <p> 删除 单元 操作日志</p>
     *
     * @param adgroupType
     * @return 操作日志数据
     */
    SystemLogDTO removeAdgroup(AdgroupDTO adgroupType);

    /**
     * <p> 更新 单元 操作日志</p>
     *
     * @param adgroupType
     * @return 操作日志数据
     */
    SystemLogDTO updateAdgroup(AdgroupType adgroupType, String newvalue, String oldvalue, String optObj, int contentid);

    /**
     * <p> 添加 创意 操作日志保存</p>
     *
     * @param creativeType
     * @return 操作日志数据
     */
    SystemLogDTO addCreative(CreativeType creativeType);

    /**
     * <p> 删除 创意  操作日志</p>
     *
     * @param creativeType
     * @return 操作日志数据
     */
    SystemLogDTO removeCreative(CreativeDTO creativeType);

    /**
     * <p> 更新 创意 操作日志</p>
     *
     * @param creativeType
     * @return 操作日志数据
     */
    SystemLogDTO updateCreative(CreativeType creativeType, String newvalue, String oldvalue, String optObj, int contentid);


    void getCamAdgroupInfoByLong(Long adgroupId, SystemLogDTOBuilder builder);

    void getCampInfoByLongId(Long campaignId, SystemLogDTOBuilder builder);

    void save(List<SystemLogDTO> systemLogDTOs);

}
