package com.perfect.utils;

import com.google.common.io.Files;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;

/**
 * Created by XiaoWei on 2014/8/18.
 */
public class UploadHelper {
    private static String tempPath;

    public UploadHelper() {
        tempPath = System.getProperty("java.io.tmpdir") + "\\Perfect\\";
        existFileDirs(tempPath);
    }



    public static String getTempPath() {
        return tempPath;
    }

    public String getExt(MultipartFile file){
        String ext=null;
        try {
            byte[] bytes=file.getBytes();
            ext = Files.getFileExtension(file.getOriginalFilename());
            return ext;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ext;
    }

    public String getFileName(MultipartFile file){
        return file.getOriginalFilename();
    }
    /**
     * 执行上传操作
     * @param file
     * @return
     */
    public boolean upLoad(MultipartFile file) {
        byte[] bytes = new byte[0];
        try {
            bytes = file.getBytes();
            File dirPath = new File(tempPath);
            File uploadedFile = new File(tempPath + "\\" + file.getOriginalFilename());
            FileCopyUtils.copy(bytes, uploadedFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 检测缓存目录是否存在
     * @param tempPath
     */
    private static void existFileDirs(String tempPath) {
        File file = new File(tempPath);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
    }
}
