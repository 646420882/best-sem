package com.perfect.app.batchUpload.controller;

import com.google.common.io.Files;
import com.perfect.dao.CSVKeywordDAO;
import com.perfect.entity.KeywordEntity;
import com.perfect.utils.CsvReadUtil;
import com.perfect.utils.ExcelReadUtil;
import com.perfect.utils.UploadHelper;
import com.perfect.utils.web.WebContextSupport;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XiaoWei on 2014/8/4.
 */
@Controller
@RequestMapping("/upload")
public class UploadManager extends WebContextSupport {

    @Resource
    CSVKeywordDAO cSVKeywordDAO;

    /**
     * 跳转至Excel文件上传
     *
     * @return
     */
    @RequestMapping(value = "/uploadManager")
    public ModelAndView convertUploadExcel() {
        return new ModelAndView("upload/uploadMain");
    }

    @RequestMapping(value = "/tmpList")
    public void tmpList(HttpServletResponse response) {
        String filePath = new UploadHelper().getTempPath();
        List<FileAttribute> fileAttributes = new ArrayList<>();
        File file = new File(filePath);
        for (File s : file.listFiles()) {
            String ext=Files.getFileExtension(s.getName());
            if (ext.equals("xlsx") || ext.equals("xls")) {
                FileAttribute fa = new FileAttribute();
                fa.setFileName(s.getName());
                fa.setFileSize(new UploadHelper().getFileSize(s.length()));
                fa.setFileExt(Files.getFileExtension(s.getName()));
                fileAttributes.add(fa);
            }
        }
        wirteJson(fileAttributes, response);
    }


    /**
     * 上传文件处理
     *
     * @param response   响应
     * @param request    请求
     * @param file       文件流
     * @param jsessionid session
     * @throws IOException IO异常
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public void uploadFile(HttpServletResponse response, HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file, String jsessionid) throws IOException {
        UploadHelper upload = new UploadHelper();
        String ext = upload.getExt(file);
        if (ext.equals("xls") || ext.equals("xlsx")) {
            excelSupport(upload, file, response);
        }
        else if (ext.equals("csv")) {
            csvSupport(upload, file, response);
        }

    }
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public void getIndex(HttpServletRequest request,HttpServletResponse response, String value){
        UploadHelper uh=new UploadHelper();
        String filePath=uh.getTempPath()+"/"+value;
        System.out.println(filePath+">><");
        byte[] bytes=uh.getBytes(filePath);
        try {
            response.setContentType(Files.getFileExtension(value));
            response.setHeader("Content-disposition", "attachment; filename=\"" + value + "\"");
            FileCopyUtils.copy(bytes, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void excelSupport(UploadHelper upload, MultipartFile file, HttpServletResponse response) {
        boolean bol = upload.upLoad(file);
        if (bol) {
            String fileName = upload.getFileName(file);
            ExcelReadUtil.checkUrl(new UploadHelper().getTempPath() + "/" + fileName);
            writeHtml(SUCCESS, response);
        } else {
            writeHtml(EXCEPTION, response);
        }
    }

    private void csvSupport(UploadHelper upload, MultipartFile file, HttpServletResponse response) {
        boolean bol = upload.upLoad(file);
        if (bol) {
            String fileName = upload.getFileName(file);
            CsvReadUtil csvReadUtil = new CsvReadUtil(new UploadHelper().getTempPath() + "/" + fileName, CsvReadUtil.ENCODING_GBK);
            List<KeywordEntity> keywordEntityList = csvReadUtil.getList();
            cSVKeywordDAO.insertAll(keywordEntityList);
            writeHtml(SUCCESS, response);
        } else {
            writeHtml(EXCEPTION, response);
        }

    }

    class FileAttribute {
        private String fileName;
        private String fileSize;
        private String fileExt;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileSize() {
            return fileSize;
        }

        public void setFileSize(String fileSize) {
            this.fileSize = fileSize;
        }

        public String getFileExt() {
            return fileExt;
        }

        public void setFileExt(String fileExt) {
            this.fileExt = fileExt;
        }
    }

}
