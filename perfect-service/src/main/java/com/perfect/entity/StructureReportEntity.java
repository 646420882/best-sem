package com.perfect.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by SubDong on 2014/8/12.
 */
public class StructureReportEntity {
    @Id
    private String id;

    private String date; //时间

    @Field(value = "agid")
    private Long adgroupId;

    @Field(value = "agna")
    private String adgroupName; //单元

    @Field(value = "cpna")
    private String campaignName; //计划

    @Field(value = "pcis")
    private Integer pcImpression;     //PC展现次数

    @Field(value = "pccli")
    private Integer pcClick;      //PC点击次数

    @Field(value = "pcctr")
    private Double pcCtr;     //PC点击率=点击次数/展现次数

    @Field(value = "pccost")
    private Double pcCost;        //PC消费

    @Field(value = "pccpc")
    private Double pcCpc;     //PC平均点击价格=消费/点击次数

    @Field(value = "pccpm")
    private Double pcCpm;       //PC千次展现消费

    @Field(value = "pccs")
    private Double pcConversion;      //PC转化

    @Field(value = "mis")
    private Integer mobileImpression;

    @Field(value = "mcli")
    private Integer mobileClick;

    @Field(value = "mctr")
    private Double mobileCtr;

    @Field(value = "mcost")
    private Double mobileCost;

    @Field(value = "mcpc")
    private Double mobileCpc;

    @Field(value = "mcpm")
    private Double mobileCpm;

    @Field(value = "mcs")
    private Double mobileConversion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getAdgroupId() {
        return adgroupId;
    }

    public void setAdgroupId(Long adgroupId) {
        this.adgroupId = adgroupId;
    }

    public String getAdgroupName() {
        return adgroupName;
    }

