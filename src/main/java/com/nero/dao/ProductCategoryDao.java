package com.nero.dao;

import com.nero.entity.ProductCategory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/6/5 0005
 * Time: 10:21
 */
public interface ProductCategoryDao {
    /**
     * 通过 shopId 查询店铺类别
     * @param shopId
     * @return
     */
    List<ProductCategory> queryProductCategoryList(long shopId);
}
