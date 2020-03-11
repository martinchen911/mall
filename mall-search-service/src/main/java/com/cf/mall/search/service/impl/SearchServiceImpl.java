package com.cf.mall.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.mall.bean.PmsSearchParam;
import com.cf.mall.bean.PmsSearchSkuInfo;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author chen
 * @Date 2020/3/8
 */
@Service
public class SearchServiceImpl implements com.cf.mall.service.SearchService {

    @Autowired
    private JestClient jestClient;


    @Override
    public List<PmsSearchSkuInfo> list(PmsSearchParam param) {

        // 构建查询语句
        String dsl = getSearchDsl(param);

        // 复杂查询
        Search search = new Search.Builder(dsl).addIndex("mall")
                .addType("SkuInfo").build();

        SearchResult execute = null;
        try {
            execute = jestClient.execute(search);
            List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = execute.getHits(PmsSearchSkuInfo.class);
            List<PmsSearchSkuInfo> collect = hits.stream().map(s -> {
                PmsSearchSkuInfo source = s.source;
                if (StringUtils.isNotBlank(param.getKeyword())) {
                    source.setSkuName(s.highlight.get("skuName").get(0));
                }
                return source;
            }).collect(Collectors.toList());
            return collect;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getSearchDsl(PmsSearchParam param) {
        // jest 的 dsl 工具
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // query
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();


        // filter
        if (StringUtils.isNotBlank(param.getCatalog3Id())) {
            boolQueryBuilder.filter(new TermQueryBuilder("catalog3Id",param.getCatalog3Id()));
        }
        if (param.getValueId() != null) {
            param.getValueId().forEach(p -> {
                boolQueryBuilder.filter(new TermQueryBuilder("skuAttrValueList.valueId",p));
            });
        }

        // must
        if (StringUtils.isNotBlank(param.getKeyword())) {
            boolQueryBuilder.must(new MatchQueryBuilder("skuName",param.getKeyword()));
            boolQueryBuilder.must(new MatchQueryBuilder("skuDesc",param.getKeyword()));
        }

        // highlight
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style='color:red'>")
                .field("skuName")
                .postTags("</span>");

        // aggs
        TermsBuilder aggs = AggregationBuilders.terms("groupby_attr").field("skuAttrValueList.valueId");

        sourceBuilder.query(boolQueryBuilder)
                .sort("price", SortOrder.DESC)
                .from(0)
                .size(20)
                .highlight(highlightBuilder)
                .aggregation(aggs);

        return sourceBuilder.toString();
    }
}
