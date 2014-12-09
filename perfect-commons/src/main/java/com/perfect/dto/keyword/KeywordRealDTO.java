package com.perfect.dto.keyword;

/**
 * Created by SubDong on 2014/11/26.
 */
public class KeywordRealDTO implements Comparable<KeywordRealDTO> {

    private Long keywordId;

    private String keywordName;

    private Integer impression;     //展现次数

    private Integer click;      //点击次数

    private Double ctr;     //点击率=点击次数/展现次数

    private Double cost;        //消费

    private Double cpc;     //平均点击价格=消费/点击次数

    private Double position;       //平均排名

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

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderBy() {
        return orderBy;
    }

    @Override
    public int compareTo(KeywordRealDTO o) {
        switch (o.getOrderBy()) {
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
