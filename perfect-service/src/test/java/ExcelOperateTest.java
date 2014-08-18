import com.perfect.utils.vo.CSVUrlEntity;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by XiaoWei on 2014/8/12.
 */
public class ExcelOperateTest {

    private List<CSVUrlEntity> ls = new LinkedList<>();

    public ExcelOperateTest(String sourceFile) {

        try {
            XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(sourceFile));
            XSSFSheet sheet = xwb.getSheetAt(0);
            XSSFRow row;
            String cell;
            for (int i = sheet.getFirstRowNum() + 1; i < sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                CSVUrlEntity csvUrls = new CSVUrlEntity();
                csvUrls.setLineNumber(i);
                csvUrls.setKeyword(row.getCell(0).toString());
                csvUrls.setKeywordURL(row.getCell(1).toString());
                csvUrls.setFactURL(row.getCell(2).toString());
                ls.add(csvUrls);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<CSVUrlEntity> getData() {
        return ls;
    }

    public void writeExcel(String readFile,String outFile){
        List<CSVUrlEntity> list=getData();
        XSSFWorkbook hwb=new XSSFWorkbook();
        XSSFSheet sheet=hwb.createSheet();
        hwb.setSheetName(0,"验证后Sheet");
        XSSFRow headRow=sheet.createRow(0);
        XSSFCell headCell=headRow.createCell(0);
        headCell.setCellValue("关键词名称");
        XSSFCell headCell1=headRow.createCell(1);
        headCell1.setCellValue("关键词访问URL");
        XSSFCell headCell2=headRow.createCell(2);
        headCell2.setCellValue("实际URL");
        for (int i=0;i<list.size();i++){
            XSSFRow row=sheet.createRow(i+1);
            XSSFCell cell=row.createCell(0);
            cell.setCellValue(list.get(i).getKeyword());
            XSSFCell cell1=row.createCell(1);
            cell1.setCellValue(list.get(i).getKeywordURL());
            XSSFCell cell2=row.createCell(2);
            cell2.setCellValue(list.get(i).getFactURL());

        }
        try {
            FileOutputStream fos=new FileOutputStream(outFile);
            hwb.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] agrs) {
//        String readFile = "D:/temp/files/aaa.xlsx";
//        String outFile="D:/temp/files/bbb.xlsx";
//        ExcelOperateTest excelOperateTest = new ExcelOperateTest(readFile);
////        List<CSVUrlEntity> lst = excelOperateTest.getData();
////        for (CSVUrlEntity s : lst) {
////            System.out.println(s.getLineNumber()+"---"+s.getKeyword()+"---"+s.getKeywordURL()+"---"+s.getFactURL());
////        }
//        excelOperateTest.writeExcel(readFile,outFile);
        String path = System.getProperty("java.io.tmpdir");
//        File file = new File(path);
//        if (!file.isDirectory()) {
//            file.mkdirs();
//        }
//        try {
//            FileOutputStream fos = new FileOutputStream(path + "aa.txt");
//            byte[] bytes = new byte[1024];
//            String aa = "123";
//            bytes = aa.getBytes();
//            fos.write(bytes);
//            fos.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println(path);
    }
    }
