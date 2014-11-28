package com.perfect;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by baizz on 14-11-25.
 */
public class DateUtils {

    public static final String KEY_STRING = "_string";

    public static final String KEY_DATE = "_date";

    public static List<String> getPeriod(String _startDate, String _endDate) {
        Objects.requireNonNull(_startDate);
        Objects.requireNonNull(_endDate);

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

    public static Map<String, List> getsLatestSevenDays() {
        return getsLatestAnyDays("MM-dd", 7);
    }

    public static Map<String, List> getsLatestAnyDays(String format, int num) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
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

        Map<String, List> map = new HashMap<>(2);
        map.put(KEY_STRING, anyDays);
        map.put(KEY_DATE, dates);
        return map;
    }


    public static Date getYesterday() {
        return (Date) getsLatestAnyDays("yyyy-MM-dd", 1).get(KEY_DATE).get(0);
    }

    public static String getYesterdayStr() {
        return getsLatestAnyDays("yyyy-MM-dd", 1).get(KEY_STRING).get(0).toString();
    }
}
