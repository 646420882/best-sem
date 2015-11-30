package com.perfect.param;

/**
 * @author xiaowei
 * @title FindOrReplaceParam
 * @package com.perfect.param
 * @description 文字查找，替换param类，用于封装前段传入数据
 * @update 2015年09月25日. 上午10:35
 * @update 2015年10月14日18:12:12  添加单元ID变量
 */
public class FindOrReplaceParam {
    //要查找的层级类别
    String type;

    //确定是选择的某几条数据还是所有数据
    Integer forType;//0为有选中的数据,1为当前选中计划,-1为全账户(所有计划),2为当前选择的单元下所有

    //查找选中状态的物料，
    String checkData;

    //要查找的文字
    String findText;

    //查找位于层级哪个字段
    String forPlace;

    //匹配大小写
    boolean fQcaseLowerAndUpper;

    //匹配整个字词
    boolean fQcaseAll;


    //忽略文字两端空格(查找)
    boolean fQigonreTirm;

    //忽略文字两端空格(替换)
    boolean rQigonreTirm;

    //要替换的文字
    String replaceText = null;

    String campaignId;

    String adgroupId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCheckData() {
        return checkData;
    }

    public void setCheckData(String checkData) {
        this.checkData = checkData;
    }

    public String getFindText() {
        return findText;
    }

    public void setFindText(String findText) {
        this.findText = findText;
    }

    public String getForPlace() {
        return forPlace;
    }

    public void setForPlace(String forPlace) {
        this.forPlace = forPlace;
    }

    public boolean isfQcaseLowerAndUpper() {
        return fQcaseLowerAndUpper;
    }

    public void setfQcaseLowerAndUpper(boolean fQcaseLowerAndUpper) {
        this.fQcaseLowerAndUpper = fQcaseLowerAndUpper;
    }

    public boolean isfQcaseAll() {
        return fQcaseAll;
    }

    public void setfQcaseAll(boolean fQcaseAll) {
        this.fQcaseAll = fQcaseAll;
    }

    public boolean isfQigonreTirm() {
        return fQigonreTirm;
    }

    public void setfQigonreTirm(boolean fQigonreTirm) {
        this.fQigonreTirm = fQigonreTirm;
    }

    public boolean isrQigonreTirm() {
        return rQigonreTirm;
    }

    public void setrQigonreTirm(boolean rQigonreTirm) {
        this.rQigonreTirm = rQigonreTirm;
    }

    public String getReplaceText() {
        return replaceText;
    }

    public void setReplaceText(String replaceText) {
        this.replaceText = replaceText;
    }

    public Integer getForType() {
        return forType;
    }

    public void setForType(Integer forType) {
        this.forType = forType;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getAdgroupId() {
        return adgroupId;
    }

    public void setAdgroupId(String adgroupId) {
        this.adgroupId = adgroupId;
    }

    @Override
    public String toString() {
        return "FindOrReplaceParam{" +
                "type='" + type + '\'' +
                ", forType=" + forType +
                ", checkData='" + checkData + '\'' +
                ", findText='" + findText + '\'' +
                ", forPlace='" + forPlace + '\'' +
                ", fQcaseLowerAndUpper=" + fQcaseLowerAndUpper +
                ", fQcaseAll=" + fQcaseAll +
                ", fQigonreTirm=" + fQigonreTirm +
                ", rQigonreTirm=" + rQigonreTirm +
                ", replaceText='" + replaceText + '\'' +
                ", campaignId='" + campaignId + '\'' +
                ", adgroupId='" + adgroupId + '\'' +
                '}';
    }
}
