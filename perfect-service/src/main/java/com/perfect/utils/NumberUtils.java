package com.perfect.utils;

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
}
