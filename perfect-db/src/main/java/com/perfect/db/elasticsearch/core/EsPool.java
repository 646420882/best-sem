package com.perfect.db.elasticsearch.core;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

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
//            InputStream is = EsPool.class.getClassLoader().getResourceAsStream("es.properties");
            ResourceBundle bundle = ResourceBundle.getBundle("elasticsearch");
            String host = bundle.getString("es.host");
            int port = Integer.valueOf(host.substring(host.indexOf(":") + 1, host.length()));
            host = host.substring(0, host.indexOf(":"));
            String clusterName = bundle.getString("es.clusterName");

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
