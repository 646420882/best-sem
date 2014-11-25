package com.perfect.utils.excel;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

/**
 * Created by baizz on 2014-10-10.
 */
public class XSSFUtils {

    public static void read(Path file, XSSFSheetHandler handler) throws Exception {
        final long size = Files.size(file);
        try (InputStream is = new BufferedInputStream(new FileInputStream(file.toFile()), size > Integer.MAX_VALUE ? 1024 * 1024 * 10 : (int) size)) {
            process(is, handler);
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
}
