package com.perfect.utils;

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
        return new OperationRecordModelBuilder().setOptTime(System.currentTimeMillis());
    }

    public OperationRecordModelBuilder setUnitId(long unitId) {
        this.model.setUnitId(unitId);
        return this;
    }

    public OperationRecordModel build() {
        return this.model;
    }

    public OperationRecordModelBuilder setOptTime(long optTime) {
        this.model.setOptTime(optTime);
        return this;
    }

    public OperationRecordModelBuilder setUserId(Long userId) {
        this.model.setUserId(userId);
        return this;
    }

    public OperationRecordModelBuilder setUnitName(String unitName) {
        this.model.setUnitName(unitName);
        return this;
    }

    public OperationRecordModelBuilder setPlanId(Long planId) {
        this.model.setPlanId(planId);
        return this;
    }

    public OperationRecordModelBuilder setPlanName(String planName) {
        this.model.setPlanName(planName);
        return this;
    }

    public OperationRecordModelBuilder setOptLevel(int optLevel) {
        this.model.setOptLevel(optLevel);
        return this;
    }

    public OperationRecordModelBuilder setOptContentId(int optContentId) {
        this.model.setOptContentId(optContentId);
        return this;
    }

    public OperationRecordModelBuilder setOptContent(String optContent) {
        this.model.setOptContent(optContent);
        return this;
    }

    public OperationRecordModelBuilder setOptComprehensiveID(Long optComprehensiveID) {
        this.model.setOptComprehensiveID(optComprehensiveID);
        return this;
    }

    public OperationRecordModelBuilder setOldValue(String oldValue) {
        this.model.setOldValue(oldValue);
        return this;
    }

    public OperationRecordModelBuilder setNewValue(String newValue) {
        this.model.setNewValue(newValue);
        return this;
    }

    public OperationRecordModelBuilder setOptType(int optType) {
        this.model.setOptType(optType);
        return this;
    }


    public OperationRecordModelBuilder setOptObj(String optObj) {
        this.model.setOptObj(optObj);
        return this;
    }
}
