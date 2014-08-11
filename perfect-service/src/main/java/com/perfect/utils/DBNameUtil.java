package com.perfect.utils;

/**
 * Created by baizz on 2014-08-07.
 */
public class DBNameUtil {

    public static final String SYS_DB_NAME = "sys";

    private static final String DEFAULT_TYPE = "report";

    public static String getUserDBName(String username, String dbType) {
        if (username != null && dbType != null) {
            return "user_" + username + "_" + dbType;       //如: user_perfect_report
        }
        if (username != null) {
            return "user_" + username;      //如: user_perfect
        }

        return null;
    }

    public static String getReportDBName(String username) {
        return getUserDBName(username, DEFAULT_TYPE);
    }

}
