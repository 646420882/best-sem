package com.perfect.api.baidu;

import com.perfect.autosdk.core.JacksonUtil;
import org.apache.commons.httpclient.methods.PostMethod;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vbzer_000 on 2014/9/24.
 */
public class PostMethodFactory {

    private static String getValue(String cmd, String key) {
        String value = cmd.substring(cmd.indexOf(key), cmd.lastIndexOf("'"));
        value = value.replace(key + ": ", "");
        value = value.substring(0, value.indexOf("'"));
        return value;
    }

    private static void setDataMap(String cmd, String key, int region, int device, int page, PostMethod postMethod) {
        String data = cmd.substring(cmd.indexOf("--data "), cmd.lastIndexOf("'")).replace("--data ", "").replaceAll("'", "");
        Map<String, Object> dataMap = new HashMap<>();
        try {
            data = URLDecoder.decode(data, "utf-8");
            for (String param : data.split("&")) {
                String[] keyValue = param.split("=");
                if (keyValue[0].equals("params"))
                    continue;
                postMethod.addParameter(keyValue[0], keyValue[1]);
            }

            dataMap.put("area", region);
            dataMap.put("keyword", URLEncoder.encode(key, "utf-8"));
            dataMap.put("pageNo", page);

            postMethod.addParameter("params", JacksonUtil.obj2Str(dataMap));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static PostMethod getMethod(String src, String key, int region, int device, int page) {
        PostMethod postMethod = new PostMethod("http://fengchao.baidu.com/nirvana/request.ajax?path=GET/Live");
        postMethod.addRequestHeader("Accept-Language", "zh-CN");
        postMethod.addRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        postMethod.addRequestHeader("Connection", "keep-alive");
        postMethod.addRequestHeader("Origin", "http://fengchao.baidu.com");
        postMethod.addRequestHeader("Accept-Encoding", "gzip,deflate");
        postMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Maxthon/4.4.1.5000 Chrome/30.0.1599.101 Safari/537.36");
        postMethod.addRequestHeader("Cookie", getValue(src, "Cookie"));
        postMethod.addRequestHeader("Host", "fengchao.baidu.com");
        postMethod.addRequestHeader("Referer", getValue(src, "Referer"));
        postMethod.addRequestHeader("DNT", "1");
        postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");


        postMethod.addParameter("path", "GET%2FLive");
        postMethod.setQueryString("path=GET/Live");
        setDataMap(src, key, region, device, page, postMethod);

        return postMethod;
    }


//    public static void main(String[] args) {
//        PostMethodFactory factory = new PostMethodFactory();
//        PostMethod method = factory.getMethod("curl 'http://fengchao.baidu.com/nirvana/request.ajax?path=GET/Live' -H" +
//                " " +
//                "'Accept-Language: " +
//                "zh-CN' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9," +
//                "*/*;q=0.8' -H 'Connection: keep-alive' -H 'Origin: http://fengchao.baidu.com' -H 'Accept-Encoding: " +
//                "gzip,deflate' -H 'User-Agent: Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, " +
//                "like Gecko) Maxthon/4.4.1.5000 Chrome/30.0.1599.101 Safari/537.36' -H 'Cookie: " +
//                "BAIDUID=969E042AD05EC7C50FEB0F0159E19E76:FG=1; " +
//                "BDUSS=c4bDBMUUlaM3JPNWpPblFDT3R6Rnh4bzc3U3gzZUhaSURoWEN6bzBiMmlWa2RVQUFBQUFBJCQAAAAAAAAAAAEAAAAKQCABemVyb2Nvb2x5cwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAKLJH1SiyR9Ua0; uc_login_unique=634ae7bef3dc75560f57ff5638fd0785; MSA_WH=389_610; SFSSID=ee4e26d2ace5ff7af73dfacc0e7abce8; SIGNIN_UC=70a2711cf1d3d9b1a82d2f87d633bd8a01615376566; __cas__st__3=35d97e5b582794ccb7ff32fe058c3514209ee9bfbf19cfcf2b29b05c414bbfe7fdeb25eb8521caa6e938ad78; __cas__id__3=7001963; __cas__rn__=161537656; lsv=93f901ca1da51db8; SAMPLING_USER_ID=7001963' -H 'Host: fengchao.baidu.com' -H 'Referer: http://fengchao.baidu.com/nirvana/main.html?userid=7001963&castk=3ce47ua772062d1c7d354' -H 'DNT: 1' -H 'Content-Type: application/x-www-form-urlencoded' --data 'path=GET%2FLive&params=%7B%22device%22%3A1%2C%22keyword%22%3A%22%25E5%258C%2597%25E4%25BA%25AC%22%2C%22area%22%3A%22226%22%2C%22pageNo%22%3A0%7D&userid=7001963&token=35d97e5b582794ccb7ff32fe058c3514209ee9bfbf19cfcf2b29b05c414bbfe7fdeb25eb8521caa6e938ad78' --compressed", "去哪儿旅游", 1, 1, 0);
//
//
//        HttpClient client = new HttpClient();
//        try {
//            int code = client.executeMethod(method);
//            GZIPInputStream is = new GZIPInputStream(method.getResponseBodyAsStream());
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
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
