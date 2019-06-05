package com.nero.service;

import com.nero.entity.ProductCategory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/6/5 0005
 * Time: 15:43
 */
public interface ProductCategoryService {
    /**
     * 查询指定的店铺下所有商品的类别信息
     *
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategoryList(long shopId);
}
