package com.perfect.crawl;

import com.perfect.crawl.pageprocessor.AmazonPageProcessor;
import com.perfect.entity.CreativeSourceEntity;
import com.perfect.utils.excel.XSSFSheetHandler;
import com.perfect.utils.excel.XSSFUtils;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.CollectorPipeline;
import us.codecraft.webmagic.pipeline.ResultItemsCollectorPipeline;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by baizz on 2014-11-5.
 */
public class CrawlAmazon {

    private static String amazonTemplate = "http://www.amazon.cn/s/ref=sr_st_popularity-rank?keywords=%s&sort=popularity-rank";

    public static void main(String[] args) throws Exception {

        Path file = Paths.get("/home/baizz/文档/SEM/创意片段采集关键词&网址/创意片段采集-关键词.xlsx");
        final Map<Integer, List<String>> keywordMap = new LinkedHashMap<>();
        XSSFUtils.read(file, new XSSFSheetHandler() {
            @Override
            protected void rowMap(int sheetIndex, int rowIndex, List<Object> row) {
                //只读取amazon的关键词
                if (sheetIndex == 6) {
                    if (!row.isEmpty() && rowIndex > 0) {
                        List<String> keywordList = new ArrayList<>();
                        for (int i = 1; i < row.size(); i++) {
                            String keyword = (String) row.get(i);
                            keywordList.add(keyword);
                        }
                        keywordMap.put(keywordMap.size() + 1, keywordList);
                    }
                }
            }
        });

        RequestDelayedTask requestTask = new RequestDelayedTask();

        int siteCode = WebSiteConstant.AMAZON.getCode();
        //add task
        for (Map.Entry<Integer, List<String>> entry : keywordMap.entrySet()) {
            //
            if (requestTask.getTaskQuantity() == 5) {
                break;
            }
            //
            Map<String, Object> tmpKeywordMap = new HashMap<>();
            tmpKeywordMap.put(HttpURLHandler.siteCode, siteCode);
            tmpKeywordMap.put(HttpURLHandler.keyword, entry.getValue());
            requestTask.addTask(new RequestDelayedTask.DelayedTask(/*entry.getKey() << */1, tmpKeywordMap));
        }
        requestTask.run();

        List<Request> requestList = requestTask.getRequestList();

        runCrawl(requestList);
    }

    protected static void runCrawl(List<Request> requestList) {
        //PhantomDownloader
        PhantomDownloader phantomDownloader = new PhantomDownloader().setRetryNum(3);

        //pipeline
        CollectorPipeline<ResultItems> collectorPipeline = new ResultItemsCollectorPipeline();

        Spider.create(new AmazonPageProcessor())
                .startRequest(requestList)
                .setDownloader(phantomDownloader)
                .addPipeline(collectorPipeline)
                .thread((Runtime.getRuntime().availableProcessors() - 1) << 1)
                .run();

        List<ResultItems> resultItemsList = collectorPipeline.getCollected();
        List<CreativeSourceEntity> creativeList = new ArrayList<>();
        for (ResultItems resultItems : resultItemsList) {
            List<CreativeSourceEntity> creativeSourceEntityList = resultItems.get("creatives");
            if (creativeSourceEntityList != null && !creativeSourceEntityList.isEmpty())
                creativeList.addAll(creativeSourceEntityList);

            for (CreativeSourceEntity entity : creativeSourceEntityList) {
                System.out.println(entity.getKeyword() + ", " + entity.getTitle() + ", " + entity.getHtml());
            }
        }
    }
}
