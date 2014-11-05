package com.perfect.crawl;

import com.perfect.entity.CreativeSourceEntity;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.CollectorPipeline;
import us.codecraft.webmagic.pipeline.ResultItemsCollectorPipeline;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baizz on 2014-11-5.
 */
public class CrawlAmazon {

    private static String amazonTemplate = "http://www.amazon.cn/s/ref=sr_st_popularity-rank?keywords=%s&sort=popularity-rank";

    public static void main(String[] args) throws Exception {
        String inputWord = java.net.URLEncoder.encode("冬装", "UTF-8");
        String url = String.format(amazonTemplate, inputWord);
        Request request = new Request();
        request.setUrl(url);
        request.setMethod(HttpConstant.Method.GET);

        //PhantomDownloader
        PhantomDownloader phantomDownloader = new PhantomDownloader().setRetryNum(3);

        //pipeline
        CollectorPipeline<ResultItems> collectorPipeline = new ResultItemsCollectorPipeline();

        Spider.create(new AmazonPageProcessor())
//                .startRequest(Arrays.asList(request))
                .addUrl(url)
                .setDownloader(phantomDownloader)
                .addPipeline(collectorPipeline)
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
