package com.perfect.bdlogin;

import com.perfect.app.bdlogin.core.BaiduSearchPageUtils;
import com.perfect.base.JUnitBaseTest;
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

/**
 * Created by baizz on 2014-12-11.
 */
public class BaiduLoginTest extends JUnitBaseTest {

    @Resource
    private CookieService cookieService;

    @Test
    public void test() {
        List<String> list = new ArrayList<>(Arrays.asList("淘宝双12", "冬装", "书包", "手套", "风衣", "电饭锅", "充电宝", "精品女装"));
        CookieDTO cookieDTO = cookieService.takeOne();
        for (int i = 0, s = list.size(); i < s; i++) {
            try {
                String html = BaiduSearchPageUtils.getBaiduSearchPage(cookieDTO.getCookie(), list.get(i), 1000);
                if (html.length() < 200) {
                    System.out.println(html);
                }
                FileWriter file = new FileWriter("/home/baizz/data/preview" + Integer.toString(i) + ".html");
                PrintWriter pw = new PrintWriter(file);
                pw.write(html);
                Thread.sleep(3000);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
