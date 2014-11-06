package com.groot.webmagic;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.Scheduler;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by yousheng on 14/11/5.
 */
public class BaiduKeywordScheduler implements Scheduler, Constant {

    BlockingDeque<Request> queue = new LinkedBlockingDeque<>();

    private static BaiduKeywordScheduler instance = new BaiduKeywordScheduler();

    public static BaiduKeywordScheduler getInstance() {
        return instance;
    }

    private BaiduKeywordScheduler() {
    }

    public void push(String uuid, String curl, String keyword, String region) {

        assert uuid != null;
        assert curl != null;

        Request request = new Request(SITE_HOST);

        request.putExtra(REQUEST_UUID, uuid);

        request.putExtra(REQUEST_CURL, curl);
        request.putExtra(REQUEST_KEY, keyword);
        request.putExtra(REQUEST_REGION, region);

        push(request, null);
    }

    public void push(String uuid, String curl, String keyword, Integer region) {
        push(uuid,curl,keyword,region.toString());
    }

    @Override
    public void push(Request request, Task task) {
        queue.addLast(request);
    }

    @Override
    public Request poll(Task task) {
        try {
            return queue.takeFirst();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

        try {
            InputStream outputStream = Runtime.getRuntime().exec("./run.sh").getInputStream();
            File f = new File("test");
            f.setExecutable(true);
            System.out.println("process");
            byte[] bytes = new byte[200 * 1024];

            while (outputStream.read(bytes) != -1) {
                System.out.println(new String(bytes));
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
