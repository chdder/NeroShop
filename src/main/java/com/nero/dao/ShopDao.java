package com.nero.dao;

import com.nero.entity.Shop;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/5/13 0013
 * Time: 16:53
 */
public interface ShopDao {
    /**
     * 新增店铺
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺信息
     * @param shop
     * @return
     */
    int updateShop(Shop shop);
}
