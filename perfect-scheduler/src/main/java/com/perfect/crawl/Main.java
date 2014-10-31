package com.perfect.crawl;

import com.perfect.entity.CreativeSourceEntity;
import com.perfect.entity.MD5;
import com.perfect.service.CreativeSourceService;
import com.perfect.utils.excel.RowHandler;
import com.perfect.utils.excel.XSSFUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.pipeline.CollectorPipeline;
import us.codecraft.webmagic.pipeline.ResultItemsCollectorPipeline;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-10-27.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Path file = Paths.get("/home/baizz/文档/淘宝关键词.xlsx");
        final Map<Integer, List<String>> keywordMap = new LinkedHashMap<>();
        XSSFUtils.read(file, new RowHandler() {
            @Override
            protected void mapRow(int sheetIndex, int rowIndex, List<Object> row) {
                if (!row.isEmpty() && row.size() > 2 && rowIndex < 4) {
                    List<String> keywordList = new ArrayList<>();
                    for (int i = 1; i < row.size(); i++) {
                        String keyword = row.get(i).toString();
                        keywordList.add(keyword);
                    }
                    keywordMap.put(keywordMap.size() + 1, keywordList);
                }
            }
        });


        RequestDelayedTask requestTask = new RequestDelayedTask();
        //add task
        for (Map.Entry<Integer, List<String>> entry : keywordMap.entrySet()) {
            requestTask.addTask(new RequestDelayedTask.DelayedTask(entry.getKey() << 1, entry.getValue()));
        }
        requestTask.run();

        List<Request> requestList = requestTask.getRequestList();
        runCrawl(requestList);
    }

    protected static void runCrawl(List<Request> requestList) {
        //seleniumDownloader
        SeleniumDownloader seleniumDownloader = new SeleniumDownloader("/usr/bin/chromedriver");
        //PhantomDownloader
        PhantomDownloader phantomDownloader = new PhantomDownloader();

        //pipeline
        CollectorPipeline<ResultItems> collectorPipeline = new ResultItemsCollectorPipeline();

        Spider.create(new TaobaoPageProcessor())
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
                System.out.println(entity.getKeyword() + ", " + entity.getTitle());
            }
        }

//        saveToElasticsearch(creativeList);
    }

    protected static void saveToElasticsearch(List<CreativeSourceEntity> list) {
//        CreativeSourceService creativeSourceService = (CreativeSourceService) ApplicationContextHelper.getBeanByName("creativeSourceService");

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        CreativeSourceService creativeSourceService = (CreativeSourceService) applicationContext.getBean("creativeSourceService");

        for (CreativeSourceEntity entity : list) {
            String source = entity.getTitle() + entity.getHtml();

            MD5.Builder md5 = new MD5.Builder();
            md5.password(source);
            md5.salt("hello,salt");

            String code = md5.build().getMD5();

            boolean exists = creativeSourceService.exits(code);
            if (!exists) {
                entity.setId(code);
            }
        }

        if (list.isEmpty())
            return;
        creativeSourceService.save(list);
    }

}
