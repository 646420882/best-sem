package com.perfect.commons;

/**
 * Created by subdong on 15-12-16.
 */
public class CustomUserDetails {

    private static boolean usernameNotFound = false;

    private static boolean verifyNotPass = false;

    private static boolean forbidden = false;

    /**
     * 登录状态
     */
    private static int passwdBadCredentialsNum = 0;

    private static boolean hasBaiduAccount = false;

    public static boolean isUsernameNotFound() {
        return usernameNotFound;
    }

    public static void setUsernameNotFound(boolean usernameNotFound) {
        CustomUserDetails.usernameNotFound = usernameNotFound;
    }

    public static boolean isVerifyNotPass() {
        return verifyNotPass;
    }

    public static void setVerifyNotPass(boolean verifyNotPass) {
        CustomUserDetails.verifyNotPass = verifyNotPass;
    }

    public static boolean isForbidden() {
        return forbidden;
    }

    public static void setForbidden(boolean forbidden) {
        CustomUserDetails.forbidden = forbidden;
    }

    public static int getPasswdBadCredentialsNum() {
        return passwdBadCredentialsNum;
    }

    public static void setPasswdBadCredentialsNum(int passwdBadCredentialsNum) {
        CustomUserDetails.passwdBadCredentialsNum = passwdBadCredentialsNum;
    }

    public static boolean isHasBaiduAccount() {
        return hasBaiduAccount;
    }

    public static void setHasBaiduAccount(boolean hasBaiduAccount) {
        CustomUserDetails.hasBaiduAccount = hasBaiduAccount;
    }
}
