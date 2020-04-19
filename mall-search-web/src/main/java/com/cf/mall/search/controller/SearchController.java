package com.cf.mall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cf.mall.annotations.LoginRequired;
import com.cf.mall.bean.PmsBaseAttrInfo;
import com.cf.mall.bean.PmsSearchCrumb;
import com.cf.mall.bean.PmsSearchParam;
import com.cf.mall.bean.PmsSearchSkuInfo;
import com.cf.mall.service.AttrService;
import com.cf.mall.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author chen
 * @Date 2020/3/7
 */
@Controller
public class SearchController {


    @Reference
    SearchService searchService;

    @Reference
    AttrService attrService;

    @RequestMapping("list.html")
    public String list(PmsSearchParam param, ModelMap map) {
        List<PmsSearchSkuInfo> infoList = searchService.list(param);
        map.put("skuLsInfoList",infoList);

        // 查询商品属性列表   filter()
        List<Long> ids = infoList.stream()
                .flatMap(x -> x.getSkuAttrValueList()
                        .stream()).map(y -> y.getValueId())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        List<PmsBaseAttrInfo> attrList = attrService.listAttr(ids);

        // 过滤已选属性组 & 面包屑
        List<PmsSearchCrumb> crumbs = new LinkedList();
        if (null != param.getValueId()) {
            attrList = attrList.stream()
                    .filter(x -> !(x.getAttrValueList().stream().anyMatch(y -> {
                        boolean flag = param.getValueId().contains(y.getId().toString());
                        if (flag) {
                            PmsSearchCrumb crumb = new PmsSearchCrumb(y.getId().toString()
                                    , x.getAttrName() + ":" + y.getValueName()
                                    , getUrlParam(param, String.valueOf(y.getId())));
                            crumbs.add(crumb);
                        }
                        return flag;
                    })))
                    .collect(Collectors.toList());
            // 添加面包屑
            map.put("attrValueSelectedList",crumbs);
        }
        map.put("attrList",attrList);

        // 拼接参数url
        String urlParam = getUrlParam(param);
        map.put("urlParam",urlParam);
//        map.put("keyword",param.getKeyword());
        return "list";
    }


    private String getUrlParam(PmsSearchParam param,String ...valueId) {
        List<String> p = new ArrayList<>();

        if(StringUtils.isNotBlank(param.getCatalog3Id())) {
            p.add("catalog3Id="+param.getCatalog3Id());
        }

        if(StringUtils.isNotBlank(param.getKeyword())) {
            p.add("keyword="+param.getKeyword());
        }

        if (null != param.getValueId()) {
            param.getValueId().stream().forEach(x -> {
                if (0 == valueId.length || !x.equals(valueId[0])) {
                    p.add("valueId="+x);
                }
            });
        }
        return StringUtils.join(p,"&");
    }

    @RequestMapping("index")
    @LoginRequired(loginSuccess = false)
    public String index() {
        return "index";
    }

    @RequestMapping("")
    @LoginRequired(loginSuccess = false)
    public String home() {
        return "index";
    }
}
