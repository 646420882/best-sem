import com.groot.webmagic.BaiduKeywordScheduler;
import com.groot.webmagic.BaiduSearchResultProcessor;
import com.groot.webmagic.Constant;
import com.groot.webmagic.CurlDownload;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by yousheng on 14/11/6.
 */
public class BaiduTest implements Constant {

    public static void main(String[] args) throws InterruptedException {
        Request request = new Request("");

        request.putExtra(REQUEST_CURL,"curl 'http://fengchao.baidu.com/nirvana/request.ajax?path=nirvana/GET/Live' -H 'Cookie: SFSSID=dd8eab4173b4c333d27cc55b43dd8a53; BAIDUID=46F805AC9F4370D41740313E9400184C:FG=1; SIGNIN_UC=70a2711cf1d3d9b1a82d2f87d633bd8a01652578622; uc_login_unique=1ebce2b9b220beb533fd16cbf4a863af; __cas__st__3=a8410c56e26dc66310881a0e1dfadd69688f2c3ec141cf6a636c7792ac4b6e1f95c512205536facfb40c3b5f; __cas__id__3=7001963; __cas__rn__=165257862; SAMPLING_USER_ID=7001963' -H 'Origin: http://fengchao.baidu.com' -H 'Accept-Encoding: gzip,deflate' -H 'Accept-Language: zh-CN,zh;q=0.8' -H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/37.0.2062.120 Chrome/37.0.2062.120 Safari/537.36' -H 'Content-Type: application/x-www-form-urlencoded' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8' -H 'Cache-Control: max-age=0' -H 'Referer: http://fengchao.baidu.com/nirvana/main.html?userid=7001963&castk=b733egm7d710794c3d602' -H 'Connection: keep-alive' --data 'path=nirvana%2FGET%2FLive&params=%7B%22device%22%3A1%2C%22keyword%22%3A%22test%22%2C%22area%22%3A1%2C%22pageNo%22%3A0%7D&userid=7001963&token=a8410c56e26dc66310881a0e1dfadd69688f2c3ec141cf6a636c7792ac4b6e1f95c512205536facfb40c3b5f' --compressed");
            request.putExtra(REQUEST_KEY, null /*URLEncoder.encode("婚纱照", "utf-8")*/);
            request.putExtra(REQUEST_REGION,null);



        Spider.create(new BaiduSearchResultProcessor()).setScheduler(BaiduKeywordScheduler.getInstance()).setDownloader(new CurlDownload()).runAsync();

//        BaiduKeywordScheduler.getInstance().push("curl 'http://fengchao.baidu.com/nirvana/request.ajax?path=nirvana/GET/Live' -H 'Cookie: SFSSID=dd8eab4173b4c333d27cc55b43dd8a53; BAIDUID=46F805AC9F4370D41740313E9400184C:FG=1; SIGNIN_UC=70a2711cf1d3d9b1a82d2f87d633bd8a01652578622; uc_login_unique=1ebce2b9b220beb533fd16cbf4a863af; __cas__st__3=a8410c56e26dc66310881a0e1dfadd69688f2c3ec141cf6a636c7792ac4b6e1f95c512205536facfb40c3b5f; __cas__id__3=7001963; __cas__rn__=165257862; SAMPLING_USER_ID=7001963' -H 'Origin: http://fengchao.baidu.com' -H 'Accept-Encoding: gzip,deflate' -H 'Accept-Language: zh-CN,zh;q=0.8' -H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/37.0.2062.120 Chrome/37.0.2062.120 Safari/537.36' -H 'Content-Type: application/x-www-form-urlencoded' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8' -H 'Cache-Control: max-age=0' -H 'Referer: http://fengchao.baidu.com/nirvana/main.html?userid=7001963&castk=b733egm7d710794c3d602' -H 'Connection: keep-alive' --data 'path=nirvana%2FGET%2FLive&params=%7B%22device%22%3A1%2C%22keyword%22%3A%22test%22%2C%22area%22%3A133%2C%22pageNo%22%3A0%7D&userid=7001963&token=a8410c56e26dc66310881a0e1dfadd69688f2c3ec141cf6a636c7792ac4b6e1f95c512205536facfb40c3b5f' --compressed","婚博会","1");
        BaiduKeywordScheduler.getInstance().push(request,null);


        Thread.sleep(30000);

        BaiduKeywordScheduler.getInstance().push(request,null);

        Thread.currentThread().join();
    }
}
