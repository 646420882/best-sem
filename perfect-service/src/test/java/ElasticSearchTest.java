import com.perfect.elasticsearch.service.impl.ElasticSearchServiceImpl;
import com.perfect.entity.CreativeSourceEntity;
import com.perfect.utils.JSONUtils;
import org.elasticsearch.action.admin.cluster.repositories.put.PutRepositoryRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.template.put.PutIndexTemplateRequestBuilder;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.support.AbstractClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;
import org.unitils.spring.annotation.SpringBeanByType;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;

/**
 * Created by baizz on 2014-9-30.
 */
@SpringApplicationContext({"spring-es.xml"})
public class ElasticSearchTest extends UnitilsJUnit4 {

//    @SpringBeanByType
//    private ElasticsearchRepositoryFactory elasticsearchRepositoryFactory;

//    @SpringBeanByType
//    private ElasticsearchTemplate elasticsearchTemplate;

    @SpringBean("esClient")
    private AbstractClient client;

    @SpringBeanByType
    private ElasticSearchServiceImpl elasticSearchService;

    private static IndexRequestBuilder indexRequestBuilder;

    private static PutIndexTemplateRequestBuilder putIndexTemplateRequestBuilder;

    private static DeleteIndexRequestBuilder deleteIndexRequestBuilder;

    private static GetRequestBuilder getRequestBuilder;

    private static PutRepositoryRequestBuilder putRepositoryRequestBuilder;

    private static UpdateRequestBuilder updateRequestBuilder;

    private static DeleteRequestBuilder deleteRequestBuilder;

    private static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void putDocumentIntoIndex() {
        CreativeSourceEntity creativeSourceEntity = new CreativeSourceEntity();
        creativeSourceEntity.setBody("婚博会测试2151");
        creativeSourceEntity.setHost("http://182.92.188.177");
        creativeSourceEntity.setRegion(1000);
        creativeSourceEntity.setTitle("婚博会title");

        CreativeSourceEntity creativeSourceEntity1 = new CreativeSourceEntity();
        creativeSourceEntity1.setBody("婚博会测试3151");
        creativeSourceEntity1.setHost("http://182.92.188.177");
        creativeSourceEntity1.setRegion(2000);
        creativeSourceEntity1.setTitle("婚博会title");

        List<CreativeSourceEntity> list = Arrays.asList(creativeSourceEntity, creativeSourceEntity1);

        //Spring Data API
//        ElasticsearchEntityInformation<CreativeSourceEntity, String> entityElasticsearchEntityInformation = elasticsearchRepositoryFactory.getEntityInformation(CreativeSourceEntity.class);
//        SimpleElasticsearchRepository<CreativeSourceEntity> elasticSearchRepository = new SimpleElasticsearchRepository<>(entityElasticsearchEntityInformation, elasticsearchTemplate);

//        elasticSearchRepository.delete("");

//        creativeSourceEntity = elasticSearchRepository.save(creativeSourceEntity);


        //ElasticSearch API
        //PUT document
        IndexRequestBuilder indexRequestBuilder = client.prepareIndex();
        indexRequestBuilder.setIndex("test").setType("creative");
        String jsonString = JSONUtils.getJsonString(creativeSourceEntity);
//        IndexResponse indexResponse = indexRequestBuilder.setSource(jsonString).execute().actionGet();

        //DELETE document
//        DeleteRequestBuilder deleteRequeZF8LkhjITlSQbtiXEjVtbQstBuilder = client.prepareDelete();
//        deleteRequestBuilder.setIndex("test").setType("creative");
//        DeleteResponse



        elasticSearchService.setIndex("data");
        elasticSearchService.setType("creative");
        System.out.println(elasticSearchService.toString());

//        elasticSearchService.save(list);
//        elasticSearchService.delete("");

        if (logger.isInfoEnabled()) {
            logger.info("test finished ... ");
        }
    }
}
