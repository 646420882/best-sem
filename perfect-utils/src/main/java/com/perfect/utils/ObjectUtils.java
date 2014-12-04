package com.perfect.utils;


import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by vbzer_000 on 14-11-25.
 */
public class ObjectUtils {

    public static <T> List<T> convert(List<?> srcList, Class<T> targetClz) {
        Objects.requireNonNull(srcList);
        if (srcList.isEmpty()) {
            return Collections.emptyList();
        }

        List<T> targetList = new ArrayList<>(srcList.size());
        srcList.stream().filter((o) -> o != null).forEach((o) -> {
            try {
                T t = targetClz.newInstance();
                BeanUtils.copyProperties(o, t);
                targetList.add(t);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return targetList;
    }

    public static <S, T> T convert(S srcObj, Class<T> targetClz) {
        Objects.requireNonNull(srcObj);
        T t = null;
        try {
            t = targetClz.newInstance();
            BeanUtils.copyProperties(srcObj, t);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return t;
    }
}
