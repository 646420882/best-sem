package com.perfect.elasticsearch.repo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.count.CountRequestBuilder;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.support.AbstractClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.FacetedPage;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;

/**
 * Created by baizz on 2014-9-30.
 */
public abstract class BaseElasticSearchRepository<T, ID extends Serializable> implements ElasticsearchRepository<T, ID> {

    @Resource
    private AbstractClient esClient;

    static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    protected String index;
    protected String type;

    protected BaseElasticSearchRepository() {
    }

    public BaseElasticSearchRepository(String index, String type) {
        this.index = index;
        this.type = type;
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

    public abstract Class<T> getEntityClass();

    @Override
    public <S extends T> S index(S entity) {
        return null;
    }

    @Override
    public Iterable<T> search(QueryBuilder query) {
        return null;
    }

    @Override
    public FacetedPage<T> search(QueryBuilder query, Pageable pageable) {
        return null;
    }

    @Override
    public FacetedPage<T> search(SearchQuery searchQuery) {
        return null;
    }

    @Override
    public Page<T> searchSimilar(T entity, String[] fields, Pageable pageable) {
        return null;
    }

    @Override
    public Iterable<T> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends T> S save(S entity) {
        IndexRequestBuilder indexRequestBuilder = getIndexRequestBuilder();
        IndexResponse indexResponse = indexRequestBuilder.setSource(toJsonString(entity)).get();
        if (indexResponse.isCreated()) {
            return entity;
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends T> Iterable<S> save(Iterable<S> entities) {
        BulkRequestBuilder bulkRequestBuilder = getBulkRequestBuilder();
        for (S s : entities) {
            bulkRequestBuilder.add(getIndexRequestBuilder().setSource(toJsonString(s)));
        }

        BulkResponse bulkResponse = bulkRequestBuilder.get();
        if (!bulkResponse.hasFailures()) {
            return entities;
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public T findOne(ID id) {
        return null;
    }

    @Override
    public boolean exists(ID id) {
        return false;
    }

    @Override
    public Iterable<T> findAll() {
        return null;
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {
        return null;
    }

    @Override
    public long count() {
        CountRequestBuilder countRequestBuilder = getCountRequestBuilder();
        return countRequestBuilder.get().getCount();
    }

    @Override
    public void delete(ID id) {
        DeleteRequestBuilder deleteRequestBuilder = getDeleteRequestBuilder();
        deleteRequestBuilder.setId(id.toString());
        deleteRequestBuilder.get();
    }

    @Override
    public void delete(T entity) {
        try {
            Method method = getEntityClass().getDeclaredMethod("getId");
            String id = method.invoke(entity).toString();
            DeleteRequestBuilder deleteRequestBuilder = getDeleteRequestBuilder();
            deleteRequestBuilder.setId(id);
            deleteRequestBuilder.get();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            LOGGER.error("A error occured when delete a entity: " + e.getMessage());
        }
    }

    @Override
    public void delete(Iterable<? extends T> entities) {

    }

    @Override
    public void deleteAll() {

    }

    private IndexRequestBuilder getIndexRequestBuilder() {
        IndexRequestBuilder indexRequestBuilder = esClient
                .prepareIndex();
        indexRequestBuilder.setIndex(index).setType(type);
        return indexRequestBuilder;
    }

    private CountRequestBuilder getCountRequestBuilder() {
        return esClient.prepareCount(index);
    }

    public UpdateRequestBuilder getUpdateRequestBuilder() {
        UpdateRequestBuilder updateRequestBuilder = esClient.prepareUpdate();
        updateRequestBuilder.setIndex(index).setType(type);
        return updateRequestBuilder;
    }

    private DeleteRequestBuilder getDeleteRequestBuilder() {
        DeleteRequestBuilder deleteRequestBuilder = esClient.prepareDelete();
        deleteRequestBuilder.setIndex(index).setType(type);
        return deleteRequestBuilder;
    }

    private BulkRequestBuilder getBulkRequestBuilder() {
        return esClient.prepareBulk();
    }

    private String toJsonString(Object o) {
        if (o == null) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

}
