package com.perfect.api.baidu;

import com.google.common.io.Files;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.sms.v3.GetPreviewRequest;
import com.perfect.autosdk.sms.v3.GetPreviewResponse;
import com.perfect.commons.context.ApplicationContextHelper;
import com.perfect.dao.FarmDAO;
import com.perfect.entity.UrlEntity;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;

/**
 * Created by vbzer_000 on 2014/9/17.
 */
public class RequestHelper {

    private static final Logger logger = LoggerFactory.getLogger(RequestHelper.class);

    private int semaphoreValue;

    private static Map<String, Semaphore> accountSemaphore = new ConcurrentHashMap<>();

    private static ExecutorService crawlExecutor = Executors.newCachedThreadPool();

    @Deprecated
    private Map<String, ExecutorService> executorServiceMap = new HashMap<>();

    @Deprecated
    public GetPreviewResponse addRequest(CommonService commonService, GetPreviewRequest request) {

//        String token = commonService.getToken();
//
//        if (!executorServiceMap.containsKey(token)) {
//            executorServiceMap.put(token, Executors.newSingleThreadExecutor());
//        }
//
//        if (!accountSemaphore.containsKey(token)) {
//            accountSemaphore.put(token, new Semaphore(1));
//        }
//
//        AccountRequestSender sender = new AccountRequestSender(commonService,
//                request, accountSemaphore.get(token));
//
//
//        Future<GetPreviewResponse> future = executorServiceMap.get(token).submit(sender);
//        try {
//            return future.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

        return null;
    }


    public int getSemaphoreValue() {
        return semaphoreValue;
    }

    public void setSemaphoreValue(int semaphoreValue) {
        this.semaphoreValue = semaphoreValue;
    }

    public static Future<String> addRequest(String keyword, Integer region) {

        UrlSearchCallable crawlCallback = new UrlSearchCallable(keyword, region);
        Future<String> future = crawlExecutor.submit(crawlCallback);
        return future;
    }


//    private static class AccountRequestSender implements Callable<GetPreviewResponse> {
//
//
//        private final CommonService commonService;
//        private final GetPreviewRequest request;
//        private Semaphore semaphore;
//
//        public AccountRequestSender(CommonService commonService, GetPreviewRequest request, Semaphore semaphore) {
//            this.commonService = commonService;
//            this.request = request;
//            this.semaphore = semaphore;
//        }
//
//        @Override
//        public GetPreviewResponse call() throws Exception {
//
//            try {
//                semaphore.acquire();
//                RankService rankService = null;
//                rankService = commonService.getService(RankService.class);
//                GetPreviewResponse response = rankService.getPreview(request);
//                Thread.sleep(3000);
//                return response;
//            } catch (Exception ie) {
//
//            } finally {
//                semaphore.release();
//            }
//            return null;
//        }
//    }


    static class UrlSearchCallable implements Callable<String> {

        private static final int BUFFER_SIZE = 1024 * 20;
        private String keyword;
        private Integer region;

        public UrlSearchCallable(String keyword, Integer region) {
            this.keyword = keyword;
            this.region = region;
        }

