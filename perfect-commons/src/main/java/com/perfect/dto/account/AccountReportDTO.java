package com.perfect.dto.account;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by SubDong on 2014/8/21.
 */
public class AccountReportDTO extends AccountIdDTO implements Comparable<AccountReportDTO>{


    private String accountName;

    private Date date;

    private Integer pcImpression;     //PC展现次数

    private Integer pcClick;      //PC点击次数

    private Double pcCtr;     //PC点击率=点击次数/展现次数

    private BigDecimal pcCost;        //PC消费

    private BigDecimal pcCpc;     //PC平均点击价格=消费/点击次数

    private BigDecimal pcCpm;       //PC千次展现消费

    private Double pcConversion;      //PC转化

    private Integer mobileImpression;

    private Integer mobileClick;

    private Double mobileCtr;

    private BigDecimal mobileCost;

    private BigDecimal mobileCpc;

    private BigDecimal mobileCpm;

    private Double mobileConversion;

    private long count;

    private String dateRep;

    private String OrderBy;

    private int devices;


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public BigDecimal getPcCost() {
        return pcCost;
    }

    public void setPcCost(BigDecimal pcCost) {
        this.pcCost = pcCost;
    }

    public BigDecimal getPcCpc() {
        return pcCpc;
    }

    public void setPcCpc(BigDecimal pcCpc) {
        this.pcCpc = pcCpc;
    }

    public BigDecimal getPcCpm() {
        return pcCpm;
    }

    public void setPcCpm(BigDecimal pcCpm) {
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

    public BigDecimal getMobileCost() {
        return mobileCost;
    }

    public void setMobileCost(BigDecimal mobileCost) {
        this.mobileCost = mobileCost;
    }

    public BigDecimal getMobileCpc() {
        return mobileCpc;
    }

    public void setMobileCpc(BigDecimal mobileCpc) {
        this.mobileCpc = mobileCpc;
    }

    public BigDecimal getMobileCpm() {
        return mobileCpm;
    }

    public void setMobileCpm(BigDecimal mobileCpm) {
        this.mobileCpm = mobileCpm;
    }

    public Double getMobileConversion() {
        return mobileConversion;
    }

    public void setMobileConversion(Double mobileConversion) {
        this.mobileConversion = mobileConversion;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getDateRep() {
        return dateRep;
    }

    public void setDateRep(String dateRep) {
        this.dateRep = dateRep;
    }

    public String getOrderBy() {
        return OrderBy;
    }

    public void setOrderBy(String orderBy) {
        OrderBy = orderBy;
    }

    public int getDevices() {
        return devices;
    }

    public void setDevices(int devices) {
        this.devices = devices;
    }

    @Override
    public int compareTo(AccountReportDTO o) {
        switch (o.getOrderBy()){
            case "1":
                return this.getDate().compareTo(o.getDate());
            case "2":
                if(o.getDevices() == 2){
                    return this.getMobileImpression().compareTo(o.getMobileImpression());
                }else{
                    return this.getPcImpression().compareTo(o.getPcImpression());
                }
            case "-2":
                if(o.getDevices() == 2){
                    return o.getMobileImpression().compareTo(this.getMobileImpression());
                }else{
                    return o.getPcImpression().compareTo(this.getPcImpression());
                }
            case "3":
                if(o.getDevices() == 2){
                    return this.getMobileClick().compareTo(o.getMobileClick());
                }else{
                    return this.getPcClick().compareTo(o.getPcClick());
                }
            case "-3":
                if(o.getDevices() == 2){
                    return o.getMobileClick().compareTo(this.getMobileClick());
                }else{
                    return o.getPcClick().compareTo(this.getPcClick());
                }
            case "4":
                if(o.getDevices() == 2){
                    return this.getMobileCost().compareTo(o.getMobileCost());
                }else{
                    return this.getPcCost().compareTo(o.getPcCost());
                }
            case "-4":
                if(o.getDevices() == 2){
                    return o.getMobileCost().compareTo(this.getMobileCost());
                }else{
                    return o.getPcCost().compareTo(this.getPcCost());
                }
            case "5":
                if(o.getDevices() == 2){
                    return this.getMobileCpc().compareTo(o.getMobileCpc());
                }else{
                    return this.getPcCpc().compareTo(o.getPcCpc());
                }
            case "-5":
                if(o.getDevices() == 2){
                    return o.getMobileCpc().compareTo(this.getMobileCpc());
                }else{
                    return o.getPcCpc().compareTo(this.getPcCpc());
                }
            case "6":
                if(o.getDevices() == 2){
                    return this.getMobileCtr().compareTo(o.getMobileCtr());
                }else{
                    return this.getPcCtr().compareTo(o.getPcCtr());
                }
            case "-6":
                if(o.getDevices() == 2){
                    return o.getMobileCtr().compareTo(this.getMobileCtr());
                }else{
                    return o.getPcCtr().compareTo(this.getPcCtr());
                }
            case "7":
                if(o.getDevices() == 2){
                    return this.getMobileConversion().compareTo(o.getMobileConversion());
                }else{
                    return this.getPcConversion().compareTo(o.getPcConversion());
                }
            case "-7":
                if(o.getDevices() == 2){
                    return o.getMobileConversion().compareTo(this.getMobileConversion());
                }else{
                    return o.getPcConversion().compareTo(this.getPcConversion());
                }
            default:
                return o.getDate().compareTo(this.getDate());
        }
    }
}
