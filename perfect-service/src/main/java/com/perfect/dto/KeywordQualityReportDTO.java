package com.perfect.dto;

import com.perfect.entity.KeywordReportEntity;

import java.util.List;

/**
 * Created by baizz on 2014-9-3.
 */
public class KeywordQualityReportDTO {

    private Integer grade;

    private List<KeywordReportEntity> reportList;

    public KeywordQualityReportDTO(Integer grade, List<KeywordReportEntity> reportList) {
        this.grade = grade;
        this.reportList = reportList;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public List<KeywordReportEntity> getReportList() {
        return reportList;
    }

    public void setReportList(List<KeywordReportEntity> reportList) {
        this.reportList = reportList;
    }
}
