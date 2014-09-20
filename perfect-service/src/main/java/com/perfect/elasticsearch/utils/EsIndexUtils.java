package com.perfect.elasticsearch.utils;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by vbzer_000 on 2014/9/20.
 */
public class EsIndexUtils {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-es.xml");

        if (args.length != 2) {
            System.out.println("missing index name.");
            System.out.println("Usage: EsIndexUtils [index] [type]");
            return;
        }

        String indexName = args[0];

        String typeName = args[1];

        int start = 0;

        int size = 500;
        Client client = applicationContext.getBean(Client.class);

        IndicesExistsResponse indicesExistsResponse = client.admin().indices().prepareExists(indexName).get();
        if (!indicesExistsResponse.isExists()) {
            CreateIndexResponse response = client.admin().indices().prepareCreate(indexName).get();
            if (response == null) {
                return;
            }

            if (!response.isAcknowledged()) {
                return;
            }
            System.out.println("Index [" + indexName + "] created!");
        }else{
            System.out.println("Index [" + indexName + "] already exists!");
        }


        String scrollId = null;
        SearchResponse searchResponse = null;
        while (true) {
            if (scrollId == null) {
                searchResponse = client.prepareSearch("source").setQuery(QueryBuilders.matchAllQuery())
                        .setScroll(TimeValue
                                .timeValueMinutes
                                        (5)).setFrom(start).setSize(size).get();

            } else {
                searchResponse = client.prepareSearchScroll(scrollId).get();
            }

            if (searchResponse == null) {
                return;
            }

            if (searchResponse.getHits().getHits().length == 0) {
                return;
            }

            if(scrollId == null )
                scrollId = searchResponse.getScrollId();


            BulkRequestBuilder builder = client.prepareBulk();

            for (SearchHit searchHit : searchResponse.getHits()) {
                builder.add(client.prepareIndex(indexName, typeName).setSource(searchHit.getSource()));
                if (builder.numberOfActions() == 500) {
                    builder.execute();
                }
            }
        }

    }
}
