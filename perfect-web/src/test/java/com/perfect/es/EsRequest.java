package com.perfect.es;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.count.CountRequestBuilder;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.deletebyquery.DeleteByQueryRequestBuilder;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;

/**
 * Created by baizz on 2014-12-1.
 */
public interface EsRequest {

    public static final String IDS = "ids";

    public static final String SALT = "hello,salt";

    TransportClient getEsClient();

    String index();

    String type();

    default GetRequestBuilder getGetRequestBuilder() {
        GetRequestBuilder getRequestBuilder = getEsClient().prepareGet();
        getRequestBuilder.setIndex(index()).setType(type());
        return getRequestBuilder;
    }

    default IndexRequestBuilder getIndexRequestBuilder() {
        IndexRequestBuilder indexRequestBuilder = getEsClient().prepareIndex();
        indexRequestBuilder.setIndex(index()).setType(type());
        return indexRequestBuilder;
    }

    default CountRequestBuilder getCountRequestBuilder() {
        return getEsClient().prepareCount(index());
    }

    default DeleteRequestBuilder getDeleteRequestBuilder() {
        DeleteRequestBuilder deleteRequestBuilder = getEsClient().prepareDelete();
        deleteRequestBuilder.setIndex(index()).setType(type());
        return deleteRequestBuilder;
    }

    default DeleteByQueryRequestBuilder getDeleteByQueryRequestBuilder() {
        return getEsClient().prepareDeleteByQuery();
    }

    default BulkRequestBuilder getBulkRequestBuilder() {
        return getEsClient().prepareBulk();
    }
}
