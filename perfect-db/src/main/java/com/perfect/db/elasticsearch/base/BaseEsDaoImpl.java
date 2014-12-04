package com.perfect.db.elasticsearch.base;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.BaseDTO;
import com.perfect.utils.json.JSONUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.count.CountRequestBuilder;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.deletebyquery.DeleteByQueryRequestBuilder;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yousheng on 14/12/4.
 */
public abstract class BaseEsDaoImpl<T extends BaseDTO, ID extends Serializable> implements HeyCrudRepository<T, ID> {

    @Resource
    private Client esClient;

    public abstract String getIndex();

    public abstract String getType();

    public abstract String getId();

    public abstract Class<ID> getIdClass();

    @Override
    public T save(T dto) {
        Map source = JSONUtils.getJsonMapData(dto);
        IndexRequest indexRequest = esClient.prepareIndex().setIndex(getIndex()).setType(getType()).setSource(source).request();
        esClient.index(indexRequest);
        return dto;
    }

    @Override
    public Iterable<T> save(Iterable<T> entities) {

        BulkRequestBuilder bulkRequestBuilder = esClient.prepareBulk();

        for (T t : entities) {
            Map source = JSONUtils.getJsonMapData(t);
            bulkRequestBuilder.add(esClient.prepareIndex().setIndex(getIndex()).setType(getType()).setSource(source).request());
        }

        esClient.bulk(bulkRequestBuilder.request());
        return entities;
    }

    @Override
    public T findOne(ID id) {

        SearchRequestBuilder searchRequestBuilder = esClient.prepareSearch(getIndex());

        IdsQueryBuilder builder = new IdsQueryBuilder();
        builder.queryName("ids").ids(id.toString());

        SearchResponse response = searchRequestBuilder.setQuery(builder).get();

        long hits = response.getHits().getTotalHits();
        if (hits != 1) {
            return null;
        }

        SearchHit searchHit = response.getHits().getHits()[0];

        return JSONUtils.getObjectByJson(searchHit.getSourceAsString(), getDTOClass());
    }

    @Override
    public boolean exists(ID id) {


        SearchRequestBuilder searchRequestBuilder = esClient.prepareSearch(getIndex()).setTypes(getType());

        IdsQueryBuilder builder = new IdsQueryBuilder();
        builder.queryName("ids").ids(id.toString());

        SearchResponse response = searchRequestBuilder.setQuery(builder).get();

        long hits = response.getHits().getTotalHits();
        return hits == 1;

    }

