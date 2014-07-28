package com.perfect.mongodb.dao.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.KeywordQualityDAO;
import com.perfect.entity.KeywordRealTimeDataVOEntity;
import com.perfect.mongodb.utils.BaseMongoTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by baizz on 2014-7-24.
 */
@Repository("KeywordQualityDAO")
public class KeywordQualityDAOImpl implements KeywordQualityDAO {

    private static final String logName = "-KeywordRealTimeData-log-";

    private String currUserName = AppContext.getUser().toString();

    private MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(currUserName);

    private Class<KeywordRealTimeDataVOEntity> _class = KeywordRealTimeDataVOEntity.class;

    private Map<String, KeywordRealTimeDataVOEntity> map;

    private Method getMethod;

    private int topN;

    @Override
    public KeywordRealTimeDataVOEntity[] find(String _startDate, String _endDate, String fieldName, int limit) {
        map = new ConcurrentHashMap<>();
        this.topN = limit;
        currUserName = currUserName.substring(0, 1).toUpperCase() + currUserName.substring(1, currUserName.length());
        Date startDate = null, endDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        boolean isLoadYesterdayData = false;
        if (_startDate != null) {
            Assert.notNull(_endDate, "_endDate must not be null!");
        }
        if (_endDate != null) {
            Assert.notNull(_startDate, "_startDate must not be null!");
        }
        if (_startDate == null && _endDate == null) {
            isLoadYesterdayData = true;
            //昨天的时间
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.add(Calendar.DATE, -1);
            startDate = cal.getTime();
            endDate = cal.getTime();
        } else {
            try {
                startDate = sdf.parse(_startDate);
                endDate = sdf.parse(_endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        List<KeywordRealTimeDataVOEntity> list;
        Map<String, KeywordRealTimeDataVOEntity> map = null;
        List<String> names = new ArrayList<>();     //待查询的collectionName

        if (isLoadYesterdayData) {
            String name = currUserName + logName + _startDate;
            names.add(name);
        } else {
            if (_startDate.equals(_endDate)) {
                //查询的是某一天的数据
                String name = currUserName + logName + _startDate;
                names.add(name);
            } else {
                //查询的是某一个时间段的数据
                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(startDate);
                while (cal1.getTime().getTime() <= endDate.getTime()) {
                    cal1.setTime(startDate);
                    names.add(currUserName + logName + sdf.format(cal1.getTime()));
                    cal1.add(Calendar.DATE, 1);
                    startDate = cal1.getTime();
                }

            }

        }

        //查询, 去重, 求和
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        try {
            QueryTask task1 = new QueryTask(names, 0, names.size());
            Future<List<KeywordRealTimeDataVOEntity>> voResult = forkJoinPool.submit(task1);
            list = voResult.get();
            if (list.size() == 0)
                return null;

            CalculateTask task2 = new CalculateTask(list, 0, list.size());
            Future<Map<String, KeywordRealTimeDataVOEntity>> result = forkJoinPool.submit(task2);
            map = result.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            forkJoinPool.shutdown();
        }

        //点击率和平均点击价格
        DecimalFormat df = new DecimalFormat("#.00");
        for (Map.Entry<String, KeywordRealTimeDataVOEntity> entry : map.entrySet()) {
            KeywordRealTimeDataVOEntity vo = entry.getValue();
            Double cost = vo.getCost();
            Double ctr = (vo.getClick() + 0.) / vo.getImpression();
            Double cpc = 0.;
            cost = Double.valueOf(df.format(cost));
            ctr = Double.valueOf(df.format(ctr));
            if (vo.getClick() > 0)
                cpc = vo.getCost() / vo.getClick();
            cpc = Double.valueOf(df.format(cpc));
            vo.setCost(cost);
            vo.setCtr(ctr);
            vo.setCpc(cpc);
            entry.setValue(vo);
        }

        List<KeywordRealTimeDataVOEntity> list1 = new ArrayList<>(map.values());
        KeywordRealTimeDataVOEntity[] topNData = TopN(list1.toArray(new KeywordRealTimeDataVOEntity[list1.size()]), topN, fieldName);

        return topNData;
    }

    /**
     * TopN算法
     *
     * @param objects
     * @param n
     * @param fieldName
     */
    @SuppressWarnings("unchecked")
    private KeywordRealTimeDataVOEntity[] TopN(KeywordRealTimeDataVOEntity[] objects, int n, String fieldName) {
        try {
            StringBuilder fieldGetterName = new StringBuilder("get");
            fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
            this.getMethod = _class.getDeclaredMethod(fieldGetterName.toString());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        //TopN数组
        KeywordRealTimeDataVOEntity[] topNData = Arrays.copyOf(objects, n);

        //采用快速排序
        quickSort(topNData, 0, topNData.length - 1);

        //遍历剩余部分
        try {
            for (int i = n, l = objects.length; i < l; i++) {
                if (((Comparable) getMethod.invoke(objects[i])).compareTo(getMethod.invoke(topNData[n - 1])) == 1) {
                    topNData[n - 1] = objects[i];
                    quickSort(topNData, 0, topNData.length - 1);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
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
    private void quickSort(KeywordRealTimeDataVOEntity arr[], int low, int high) {
        int l = low;
        int h = high;

        if (l >= h)
            return;

        //确定指针方向的逻辑变量
        boolean transfer = true;

        try {
            while (l != h) {
                if (((Comparable) getMethod.invoke(arr[l])).compareTo(getMethod.invoke(arr[h])) == -1) {
                    //交换数据
                    KeywordRealTimeDataVOEntity tempObj = arr[l];
                    arr[l] = arr[h];
                    arr[h] = tempObj;
                    //决定下标移动, 还是上标移动
                    transfer = (transfer == true) ? false : true;
                }

                //将指针向前或者向后移动
                if (transfer)
                    h--;
                else
                    l++;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //将数组分开两半, 确定每个数字的正确位置
        l--;
        h++;
        quickSort(arr, low, l);
        quickSort(arr, h, high);
    }

    private class QueryTask extends RecursiveTask<List<KeywordRealTimeDataVOEntity>> {
        private static final int threshold = 4;

        private int first;
        private int last;
        private List<String> collectionNameList;

        QueryTask(List<String> collectionNameList, int first, int last) {
            this.first = first;
            this.last = last;
            this.collectionNameList = collectionNameList;
        }

        @Override
        protected List<KeywordRealTimeDataVOEntity> compute() {
            List<KeywordRealTimeDataVOEntity> voList = new ArrayList<>();
            boolean stat = (last - first) < threshold;
            if (stat) {
                for (int i = first; i < last; i++) {
                    voList.addAll(mongoTemplate.findAll(_class, collectionNameList.get(i)));
                }
            } else {
                int middle = (first + last) / 2;
                QueryTask task1 = new QueryTask(collectionNameList, first, middle);
                QueryTask task2 = new QueryTask(collectionNameList, middle, last);

                task1.fork();
                task2.fork();

                voList.addAll(task1.join());
                voList.addAll(task2.join());
            }
            return voList;
        }
    }

    private class CalculateTask extends RecursiveTask<Map<String, KeywordRealTimeDataVOEntity>> {

        private static final int threshold = 100;

        private int first;
        private int last;
        private List<KeywordRealTimeDataVOEntity> list;

        CalculateTask(List<KeywordRealTimeDataVOEntity> list, int first, int last) {
            this.first = first;
            this.last = last;
            this.list = list;
        }

        @Override
        protected Map<String, KeywordRealTimeDataVOEntity> compute() {
            boolean stat = (last - first) < threshold;
            if (stat) {
                for (int i = first; i < last; i++) {
                    KeywordRealTimeDataVOEntity vo = list.get(i);
                    String keywordId = vo.getKeywordId().toString();
                    KeywordRealTimeDataVOEntity _vo = map.get(keywordId);
                    if (_vo != null) {
                        _vo.setImpression(_vo.getImpression() + vo.getImpression());
                        _vo.setClick(_vo.getClick() + vo.getClick());
                        _vo.setCtr(0.);
                        _vo.setCost(_vo.getCost() + vo.getCost());
                        _vo.setCpc(0.);
                        _vo.setPosition(_vo.getPosition() + vo.getPosition());
                        _vo.setConversion(_vo.getConversion() + vo.getConversion());
                        map.put(keywordId, _vo);
                    } else {
                        map.put(keywordId, vo);
                    }
                }
            } else {
                //拆分
                int middle = (first + last) / 2;
                CalculateTask task1 = new CalculateTask(list, first, middle);
                CalculateTask task2 = new CalculateTask(list, middle, last);

                //执行
                task1.fork();
                task2.fork();

                //合并
                map.putAll(task1.join());
                map.putAll(task2.join());
            }
            return map;
        }

    }

}
