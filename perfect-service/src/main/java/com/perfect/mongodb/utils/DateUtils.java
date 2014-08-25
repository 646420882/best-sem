package com.perfect.mongodb.utils;

import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by baizz on 2014-8-1.
 */
public class DateUtils {

    public static List<String> getPeriod(String _startDate, String _endDate) {
        if (_startDate == null) {
            Assert.notNull(_startDate, "_startDate must not be null!");
        }

        if (_endDate == null) {
            Assert.notNull(_endDate, "_endDate must not be null!");
        }

        List<String> dateStrList = new ArrayList<>();
        Date startDate = null, endDate = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startDate = sdf.parse(_startDate);
            endDate = sdf.parse(_endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (_startDate.equals(_endDate)) {
            dateStrList.add(_startDate);
        } else {
            //某一个时间段
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(startDate);
            while (cal1.getTime().getTime() <= endDate.getTime()) {
                cal1.setTime(startDate);
                dateStrList.add(sdf.format(cal1.getTime()));
                cal1.add(Calendar.DATE, 1);
                startDate = cal1.getTime();
            }

        }

        return dateStrList;
    }

    public static Map<String, Object> getsLatestSevenDays() {
        return getsLatestAnyDays(7);
    }

    public static Map<String, Object> getsLatestAnyDays(int num) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        List<String> anyDays = new ArrayList<>();
        List<Date> dates = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        for (int i = -num; i < 0; i++) {
            cal.add(Calendar.DATE, -1);
            dates.add(cal.getTime());
            anyDays.add(sdf.format(cal.getTime()));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("_string", anyDays);
        map.put("_date", dates);
        return map;
    }

}
