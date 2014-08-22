package com.perfect.utils;

import com.perfect.utils.vo.CSVUrlEntity;
import com.perfect.utils.forkjoin.task.ExcelCheckUrlTask;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * Created by XiaoWei on 2014/8/12.
 */
public class ExcelReadUtil {

    @Resource
    private UploadHelper uploadHelper;

    public static XSSFWorkbook getBook(List<CSVUrlEntity> csvUrlEntityList) {
        XSSFWorkbook hwb = new XSSFWorkbook();

        XSSFSheet sheet = hwb.createSheet();
        hwb.setSheetName(0, "验证后Sheet");
        XSSFRow headRow = sheet.createRow(0);

        XSSFCell headCell = headRow.createCell(0);
        headCell.setCellValue("推广计划名称");
        XSSFCell headCell1 = headRow.createCell(1);
        headCell1.setCellValue("推广单元名称");
        XSSFCell headCell2 = headRow.createCell(2);
        headCell2.setCellValue("关键词名称");
        XSSFCell headCell3 = headRow.createCell(3);
        headCell3.setCellValue("关键词访问URL");
        XSSFCell headCell4 = headRow.createCell(4);
        headCell4.setCellValue("实际URL");

        if (csvUrlEntityList.size() > 0) {
            for (int i = 0; i < csvUrlEntityList.size(); i++) {
                XSSFRow row = sheet.createRow(i + 1);
                XSSFCell cell = row.createCell(0);
                cell.setCellValue(csvUrlEntityList.get(i).getPlanName());
                XSSFCell cell1 = row.createCell(1);
                cell1.setCellValue(csvUrlEntityList.get(i).getUnitName());
                XSSFCell cell2 = row.createCell(2);
                cell2.setCellValue(csvUrlEntityList.get(i).getKeyword());
                XSSFCell cell3 = row.createCell(3);
                cell3.setCellValue(csvUrlEntityList.get(i).getKeywordURL());
                XSSFCell cell4 = row.createCell(4);
                cell4.setCellValue(csvUrlEntityList.get(i).getFactURL());
            }
        }
        return hwb;
    }

    public static void checkUrl(String readFile) {
        List<CSVUrlEntity> list = getData(readFile);
        List<CSVUrlEntity> forkList = new LinkedList<>();
        ForkJoinPool pool = new ForkJoinPool();
        ExcelCheckUrlTask task = new ExcelCheckUrlTask(0, list.size(), list);
        Future<List<CSVUrlEntity>> future = pool.submit(task);
        try {
            forkList = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        XSSFWorkbook hwb = getBook(forkList);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(readFile);
            hwb.write(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean invokeSum(String session, String[] fileName) {
        Map<String, List<String>> fileMap = getMap(session, fileName);
        try {
            if (!session.equals("") || session != null) {
                List<String> fileNames = fileMap.get(session);
                    List<TotalEntity> totalEntities = getSource(fileNames.get(0));
                    for (int i=0;i<totalEntities.size();i++){
//                        System.out.println("账号："+totalEntities.get(i).getAccountName()+",本周："+totalEntities.get(i).getThisWeekPrice()+",上周"+totalEntities.get(i).getLastWeekPrice());
                    }

            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Map<String, List<String>> getMap(String session, String[] fileName) {
        Map<String, List<String>> map = new HashMap<>();
        List<String> fileNames = new ArrayList<>();
        UploadHelper upload = new UploadHelper();
        for (int i = 0; i < fileName.length; i++) {
            fileNames.add(upload.getTotalTempPath() + "\\" + fileName[i]);
        }
        map.put(session, fileNames);
        return map;
    }

    private static List<CSVUrlEntity> getData(String sourceFile) {
        List<CSVUrlEntity> ls = new LinkedList<>();
        try {
            XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(sourceFile));
            XSSFSheet sheet = xwb.getSheetAt(0);
            XSSFRow row;
            for (int i = sheet.getFirstRowNum() + 1; i < sheet.getLastRowNum() + 1; i++) {
                row = sheet.getRow(i);
                CSVUrlEntity csvUrls = new CSVUrlEntity();
                csvUrls.setLineNumber(i);
                csvUrls.setPlanName(row.getCell(0).toString());
                csvUrls.setUnitName(row.getCell(1).toString());
                csvUrls.setKeyword(row.getCell(2).toString());
                csvUrls.setKeywordURL(row.getCell(3).toString());
                ls.add(csvUrls);
            }

            return ls;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ls;
    }

    private List<TotalEntity> getSource(String sourceFile) {
        List<TotalEntity> totalEntities = new LinkedList<>();
        try {
            XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(sourceFile));
            int sheetIndex = xwb.getNumberOfSheets();
            if (sheetIndex > 0) {
                TotalEntity total=new TotalEntity();
                double thisWeekPrice=0.0;
                double lastWeekPrice=0.0;
                for (int h = 0; h < sheetIndex; h++) {
                    XSSFSheet sheet = xwb.getSheetAt(h);
                    XSSFRow row;
                    for (int i = sheet.getFirstRowNum() + 3; i < sheet.getLastRowNum(); i++) {
                        row = sheet.getRow(i);
                         TotalEntity totalEntity = new TotalEntity();
                        totalEntity.setAccountName(row.getCell(0).toString());
                        totalEntity.setThisWeekPrice(Double.parseDouble(row.getCell(1).toString()));
                        totalEntity.setLastWeekPrice(Double.parseDouble(row.getCell(2).toString()));
                        total.setAccountName(row.getCell(0).toString());
//                        thisWeekPrice+=Double.parseDouble(row.getCell(1).toString());
//                        lastWeekPrice+=Double.parseDouble(row.getCell(2).toString());
//                        totalEntities.add(totalEntity);

                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return totalEntities;
    }

    class TotalEntity {
        private String accountName;
        private double thisWeekPrice;
        private double lastWeekPrice;

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public double getThisWeekPrice() {
            return thisWeekPrice;
        }

        public void setThisWeekPrice(double thisWeekPrice) {
            this.thisWeekPrice = thisWeekPrice;
        }

        public double getLastWeekPrice() {
            return lastWeekPrice;
        }

        public void setLastWeekPrice(double lastWeekPrice) {
            this.lastWeekPrice = lastWeekPrice;
        }
    }

    public static void main(String[] args) {

    }

}
