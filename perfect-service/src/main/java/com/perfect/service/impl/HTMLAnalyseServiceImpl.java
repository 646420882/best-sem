package com.perfect.service.impl;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.dto.CreativeDTO;
import com.perfect.service.HTMLAnalyseService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
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

    private final ServiceFactory serviceFactory;

    public HTMLAnalyseServiceImpl(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    public static HTMLAnalyseService createService(ServiceFactory serviceFactory) {
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
    public List<PreviewData> getPageData(GetPreviewRequest getPreviewRequest) {
        List<CreativeDTO> leftCreativeVOList = new ArrayList<>();
        List<CreativeDTO> rightCreativeVOList = new ArrayList<>();

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
        Elements div_left = null;
        boolean _temp1 = (doc.getElementById("content_left") != null) && (doc.getElementById("content_left").children().size() > 0);

        if (_temp1) {
            div_left = doc.getElementById("content_left").children();
        }


        //过滤出左侧的推广信息
        if (_temp1) {
            for (int i = div_left.size() - 1; i >= 0; i--) {
                if (div_left.get(i).hasAttr("id")) {
                    div_left.remove(i);
                }
            }
        }

        //获取左侧推广数据
        if (_temp1 && div_left.size() > 0) {
            if (div_left.select("table").size() > 0 && "推广链接".equals(div_left.select("table").first().select("a").first().text())) {
                List<Element> elementList = new ArrayList<>();
                for (Element _e : div_left) {
                    if ("table".equals(_e.tagName())) {
                        elementList.add(_e);
                    }
                }
                div_left = new Elements(elementList.subList(0, elementList.size() / 2));
                //table
                CreativeDTO creativeVO = null;

                for (Element e : div_left) {
                    if (e.child(0).select("table").size() > 0) {//one of conditions -> 搜索"去哪儿旅游"
                        creativeVO = new CreativeDTO();
                        String _title = e.select("tr").get(1).child(0).text();
                        String _description = e.child(0).select("table").select("td").get(1).child(0).child(0).text();
                        String _url = e.child(0).select("table").select("td").get(1).child(0).children().last().child(0).text();

                        Elements elements = e.child(0).select("table").select("td").get(1).child(0).children().get(2).select("a");
                        List<SublinkInfo> list = new ArrayList<>(elements.size());
                        for (Element e1 : elements) {
                            if (StringUtils.isBlank(e1.text()))
                                continue;
                            SublinkInfo sublinkInfo = new SublinkInfo();
                            sublinkInfo.setDescription(e1.text());
                            sublinkInfo.setDestinationUrl(e1.attr("abs:href"));
                            list.add(sublinkInfo);
                        }

                        creativeVO.setTitle(_title);
                        creativeVO.setDescription(_description);
                        creativeVO.setUrl(_url);
                        creativeVO.setSublinkInfos(list);
                        leftCreativeVOList.add(creativeVO);

                        continue;
                    }
                    Element _e1 = e.select("tr").get(2).select("td").first();
                    Element _e2 = e.select("tr").get(3).select("td").first();
                    String _title = e.select("tr").get(1).select("td").first().select("a").first().text();
                    String _url = e.select("tr").get(1).select("td").first().select("a").eq(1).text();
                    String _description;

                    if (_e1.select("a").size() == 1 && _e2.select("a").size() == 0) {
                        //only description
                        creativeVO = new CreativeDTO();
                        _description = _e1.select("a").text();
                        creativeVO.setTitle(_title);
                        creativeVO.setDescription(_description);
                        creativeVO.setUrl(_url);
                        leftCreativeVOList.add(creativeVO);
                    } else if (_e1.select("div").size() == 1) {
                        //only sublink(ul-li)
                        creativeVO = new CreativeDTO();

                        //get sublink
                        List<SublinkInfo> list = new ArrayList<>();
                        for (Element e1 : _e1.select("div").first().select("ul").first().children()) {
                            Elements _elements = e1.select("a");
                            SublinkInfo sublinkInfo;
                            for (Element e2 : _elements) {
                                if (StringUtils.isBlank(e2.text()))
                                    continue;
                                sublinkInfo = new SublinkInfo();
                                sublinkInfo.setDescription(e2.text());
                                sublinkInfo.setDestinationUrl(e2.attr("abs:href"));
                                list.add(sublinkInfo);
                            }
                        }
                        creativeVO.setTitle(_title);
                        creativeVO.setUrl(_url);
                        creativeVO.setSublinkInfos(list);
                        leftCreativeVOList.add(creativeVO);
                    } else if (_e1.select("a").size() > 0 && _e2.select("a").size() == 0) {
                        //only sublink(a)
                        creativeVO = new CreativeDTO();

                        //get sublink
                        Elements _elements = _e1.select("a");
                        List<SublinkInfo> list = new ArrayList<>(_elements.size());
                        SublinkInfo sublinkInfo;
                        for (Element e1 : _elements) {
                            if (StringUtils.isBlank(e1.text()))
                                continue;
                            sublinkInfo = new SublinkInfo();
                            sublinkInfo.setDescription(e1.text());
                            sublinkInfo.setDestinationUrl(e1.attr("abs:href"));
                            list.add(sublinkInfo);
                        }

                        creativeVO.setTitle(_title);
                        creativeVO.setUrl(_url);
                        creativeVO.setSublinkInfos(list);
                        leftCreativeVOList.add(creativeVO);
                    } else if (_e2.select("div").size() == 1) {
                        //description & sublink(ul-li)
                        creativeVO = new CreativeDTO();
                        _description = _e1.select("a").text();

                        //get sublink
                        List<SublinkInfo> list = new ArrayList<>();
                        for (Element e1 : _e2.select("div").first().select("ul").first().children()) {
                            Elements _elements = e1.select("a");
                            SublinkInfo sublinkInfo;
                            for (Element e2 : _elements) {
                                if (StringUtils.isBlank(e2.text()))
                                    continue;
                                sublinkInfo = new SublinkInfo();
                                sublinkInfo.setDescription(e2.text());
                                sublinkInfo.setDestinationUrl(e2.attr("abs:href"));
                                list.add(sublinkInfo);
                            }
                        }
                        creativeVO.setTitle(_title);
                        creativeVO.setDescription(_description);
                        creativeVO.setUrl(_url);
                        creativeVO.setSublinkInfos(list);
                        leftCreativeVOList.add(creativeVO);
                    } else if (_e2.select("a").size() > 0) {
                        //description & sublink(a)
                        creativeVO = new CreativeDTO();
                        _description = _e1.select("a").text();

                        //get sublink
                        Elements _elements = _e2.select("a");
                        List<SublinkInfo> list = new ArrayList<>(_elements.size());
                        SublinkInfo sublinkInfo;
                        for (Element e1 : _elements) {
                            if (StringUtils.isBlank(e1.text()))
                                continue;
                            sublinkInfo = new SublinkInfo();
                            sublinkInfo.setDescription(e1.text());
                            sublinkInfo.setDestinationUrl(e1.attr("abs:href"));
                            list.add(sublinkInfo);
                        }

                        creativeVO.setTitle(_title);
                        creativeVO.setDescription(_description);
                        creativeVO.setUrl(_url);
                        creativeVO.setSublinkInfos(list);
                        leftCreativeVOList.add(creativeVO);
                    }
                }
            } else {
                //div
                CreativeDTO creativeVO = null;

                for (Element e : div_left) {
                    if (!e.tagName().equals("div")) {
                        continue;
                    }
                    Elements elements_div = e.children();
                    if (elements_div.size() == 2) {//one of conditions -> 搜索"上海婚博会"
                        creativeVO = new CreativeDTO();
                        String _title = elements_div.first().select("a").first().text();
                        String _description = elements_div.get(1).select("tr").first()
                                .select("td").get(1).children().get(0).children().get(0).text();
                        String _url = elements_div.get(1).select("tr").first()
                                .select("td").get(1).children().get(0).children().get(2).child(0).text();

                        if (elements_div.get(1).select("tr").first().select("td").get(1).child(0).child(1).select("a").size() > 0) {
                            //sublink
                            Elements _elements = elements_div.get(1).select("tr").first()
                                    .select("td").get(1).child(0).child(1).select("a");
                            List<SublinkInfo> list = new ArrayList<>(_elements.size());
                            SublinkInfo sublinkInfo;
                            for (Element e1 : _elements) {
                                if (StringUtils.isBlank(e1.text()))
                                    continue;
                                sublinkInfo = new SublinkInfo();
                                sublinkInfo.setDescription(e1.text());
                                sublinkInfo.setDestinationUrl(e1.attr("abs:href"));
                                list.add(sublinkInfo);
                            }
                            creativeVO.setSublinkInfos(list);
                        }

                        creativeVO.setTitle(_title);
                        creativeVO.setDescription(_description);
                        creativeVO.setUrl(_url);
                        leftCreativeVOList.add(creativeVO);
                        continue;
                    }
                    //only sublink(including a)
                    Elements elements1 = elements_div.get(1).children();
                    //only sublink(including ul-li)
                    Elements _elements1 = elements_div.get(1).children();
                    //description & sublink(including a)
                    Elements _elements2 = elements_div.get(2).children();
                    //description & sublink(including ul-li)
                    Elements _elements3 = elements_div.get(2).children();

                    //判断elements1的直接子元素是否全部为a
                    boolean elements1AllIsA = true;
                    for (Element _e : elements1) {
                        if (!"a".equals(_e.tagName())) {
                            elements1AllIsA = false;
                            break;
                        }
                    }

                    //判断_elements2的直接子元素是否全部为a
                    boolean _elements2AllIsA = true;
                    for (Element _e : _elements2) {
                        if (!"a".equals(_e.tagName())) {
                            _elements2AllIsA = false;
                            break;
                        }
                    }

                    String _title = elements_div.first().text();
                    String _description;
                    String _url;
                    if (_elements1.select("div").size() == 0 && elements_div.size() == 3) {
                        //only description
                        creativeVO = new CreativeDTO();
                        _description = elements_div.get(1).text();
                        _url = elements_div.get(2).child(0).text();
                        creativeVO.setTitle(_title);
                        creativeVO.setDescription(_description);
                        creativeVO.setUrl(_url);
                        leftCreativeVOList.add(creativeVO);
                    } else if (_elements1.size() == 1 && "div".equals(_elements1.get(0).tagName()) && elements_div.size() == 3 && _elements1.first().select("ul").size() == 1) {
                        //only sublink(ul-li)
                        creativeVO = new CreativeDTO();
                        _url = elements_div.get(2).child(0).text();

                        //get sublink
                        List<SublinkInfo> list = new ArrayList<>();
                        for (Element e1 : _elements1.first().select("ul").first().children()) {
                            Elements _elements = e1.select("a");
                            SublinkInfo sublinkInfo;
                            for (Element e2 : _elements) {
                                if (StringUtils.isBlank(e2.text()))
                                    continue;
                                sublinkInfo = new SublinkInfo();
                                sublinkInfo.setDescription(e2.text());
                                sublinkInfo.setDestinationUrl(e2.attr("abs:href"));
                                list.add(sublinkInfo);
                            }
                        }

                        creativeVO.setTitle(_title);
                        creativeVO.setUrl(_url);
                        creativeVO.setSublinkInfos(list);
                        leftCreativeVOList.add(creativeVO);
                    } else if (elements1AllIsA && elements_div.size() == 3) {
                        //only sublink(a)
                        creativeVO = new CreativeDTO();
                        _url = elements_div.get(2).child(0).text();

                        //get sublink
                        List<SublinkInfo> list = new ArrayList<>(elements1.size());
                        SublinkInfo sublinkInfo;
                        for (Element e1 : elements1) {
                            if (StringUtils.isBlank(e1.text()))
                                continue;
                            sublinkInfo = new SublinkInfo();
                            sublinkInfo.setDescription(e1.text());
                            sublinkInfo.setDestinationUrl(e1.attr("abs:href"));
                            list.add(sublinkInfo);
                        }

                        creativeVO.setTitle(_title);
                        creativeVO.setUrl(_url);
                        creativeVO.setSublinkInfos(list);
                        leftCreativeVOList.add(creativeVO);
                    } else if (!elements1AllIsA && elements_div.size() == 3) {
                        if (elements1.size() == 1 && "div".equals(elements1.first().tagName())) {
                            if (elements1.first().select("div").size() > 0 && elements1.first().select("div").get(0).select("a").size() > 0) {
                                /**
                                 * elements_div.get(1)
                                 *
                                 * <div class="ec_desc ec_font_small">
                                 *  <div class="EC_palist">
                                 *      <div class="EC_pp_pal EC_pal3002 EC_PP" style="">
                                 *          <a style="word-wrap:normal;width:25%;text-align:left; padding-right:1%" href="javascript:void(0)" target="_blank">韩国5日游</a>
                                 *          <a style="word-wrap:normal;width:22%;text-align:left; padding-right:1%;text-decoration:none">让&quot;利&quot;到底</a>
                                 *          <a style="word-wrap:normal;width:17%;text-align:left; padding-right:1%;text-decoration:none">2380元起</a>
                                 *          <a style="word-wrap:normal;width:25%;text-align:left; padding-right:0%" href="javascript:void(0)" target="_blank">参考行程</a>
                                 *      </div>
                                 *  </div>
                                 *</div>
                                 */
                                Elements elements_1 = elements1.first().select("a");
                                creativeVO = new CreativeDTO();
                                _url = elements_div.get(2).child(0).text();

                                List<SublinkInfo> list = new ArrayList<>(elements_1.size());
                                SublinkInfo sublinkInfo;
                                for (Element e1 : elements_1) {
                                    if (StringUtils.isBlank(e1.text()))
                                        continue;

                                    sublinkInfo = new SublinkInfo();
                                    sublinkInfo.setDescription(e1.text());
                                    sublinkInfo.setDestinationUrl(e1.attr("abs:href"));
                                    list.add(sublinkInfo);
                                }

                                creativeVO.setTitle(_title);
                                creativeVO.setUrl(_url);
                                creativeVO.setSublinkInfos(list);
                                leftCreativeVOList.add(creativeVO);
                            }
                        }
                    } else if (_elements1.select("div").size() == 0 && elements_div.size() == 4 && _elements2AllIsA) {
                        //description & sublink(a)
                        creativeVO = new CreativeDTO();
                        _description = elements_div.get(1).text();
                        _url = elements_div.get(3).child(0).text();

                        //get sublink
                        List<SublinkInfo> list = new ArrayList<>(_elements2.size());
                        SublinkInfo sublinkInfo;
                        for (Element e1 : _elements2) {
                            if (StringUtils.isBlank(e1.text()))
                                continue;
                            sublinkInfo = new SublinkInfo();
                            sublinkInfo.setDescription(e1.text());
                            sublinkInfo.setDestinationUrl(e1.attr("abs:href"));
                            list.add(sublinkInfo);
                        }

                        creativeVO.setTitle(_title);
                        creativeVO.setDescription(_description);
                        creativeVO.setUrl(_url);
                        creativeVO.setSublinkInfos(list);
                        leftCreativeVOList.add(creativeVO);
                    } else if (_elements1.select("div").size() == 0 && elements_div.size() == 4 && _elements3.size() == 1 && "div".equals(_elements3.get(0).tagName()) && _elements3.first().select("ul").size() == 1) {
                        //description & sublink(ul-li)
                        creativeVO = new CreativeDTO();
                        _description = elements_div.get(1).text();
                        _url = elements_div.get(3).child(0).text();

                        //get sublink
                        List<SublinkInfo> list = new ArrayList<>();
                        for (Element e1 : _elements3.first().select("ul").first().children()) {
                            Elements _elements = e1.select("a");
                            SublinkInfo sublinkInfo;
                            for (Element e2 : _elements) {
                                if (StringUtils.isBlank(e2.text()))
                                    continue;
                                sublinkInfo = new SublinkInfo();
                                sublinkInfo.setDescription(e2.text());
                                sublinkInfo.setDestinationUrl(e2.attr("abs:href"));
                                list.add(sublinkInfo);
                            }
                        }

                        creativeVO.setTitle(_title);
                        creativeVO.setDescription(_description);
                        creativeVO.setUrl(_url);
                        creativeVO.setSublinkInfos(list);
                        leftCreativeVOList.add(creativeVO);
                    }
                }
            }
        }

    }

    private Map<String, String> getHTML(GetPreviewRequest getPreviewRequest, CommonService commonService) {
        RankService rankService = null;
        try {
            rankService = commonService.getService(RankService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }

        GetPreviewResponse response = rankService.getPreview(getPreviewRequest);
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

        private List<CreativeDTO> left;

        private List<CreativeDTO> right;

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
    }


    public static void main(String[] args) {
        System.out.println("HTMLAnalyseServiceImpl.main");


        try {
            ServiceFactory service = ServiceFactory.getInstance("baidu-上品折扣2103914", "SHANGpin8952", "f35d9f818141591cc4fd43ac8e8056b8", null);
            HTMLAnalyseService htmlService = HTMLAnalyseServiceImpl.createService(service);

            GetPreviewRequest request = new GetPreviewRequest();

            request.setDevice(0);
            request.setRegion(28000);


            request.setKeyWords(Arrays.asList(new String[]{"车展"}));

            List<PreviewData> map = htmlService.getPageData(request);

            for (PreviewData data : map) {
                System.out.println("data.getLeft() = " + data.getLeft());
            }

        } catch (ApiException e) {
            e.printStackTrace();
        }


    }
}