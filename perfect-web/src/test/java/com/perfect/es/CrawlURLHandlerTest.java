package com.perfect.es;

import com.perfect.base.JUnitBaseTest;
import com.perfect.commons.crawl.CrawlURLHandler;
import com.perfect.dao.sys.CrawlWordDAO;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by baizz on 2015-1-21.
 */
public class CrawlURLHandlerTest extends JUnitBaseTest {

    @Resource
    private CrawlWordDAO crawlWordDAO;


    @Test
    public void readKeyword() {
        CrawlURLHandler urlHandler = CrawlURLHandler.build();
        urlHandler.setCrawlWordDAO(crawlWordDAO);
        urlHandler.setSites("taobao", "amazon").run();
    }
}
