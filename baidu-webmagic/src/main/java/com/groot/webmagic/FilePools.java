package com.groot.webmagic;

/**
 * Created by yousheng on 14/11/6.
 */
public class FilePools implements Constant {

    private static boolean isInit = false;

    public static void init() {
        if(isInit){
            return;
        }
        synchronized (FilePools.class) {
            if(!isInit){

            }
        }
    }

}
