package com.perfect.commons.bdpreview;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by baizz on 2015-1-9.
 */
public class CmdHandler implements CmdConstants {

    static {
        if (Files.notExists(Paths.get(PREVIEW_PATH))) {
            try {
                Files.createDirectory(Paths.get(PREVIEW_PATH));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * <p>create curl
     *
     * @param cookieMap
     * @param keyword
     * @param area
     * @return
     */
    public static String createCurl(Map<String, String> cookieMap, String keyword, int area) {
        String curl = CURL_TEMPLATE;
        for (Map.Entry<String, String> entry : cookieMap.entrySet()) {
            switch (entry.getKey()) {
                case __CAS__ID__3:
                    curl = curl.replace("%" + __CAS__ID__3 + "%", entry.getValue()).replaceAll(USER_ID, entry.getValue());
                    break;
                case __CAS__ST__3:
                    curl = curl.replace("%" + __CAS__ST__3 + "%", entry.getValue()).replace(TOKEN, entry.getValue());
                    break;
                default:
                    curl = curl.replace("%" + entry.getKey() + "%", entry.getValue());
                    break;
            }
        }

        curl = curl.replace(KEYWORD, keyword).replace(AREA_ID, area + "");

        return curl;
    }

    /**
     * <p>create shell
     *
     * @param curl
     * @return
     * @throws IOException
     */
    public static String createShell(String curl) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String cmdContent = SH_PREFIX + curl + String.format(CURL_SUFFIX, uuid);
        Files.write(Paths.get(PREVIEW_PATH + uuid + SH_SUFFIX), Lists.newArrayList(cmdContent), StandardOpenOption.CREATE_NEW);
        return uuid;
    }

    /**
     * <p>execute shell
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String executeShell(String fileName) throws IOException {
        String cmd = SH_CMD + PREVIEW_PATH + fileName + SH_SUFFIX;
        Process process = Runtime.getRuntime().exec(cmd);

        while (process.isAlive()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        final StringBuilder html = new StringBuilder("");
        Files.lines(Paths.get(PREVIEW_PATH + fileName + HTML_SUFFIX), StandardCharsets.UTF_8).forEach(html::append);
        return html.toString();
    }

    /**
     * <p>delete temp file
     *
     * @param fileName
     * @throws IOException
     */
    public static void deleteTempFile(String fileName) throws IOException {
        String shPath = PREVIEW_PATH + fileName + SH_SUFFIX, htmlPath = PREVIEW_PATH + fileName + HTML_SUFFIX;
        Files.delete(Paths.get(shPath));
        Files.delete(Paths.get(htmlPath));
    }

}
