package com.perfect.schedule.task.core;

/**
 * Created by yousheng on 2014/8/7.
 *
 * @author yousheng
 */
public enum ReportType {

    STRUCTURE("STRUCTURE"), KEYWORD("KEYWORD"), CREATURE("CREATURE"), REGION("REGION");


    private String value;

    private ReportType(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
