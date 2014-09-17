package com.perfect.service.impl;

import com.perfect.api.baidu.RequestHelper;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.GetPreviewRequest;
import com.perfect.autosdk.sms.v3.GetPreviewResponse;
import com.perfect.autosdk.sms.v3.PreviewInfo;
import com.perfect.autosdk.sms.v3.SublinkInfo;
import com.perfect.dto.CreativeDTO;
import com.perfect.service.HTMLAnalyseService;
import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 * Created by baizz on 2014-7-15.
 */
public class HTMLAnalyseServiceImpl implements HTMLAnalyseService {

    private final CommonService serviceFactory;

    private HTMLAnalyseServiceImpl(CommonService serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    public static HTMLAnalyseService createService(CommonService serviceFactory) {
        return new HTMLAnalyseServiceImpl(serviceFactory);
    }


    //模糊匹配
    public boolean vagueMatch(String keyword, String matchWord) {
        char[] chars1 = keyword.toCharArray();
        char[] chars2 = matchWord.toCharArray();
        Arrays.sort(chars1);
        Arrays.sort(chars2);
        keyword = String.valueOf(chars1);
        matchWord = String.valueOf(chars2);
        return keyword.equals(matchWord);
    }
    //从模拟页面获取推广链接和关键词

    @Override
    public List<PreviewData> getPageData(String[] keyword, Integer region) {
        GetPreviewRequest getPreviewRequest = new GetPreviewRequest();

        getPreviewRequest.setRegion(region);
        getPreviewRequest.setKeyWords(Arrays.asList(keyword));
        getPreviewRequest.setDevice(0);

        List<CreativeDTO> leftCreativeVOList = new LinkedList<>();
        List<CreativeDTO> rightCreativeVOList = new LinkedList<>();

        Map<String, String> htmls = getHTML(getPreviewRequest, serviceFactory);

        if (htmls == null)
            return Collections.EMPTY_LIST;

        List<PreviewData> previewDatas = new ArrayList<>(htmls.size());

        for (Map.Entry<String, String> htmlEntry : htmls.entrySet()) {
            Document doc = Jsoup.parse(htmlEntry.getValue());

            handleLeft(doc, leftCreativeVOList);
            handleRight(doc, rightCreativeVOList);

            PreviewData previewData = new PreviewData();

            previewData.setKeyword(htmlEntry.getKey());
            previewData.setRegion(getPreviewRequest.getRegion());
            previewData.setDevice(getPreviewRequest.getDevice());
            previewData.setLeft(leftCreativeVOList);
            previewData.setRight(rightCreativeVOList);
            previewDatas.add(previewData);
        }

        return previewDatas;
    }

    private void handleRight(Document doc, final List<CreativeDTO> rightCreativeVOList) {
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
            CreativeDTO creativeVO = null;
            for (Element e : div_right) {
                String _title = e.select("a").first().text();
                String _description = e.select("a").get(1).text();
                String _url = e.select("a").get(1).select("font").last().text();
                _description.replace(_url, "");

                creativeVO = new CreativeDTO();
                creativeVO.setTitle(_title);
                creativeVO.setDescription(_description);
                creativeVO.setUrl(_url);
                rightCreativeVOList.add(creativeVO);
            }
        }
    }

