package com.perfect.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by baizz on 2014-08-06.
 */
public class KCRealTimeDataEntity {

    @Id
    private String id;

    @Field(value = "kwId")
    private Long keywordId;

    @Field(value = "kwName")
    private String keywordName;

    @Field(value = "creId")
    private Long creativeId;

    @Field(value = "creName")
    private String creativeName;

    @Field(value = "agrName")
    private String adgroupName;

    @Field(value = "camName")
    private String campaignName;

    private Integer type;   //1.关键词报告, 2.创意报告

    @Field(value = "impr")
    private Integer impression;     //展现次数

    private Integer click;      //点击次数

    private Double ctr;     //点击率=点击次数/展现次数

    private Double cost;        //消费

    private Double cpc;     //平均点击价格=消费/点击次数

    @Field(value = "posi")
    private Double position;       //平均排名

    @Field(value = "conv")
    private Double conversion;      //转化

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCreativeName() {
        return creativeName;
    }

    public void setCreativeName(String creativeName) {
        this.creativeName = creativeName;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getImpression() {
        return impression;
    }

    public void setImpression(Integer impression) {
        this.impression = impression;
    }

    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

    public Double getCtr() {
        return ctr;
    }

    public void setCtr(Double ctr) {
        this.ctr = ctr;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getCpc() {
        return cpc;
    }

    public void setCpc(Double cpc) {
        this.cpc = cpc;
    }

    public Double getPosition() {
        return position;
    }

    public void setPosition(Double position) {
        this.position = position;
    }

    public Double getConversion() {
        return conversion;
    }

    public void setConversion(Double conversion) {
        this.conversion = conversion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KCRealTimeDataEntity that = (KCRealTimeDataEntity) o;

        if (adgroupName != null ? !adgroupName.equals(that.adgroupName) : that.adgroupName != null) return false;
        if (campaignName != null ? !campaignName.equals(that.campaignName) : that.campaignName != null) return false;
        if (click != null ? !click.equals(that.click) : that.click != null) return false;
        if (conversion != null ? !conversion.equals(that.conversion) : that.conversion != null) return false;
        if (cost != null ? !cost.equals(that.cost) : that.cost != null) return false;
        if (cpc != null ? !cpc.equals(that.cpc) : that.cpc != null) return false;
        if (creativeId != null ? !creativeId.equals(that.creativeId) : that.creativeId != null) return false;
        if (creativeName != null ? !creativeName.equals(that.creativeName) : that.creativeName != null) return false;
        if (ctr != null ? !ctr.equals(that.ctr) : that.ctr != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (impression != null ? !impression.equals(that.impression) : that.impression != null) return false;
        if (keywordId != null ? !keywordId.equals(that.keywordId) : that.keywordId != null) return false;
        if (keywordName != null ? !keywordName.equals(that.keywordName) : that.keywordName != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (keywordId != null ? keywordId.hashCode() : 0);
        result = 31 * result + (keywordName != null ? keywordName.hashCode() : 0);
        result = 31 * result + (creativeId != null ? creativeId.hashCode() : 0);
        result = 31 * result + (creativeName != null ? creativeName.hashCode() : 0);
        result = 31 * result + (adgroupName != null ? adgroupName.hashCode() : 0);
        result = 31 * result + (campaignName != null ? campaignName.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (impression != null ? impression.hashCode() : 0);
        result = 31 * result + (click != null ? click.hashCode() : 0);
        result = 31 * result + (ctr != null ? ctr.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (cpc != null ? cpc.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (conversion != null ? conversion.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "KCRealTimeDataEntity{" +
                "id='" + id + '\'' +
                ", keywordId=" + keywordId +
                ", keywordName='" + keywordName + '\'' +
                ", creativeId=" + creativeId +
                ", creativeName='" + creativeName + '\'' +
                ", adgroupName='" + adgroupName + '\'' +
                ", campaignName='" + campaignName + '\'' +
                ", type=" + type +
                ", impression=" + impression +
                ", click=" + click +
                ", ctr=" + ctr +
                ", cost=" + cost +
                ", cpc=" + cpc +
                ", position=" + position +
                ", conversion=" + conversion +
                '}';
    }
}
