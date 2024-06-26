package com.youkeda.dewu.api;

import com.youkeda.comment.model.Paging;
import com.youkeda.dewu.model.Product;
import com.youkeda.comment.model.Result;
import com.youkeda.dewu.param.BasePageParam;
import com.youkeda.dewu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/product")
public class ProductAPI {

    @Autowired
    private ProductService productService;

    @ResponseBody
    @GetMapping("/page")
    public Result<Paging<Product>> queryPage(@RequestBody(required = false) BasePageParam param) {
        Result<Paging<Product>> result = new Result<>();
        if (param == null) {
            param = new BasePageParam();
        }
        result.setData(productService.pageQueryProduct(param));
        result.setSuccess(true);
        return result;

    }

    @ResponseBody
    @GetMapping("/get")
    public Result<Product> get(@RequestParam String productId) {
        Result<Product> result = new Result<>();
        result.setData(productService.get(productId));
        result.setSuccess(true);
        return result;
    }
}
