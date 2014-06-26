/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.mongodb.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ScheduleType")
public class ScheduleEntity {
    private Long weekDay;

    //------------------------
    // MEMBER VARIABLES
    //------------------------
    private Long startHour;
    private Long endHour;

    public boolean setWeekDay(Long aWeekDay) {
        boolean wasSet = false;
        weekDay = aWeekDay;
        wasSet = true;
        return wasSet;
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setStartHour(Long aStartHour) {
        boolean wasSet = false;
        startHour = aStartHour;
        wasSet = true;
        return wasSet;
    }

    public boolean setEndHour(Long aEndHour) {
        boolean wasSet = false;
        endHour = aEndHour;
        wasSet = true;
        return wasSet;
    }

    public Long getWeekDay() {
        return weekDay;
    }

    public Long getStartHour() {
        return startHour;
    }

    public Long getEndHour() {
        return endHour;
    }

    public void delete() {
    }

    public String toString() {
        String outputString = "";
        return super.toString() + "[" +
                "weekDay" + ":" + getWeekDay() + "," +
                "startHour" + ":" + getStartHour() + "," +
                "endHour" + ":" + getEndHour() + "]"
                + outputString;
    }

}