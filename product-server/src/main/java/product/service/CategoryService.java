package product.service;

import product.dataobject.ProductCategory;

import java.util.List;

/**
 * @author niexiaoshuang
 */
public interface CategoryService {

    /**
     * 根据分类编号列表查询分类
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
