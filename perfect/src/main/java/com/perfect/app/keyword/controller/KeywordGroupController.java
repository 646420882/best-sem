package com.perfect.app.keyword.controller;

import com.perfect.service.KeywordGroupService;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-08-08.
 */
@RestController
@Scope("prototype")
@RequestMapping(value = "/getKRWords")
public class KeywordGroupController {

    @Resource
    private KeywordGroupService keywordGroupService;

    /**
     * 从百度获取关键词
     *
     * @param seedWords
     * @param skip
     * @param limit
     * @param krFileId
     * @return
     */
    @RequestMapping(value = "/bd", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getKeywordFromBaidu(@RequestParam(value = "seedWords", required = false) String seedWords,
                                            @RequestParam(value = "skip", required = false, defaultValue = "0") int skip,
                                            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                                            @RequestParam(value = "krFileId", required = false) String krFileId) {
        List<String> seedWordList = new ArrayList<>(Arrays.asList(seedWords.split(",")));
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attributes = keywordGroupService.getKeywordFromBaidu(seedWordList, skip, limit, krFileId);
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }

    /**
     * 从系统词库获取关键词
     *
     * @param trade
     * @param category
     * @param skip
     * @param limit
     * @return
     */
    @RequestMapping(value = "/p", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getKeywordFromPerfect(@RequestParam(value = "trade", required = false) String trade,
                                              @RequestParam(value = "category", required = false) String category,
                                              @RequestParam(value = "skip", required = false, defaultValue = "0") Integer skip,
                                              @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                              @RequestParam(value = "status", required = false, defaultValue = "0") Integer status) {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        Map<String, Object> values = keywordGroupService.getKeywordFromPerfect(trade, category, skip, limit, status);
        jsonView.setAttributesMap(values);
        return new ModelAndView(jsonView);
    }

    /**
     * 获取行业词库下的类别
     *
     * @param trade
     * @return
     */
    @RequestMapping(value = "/getCategories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getCategories(@RequestParam(value = "trade") String trade) {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        Map<String, Object> values = keywordGroupService.findCategories(trade);
        jsonView.setAttributesMap(values);
        return new ModelAndView(jsonView);
    }

    /**
     * 获取百度CSV文件下载路径
     *
     * @param krFileId
     * @return
     */
    @RequestMapping(value = "/getBaiduCSVFilePath", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getBaiduCSVFilePath(@RequestParam(value = "krFileId") String krFileId) {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        Map<String, Object> values = keywordGroupService.getBaiduCSVFilePath(krFileId);
        jsonView.setAttributesMap(values);
        return new ModelAndView(jsonView);
    }

    /**
     * 下载CSV词库文件
     *
     * @param response
     * @param trade
     * @param category
     * @return
     */
    @RequestMapping(value = "/downloadCSV", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ModelAndView downloadCSV(HttpServletResponse response,
                                    @RequestParam(value = "trade", required = false) String trade,
                                    @RequestParam(value = "category", required = false) String category) {
        String filename = trade + ".csv";
        OutputStream os = null;
        try {
            response.addHeader("Content-Disposition", "attachment;filename=" + new String((filename).getBytes("UTF-8"), "ISO8859-1"));
            os = response.getOutputStream();
            keywordGroupService.downloadCSV(trade, category, os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
