package com.perfect.db.elasticsearch.core;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created on 2014-12-01.
 *
 * @author dolphineor
 */
public class EsPools {

    private EsPools() {
    }


    private static class LazyHolder {
        private static final TransportClient INSTANCE = getInstance();
    }

    public static TransportClient getEsClient() {
        return LazyHolder.INSTANCE;
    }


    private static TransportClient getInstance() {
        ResourceBundle bundle = ResourceBundle.getBundle("elasticsearch");
        String[] hosts = bundle.getString("es.host").split(",");
        List<InetSocketTransportAddress> addressList = new ArrayList<>();
        for (String host : hosts) {
            String[] arr = host.split(":");
            if (arr.length == 1) {
                addressList.add(new InetSocketTransportAddress(new InetSocketAddress(arr[0], 9300)));
            } else if (arr.length == 2) {
                addressList.add(new InetSocketTransportAddress(new InetSocketAddress(arr[0], Integer.parseInt(arr[1]))));
            }


        }
        String clusterName = bundle.getString("es.cluster");

        //设置client.transport.sniff为true来使客户端去嗅探整个集群的状态, 把集群中其它机器的ip地址加到客户端中

        Settings.Builder settings = Settings.builder()
                .put("cluster.name", clusterName)
                .put("client.transport.sniff", true)
                .put("client.transport.ignore_cluster_name", false)
                .put("client.transport.ping_timeout", "10s")
                .put("client.transport.nodes_sampler_interval", "15s");

        TransportClient client = TransportClient.builder().settings(settings).build();
        client.addTransportAddresses(addressList.toArray(new InetSocketTransportAddress[addressList.size()]));

        return client;
    }

}
