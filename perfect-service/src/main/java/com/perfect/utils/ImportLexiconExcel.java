package com.perfect.utils;

import com.perfect.entity.LexiconEntity;
import com.perfect.mongodb.base.BaseMongoTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Created by baizz on 2014-8-19.
 */
public class ImportLexiconExcel {

    private static String trade;

    public static void main(String[] args) {
        ExcelUtils<LexiconEntity> entityExcelUtils = new ExcelUtils<>();
        Map<String, List<LexiconEntity>> map = entityExcelUtils
                .readExcel("/home/baizz/文档/五大行业词包20140808/电商行业.xlsx", "/home/baizz/keyword.xml", LexiconEntity.class);
        List<LexiconEntity> list = new ArrayList<>();
        for (Map.Entry<String, List<LexiconEntity>> entry : map.entrySet()) {
            list = entry.getValue();
            trade = entry.getKey();
        }
        trade = trade.substring(0, trade.indexOf("."));

        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() + 1);
        try {
            LexiconTask task = new LexiconTask(list, 0, list.size() - 1);
            pool.invoke(task);
        } finally {
            pool.shutdown();
        }

    }

    static class LexiconTask extends RecursiveAction {

        private int first;
        private int last;
        private List<LexiconEntity> entityList;

        LexiconTask(List<LexiconEntity> entityList, int first, int last) {
            this.entityList = entityList;
            this.first = first;
            this.last = last;
        }

        @Override
        protected void compute() {
            if (last - first < 3_000) {
                List<LexiconEntity> list = new ArrayList<>();
                for (int i = first; i <= last; i++) {
                    LexiconEntity entity = entityList.get(i);
                    entity.setTrade(trade);
                    list.add(entity);
                }
                MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
                mongoTemplate.insertAll(list);
            } else {
                int middle = (last - first) / 2;
                LexiconTask task1 = new LexiconTask(entityList, first, middle + first);
                LexiconTask task2 = new LexiconTask(entityList, middle + first + 1, last);
                invokeAll(task1, task2);
            }
        }
    }
}
