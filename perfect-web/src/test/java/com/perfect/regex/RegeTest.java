package com.perfect.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by XiaoWei on 2015/2/10.
 */
public class RegeTest {
    public static void main(String[] args) {
        Pattern pattern=Pattern.compile("(^javascript:).*||#");
        Matcher m=pattern.matcher("http://");
        boolean bol=m.matches();
        System.out.println(bol);
    }
}
