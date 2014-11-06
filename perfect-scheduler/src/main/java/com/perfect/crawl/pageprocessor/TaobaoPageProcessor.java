package com.perfect.crawl.pageprocessor;

import com.perfect.entity.CreativeSourceEntity;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baizz on 2014-10-27.
 */
public class TaobaoPageProcessor implements PageProcessor {

    //1.抓取网站的相关配置, 包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setDomain("s.taobao.com")
            .setCharset("GBK")
            .setUserAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:33.0) Gecko/20100101 Firefox/33.0")
            .addHeader("Referer", "http://www.taobao.com/")
            .setRetryTimes(3).setSleepTime(1000);

    @Override
    //process是定制爬虫逻辑的核心接口, 在这里编写抽取逻辑
    public void process(Page page) {
        //2.定义如何抽取页面信息, 并保存下来
        if (page.getRawText() != null)
            htmlHandler(page);
    }

    @Override
    public Site getSite() {
        return site;
    }

    protected void htmlHandler(Page page) {
        Html html = page.getHtml();
        String inputWord = html.xpath("//form[@action='/search']").css("input", "value").toString();
        List<Selectable> nodes;
        List<CreativeSourceEntity> list = page.getResultItems().get("creatives");
        if (list == null) {
            list = new ArrayList<>();
        }

        if (html.xpath("//div[@class='m-itemlist']").$("div.item").nodes().size() > 0) {
            nodes = html.xpath("//div[@class='m-itemlist']").$("div.item").nodes();
            for (Selectable node : nodes) {
                if (list.size() == 10) {
                    break;
                }
                String creativeTitle = node.xpath("//*[@class='title']/a/text()").toString();
                CreativeSourceEntity entity = new CreativeSourceEntity();
                entity.setHost(site.getDomain());
                entity.setKeyword(inputWord);
                entity.setTitle(creativeTitle);
                entity.setHtml(node.xpath("//*[@class='title']/a/html()").toString());
                list.add(entity);
            }

            page.putField("creatives", list);

            if (page.getResultItems().get("creatives") == null) {
                //skip this page
                page.setSkip(true);
            }
        } else if (html.xpath("//div[@class='product-list']").$("div.product-item").nodes().size() > 0) {
            nodes = html.xpath("//div[@class='product-list']").$("div.product-item").nodes();
            for (Selectable node : nodes) {
                if (list.size() == 10) {
                    break;
                }
                String creativeTitle = node.$("div.title").$("a", "title").toString();
                CreativeSourceEntity entity = new CreativeSourceEntity();
                entity.setHost(site.getDomain());
                entity.setKeyword(inputWord);
                entity.setTitle(creativeTitle);
                entity.setHtml(node.$("div.title").xpath("//a/html()").toString());
                list.add(entity);
            }

            page.putField("creatives", list);

            if (page.getResultItems().get("creatives") == null) {
                page.setSkip(true);
            }
        }
    }
}