    @Override
    public Iterable<T> findAll() {

        SearchRequestBuilder searchRequestBuilder = esClient.prepareSearch(getIndex());

        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();

        SearchResponse response = searchRequestBuilder.setQuery(matchAllQueryBuilder).get();

        long hits = response.getHits().getTotalHits();
        if (hits != 1) {
            return null;
        }

        List<T> outputList = new ArrayList<>();
        for (SearchHit searchHit : response.getHits()) {
            T dto = JSONUtils.getObjectByJson(searchHit.getSourceAsString(), getDTOClass());
            outputList.add(dto);
        }


        return outputList;
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {
        SearchRequestBuilder searchRequestBuilder = esClient.prepareSearch(getIndex());

        IdsQueryBuilder builder = new IdsQueryBuilder();


        List<String> idList = new ArrayList<>();

        ids.forEach((id) -> {
            idList.add(id.toString());
        });
        builder.queryName("ids").ids(idList.toArray(new String[]{}));

        SearchResponse response = searchRequestBuilder.setQuery(builder).get();

        long hits = response.getHits().getTotalHits();
        if (hits != 1) {
            return null;
        }

        List<T> outputList = new ArrayList<>();
        for (SearchHit searchHit : response.getHits()) {
            T dto = JSONUtils.getObjectByJson(searchHit.getSourceAsString(), getDTOClass());
            outputList.add(dto);
        }

        return outputList;
    }

    @Override
    public long count() {
        CountRequestBuilder countRequestBuilder = esClient.prepareCount(getIndex()).setTypes(getType());
        CountResponse response = countRequestBuilder.setQuery(QueryBuilders.matchAllQuery()).get();
        return response.getCount();
    }

    @Override
    public boolean delete(ID id) {
        DeleteResponse response = esClient.prepareDelete().setIndex(getIndex()).setType(getType()).setId(id.toString()).get();

        return response.isFound();
    }

    @Override
    public boolean delete(T entity) {
        DeleteResponse response = esClient.prepareDelete().setIndex(getIndex()).setType(getType()).setId(entity.getId()).get();

        return response.isFound();
    }

    @Override
    public int delete(Iterable<? extends T> entities) {

        BulkRequestBuilder bulkRequestBuilder = esClient.prepareBulk();

        for (T t : entities) {
            bulkRequestBuilder.add(esClient.prepareDelete().setIndex(getIndex()).setType(getType()).setId(t.getId()).request());
        }

        BulkResponse responses = bulkRequestBuilder.get();

        return responses.getItems().length;
    }

    @Override
    public boolean deleteAll() {
        DeleteIndexResponse deleteIndexResponse = esClient.admin().indices().prepareDelete(getIndex()).get();

        return deleteIndexResponse.isAcknowledged();
    }

    @Override
    public int deleteByIds(List<ID> ids) {

        DeleteByQueryRequestBuilder deleteByQueryRequestBuilder = esClient.prepareDeleteByQuery(getIndex());


        IdsQueryBuilder idsQueryBuilder = QueryBuilders.idsQuery(getType()).addIds(ids.toArray(new String[]{}));


        DeleteByQueryResponse responses = deleteByQueryRequestBuilder.setQuery(idsQueryBuilder).get();

        return responses.getIndex(getIndex()).getSuccessfulShards();
    }

    @Override
    public List<T> find(Map<String, Object> params, int skip, int limit) {

        SearchRequestBuilder searchRequestBuilder = esClient.prepareSearch(getIndex()).setTypes(getType()).setSize(skip + limit);

        BoolQueryBuilder booleanQueryBuilder = QueryBuilders.boolQuery();

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            booleanQueryBuilder.must(QueryBuilders.termQuery(entry.getKey(), entry.getValue()));
        }

        SearchResponse response = searchRequestBuilder.setQuery(booleanQueryBuilder).get();

        long hits = response.getHits().getTotalHits();
        if (hits != 1) {
            return null;
        }

        List<T> outputList = new ArrayList<>();
        int idx = 0;
        for (SearchHit searchHit : response.getHits()) {
            if (++idx <= skip) {
                continue;
            }
            T dto = JSONUtils.getObjectByJson(searchHit.getSourceAsString(), getDTOClass());
            outputList.add(dto);
        }

        return outputList;
    }

    @Override
    public List<T> find(Map<String, Object> params, int skip, int limit, String sort, boolean asc) {

        SearchRequestBuilder searchRequestBuilder = esClient.prepareSearch(getIndex()).setTypes(getType())
                .setSize(skip + limit).addSort(SortBuilders.fieldSort(sort).order((asc) ? SortOrder.ASC : SortOrder.DESC));

        BoolQueryBuilder booleanQueryBuilder = QueryBuilders.boolQuery();

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            booleanQueryBuilder.must(QueryBuilders.termQuery(entry.getKey(), entry.getValue()));
        }

        SearchResponse response = searchRequestBuilder.setQuery(booleanQueryBuilder).get();

        long hits = response.getHits().getTotalHits();
        if (hits != 1) {
            return null;
        }

        List<T> outputList = new ArrayList<>();
        int idx = 0;
        for (SearchHit searchHit : response.getHits()) {
            if (++idx <= skip) {
                continue;
            }
            T dto = JSONUtils.getObjectByJson(searchHit.getSourceAsString(), getDTOClass());
            outputList.add(dto);
        }

        return outputList;
    }
}
