package com.perfect.db.mongodb.proxy;

/**
 * Created by vbzer_000 on 2014/6/26.
 */
public class MongoCollectionUtils {

    private final String DAO_NAME = "DAO";


    public static <T> String getDaoName(Class<T> clz) {

        String simpleName = clz.getSimpleName();
        return simpleName.toLowerCase().charAt(0) + simpleName.substring(1) + "DAO";
    }


    public static void main(String args[]) {
        Class clz = MongoCollectionUtils.class;

        String name = MongoCollectionUtils.getDaoName(clz);
        System.out.println(name);
    }
}
