package com.nxs.product.dataobject;


import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品
 * @author niexiaoshuang
 */
@Data
@Entity
@Table(name = "product_info")
public class ProductInfo {

    @Id
    private String productId;

    /**
     * 名称
     */
    private String productName;

    /**
     * 价格
     */
    private BigDecimal productPrice;

    /**
     * 描述
     */
    private String productDescription;

    /**
     * 小图
     */
    private String productIcon;

    /**
     * 状态 0 正常 1 下架
     */
    private Integer productStatus;


    /**
     * 库存
     */
    private Integer productStock;

    /**
     * 类目编号
     */
    private Integer categoryType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
