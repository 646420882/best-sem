package com.perfect.elasticsearch.repo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.count.CountRequestBuilder;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.deletebyquery.DeleteByQueryRequestBuilder;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.support.AbstractClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-9-30.
 */
public abstract class BaseElasticSearchRepository<T, ID extends Serializable> implements CrudRepository<T, ID> {

    @Resource
    private AbstractClient esClient;

    static final String ID = "id";
    static final String _ID = "_id";
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

    public Iterable<T> search(QueryBuilder query, int page, int limit) {
        SearchRequestBuilder searchRequestBuilder = getSearchRequestBuilder();
        searchRequestBuilder.setQuery(query);
        SearchHit[] searchHits = searchRequestBuilder.setFrom(page).setSize(limit).get().getHits().getHits();

        List<T> result = new ArrayList<>();
        for (SearchHit searchHit : searchHits) {
            Map<String, Object> map = searchHit.getSource();
            map.put(ID, searchHit.getId());
            try {
                T entity = getEntityClass().newInstance();
                for (Map.Entry<String, HighlightField> entry : searchHit.getHighlightFields().entrySet()) {
                    HighlightField field = entry.getValue();
                    map.put(field.name(), field.fragments()[0]);
                }
                BeanUtils.populate(entity, map);
                result.add(entity);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }

        }

        return result;
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
        GetRequestBuilder getRequestBuilder = getGetRequestBuilder();
        getRequestBuilder.setId(id.toString()).setOperationThreaded(false);
        GetResponse getResponse = getRequestBuilder.get();
        Map<String, Object> map = getResponse.getSource();
        map.put(ID, getResponse.getId());
        try {
            T entity = getEntityClass().newInstance();
            BeanUtils.populate(entity, map);
            return entity;
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean exists(ID id) {
        return findOne(id) != null;
    }

    @Override
    public Iterable<T> findAll() {
        SearchRequestBuilder searchRequestBuilder = getSearchRequestBuilder();
        searchRequestBuilder.setQuery(QueryBuilders.matchAllQuery());
        SearchHit[] searchHits = searchRequestBuilder.get().getHits().getHits();

        List<T> result = new ArrayList<>();
        for (SearchHit searchHit : searchHits) {
            Map<String, Object> map = searchHit.getSource();
            map.put(ID, searchHit.getId());
            try {
                T entity = getEntityClass().newInstance();
                for (Map.Entry<String, HighlightField> entry : searchHit.getHighlightFields().entrySet()) {
                    HighlightField field = entry.getValue();
                    map.put(field.name(), field.fragments()[0]);
                }
                BeanUtils.populate(entity, map);
                result.add(entity);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }

        }

        return result;
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {
        List<String> _ids = new ArrayList<>();
        for (ID id : ids) {
            _ids.add(id.toString());
        }
        QueryBuilder queryBuilder = QueryBuilders.inQuery(_ID, _ids);
        SearchRequestBuilder searchRequestBuilder = getSearchRequestBuilder();
        searchRequestBuilder.setQuery(queryBuilder);
        SearchHit[] searchHits = searchRequestBuilder.get().getHits().getHits();

        List<T> result = new ArrayList<>();
        for (SearchHit searchHit : searchHits) {
            Map<String, Object> map = searchHit.getSource();
            map.put(ID, searchHit.getId());
            try {
                T entity = getEntityClass().newInstance();
                for (Map.Entry<String, HighlightField> entry : searchHit.getHighlightFields().entrySet()) {
                    HighlightField field = entry.getValue();
                    map.put(field.name(), field.fragments()[0]);
                }
                BeanUtils.populate(entity, map);
                result.add(entity);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }

        }

        return result;
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
        List<String> ids = new ArrayList<>();
        for (T t : entities) {
            try {
                Method method = getEntityClass().getDeclaredMethod("getId");
                String id = method.invoke(t).toString();
                ids.add(id);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                LOGGER.error("A error occured when delete entities: " + e.getMessage());
            }
        }

        DeleteByQueryRequestBuilder deleteByQueryRequestBuilder = getDeleteByQueryRequestBuilder();
        deleteByQueryRequestBuilder.setQuery(QueryBuilders.inQuery(_ID, ids)).get();
    }

    @Override
    public void deleteAll() {
        DeleteByQueryRequestBuilder deleteByQueryRequestBuilder = getDeleteByQueryRequestBuilder();
        deleteByQueryRequestBuilder.setQuery(QueryBuilders.matchAllQuery());
        deleteByQueryRequestBuilder.get();
    }

    private GetRequestBuilder getGetRequestBuilder() {
        GetRequestBuilder getRequestBuilder = esClient.prepareGet();
        getRequestBuilder.setIndex(index).setType(type);
        return getRequestBuilder;
    }

    private IndexRequestBuilder getIndexRequestBuilder() {
        IndexRequestBuilder indexRequestBuilder = esClient.prepareIndex();
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

    private DeleteByQueryRequestBuilder getDeleteByQueryRequestBuilder() {
        DeleteByQueryRequestBuilder deleteByQueryRequestBuilder = esClient.prepareDeleteByQuery();
        deleteByQueryRequestBuilder.setIndices(index).setTypes(type);
        return deleteByQueryRequestBuilder;
    }

    private SearchRequestBuilder getSearchRequestBuilder() {
        SearchRequestBuilder searchRequestBuilder = esClient.prepareSearch();
        searchRequestBuilder.setIndices(index).setTypes(type);
        return searchRequestBuilder;
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
