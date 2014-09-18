package com.perfect.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by baizz on 2014-08-16.
 */
@SuppressWarnings("unchecked")
public class TopN {

    private static Method getMethod;  //the field's declared method

    private static int sort = -1;  //default DESC, get topN data

    private TopN() {
    }

    /**
     * topN算法
     *
     * @param ts
     * @param n
     * @param fieldName
     * @param sort
     * @return
     */
    public static <T> T[] getTopN(T[] ts, int n, String fieldName, int sort) {
        if (ts == null || ts.length == 0)
            return null;

        Class _class = ts[0].getClass();
        TopN.sort = sort;
        try {
            String fieldGetterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            TopN.getMethod = _class.getDeclaredMethod(fieldGetterName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        //TopN数组
        T topNData[];
        int l = ts.length;
        if (l >= n) {
            topNData = Arrays.copyOf(ts, n);
        } else {
            topNData = ts;
        }

        //采用快速排序
        quickSort(topNData, 0, topNData.length - 1);

        //遍历剩余部分
        try {
            for (int i = n; i < l; i++) {
                int index = (l >= n) ? (n - 1) : (l - 1);
                if (((Comparable) TopN.getMethod.invoke(ts[i])).compareTo(TopN.getMethod.invoke(topNData[index])) == 1) {
                    topNData[index] = ts[i];
                    quickSort(topNData, 0, topNData.length - 1);
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return topNData;
    }

    /**
     * 自定义快速排序实现
     *
     * @param arr
     * @param low
     * @param high
     */

    private static <T> void quickSort(T arr[], int low, int high) {
        int l = low;
        int h = high;

        if (l >= h)
            return;

        //确定指针方向的逻辑变量
        boolean transfer = true;

        try {
            while (l != h) {
                if (((Comparable) TopN.getMethod.invoke(arr[l])).compareTo(TopN.getMethod.invoke(arr[h])) == TopN.sort) {
                    //交换数据
                    T tempObj = arr[l];
                    arr[l] = arr[h];
                    arr[h] = tempObj;
                    //决定下标移动, 还是上标移动
                    transfer = !transfer;
                }

                //将指针向前或者向后移动
                if (transfer)
                    h--;
                else
                    l++;
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        //将数组分开两半, 确定每个数字的正确位置
        l--;
        h++;
        quickSort(arr, low, l);
        quickSort(arr, h, high);
    }
}
