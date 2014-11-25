package com.perfect.utils;

import java.math.BigDecimal;

/**
 * Created by yousheng on 2014/8/11.
 *
 * @author yousheng
 */
public class NumberUtils {

    public static Integer parseInt(Object obj) {
        if (obj == null) {
            return 0;
        }

        try {
            return Integer.parseInt(obj.toString());
        } catch (Exception ex) {
            return 0;
        }
    }

    public static Double parseDouble(Object obj) {
        if (obj == null) {
            return 0.0;
        }

        try {
            return Double.parseDouble(obj.toString());
        } catch (Exception ex) {
            return 0.0;
        }
    }


    public static Long parseLong(Object obj) {
        if (obj == null) {
            return 0l;
        }

        try {
            return Long.parseLong(obj.toString());
        } catch (Exception ex) {
            return 0l;
        }
    }

    public static BigDecimal parseBigDecimal(Object obj) {
        if (obj == null) {
            return BigDecimal.ZERO;
        }

        try {
            return BigDecimal.valueOf(Double.parseDouble(obj.toString()));
        } catch (Exception ex) {
            return BigDecimal.ZERO;
        }
    }

    public static Number getNumber(Number number) {
        return (number == null) ? 0 : number;
    }

    public static Integer getInteger(Integer integer) {
        return (Integer) getNumber(integer);
    }

    public static Double getDouble(Double num) {
        return (Double) getNumber(num);
    }

    public static Long getLong(Long num) {
        return (Long) getNumber(num);
    }

}
