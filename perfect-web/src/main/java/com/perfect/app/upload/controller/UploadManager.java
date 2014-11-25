package com.perfect.app.upload.controller;

import com.google.common.io.Files;
import com.perfect.dao.KeywordDAO;
import com.perfect.entity.KeywordEntity;
import com.perfect.utils.CsvReadUtil;
import com.perfect.utils.ExcelReadUtil;
import com.perfect.utils.UploadHelper;
import com.perfect.commons.web.WebContextSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    /**
     * DEFAULT 默认在Perfect目录下
     * TOTAL Total目录下，表示是用户上传的需要统计的文件
     */
   public enum OutType {
        DEFAULT,
        TOTAL;
    }
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
    public ModelAndView convertUploadTotal() {
        return new ModelAndView("upload/uploadTotal");
    }

    /**
     * 加载一般文件
     *
     * @param response
     */
    @RequestMapping(value = "/tmpList")
    public void tmpList(HttpServletResponse response) {
        getFileList(OutType.DEFAULT,response);
    }
    /**
     * 加载统计文件
     *
     * @param response
     */
    @RequestMapping(value = "/talList")
    public void talList(HttpServletResponse response) {
        getFileList(OutType.TOTAL,response);
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

    @RequestMapping(value = "/upTotal",method = RequestMethod.POST)
    public void upTotal(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "file",required = false) MultipartFile file,String jsessionid) throws IOException {
        String fileName=file.getOriginalFilename();
        UploadHelper upload=new UploadHelper();
        if (upload.totalUpload(file.getBytes(),fileName)){
            writeHtml(SUCCESS, response);
        }else{
            writeHtml(EXCEPTION,response);
        }
    }
    @RequestMapping(value = "/getSum",method = RequestMethod.GET)
    public ModelAndView getSum(HttpServletRequest request,String fileNames,HttpServletResponse response){
        String[] fileListNames=fileNames.split(",");
        if (new ExcelReadUtil().invokeSum(request.getSession().getId(),fileListNames)){
            writeHtml(SUCCESS,response);
        }
        return null;
    }

    /**
     *  下载普通文件
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getDefault", method = RequestMethod.GET)
    public ModelAndView getDefault(HttpServletRequest request, HttpServletResponse response)  {
        download(OutType.DEFAULT, request, response);
        return null;
    }

    /**
     * 下载求和后的文件
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getTotal", method = RequestMethod.GET)
    public ModelAndView getTotal(HttpServletRequest request, HttpServletResponse response)  {
        download(OutType.TOTAL, request, response);
        return null;
    }

    /**
     *  下载文件方法
     * @param outType 下载的文件类型
     * @param request
     * @param response
     */
    private void download(OutType outType, HttpServletRequest request, HttpServletResponse response) {
        String fileName = request.getParameter("fileName");
        response.setContentType("text/html;charset=utf-8");
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        UploadHelper uh = new UploadHelper();
        String filePath =null;
        switch (outType){
            case DEFAULT:
                filePath = uh.getDefaultTempPath() + File.separator  + fileName;
                break;
            case TOTAL:
                filePath = uh.getTotalTempPath() +  File.separator  + fileName;
        }
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
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (bos != null)
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
    }
    /**
     * excel文件处理
     *
     * @param upload
     * @param file
     * @param response
     */
    private void excelSupport(UploadHelper upload, MultipartFile file, HttpServletResponse response) throws IOException {
        boolean bol = upload.defaultUpload(file.getBytes(), file.getOriginalFilename());
        if (bol) {
            String fileName = file.getOriginalFilename();
            ExcelReadUtil.checkUrl(new UploadHelper().getDefaultTempPath() + "/" + fileName);
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
        boolean bol = upload.defaultUpload(file.getBytes(), file.getOriginalFilename());
        if (bol) {
            String fileName = file.getOriginalFilename();
            CsvReadUtil csvReadUtil = new CsvReadUtil(new UploadHelper().getDefaultTempPath() + "/" + fileName, CsvReadUtil.ENCODING_GBK);
            List<KeywordEntity> keywordEntityList = csvReadUtil.getList();
//            keywordDAO.insertAndQuery(keywordEntityList);
        } else {
            writeHtml(EXCEPTION, response);
        }
    }


    /**
     * 获取文件列表
     * @param outType
     * @param response
     */
    private void getFileList(OutType outType,HttpServletResponse response){
        String filePath=null;
        switch (outType){
            case DEFAULT:
                filePath = new UploadHelper().getDefaultTempPath();
                break;
            case TOTAL:
                filePath = new UploadHelper().getTotalTempPath();
                break;
        }
        List<FileAttribute> fileAttributes = new ArrayList<>();
        File file = new File(filePath);
        for (File s : file.listFiles()) {
            String ext = Files.getFileExtension(s.getName());
            if (ext.equals("xlsx") || ext.equals("xls")) {
                FileAttribute fa = new FileAttribute();
                fa.setFileName(s.getName());
                fa.setFileSize(new UploadHelper().getFileSize(s.length()));
                fa.setFileExt("." + Files.getFileExtension(s.getName()));
                fileAttributes.add(fa);
            }
        }
        writeJson(fileAttributes, response);
    }

    /**
     * 删除普通文件
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/delDefault", method = RequestMethod.GET)
    public ModelAndView delDefaultFile(HttpServletRequest request, HttpServletResponse response) {
        deleteFile(OutType.DEFAULT,request,response);
        return null;
    }

    /**
     * 删除统计文件
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/delTotal", method = RequestMethod.GET)
    public ModelAndView delTotalFile(HttpServletRequest request, HttpServletResponse response) {
        deleteFile(OutType.TOTAL,request,response);
        return null;
    }



    /**
     * 删除文件方法
     * @param outType 删除文件目录
     * @param request
     * @param response
     */
    private void deleteFile(OutType outType,HttpServletRequest request,HttpServletResponse response){
        String fileName = request.getParameter("fileName");
        UploadHelper up = new UploadHelper();
        String filePath =null;
        switch (outType){
            case DEFAULT:
                filePath = up.getDefaultTempPath() + File.separator + fileName;
                break;
            case TOTAL:
                filePath = up.getTotalTempPath() +  File.separator  + fileName;
                break;
        }
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
            writeHtml(SUCCESS, response);
        } else {
            writeHtml(EXCEPTION, response);
        }
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
