package com.perfect.crawl;

import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.AbstractDownloader;
import us.codecraft.webmagic.selector.PlainText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;

/**
 * Created by baizz on 2014-10-31.
 * The PhantomDownloader is designed by PhantomJS
 *
 * @author baizz
 * @version 0.5.0
 */
public class PhantomDownloader extends AbstractDownloader {

    private static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static String phantomJSPath;

    private int retryNum;
    private int threadNum;

    public PhantomDownloader() {
        PhantomDownloader.phantomJSPath = MethodHandles.lookup().lookupClass().getClassLoader().getResource("").getPath() + "phantomJs/crawl.js ";
    }

    @Override
    public Page download(Request request, Task task) {
        if (logger.isInfoEnabled()) {
            logger.info("download: " + request.getUrl());
        }
        String content = getPage(request);
        if (content.contains("HTTP request failed")) {
            for (int i = 1; i <= getRetryNum(); i++) {
                content = getPage(request);
                if (!content.contains("HTTP request failed")) {
                    break;
                }
            }
            if (content.contains("HTTP request failed")) {
                //下载页面失败后的处理
                return new Page();
            }
        }

        Page page = new Page();
        page.setRawText(content);
        page.setUrl(new PlainText(request.getUrl()));
        page.setRequest(request);
        page.setStatusCode(HttpStatus.SC_OK);
        return page;
    }

    @Override
    public void setThread(int threadNum) {
        this.threadNum = threadNum;
    }

    protected String getPage(Request request) {
        try {
            String url = request.getUrl();
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("phantomjs " + phantomJSPath + url);
            InputStream is = process.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line).append("\n");
            }
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int getRetryNum() {
        return retryNum;
    }

    public PhantomDownloader setRetryNum(int retryNum) {
        this.retryNum = retryNum;
        return this;
    }
}
