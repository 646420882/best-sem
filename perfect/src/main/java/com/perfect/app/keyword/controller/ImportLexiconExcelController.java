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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by baizz on 2014-10-9.
 */
@RestController
@Scope("prototype")
@RequestMapping("/admin/lexicon")
public class ImportLexiconExcelController {

    static String trade;

    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void importLexicon(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(value = "excelFile") MultipartFile[] files)
            throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("/");
        if (url == null) {
            return;
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
            return;
        }

        trade = fileName.substring(0, fileName.indexOf("."));
        trade = trade.substring(0, trade.length() - 2);
        Path file = Paths.get(tempFile);
        final Map<String, LexiconEntity> map = new HashMap<>(1 << 16);
        XSSFUtils.read(file, new RowMapper() {
            @Override
            protected void mapRow(int sheetIndex, int rowIndex, List<Object> row) {
                LexiconEntity lexiconEntity = new LexiconEntity();
                lexiconEntity.setTrade(trade);
                if (!row.isEmpty() && row.size() == 3) {
                    String keyword = row.get(2).toString();
                    lexiconEntity.setCategory(row.get(0).toString());
                    lexiconEntity.setGroup(row.get(1).toString());
                    lexiconEntity.setKeyword(keyword);
                    map.put(trade + keyword, lexiconEntity);
                }

            }

        });
        List<LexiconEntity> list = new ArrayList<>(map.values());

        //delete tempFile
        Files.deleteIfExists(Paths.get(tempFile));

        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() + 1);
        try {
            LexiconTask task = new LexiconTask(list, 0, list.size() - 1);
            pool.invoke(task);
        } finally {
            pool.shutdown();
        }

        response.getWriter().write("<script type='text/javascript'>parent.callback('true')</script>");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView deleteLexiconByTrade(@RequestParam(value = "trade") String trade,
                                             @RequestParam(value = "category", required = false) String category)
            throws IOException {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        Query query = new Query();
        if (category != null && category.length() > 0) {
            query.addCriteria(Criteria.where("tr").is(trade).and("cg").is(category));
        } else {
            query.addCriteria(Criteria.where("tr").is(trade));
        }
//        mongoTemplate.remove(query, LexiconEntity.class);
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> result = new HashMap<String, Object>() {{
            put("status", true);
        }};
        jsonView.setAttributesMap(result);
        return new ModelAndView(jsonView);
    }

}
