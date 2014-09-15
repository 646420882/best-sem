package com.perfect.dto;

import java.io.Serializable;

/**
 * Created by baizz on 2014-9-15.
 */
public class BaiduKeywordDTO implements Serializable {

    private String groupName;

    private String seedWord;

    private String keywordName;

    private Integer dsQuantity;      //日均展现量(精确)

    private Double competition;     //竞争激烈程度

    private String recommendReason1;    //一级推荐理由

    private String recommendReason2;    //二级推荐理由

    public BaiduKeywordDTO() {
    }

    public BaiduKeywordDTO(String groupName, String seedWord, String keywordName, Integer dsQuantity,
                           Double competition, String recommendReason1, String recommendReason2) {
        this.groupName = groupName;
        this.seedWord = seedWord;
        this.keywordName = keywordName;
        this.dsQuantity = dsQuantity;
        this.competition = competition;
        this.recommendReason1 = recommendReason1;
        this.recommendReason2 = recommendReason2;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSeedWord() {
        return seedWord;
    }

    public void setSeedWord(String seedWord) {
        this.seedWord = seedWord;
    }

    public String getKeywordName() {
        return keywordName;
    }

    public void setKeywordName(String keywordName) {
        this.keywordName = keywordName;
    }

    public Integer getDsQuantity() {
        return dsQuantity;
    }

    public void setDsQuantity(Integer dsQuantity) {
        this.dsQuantity = dsQuantity;
    }

    public Double getCompetition() {
        return competition;
    }

    public void setCompetition(Double competition) {
        this.competition = competition;
    }

    public String getRecommendReason1() {
        return recommendReason1;
    }

    public void setRecommendReason1(String recommendReason1) {
        this.recommendReason1 = recommendReason1;
    }

    public String getRecommendReason2() {
        return recommendReason2;
    }

    public void setRecommendReason2(String recommendReason2) {
        this.recommendReason2 = recommendReason2;
    }
}
