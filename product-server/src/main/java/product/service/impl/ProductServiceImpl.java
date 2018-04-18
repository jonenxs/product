package product.service.impl;

import com.nxs.product.common.DecreaseStockInput;
import com.nxs.product.common.ProductInfoOutput;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import product.dataobject.ProductInfo;
import product.enums.ProductStatusEnum;
import product.enums.ResultEnum;
import product.exception.ProductException;
import product.repository.ProductInfoRepository;
import product.service.ProductService;
import product.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public List<ProductInfo> findAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfoOutput> findList(List<String> productIdList) {
        return productInfoRepository.findByProductIdIn(productIdList).stream()
                .map(e -> {
                    ProductInfoOutput output = new ProductInfoOutput();
                    BeanUtils.copyProperties(e, output);
                    return output;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void decreaseStock(List<DecreaseStockInput> cartDTOList) {
        List<ProductInfo> productInfoList = decreaseStockProcess(cartDTOList);
        //发送mq消息
        List<ProductInfoOutput> productInfoOutputs = productInfoList.stream().map(e -> {
            ProductInfoOutput output = new ProductInfoOutput();
            BeanUtils.copyProperties(e, output);
            return output;
        }).collect(Collectors.toList());
        amqpTemplate.convertAndSend("productInfo", JsonUtil.toJson(productInfoOutputs));
    }

    @Transactional
    public List<ProductInfo> decreaseStockProcess(List<DecreaseStockInput> cartDTOList) {
        List<ProductInfo> productInfoList = new ArrayList<>();
        for (DecreaseStockInput cartDTO : cartDTOList) {
            Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(cartDTO.getProductId());
            //商品不存在
            if (!productInfoOptional.isPresent()) {
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXISTS);
            }

            ProductInfo productInfo = productInfoOptional.get();
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            //库存不足
            if (result < 0) {
                throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);

            productInfoList.add(productInfo);
        }
        return productInfoList;
    }
}
