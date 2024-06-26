package com.youkeda.dewu.service.impl;

import com.youkeda.dewu.dao.ProductDAO;
import com.youkeda.dewu.dataobject.ProductDO;
import com.youkeda.comment.model.Paging;
import com.youkeda.dewu.model.Product;
import com.youkeda.dewu.param.BasePageParam;
import com.youkeda.dewu.service.ProductService;
import com.youkeda.dewu.util.ProductDOUtil;
import com.youkeda.dewu.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Override
    public int save(Product product) {

        if (StringUtils.isBlank(product.getId())) {
            product.setId(UUIDUtils.getUUID());
            return productDAO.insert(ProductDOUtil.toDO(product));
        }

        return productDAO.updateByPrimaryKey(ProductDOUtil.toDO(product));
    }

    @Override
    public Paging<Product> pageQueryProduct(BasePageParam param) {

        Paging<Product> result = new Paging<>();
        if (param == null) {
            return result;
        }

        if (param.getPagination() < 0) {
            param.setPagination(0);
        }

        if (param.getPageSize() < 0) {
            param.setPageSize(10);
        }

        //查询数据总数
        int count = productDAO.selectAllCounts();
        if (count < 0) {
            return result;
        }
        //组装返回数据
        result.setTotalCount(count);
        result.setPageSize(param.getPageSize());
        result.setPageNum(param.getPagination());

        int totalPage = (int)Math.ceil(count / (param.getPageSize() * 1.0));
        result.setTotalPage(totalPage);
        //实际返回的数据
        List<ProductDO> productDOS = productDAO.pageQuery(param);
        List<Product> products = CollectionUtils.isEmpty(productDOS) ? new ArrayList<>() : productDOS.stream()
                        .map(ProductDOUtil::toModel).collect(Collectors.toList());
        result.setData(products);

        return result;
    }

    @Override
    public Product get(String productId) {
        return ProductDOUtil.toModel(productDAO.selectByPrimaryKey(productId));
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return productDAO.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Product record) {
        return productDAO.insert(ProductDOUtil.toDO(record));
    }

    @Override
    public Product selectByPrimaryKey(String id) {
        return ProductDOUtil.toModel(productDAO.selectByPrimaryKey(id));
    }

    @Override
    public List<Product> selectAll() {
        return productDAO.selectAll().stream().map(ProductDOUtil::toModel).collect(Collectors.toList());
    }

    @Override
    public int updateByPrimaryKey(Product record) {
        return productDAO.updateByPrimaryKey(ProductDOUtil.toDO(record));
    }

    @Override
    public int selectAllCounts() {
        return productDAO.selectAllCounts();
    }

    @Override
    public List<Product> pageQuery(BasePageParam param) {
        if (param == null) {
            return new ArrayList<>();
        }
        return productDAO.pageQuery(param).stream().map(ProductDOUtil::toModel).collect(Collectors.toList());
    }

}
