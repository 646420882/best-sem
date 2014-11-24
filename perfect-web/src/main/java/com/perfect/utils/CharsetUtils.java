package com.perfect.utils;

import java.io.UnsupportedEncodingException;

/**
 * Created by vbzer_000 on 2014/9/12.
 */
public class CharsetUtils {
    public static String decode(String s) {
        try {
            return new String(s.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return s;
    }
}
