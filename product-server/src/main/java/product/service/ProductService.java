package product.service;



import com.nxs.product.common.DecreaseStockInput;
import com.nxs.product.common.ProductInfoOutput;
import product.dataobject.ProductInfo;

import java.util.List;

public interface ProductService {

    /**
     * 查询所有在架商品
     * @return 在架商品列表
     */
    List<ProductInfo> findAll();

    /**
     * 根据商品id list查询商品列表
     * @param productIdList
     * @return
     */
    List<ProductInfoOutput> findList(List<String> productIdList);

    /**
     * 扣库存
     * @param decreaseStockInputList
     */
    void decreaseStock(List<DecreaseStockInput> decreaseStockInputList);
}
