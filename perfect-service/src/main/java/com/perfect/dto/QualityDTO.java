package com.perfect.dto;

/**
 * Created by baizz on 2014-9-3.
 */
public class QualityDTO {

    private Integer keywordQty;

    private Double keywordQtyRate;

    private Integer impression;

    private Double impressionRate;

    private Integer click;

    private Double clickRate;

    private Double ctr;

    private Double cost;

    private Double costRate;

    private Double cpc;

    private Double conversion;

    private Double conversionRate;

    public QualityDTO(Integer keywordQty, Double keywordQtyRate, Integer impression,
                      Double impressionRate, Integer click, Double clickRate, Double ctr,
                      Double cost, Double costRate, Double cpc, Double conversion, Double conversionRate) {
        this.keywordQty = keywordQty;
        this.keywordQtyRate = keywordQtyRate;
        this.impression = impression;
        this.impressionRate = impressionRate;
        this.click = click;
        this.clickRate = clickRate;
        this.ctr = ctr;
        this.cost = cost;
        this.costRate = costRate;
        this.cpc = cpc;
        this.conversion = conversion;
        this.conversionRate = conversionRate;
    }

    public Integer getKeywordQty() {
        return keywordQty;
    }

    public void setKeywordQty(Integer keywordQty) {
        this.keywordQty = keywordQty;
    }

    public Double getKeywordQtyRate() {
        return keywordQtyRate;
    }

    public void setKeywordQtyRate(Double keywordQtyRate) {
        this.keywordQtyRate = keywordQtyRate;
    }

    public Integer getImpression() {
        return impression;
    }

    public void setImpression(Integer impression) {
        this.impression = impression;
    }

    public Double getImpressionRate() {
        return impressionRate;
    }

    public void setImpressionRate(Double impressionRate) {
        this.impressionRate = impressionRate;
    }

    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

    public Double getClickRate() {
        return clickRate;
    }

    public void setClickRate(Double clickRate) {
        this.clickRate = clickRate;
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

    public Double getCostRate() {
        return costRate;
    }

    public void setCostRate(Double costRate) {
        this.costRate = costRate;
    }

    public Double getCpc() {
        return cpc;
    }

    public void setCpc(Double cpc) {
        this.cpc = cpc;
    }

    public Double getConversion() {
        return conversion;
    }

    public void setConversion(Double conversion) {
        this.conversion = conversion;
    }

    public Double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }
}
