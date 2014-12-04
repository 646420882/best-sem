package com.perfect.app.controller;

import com.perfect.dto.CrawlWordDTO;
import com.perfect.service.CrawlWordService;
import com.perfect.utils.excel.XSSFReadUtils;
import com.perfect.utils.excel.XSSFSheetHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

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

    private static final List<CrawlWordDTO> crawlWordList = new ArrayList<>();

    @Autowired
    private CrawlWordService crawlWordService;

    public void setCrawlWordService(@Qualifier("crawlWordService") CrawlWordService crawlWordService) {
        this.crawlWordService = crawlWordService;
    }

    @Test
    public void insert() {
        Path file = Paths.get("/home/baizz/文档/SEM/创意片段采集关键词&网址/创意片段采集-关键词.xlsx");

        try {
            XSSFReadUtils.read(file, new XSSFSheetHandler() {
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
            crawlWordService.save(crawlWordList);
        }
    }

    @Test
    public void insertTaobaoCrawlWord() {
        Path file = Paths.get("/home/baizz/文档/SEM/淘宝关键词.xlsx");
        try {
            XSSFReadUtils.read(file, new XSSFSheetHandler() {
                @Override
                protected void rowMap(int sheetIndex, int rowIndex, List<Object> row) {
                    if (!row.isEmpty() && row.size() > 2) {
                        String category = (String) row.get(0);
                        for (int i = 1; i < row.size(); i++) {
                            String keyword = (String) row.get(i);
                            CrawlWordDTO dto = new CrawlWordDTO();
                            dto.setSite(sheetNameMap.get(7));
                            dto.setCategory(category);
                            dto.setKeyword(keyword);
                            crawlWordList.add(dto);
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!crawlWordList.isEmpty()) {
            crawlWordService.save(crawlWordList);
        }
    }


    protected static void rowProcessor(int sheetIndex, int rowIndex, List<Object> row) {
        if (!row.isEmpty() && rowIndex > 0) {
            String category = (String) row.get(0);
            for (int i = 1; i < row.size(); i++) {
                String keyword = (String) row.get(i);
                CrawlWordDTO dto = new CrawlWordDTO();
                dto.setSite(sheetNameMap.get(sheetIndex));
                dto.setCategory(category);
                dto.setKeyword(keyword);
                crawlWordList.add(dto);
            }
        }
    }
}
