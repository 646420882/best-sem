package com.perfect.utils;

import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.XLSReader;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-08-18.
 */
public class ExcelUtils {

    protected static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * @param srcFilePath
     * @param templateFilePath
     * @param _class
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, List<T>> readExcel(String srcFilePath, String templateFilePath, Class<T> _class) {
        try {
            Map<String, List<T>> map = new HashMap<>();
            //绑定XML文件
            if (logger.isInfoEnabled()) {
                logger.info("binding excel template xml ... ");
            }
            XLSReader xlsReader = ReaderBuilder.buildFromXML(Files.newInputStream(Paths.get(templateFilePath)));

            if (logger.isInfoEnabled()) {
                logger.info("loading excel srcFile ... ");
            }
            Path path = Paths.get(srcFilePath);
            if (logger.isInfoEnabled()) {
                logger.info("construct inputStream ... ");
            }
            InputStream is = Files.newInputStream(path, StandardOpenOption.READ);
            T bean = _class.newInstance();
            List<T> beans = new ArrayList<>(0);

            if (logger.isInfoEnabled()) {
                logger.info("starting read excel ... ");
            }
            Map<String, Object> beanParams = new HashMap<>();
            beanParams.put("bean", bean);
            beanParams.put("beans", beans);
            xlsReader.read(is, beanParams);
            beans = new ArrayList<>((List<T>) beanParams.get("beans"));
            if (logger.isInfoEnabled()) {
                logger.info("read finish !!! ");
            }

            map.put(path.getFileName().toString(), beans);
            is.close();
            return map;
        } catch (InstantiationException | IllegalAccessException | IOException | SAXException | InvalidFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

}