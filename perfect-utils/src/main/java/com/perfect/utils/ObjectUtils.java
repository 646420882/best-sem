package com.perfect.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vbzer_000 on 14-11-25.
 */
public class ObjectUtils {

    public static <T> List<T> convert(List srcList, Class<T> targetClz) {

        if (srcList == null || srcList.isEmpty()) {
            return Collections.emptyList();
        }

        List<T> targetList = new ArrayList(srcList.size());
        srcList.stream().filter((o) -> {
            return o != null;
        }).forEach((o) -> {
            try {
                T t = targetClz.newInstance();
                BeanUtils.copyProperties(o, t);

                targetList.add(t);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return targetList;
    }
}