    private void handleLeft(Document doc, final List<CreativeDTO> leftCreativeVOList) {
        LinkedList<CreativeDTO> creativeDTOList = new LinkedList<>();

        //获取左侧推广数据
        if (doc.select("#content_left > table").isEmpty()) {
            //div
            // ec_title
            Elements elements = doc.select("#content_left > div");
            for (Element element : elements) {
                CreativeDTO creativeDTO = new CreativeDTO();
                creativeDTO.setTitle(element.select(".ec_title").text());

                creativeDTO.setTitle(element.select(".ec_title").text());
                if (element.select(".ec_desc .EC_pap_big_desc").size() > 0) {
                    creativeDTO.setDescription(element.select(".ec_desc .EC_pap_big_desc").text());
                    creativeDTO.setUrl(element.select(".ec_desc .ec_meta .ec_url").text());
                    creativeDTO.setTime(element.select(".ec_desc .ec_meta .ec_date").text());
                    Elements children = element.select(".ec_desc .EC_pap_big_paxj a");
                    if (children != null) {
                        List<SublinkInfo> sublinkInfos = new ArrayList<>();
                        ListIterator<Element> iterator = children.listIterator();
                        while (iterator.hasNext()) {
                            SublinkInfo sublinkInfo = new SublinkInfo();
                            sublinkInfo.setDescription(iterator.next().text());
                            sublinkInfos.add(sublinkInfo);
                        }
                        creativeDTO.setSublinkInfos(sublinkInfos);
                    }
                } else {
                    creativeDTO.setDescription(element.select(".ec_desc").text());
                    creativeDTO.setDescSource(element.select(".ec_desc").html());
                    creativeDTO.setUrl(element.select(".ec_meta .ec_url").text());
                    creativeDTO.setTime(element.select(".ec_meta .ec_date").text());
                }
                creativeDTOList.addLast(creativeDTO);

            }

        } else {
            Elements tables = doc.select("#content_left table");
            for (Element table : tables) {
                if (!table.className().startsWith(" ")) {
                    continue;
                }
                CreativeDTO creativeDTO = new CreativeDTO();
                creativeDTO.setTitle(table.select(".EC_title").text());

                Elements descs = table.select(".EC_body");
                if (descs.isEmpty()) {
                    creativeDTO.setDescription(table.select(".EC_pap_big_zpdes").text());

                    // 处理XJ
//                    Elements xjs = table.select(".EC_pap_big_xj");
                } else {
                    creativeDTO.setDescription(descs.text());
                }

                Elements sublinks = table.select(".EC_xj_underline a");
                if (!sublinks.isEmpty()) {
                    ListIterator<Element> children = sublinks.listIterator();
                    List<SublinkInfo> sublinkInfos = new ArrayList<>();
                    while (children.hasNext()) {
                        SublinkInfo sublinkInfo = new SublinkInfo();
                        sublinkInfo.setDescription(children.next().text());
                        sublinkInfos.add(sublinkInfo);
                    }
                    creativeDTO.setSublinkInfos(sublinkInfos);
                }

                creativeDTO.setUrl(table.select(".EC_url").text());

                creativeDTOList.addLast(creativeDTO);
            }
        }
        leftCreativeVOList.addAll(creativeDTOList);
    }

    private Map<String, String> getHTML(GetPreviewRequest getPreviewRequest, CommonService commonService) {
        GetPreviewResponse response = RequestHelper.addRequest(commonService, getPreviewRequest);
        if (response == null)
            return null;
        List<PreviewInfo> list1 = response.getPreviewInfos();
        if (list1 == null || list1.isEmpty()) {
            return null;
        }

        Map<String, String> htmls = new HashMap<>();

        for (PreviewInfo info : list1) {
            try {
                htmls.put(info.getKeyword(), uncompress(info.getData()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return htmls;
    }

    private String uncompress(String str) throws IOException {
        if (str == null || str.length() == 0)
            return str;

        byte[] bytes = Base64.decodeBase64(str);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        return out.toString("utf-8");
    }


    public static class PreviewData {

        private String keyword;

        private int region;

        private List<CreativeDTO> left;

        private List<CreativeDTO> right;
        private Integer device;

        public List<CreativeDTO> getLeft() {
            return left;
        }

        public void setLeft(List<CreativeDTO> left) {
            this.left = left;
        }

        public List<CreativeDTO> getRight() {
            return right;
        }

        public void setRight(List<CreativeDTO> right) {
            this.right = right;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public int getRegion() {
            return region;
        }

        public void setRegion(int region) {
            this.region = region;
        }

        public void setDevice(Integer device) {
            this.device = device;
        }

        public Integer getDevice() {
            return device;
        }
    }


    public static void main(String[] args) {
        System.out.println("HTMLAnalyseServiceImpl.main");


        try {
            ServiceFactory service = ServiceFactory.getInstance("baidu-上品折扣2103914", "SHANGpin8952", "f35d9f818141591cc4fd43ac8e8056b8", null);
            HTMLAnalyseService htmlService = HTMLAnalyseServiceImpl.createService(service);

            List<PreviewData> map = htmlService.getPageData(new String[]{"车贷", "婚纱照", "手机", "机票", "二手房"}, 28000);

            for (PreviewData data : map) {

                for (CreativeDTO dto : data.getLeft()) {
                    System.out.println(dto);
                }

                for (CreativeDTO dto : data.getRight()) {
                    System.out.println(dto);
                }
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }


    }
}