package com.perfect.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by baizz on 14-7-23.
 */
public class KeywordRealTimeDataVOEntity implements Comparable<KeywordRealTimeDataVOEntity>{

    @Id
    private Long keywordId;

    @Field(value = "name")
    private String keywordName;

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

    private String orderBy;

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

        KeywordRealTimeDataVOEntity that = (KeywordRealTimeDataVOEntity) o;

        if (click != null ? !click.equals(that.click) : that.click != null) return false;
        if (conversion != null ? !conversion.equals(that.conversion) : that.conversion != null) return false;
        if (cost != null ? !cost.equals(that.cost) : that.cost != null) return false;
        if (cpc != null ? !cpc.equals(that.cpc) : that.cpc != null) return false;
        if (ctr != null ? !ctr.equals(that.ctr) : that.ctr != null) return false;
        if (impression != null ? !impression.equals(that.impression) : that.impression != null) return false;
        if (keywordId != null ? !keywordId.equals(that.keywordId) : that.keywordId != null) return false;
        if (keywordName != null ? !keywordName.equals(that.keywordName) : that.keywordName != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = keywordId != null ? keywordId.hashCode() : 0;
        result = 31 * result + (keywordName != null ? keywordName.hashCode() : 0);
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
        return "KeywordRealTimeDataVOEntity{" +
                "keywordId=" + keywordId +
                ", keywordName='" + keywordName + '\'' +
                ", impression=" + impression +
                ", click=" + click +
                ", ctr=" + ctr +
                ", cost=" + cost +
                ", cpc=" + cpc +
                ", position=" + position +
                ", conversion=" + conversion +
                '}';
    }

    public void setOrderBy(String orderBy){
        this.orderBy=orderBy;
    }
    public String getOrderBy(){
        return orderBy;
    }
    @Override
    public int compareTo(KeywordRealTimeDataVOEntity o) {
        switch (o.getOrderBy()){
            case "1":
                return this.getImpression().compareTo(o.getImpression());
            case "2":
                return this.getClick().compareTo(o.getClick());
            case "-2":
                return o.getClick().compareTo(this.getClick());
            case "3":
                return this.getCost().compareTo(o.getCost());
            case "-3":
                return o.getCost().compareTo(this.getCost());
            case "4":
                return this.getCpc().compareTo(o.getCpc());
            case "-4":
                return o.getCpc().compareTo(this.getCpc());
            case "5":
                return this.getCtr().compareTo(o.getCtr());
            case "-5":
                return o.getCtr().compareTo(this.getCtr());
            case "6":
                return this.getConversion().compareTo(o.getConversion());
            case "-6":
                return o.getConversion().compareTo(this.getConversion());
            case "7":
                return this.getPosition().compareTo(o.getPosition());
            case "-7":
                return o.getPosition().compareTo(this.getPosition());
            default:
                return o.getImpression().compareTo(this.getImpression());
        }

    }
}
