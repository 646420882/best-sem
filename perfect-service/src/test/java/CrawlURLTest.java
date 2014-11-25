import com.perfect.crawl.CrawlURLHandler;

/**
 * Created by baizz on 14-11-18.
 */
public class CrawlURLTest {
    public static void main(String[] args) {
        CrawlURLHandler.build().setSites("taobao", "amazon").run();
    }
}
