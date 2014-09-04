package com.perfect.app.keyword.service;

import com.perfect.entity.LexiconEntity;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.utils.ExcelUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Created by baizz on 2014-8-30.
 */
@Service("importLexiconExcelService")
public class ImportLexiconExcelService {

    private static final String SUFFIX = "template/keyword.xml";

    private static String trade;

    public void importExcel() {
        URL url = MethodHandles.lookup().lookupClass().getClassLoader().getResource("");
        String templatePath = "";
        if (url != null) {
            templatePath = url.getPath() + SUFFIX;
            templatePath = templatePath.substring(1);
        }

        Map<String, List<LexiconEntity>> map = ExcelUtils
                .readExcel("xlsxPath", templatePath, LexiconEntity.class);
        List<LexiconEntity> list = new ArrayList<>();

        for (Map.Entry<String, List<LexiconEntity>> entry : map.entrySet()) {
            list = entry.getValue();
            if (trade == null || "".equals(trade)) {
                trade = entry.getKey();
            }
        }

        trade = trade.substring(0, trade.indexOf("."));
        trade = trade.substring(0, trade.length() - 2);
//        System.out.println("list.size() = " + list.size());

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
//                System.out.println("=======================" + first);
            } else {
                int middle = (last - first) / 2;
                LexiconTask task1 = new LexiconTask(entityList, first, middle + first);
                LexiconTask task2 = new LexiconTask(entityList, middle + first + 1, last);
                invokeAll(task1, task2);
            }
        }
    }
}