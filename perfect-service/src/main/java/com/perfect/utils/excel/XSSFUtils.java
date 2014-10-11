package com.perfect.utils.excel;

import org.apache.poi.openxml4j.opc.OPCPackage;
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

    public static SheetContentsHandler read(Path file) throws Exception {
        SheetContentsHandler sheetHandler = new SheetContentsHandler((int) (Files.size(file) / 50));
        read(file, sheetHandler);
        return sheetHandler;
    }

    public static void read(Path file, RowMapper mapper) throws Exception {
        final long size = Files.size(file);
        try (InputStream is = new BufferedInputStream(new FileInputStream(file.toFile()), size > Integer.MAX_VALUE ? 1024 * 1024 * 10 : (int) size)) {
            process(is, mapper);
        }
    }

    static void process(InputStream is, RowMapper mapper) throws Exception {
        XSSFReader reader = new XSSFReader(OPCPackage.open(is));
        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");

        mapper.setSharedStringsTable(reader.getSharedStringsTable());
        parser.setContentHandler(mapper);

        for (Iterator<InputStream> iterator = reader.getSheetsData(); iterator.hasNext(); ) {
            try (InputStream sheetIn = iterator.next()) {
                parser.parse(new InputSource(sheetIn));
            }
        }
    }
}
