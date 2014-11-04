package com.perfect.crawl;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * A handler to get url
 * Created by baizz on 2014-10-28.
 *
 * @author baizz
 * @version 0.5.0
 */
class HttpURLHandler {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static String taobaoUrlTemplate = "http://s.taobao.com/search?q=%s";

    /**
     * A method to get taobao's url
     *
     * @param inputWord
     * @return
     */
    public static String getTaobaoURL(String inputWord) {
        HttpURLConnection httpURLConnection = null;
        try {
            inputWord = java.net.URLEncoder.encode(inputWord, "GBK");
            URL url = new URL(String.format(taobaoUrlTemplate, inputWord));
            httpURLConnection = (HttpURLConnection) url.openConnection();
            if (httpURLConnection.getResponseCode() == HttpStatus.SC_OK) {
                if (logger.isInfoEnabled())
                    logger.info(httpURLConnection.getResponseCode() + "-----" + httpURLConnection.getURL().toString());
                return httpURLConnection.getURL().toString() + "&sort=sale-desc";
            } else {
                //try again
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                httpURLConnection = (HttpURLConnection) url.openConnection();
                if (httpURLConnection.getResponseCode() == HttpStatus.SC_OK) {
                    if (logger.isInfoEnabled())
                        logger.info(httpURLConnection.getResponseCode() + "again-----" + httpURLConnection.getURL().toString());
                    return httpURLConnection.getURL().toString() + "&sort=sale-desc";
                }
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }

    public static List<Request> getURLRequests(List<String> keywordList) {
        List<Request> requestList = new ArrayList<>();
        for (String keyword : keywordList) {
            String url = HttpURLHandler.getTaobaoURL(keyword);
            if (url == null) {
                continue;
            }
            Request request = new Request();
            request.setUrl(url);
            request.setMethod(HttpConstant.Method.GET);
            requestList.add(request);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() + 1);
//        try {
//            HttpURLTask task = new HttpURLTask(keywordList, 0, keywordList.size() - 1);
//            Future<List<Request>> result = forkJoinPool.submit(task);
//            requestList = result.get();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        } finally {
//            forkJoinPool.shutdown();
//        }
        return requestList;
    }

    /**
     * @see com.perfect.crawl.HttpURLHandler#getURLRequests(java.util.List)
     * @deprecated
     */
    @Deprecated
    protected static class HttpURLTask extends RecursiveTask<List<Request>> {

        private int first;
        private int last;
        private List<String> keywordList;

        protected HttpURLTask(List<String> keywordList, int first, int last) {
            this.keywordList = keywordList;
            this.first = first;
            this.last = last;
        }

        @Override
        protected List<Request> compute() {
            List<Request> requestList = new ArrayList<>();
            if (last - first < 100) {
                for (int i = first; i < last; i++) {
                    Request request = new Request();
                    String url = HttpURLHandler.getTaobaoURL(keywordList.get(i));
                    request.setUrl(url);
                    request.setMethod(HttpConstant.Method.GET);
                    requestList.add(request);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (logger.isInfoEnabled())
                    logger.info("first: " + first + ", last: " + last);
            } else {
                int middle = (last - first) / 2;
                HttpURLTask task1 = new HttpURLTask(keywordList, first, middle + first);
                HttpURLTask task2 = new HttpURLTask(keywordList, middle + first + 1, last);

                invokeAll(task1, task2);

                requestList.clear();
                requestList.addAll(task1.join());
                requestList.addAll(task2.join());
            }
            return requestList;
        }
    }
}
