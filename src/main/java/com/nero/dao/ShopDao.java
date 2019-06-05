package com.nero.dao;

import com.nero.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/5/13 0013
 * Time: 16:53
 */
public interface ShopDao {
    /**
     * 分页查询店铺，可输入的条件有：店铺名(模糊查询)，店铺状态，店铺类别，区域ID，owner
     *
     * @param shopCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex,
                             @Param("pageSize") int pageSize);

    /**
     * 返回 queryShopList 店铺的总数量
     *
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition")Shop shopCondition);

    /**
     * 根据id查找店铺
     *
     * @param shopId
     * @return
     */
    Shop queryByShopId(Long shopId);

    /**
     * 新增店铺
     *
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺信息
     *
     * @param shop
     * @return
     */
    int updateShop(Shop shop);
}
