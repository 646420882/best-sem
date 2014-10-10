package com.perfect.app.keyword.controller;

import com.perfect.entity.LexiconEntity;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.utils.DBNameUtils;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * Created by baizz on 2014-10-9.
 */
class LexiconTask extends RecursiveAction {
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
                entity.setTrade(ImportLexiconExcelController.trade);
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