    public void setAdgroupName(String adgroupName) {
        this.adgroupName = adgroupName;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public Integer getPcImpression() {
        return pcImpression;
    }

    public void setPcImpression(Integer pcImpression) {
        this.pcImpression = pcImpression;
    }

    public Integer getPcClick() {
        return pcClick;
    }

    public void setPcClick(Integer pcClick) {
        this.pcClick = pcClick;
    }

    public Double getPcCtr() {
        return pcCtr;
    }

    public void setPcCtr(Double pcCtr) {
        this.pcCtr = pcCtr;
    }

    public Double getPcCost() {
        return pcCost;
    }

    public void setPcCost(Double pcCost) {
        this.pcCost = pcCost;
    }

    public Double getPcCpc() {
        return pcCpc;
    }

    public void setPcCpc(Double pcCpc) {
        this.pcCpc = pcCpc;
    }

    public Double getPcCpm() {
        return pcCpm;
    }

    public void setPcCpm(Double pcCpm) {
        this.pcCpm = pcCpm;
    }

    public Double getPcConversion() {
        return pcConversion;
    }

    public void setPcConversion(Double pcConversion) {
        this.pcConversion = pcConversion;
    }

    public Integer getMobileImpression() {
        return mobileImpression;
    }

    public void setMobileImpression(Integer mobileImpression) {
        this.mobileImpression = mobileImpression;
    }

    public Integer getMobileClick() {
        return mobileClick;
    }

    public void setMobileClick(Integer mobileClick) {
        this.mobileClick = mobileClick;
    }

    public Double getMobileCtr() {
        return mobileCtr;
    }

    public void setMobileCtr(Double mobileCtr) {
        this.mobileCtr = mobileCtr;
    }

    public Double getMobileCost() {
        return mobileCost;
    }

    public void setMobileCost(Double mobileCost) {
        this.mobileCost = mobileCost;
    }

    public Double getMobileCpc() {
        return mobileCpc;
    }

    public void setMobileCpc(Double mobileCpc) {
        this.mobileCpc = mobileCpc;
    }

    public Double getMobileCpm() {
        return mobileCpm;
    }

    public void setMobileCpm(Double mobileCpm) {
        this.mobileCpm = mobileCpm;
    }

    public Double getMobileConversion() {
        return mobileConversion;
    }

    public void setMobileConversion(Double mobileConversion) {
        this.mobileConversion = mobileConversion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StructureReportEntity that = (StructureReportEntity) o;

        if (adgroupId != null ? !adgroupId.equals(that.adgroupId) : that.adgroupId != null) return false;
        if (adgroupName != null ? !adgroupName.equals(that.adgroupName) : that.adgroupName != null) return false;
        if (campaignName != null ? !campaignName.equals(that.campaignName) : that.campaignName != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (mobileClick != null ? !mobileClick.equals(that.mobileClick) : that.mobileClick != null) return false;
        if (mobileConversion != null ? !mobileConversion.equals(that.mobileConversion) : that.mobileConversion != null)
            return false;
        if (mobileCost != null ? !mobileCost.equals(that.mobileCost) : that.mobileCost != null) return false;
        if (mobileCpc != null ? !mobileCpc.equals(that.mobileCpc) : that.mobileCpc != null) return false;
        if (mobileCpm != null ? !mobileCpm.equals(that.mobileCpm) : that.mobileCpm != null) return false;
        if (mobileCtr != null ? !mobileCtr.equals(that.mobileCtr) : that.mobileCtr != null) return false;
        if (mobileImpression != null ? !mobileImpression.equals(that.mobileImpression) : that.mobileImpression != null)
            return false;
        if (pcClick != null ? !pcClick.equals(that.pcClick) : that.pcClick != null) return false;
        if (pcConversion != null ? !pcConversion.equals(that.pcConversion) : that.pcConversion != null) return false;
        if (pcCost != null ? !pcCost.equals(that.pcCost) : that.pcCost != null) return false;
        if (pcCpc != null ? !pcCpc.equals(that.pcCpc) : that.pcCpc != null) return false;
        if (pcCpm != null ? !pcCpm.equals(that.pcCpm) : that.pcCpm != null) return false;
        if (pcCtr != null ? !pcCtr.equals(that.pcCtr) : that.pcCtr != null) return false;
        if (pcImpression != null ? !pcImpression.equals(that.pcImpression) : that.pcImpression != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (adgroupId != null ? adgroupId.hashCode() : 0);
        result = 31 * result + (adgroupName != null ? adgroupName.hashCode() : 0);
        result = 31 * result + (campaignName != null ? campaignName.hashCode() : 0);
        result = 31 * result + (pcImpression != null ? pcImpression.hashCode() : 0);
        result = 31 * result + (pcClick != null ? pcClick.hashCode() : 0);
        result = 31 * result + (pcCtr != null ? pcCtr.hashCode() : 0);
        result = 31 * result + (pcCost != null ? pcCost.hashCode() : 0);
        result = 31 * result + (pcCpc != null ? pcCpc.hashCode() : 0);
        result = 31 * result + (pcCpm != null ? pcCpm.hashCode() : 0);
        result = 31 * result + (pcConversion != null ? pcConversion.hashCode() : 0);
        result = 31 * result + (mobileImpression != null ? mobileImpression.hashCode() : 0);
        result = 31 * result + (mobileClick != null ? mobileClick.hashCode() : 0);
        result = 31 * result + (mobileCtr != null ? mobileCtr.hashCode() : 0);
        result = 31 * result + (mobileCost != null ? mobileCost.hashCode() : 0);
        result = 31 * result + (mobileCpc != null ? mobileCpc.hashCode() : 0);
        result = 31 * result + (mobileCpm != null ? mobileCpm.hashCode() : 0);
        result = 31 * result + (mobileConversion != null ? mobileConversion.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StructureReportEntity{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", adgroupId=" + adgroupId +
                ", adgroupName='" + adgroupName + '\'' +
                ", campaignName='" + campaignName + '\'' +
                ", pcImpression=" + pcImpression +
                ", pcClick=" + pcClick +
                ", pcCtr=" + pcCtr +
                ", pcCost=" + pcCost +
                ", pcCpc=" + pcCpc +
                ", pcCpm=" + pcCpm +
                ", pcConversion=" + pcConversion +
                ", mobileImpression=" + mobileImpression +
                ", mobileClick=" + mobileClick +
                ", mobileCtr=" + mobileCtr +
                ", mobileCost=" + mobileCost +
                ", mobileCpc=" + mobileCpc +
                ", mobileCpm=" + mobileCpm +
                ", mobileConversion=" + mobileConversion +
                '}';
    }
}