        @Override
        public String call() throws Exception {
            UrlEntity urlEntity = null;
            FarmDAO farmDAO = (FarmDAO) ApplicationContextHelper.getBeanByName("farmDAO");
            try {
                while (true) {

                    urlEntity = farmDAO.takeOne();

                    if (urlEntity == null) {
                        Thread.sleep(500);
                        continue;
                    }
                    if (logger.isDebugEnabled()) {
                        logger.debug("+++++ " + keyword + " obtains :  " + urlEntity.getId() + " " +
                                urlEntity.getFinishTime());
                    }
                    String requestStr = urlEntity.getRequest();

                    PostMethod method = PostMethodFactory.getMethod(requestStr, keyword, region, 1, 0);
                    HttpClient client = new HttpClient();

                    int code = client.executeMethod(method);
                    if (code == HttpStatus.SC_OK) {
                        InputStream is = null;
                        try {
                            is = new GZIPInputStream(method.getResponseBodyAsStream());
                            int count;
                            byte[] bytes = new byte[BUFFER_SIZE];
                            ByteArrayOutputStream out = new ByteArrayOutputStream();
                            while ((count = is.read(bytes)) != -1) {
                                out.write(bytes, 0, count);
                            }
                            Files.write(out.toByteArray(), new File(keyword + ".html"));
                            String html = out.toString();
                            if (html.length() < 5000 && html.contains("频繁")) {
                                if (logger.isWarnEnabled()) {
                                    logger.warn("===== " + keyword + " 操作过于频繁,休息10秒");
                                }
                                Thread.sleep(10000);
                                if (urlEntity != null && urlEntity.getId() != null) {
                                    urlEntity.setFinishTime(System.currentTimeMillis() + 5000);
                                    farmDAO.returnOne(urlEntity);
                                    if (logger.isWarnEnabled()) {
                                        logger.warn("----- " + keyword + " return :  " + urlEntity.getId() +
                                                " " +
                                                urlEntity.getFinishTime());
                                    }
                                }
                                continue;
                            }
                            return out.toString();
                        } catch (ZipException ze) {

                            if (logger.isWarnEnabled()) {
                                logger.warn(urlEntity.getId() + " 需要重新登录.");
                            }
                            farmDAO.delete(urlEntity.getId());
                            continue;

//                            is = method.getResponseBodyAsStream();
//                            int count;
//                            byte[] bytes = new byte[BUFFER_SIZE];
//                            ByteArrayOutputStream out = new ByteArrayOutputStream();
//                            while ((count = is.read(bytes)) != -1) {
//                                out.write(bytes, 0, count);
//                            }
//                            Files.write(out.toByteArray(), new File(keyword + ".html"));
//                            String html = out.toString();
//                            if (html.contains("redirecturl")) {
//                            }
//                            Thread.sleep(10000);
//                            continue;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Thread.sleep(5000);
                if (urlEntity != null && urlEntity.getId() != null) {
                    urlEntity.setFinishTime(System.currentTimeMillis() + 3000);
                    farmDAO.returnOne(urlEntity);
                    if (logger.isDebugEnabled()) {
                        logger.debug("----- " + keyword + " return :  " + urlEntity.getId() +
                                " " +
                                urlEntity.getFinishTime());
                    }
                }
            }
            return "";
        }
    }

//    public static void main1(String[] args) {
//        PostMethod postMethod = new PostMethod("http://fengchao.baidu.com/nirvana/request.ajax?path=GET/Live");
//        postMethod.addRequestHeader("Accept-Language", "zh-CN");
//        postMethod.addRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//        postMethod.addRequestHeader("Connection", "keep-alive");
//        postMethod.addRequestHeader("Origin", "http://fengchao.baidu.com");
//        postMethod.addRequestHeader("Accept-Encoding", "gzip,deflate");
//        postMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Maxthon/4.4.1.5000 Chrome/30.0.1599.101 Safari/537.36");
//        postMethod.addRequestHeader("Cookie", "BAIDUID=969E042AD05EC7C50FEB0F0159E19E76:FG=1; BDUSS=c4bDBMUUlaM3JPNWpPblFDT3R6Rnh4bzc3U3gzZUhaSURoWEN6bzBiMmlWa2RVQUFBQUFBJCQAAAAAAAAAAAEAAAAKQCABemVyb2Nvb2x5cwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAKLJH1SiyR9Ua0; uc_login_unique=634ae7bef3dc75560f57ff5638fd0785; MSA_WH=389_610; SFSSID=ee4e26d2ace5ff7af73dfacc0e7abce8; SIGNIN_UC=70a2711cf1d3d9b1a82d2f87d633bd8a01615376566; __cas__st__3=35d97e5b582794ccb7ff32fe058c3514209ee9bfbf19cfcf2b29b05c414bbfe7fdeb25eb8521caa6e938ad78; __cas__id__3=7001963; __cas__rn__=161537656; lsv=93f901ca1da51db8; SAMPLING_USER_ID=7001963");
//        postMethod.addRequestHeader("Host", "fengchao.baidu.com");
//        postMethod.addRequestHeader("Referer", "http://fengchao.baidu.com/nirvana/main.html?userid=7001963&castk=3ce47ua772062d1c7d354");
//        postMethod.addRequestHeader("DNT", "1");
//        postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
//
//
//        postMethod.addParameter("path", "GET%2FLive");
//        try {
//            System.out.println(URLEncoder.encode("婚纱照", "utf-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        postMethod.addParameter("params", "{\"device\":1,\"keyword\":\"%E5%A9%9A%E7%BA%B1%E7%85%A7\"," +
//                "\"area\":\"226\",\"pageNo\":0}");
//        postMethod.addParameter("userid", "7001963");
//        postMethod.addParameter("token", "35d97e5b582794ccb7ff32fe058c3514209ee9bfbf19cfcf2b29b05c414bbfe7fdeb25eb8521caa6e938ad78");
////            RequestEntity requestEntity = new StringRequestEntity
////                    ("path=GET%2FLive&params=%7B%22device%22%3A1%2C%22keyword%22%3A%22%25E5%258C%2597%25E4%25BA%25AC%22%2C%22area%22%3A%22226%22%2C%22pageNo%22%3A0%7D&userid=7001963&token=35d97e5b582794ccb7ff32fe058c3514209ee9bfbf19cfcf2b29b05c414bbfe7fdeb25eb8521caa6e938ad78", "application/x-www-form-urlencoded", "UTF-8");
////            postMethod.setRequestEntity(requestEntity);
//        postMethod.setQueryString("path=GET/Live");
//        try {
//
//            HttpClient client = new HttpClient();
//            int code = client.executeMethod(postMethod);
//
//
////            System.out.println(postMethod.getResponseBodyAsString());
//            GZIPInputStream is = new GZIPInputStream(postMethod.getResponseBodyAsStream());
//            System.out.println(code + " " + is.available());
//            StringBuilder stringBuilder = new StringBuilder();
//            int count;
//            byte[] bytes = new byte[1024];
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            while ((count = is.read(bytes)) != -1) {
//                out.write(bytes, 0, count);
//            }
////            System.out.println(out.toString());
//
//            Files.write(out.toByteArray(), new File("test.html"));
//
//            Document doc = Jsoup.parse(out.toString());
//            List<CreativeDTO> leftCreativeVOList = new LinkedList<>();
//            List<CreativeDTO> rightCreativeVOList = new LinkedList<>();
//            handleLeft(doc, leftCreativeVOList);
////            handleRight(doc, rightCreativeVOList);
//
//            BaiduPreviewHelper.PreviewData previewData = new BaiduPreviewHelper.PreviewData();
//
////            previewDatas.add(previewData);
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


//
//
//    public static void main(String[] args) {
//
//        String cmd = "curl 'http://fengchao.baidu.com/nirvana/request.ajax?path=GET/Live' -H 'Accept-Language: zh-CN'" +
//                " -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8' -H 'Connection: keep-alive' -H 'Origin: http://fengchao.baidu.com' -H 'Accept-Encoding: gzip,deflate' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Maxthon/4.4.1.5000 Chrome/30.0.1599.101 Safari/537.36' -H 'Cookie: BAIDUID=969E042AD05EC7C50FEB0F0159E19E76:FG=1; BDUSS=c4bDBMUUlaM3JPNWpPblFDT3R6Rnh4bzc3U3gzZUhaSURoWEN6bzBiMmlWa2RVQUFBQUFBJCQAAAAAAAAAAAEAAAAKQCABemVyb2Nvb2x5cwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAKLJH1SiyR9Ua0; uc_login_unique=634ae7bef3dc75560f57ff5638fd0785; MSA_WH=389_610; SFSSID=ee4e26d2ace5ff7af73dfacc0e7abce8; SIGNIN_UC=70a2711cf1d3d9b1a82d2f87d633bd8a01615376566; __cas__st__3=35d97e5b582794ccb7ff32fe058c3514209ee9bfbf19cfcf2b29b05c414bbfe7fdeb25eb8521caa6e938ad78; __cas__id__3=7001963; __cas__rn__=161537656; lsv=93f901ca1da51db8; SAMPLING_USER_ID=7001963' -H 'Host: fengchao.baidu.com' -H 'Referer: http://fengchao.baidu.com/nirvana/main.html?userid=7001963&castk=3ce47ua772062d1c7d354' -H 'DNT: 1' -H 'Content-Type: application/x-www-form-urlencoded' --data 'path=GET%2FLive&params=%7B%22device%22%3A1%2C%22keyword%22%3A%22%25E5%258C%2597%25E4%25BA%25AC%22%2C%22area%22%3A%22226%22%2C%22pageNo%22%3A0%7D&userid=7001963&token=35d97e5b582794ccb7ff32fe058c3514209ee9bfbf19cfcf2b29b05c414bbfe7fdeb25eb8521caa6e938ad78' --compressed";
//
////        String data = null;
////
////        data = cmd.substring(cmd.indexOf("--data "), cmd.lastIndexOf("'")).replace("--data ", "").replaceAll("'", "");
////        try {
////            data = URLDecoder.decode(data, "utf-8");
////            for (String param : data.split("&")) {
////                System.out.println("param = " + param);
////                String[] keyValue = param.split("=");
////
////            }
////        } catch (UnsupportedEncodingException e) {
////            e.printStackTrace();
////        }
////        System.out.println("data = " + data);
//
//        String cookie = cmd.substring(cmd.indexOf("Referer"), cmd.lastIndexOf("'"));
//        cookie = cookie.replace("Referer: ", "");
//        cookie = cookie.substring(0, cookie.indexOf("'"));
//        System.out.println(cookie);
//    }
//
//
//    public String getValue(String cmd, String key) {
//        String value = cmd.substring(cmd.indexOf(key), cmd.lastIndexOf("'"));
//        value = value.replace(key + ": ", "");
//        value = value.substring(0, value.indexOf("'"));
//        return value;
//    }
//
//    public String getData(String cmd) {
//
//        String data = null;
//
//        data = cmd.substring(cmd.indexOf("--data "), cmd.lastIndexOf("'"));
//
//        System.out.println("data = " + data);
//
//        return data;
//    }
}
