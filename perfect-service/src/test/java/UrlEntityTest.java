import com.perfect.api.baidu.BaiduPreviewHelper;
import com.perfect.api.baidu.BaiduPreviewHelperFactory;
import com.perfect.commons.context.ApplicationContextHelper;
import com.perfect.dao.FarmDAO;
import com.perfect.entity.sys.UrlEntity;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

/**
 * Created by vbzer_000 on 2014/9/24.
 */
@SpringApplicationContext({"spring.xml"})
public class UrlEntityTest extends UnitilsJUnit4 {

    @SpringBeanByType
    private FarmDAO farmDAO;

    @SpringBeanByType
    private ApplicationContextHelper applicationContextHelper;

    @Test
    public void insert() {
        UrlEntity urlEntity = new UrlEntity();

        urlEntity.setIdle(true);
        urlEntity.setRequest("curl 'http://fengchao.baidu.com/nirvana/request.ajax?path=GET/Live' -H 'Accept-Language: zh-CN' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8' -H 'Connection: keep-alive' -H 'Origin: http://fengchao.baidu.com' -H 'Accept-Encoding: gzip,deflate' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Maxthon/4.4.1.5000 Chrome/30.0.1599.101 Safari/537.36' -H 'Cookie: uc_login_unique=634ae7bef3dc75560f57ff5638fd0785; MSA_WH=389_610; BDUSS=d3TUZFSk9IT3RySHdDc1dGUU1GOG85WE9LSUtBYnN6Y2pSQkhNdjluT1Q4RWxVQUFBQUFBJCQAAAAAAAAAAAEAAAAKQCABemVyb2Nvb2x5cwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAJNjIlSTYyJUfj; ATS_PASS=0; BAIDUID=4A50BB8E6DBADEB73127AA4F029AAA8A:FG=1; SFSSID=0bb774452dc216826c3289476737b070; SIGNIN_UC=70a2711cf1d3d9b1a82d2f87d633bd8a01617132511; __cas__st__3=d4e62ff6b5b8fe18ee75cd1d1907ef743df9d0c4e80a3e350fce568ae9ace17450ffcde61fdc800ac260d038; __cas__id__3=7001963; __cas__rn__=161713251; lsv=bdda4827ea394787; SAMPLING_USER_ID=7001963' -H 'Host: fengchao.baidu.com' -H 'Referer: http://fengchao.baidu.com/nirvana/main.html?userid=7001963&castk=42a5bop76e3b67643d285' -H 'DNT: 1' -H 'Content-Type: application/x-www-form-urlencoded' --data 'path=GET%2FLive&params=%7B%22device%22%3A1%2C%22keyword%22%3A%22test%22%2C%22area%22%3A0%2C%22pageNo%22%3A0%7D&userid=7001963&token=d4e62ff6b5b8fe18ee75cd1d1907ef743df9d0c4e80a3e350fce568ae9ace17450ffcde61fdc800ac260d038' --compressed");

        farmDAO.save(urlEntity);

    }

    @Test
    public void run() {

        BaiduPreviewHelperFactory factory = (BaiduPreviewHelperFactory) applicationContextHelper.getBeanByClass(BaiduPreviewHelperFactory.class);

        BaiduPreviewHelper helper = factory.createInstance(null);


        for (String key : new String[]{"房地产", "二手房", "婚博会","女装","服饰","哪里的婚纱照便宜"}) {
            new Thread(new KeyRunner(key)).start();

//            for (BaiduPreviewHelper.PreviewData previewData : dataList) {
//                System.out.println("previewData = " + previewData.getLeft());
//                System.out.println("previewData = " + previewData.getRight());
//            }
        }

        while (true){
            try {
                Thread.sleep(10000000000000000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    class KeyRunner implements Runnable {

        private String keyword;

        public KeyRunner(String key) {
            this.keyword = key;
        }

        @Override
        public void run() {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
//                List<BaiduSpiderHelper.PreviewData> dataList = BaiduSpiderHelper.crawl(keyword, 1);
            }
        }
    }

    @Test
    public void takeOne() {


        TestR test = new TestR("1", farmDAO);

        TestR test1 = new TestR("2", farmDAO);

        TestR test2 = new TestR("3", farmDAO);

        new Thread(test).start();
        new Thread(test1).start();
        new Thread(test2).start();

        try {
            Thread.sleep(50000000000000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class TestR implements Runnable {

        private final String id;
        private final FarmDAO farmDAO;

        public TestR(String id, FarmDAO farmDAO) {
            this.id = id;

            this.farmDAO = farmDAO;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                UrlEntity entity = null;
                try {
                    while (true) {
                        Thread.sleep((int) (Math.random() * 10) * 1000);
                        entity = farmDAO.takeOne();
                        if (entity == null) {
                            System.out.println("Sleep" + id);
                            Thread.sleep(500);

                            continue;
                        }
                        System.out.println(id + " OK , I got one " + entity.getId());

                        Thread.sleep(3000);

                        System.out.println(id + " Over , I return it");

                        break;
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (entity != null)
                        farmDAO.returnOne(entity);
                }
            }
        }
    }
}
