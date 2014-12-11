package com.perfect.app.bdlogin.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.perfect.dto.creative.CreativeInfoDTO;
import com.perfect.dto.creative.SublinkInfoDTO;
import com.perfect.utils.json.JSONUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by baizz on 2014-11-10.
 * 2014-11-26 refactor
 */
public class BaiduSearchPageUtils {

    private static String previewUrl;

    static {
        String path = new File(BaiduHttpLogin.class.getResource("/").getPath()).getPath() + System.getProperty("file.separator") + "bdlogin.properties";
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(path));
            Properties properties = new Properties();
            properties.load(is);
            previewUrl = properties.getProperty("bd.preview-url");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getBaiduSearchPage(String cookies, String keyword, int area) {
        CookieStore cookieStore = new BasicCookieStore();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            for (JsonNode node : JSONUtils.getMapper().readTree(cookies).get("cookies")) {
                BasicClientCookie cookie = new BasicClientCookie(node.get("name").asText(), node.get("value").asText());
                cookie.setVersion(node.get("version").asInt());
                cookie.setDomain(node.get("domain").asText());
                cookie.setPath(node.get("path").asText());
                cookie.setSecure(node.get("secure").asBoolean());
                if (node.get("expiryDate") != null) {
                    cookie.setExpiryDate(sdf.parse(node.get("expiryDate").asText()));
                }
                cookieStore.addCookie(cookie);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        StringBuilder _cookies = new StringBuilder("");
        String userid = "";
        String token = "";

        for (Cookie cookie : cookieStore.getCookies()) {
            if (BaiduHttpLogin.set.contains(cookie.getName())) {
                continue;
            }

            if ("__cas__st__3".equals(cookie.getName())) {
                token = cookie.getValue();
            } else if ("__cas__id__3".equals(cookie.getName())) {
                userid = cookie.getValue();
            }

            _cookies.append(cookie.getName()).append("=").append(cookie.getValue()).append("; ");
        }
        _cookies = _cookies.delete(_cookies.length() - 2, _cookies.length());

        HttpPost httpPost = new HttpPost(previewUrl);
        httpPost.addHeader("Host", "fengchao.baidu.com");
        httpPost.addHeader("Cookie", _cookies.toString());
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> postData = new ArrayList<>();
        postData.add(new BasicNameValuePair("params", "{\"device\":1,\"keyword\":\"" + keyword + "\",\"area\":" + area + ",\"pageNo\":0}"));
        postData.add(new BasicNameValuePair("userid", userid));
        postData.add(new BasicNameValuePair("token", token));
        httpPost.setEntity(new UrlEncodedFormEntity(postData, StandardCharsets.UTF_8));
        BaiduHttpLogin.headerWrap(httpPost);

        HttpClient client = HttpClients.createDefault();
        try {
            HttpResponse ht = client.execute(httpPost);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ht.getEntity().writeTo(outputStream);

            return new String(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int where(String html, String expectedHost) {


        Document doc = Jsoup.parse(html);

        List<CreativeInfoDTO> leftCreativeVOList = new ArrayList<>();
        List<CreativeInfoDTO> rightCreativeVOList = new ArrayList<>();

        handleLeft(doc, leftCreativeVOList);
        for (CreativeInfoDTO creativeInfoDTO : leftCreativeVOList) {
            if (creativeInfoDTO.getUrl().equals(expectedHost)) {
                return leftCreativeVOList.indexOf(creativeInfoDTO);
            }
        }

        handleRight(doc, rightCreativeVOList);

        for (CreativeInfoDTO creativeInfoDTO : rightCreativeVOList) {
            if (creativeInfoDTO.getUrl().equals(expectedHost)) {
                return -1 * leftCreativeVOList.indexOf(creativeInfoDTO);
            }
        }

        return 0;
    }


    private static void handleLeft(Document doc, final List<CreativeInfoDTO> leftCreativeVOList) {
        LinkedList<CreativeInfoDTO> creativeInfoDTOList = new LinkedList<>();

        //获取左侧推广数据
        if (doc.select("#content_left > table").isEmpty()) {
            //div
            // ec_title
            Elements elements = doc.select("#content_left > .Ec_result");
            for (Element element : elements) {
                if (element.attr("id").startsWith("5"))
                    continue;

                CreativeInfoDTO creativeInfoDTO = new CreativeInfoDTO();
                creativeInfoDTO.setDescSource(element.html());
                creativeInfoDTO.setTitle(element.select(".ec_title").text());

                creativeInfoDTO.setTitle(element.select(".ec_title").text());
                if (element.select(".ec_desc .EC_pap_big_desc").size() > 0) {
                    creativeInfoDTO.setDescription(element.select(".ec_desc .EC_pap_big_desc").text());
                    creativeInfoDTO.setUrl(element.select(".ec_desc .ec_meta .ec_url").text());
                    creativeInfoDTO.setTime(element.select(".ec_desc .ec_meta .ec_date").text());
                    Elements children = element.select(".ec_desc .EC_pap_big_paxj a");
                    if (children != null) {
                        List<SublinkInfoDTO> sublinkInfos = new ArrayList<>();
                        ListIterator<Element> iterator = children.listIterator();
                        while (iterator.hasNext()) {
                            SublinkInfoDTO sublinkInfo = new SublinkInfoDTO();
                            sublinkInfo.setDescription(iterator.next().text());
                            sublinkInfos.add(sublinkInfo);
                        }
                        creativeInfoDTO.setSublinkInfoDTOs(sublinkInfos);
                    }
                } else {
                    creativeInfoDTO.setDescription(element.select(".ec_desc").text());
                    creativeInfoDTO.setUrl(element.select(".ec_meta .ec_url").text());
                    creativeInfoDTO.setTime(element.select(".ec_meta .ec_date").text());
                }
                creativeInfoDTOList.addLast(creativeInfoDTO);

            }

        } else {
            Elements tables = doc.select("#content_left table");
            for (Element table : tables) {
                if (!table.className().startsWith(" ")) {
                    continue;
                }
                CreativeInfoDTO creativeInfoDTO = new CreativeInfoDTO();
                creativeInfoDTO.setDescSource(table.html());
                creativeInfoDTO.setTitle(table.select(".EC_title").text());

                Elements descs = table.select(".EC_body");
                if (descs.isEmpty()) {
                    creativeInfoDTO.setDescription(table.select(".EC_pap_big_zpdes").text());

                    // 处理XJ
//                    Elements xjs = table.select(".EC_pap_big_xj");
                } else {
                    creativeInfoDTO.setDescription(descs.text());
                }

                Elements sublinks = table.select(".EC_xj_underline a");
                if (!sublinks.isEmpty()) {
                    ListIterator<Element> children = sublinks.listIterator();
                    List<SublinkInfoDTO> sublinkInfos = new ArrayList<>();
                    while (children.hasNext()) {
                        SublinkInfoDTO sublinkInfo = new SublinkInfoDTO();
                        sublinkInfo.setDescription(children.next().text());
                        sublinkInfos.add(sublinkInfo);
                    }
                    creativeInfoDTO.setSublinkInfoDTOs(sublinkInfos);
                }

                creativeInfoDTO.setUrl(table.select(".EC_url").text());

                creativeInfoDTOList.addLast(creativeInfoDTO);
            }
        }
        leftCreativeVOList.addAll(creativeInfoDTOList);
    }

    private static void handleRight(Document doc, final List<CreativeInfoDTO> rightCreativeVOList) {
        Elements div_right = null;
        boolean _temp2 = (doc.getElementById("ec_im_container") != null) && (doc.getElementById("ec_im_container").children().size() > 0);

        if (_temp2) {
            div_right = doc.getElementById("ec_im_container").children();
        }

        //过滤出右侧的推广信息
        if (_temp2) {
            for (int j = div_right.size() - 1; j >= 0; j--) {
                if (!div_right.get(j).hasAttr("id")) {
                    div_right.remove(j);
                }
            }
        }

        //获取右侧推广数据
        if (_temp2 && div_right.size() > 0) {
            CreativeInfoDTO creativeVO = null;
            for (Element e : div_right) {
                String _title = e.select("a").first().text();
                String _description = e.select("a").get(1).text();
                String _url = e.select("a").get(1).select("font").last().text();
                _description.replace(_url, "");

                creativeVO = new CreativeInfoDTO();
                creativeVO.setDescSource(e.html());
                creativeVO.setTitle(_title);
                creativeVO.setDescription(_description);
                creativeVO.setUrl(_url);
                rightCreativeVOList.add(creativeVO);
            }
        }
    }

}
