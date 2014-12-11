package com.perfect.test.app.controller;

import com.perfect.app.bdlogin.core.BaiduSearchPageUtils;
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
public class BaiduLoginTest extends JUnitBaseControllerTest {

    @Resource
    private CookieService cookieService;

    @Test
    public void test() {
        List<String> list = new ArrayList<>(Arrays.asList("淘宝双12", "冬装", "书包", "手套", "风衣", "电饭锅", "手机", "笔记本"));
        for (int i = 0, s = list.size(); i < s; i++) {
            CookieDTO cookieDTO = cookieService.takeOne();
            try {
                FileWriter file = new FileWriter("/home/baizz/data/preview" + Integer.toString(i) + ".html");
                PrintWriter pw = new PrintWriter(file);
                pw.write(BaiduSearchPageUtils.getBaiduSearchPage(cookieDTO.getCookie(), list.get(i), 1000));
                Thread.sleep(3000);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
