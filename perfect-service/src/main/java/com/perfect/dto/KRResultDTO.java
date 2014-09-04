package com.perfect.dto;

/**
 * Created by baizz on 2014-9-4.
 */
public class KRResultDTO {

    private String word;
    private Long exactPV;
    private Integer competition;
    private Integer flag1;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Long getExactPV() {
        return exactPV;
    }

    public void setExactPV(Long exactPV) {
        this.exactPV = exactPV;
    }

    public Integer getCompetition() {
        return competition;
    }

    public void setCompetition(Integer competition) {
        this.competition = competition;
    }

    public Integer getFlag1() {
        return flag1;
    }

    public void setFlag1(Integer flag1) {
        this.flag1 = flag1;
    }
}
