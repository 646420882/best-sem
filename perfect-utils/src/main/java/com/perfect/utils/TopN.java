package com.perfect.utils;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * TopN algorithm, include QuickSort and TimSort
 * Date: 2014-08-16
 * 2014-12-01 refactor
 *
 * @author baizz
 */
@SuppressWarnings("unchecked")
public class TopN {

    private static Method method;  //the field's declared method

    private static int sort = -1;  //default DESC

    private TopN() {
    }

    /**
     * QuickSort
     *
     * @param ts
     * @param n
     * @param fieldName
     * @param sort
     * @return
     */
    public static <T> T[] getTopN(T[] ts, int n, String fieldName, int sort) {
        Objects.requireNonNull(ts);
        if (ts.length == 0 || sort * sort != 1)
            return null;

        TopN.sort = sort;
        try {
            String fieldGetterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            TopN.method = ts[0].getClass().getDeclaredMethod(fieldGetterName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        //TopN数组
        T topNData[];
        int l = ts.length;
        if (l >= n) {
            topNData = (T[]) Array.newInstance(ts.getClass().getComponentType(), n);
            System.arraycopy(ts, 0, topNData, 0, n);
        } else {
            topNData = ts;
        }

        //采用快速排序
        quickSort(topNData, 0, topNData.length - 1);

        //遍历剩余部分
        try {
            for (int i = n; i < l; i++) {
                int index = (l >= n) ? (n - 1) : (l - 1);
                if (((Comparable) TopN.method.invoke(ts[i])).compareTo(TopN.method.invoke(topNData[index])) == 1) {
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
                if (((Comparable) TopN.method.invoke(arr[l])).compareTo(TopN.method.invoke(arr[h])) == TopN.sort) {
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

    /**
     * TimSort
     *
     * @param ts
     * @param n
     * @param fieldName
     * @param sort
     * @param <T>
     * @return
     */
    public static <T> T[] getTopNByTimSort(T[] ts, int n, String fieldName, int sort) {
        Objects.requireNonNull(ts);
        if (ts.length == 0 || sort * sort != 1)
            return null;

        try {
            String fieldGetterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method method = ts[0].getClass().getDeclaredMethod(fieldGetterName);
            Arrays.parallelSort(ts, Comparators.getComparator(method, sort));

            T topNData[];
            int l = ts.length;
            if (l >= n) {
                topNData = (T[]) Array.newInstance(ts.getClass().getComponentType(), n);
                System.arraycopy(ts, 0, topNData, 0, n);
            } else topNData = ts;

            return topNData;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return (T[]) Array.newInstance(ts.getClass().getComponentType(), 0);
    }

    protected static class Comparators {
        public static <T> Comparator<T> getComparator(Method _method, int sort) {
            return (t1, t2) -> {
                try {
                    int compareResult = ((Comparable) _method.invoke(t1)).compareTo(_method.invoke(t2));
                    if (sort == -1) {//DESC
                        return ~compareResult;
                    } else {//ASC
                        return compareResult;
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                return 0;
            };
        }
    }

}
