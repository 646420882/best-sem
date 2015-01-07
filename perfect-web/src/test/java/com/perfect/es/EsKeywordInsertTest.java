package com.perfect.es;

import com.perfect.utils.excel.XSSFReadUtils;
import com.perfect.utils.excel.XSSFSheetHandler;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-12-17.
 */
public class EsKeywordInsertTest {

//    private static final List<Map<String, Object>> crawlWordList = new ArrayList<>();
//
//    private EsTemplate esTemplate = new EsTemplate("datakw", "keyword");
//
//
//    @Test
//    public void insert() {
//        Path file = Paths.get("/home/baizz/文档/SEM/创意片段采集关键词&网址/创意片段采集-关键词.xlsx");
//
//        try {
//            XSSFReadUtils.read(file, new XSSFSheetHandler() {
//                @Override
//                protected void rowMap(int sheetIndex, int rowIndex, List<Object> row) {
//
//                    switch (sheetIndex) {
//                        case 0:
//                            rowProcessor(sheetIndex, rowIndex, row);
//                            break;
//                        case 1:
//                            rowProcessor(sheetIndex, rowIndex, row);
//                            break;
//                        case 2:
//                            rowProcessor(sheetIndex, rowIndex, row);
//                            break;
//                        case 3:
//                            rowProcessor(sheetIndex, rowIndex, row);
//                            break;
//                        case 4:
//                            rowProcessor(sheetIndex, rowIndex, row);
//                            break;
//                        case 5:
//                            rowProcessor(sheetIndex, rowIndex, row);
//                            break;
//                        case 6:
//                            rowProcessor(sheetIndex, rowIndex, row);
//                            break;
//                        default:
//                            break;
//                    }
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (!crawlWordList.isEmpty()) {
//            esTemplate.save(crawlWordList);
//        }
//    }
//
//    @Test
//    public void insertTaobaoCrawlWord() {
//        Path file = Paths.get("/home/baizz/文档/SEM/淘宝关键词.xlsx");
//        try {
//            XSSFReadUtils.read(file, new XSSFSheetHandler() {
//                @Override
//                protected void rowMap(int sheetIndex, int rowIndex, List<Object> row) {
//                    if (!row.isEmpty() && row.size() > 2) {
//                        String category = (String) row.get(0);
//                        for (int i = 1; i < row.size(); i++) {
//                            String keyword = (String) row.get(i);
//                            Map<String, Object> source = new HashMap<>();
//                            source.put("name", keyword);
//                            source.put("category", category);
//                            source.put("utime", Instant.now().getEpochSecond());
//                            crawlWordList.add(source);
//                        }
//                    }
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (!crawlWordList.isEmpty()) {
//            esTemplate.save(crawlWordList);
//        }
//    }
//
//
//    protected static void rowProcessor(int sheetIndex, int rowIndex, List<Object> row) {
//        if (!row.isEmpty() && rowIndex > 0) {
//            String category = (String) row.get(0);
//            for (int i = 1; i < row.size(); i++) {
//                String keyword = (String) row.get(i);
//                Map<String, Object> source = new HashMap<>();
//                source.put("name", keyword);
//                source.put("category", category);
//                source.put("utime", Instant.now().getEpochSecond());
//                crawlWordList.add(source);
//            }
//        }
//    }
}
