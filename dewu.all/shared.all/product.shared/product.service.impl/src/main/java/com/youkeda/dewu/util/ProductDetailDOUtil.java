package com.youkeda.dewu.util;

import com.youkeda.dewu.dataobject.ProductDetailDO;
import com.youkeda.dewu.model.ProductDetail;
import org.springframework.beans.BeanUtils;

public class ProductDetailDOUtil {

    /**
     *
     * 模型 转换为 DO
     *
     * @param productDetail
     * @return
     */
    public static ProductDetailDO toDO(ProductDetail productDetail) {
        ProductDetailDO productDetailDO = new ProductDetailDO();
        BeanUtils.copyProperties(productDetail,productDetailDO);
        return productDetailDO;
    }
    /**
     *
     * DO 转换为 模型
     *
     * @param productDetailDO
     * @return
     */
    public static ProductDetail toModel(ProductDetailDO productDetailDO) {
        ProductDetail productDetail = new ProductDetail();
        BeanUtils.copyProperties(productDetailDO,productDetail);
        return productDetail;
    }
}
