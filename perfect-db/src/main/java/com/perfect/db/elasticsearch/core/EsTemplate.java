package com.perfect.db.elasticsearch.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfect.utils.MD5;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.count.CountRequestBuilder;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.deletebyquery.DeleteByQueryRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.*;

/**
 * Created by baizz on 2014-12-1.
 */
public class EsTemplate implements EsRequest {

    private static final ObjectMapper mapper = new ObjectMapper();

    protected TransportClient esClient;
    protected String index;
    protected String type;

    public EsTemplate(String index, String type) {
        this.index = index;
        this.type = type;
        esClient = EsPool.getEsClient();
    }

    @Override
    public TransportClient getEsClient() {
        return esClient;
    }

    @Override
    public String index() {
        Objects.requireNonNull(index);
        return index;
    }

    @Override
    public String type() {
        Objects.requireNonNull(type);
        return type;
    }

    public List<Map<String, Object>> findAll() {
        SearchRequestBuilder searchRequestBuilder = getSearchRequestBuilder();
        SearchResponse response = searchRequestBuilder.setQuery(QueryBuilders.matchAllQuery()).get();
        if (response.getHits().getTotalHits() != 1) {
            return Collections.emptyList();
        }

        List<Map<String, Object>> results = new ArrayList<>();
        response.getHits().forEach(s -> results.add(s.getSource()));
        return results;
    }

    public boolean save(Map<String, Object> source) {
        IndexRequestBuilder indexRequestBuilder = getIndexRequestBuilder();
        indexRequestBuilder.setSource(toJsonString(source));

        String id = getMD5(source);
        if (!exists(id)) {
            indexRequestBuilder.setId(id);
            IndexResponse indexResponse = indexRequestBuilder.get();
            return indexResponse.isCreated();
        }

        return false;
    }

    public boolean save(List<Map<String, Object>> sourceList) {
        BulkRequestBuilder bulkRequestBuilder = getBulkRequestBuilder();
        sourceList.stream().forEach(s -> {
            String id = getMD5(s);
            if (!exists(id)) {
                IndexRequestBuilder indexRequestBuilder = getIndexRequestBuilder().setSource(toJsonString(s));
                indexRequestBuilder.setId(id);
                bulkRequestBuilder.add(indexRequestBuilder);
            }
        });
        if (bulkRequestBuilder.numberOfActions() == 0) {
            return false;
        }
        BulkResponse bulkResponse = bulkRequestBuilder.get();
        return !bulkResponse.hasFailures();
    }

    public boolean exists(String id) {
        SearchRequestBuilder searchRequestBuilder = getEsClient().prepareSearch(getIndex()).setTypes(getType());
        IdsQueryBuilder idsQueryBuilder = new IdsQueryBuilder();
        idsQueryBuilder.queryName(IDS).ids(id);
        SearchResponse response = searchRequestBuilder.setQuery(idsQueryBuilder).get();
        long hits = response.getHits().getTotalHits();
        return hits == 1;
    }

    public long count() {
        CountRequestBuilder countRequestBuilder = getCountRequestBuilder();
        return countRequestBuilder.get().getCount();
    }

    public void delete(String id) {
        DeleteRequestBuilder deleteRequestBuilder = getDeleteRequestBuilder();
        deleteRequestBuilder.setId(id);
        deleteRequestBuilder.get();
    }

    public void deleteAll(String index) {
        DeleteByQueryRequestBuilder deleteByQueryRequestBuilder = getDeleteByQueryRequestBuilder();
        deleteByQueryRequestBuilder.setIndices(index).setQuery(QueryBuilders.matchAllQuery());
        deleteByQueryRequestBuilder.get();
    }

    public String getIndex() {
        return index;
    }

    public EsTemplate setIndex(String index) {
        this.index = index;
        return this;
    }

    public String getType() {
        return type;
    }

    public EsTemplate setType(String type) {
        this.type = type;
        return this;
    }

    private String getMD5(Map<String, Object> source) {
        String key = "";
        switch (index) {
            case "data":
                key = (String) source.get("title") + source.get("html");
                break;
            case "datakw":
                key = (String) source.get("name") + source.get("category");
                break;
            default:
                break;
        }

        MD5.Builder md5 = new MD5.Builder();
        md5.password(key);
        md5.salt(SALT);
        return md5.build().getMD5();
    }

    private String toJsonString(Object o) {
        Objects.requireNonNull(o);

        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

}
