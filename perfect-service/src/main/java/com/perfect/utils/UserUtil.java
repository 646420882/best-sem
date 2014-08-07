package com.perfect.utils;

/**
 * Created by baizz on 2014-08-07.
 */
public class UserUtil {

    public static String getDatabaseName(String... args) {
        String databaseName = null;

        if (args.length == 0) {
            databaseName = "sys";
        } else if (args.length == 1) {
            //只传用户名
            databaseName = "user_" + args[0];   //如: user_perfect
        } else if (args.length == 2) {
            databaseName = "user_" + args[0] + "_" + args[1];   //如: user_perfect_report
        }

        return databaseName;
    }

}
