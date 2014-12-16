package com.perfect.commons.bdlogin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by baizz on 2014-11-8.
 */
public class CaptchaHandler extends AbstractBaiduHttpClient {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final CaptchaHandler instance = new CaptchaHandler();

    private static byte[] captchaBytes;
    private static String _cookies;

    private static CaptchaHandler getInstance() {
        return instance;
    }

    public static void handle(String phantomJSPath) throws IOException {
        clearCookie();
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("phantomjs " + phantomJSPath);
        InputStream is = process.getInputStream();
        JsonNode jsonNodes = mapper.readTree(is);
        List<String> cookieList = new ArrayList<>();
        for (JsonNode jsonNode : jsonNodes) {
            cookieList.add(jsonNode.get("name").asText() + "=" + jsonNode.get("value").asText() + "; " + "path=" + jsonNode.get("path").asText() + "; domain=" + jsonNode.get("domain").asText() + "; " + "httponly");
        }
        for (String strCookie : cookieList) {
            _cookies += strCookie.substring(0, strCookie.indexOf(";")) + "; ";
        }
        _cookies = _cookies.substring(0, _cookies.lastIndexOf(";"));

        CloseableHttpClient sslHttpClient = getInstance().createSSLClientDefault(true);
        HttpGet captchaRequest = new HttpGet(captchaURL + System.currentTimeMillis());
        getInstance().headerWrap(captchaRequest);

        captchaRequest.addHeader("Cookie", _cookies);
        HttpResponse httpResponse;

        httpResponse = sslHttpClient.execute(captchaRequest);
        if (httpResponse.getHeaders("Set-Cookie") != null) {
            String tmpStr = httpResponse.getHeaders("Set-Cookie")[0].getValue();
            tmpStr = tmpStr.substring(0, tmpStr.indexOf(";"));
            _cookies += "; " + tmpStr;
        }
        HttpEntity entity = httpResponse.getEntity();
        InputStream content = entity.getContent();
        captchaBytes = IOUtils.toByteArray(content);
//        FileUtils.writeByteArrayToFile(new File("/home/baizz/captcha.jpeg"), captchaBytes);
        EntityUtils.consume(entity);
        sslHttpClient.close();
    }

    public static byte[] getCaptchaBytes() {
        return captchaBytes;
    }

    public static String getCaptchaCookies() {
        return _cookies;
    }

    private static void clearCookie() {
        captchaBytes = new byte[0];
        _cookies = "";
    }
}
