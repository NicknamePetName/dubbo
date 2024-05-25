package com.youkeda.dewu.control;

import com.youkeda.dewu.model.Product;
import com.youkeda.dewu.service.ProductService;
import com.youkeda.dewu.param.BasePageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product/page")
    public List<Product> pageQuery(@RequestBody(required = false) BasePageParam param) {
        if (param == null) {
            param = new BasePageParam();
        }
        return productService.pageQuery(param);
    }
}
