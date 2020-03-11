package com.cf.mall.search;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cf.mall.bean.PmsSearchSkuInfo;
import com.cf.mall.bean.PmsSkuInfo;
import com.cf.mall.service.SkuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MallSearchServiceApplicationTests {

    @Reference
    private SkuService skuService;

    @Autowired
    private JestClient jestClient;

    @Test
    public void contextLoads() throws IOException  {
        put();
    }

    public void put() {
        // 查询mysql数据
        List<PmsSkuInfo> skuInfos = skuService.listSku();

        // 转化为 es 数据
        List<PmsSearchSkuInfo> searchSkuInfos = skuInfos.stream()
                .map(PmsSearchSkuInfo::new).collect(Collectors.toList());

        // 存入 es
        searchSkuInfos.forEach(x -> {
            Index index = new Index.Builder(x).index("mall").type("SkuInfo").id(x.getId()).build();
            try {
                jestClient.execute(index);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void search() throws IOException {
        // jest 的 dsl 工具
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // query
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();


        // filter
        boolQueryBuilder.filter(new TermQueryBuilder("skuAttrValueList.valueId","41"));

        // must
        boolQueryBuilder.must(new MatchQueryBuilder("skuName","小米6"));


        sourceBuilder.query(boolQueryBuilder).from(0).size(20);


        // 复杂查询
        Search search = new Search.Builder(sourceBuilder.toString()).addIndex("mall").addType("SkuInfo").build();

        SearchResult execute = jestClient.execute(search);

        List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = execute.getHits(PmsSearchSkuInfo.class);

        hits.forEach(x -> System.err.println(x.source));
    }
}
