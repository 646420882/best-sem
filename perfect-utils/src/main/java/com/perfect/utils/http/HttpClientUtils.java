package com.perfect.utils.http;

import com.google.common.collect.Lists;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created on 2015-12-07.
 *
 * @author dolphineor
 */
public class HttpClientUtils {

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
            HttpPost httpPost = new HttpPost(url);

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
}
