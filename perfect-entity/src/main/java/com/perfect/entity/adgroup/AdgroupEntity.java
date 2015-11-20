/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.entity.adgroup;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.entity.account.AccountIdEntity;
import com.perfect.entity.baidu.OptTypeEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * Created by baizz on 2014-08-13.
 *
 * @description 百度推广单元实体类
 */
@Document(collection = MongoEntityConstants.TBL_ADGROUP)
public class AdgroupEntity extends AccountIdEntity {

    @Id
    private String id;

    @Indexed(sparse = true)
    @Field(MongoEntityConstants.ADGROUP_ID)
    private Long adgroupId;                         // 百度推广单元ID

    //------------------------
    // MEMBER VARIABLES
    //------------------------
    @Field(MongoEntityConstants.CAMPAIGN_ID)
    private Long campaignId;                        // 百度推广计划ID


    @Field(MongoEntityConstants.OBJ_CAMPAIGN_ID)
    private String campaignObjId;                   // 本地推广计划操作ID

    @Field("name")
    private String adgroupName;                     // 推广单元名称

    @Field("max")
    private Double maxPrice;                        // 最大单元出价

    @Field("neg")
    private List<String> negativeWords;             // 否定关键词

    @Field("exneg")
    private List<String> exactNegativeWords;        // 单元精确否定关键词

    @Field("res")
    private String reserved;                        // 预留字段(来自百度API的描述)

    @Field("p")
    private Boolean pause;                          // 暂停/启用 推广单元

    @Field("s")
    private Integer status;                         // 推广单元状态

    @Field("o")
    private OptTypeEntity opt;                      // 未知属性

    @Field("m")
    private Double mib;                             // 未知属性

    @Field("priceR")
    private Double priceRatio;                      // 单元无线出价比例

    @Field("ls")
    private Integer localStatus;                    // 本地操作状态标识

    public String getCampaignObjId() {
        return campaignObjId;
    }

    public void setCampaignObjId(String campaignObjId) {
        this.campaignObjId = campaignObjId;
    }

    public Double getMib() {
        return mib;
    }

    public void setMib(Double mib) {
        this.mib = mib;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    private String campaignName;

    public Integer getLocalStatus() {
        return localStatus;
    }

    public void setLocalStatus(Integer localStatus) {
        this.localStatus = localStatus;
    }

    public Double getPriceRatio() {
        return priceRatio;
    }

    public void setPriceRatio(Double priceRatio) {
        this.priceRatio = priceRatio;
    }

    public boolean setAdgroupId(Long aAdgroupId) {
        boolean wasSet = false;
        adgroupId = aAdgroupId;
        wasSet = true;
        return wasSet;
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setCampaignId(Long aCampaignId) {
        boolean wasSet = false;
        campaignId = aCampaignId;
        wasSet = true;
        return wasSet;
    }

    public boolean setAdgroupName(String aAdgroupName) {
        boolean wasSet = false;
        adgroupName = aAdgroupName;
        wasSet = true;
        return wasSet;
    }

    public boolean setMaxPrice(Double aMaxPrice) {
        boolean wasSet = false;
        maxPrice = aMaxPrice;
        wasSet = true;
        return wasSet;
    }

    public boolean addNegativeWord(String aNegativeWord) {
        boolean wasAdded = false;
        wasAdded = negativeWords.add(aNegativeWord);
        return wasAdded;
    }

    public boolean removeNegativeWord(String aNegativeWord) {
        boolean wasRemoved = false;
        wasRemoved = negativeWords.remove(aNegativeWord);
        return wasRemoved;
    }

    public boolean addExactNegativeWord(String aExactNegativeWord) {
        boolean wasAdded = false;
        wasAdded = exactNegativeWords.add(aExactNegativeWord);
        return wasAdded;
    }

    public boolean removeExactNegativeWord(String aExactNegativeWord) {
        boolean wasRemoved = false;
        wasRemoved = exactNegativeWords.remove(aExactNegativeWord);
        return wasRemoved;
    }

    public boolean setReserved(String aReserved) {
        boolean wasSet = false;
        reserved = aReserved;
        wasSet = true;
        return wasSet;
    }

    public boolean setPause(Boolean aPause) {
        boolean wasSet = false;
        pause = aPause;
        wasSet = true;
        return wasSet;
    }

    public boolean setStatus(Integer aStatus) {
        boolean wasSet = false;
        status = aStatus;
        wasSet = true;
        return wasSet;
    }

    public boolean setOpt(OptTypeEntity aOpt) {
        boolean wasSet = false;
        opt = aOpt;
        wasSet = true;
        return wasSet;
    }

    public Long getAdgroupId() {
        return adgroupId;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public String getAdgroupName() {
        return adgroupName;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public String getNegativeWord(int index) {
        String aNegativeWord = negativeWords.get(index);
        return aNegativeWord;
    }

    public List<String> getNegativeWords() {
        return negativeWords;
    }

    public void setNegativeWords(List<String> anegativeWords) {
        negativeWords = anegativeWords;
    }

    public int numberOfNegativeWords() {
        int number = negativeWords.size();
        return number;
    }

    public boolean hasNegativeWords() {
        boolean has = negativeWords.size() > 0;
        return has;
    }

    public int indexOfNegativeWord(String aNegativeWord) {
        int index = negativeWords.indexOf(aNegativeWord);
        return index;
    }

    public String getExactNegativeWord(int index) {
        String aExactNegativeWord = exactNegativeWords.get(index);
        return aExactNegativeWord;
    }

    public List<String> getExactNegativeWords() {
        return exactNegativeWords;
    }

    public void setExactNegativeWords(List<String> aexactNegativeWords) {
        exactNegativeWords = aexactNegativeWords;
    }

    public int numberOfExactNegativeWords() {
        int number = exactNegativeWords.size();
        return number;
    }

    public boolean hasExactNegativeWords() {
        boolean has = exactNegativeWords.size() > 0;
        return has;
    }

    public int indexOfExactNegativeWord(String aExactNegativeWord) {
        int index = exactNegativeWords.indexOf(aExactNegativeWord);
        return index;
    }

    public String getReserved() {
        return reserved;
    }

    public Boolean getPause() {
        return pause;
    }

    public Integer getStatus() {
        return status;
    }

    public OptTypeEntity getOpt() {
        return opt;
    }

    public void delete() {
    }

    public String toString() {
        String outputString = "";
        return super.toString() + "[" +
                "adgroupId" + ":" + getAdgroupId() + "," +
                "campaignId" + ":" + getCampaignId() + "," +
                "adgroupName" + ":" + getAdgroupName() + "," +
                "maxPrice" + ":" + getMaxPrice() + "," +
                "reserved" + ":" + getReserved() + "," +
                "pause" + ":" + getPause() + "," +
                "status" + ":" + getStatus() + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "opt" + "=" + (getOpt() != null ? !getOpt().equals(this) ? getOpt().toString().replaceAll("  ", "    ") : "this" : "null")
                + outputString;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}