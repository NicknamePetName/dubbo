package com.youkeda.dewu.util;

import com.youkeda.dewu.dataobject.ProductDO;
import com.youkeda.dewu.model.Product;
import org.springframework.beans.BeanUtils;

public class ProductDOUtil {

    /**
     *
     * 模型 转换为 DO
     *
     * @param product
     * @return
     */
    public static ProductDO toDO(Product product) {
        ProductDO productDO = new ProductDO();
        BeanUtils.copyProperties(product,productDO);
        return productDO;
    }

    /**
     *
     * DO 转换为 模型
     *
     * @param productDO
     * @return
     */

    public static Product toModel(ProductDO productDO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDO,product);
        return product;
    }
}
