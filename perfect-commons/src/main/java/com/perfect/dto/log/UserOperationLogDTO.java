package com.perfect.dto.log;

import com.perfect.dto.BaseDTO;

/**
 * Created by yousheng on 2015/12/13.
 */
public class UserOperationLogDTO extends BaseDTO {

    private Long oid;

    private long userId;

    private long campgainId;

    private String campaignName;

    private long adgroupdId;

    private String adgroupName;

    private String name;

    private int type;

    private String property;

    private Object before;

    private Object after;

    private long time;

    private boolean uploaded;

    public Long getOid() {
        return oid;
    }

    public UserOperationLogDTO setOid(Long oid) {
        this.oid = oid;
        return this;
    }

    public long getCampgainId() {
        return campgainId;
    }

    public UserOperationLogDTO setCampgainId(long campgainId) {
        this.campgainId = campgainId;
        return this;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public UserOperationLogDTO setCampaignName(String campaignName) {
        this.campaignName = campaignName;
        return this;
    }

    public long getAdgroupdId() {
        return adgroupdId;
    }

    public UserOperationLogDTO setAdgroupdId(long adgroupdId) {
        this.adgroupdId = adgroupdId;
        return this;
    }

    public String getAdgroupName() {
        return adgroupName;
    }

    public UserOperationLogDTO setAdgroupName(String adgroupName) {
        this.adgroupName = adgroupName;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserOperationLogDTO setName(String name) {
        this.name = name;
        return this;
    }

    public int getType() {
        return type;
    }

    public UserOperationLogDTO setType(int type) {
        this.type = type;
        return this;
    }

    public String getProperty() {
        return property;
    }

    public UserOperationLogDTO setProperty(String property) {
        this.property = property;
        return this;
    }

    public Object getBefore() {
        return before;
    }

    public UserOperationLogDTO setBefore(Object before) {
        this.before = before;
        return this;
    }

    public Object getAfter() {
        return after;
    }

    public UserOperationLogDTO setAfter(Object after) {
        this.after = after;
        return this;
    }

    public long getTime() {
        return time;
    }

    public UserOperationLogDTO setTime(long time) {
        this.time = time;
        return this;
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public UserOperationLogDTO setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public UserOperationLogDTO setUserId(long userId) {
        this.userId = userId;
        return this;
    }
}
