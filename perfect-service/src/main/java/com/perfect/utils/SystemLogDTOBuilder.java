package com.perfect.utils;

import com.perfect.core.AppContext;
import com.perfect.dto.log.UserOperationLogDTO;

/**
 * @author xiaowei
 * @title SystemLogDTOBuilder
 * @package com.perfect.utils
 * @description
 * @update 2015年12月08日. 下午6:58
 */
public class SystemLogDTOBuilder {

    private UserOperationLogDTO model = new UserOperationLogDTO();

    public static SystemLogDTOBuilder builder() {
        return new SystemLogDTOBuilder().setTime(System.currentTimeMillis()).setUserId(AppContext.getAccountId());
    }

    /**
     * 单元id
     * @param adgroupId
     * @return
     */
    public SystemLogDTOBuilder setAdgroupId(long adgroupId) {
        this.model.setAdgroupdId(adgroupId);
        return this;
    }

    public UserOperationLogDTO build() {
        return this.model;
    }

    /**
     * 日志记录时间
     * @param time
     * @return
     */
    public SystemLogDTOBuilder setTime(long time) {
        this.model.setTime(time);
        return this;
    }

    /**
     * 用户id
     * @param userId
     * @return
     */
    public SystemLogDTOBuilder setUserId(Long userId) {
        this.model.setUserId(userId);
        return this;
    }

    /**
     * 单元名称
     * @param adgroupName
     * @return
     */
    public SystemLogDTOBuilder setAdgroupName(String adgroupName) {
        this.model.setAdgroupName(adgroupName);
        return this;
    }

    /**
     * 计划id
     * @param campaignId
     * @return
     */
    public SystemLogDTOBuilder setCampaignId(long campaignId) {
        this.model.setCampgainId(campaignId);
        return this;
    }

    /**
     * 计划名称
     * @param campaignName
     * @return
     */
    public SystemLogDTOBuilder setCampaignName(String campaignName) {
        this.model.setCampaignName(campaignName);
        return this;
    }

    /**
     * 层级
     * @param type
     * @return
     */
    public SystemLogDTOBuilder setType(int type) {
        this.model.setType(type);
        return this;
    }

    /**
     * 大操作类型
     * @param oid
     * @return
     */
    public SystemLogDTOBuilder setOid(Long oid) {
        this.model.setOid(oid);
        return this;
    }

    /**
     * 操作对象文本
     * @param optContent
     * @return
     */
    public SystemLogDTOBuilder setOptContent(String optContent) {
//        this.model.setOptContent(optContent);
        return this;
    }

    /**
     * 操作层级数据id
     * @param optComprehensiveID
     * @return
     */
    public SystemLogDTOBuilder setOptComprehensiveID(Long optComprehensiveID) {
//        this.model.setOptComprehensiveID(optComprehensiveID);
        return this;
    }

    /**
     * 老值
     * @param oldValue
     * @return
     */
    public SystemLogDTOBuilder setBefore(String oldValue) {
        this.model.setBefore(oldValue);
        return this;
    }

    /**
     * 新值
     * @param newValue
     * @return
     */
    public SystemLogDTOBuilder setAfter(String newValue) {
        this.model.setAfter(newValue);
        return this;
    }

    /**
     * 小操作类型
     * @param optType
     * @return
     */
    public SystemLogDTOBuilder setOptType(int optType) {
//        this.model.setOptType(optType);
        return this;
    }


    /**
     * 操作字段属性
     * @param optObj
     * @return
     */
    public SystemLogDTOBuilder setOptObj(String optObj) {
//        this.model.setOptObj(optObj);
        return this;
    }
}
