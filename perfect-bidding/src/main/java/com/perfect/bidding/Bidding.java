package com.perfect.bidding;

import com.groot.webmagic.BaiduKeywordScheduler;
import com.groot.webmagic.BaiduRankPipeline;
import com.groot.webmagic.BaiduSearchResultProcessor;
import com.groot.webmagic.CurlDownload;
import com.perfect.bidding.core.JobBoostrap;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Arrays;

/**
 * Created by vbzer_000 on 2014/9/24.
 */
public class Bidding {

    public static void main(String[] args) {
        String xml = "bidding.xml";

        if (args.length == 1) {
            xml = args[0];
        }

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(xml);

        JobBoostrap jobBoostrap = context.getBean(JobBoostrap.class);


        Spider.create(new BaiduSearchResultProcessor()).setDownloader(new CurlDownload()).setScheduler(BaiduKeywordScheduler.getInstance()).setPipelines(Arrays.asList(new Pipeline[]{new BaiduRankPipeline()})).setExitWhenComplete(false).runAsync();

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
