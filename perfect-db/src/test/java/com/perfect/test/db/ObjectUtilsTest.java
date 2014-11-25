package com.perfect.test.db;

import com.perfect.db.utils.ObjectUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yousheng on 14/11/25.
 */
public class ObjectUtilsTest {

    @Test
    public void output() {
        List<A> aList = new ArrayList<>();

        A a = new A();
        a.name = "Hello";
        aList.add(a);


        List<B> testList = ObjectUtils.convert(aList, B.class);

        System.out.println("testList = " + testList);

    }


    static class A {
        String name;
    }

    static class B {
        String name;
    }
}
