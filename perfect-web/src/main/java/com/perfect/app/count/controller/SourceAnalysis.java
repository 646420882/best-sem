package com.perfect.app.count.controller;

import com.perfect.dto.count.CensusDTO;
import com.perfect.service.CensusService;
import com.perfect.service.SourceService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by SubDong on 2014/11/19.
 */
@Controller
@Scope("prototype")
@RequestMapping("/pftstis")
public class SourceAnalysis {

    @Resource
    private CensusService censusService;

    @Resource
    private SourceService sourceService;

    /**
     * 全部来源
     *
     * @return
     */
    @RequestMapping(value = "/getAllSourcePage", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getAllSourcePage(ModelMap modelMap, HttpServletRequest request) {
        String s = request.getParameter("flag");
        modelMap.put("flag",s);
        return new ModelAndView("count/allSource",modelMap);
    }

    /**
     * 全部来源 当前数据
     *
     * @return
     */
    @RequestMapping(value = "/getAllSource", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getAllSource(HttpServletResponse response,
                             @RequestParam(value = "startDate", required = false) String startDate,
                             @RequestParam(value = "endDate", required = false) String endDate,
                             @RequestParam(value = "accessType", required = false) Integer accessType,
                             @RequestParam(value = "allTypes", required = false) Integer allTypes) {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        if (startDate == null || startDate.equals("")) {
            startDate = yesterday;
            endDate = yesterday;
        } else if (endDate == null || endDate.equals("")) {
            endDate = yesterday;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDates = null,newendDates = null;
        try {
            newDates = sdf.parse(startDate);
            newendDates = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Date> dates = new ArrayList<>();
        dates.add(newDates);
        dates.add(newendDates);


        List<CensusDTO> allType = sourceService.getSourceAnalysis(dates, accessType, allTypes);


        /*Map<String, List<Object>> mainMap = new HashMap<>();

        Map<String, List<Object>> storMap = new HashMap<>();

        List<Object> objects1 = new ArrayList<>();
        List<Object> objects = new ArrayList<>();

        switch (allType){
            case 1:
                //全部来源头数据
                Map<String, String> map = getSource(startDate);
                objects1.add(map);
                storMap.put("head", objects1);

                List<Object> objectList = getTableData(startDate);
                storMap.put("tableData", objectList);

                List<Object> stringListMap = getTableDataExpansion(startDate);

                storMap.put("expansion",stringListMap);
                break;
            case 2:
                //搜索引擎头数据
                Map<String, String> map1 = getSource(startDate);
                objects1.add(map1);
                storMap.put("head", objects1);

                List<Object> objectList1 = getTableData(startDate);
                storMap.put("tableData", objectList1);

                break;
            case 3:
                //搜索引擎头数据
                Map<String, String> map3 = getSource(startDate);
                objects1.add(map3);
                storMap.put("head", objects1);

                List<Object> objectList2 = getTableData(startDate);
                storMap.put("tableData", objectList2);
                break;
            case 4:
                //搜索引擎头数据
                Map<String, String> map4 = getSource(startDate);
                objects1.add(map4);
                storMap.put("head", objects1);

                List<Object> objectList3 = getTableData(startDate);
                storMap.put("tableData", objectList3);
                break;
        }

        objects.add(storMap);

        mainMap.put("rows", objects);*/

        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(null);
        return new ModelAndView(jsonView);
    }


    /**
     * 全部来源 头数据
     */
    private Map<String, String> getSource(String dateTime) {
        Map<String, String> map = new HashMap<>();
        switch (dateTime) {
            case "-1":
                map.put("lllzb", "99.99%");
                map.put("fwcs", "854");
                map.put("xfks", "442");
                map.put("xfkbl", "78%");
                map.put("ips", "332");
                map.put("tcl", "60%");
                break;
            case "1":
                map.put("lllzb", "89.99%");
                map.put("fwcs", "454");
                map.put("xfks", "142");
                map.put("xfkbl", "58%");
                map.put("ips", "132");
                map.put("tcl", "60%");
                break;
            case "7":
                map.put("lllzb", "69.99%");
                map.put("fwcs", "12454");
                map.put("xfks", "7142");
                map.put("xfkbl", "98%");
                map.put("ips", "5132");
                map.put("tcl", "80%");
                break;
            case "30":
                map.put("lllzb", "85.99%");
                map.put("fwcs", "312454");
                map.put("xfks", "17142");
                map.put("xfkbl", "88%");
                map.put("ips", "15132");
                map.put("tcl", "87.96%");
                break;
        }
        return map;
    }

    /**
     * 全部来源 头数据
     */
    private Map<String, String> getSouSuo(String dateTime) {
        Map<String, String> map = new HashMap<>();
        switch (dateTime) {
            case "-1":
                map.put("lllzb", "665");
                map.put("fwcs", "100%");
                map.put("xfks", "582");
                map.put("xfkbl", "447");
                map.put("ips", "432");
                map.put("tcl", "93.31%");
                break;
            case "1":
                map.put("lllzb", "565");
                map.put("fwcs", "100%");
                map.put("xfks", "382");
                map.put("xfkbl", "747");
                map.put("ips", "132");
                map.put("tcl", "50.31%");
                break;
            case "7":
                map.put("lllzb", "1265");
                map.put("fwcs", "100%");
                map.put("xfks", "982");
                map.put("xfkbl", "847");
                map.put("ips", "732");
                map.put("tcl", "81.31%");
                break;
            case "30":
                map.put("lllzb", "11665");
                map.put("fwcs", "100%");
                map.put("xfks", "2582");
                map.put("xfkbl", "6447");
                map.put("ips", "4432");
                map.put("tcl", "25.31%");
                break;
        }
        return map;
    }

    /**
     * 全部来源 曲线图数据
     */
    private Map<String, String> getDiagram(String dateTime) {
        Map<String, String> map = new HashMap<>();

        return map;
    }

    /**
     * 全部来源 表格数据
     */
    private List<Object> getTableData(String dateTime) {
        List<Object> objects = new ArrayList<>();
        switch (dateTime) {
            case "-1":
                Map<String, String> map = new HashMap<>();
                map.put("id", "c");
                map.put("lylx", "直接访问");
                map.put("llbl", "55.38%");
                map.put("fwcs", "177");
                map.put("fwks", "116");
                map.put("xfkbl", "72.05%");
                map.put("ips", "158");
                map.put("tcl", "82.14%");
                objects.add(map);
                map = new HashMap<>();
                map.put("id", "b");
                map.put("lylx", "搜索引擎");
                map.put("llbl", "34.7%");
                map.put("fwcs", "735");
                map.put("fwks", "480");
                map.put("xfkbl", "84.51%");
                map.put("ips", "551");
                map.put("tcl", "94.28%");
                objects.add(map);
                map = new HashMap<>();
                map.put("id", "a");
                map.put("lylx", "外部链接");
                map.put("llbl", "55.38%");
                map.put("fwcs", "1,139");
                map.put("fwks", "780");
                map.put("xfkbl", "77.61%");
                map.put("ips", "969");
                map.put("tcl", "93.15%");
                objects.add(map);
                break;
            case "1":
                Map<String, String> map1 = new HashMap<>();
                map1.put("id", "c");
                map1.put("lylx", "直接访问");
                map1.put("llbl", "65.38%");
                map1.put("fwcs", "717");
                map1.put("fwks", "316");
                map1.put("xfkbl", "62.05%");
                map1.put("ips", "258");
                map1.put("tcl", "62.14%");
                objects.add(map1);
                map1 = new HashMap<>();
                map1.put("id", "b");
                map1.put("lylx", "搜索引擎");
                map1.put("llbl", "44.7%");
                map1.put("fwcs", "1735");
                map1.put("fwks", "780");
                map1.put("xfkbl", "84.11%");
                map1.put("ips", "651");
                map1.put("tcl", "84.28%");
                objects.add(map1);
                map1 = new HashMap<>();
                map1.put("id", "a");
                map1.put("lylx", "外部链接");
                map1.put("llbl", "15.38%");
                map1.put("fwcs", "8139");
                map1.put("fwks", "3780");
                map1.put("xfkbl", "77%");
                map1.put("ips", "969");
                map1.put("tcl", "93.15%");
                objects.add(map1);
                break;
            case "7":
                Map<String, String> map2 = new HashMap<>();
                map2.put("id", "c");
                map2.put("lylx", "直接访问");
                map2.put("llbl", "95.38%");
                map2.put("fwcs", "1717");
                map2.put("fwks", "1316");
                map2.put("xfkbl", "52.05%");
                map2.put("ips", "1258");
                map2.put("tcl", "72.14%");
                objects.add(map2);
                map2 = new HashMap<>();
                map2.put("id", "b");
                map2.put("lylx", "搜索引擎");
                map2.put("llbl", "84.7%");
                map2.put("fwcs", "11735");
                map2.put("fwks", "1780");
                map2.put("xfkbl", "80%");
                map2.put("ips", "1651");
                map2.put("tcl", "89%");
                objects.add(map2);
                map2 = new HashMap<>();
                map2.put("id", "a");
                map2.put("lylx", "外部链接");
                map2.put("llbl", "45.38%");
                map2.put("fwcs", "18139");
                map2.put("fwks", "13780");
                map2.put("xfkbl", "37%");
                map2.put("ips", "1969");
                map2.put("tcl", "53.15%");
                objects.add(map2);
                break;
            case "30":
                Map<String, String> map3 = new HashMap<>();
                map3.put("id", "c");
                map3.put("lylx", "直接访问");
                map3.put("llbl", "95.38%");
                map3.put("fwcs", "21717");
                map3.put("fwks", "11316");
                map3.put("xfkbl", "52.05%");
                map3.put("ips", "11258");
                map3.put("tcl", "72.14%");
                objects.add(map3);
                map3 = new HashMap<>();
                map3.put("id", "b");
                map3.put("lylx", "搜索引擎");
                map3.put("llbl", "84.7%");
                map3.put("fwcs", "311735");
                map3.put("fwks", "21780");
                map3.put("xfkbl", "80%");
                map3.put("ips", "21651");
                map3.put("tcl", "89%");
                objects.add(map3);
                map3 = new HashMap<>();
                map3.put("id", "a");
                map3.put("lylx", "外部链接");
                map3.put("llbl", "45.38%");
                map3.put("fwcs", "418139");
                map3.put("fwks", "213780");
                map3.put("xfkbl", "37%");
                map3.put("ips", "21969");
                map3.put("tcl", "53.15%");
                objects.add(map3);
                break;
        }
        return objects;
    }

    /**
     * Tabledata 展开数据
     */
    private List<Object> getTableDataExpansion(String dateTime) {
        Map<String,List<Object>> mainMap = new HashMap<>();
        List<Object> objectsMain = new ArrayList<>();
        List<Object> objects = new ArrayList<>();
        switch (dateTime) {
            case "-1":
                for (int i = 0; i <= 6; i++) {
                    Map<String, String> map = new HashMap<>();
                    map.put("lylx", "http://tuiguang.baidu.com");
                    map.put("llbl", "63.32%");
                    map.put("fwcs", "170");
                    map.put("fwks", "115");
                    map.put("xfkbl", "72.78%");
                    map.put("ips", "158");
                    map.put("tcl", "95.54%");
                    objects.add(map);
                }
                mainMap.put("a",objects);
                objects = new ArrayList<>();
                for (int i = 0; i <= 6; i++) {
                    Map<String, String> map = new HashMap<>();
                    map.put("lylx", "百度");
                    map.put("llbl", "89.46%");
                    map.put("fwcs", "267");
                    map.put("fwks", "150");
                    map.put("xfkbl", "71.43%");
                    map.put("ips", "209");
                    map.put("tcl", "92.54%");
                    objects.add(map);
                }
                mainMap.put("b",objects);
                objects = new ArrayList<>();
                mainMap.put("c",objects);
                break;
            case "1":
                for (int i = 0; i <= 6; i++) {
                    Map<String, String> map = new HashMap<>();
                    map.put("lylx", "http://tuiguang.baidu.com");
                    map.put("llbl", "63.32%");
                    map.put("fwcs", "170");
                    map.put("fwks", "115");
                    map.put("xfkbl", "72.78%");
                    map.put("ips", "158");
                    map.put("tcl", "95.54%");
                    objects.add(map);
                }
                mainMap.put("a",objects);
                objects = new ArrayList<>();
                for (int i = 0; i <= 6; i++) {
                    Map<String, String> map = new HashMap<>();
                    map.put("lylx", "百度");
                    map.put("llbl", "89.46%");
                    map.put("fwcs", "267");
                    map.put("fwks", "150");
                    map.put("xfkbl", "71.43%");
                    map.put("ips", "209");
                    map.put("tcl", "92.54%");
                    objects.add(map);
                }
                mainMap.put("b",objects);
                objects = new ArrayList<>();
                mainMap.put("c",objects);
                break;
            case "7":
                for (int i = 0; i <= 6; i++) {
                    Map<String, String> map = new HashMap<>();
                    map.put("lylx", "http://tuiguang.baidu.com");
                    map.put("llbl", "63.32%");
                    map.put("fwcs", "170");
                    map.put("fwks", "115");
                    map.put("xfkbl", "72.78%");
                    map.put("ips", "158");
                    map.put("tcl", "95.54%");
                    objects.add(map);
                }
                mainMap.put("a",objects);
                objects = new ArrayList<>();
                for (int i = 0; i <= 6; i++) {
                    Map<String, String> map = new HashMap<>();
                    map.put("lylx", "百度");
                    map.put("llbl", "89.46%");
                    map.put("fwcs", "267");
                    map.put("fwks", "150");
                    map.put("xfkbl", "71.43%");
                    map.put("ips", "209");
                    map.put("tcl", "92.54%");
                    objects.add(map);
                }
                mainMap.put("b",objects);
                objects = new ArrayList<>();
                mainMap.put("c",objects);
                break;
            case "30":
                for (int i = 0; i <= 6; i++) {
                    Map<String, String> map = new HashMap<>();
                    map.put("lylx", "http://tuiguang.baidu.com");
                    map.put("llbl", "63.32%");
                    map.put("fwcs", "170");
                    map.put("fwks", "115");
                    map.put("xfkbl", "72.78%");
                    map.put("ips", "158");
                    map.put("tcl", "95.54%");
                    objects.add(map);
                }
                mainMap.put("a",objects);
                objects = new ArrayList<>();
                for (int i = 0; i <= 6; i++) {
                    Map<String, String> map = new HashMap<>();
                    map.put("lylx", "百度");
                    map.put("llbl", "89.46%");
                    map.put("fwcs", "267");
                    map.put("fwks", "150");
                    map.put("xfkbl", "71.43%");
                    map.put("ips", "209");
                    map.put("tcl", "92.54%");
                    objects.add(map);
                }
                mainMap.put("b",objects);
                objects = new ArrayList<>();
                mainMap.put("c",objects);
                break;
        }
        objectsMain.add(mainMap);
        return objectsMain;
    }
}
