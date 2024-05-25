package com.youkeda.dewu.service.impl;

import com.youkeda.comment.model.Paging;
import com.youkeda.dewu.model.Product;
import com.youkeda.dewu.param.BasePageParam;
import com.youkeda.dewu.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ProductServiceImplTest {

    @Resource
    private ProductService productService;
    @Test
    void pageQueryProduct() {
        BasePageParam basePageParam = new BasePageParam();
        basePageParam.setPagination(0);
        Paging<Product> pageResult = productService.pageQueryProduct(basePageParam);

        System.out.println(pageResult.getPageSize());
    }
}