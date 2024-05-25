package com.youkeda.dewu.service;

import com.youkeda.comment.model.Paging;
import com.youkeda.dewu.model.Product;
import com.youkeda.dewu.param.BasePageParam;

import java.util.List;

public interface ProductService {
    /**
     * 增加或修改商品
     * @param product 商品
     * @return Product
     */
    int save(Product product);

    /**
     * 分页查询商品
     * @param param 商品分页参数
     * @return PagingData<Product>
     */
    Paging<Product> pageQueryProduct(BasePageParam param);

    /**
     * 根据主键获取商品
     * @param productId 商品主键
     * @return Product
     */
    Product get(String productId);

    /** 根据主键删除数据 */
    int deleteByPrimaryKey(String id);

    /** 新增一条数据 */
    int insert(Product record);

    /** 根据主键查询一条数据 */
    Product selectByPrimaryKey(String id);

    /** 查询所有条数据 */
    List<Product> selectAll();

    /** 根据参数对象中的主键值更新一条数据 */
    int updateByPrimaryKey(Product record);

    /** 查询一共有多少条数据 */
    int selectAllCounts();

    /** 分页查询 */
    List<Product> pageQuery(BasePageParam param);
}