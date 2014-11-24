package com.perfect.app.controller;

import com.perfect.entity.CrawlWordEntity;
import com.perfect.dao.mongodb.base.BaseMongoTemplate;
import com.perfect.utils.DBNameUtils;
import com.perfect.utils.excel.XSSFSheetHandler;
import com.perfect.utils.excel.XSSFUtils;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-11-17.
 */
public class InsertCrawlWordController extends JUnitBaseController {

    private static final Map<Integer, String> sheetNameMap = new LinkedHashMap<Integer, String>() {{
        put(0, "lefeng&vip");
        put(1, "yhd");
        put(2, "xiu");
        put(3, "gome");
        put(4, "suning");
        put(5, "dangdang");
        put(6, "amazon");
        put(7, "taobao");
    }};

    private static final List<CrawlWordEntity> crawlWordList = new ArrayList<>();

//    private static final String[] rowName = new String[2];

    @Test
    public void insert() {
        Path file = Paths.get("/home/baizz/文档/SEM/创意片段采集关键词&网址/创意片段采集-关键词.xlsx");

        try {
            XSSFUtils.read(file, new XSSFSheetHandler() {
                @Override
                protected void rowMap(int sheetIndex, int rowIndex, List<Object> row) {

                    switch (sheetIndex) {
                        case 0:
                            rowProcessor(sheetIndex, rowIndex, row);
                            break;
                        case 1:
                            rowProcessor(sheetIndex, rowIndex, row);
                            break;
                        case 2:
                            rowProcessor(sheetIndex, rowIndex, row);
                            break;
                        case 3:
                            rowProcessor(sheetIndex, rowIndex, row);
                            break;
                        case 4:
                            rowProcessor(sheetIndex, rowIndex, row);
                            break;
                        case 5:
                            rowProcessor(sheetIndex, rowIndex, row);
                            break;
                        case 6:
                            rowProcessor(sheetIndex, rowIndex, row);
                            break;
                        default:
                            break;
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!crawlWordList.isEmpty()) {
            MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getSysDBName());
            mongoTemplate.insertAll(crawlWordList);
        }
    }

    @Test
    public void insertTaobaoCrawlWord() {
        Path file = Paths.get("/home/baizz/文档/SEM/淘宝关键词.xlsx");
        try {
            XSSFUtils.read(file, new XSSFSheetHandler() {
                @Override
                protected void rowMap(int sheetIndex, int rowIndex, List<Object> row) {
                    if (!row.isEmpty() && row.size() > 2) {
                        String category = (String) row.get(0);
                        for (int i = 1; i < row.size(); i++) {
                            String keyword = (String) row.get(i);
                            CrawlWordEntity entity = new CrawlWordEntity();
                            entity.setSite(sheetNameMap.get(7));
                            entity.setCategory(category);
                            entity.setKeyword(keyword);
                            crawlWordList.add(entity);
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!crawlWordList.isEmpty()) {
            MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getSysDBName());
            mongoTemplate.insertAll(crawlWordList);
        }
    }


    protected static void rowProcessor(int sheetIndex, int rowIndex, List<Object> row) {
        if (!row.isEmpty() && rowIndex > 0) {
//            if (rowName[0] == null && rowName[1] == null) {
//                rowName[0] = (String) row.get(0);
//            }
//            rowName[1] = (String) row.get(0);
//            boolean categoryIsNotEmpty = (rowName[1] != null) && (!"".equals(rowName[1])) && (rowName[1].trim().length() > 0);
            String category = (String) row.get(0);
            for (int i = 1; i < row.size(); i++) {
                String keyword = (String) row.get(i);
                CrawlWordEntity entity = new CrawlWordEntity();
                entity.setSite(sheetNameMap.get(sheetIndex));
//                entity.setCategory(categoryIsNotEmpty ? rowName[1] : rowName[0]);
                entity.setCategory(category);
                entity.setKeyword(keyword);
                crawlWordList.add(entity);
            }
//            if (categoryIsNotEmpty) {
//                rowName[0] = (String) row.get(0);
//            }
        }
    }
}
