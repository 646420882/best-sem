package com.perfect.service;

import com.perfect.autosdk.sms.v3.AdgroupType;
import com.perfect.autosdk.sms.v3.CampaignType;
import com.perfect.autosdk.sms.v3.CreativeType;
import com.perfect.autosdk.sms.v3.KeywordType;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.dto.log.UserOperationLogDTO;
import com.perfect.utils.SystemLogDTOBuilder;

import java.util.List;


/**
 * @author xiaowei
 * @title UserOperationLogService
 * @package com.perfect.service
 * @description
 * @update 2015年12月08日. 下午6:44
 */
public interface UserOperationLogService {

    UserOperationLogDTO saveKeywordLog(KeywordType newWord);

     UserOperationLogDTO updateKeywordLog(KeywordType newWord, Object newVal, Object oldVal, String property);

    UserOperationLogDTO deleteKeywordLog(KeywordDTO newWord);

    UserOperationLogDTO uploadLogWordUpdate(KeywordType newWord);


    /**
     * <p> 添加 计划 操作日志保存</p>
     *
     * @param campaignType
     * @return 操作日志数据
     */
    UserOperationLogDTO addCampaign(CampaignType campaignType);

    /**
     * <p> 删除 计划 操作日志</p>
     *
     * @param campaignType
     * @return 操作日志数据
     */
    UserOperationLogDTO removeCampaign(CampaignDTO campaignType);

    /**
     * <p> 更新 计划 操作日志</p>
     *
     * @param campaignType
     * @return 操作日志数据
     */
    UserOperationLogDTO updateCampaign(CampaignType campaignType, String newvalue, String oldvalue, String property);

    public UserOperationLogDTO updateCampaignAll(CampaignType newCampaign);
    /**
     * <p> 添加 单元 操作日志保存</p>
     *
     * @param adgroupType
     * @return 操作日志数据
     */
    UserOperationLogDTO addAdgroup(AdgroupType adgroupType);

    /**
     * <p> 删除 单元 操作日志</p>
     *
     * @param adgroupType
     * @return 操作日志数据
     */
    UserOperationLogDTO removeAdgroup(AdgroupDTO adgroupType);

    /**
     * <p> 更新 单元 操作日志</p>
     *
     * @param adgroupType
     * @return 操作日志数据
     */
    public UserOperationLogDTO updateAdgroup(AdgroupType adgroupType, String newvalue, String oldvalue, String property);


    public UserOperationLogDTO updateAdgroupAll(AdgroupType newAdgroup);
    /**
     * <p> 添加 创意 操作日志保存</p>
     *
     * @param creativeType
     * @return 操作日志数据
     */
    UserOperationLogDTO addCreative(CreativeType creativeType);

    /**
     * <p> 删除 创意  操作日志</p>
     *
     * @param creativeType
     * @return 操作日志数据
     */
    UserOperationLogDTO removeCreative(CreativeDTO creativeType);

    /**
     * <p> 更新 创意 操作日志</p>
     *
     * @param creativeType
     * @return 操作日志数据
     */
    UserOperationLogDTO updateCreative(CreativeType creativeType, String newvalue, String oldvalue, String property);

    UserOperationLogDTO updateCreativeAll(CreativeType newCreative);

    void getCamAdgroupInfoByLong(Long adgroupId, SystemLogDTOBuilder builder);

    void getCampInfoByLongId(Long campaignId, SystemLogDTOBuilder builder);

    void save(List<UserOperationLogDTO> userOperationLogDTOs);

    void saveLog(UserOperationLogDTO userOperationLogDTO);


}
