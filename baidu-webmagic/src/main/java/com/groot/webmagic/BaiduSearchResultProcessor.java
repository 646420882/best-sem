package com.groot.webmagic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.*;

/**
 * Created by yousheng on 14/11/5.
 */
public class BaiduSearchResultProcessor implements PageProcessor, Constant {

    @Override
    public void process(Page page) {

        page.putField(REQUEST_UUID,page.getRequest().getExtra(REQUEST_UUID));

        if (page.getRawText().length() < 200) {
            page.putField(PAGE_TIMEOUT,true);
        } else {

            Document doc = Jsoup.parse(page.getRawText());

            LinkedList<Map<String, Object>> leftList = handleLeft(doc);

            LinkedList<Map<String, Object>> rightList = handleRight(doc);

            if(leftList.size() == 0 && rightList.size() == 0){
                page.putField(PAGE_EMPTY,true);
            }
            page.putField(PAGE_LEFT, leftList);
            page.putField(PAGE_RIGHT, rightList);
        }
    }

    @Override
    public Site getSite() {
        return Site.me();
    }


    private LinkedList<Map<String, Object>> handleLeft(Document doc) {
        LinkedList<Map<String, Object>> creativeDTOList = new LinkedList<>();

        //获取左侧推广数据
        if (doc.select("#content_left > table").isEmpty()) {
            //div
            // ec_title
            Elements elements = doc.select("#content_left > .Ec_result");
            for (Element element : elements) {
                if (element.attr("id").startsWith("5"))
                    continue;

                Map<String, Object> creativeDTO = new HashMap<>();
                creativeDTO.put(CREATIVE_DESCSRC, element.html());
                creativeDTO.put(CREATIVE_TITLE, element.select(".ec_title").text());

                if (element.select(".ec_desc .EC_pap_big_desc").size() > 0) {
                    creativeDTO.put(CREATIVE_DESC, element.select(".ec_desc .EC_pap_big_desc").text());
                    creativeDTO.put(CREATIVE_URL, element.select(".ec_desc .ec_meta .ec_url").text());
                    creativeDTO.put(CREATIVE_TIME, element.select(".ec_desc .ec_meta .ec_date").text());
                    Elements children = element.select(".ec_desc .EC_pap_big_paxj a");
                    if (children != null) {
                        List<Map<String, Object>> sublinkInfos = new ArrayList<>();
                        ListIterator<Element> iterator = children.listIterator();
                        while (iterator.hasNext()) {
                            Map<String, Object> sublinkInfo = new HashMap<>();
                            sublinkInfo.put(SUBLINK_DESC, iterator.next().text());
                            sublinkInfos.add(sublinkInfo);
                        }
                        creativeDTO.put(CREATIVE_SUBLINKS, sublinkInfos);
                    }
                } else {
                    creativeDTO.put(CREATIVE_DESC, element.select(".ec_desc").text());
                    creativeDTO.put(CREATIVE_URL, element.select(".ec_meta .ec_url").text());
                    creativeDTO.put(CREATIVE_TIME, element.select(".ec_meta .ec_date").text());
                }
                creativeDTOList.addLast(creativeDTO);

            }

        } else {
            Elements tables = doc.select("#content_left table");
            for (Element table : tables) {
                if (!table.className().startsWith(" ")) {
                    continue;
                }
                Map<String, Object> creativeDTO = new HashMap<>();
                creativeDTO.put(CREATIVE_DESCSRC, table.html());
                creativeDTO.put(CREATIVE_TITLE, table.select(".EC_title").text());

                Elements descs = table.select(".EC_body");
                if (descs.isEmpty()) {
                    creativeDTO.put(CREATIVE_DESC, table.select(".EC_pap_big_zpdes").text());

                    // 处理XJ
//                    Elements xjs = table.select(".EC_pap_big_xj");
                } else {
                    creativeDTO.put(CREATIVE_DESC, descs.text());
                }

                Elements sublinks = table.select(".EC_xj_underline a");
                if (!sublinks.isEmpty()) {
                    ListIterator<Element> children = sublinks.listIterator();
                    List<Map<String, Object>> sublinkInfos = new ArrayList<>();
                    while (children.hasNext()) {
                        Map<String, Object> sublinkInfo = new HashMap<>();
                        sublinkInfo.put(SUBLINK_DESC, children.next().text());
                        sublinkInfos.add(sublinkInfo);
                    }
                    creativeDTO.put(CREATIVE_SUBLINKS, sublinkInfos);
                }

                creativeDTO.put(CREATIVE_URL, table.select(".EC_url").text());

                creativeDTOList.addLast(creativeDTO);
            }
        }
        return creativeDTOList;
    }


    private static LinkedList<Map<String, Object>> handleRight(Document doc) {
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

        LinkedList<Map<String, Object>> creativeList = new LinkedList<>();
        //获取右侧推广数据
        if (_temp2 && div_right.size() > 0) {
            Map<String, Object> creativeVO = new HashMap<>();
            for (Element e : div_right) {
                String _title = e.select("a").first().text();
                String _description = e.select("a").get(1).text();
                String _url = e.select("a").get(1).select("font").last().text();
                _description.replace(_url, "");

                creativeVO.put(CREATIVE_DESCSRC, e.html());
                creativeVO.put(CREATIVE_TITLE, _title);
                creativeVO.put(CREATIVE_DESC, _description);
                creativeVO.put(CREATIVE_URL, _url);
                creativeList.addLast(creativeVO);
            }
        }

        return creativeList;
    }

}
