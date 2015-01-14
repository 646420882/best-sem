package com.perfect.commons.bdlogin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by baizz on 2015-1-14.
 */
class JavascriptFileHelper {

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");

    private static final String TMP_DIR = System.getProperty("java.io.tmpdir") + FILE_SEPARATOR;

    private static final String JAVASCRIPT_PREFIX = "baiduLogin";

    private static final String JAVASCRIPT_SUFFIX = ".js";

    private static final byte[] JAVASCRIPT_CONTENT = ("var system = require('system');\n" +
            "var url = \"http://www2.baidu.com\";\n" +
            "\n" +
            "var page = require('webpage').create();\n" +
            "page.settings.loadImages = false;\n" +
            "page.settings.userAgent = 'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:33.0) Gecko/20100101 Firefox/33.0';\n" +
            "page.settings.resourceTimeout = 5000;\n" +
            "\n" +
            "page.open(url, function (status) {\n" +
            "    if (status != 'success') {\n" +
            "        console.log(\"HTTP request failed!\");\n" +
            "    } else {\n" +
            "        console.log(JSON.stringify(page.cookies));\n" +
            "    }\n" +
            "\n" +
            "    page.close();\n" +
            "    phantom.exit();\n" +
            "});").getBytes();

    private static final Path JAVASCRIPT_PATH = Paths.get(TMP_DIR + JAVASCRIPT_PREFIX + JAVASCRIPT_SUFFIX);


    public static Path getJavascriptPath() throws IOException {
        Files.write(JAVASCRIPT_PATH, JAVASCRIPT_CONTENT);
        return JAVASCRIPT_PATH;
    }
}
