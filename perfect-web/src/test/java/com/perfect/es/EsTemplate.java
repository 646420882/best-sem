package com.perfect.es;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String getMD5(Map<String, Object> source) {
        String key = (String) source.get("name") + source.get("category");
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
