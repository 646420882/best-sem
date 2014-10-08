package com.perfect.utils.lexicon;

import com.perfect.entity.LexiconEntity;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.utils.DBNameUtils;
import com.perfect.utils.ExcelUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Created by baizz on 2014-10-8.
 */
public class ImportLexiconExcel {

    private static String trade;

    public static void main(String[] args) {
        Map<String, List<LexiconEntity>> map = ExcelUtils
                .readExcel("/home/baizz/文档/五大行业词包20140808/电商行业.xlsx", "/home/baizz/keyword.xml", LexiconEntity.class);
        List<LexiconEntity> list = new ArrayList<>();
        for (Map.Entry<String, List<LexiconEntity>> entry : map.entrySet()) {
            list = entry.getValue();
            trade = entry.getKey();
        }
        trade = trade.substring(0, trade.indexOf("."));
        trade = trade.substring(0, trade.length() - 2);

        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() + 1);
        try {
            LexiconTask task = new LexiconTask(list, 0, list.size() - 1);
            pool.invoke(task);
        } finally {
            pool.shutdown();
        }

    }

    private void deleteOldLexicon(String trade) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getSysDBName());
        mongoTemplate.remove(Query.query(Criteria.where("tr").is(trade)), LexiconEntity.class);
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
                MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getSysDBName());
                mongoTemplate.insertAll(list);
                System.out.println("=======================" + first);
            } else {
                int middle = (last - first) / 2;
                LexiconTask task1 = new LexiconTask(entityList, first, middle + first);
                LexiconTask task2 = new LexiconTask(entityList, middle + first + 1, last);
                invokeAll(task1, task2);
            }
        }
    }
}
