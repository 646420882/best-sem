package com.perfect.mongodb.dao.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.KeywordQualityDAO;
import com.perfect.entity.KCRealTimeDataEntity;
import com.perfect.mongodb.utils.BaseMongoTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Created by baizz on 2014-07-24.
 */
@Repository("KeywordQualityDAO")
public class KeywordQualityDAOImpl implements KeywordQualityDAO {

    private String currUserName = AppContext.getUser().toString();

    private MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate("user_perfect_report");

    private Class<KCRealTimeDataEntity> _class = KCRealTimeDataEntity.class;

    private Method getMethod;

    private int sort = -1;  //默认降序, 获取前n条数据

    private int topN;

    @Override
    public KCRealTimeDataEntity[] find(String _startDate, String _endDate, String fieldName, int limit, int sort) {
        this.sort = sort;
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

        List<KCRealTimeDataEntity> list;
        Map<String, KCRealTimeDataEntity> map = null;
        List<String> names = new ArrayList<>();     //待查询的collectionName

        if (isLoadYesterdayData) {
            String name = _startDate + "-KC";
            names.add(name);
        } else {
            if (_startDate.equals(_endDate)) {
                //查询的是某一天的数据
                String name = _startDate + "-KC";
                names.add(name);
            } else {
                //查询的是某一个时间段的数据
                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(startDate);
                while (cal1.getTime().getTime() <= endDate.getTime()) {
                    cal1.setTime(startDate);
                    names.add(sdf.format(cal1.getTime()) + "-KC");
                    cal1.add(Calendar.DATE, 1);
                    startDate = cal1.getTime();
                }

            }

        }

        //查询, 去重, 求和
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        try {
            QueryTask task1 = new QueryTask(names, 0, names.size());
            Future<List<KCRealTimeDataEntity>> voResult = forkJoinPool.submit(task1);
            list = voResult.get();
            if (list.size() == 0)
                return null;

            CalculateTask task2 = new CalculateTask(list, 0, list.size());
            Future<Map<String, KCRealTimeDataEntity>> result = forkJoinPool.submit(task2);
            map = result.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            forkJoinPool.shutdown();
        }

        //点击率和平均点击价格
        //DecimalFormat df = new DecimalFormat("#.00");
        for (Map.Entry<String, KCRealTimeDataEntity> entry : map.entrySet()) {
            KCRealTimeDataEntity vo = entry.getValue();
            Double cost = vo.getCost();
            Double ctr = (vo.getClick() + 0.) / vo.getImpression();
            Double cpc = 0.;
            cost = new BigDecimal(cost).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            ctr = new BigDecimal(ctr * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            if (vo.getClick() > 0)
                cpc = vo.getCost() / vo.getClick();
            cpc = new BigDecimal(cpc).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            vo.setCost(cost);
            vo.setCtr(ctr);
            vo.setCpc(cpc);
            entry.setValue(vo);
        }

        List<KCRealTimeDataEntity> list1 = new ArrayList<>(map.values());
        KCRealTimeDataEntity[] topNData = topN(list1.toArray(new KCRealTimeDataEntity[list1.size()]), topN, fieldName);
        return topNData;
    }

    /**
     * topN算法
     *
     * @param objects
     * @param n
     * @param fieldName
     */
    @SuppressWarnings("unchecked")
    private KCRealTimeDataEntity[] topN(KCRealTimeDataEntity[] objects, int n, String fieldName) {
        try {
            StringBuilder fieldGetterName = new StringBuilder("get");
            fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
            this.getMethod = _class.getDeclaredMethod(fieldGetterName.toString());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        //TopN数组
        KCRealTimeDataEntity[] topNData = Arrays.copyOf(objects, n);

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
    private void quickSort(KCRealTimeDataEntity arr[], int low, int high) {
        int l = low;
        int h = high;

        if (l >= h)
            return;

        //确定指针方向的逻辑变量
        boolean transfer = true;

        try {
            while (l != h) {
                if (((Comparable) getMethod.invoke(arr[l])).compareTo(getMethod.invoke(arr[h])) == sort) {
                    //交换数据
                    KCRealTimeDataEntity tempObj = arr[l];
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

    private class QueryTask extends RecursiveTask<List<KCRealTimeDataEntity>> {

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
        protected List<KCRealTimeDataEntity> compute() {
            List<KCRealTimeDataEntity> voList = new ArrayList<>();
            boolean stat = (last - first) < threshold;
            if (stat) {
                for (int i = first; i < last; i++)
                    voList.addAll(mongoTemplate.findAll(_class, collectionNameList.get(i)));
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

    private class CalculateTask extends RecursiveTask<Map<String, KCRealTimeDataEntity>> {

        private static final int threshold = 100;

        private int first;
        private int last;
        private List<KCRealTimeDataEntity> list;

        CalculateTask(List<KCRealTimeDataEntity> list, int first, int last) {
            this.first = first;
            this.last = last;
            this.list = list;
        }

        @Override
        protected Map<String, KCRealTimeDataEntity> compute() {
            Map<String, KCRealTimeDataEntity> map = new HashMap<>();
            boolean stat = (last - first) < threshold;
            if (stat) {
                for (int i = first; i < last; i++) {
                    KCRealTimeDataEntity vo = list.get(i);
                    String keywordId = vo.getKeywordId().toString();
                    KCRealTimeDataEntity _vo = map.get(keywordId);
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
                int middle = (first + last) / 2;
                CalculateTask task1 = new CalculateTask(list, first, middle);
                CalculateTask task2 = new CalculateTask(list, middle, last);

                invokeAll(task1, task2);

                //map合并处理
                map.clear();
                map = mergeMap(task1.join(), task2.join());
            }
            return map;
        }

        private Map<String, KCRealTimeDataEntity> mergeMap(Map<String, KCRealTimeDataEntity> map1, Map<String, KCRealTimeDataEntity> map2) {
            Map<String, KCRealTimeDataEntity> _map = new HashMap<>();
            for (Iterator<Map.Entry<String, KCRealTimeDataEntity>> iterator1 = map1.entrySet().iterator(); iterator1.hasNext(); ) {
                KCRealTimeDataEntity vo = iterator1.next().getValue();
                for (Iterator<Map.Entry<String, KCRealTimeDataEntity>> iterator2 = map2.entrySet().iterator(); iterator2.hasNext(); ) {
                    KCRealTimeDataEntity _vo = iterator2.next().getValue();
                    if (_vo.getKeywordId().compareTo(vo.getKeywordId()) == 0) {
                        _vo.setImpression(_vo.getImpression() + vo.getImpression());
                        _vo.setClick(_vo.getClick() + vo.getClick());
                        _vo.setCtr(0.);
                        _vo.setCost(_vo.getCost() + vo.getCost());
                        _vo.setCpc(0.);
                        _vo.setPosition(_vo.getPosition() + vo.getPosition());
                        _vo.setConversion(_vo.getConversion() + vo.getConversion());
                        _map.put(_vo.getKeywordId().toString(), _vo);
                        iterator1.remove();
                        iterator2.remove();
                        break;
                    }
                }
            }

            for (Map.Entry<String, KCRealTimeDataEntity> entry : map1.entrySet()) {
                KCRealTimeDataEntity vo = entry.getValue();
                _map.put(vo.getKeywordId().toString(), vo);
            }

            for (Map.Entry<String, KCRealTimeDataEntity> entry : map2.entrySet()) {
                KCRealTimeDataEntity vo = entry.getValue();
                _map.put(vo.getKeywordId().toString(), vo);
            }

            return _map;
        }

    }

}
