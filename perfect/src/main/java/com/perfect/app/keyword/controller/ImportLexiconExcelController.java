package com.perfect.app.keyword.controller;

import com.perfect.entity.LexiconEntity;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.utils.excel.RowMapper;
import com.perfect.utils.excel.XSSFUtils;
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
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by baizz on 2014-10-9.
 */
@RestController
@Scope("prototype")
@RequestMapping("/admin/lexicon")
public class ImportLexiconExcelController {

    static String trade;

    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ModelAndView importLexicon(HttpServletRequest request, @RequestParam(value = "excelFile") MultipartFile[] files)
            throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("/");
        if (url == null) {
            return null;
        }
        final String rootPath = url.getPath();

        //上传临时文件
        String tempFile = "";
        String fileName = "";
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                fileName = file.getOriginalFilename();
                File _file = new File(rootPath, fileName);
                FileUtils.copyInputStreamToFile(file.getInputStream(), _file);
                tempFile = rootPath + fileName;
            }
        }
        if (StringUtils.isEmpty(tempFile)) {
            return null;
        }

        trade = fileName.substring(0, fileName.indexOf("."));
        trade = trade.substring(0, trade.length() - 2);
        Path file = Paths.get(tempFile);
        final List<LexiconEntity> list = new ArrayList<>();
        XSSFUtils.read(file, new RowMapper() {
            @Override
            protected void mapRow(int sheetIndex, int rowIndex, List<Object> row) {
                LexiconEntity lexiconEntity = new LexiconEntity();
                lexiconEntity.setTrade(trade);
                if (!row.isEmpty() && row.size() == 3) {
                    lexiconEntity.setCategory(row.get(0).toString());
                    lexiconEntity.setGroup(row.get(1).toString());
                    lexiconEntity.setKeyword(row.get(2).toString());
                    list.add(lexiconEntity);
                }

            }

        });

        //delete tempFile
        Files.deleteIfExists(Paths.get(tempFile));

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
