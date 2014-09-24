package com.perfect.elasticsearch.service.impl;

import com.perfect.dto.CreativeSourceDTO;
import com.perfect.dto.EsSearchResultDTO;
import com.perfect.elasticsearch.service.EsService;
import com.perfect.utils.RegionalCodeUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by vbzer_000 on 2014/9/19.
 */
@Component
public class EsServiceImpl implements EsService {

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Resource
    private Client esClient;

    private final String AGG_KEYWORDS = "keywords";
    private final String AGG_HOSTS = "hosts";
    private final String AGG_REGIONS = "regions";

    @Override
    public EsSearchResultDTO search(String query, int page, int size) {

        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(query);
        builder.analyzer("ik").field("title").field("body").defaultOperator(QueryStringQueryBuilder.Operator.OR);

        String request = builder.toString();

        SearchResponse sr = esClient.prepareSearch("datacreative").setTypes("creative").setQuery(builder).setFrom(
                (page -
                        1) *
                        size)
                .setSize
                        (size)
                .addHighlightedField("title").addHighlightedField("body").setHighlighterPreTags("<font color='red'>")
                .setHighlighterPostTags("</font>")
                .addAggregation(AggregationBuilders.terms(AGG_KEYWORDS).field("keyword").size(10))
                .addAggregation(AggregationBuilders.terms(AGG_REGIONS).field("region").size(10))
                .addAggregation(AggregationBuilders.terms(AGG_HOSTS).field("host").size(10))
                .get();

        if (sr == null) {
            return null;
        }

        EsSearchResultDTO esSearchResultDTO = new EsSearchResultDTO();

        esSearchResultDTO.setTotal(sr.getHits().totalHits());
        Map<Integer, String> regionMap = new HashMap<>();

        List<CreativeSourceDTO> hitList = new ArrayList<>();
        for (SearchHit searchHit : sr.getHits()) {
            Map<String, Object> map = searchHit.getSource();
            CreativeSourceDTO creativeSourceEntity = new CreativeSourceDTO();
            for (Map.Entry<String, HighlightField> entry : searchHit.getHighlightFields().entrySet()) {
                HighlightField field = entry.getValue();
                map.put(field.name(), field.fragments()[0]);
            }


            try {
                BeanUtils.populate(creativeSourceEntity, map);
                Object regionObj = map.get("region");
                if (regionObj == null) {
                    creativeSourceEntity.setRegion("无");
                } else {
                    int region = Integer.parseInt(regionObj.toString());
                    regionMap.putAll(RegionalCodeUtils.regionalCode(Arrays.asList(region)));

                    creativeSourceEntity.setRegion(regionMap.get(region));
                }
                hitList.add(creativeSourceEntity);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }

        esSearchResultDTO.setList(hitList);

        BigDecimal total = BigDecimal.valueOf(sr.getHits().totalHits());
        for (Aggregation aggregation : sr.getAggregations()) {
            if (aggregation.getName().equals(AGG_KEYWORDS)) {
                Terms tr = (Terms) aggregation;
                for (Terms.Bucket bucket : tr.getBuckets()) {
                    esSearchResultDTO.addKeyword(bucket.getKey(), new BigDecimal(bucket.getDocCount()).divide(total, 4,
                            BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.TEN.multiply(BigDecimal.TEN)));
                }
            } else if (aggregation.getName().equals(AGG_HOSTS)) {
                Terms tr = (Terms) aggregation;
                for (Terms.Bucket bucket : tr.getBuckets()) {
                    esSearchResultDTO.addHost(bucket.getKey(), new BigDecimal(bucket.getDocCount()).divide(total, 4,
                            BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.TEN.multiply(BigDecimal.TEN)));
                }

            } else if (aggregation.getName().equals(AGG_REGIONS)) {
                Terms tr = (Terms) aggregation;
                for (Terms.Bucket bucket : tr.getBuckets()) {
                    int region = bucket.getKeyAsNumber().intValue();
                    String name = regionMap.get(region);
                    if (name == null) {
                        regionMap.putAll(RegionalCodeUtils.regionalCode(Arrays.asList(region)));
                        name = regionMap.get(region);
                    }
                    esSearchResultDTO.addRegions(name, new BigDecimal(bucket.getDocCount()).divide(total, 4,
                            BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.TEN.multiply(BigDecimal.TEN)));
                }
            }
        }
        return esSearchResultDTO;
    }
}
