package com.youkeda.dewu.service.impl;

import com.youkeda.dewu.dao.ProductDetailDAO;
import com.youkeda.dewu.dataobject.ProductDetailDO;
import com.youkeda.dewu.model.ProductDetail;
import com.youkeda.dewu.service.ProductDetailService;
import com.youkeda.dewu.util.ProductDetailDOUtil;
import com.youkeda.dewu.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    private ProductDetailDAO productDetailDAO;

    @Override
    public List<ProductDetail> queryProductDetail(List<String> productDetailIds) {
        if (CollectionUtils.isEmpty(productDetailIds)) {
            return null;
        }

        List<ProductDetailDO> productDetailDOS = productDetailDAO.selectByIds(productDetailIds);

        return CollectionUtils.isEmpty(productDetailDOS) ? new ArrayList<>() : productDetailDOS.stream()
                .map(ProductDetailDOUtil::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public int save(ProductDetail productDetail) {

        if (StringUtils.isBlank(productDetail.getId())) {
            productDetail.setId(UUIDUtils.getUUID());
            return productDetailDAO.insert(ProductDetailDOUtil.toDO(productDetail));
        }

        return productDetailDAO.updateByPrimaryKey(ProductDetailDOUtil.toDO(productDetail));

    }

    @Override
    public List<ProductDetail> getByProductId(String productId) {
        if (StringUtils.isBlank(productId)) {
            return new ArrayList<>();
        }

        List<ProductDetailDO> productDetailDOS = productDetailDAO.selectByProductId(productId);

        return CollectionUtils.isEmpty(productDetailDOS) ? new ArrayList<>() : productDetailDOS.stream()
                .map(ProductDetailDOUtil::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDetail get(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }

        return ProductDetailDOUtil.toModel(productDetailDAO.selectByPrimaryKey(id));
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return productDetailDAO.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ProductDetail record) {
        return productDetailDAO.insert(ProductDetailDOUtil.toDO(record));
    }

    @Override
    public ProductDetail selectByPrimaryKey(String id) {
        return ProductDetailDOUtil.toModel(productDetailDAO.selectByPrimaryKey(id));
    }

    @Override
    public List<ProductDetail> selectAll() {
        return productDetailDAO.selectAll().stream().map(ProductDetailDOUtil::toModel).collect(Collectors.toList());
    }

    @Override
    public int updateByPrimaryKey(ProductDetail record) {
        return productDetailDAO.updateByPrimaryKey(ProductDetailDOUtil.toDO(record));
    }

    @Override
    public List<ProductDetail> selectByProductId(String productId) {
        return productDetailDAO.selectByProductId(productId).stream().map(ProductDetailDOUtil::toModel).collect(Collectors.toList());
    }

    @Override
    public List<ProductDetail> selectByIds(List<String> ids) {
        return productDetailDAO.selectByIds(ids).stream().map(ProductDetailDOUtil::toModel).collect(Collectors.toList());
    }
}
