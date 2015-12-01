package com.perfect.utils.excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by baizz on 2014-10-10.
 */
public class XSSFReadUtils {

    /**
     * apply to read big data
     *
     * @param file
     * @param handler
     * @throws Exception
     */
    public static void read(Path file, XSSFSheetHandler handler) throws Exception {
        final long size = Files.size(file);
        try (InputStream is = new BufferedInputStream(new FileInputStream(file.toFile()), size > Integer.MAX_VALUE ? 1024 * 1024 * 10 : (int) size)) {
            process(is, handler);
        }
    }

    /**
     * common read
     *
     * @param file
     * @param handler
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static void read(Path file, SheetHandler handler) throws IOException, InvalidFormatException {
        final long size = Files.size(file);
        try (InputStream is = new BufferedInputStream(new FileInputStream(file.toFile()), size > Integer.MAX_VALUE ? 1024 * 1024 * 10 : (int) size)) {
            OPCPackage pkg = OPCPackage.open(is);
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(pkg);
            List<XSSFSheet> sheets = new ArrayList<>();
            for (int i = 0; i < xssfWorkbook.getNumberOfSheets(); i++) {
                sheets.add(xssfWorkbook.getSheetAt(i));
            }

            sheets.stream().forEach(sheet -> sheet.forEach(row -> {
                List<Object> rowData = new ArrayList<>();
                row.forEach(cell -> {
                    XSSFCell xssfCell = (XSSFCell) cell;
                    rowData.add(xssfCell.getStringCellValue());
                });
                handler.rowMap(sheet.getSheetName(), row.getRowNum(), rowData);
            }));
        }
    }

    private static void process(InputStream is, XSSFSheetHandler handler) throws Exception {
        OPCPackage pkg = OPCPackage.open(is);
        XSSFReader reader = new XSSFReader(pkg);
        ReadOnlySharedStringsTable readOnlySharedStringsTable = new ReadOnlySharedStringsTable(pkg);
//        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        XMLReader parser = XMLReaderFactory.createXMLReader();

        handler.setReadOnlySharedStringsTable(readOnlySharedStringsTable);
        parser.setContentHandler(handler);

        for (Iterator<InputStream> iterator = reader.getSheetsData(); iterator.hasNext(); ) {
            try (InputStream sheetIn = iterator.next()) {
                parser.parse(new InputSource(sheetIn));
            }
        }
    }

    public interface SheetHandler {
        void rowMap(String sheetName, int rowIndex, List<Object> row);
    }
}
