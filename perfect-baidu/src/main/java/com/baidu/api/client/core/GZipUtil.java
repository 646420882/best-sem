/**
 * ZipUtil.java
 * <p>
 * Copyright 2011 Baidu, Inc.
 * <p>
 * Baidu licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.baidu.api.client.core;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.JavaType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipInputStream;

/**
 * @author
 * @author@ (@author-email@)
 * @version@, $Date: 2011-3-28$
 */
public abstract class GZipUtil {

    public static byte[] unGzip(byte[] data) throws IOException {
        try (GZIPInputStream zin = new GZIPInputStream(
                new ByteArrayInputStream(data)); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            data = new byte[10240];
            int len;
            while ((len = zin.read(data)) != -1) {
                out.write(data, 0, len);
            }
            return out.toByteArray();
        }
    }

    public static byte[] unzip(byte[] data) throws IOException {
        byte[] b = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ZipInputStream zip = new ZipInputStream(bis);
            while (zip.getNextEntry() != null) {
                byte[] buf = new byte[1024];
                int num = -1;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while ((num = zip.read(buf, 0, buf.length)) != -1) {
                    baos.write(buf, 0, num);
                }
                b = baos.toByteArray();
                baos.flush();
                baos.close();
            }
            zip.close();
            bis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    public static byte[] gzip(byte[] data) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream zout = new GZIPOutputStream(out);
        zout.write(data);
        zout.close();
        return out.toByteArray();
    }

    public static byte[] gzipString(String str) throws IOException {
        byte[] data = str.getBytes("UTF-8");
        return gzip(data);
    }

    public static String unGzipString(byte[] data) throws IOException {
        data = unGzip(data);
        return new String(data, "UTF-8");
    }

    public static <T> T readObj(byte[] data, Class<T> valueType)
            throws JsonParseException, JsonMappingException, IOException {
        try (GZIPInputStream zin = new GZIPInputStream(
                new ByteArrayInputStream(data))) {
            return JacksonUtil.readObj(zin, valueType);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T readObj(byte[] data, JavaType valueType)
            throws JsonParseException, JsonMappingException, IOException {
        try (GZIPInputStream zin = new GZIPInputStream(
                new ByteArrayInputStream(data))) {
            return (T) JacksonUtil.readObj(zin, valueType);
        }
    }
}
