package com.perfect.es;

import com.perfect.db.elasticsearch.core.EsTemplate;
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

    private static final List<Map<String, Object>> crawlWordList = new ArrayList<>();

    @Test
    public void insert() {
        Path file = Paths.get("/home/baizz/文档/SEM/创意片段采集关键词&网址/创意片段采集-关键词.xlsx");
        EsTemplate esTemplate = new EsTemplate("keyword", "e-commerce", new String[]{"name"});

        try {
            XSSFReadUtils.read(file, new XSSFSheetHandler() {
                @Override
                protected void rowMap(int sheetIndex, int rowIndex, List<Object> row) {
                    rowProcessor(sheetIndex, rowIndex, row);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!crawlWordList.isEmpty()) {
            esTemplate.save(crawlWordList);
        }
    }

    @Test
    public void insertTradeKeyword() throws Exception {
        Path file = Paths.get("/home/baizz/文档/SEM/五大行业词包20140808/金融行业.xlsx");
        EsTemplate esTemplate = new EsTemplate("keyword", "finance", new String[]{"name"});
        XSSFReadUtils.read(file, new XSSFSheetHandler() {
            @Override
            protected void rowMap(int sheetIndex, int rowIndex, List<Object> row) {
                if (!row.isEmpty() && row.size() == 3) {
                    String keyword = row.get(2).toString();
                    Map<String, Object> source = new HashMap<>();
                    source.put("name", keyword);
                    source.put("c1", row.get(0).toString());
                    source.put("c2", row.get(1).toString());
                    source.put("utime", Instant.now().getEpochSecond());
                    source.put("rc", 0);
                    source.put("rkw", new String[]{});
                    crawlWordList.add(source);
                }

            }

        });

        esTemplate.save(crawlWordList);
    }

    @Test
    public void insertTaobaoCrawlWord() {
        Path file = Paths.get("/home/baizz/文档/SEM/淘宝关键词.xlsx");
        EsTemplate esTemplate = new EsTemplate("keyword", "e-commerce", new String[]{"name"});
        try {
            XSSFReadUtils.read(file, new XSSFSheetHandler() {
                @Override
                protected void rowMap(int sheetIndex, int rowIndex, List<Object> row) {
                    if (!row.isEmpty() && row.size() > 2) {
                        String category = (String) row.get(0);
                        for (int i = 1; i < row.size(); i++) {
                            String keyword = (String) row.get(i);
                            if (keyword == null)
                                continue;
                            Map<String, Object> source = new HashMap<>();
                            source.put("name", keyword);
                            source.put("c1", category);
                            source.put("utime", Instant.now().getEpochSecond());
                            source.put("rc", 0);
                            source.put("rkw", new String[]{});
                            crawlWordList.add(source);
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!crawlWordList.isEmpty()) {
            esTemplate.save(crawlWordList);
        }
    }


    protected static void rowProcessor(int sheetIndex, int rowIndex, List<Object> row) {
        if (!row.isEmpty() && rowIndex > 0) {
            String category = (String) row.get(0);
            for (int i = 1; i < row.size(); i++) {
                String keyword = (String) row.get(i);
                if (keyword == null)
                    continue;
                Map<String, Object> source = new HashMap<>();
                source.put("name", keyword);
                source.put("c1", category);
                source.put("utime", Instant.now().getEpochSecond());
                source.put("rc", 0);
                source.put("rkw", new String[]{});
                crawlWordList.add(source);
            }
        }
    }
}
