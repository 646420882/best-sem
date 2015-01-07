package com.perfect.es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.*;

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
            String[] hosts = properties.getProperty("es.host").split(",");
            List<InetSocketTransportAddress> addressList = new ArrayList<>();
            for (String host : hosts) {
                String[] arr = host.split(":");
                if (arr.length == 1)
                    addressList.add(new InetSocketTransportAddress(arr[0], 19300));
                else if (arr.length == 2)
                    addressList.add(new InetSocketTransportAddress(arr[0], Integer.valueOf(arr[1])));

            }
            String clusterName = properties.getProperty("es.cluster");

            //设置client.transport.sniff为true来使客户端去嗅探整个集群的状态, 把集群中其它机器的ip地址加到客户端中
            Settings settings = ImmutableSettings.settingsBuilder().put(esMap).put("cluster.name", clusterName).put("client.transport.sniff", true).build();
            Class<?> clazz = Class.forName(TransportClient.class.getName());
            Constructor<?> constructor = clazz.getDeclaredConstructor(Settings.class);
            constructor.setAccessible(true);
            client = (TransportClient) constructor.newInstance(settings);
            client.addTransportAddresses(addressList.toArray(new InetSocketTransportAddress[addressList.size()]));
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return client;
    }

}
