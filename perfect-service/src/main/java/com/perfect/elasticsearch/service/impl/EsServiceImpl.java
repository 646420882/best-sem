package com.perfect.elasticsearch.service.impl;

import com.perfect.dto.EsSearchResultDTO;
import com.perfect.elasticsearch.service.EsService;
import com.perfect.entity.CreativeSourceEntity;
import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.facet.Facet;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.terms.TermsFacet;
import org.elasticsearch.search.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by vbzer_000 on 2014/9/19.
 */
@Component
public class EsServiceImpl implements EsService {

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Resource
    private Client esClient;


    @Override
    public EsSearchResultDTO search(String query, int page, int size) {

        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(query);
        builder.analyzer("ik").field("title").field("body").defaultOperator(QueryStringQueryBuilder.Operator.OR);

        String request = builder.toString();

        SearchResponse sr = esClient.prepareSearch("data").setTypes("creative").setQuery(builder).setFrom((page - 1) *
                size)
                .setSize
                        (size)
                .addHighlightedField("title").addHighlightedField("body").setHighlighterPreTags("<font color='red'>")
                .setHighlighterPostTags("</font>")
                .addFacet(FacetBuilders
                                .termsFacet
                                        ("terms").field("title").field("body").size(300)
                                .order(TermsFacet.ComparatorType.COUNT)
                ).get();

        if (sr == null) {
            return null;
        }

        EsSearchResultDTO esSearchResultDTO = new EsSearchResultDTO();

        esSearchResultDTO.setTotal(sr.getHits().totalHits());


        List<CreativeSourceEntity> hitList = new ArrayList<>();
        for (SearchHit searchHit : sr.getHits()) {
            Map<String, Object> map = searchHit.getSource();
            CreativeSourceEntity creativeSourceEntity = new CreativeSourceEntity();
            for (Map.Entry<String, HighlightField> entry : searchHit.getHighlightFields().entrySet()) {
                HighlightField field = entry.getValue();
                map.put(field.name(), field.fragments()[0]);
            }

            try {
                BeanUtils.populate(creativeSourceEntity, map);
                hitList.add(creativeSourceEntity);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }

        esSearchResultDTO.setList(hitList);
        Map<String, Integer> termcount = new TreeMap<>();
        for (Facet facetResult : sr.getFacets()) {
            if (facetResult instanceof TermsFacet) {
                TermsFacet tr = (TermsFacet) facetResult;
                BigDecimal total = BigDecimal.valueOf(tr.getTotalCount());


                for (TermsFacet.Entry t : tr.getEntries()) {
                    esSearchResultDTO.add(t.getTerm().string(), new BigDecimal(t.getCount()).divide(total, 4,
                            BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.TEN.multiply(BigDecimal.TEN)));
                }
            }
        }


        return esSearchResultDTO;
    }
}
