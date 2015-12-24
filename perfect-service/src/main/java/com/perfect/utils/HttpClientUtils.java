package com.perfect.utils;

import com.google.common.collect.Lists;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.imageio.IIOException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;


/**
 * Created by subdong on 15-12-21.
 */
public class HttpClientUtils {

    private static final String HTTP_URL = "http://192.168.1.104:8000/config/site_list?";

    private final HttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();

    private HttpClientUtils() {
    }

    private static class LazyHolder {
        private static final HttpClientUtils INSTANCE = new HttpClientUtils();
    }

    private static HttpClientUtils getInstance() {
        return LazyHolder.INSTANCE;
    }


    private HttpClientConnectionManager getConnManager() {
        return connManager;
    }

    public static String postRequest(String url, Map<String, Object> params) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(HTTP_URL + url);

            List<NameValuePair> postParams = Lists.<NameValuePair>newArrayList(params
                    .entrySet()
                    .stream()
                    .map(p -> new BasicNameValuePair(p.getKey(), p.getValue().toString()))
                    .collect(Collectors.toList())
            );
            httpPost.setEntity(new UrlEncodedFormEntity(postParams, UTF_8));
            CloseableHttpResponse response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity(), UTF_8);
            } else {
                return null;
            }
        }
    }

    public static String getRequest(String url) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(HTTP_URL + url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity(), UTF_8);
            } else {
                return null;
            }
        }
    }
}
