package com.perfect.commons.bdpreview;

/**
 * Created by baizz on 2015-1-9.
 */
public interface CmdConstants {

    public static final String TMP_DIR = System.getProperty("java.io.tmpdir");

    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    public static final String PREVIEW_PATH = TMP_DIR + FILE_SEPARATOR + "biddingPreview" + FILE_SEPARATOR;

    public static final String SH_CMD = "sh ";

    public static final String SH_PREFIX = "#!/bin/sh\n";

    public static final String SH_SUFFIX = ".sh";

    public static final String HTML_SUFFIX = ".html";

    public static final String CURL_SUFFIX = " > " + PREVIEW_PATH + "%s.html";

    public static final String CURL_TEMPLATE = "curl 'http://fengchao.baidu.com/nirvana/request.ajax?path=nirvana/GET/Live' -H 'Cookie: SFSSID=%SFSSID%; SIGNIN_UC=%SIGNIN_UC%; uc_login_unique=%UC_LOGIN_UNIQUE%; __cas__st__3=%__CAS__ST__3%; __cas__id__3=%__CAS__ID__3%; __cas__rn__=%__CAS__RN__%; SAMPLING_USER_ID=%SAMPLING_USER_ID%' -H 'Origin: http://fengchao.baidu.com' -H 'Accept-Encoding: gzip, deflate' -H 'Accept-Language: zh-CN,zh;q=0.8' -H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36' -H 'Content-Type: application/x-www-form-urlencoded' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8' -H 'Cache-Control: max-age=0' -H 'Referer: http://fengchao.baidu.com/nirvana/main.html?userid=%USER_ID%&castk=%CASTK%' -H 'Connection: keep-alive' --data 'path=nirvana%2FGET%2FLive&params=%7B%22device%22%3A1%2C%22keyword%22%3A%22%KEYWORD%%22%2C%22area%22%3A%AREA_ID%%2C%22pageNo%22%3A0%7D&userid=%USER_ID%&token=%TOKEN%' --compressed";

    public static final String __CAS__ID__3 = "__CAS__ID__3";

    public static final String __CAS__ST__3 = "__CAS__ST__3";

    public static final String USER_ID = "%USER_ID%";

    public static final String TOKEN = "%TOKEN%";

    public static final String KEYWORD = "%KEYWORD%";

    public static final String AREA_ID = "%AREA_ID%";

}
