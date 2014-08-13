/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.entity;

import com.perfect.autosdk.common.OptType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "adgroup")
public class AdgroupEntity {

    @Id
    private ObjectId id;

    //AdgroupType Attributes
    @Indexed(unique = true)
    @Field("adid")
    private Long adgroupId;

    //------------------------
    // MEMBER VARIABLES
    //------------------------
    @Field("cid")
    private Long campaignId;

    @Field("name")
    private String adgroupName;

    @Field("max")
    private Double maxPrice;

    @Field("neg")
    private List<String> negativeWords;

    @Field("exneg")
    private List<String> exactNegativeWords;

    @Field("res")
    private String reserved;

    @Field("p")
    private Boolean pause;

    @Field("s")
    private Integer status;

    @Field("o")
    private OptType opt;

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

    public boolean setOpt(OptType aOpt) {
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

    public OptType getOpt() {
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

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}