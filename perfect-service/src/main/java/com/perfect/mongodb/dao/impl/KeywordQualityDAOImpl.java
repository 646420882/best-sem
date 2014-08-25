package com.perfect.mongodb.dao.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.KeywordQualityDAO;
import com.perfect.entity.KeywordReportEntity;
import com.perfect.mongodb.base.BaseMongoTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Created by baizz on 2014-07-24.
 */
@Repository("KeywordQualityDAO")
public class KeywordQualityDAOImpl implements KeywordQualityDAO {

    @Override
    public List<KeywordReportEntity> find(String _startDate, String _endDate) {
        Date startDate = new Date(), endDate = new Date();
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

        List<KeywordReportEntity> list = new ArrayList<>();
        List<String> names = new ArrayList<>();     //待查询的collectionName

        if (isLoadYesterdayData) {
            String name = _startDate + "-keyword";
            names.add(name);
        } else {
            if (_startDate.equals(_endDate)) {
                //查询的是某一天的数据
                String name = _startDate + "-keyword";
                names.add(name);
            } else {
                //查询的是某一个时间段的数据
                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(startDate);
                while (cal1.getTime().getTime() <= endDate.getTime()) {
                    cal1.setTime(startDate);
                    names.add(sdf.format(cal1.getTime()) + "-keyword");
                    cal1.add(Calendar.DATE, 1);
                    startDate = cal1.getTime();
                }

            }

        }

        //查询
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        try {
            String userName = AppContext.getUser();
            QueryTask task = new QueryTask(userName,names, 0, names.size());
            Future<List<KeywordReportEntity>> voResult = forkJoinPool.submit(task);
            list = voResult.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            forkJoinPool.shutdown();
        }
        return list;
    }

    class QueryTask extends RecursiveTask<List<KeywordReportEntity>> {

        private static final int threshold = 4;
        private final String userName;

        private int first;
        private int last;
        private List<String> collectionNameList;

        QueryTask(String userName, List<String> collectionNameList, int first, int last) {
            this.first = first;
            this.last = last;
            this.collectionNameList = collectionNameList;
            this.userName = userName;
        }

        @Override
        protected List<KeywordReportEntity> compute() {
            AppContext.setUser(userName);

            List<KeywordReportEntity> voList = new ArrayList<>();
            boolean status = (last - first) < threshold;
            if (status) {
                MongoTemplate mongoTemplate = BaseMongoTemplate.getUserReportMongo();
                for (int i = first; i < last; i++) {
                    voList.addAll(mongoTemplate.findAll(KeywordReportEntity.class, collectionNameList.get(i)));
                }
            } else {
                int middle = (first + last) / 2;
                QueryTask task1 = new QueryTask(userName, collectionNameList, first, middle);
                QueryTask task2 = new QueryTask(userName, collectionNameList, middle, last);
                task1.fork();
                task2.fork();
                voList.addAll(task1.join());
                voList.addAll(task2.join());
            }
            return voList;
        }
    }
}
