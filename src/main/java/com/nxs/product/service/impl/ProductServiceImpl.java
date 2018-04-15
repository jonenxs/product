package com.nxs.product.service.impl;

import com.nxs.product.dataobject.ProductInfo;
import com.nxs.product.enums.ProductStatusEnum;
import com.nxs.product.repository.ProductInfoRepository;
import com.nxs.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public List<ProductInfo> findAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }
}
