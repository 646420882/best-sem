package com.perfect.dto;

/**
 * Created by yousheng on 14/11/20.
 * 2014-11-28 refactor
 */
public class SchedulerDTO {
    private Long weekDay;
    private Long startHour;
    private Long endHour;

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setWeekDay(Long aWeekDay)
    {
        boolean wasSet = false;
        weekDay = aWeekDay;
        wasSet = true;
        return wasSet;
    }

    public boolean setStartHour(Long aStartHour)
    {
        boolean wasSet = false;
        startHour = aStartHour;
        wasSet = true;
        return wasSet;
    }

    public boolean setEndHour(Long aEndHour)
    {
        boolean wasSet = false;
        endHour = aEndHour;
        wasSet = true;
        return wasSet;
    }

    public Long getWeekDay()
    {
        return weekDay;
    }

    public Long getStartHour()
    {
        return startHour;
    }

    public Long getEndHour()
    {
        return endHour;
    }

    public void delete()
    {}


    public String toString()
    {
        String outputString = "";
        return super.toString() + "["+
                "weekDay" + ":" + getWeekDay()+ "," +
                "startHour" + ":" + getStartHour()+ "," +
                "endHour" + ":" + getEndHour()+ "]"
                + outputString;
    }
}
