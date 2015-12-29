package com.perfect.app.keyword.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.primitives.Bytes;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.service.KeywordService;
import com.perfect.service.SysKeywordService;
import com.perfect.utils.MD5;
import com.perfect.utils.excel.HSSFReadUtils;
import com.perfect.utils.excel.XSSFReadUtils;
import com.perfect.utils.excel.XSSFSheetHandler;
import com.perfect.utils.json.JSONUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-7-8.
 * 2014-11-28 refactor
 */
@RestController
@Scope("prototype")
@RequestMapping(value = "/keyword")
public class KeywordController {

    @Resource
    private KeywordService keywordService;

    @Resource
    private SysKeywordService sysKeywordService;

    private static final String TMP_DIR = System.getProperty("java.io.tmpdir");
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");

    @RequestMapping(value = "/getKeywordByAdgroupId/{adgroupId}", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getKeywordByAdgroupId(@PathVariable Long adgroupId,
                                              @RequestParam(value = "skip", required = false, defaultValue = "0") int skip,
                                              @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        List<KeywordDTO> list = keywordService.getKeywordByAdgroupId(adgroupId, null, skip, limit);
        return new ModelAndView(getJsonView(JSONUtils.getJsonMapData(list)));
    }

    @RequestMapping(value = "/all/{adgroupId}", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getAllKeywordsByAdgroupdId(@PathVariable Long adgroupId) {
        List<KeywordDTO> list = sysKeywordService.findByAdgroupId(adgroupId, null, null);
        return new ModelAndView(getJsonView(JSONUtils.getJsonMapData(list)));
    }

    @RequestMapping(value = "/getKeywordIdByAdgroupId/{adgroupId}", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getKeywordIdByAdgroupId(@PathVariable Long adgroupId) {
        List<Long> list = keywordService.getKeywordIdByAdgroupId(adgroupId);
        return new ModelAndView(getJsonView(JSONUtils.getJsonMapData(list)));
    }

    @RequestMapping(value = "/showAll", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView findByPage(@RequestParam(value = "skip", required = false, defaultValue = "0") int skip,
                                   @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        List<KeywordDTO> list = keywordService.find(null, skip, limit);
        return new ModelAndView(getJsonView(JSONUtils.getJsonMapData(list)));
    }

    @RequestMapping(value = "/getKeywordByKeywordId/{keywordId}", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView getKeywordByKeywordId(@PathVariable Long keywordId) {
        KeywordDTO keywordDTO = keywordService.findOne(keywordId);
        return new ModelAndView(getJsonView(JSONUtils.getJsonMapData(new KeywordDTO[]{keywordDTO})));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView add(@RequestBody List<KeywordDTO> list) {
        List<String> stringList = keywordService.insertAll(list);
        return new ModelAndView(getJsonView(JSONUtils.getJsonMapData(stringList)));
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void importLexicon(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(value = "file") MultipartFile file,
                              @RequestParam(value = "adgroupId") String adgroupId,
                              @RequestParam(value = "price") String price,
                              @RequestParam(value = "matchType") String matchType,
                              @RequestParam(value = "phraseType") String phraseType) {

        String tmpDirPath = TMP_DIR + FILE_SEPARATOR;
        try {
            //上传临时文件
            String tmpFile;
            String fileName;
            String XLSX = "xlsx";
            if (!file.isEmpty()) {
                fileName = file.getOriginalFilename();
                int point = fileName.lastIndexOf(".");
                String type = fileName.substring(point);

                if (type.equals(".xls")) {
                    XLSX = "xls";
                }
                MD5.Builder md5Builder = new MD5.Builder();
                MD5 md5 = md5Builder.source(fileName.replace("." + XLSX, "")).salt(XLSX).build();
                fileName = md5.getMD5();

                File _file = new File(tmpDirPath, fileName);
                FileUtils.copyInputStreamToFile(file.getInputStream(), _file);
                tmpFile = tmpDirPath + fileName;

            } else {
                return;
            }
            if (StringUtils.isEmpty(tmpFile)) {
                return;
            }

            List<KeywordDTO> keyword = new ArrayList<>();
            if (!XLSX.equals("xls")) {
                Path tempFile = Paths.get(tmpFile);
                XSSFReadUtils.read(tempFile, new XSSFSheetHandler() {
                    @Override
                    protected void rowMap(int sheetIndex, int rowIndex, List<Object> row) {
                        if (!row.isEmpty()) {
                            row.forEach(e -> {
                                KeywordDTO keywordDTO = restructuring(e.toString(), adgroupId, price, matchType, phraseType);
                                keyword.add(keywordDTO);
                            });
                        }
                    }
                });
            } else {
                List<String> read = HSSFReadUtils.read(tmpFile);
                read.forEach(e -> {
                    KeywordDTO keywordDTO = restructuring(e, adgroupId, price, matchType, phraseType);
                    keyword.add(keywordDTO);
                });
            }

            List<String> stringList = keywordService.insertAll(keyword);
            //delete tmpFile
            Files.delete(Paths.get(tmpFile));

            String jsonResult = JSON.toJSONString(stringList);
            String b = "<script type='text/javascript'>parent.callbackKwd('" + jsonResult + "')</script>";
            response.getWriter().write(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/{keywordId}/update", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView update(@PathVariable Long keywordId, @RequestParam(value = "keywordEntity") String keywordStr) {
        KeywordDTO keywordDTO = JSONUtils.getObjectByJson(keywordStr, KeywordDTO.class);
        keywordService.save(keywordDTO);
        return new ModelAndView(getJsonView(null));
    }

    @RequestMapping(value = "/update/{field}/{value}", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView updateMulti(@PathVariable String field,
                                    @PathVariable Object value,
                                    @RequestParam(value = "seedWord") String seedWord) {
        keywordService.updateMulti(field, seedWord, value);
        return new ModelAndView(getJsonView(null));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView updateMultiKeyword(@RequestParam(value = "ids") Long[] ids,
                                           @RequestParam(value = "price", required = false) Double price,
                                           @RequestParam(value = "pcUrl", required = false) String pcUrl) {
        if (price != null) {
            BigDecimal _price = new BigDecimal(price);
            keywordService.updateMultiKeyword(ids, _price, null);
        }
        if (pcUrl != null) {
            keywordService.updateMultiKeyword(ids, null, pcUrl);
        }
        return new ModelAndView(getJsonView(null));
    }

    @RequestMapping(value = "/{keywordId}/del", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView deleteById(@PathVariable Long keywordId) {
        keywordService.delete(keywordId);
        return new ModelAndView(getJsonView(null));
    }

    @RequestMapping(value = "/deleteMulti", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView deleteByIds(@RequestBody List<Long> keywordIds) {
        keywordService.deleteByIds(keywordIds);
        return new ModelAndView(getJsonView(null));
    }

    @RequestMapping(value = "/downTemplate", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downTemplate(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filename = "关键词模板.xls";
            response.addHeader("Content-Disposition", "attachment;filename=" + new String((filename).getBytes("GBK"), "ISO8859-1"));
            OutputStream os = response.getOutputStream();
            os.write(Bytes.concat(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF}, ("关键词名称(必填)").getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private View getJsonView(Map<String, Object> attributes) {
        AbstractView jsonView = new MappingJackson2JsonView();
        if (attributes == null) {
            jsonView.setAttributesMap(new HashMap<String, Object>(1) {{
                put("stat", true);
            }});
            return jsonView;
        }

        jsonView.setAttributesMap(attributes);
        return jsonView;
    }

    private KeywordDTO restructuring(String keword, String adgroupId, String price, String matchType, String phraseType) {
        KeywordDTO keywordDTO = new KeywordDTO();
        if (adgroupId.length() < 24) {
            keywordDTO.setAdgroupId(Long.parseLong(adgroupId));
        } else {
            keywordDTO.setAdgroupObjId(adgroupId);
        }
        keywordDTO.setKeyword(keword);
        if (price == null || price.equals("")) {
            keywordDTO.setPrice(BigDecimal.valueOf(0.1));
        } else {
            keywordDTO.setPrice(BigDecimal.valueOf(Double.parseDouble(price)));
        }
        keywordDTO.setMatchType(Integer.parseInt(matchType));
        keywordDTO.setPause(false);
        keywordDTO.setStatus(-1);
        keywordDTO.setPhraseType(Integer.parseInt(phraseType));
        keywordDTO.setLocalStatus(1);
        return keywordDTO;
    }
}