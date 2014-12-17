package com.perfect.es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by baizz on 2014-12-1.
 */
public class EsPool {

    private static TransportClient esClient = null;
    private static Map<String, String> esMap = new HashMap<>();

    static {
        if (esClient == null)
            esClient = initEsClient();
    }

    private EsPool() {
    }

    //retrieve an es client instance
    public static TransportClient getEsClient() {
        if (esClient == null) {
            synchronized (EsPool.class) {
                if (esClient == null)
                    esClient = initEsClient();
            }
        }

        return esClient;
    }

    private static TransportClient initEsClient() {
        TransportClient client = null;
        try {
            String path = new File(EsPool.class.getResource("/").getPath()).getPath() + System.getProperty("file.separator") + "es.properties";
            InputStream is = new FileInputStream(path);
            Properties properties = new Properties();
            properties.load(is);
            String host = properties.getProperty("es.host");
            int port = Integer.valueOf(properties.getProperty("es.port"));
            String clusterName = properties.getProperty("es.clusterName");

            //设置client.transport.sniff为true来使客户端去嗅探整个集群的状态, 把集群中其它机器的ip地址加到客户端中
            Settings settings = ImmutableSettings.settingsBuilder().put(esMap).put("cluster.name", clusterName).put("client.transport.sniff", true).build();
            Class<?> clazz = Class.forName(TransportClient.class.getName());
            Constructor<?> constructor = clazz.getDeclaredConstructor(Settings.class);
            constructor.setAccessible(true);
            client = (TransportClient) constructor.newInstance(settings);
            client.addTransportAddress(new InetSocketTransportAddress(host, port));
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return client;
    }

}
