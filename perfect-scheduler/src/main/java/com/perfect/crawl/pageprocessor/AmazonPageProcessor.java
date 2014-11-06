package com.perfect.crawl.pageprocessor;

import com.perfect.entity.CreativeSourceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by baizz on 2014-11-5.
 */
public class AmazonPageProcessor implements PageProcessor {

    private static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Site site = Site.me()
            .setDomain("www.amazon.cn")
            .setCharset("UTF-8")
            .setUserAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:33.0) Gecko/20100101 Firefox/33.0")
            .addHeader("Referer", "http://www.amazon.cn/")
            .setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        if (page.getRawText() != null)
            htmlHandler(page);
        else {
            if (logger.isInfoEnabled()) {
                logger.info("HTTP request failed: " + page.getRequest());
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    protected void htmlHandler(Page page) {
        Html html = page.getHtml();
        String inputWord = html.xpath("//form[@name='site-search']//*input[@type='text']").$("input", "value").toString();
        List<Selectable> nodes;
        List<CreativeSourceEntity> list = page.getResultItems().get("creatives");
        if (list == null) {
            list = new ArrayList<>();
        }

        if (html.xpath("//div[@id='resultsCol']").xpath("//*li[@class='s-result-item']").nodes().size() > 0) {
            nodes = html.xpath("//div[@id='resultsCol']").xpath("//*li[@class='s-result-item']").nodes();
            for (Selectable node : nodes) {
                if (list.size() == 10) {
                    break;
                }

                String creativeTitle = node.xpath("//*[@class='a-link-normal s-access-detail-page a-text-normal']")
                        .$("a", "title").toString();
                CreativeSourceEntity entity = new CreativeSourceEntity();
                entity.setHost(site.getDomain());
                entity.setKeyword(inputWord);
                entity.setTitle(creativeTitle);
                entity.setHtml(node.xpath("//*[@class='a-link-normal s-access-detail-page a-text-normal']")
                        .xpath("//a/html()").toString());
                list.add(entity);
            }

            page.putField("creatives", list);

            if (page.getResultItems().get("creatives") == null) {
                page.setSkip(true);
            }
        } else if (html.xpath("//div[@id='resultsCol']").xpath("//*div[@class='prod']").nodes().size() > 0) {
            nodes = html.xpath("//div[@id='resultsCol']").xpath("//*div[@class='prod']").nodes();
            for (Selectable node : nodes) {
                if (list.size() == 10) {
                    break;
                }

                String creativeTitle = node.xpath("//h3").$("a", "href").toString();
                try {
                    int index = creativeTitle.indexOf("/dp/");
                    if (index > 21) {
                        creativeTitle = creativeTitle.substring(21, index);
                        creativeTitle = java.net.URLDecoder.decode(creativeTitle, "UTF-8");
                    } else {
                        creativeTitle = node.xpath("//h3/a/span/text()").toString();
                    }
                    CreativeSourceEntity entity = new CreativeSourceEntity();
                    entity.setHost(site.getDomain());
                    entity.setKeyword(inputWord);
                    entity.setTitle(creativeTitle);
                    entity.setHtml(node.xpath("//h3/a/html()").toString());
                    list.add(entity);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }

            page.putField("creatives", list);

            if (page.getResultItems().get("creatives") == null) {
                page.setSkip(true);
            }
        }
    }
}
