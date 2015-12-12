package com.perfect.entity.log;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by yousheng on 2015/12/13.
 */
@Document(collection = "system_log")
public class SystemLogEntity {

    @Id
    private String id;

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

    public SystemLogEntity setOid(String oid) {
        this.oid = oid;
        return this;
    }

    public long getCampgainId() {
        return campgainId;
    }

    public SystemLogEntity setCampgainId(long campgainId) {
        this.campgainId = campgainId;
        return this;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public SystemLogEntity setCampaignName(String campaignName) {
        this.campaignName = campaignName;
        return this;
    }

    public long getAdgroupdId() {
        return adgroupdId;
    }

    public SystemLogEntity setAdgroupdId(long adgroupdId) {
        this.adgroupdId = adgroupdId;
        return this;
    }

    public String getAdgroupName() {
        return adgroupName;
    }

    public SystemLogEntity setAdgroupName(String adgroupName) {
        this.adgroupName = adgroupName;
        return this;
    }

    public String getName() {
        return name;
    }

    public SystemLogEntity setName(String name) {
        this.name = name;
        return this;
    }

    public int getType() {
        return type;
    }

    public SystemLogEntity setType(int type) {
        this.type = type;
        return this;
    }

    public String getProperty() {
        return property;
    }

    public SystemLogEntity setProperty(String property) {
        this.property = property;
        return this;
    }

    public Object getBefore() {
        return before;
    }

    public SystemLogEntity setBefore(Object before) {
        this.before = before;
        return this;
    }

    public Object getAfter() {
        return after;
    }

    public SystemLogEntity setAfter(Object after) {
        this.after = after;
        return this;
    }

    public long getTime() {
        return time;
    }

    public SystemLogEntity setTime(long time) {
        this.time = time;
        return this;
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public SystemLogEntity setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public SystemLogEntity setUserId(long userId) {
        this.userId = userId;
        return this;
    }
}
