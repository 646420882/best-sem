package com.perfect.utils;

import com.google.common.io.Files;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.text.DecimalFormat;

/**
 * Created by XiaoWei on 2014/8/18.
 */
public class UploadHelper {
    private static String tempPath;

    public UploadHelper() {
        tempPath = System.getProperty("java.io.tmpdir") + "\\Perfect\\";
        existFileDirs(tempPath);
    }

    public String getTempPath() {
        return tempPath;
    }

    public String getExt(MultipartFile file) {
        return Files.getFileExtension(file.getOriginalFilename());
    }

    public String getFileName(MultipartFile file) {
        return file.getOriginalFilename();
    }

    /**
     * 执行上传操作
     *
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
     *
     * @param tempPath
     */
    private static void existFileDirs(String tempPath) {
        File file = new File(tempPath);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 获得指定文件的byte数组
     *
     * @param filePath
     * @return
     */
    public static byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }


    /**
     *  获取文件大小
     * @param fileS
     * @return
     */
    public String getFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "Kb";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "Mb";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "Git";
        }
        return fileSizeString;
    }
}
