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
 * @since 0.1.0
 */
public class PhantomDownloader extends AbstractDownloader {

    private static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private int threadNum;

    @Override
    public Page download(Request request, Task task) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("download: " + request.getUrl());
            }
            String url = request.getUrl();
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("phantomjs /home/baizz/develop/phantomJS/crawl.js " + url);
            InputStream is = process.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line).append("\n");
            }
            String content = stringBuffer.toString();
            Page page = new Page();
            page.setRawText(content);
            page.setUrl(new PlainText(request.getUrl()));
            page.setRequest(request);
            page.setStatusCode(HttpStatus.SC_OK);
            return page;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setThread(int threadNum) {
        this.threadNum = threadNum;
    }
}
