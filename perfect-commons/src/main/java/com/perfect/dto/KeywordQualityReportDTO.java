package com.perfect.dto;

import java.util.List;

/**
 * Created by baizz on 2014-9-3.
 */
public class KeywordQualityReportDTO {

    private Integer grade;

    private List<KeywordReportDTO> reportList;

    public KeywordQualityReportDTO(Integer grade, List<KeywordReportDTO> reportList) {
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
