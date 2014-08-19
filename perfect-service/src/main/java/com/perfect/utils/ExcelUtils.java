package com.perfect.utils;

import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.XLSReader;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-08-18.
 */
@Component("excelUtils")
public class ExcelUtils<T> {

    protected Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @SuppressWarnings("unchecked")
    public Map<String, List<T>> readExcel(String srcFilePath, String templateFilePath, Class<T> _class) {
        try {
            Map<String, List<T>> map = new HashMap<>();
            //绑定XML文件
            XLSReader xlsReader = ReaderBuilder.buildFromXML(new FileInputStream(templateFilePath));
            //构建Excel文件高级输入流
            File file = new File(srcFilePath);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            T bean = _class.newInstance();
            List<T> beans = new ArrayList<>(0);

            Map<String, Object> beanParams = new HashMap<>();
            beanParams.put("bean", bean);
            beanParams.put("beans", beans);
            xlsReader.read(bis, beanParams);
            beans = new ArrayList<>((List<T>) beanParams.get("beans"));

            map.put(file.getName(), beans);
            return map;
        } catch (InstantiationException | IllegalAccessException | IOException | SAXException | InvalidFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

}
