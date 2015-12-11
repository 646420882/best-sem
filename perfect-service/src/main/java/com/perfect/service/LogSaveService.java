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

    /**
     * <p>添加 关键词 操作日志保存</p>
     *
     * @param newWord
     * @return
     */
    OperationRecordModel saveKeywordLog(KeywordType newWord);

    /**
     * <p>修改 关键词 操作日志保存返回log对象</p>
     *
     * @param findKeyWord
     * @param newVal
     * @param oldVal
     * @param optObj
     * @param contentId
     * @return
     */
    OperationRecordModel updateKeywordLogAll(KeywordType findKeyWord, Object newVal, Object oldVal, String optObj, Integer contentId);

    /**
     * <p>删除 关键词 操作日志保存</p>
     *
     * @param newWord
     * @return
     */
    OperationRecordModel deleteKeywordLog(KeywordDTO newWord);

    /**
     * <p>修改 关键词总修改方法</p>
     *
     * @param newWord
     * @return
     */
    OperationRecordModel uploadLogWordUpdate(KeywordType newWord);


    /**
     * <p> 添加 计划 操作日志保存</p>
     *
     * @param campaignType
     * @return 操作日志数据
     */
    OperationRecordModel addCampaign(CampaignType campaignType);

    /**
     * <p> 删除 计划 操作日志</p>
     *
     * @param campaignType
     * @return 操作日志数据
     */
    OperationRecordModel removeCampaign(CampaignDTO campaignType);

    /**
     * <p> 更新 计划 操作日志</p>
     *
     * @param campaignType
     * @return 操作日志数据
     */
    OperationRecordModel updateCampaign(CampaignType campaignType, String newValue, String oldValue, String optObj, int contentId);


    /**
     * <p>修改计划总方法</p>
     * @param newCampaign
     * @return
     */
    OperationRecordModel updateCampaignAll(CampaignType newCampaign);

    /**
     * <p> 添加 单元 操作日志保存</p>
     *
     * @param adgroupType
     * @return 操作日志数据
     */
    OperationRecordModel addAdgroup(AdgroupType adgroupType);

    /**
     * <p> 删除 单元 操作日志</p>
     *
     * @param adgroupType
     * @return 操作日志数据
     */
    OperationRecordModel removeAdgroup(AdgroupDTO adgroupType);

    /**
     * <p> 更新 单元 操作日志</p>
     *
     * @param adgroupType
     * @return 操作日志数据
     */
    OperationRecordModel updateAdgroup(AdgroupType adgroupType, String newvalue, String oldvalue, String optObj, int contentid);

    /**
     * <p>修改单元总方法</p>
     *
     * @param newAdgroup
     * @return
     */
    OperationRecordModel updateAdgroupAll(AdgroupType newAdgroup);

    /**
     * <p> 添加 创意 操作日志保存</p>
     *
     * @param creativeType
     * @return 操作日志数据
     */
    OperationRecordModel addCreative(CreativeType creativeType);

    /**
     * <p> 删除 创意  操作日志</p>
     *
     * @param creativeType
     * @return 操作日志数据
     */
    OperationRecordModel removeCreative(CreativeDTO creativeType);

    /**
     * <p> 更新 创意 操作日志</p>
     *
     * @param creativeType
     * @return 操作日志数据
     */
    OperationRecordModel updateCreative(CreativeType creativeType, String newvalue, String oldvalue, String optObj, int contentid);

    /**
     * <p>修改创意逐个字段检测并返回log对象</p>
     *
     * @param creativeType
     * @return
     */
    OperationRecordModel updateCreativeLogAll(CreativeType creativeType);


    /**
     * <p>根据单元id获取计划和单元id,名称等信息</p>
     *
     * @param adgroupId
     * @param builder
     */
    void getCamAdgroupInfoByLong(Long adgroupId, OperationRecordModelBuilder builder);

    /**
     * <p>根据计划id获取计划id,名称</p>
     *
     * @param campaignId
     * @param builder
     */
    void getCampInfoByLongId(Long campaignId, OperationRecordModelBuilder builder);

    /**
     * <p>最终保存日志方法</p>
     *
     * @param orm
     * @return
     */
    Boolean saveLog(OperationRecordModel orm);


}
