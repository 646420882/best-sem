import com.perfect.db.elasticsearch.service.impl.ElasticSearchServiceImpl;
import com.perfect.entity.CreativeSourceEntity;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
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
//
//    @SpringBeanByType
//    private ElasticsearchTemplate elasticsearchTemplate;
//
//    @SpringBean("esClient")
//    private AbstractClient esClient;

    @SpringBeanByType
    private ElasticSearchServiceImpl elasticSearchService;

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

        //ElasticSearch API
        elasticSearchService.setIndex("data");
        elasticSearchService.setType("creative");
        CreativeSourceEntity creativeSourceEntity2 = elasticSearchService.findOne("CFuFLKCYS6mpeMrEYD62gg");

//        elasticSearchService.save(list);
//        elasticSearchService.delete("");

        if (logger.isInfoEnabled()) {
            logger.info("test finished ... ");
        }
    }
}
