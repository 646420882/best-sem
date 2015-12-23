package com.perfect.db.elasticsearch.core;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;

/**
 * Created on 2014-12-01.
 *
 * @author dolphineor
 */
public interface EsRequest {

    String IDS = "ids";

    String SALT = "ElasticSalt";

    TransportClient getEsClient();

    String index();

    String type();


    default IndexRequestBuilder getIndexRequestBuilder() {
        IndexRequestBuilder indexRequestBuilder = getEsClient().prepareIndex();
        indexRequestBuilder.setIndex(index()).setType(type());
        return indexRequestBuilder;
    }

    default SearchRequestBuilder getCountRequestBuilder() {
        return getEsClient().prepareSearch(index()).setSize(0);
    }

    default DeleteRequestBuilder getDeleteRequestBuilder() {
        DeleteRequestBuilder deleteRequestBuilder = getEsClient().prepareDelete();
        deleteRequestBuilder.setIndex(index()).setType(type());
        return deleteRequestBuilder;
    }

    default BulkRequestBuilder getBulkRequestBuilder() {
        return getEsClient().prepareBulk();
    }
}
