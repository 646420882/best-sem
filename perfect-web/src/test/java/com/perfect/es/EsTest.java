package com.perfect.es;

/**
 * Created by baizz on 14-12-17.
 */
public class EsTest {

    public static void main(String[] args) {
        EsTemplate esTemplate = new EsTemplate("test", "creative");
        System.out.println(esTemplate.count());
//        esTemplate.deleteAll("test");
        System.out.println(esTemplate.count());
    }
}
