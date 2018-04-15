package com.nxs.product.service;

import com.nxs.product.dataobject.ProductInfo;

import java.util.List;

public interface ProductService {

    /**
     * 查询所有在架商品
     * @return 在架商品列表
     */
    List<ProductInfo> findAll();
}
