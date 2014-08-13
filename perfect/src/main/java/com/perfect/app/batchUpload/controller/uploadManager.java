package com.perfect.app.batchUpload.controller;

import com.perfect.app.batchUpload.vo.FileMeta;
import com.perfect.dao.CSVKeywordDAO;
import com.perfect.entity.CSVEntity;
import com.perfect.entity.CSVUrlEntity;
import com.perfect.utils.CsvLinkedSupport;
import com.perfect.utils.ReadCsvUtil;
import com.perfect.utils.web.WebContextSupport;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by XiaoWei on 2014/8/4.
 */
@Controller
@RequestMapping("/upload")
 public class uploadManager extends WebContextSupport {
    private final static String DIR = "D:/temp/files/";
    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;
    @Resource
    CSVKeywordDAO cSVKeywordDAO;


    /**
     * 跳转至批量上传页面
     *
     * @return
     */
    @RequestMapping(value = "/uploadIndex")
    public ModelAndView convertUploadPage() {
        return new ModelAndView("upload/uploadMain");
    }

    /**
     * 上传文件处理方法
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/up", method = RequestMethod.POST)
    public
    @ResponseBody
    LinkedList<FileMeta> upload(MultipartHttpServletRequest request, HttpServletResponse response) {
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = null;
        String fileName = null;
        while (itr.hasNext()) {
            mpf = request.getFile(itr.next());
            if (files.size() >= 10)
                files.pop();
            fileMeta = new FileMeta();
            fileMeta.setFileName(mpf.getOriginalFilename());
            fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
            fileMeta.setFileType(mpf.getContentType());
            try {
                fileMeta.setBytes(mpf.getBytes());
                fileName = mpf.getOriginalFilename();
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(DIR + mpf.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            files.add(fileMeta);
            String realPath = DIR + fileName;

            /**
             * 获取CSVEntity
             */
            ReadCsvUtil p = new ReadCsvUtil(realPath, "GBK");
//            List<CSVEntity> list = p.getList();
            List<CSVUrlEntity> csvUrlEntityList=p.getUrlList();
//            CsvLinkedSupport.saveToDB(list);
//            cSVKeywordDAO.insertAll(list);
            for (CSVUrlEntity cu:csvUrlEntityList){
                System.out.println(cu.getKeyword()+">>");
            }
        }
        return files;
    }

    @RequestMapping(value = "/get/{value}", method = RequestMethod.GET)
    public void get(HttpServletResponse response, @PathVariable String value) {
        FileMeta getFile = files.get(Integer.parseInt(value));
        try {
            response.setContentType(getFile.getFileType());
            response.setHeader("Content-disposition", "attachment; filename=\"" + getFile.getFileName() + "\"");
            FileCopyUtils.copy(getFile.getBytes(), response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
