package com.perfect.utils;

import com.perfect.core.AppContext;
import com.perfect.log.model.OperationRecordModel;

/**
 * @author xiaowei
 * @title OperationRecordModelBuilder
 * @package com.perfect.utils
 * @description
 * @update 2015年12月08日. 下午6:58
 */
public class OperationRecordModelBuilder {

    private OperationRecordModel model = new OperationRecordModel();
    private Long userId;
    private Long planId;
    private int optLevel;
    private String optContent;
    private String newValue;
    private int optType;

    public static OperationRecordModelBuilder builder() {
        return new OperationRecordModelBuilder().setOptTime(System.currentTimeMillis()).setUserId(AppContext.getAccountId());
    }

    /**
     * 单元id
     * @param unitId
     * @return
     */
    public OperationRecordModelBuilder setUnitId(long unitId) {
        this.model.setUnitId(unitId);
        return this;
    }

    public OperationRecordModel build() {
        return this.model;
    }

    /**
     * 日志记录时间
     * @param optTime
     * @return
     */
    public OperationRecordModelBuilder setOptTime(long optTime) {
        this.model.setOptTime(optTime);
        return this;
    }

    /**
     * 用户id
     * @param userId
     * @return
     */
    public OperationRecordModelBuilder setUserId(Long userId) {
        this.model.setUserId(userId);
        return this;
    }

    /**
     * 单元名称
     * @param unitName
     * @return
     */
    public OperationRecordModelBuilder setUnitName(String unitName) {
        this.model.setUnitName(unitName);
        return this;
    }

    /**
     * 计划id
     * @param planId
     * @return
     */
    public OperationRecordModelBuilder setPlanId(Long planId) {
        this.model.setPlanId(planId);
        return this;
    }

    /**
     * 计划名称
     * @param planName
     * @return
     */
    public OperationRecordModelBuilder setPlanName(String planName) {
        this.model.setPlanName(planName);
        return this;
    }

    /**
     * 层级
     * @param optLevel
     * @return
     */
    public OperationRecordModelBuilder setOptLevel(int optLevel) {
        this.model.setOptLevel(optLevel);
        return this;
    }

    /**
     * 大操作类型
     * @param optContentId
     * @return
     */
    public OperationRecordModelBuilder setOptContentId(int optContentId) {
        this.model.setOptContentId(optContentId);
        return this;
    }

    /**
     * 操作对象文本
     * @param optContent
     * @return
     */
    public OperationRecordModelBuilder setOptContent(String optContent) {
        this.model.setOptContent(optContent);
        return this;
    }

    /**
     * 操作层级数据id
     * @param optComprehensiveID
     * @return
     */
    public OperationRecordModelBuilder setOptComprehensiveID(Long optComprehensiveID) {
        this.model.setOptComprehensiveID(optComprehensiveID);
        return this;
    }

    /**
     * 老值
     * @param oldValue
     * @return
     */
    public OperationRecordModelBuilder setOldValue(String oldValue) {
        this.model.setOldValue(oldValue);
        return this;
    }

    /**
     * 新值
     * @param newValue
     * @return
     */
    public OperationRecordModelBuilder setNewValue(String newValue) {
        this.model.setNewValue(newValue);
        return this;
    }

    /**
     * 小操作类型
     * @param optType
     * @return
     */
    public OperationRecordModelBuilder setOptType(int optType) {
        this.model.setOptType(optType);
        return this;
    }


    /**
     * 操作字段属性
     * @param optObj
     * @return
     */
    public OperationRecordModelBuilder setOptObj(String optObj) {
        this.model.setOptObj(optObj);
        return this;
    }
}
