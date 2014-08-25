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

    @Field("kwid")
    private Long keywordId;  //关键词ID

    @Field(value = "kwna")
    private String keywordName; //关键字

    @Field(value = "crid")
    private Long creativeId;    //创意ID

    @Field(value = "crtl")
    private String creativeTitle; //创意标题

    @Field(value = "des1")
    private String description1;//创意内容1

    @Field(value = "des2")
    private String description2;//创意内容2

    @Field(value = "rgid")
    private Long regionId; //地域ID

    @Field(value = "rgna")
    private String regionName; //地域名称

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

    public Long getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(Long keywordId) {
        this.keywordId = keywordId;
    }

    public String getKeywordName() {
        return keywordName;
    }

    public void setKeywordName(String keywordName) {
        this.keywordName = keywordName;
    }

    public Long getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(Long creativeId) {
        this.creativeId = creativeId;
    }

    public String getCreativeTitle() {
        return creativeTitle;
    }

    public void setCreativeTitle(String creativeTitle) {
        this.creativeTitle = creativeTitle;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
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

        StructureReportEntity entity = (StructureReportEntity) o;

        if (adgroupId != null ? !adgroupId.equals(entity.adgroupId) : entity.adgroupId != null) return false;
        if (adgroupName != null ? !adgroupName.equals(entity.adgroupName) : entity.adgroupName != null) return false;
        if (campaignName != null ? !campaignName.equals(entity.campaignName) : entity.campaignName != null)
            return false;
        if (creativeId != null ? !creativeId.equals(entity.creativeId) : entity.creativeId != null) return false;
        if (creativeTitle != null ? !creativeTitle.equals(entity.creativeTitle) : entity.creativeTitle != null)
            return false;
        if (date != null ? !date.equals(entity.date) : entity.date != null) return false;
        if (description1 != null ? !description1.equals(entity.description1) : entity.description1 != null)
            return false;
        if (description2 != null ? !description2.equals(entity.description2) : entity.description2 != null)
            return false;
        if (id != null ? !id.equals(entity.id) : entity.id != null) return false;
        if (keywordId != null ? !keywordId.equals(entity.keywordId) : entity.keywordId != null) return false;
        if (keywordName != null ? !keywordName.equals(entity.keywordName) : entity.keywordName != null) return false;
        if (mobileClick != null ? !mobileClick.equals(entity.mobileClick) : entity.mobileClick != null) return false;
        if (mobileConversion != null ? !mobileConversion.equals(entity.mobileConversion) : entity.mobileConversion != null)
            return false;
        if (mobileCost != null ? !mobileCost.equals(entity.mobileCost) : entity.mobileCost != null) return false;
        if (mobileCpc != null ? !mobileCpc.equals(entity.mobileCpc) : entity.mobileCpc != null) return false;
        if (mobileCpm != null ? !mobileCpm.equals(entity.mobileCpm) : entity.mobileCpm != null) return false;
        if (mobileCtr != null ? !mobileCtr.equals(entity.mobileCtr) : entity.mobileCtr != null) return false;
        if (mobileImpression != null ? !mobileImpression.equals(entity.mobileImpression) : entity.mobileImpression != null)
            return false;
        if (pcClick != null ? !pcClick.equals(entity.pcClick) : entity.pcClick != null) return false;
        if (pcConversion != null ? !pcConversion.equals(entity.pcConversion) : entity.pcConversion != null)
            return false;
        if (pcCost != null ? !pcCost.equals(entity.pcCost) : entity.pcCost != null) return false;
        if (pcCpc != null ? !pcCpc.equals(entity.pcCpc) : entity.pcCpc != null) return false;
        if (pcCpm != null ? !pcCpm.equals(entity.pcCpm) : entity.pcCpm != null) return false;
        if (pcCtr != null ? !pcCtr.equals(entity.pcCtr) : entity.pcCtr != null) return false;
        if (pcImpression != null ? !pcImpression.equals(entity.pcImpression) : entity.pcImpression != null)
            return false;
        if (regionId != null ? !regionId.equals(entity.regionId) : entity.regionId != null) return false;
        if (regionName != null ? !regionName.equals(entity.regionName) : entity.regionName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (adgroupId != null ? adgroupId.hashCode() : 0);
        result = 31 * result + (adgroupName != null ? adgroupName.hashCode() : 0);
        result = 31 * result + (campaignName != null ? campaignName.hashCode() : 0);
        result = 31 * result + (keywordId != null ? keywordId.hashCode() : 0);
        result = 31 * result + (keywordName != null ? keywordName.hashCode() : 0);
        result = 31 * result + (creativeId != null ? creativeId.hashCode() : 0);
        result = 31 * result + (creativeTitle != null ? creativeTitle.hashCode() : 0);
        result = 31 * result + (description1 != null ? description1.hashCode() : 0);
        result = 31 * result + (description2 != null ? description2.hashCode() : 0);
        result = 31 * result + (regionId != null ? regionId.hashCode() : 0);
        result = 31 * result + (regionName != null ? regionName.hashCode() : 0);
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
                ", keywordId=" + keywordId +
                ", keywordName='" + keywordName + '\'' +
                ", creativeId=" + creativeId +
                ", creativeTitle='" + creativeTitle + '\'' +
                ", description1='" + description1 + '\'' +
                ", description2='" + description2 + '\'' +
                ", regionId=" + regionId +
                ", regionName='" + regionName + '\'' +
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
