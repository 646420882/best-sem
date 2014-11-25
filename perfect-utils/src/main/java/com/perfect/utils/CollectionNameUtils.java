package com.perfect.utils;

import java.util.Calendar;

/**
 * Created by yousheng on 2014/8/11.
 *
 * @author yousheng
 */
public class CollectionNameUtils {

    private final static String TABLE_SEP = "-";

    private final static String TABLE_KC = "KC";

    private final static String TABLE_REGION = "REGION";

    public static String getKCCollection(Calendar calendar, String suffix) {

        return calendar.get(Calendar.YEAR) + TABLE_SEP + (calendar.get(Calendar.MONTH) + 1) + TABLE_SEP + calendar.get(Calendar.DAY_OF_MONTH) + TABLE_SEP + suffix;

    }

    public static String getYesterdayKc() {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.MONTH));
        calendar.setTimeInMillis(calendar.getTimeInMillis() - 24 * 60 * 60 * 1000);

        return getKCCollection(calendar, TABLE_KC);
    }

    public static String getYesterdayRegion() {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.MONTH));
        calendar.setTimeInMillis(calendar.getTimeInMillis() - 24 * 60 * 60 * 1000);

        return getKCCollection(calendar, TABLE_REGION);
    }


    public static void main(String args[]) {
        System.out.println(getYesterdayKc());
    }

}
