package com.youkeda.dewu.service;

import com.youkeda.dewu.model.ProductDetail;

import java.util.List;

public interface ProductDetailService {
    /**
     * 获取多个商品详情
     *
     * @param productDetailIds  查询参数
     * @return
     */
    public List<ProductDetail> queryProductDetail(List<String> productDetailIds);

    /**
     * 添加或者删除商品详情
     *
     * @param productDetail 商品详情
     * @return int
     */
    int save(ProductDetail productDetail);

    /**
     * 获取商品详情
     *
     * @param productId 商品主键
     * @return ProductDetail
     */
    List<ProductDetail> getByProductId(String productId);

    /**
     * 获取商品详情
     *
     * @param id 主键
     * @return ProductDetail
     */
    ProductDetail get(String id);

    /** 根据主键删除数据 */
    int deleteByPrimaryKey(String id);

    /** 新增一条数据 */
    int insert(ProductDetail record);

    /** 根据主键查询一条数据 */
    ProductDetail selectByPrimaryKey(String id);

    /** 查询所有条数据 */
    List<ProductDetail> selectAll();

    /** 根据参数对象中的主键值更新一条数据 */
    int updateByPrimaryKey(ProductDetail record);



    List<ProductDetail> selectByProductId(String productId);

    List<ProductDetail> selectByIds(List<String> ids);
}
