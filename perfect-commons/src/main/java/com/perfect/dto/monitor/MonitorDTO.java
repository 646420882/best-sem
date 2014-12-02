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

    public void setMonitorId(Long aMonitorId) {

        monitorId = aMonitorId;


    }

    public void setFolderId(Long aFolderId) {

        folderId = aFolderId;


    }

    public void setId(Long aId) {

        id = aId;


    }

    public void setAdgroupId(Long aAdgroupId) {

        adgroupId = aAdgroupId;


    }

    public void setCampaignId(Long aCampaignId) {

        campaignId = aCampaignId;


    }

    public void setType(Integer aType) {

        type = aType;


    }

    public void setOpt(OptTypeDTO aOpt) {

        opt = aOpt;


    }

    public Long getMonitorId() {
        return monitorId;
    }

    public Long getFolderId() {
        return folderId;
    }

    public Long getAdgroupId() {
        return adgroupId;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public Integer getType() {
        return type;
    }

    public OptTypeDTO getOpt() {
        return opt;
    }

    public void delete() {
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