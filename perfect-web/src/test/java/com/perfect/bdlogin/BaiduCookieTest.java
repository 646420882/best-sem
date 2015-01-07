package com.perfect.bdlogin;

import com.perfect.base.JUnitBaseTest;
import com.perfect.commons.bdlogin.BaiduSearchPageUtils;
import com.perfect.dto.CookieDTO;
import com.perfect.service.CookieService;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by baizz on 2014-12-11.
 * refactor 2015-1-7
 */
public class BaiduCookieTest extends JUnitBaseTest {

    @Resource
    private CookieService cookieService;


    @Test
    public void preview() {
        List<String> list = new ArrayList<>(Arrays.asList("iphone6", "冬装", "书包", "手套", "风衣", "电饭锅", "充电宝", "精品女装"));
        CookieDTO cookieDTO = cookieService.takeOne();
        for (int i = 0, s = list.size(); i < s; i++) {
            try {
                String html = BaiduSearchPageUtils.getBaiduSearchPage(cookieDTO.getCookie(), list.get(i), 1000);
                if (html.length() < 200) {
                    TimeUnit.SECONDS.sleep(3);
                    continue;
                }
                FileWriter file = new FileWriter("/home/baizz/data/preview" + Integer.toString(i) + ".html");
                PrintWriter pw = new PrintWriter(file);
                pw.write(html);
                TimeUnit.SECONDS.sleep(3);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        cookieService.returnOne(cookieDTO.getId());
    }

}
