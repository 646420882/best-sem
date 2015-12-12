package com.perfect.dto.log;

import com.perfect.dto.BaseDTO;

/**
 * Created by yousheng on 2015/12/13.
 */
public class SystemLogDTO extends BaseDTO {

    private String oid;

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

    public String getOid() {
        return oid;
    }

    public SystemLogDTO setOid(String oid) {
        this.oid = oid;
        return this;
    }

    public long getCampgainId() {
        return campgainId;
    }

    public SystemLogDTO setCampgainId(long campgainId) {
        this.campgainId = campgainId;
        return this;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public SystemLogDTO setCampaignName(String campaignName) {
        this.campaignName = campaignName;
        return this;
    }

    public long getAdgroupdId() {
        return adgroupdId;
    }

    public SystemLogDTO setAdgroupdId(long adgroupdId) {
        this.adgroupdId = adgroupdId;
        return this;
    }

    public String getAdgroupName() {
        return adgroupName;
    }

    public SystemLogDTO setAdgroupName(String adgroupName) {
        this.adgroupName = adgroupName;
        return this;
    }

    public String getName() {
        return name;
    }

    public SystemLogDTO setName(String name) {
        this.name = name;
        return this;
    }

    public int getType() {
        return type;
    }

    public SystemLogDTO setType(int type) {
        this.type = type;
        return this;
    }

    public String getProperty() {
        return property;
    }

    public SystemLogDTO setProperty(String property) {
        this.property = property;
        return this;
    }

    public Object getBefore() {
        return before;
    }

    public SystemLogDTO setBefore(Object before) {
        this.before = before;
        return this;
    }

    public Object getAfter() {
        return after;
    }

    public SystemLogDTO setAfter(Object after) {
        this.after = after;
        return this;
    }

    public long getTime() {
        return time;
    }

    public SystemLogDTO setTime(long time) {
        this.time = time;
        return this;
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public SystemLogDTO setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public SystemLogDTO setUserId(long userId) {
        this.userId = userId;
        return this;
    }
}
