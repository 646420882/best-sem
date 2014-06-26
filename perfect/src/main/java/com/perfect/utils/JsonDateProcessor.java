package com.perfect.utils;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * Created by baizz on 2014-6-5.
 */
public class JsonDateProcessor implements JsonValueProcessor {
    private static final String DEFAULT_JSON_VALUE_PROCESSOR_DATE_MATCHER = "yyyy-M-d";

    private java.text.DateFormat dateFormat = null;

    public JsonDateProcessor() {
        this(DEFAULT_JSON_VALUE_PROCESSOR_DATE_MATCHER);
    }

    public JsonDateProcessor(String dateFormat) {
        try {
            this.dateFormat = new java.text.SimpleDateFormat(dateFormat);
        } catch (Exception e) {
            this.dateFormat = new java.text.SimpleDateFormat(DEFAULT_JSON_VALUE_PROCESSOR_DATE_MATCHER);
        }
    }

    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        return process(value);
    }

    public Object processObjectValue(String paramString, Object value, JsonConfig jsonConfig) {
        return process(value);
    }

    private Object process(Object value) {
        if (value instanceof java.sql.Timestamp)
            return dateFormat.format((java.sql.Timestamp) value);
        else if (value instanceof java.sql.Date)
            return dateFormat.format((java.sql.Date) value);
        else if (value instanceof java.util.Date)
            return dateFormat.format((java.util.Date) value);
        else if (value == null)
            return "";
        else
            return value.toString();
    }
}
