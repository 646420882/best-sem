package com.perfect.app.batchUpload.controller;

import com.google.common.io.Files;
import com.perfect.dao.KeywordDAO;
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
    KeywordDAO keywordDAO;

    /**
     * 跳转至Excel文件上传
     *
     * @return
     */
    @RequestMapping(value = "/uploadManager")
    public ModelAndView convertUploadExcel() {
        return new ModelAndView("upload/uploadMain");
    }
    @RequestMapping(value = "/uploadTotal")
    public ModelAndView convertUploadTotal(){return new ModelAndView("upload/uploadTotal");}

    /**
     * 加载处理好的文件
     *
     * @param response
     */
    @RequestMapping(value = "/tmpList")
    public void tmpList(HttpServletResponse response) {
        String filePath = new UploadHelper().getTempPath();
        List<FileAttribute> fileAttributes = new ArrayList<>();
        File file = new File(filePath);
        for (File s : file.listFiles()) {
            String ext = Files.getFileExtension(s.getName());
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
        String ext = upload.getExt(file.getOriginalFilename());
        if (ext.equals("xls") || ext.equals("xlsx")) {
            excelSupport(upload, file, response);
        } else if (ext.equals("csv")) {
            csvSupport(upload, file, response);
        }
    }

    /**
     * 获取下载文件
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ModelAndView getIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = request.getParameter("fileName");
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        UploadHelper uh = new UploadHelper();
        String filePath = uh.getTempPath() + "\\" + fileName;
        System.out.println(filePath);
        try {
            long fileLength = uh.getFileLength(filePath);
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1") + "");
            response.setHeader("Content-Length", String.valueOf(fileLength));
//            FileCopyUtils.copy(bytes, response.getOutputStream());
            bis = new BufferedInputStream(new FileInputStream(filePath));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[2048];
            int byteRead;
            while (-1 != (byteRead = bis.read(buffer, 0, buffer.length))) {
                bos.write(buffer, 0, buffer.length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();

        }
        return null;
    }

    /**
     * excel文件处理
     *
     * @param upload
     * @param file
     * @param response
     */
    private void excelSupport(UploadHelper upload, MultipartFile file, HttpServletResponse response) throws IOException {
        boolean bol = upload.upLoad(file.getBytes(),file.getOriginalFilename());
        if (bol) {
            String fileName =file.getOriginalFilename();
            ExcelReadUtil.checkUrl(new UploadHelper().getTempPath() + "/" + fileName);
            writeHtml(SUCCESS, response);
        } else {
            writeHtml(EXCEPTION, response);
        }
    }

    /**
     * csv文件处理
     *
     * @param upload
     * @param file
     * @param response
     */
    private void csvSupport(UploadHelper upload, MultipartFile file, HttpServletResponse response) throws IOException {
        boolean bol = upload.upLoad(file.getBytes(),file.getOriginalFilename());
        if (bol) {
            String fileName = file.getOriginalFilename();
            CsvReadUtil csvReadUtil = new CsvReadUtil(new UploadHelper().getTempPath() + "/" + fileName, CsvReadUtil.ENCODING_GBK);
            List<KeywordEntity> keywordEntityList = csvReadUtil.getList();
            keywordDAO.insertAndQuery(keywordEntityList);
            writeHtml(SUCCESS, response);
        } else {
            writeHtml(EXCEPTION, response);
        }

    }

    /**
     * 删除没用文件
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public ModelAndView delFile(HttpServletRequest request, HttpServletResponse response) {
        String fileName = request.getParameter("fileName");
        UploadHelper up = new UploadHelper();
        String filePath = up.getTempPath() + "\\" + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
            writeHtml(SUCCESS, response);
        } else {
            writeHtml(EXCEPTION, response);
        }
        return null;
    }

    /**
     * 文件属性实体类
     */
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
