package com.perfect.controller;

import org.quartz.CronExpression;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by vbzer_000 on 2014/9/1.
 */
public class Main {
    public static void main1(String[] args) {
        Integer[] times = new Integer[]{0, 9, 9, 15, 16, 23};

        Arrays.sort(times);
        LinkedList<Integer> timeList = new LinkedList<>();
        for (Integer integer : times) {
            timeList.addLast(integer);
        }
        int[] hourOfDays = new int[24];
        boolean set = false;
        int start = timeList.removeFirst();
        int end = timeList.removeFirst();

        for (int i = 0; i < hourOfDays.length; i++) {
            if (i < start) {
                continue;
            } else if (end < i) {

                if (timeList.isEmpty()) {
                    break;
                }

                start = timeList.removeFirst();
                end = timeList.removeFirst();
                set = false;
                if (start < i && i < end) {
                    set = true;
                }
            } else {
                set = true;
            }
            if (set) {
                hourOfDays[i] = 1;
            }
        }

        System.out.println("hourOfDays = " + Arrays.toString(hourOfDays));


        int time = 0;
        int starts = findStart(hourOfDays, time);
        System.out.println("start : " + starts);
        System.out.println("end : " + findEnd(hourOfDays, starts));

    }

    private static int findEnd(int[] hourOfDays, int time) {
        int ret = findExp(hourOfDays, time, 0);
        if (ret == 0 || ret == -1) {
            return 23;
        } else {
            return ret - 1;
        }
    }

    private static int findStart(int[] hourOfDays, int time) {
        if (hourOfDays[time] == 1) {
            return time;
        } else {
            return findExp(hourOfDays, time, 1);
        }
    }

    private static int findExp(int[] hourOfDays, int time, int value) {
        for (int i = time; i < hourOfDays.length; i++) {
            if (hourOfDays[i] == value) {
                return i;
            }
        }
        //从0开始
        for (int i = 0; i < hourOfDays.length; i++) {
            if (hourOfDays[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {


        long start = System.currentTimeMillis();

        try {
            CronExpression cronExpression = new CronExpression("0 0 12-14 * * ?");

            Date date = Calendar.getInstance().getTime();
            for (int i = 0; i <= 20; i++) {
                date = cronExpression.getNextValidTimeAfter(date);
                System.out.println(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - start);


//        Main main = new Main();
//
//        try {
//            CronExpression cronExpression = new CronExpression("0 0 1-5/2 * * ?");
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(main.getDateInvHour(new int[]{12, 13, 14, 19}, 2));
    }

    private Date getDateInvHour(int[] times, int interval) {
        StringBuilder sb = new StringBuilder("0 0 ");

        for (int i = 0; i < times.length; i++) {
            sb.append(times[i]).append("-").append(times[++i]).append(",");
        }

        sb.deleteCharAt(sb.length() - 1).append("/").append(interval);
        sb.append(" * * ?");

        System.out.println(sb.toString());
        try {
            CronExpression cronExpression = new CronExpression(sb.toString());
            Date time = cronExpression.getNextValidTimeAfter(Calendar.getInstance().getTime());
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Date getDate(int[] times, int interval) {
        StringBuilder sb = new StringBuilder("0 */" + interval + " ");

        for (int i = 0; i < times.length; i++) {
            sb.append(times[i]).append("-").append(times[++i]).append(",");
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append(" * * ?");

        System.out.println(sb.toString());
        try {
            CronExpression cronExpression = new CronExpression(sb.toString());
            Date time = cronExpression.getNextValidTimeAfter(Calendar.getInstance().getTime());
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
