package com.perfect.db.elasticsearch.core;

import com.alibaba.fastjson.JSON;
import com.perfect.utils.MD5;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.IdsQueryBuilder;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created on 2014-12-01.
 *
 * @author dolphineor
 */
public class EsTemplate implements EsRequest {

    protected TransportClient esClient;
    protected String index;
    protected String type;

    protected String[] md5Fields = null;    // 指定哪些属性用于计算MD5码作为ES的sourceId

    public EsTemplate(String index, String type, String[] md5Fields) {
        this.index = index;
        this.type = type;
        this.md5Fields = md5Fields;
        esClient = EsPools.getEsClient();
    }

    @Override
    public TransportClient getEsClient() {
        return this.esClient;
    }

    @Override
    public String index() {
        return this.index;
    }

    @Override
    public String type() {
        return this.type;
    }

    public EsTemplate setIndex(String index) {
        this.index = index;
        return this;
    }

    public EsTemplate setType(String type) {
        this.type = type;
        return this;
    }

    public void setMd5Fields(String[] md5Fields) {
        this.md5Fields = md5Fields;
    }

    public boolean save(Map<String, Object> source) {
        IndexRequestBuilder indexRequestBuilder = getIndexRequestBuilder();
        indexRequestBuilder.setSource(JSON.toJSONString(source));

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
                IndexRequestBuilder indexRequestBuilder = getIndexRequestBuilder().setSource(JSON.toJSONString(s));
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
        SearchRequestBuilder searchRequestBuilder = getEsClient().prepareSearch(index()).setTypes(type());
        IdsQueryBuilder idsQueryBuilder = new IdsQueryBuilder();
        idsQueryBuilder.queryName(IDS).ids(id);
        SearchResponse response = searchRequestBuilder.setQuery(idsQueryBuilder).get();
        long hits = response.getHits().getTotalHits();
        return hits == 1;
    }

    public long count() {
        SearchRequestBuilder countRequestBuilder = getCountRequestBuilder();
        return countRequestBuilder.get().getHits().getTotalHits();
    }

    public void delete(String id) {
        DeleteRequestBuilder deleteRequestBuilder = getDeleteRequestBuilder();
        deleteRequestBuilder.setId(id);
        deleteRequestBuilder.get();
    }


    protected String getMD5(Map<String, Object> source) {
        Objects.requireNonNull(md5Fields);

        StringBuilder keyBuilder = new StringBuilder();
        for (int i = 0, l = md5Fields.length; i < l; i++) {
            keyBuilder.append(source.get(md5Fields[i]).toString());
        }

        MD5.Builder md5 = new MD5.Builder();
        md5.source(keyBuilder.toString()).salt(SALT);
        return md5.build().getMD5();
    }

}
