package com.perfect.utils;


import com.perfect.utils.json.JSONUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vbzer_000 on 14-11-25.
 */
public class ObjectUtils {

    public static <T> List<T> convert(List<?> srcList, Class<T> targetClz) {
        if (srcList == null || srcList.isEmpty()) {
            return Collections.emptyList();
        }

        List<T> targetList = new ArrayList<>(srcList.size());
        srcList.stream().filter(s -> s != null).forEach((s) -> {
            try {
                T t = targetClz.newInstance();
                BeanUtils.copyProperties(s, t);
                targetList.add(t);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return targetList;
    }

    public static <S, T> T convert(S srcObj, Class<T> targetClz) {
        if (srcObj == null) {
            return null;
        }

        T t = null;
        try {
            t = targetClz.newInstance();
            BeanUtils.copyProperties(srcObj, t);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return t;
    }

    public static <S, T> T convertToObject(S srcObj, Class<T> targetClz) {
        if (srcObj == null) {
            return null;
        }

        return JSONUtils.getObjectByJson(JSONUtils.getJsonObject(srcObj).toString(), targetClz);
    }

    @SuppressWarnings("unchecked")
    public static <S, T> List<T> convertToList(S list, Class<T> targetClz) {
        if (list == null) {
            return Collections.EMPTY_LIST;
        }

        return JSONUtils.getObjectListByJson(JSONUtils.getJsonObject(list).toString(), targetClz);
    }
}
