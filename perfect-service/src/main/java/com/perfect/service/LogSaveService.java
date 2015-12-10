package com.perfect.service;

import com.perfect.autosdk.sms.v3.AdgroupType;
import com.perfect.autosdk.sms.v3.CampaignType;
import com.perfect.autosdk.sms.v3.CreativeType;
import com.perfect.autosdk.sms.v3.KeywordType;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.log.model.OperationRecordModel;
import com.perfect.utils.OperationRecordModelBuilder;


/**
 * @author xiaowei
 * @title LogSaveService
 * @package com.perfect.service
 * @description
 * @update 2015年12月08日. 下午6:44
 */
public interface LogSaveService {

    OperationRecordModel saveKeywordLog(KeywordType newWord);

    OperationRecordModel updateKeywordLog(KeywordType findKeyWord,Object newVal, Object oldVal, String optObj,Integer contentId);

    OperationRecordModel deleteKeywordLog(KeywordDTO newWord);

    OperationRecordModel uploadLogWordUpdate(KeywordType newWord);


    /**
     * <p> 添加 计划 操作日志保存</p>
     * @param campaignType
     * @return 操作日志数据
     */
    OperationRecordModel addCampaign(CampaignType campaignType);

    /**
     * <p> 删除 计划 操作日志</p>
     * @param campaignType
     * @return 操作日志数据
     */
    OperationRecordModel removeCampaign(CampaignDTO campaignType);

    /**
     * <p> 更新 计划 操作日志</p>
     * @param campaignType
     * @return 操作日志数据
     */
    OperationRecordModel updateCampaign(CampaignType campaignType, String newvalue, String oldvalue, String optObj, int contentid);

    /**
     * <p> 添加 单元 操作日志保存</p>
     * @param adgroupType
     * @return 操作日志数据
     */
    OperationRecordModel addAdgroup(AdgroupType adgroupType);

    /**
     * <p> 删除 单元 操作日志</p>
     * @param adgroupType
     * @return 操作日志数据
     */
    OperationRecordModel removeAdgroup(AdgroupDTO adgroupType);

    /**
     * <p> 更新 单元 操作日志</p>
     * @param adgroupType
     * @return 操作日志数据
     */
    OperationRecordModel updateAdgroup(AdgroupType adgroupType, String newvalue, String oldvalue, String optObj, int contentid);

    /**
     * <p> 添加 创意 操作日志保存</p>
     * @param creativeType
     * @return 操作日志数据
     */
    OperationRecordModel addCreative(CreativeType creativeType);

    /**
     * <p> 删除 创意  操作日志</p>
     * @param creativeType
     * @return 操作日志数据
     */
    OperationRecordModel removeCreative(CreativeDTO creativeType);

    /**
     * <p> 更新 创意 操作日志</p>
     * @param creativeType
     * @return 操作日志数据
     */
    OperationRecordModel updateCreative(CreativeType creativeType, String newvalue, String oldvalue, String optObj, int contentid);


    void getCamAdgroupInfoByLong(Long adgroupId, OperationRecordModelBuilder builder);

    void getCampInfoByLongId(Long campaignId,OperationRecordModelBuilder builder);

    Boolean saveLog(OperationRecordModel orm);


}
