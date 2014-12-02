package com.perfect.dto.monitor;

import com.perfect.dto.BaseDTO;
import com.perfect.dto.baidu.OptTypeDTO;

public class MonitorDTO extends BaseDTO {
    private Long monitorId;
    private Long folderId;
    private Long adgroupId;
    private Long campaignId;
    private Integer type;
    private OptTypeDTO opt;

    public Long getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(Long monitorId) {
        this.monitorId = monitorId;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public Long getAdgroupId() {
        return adgroupId;
    }

    public void setAdgroupId(Long adgroupId) {
        this.adgroupId = adgroupId;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public OptTypeDTO getOpt() {
        return opt;
    }

    public void setOpt(OptTypeDTO opt) {
        this.opt = opt;
    }


    public String toString() {
        String outputString = "";
        return super.toString() + "[" +
                "monitorId" + ":" + getMonitorId() + "," +
                "folderId" + ":" + getFolderId() + "," +
                "id" + ":" + getId() + "," +
                "adgroupId" + ":" + getAdgroupId() + "," +
                "campaignId" + ":" + getCampaignId() + "," +
                "type" + ":" + getType() + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "opt" + "=" + (getOpt() != null ? !getOpt().equals(this) ? getOpt().toString().replaceAll("  ", "    ") : "this" : "null")
                + outputString;
    }
}