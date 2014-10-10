package com.perfect.app.keyword.controller;

import com.perfect.entity.LexiconEntity;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.utils.ExcelUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-10-9.
 */
@RestController
@Scope("prototype")
@RequestMapping("/admin/lexicon")
public class ImportLexiconExcelController {

    static final String lexiconTemplatePath = "lexicon/lexiconTemplate.xml";
    static String trade;

    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ModelAndView importLexicon(HttpServletRequest request, @RequestParam(value = "excelFile") MultipartFile[] files)
            throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("/");
        if (url == null) {
            return null;
        }
        String rootPath = url.getPath();

        //上传临时文件
        String tempFile = "";
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                File _file = new File(rootPath, fileName);
                FileUtils.copyInputStreamToFile(file.getInputStream(), _file);
                tempFile = rootPath + fileName;
            }
        }
        if (StringUtils.isEmpty(tempFile)) {
            return null;
        }

        String lexiconTemplatePath = url.getPath() + ImportLexiconExcelController.lexiconTemplatePath;
        Map<String, List<LexiconEntity>> map = ExcelUtils.readExcel(tempFile, lexiconTemplatePath, LexiconEntity.class);

        //delete tempFile
        Files.deleteIfExists(Paths.get(tempFile));

        List<LexiconEntity> list = new ArrayList<>();
        for (Map.Entry<String, List<LexiconEntity>> entry : map.entrySet()) {
            trade = entry.getKey();
            list = entry.getValue();
        }
        trade = trade.substring(0, trade.indexOf("."));
        trade = trade.substring(0, trade.length() - 2);

//        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() + 1);
//        try {
//            LexiconTask task = new LexiconTask(list, 0, list.size() - 1);
//            pool.invoke(task);
//        } finally {
//            pool.shutdown();
//        }

        return new ModelAndView();
    }

    @RequestMapping(value = "/delete/{trade}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView deleteLexicon(@PathVariable("trade") String trade) throws UnsupportedEncodingException {
        trade = java.net.URLDecoder.decode(trade, "UTF-8");
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        mongoTemplate.remove(Query.query(Criteria.where("tr").is(trade)), LexiconEntity.class);
        return new ModelAndView();
    }

}
