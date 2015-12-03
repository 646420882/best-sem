package com.perfect.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by subdong on 15-12-3.
 */
public class HSSFReadUtils {

    /**
     * apply to read big data
     *
     * @param file
     * @throws Exception
     */
    public static List<String> read(String file) {
        List<String> list = new ArrayList<>();
        try (POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file)); HSSFWorkbook wb = new HSSFWorkbook(fs)) {
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                HSSFSheet sheet = wb.getSheetAt(i);
                if (sheet.getLastRowNum() == 0) continue;
                for (int a = 0; a <= sheet.getLastRowNum(); a++) {
                    HSSFRow row = sheet.getRow(a);
                    if(row == null) continue;
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        list.add(cellIterator.next().toString());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
