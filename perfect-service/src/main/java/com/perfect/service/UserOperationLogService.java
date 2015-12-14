package com.perfect.service;

import com.perfect.autosdk.sms.v3.AdgroupType;
import com.perfect.autosdk.sms.v3.CampaignType;
import com.perfect.autosdk.sms.v3.CreativeType;
import com.perfect.autosdk.sms.v3.KeywordType;
import com.perfect.commons.constants.UserOperationLogLevelEnum;
import com.perfect.commons.constants.UserOperationTypeEnum;
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

    /**
     * <p>添加关键词日志操作</p>
     *
     * @param newWord 待添加关键词对象
     * @return
     */
    UserOperationLogDTO saveKeywordLog(KeywordType newWord);

    /**
     * <p>修改关键词日志操作</p>
     *
     * @param newWord 修改后关键词对象
     * @param newVal 新值
     * @param oldVal 旧值
     * @param property 字段
     * @return
     */
     UserOperationLogDTO updateKeyword(KeywordType newWord, Object newVal, Object oldVal, String property);

    /**
     * <p>删除关键词日志操作</p>
     *
     * @param newWord 要删除的关键词对象
     * @return
     */
    UserOperationLogDTO deleteKeywordLog(KeywordDTO newWord);

    /**
     * <p>修改关键词总方法</p>
     *
     * @param newWord 修改后关键词对象
     * @return
     */
    List<UserOperationLogDTO> updateKeywordAll(KeywordType newWord);


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

    /**
     * <p>修改计划总方法</p>
     *
     * @param newCampaign
     * @return
     */
     List<UserOperationLogDTO> updateCampaignAll(CampaignType newCampaign);

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
     UserOperationLogDTO updateAdgroup(AdgroupType adgroupType, String newvalue, String oldvalue, String property);

    /**
     * <p>修改单元总方法</p>
     * @param newAdgroup 修改后的关键词
     * @return
     */
    List<UserOperationLogDTO> updateAdgroupAll(AdgroupType newAdgroup);
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

    /**
     * <p>修改创意总方法</p>
     *
     * @param newCreative 修改后的创意对象
     * @return
     */
    List<UserOperationLogDTO> updateCreativeAll(CreativeType newCreative);

    /**
     * <p>自动查询本地计划，单元id跟名称并set到日志对象中</p>
     * @param adgroupId 单元百度Id
     * @param builder 日志对象
     */
    void getCamAdgroupInfoByLong(Long adgroupId, SystemLogDTOBuilder builder);

    /**
     * <p>自动查询本地计划id跟名称并set到日志对象中</p>
     *
     * @param campaignId 计划百度id
     * @param builder 日志对象
     */
    void getCampInfoByLongId(Long campaignId, SystemLogDTOBuilder builder);

    /**
     * <p>保存日志方法(批量)</p>
     * @param userOperationLogDTOs
     */
    void save(List<UserOperationLogDTO> userOperationLogDTOs);

    /**
     * <p>保存日志方法(单条)</p>
     *
     * @param userOperationLogDTO
     */
    void saveLog(UserOperationLogDTO userOperationLogDTO);


    void update(Object object, UserOperationLogLevelEnum level, String property, UserOperationTypeEnum
            userOperationTypeEnum, Object
                        oldVal,
                Object
                        newVal);

    void newdel(Object object, UserOperationLogLevelEnum level, UserOperationTypeEnum
            userOperationTypeEnum);
}
