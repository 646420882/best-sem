package com.perfect.vo;

import com.perfect.dto.keyword.KeywordReportDTO;

import java.util.List;

/**
 * Created by baizz on 2014-11-26.
 */
public class KeywordQualityReportVO {

    private Integer grade;

    private List<KeywordReportDTO> reportList;

    public KeywordQualityReportVO(Integer grade, List<KeywordReportDTO> reportList) {
        this.grade = grade;
        this.reportList = reportList;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public List<KeywordReportDTO> getReportList() {
        return reportList;
    }

    public void setReportList(List<KeywordReportDTO> reportList) {
        this.reportList = reportList;
    }
}
