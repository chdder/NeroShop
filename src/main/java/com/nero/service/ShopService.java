package com.nero.service;

import com.nero.dto.ShopExecution;
import com.nero.entity.Shop;
import com.nero.exceptions.ShopOperationException;

import java.io.File;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/5/15 0015
 * Time: 16:49
 */
public interface ShopService {
    /**
     * 通过id获取店铺
     * @param shopId
     * @return
     */
    Shop getByShopId(long shopId);

    /**
     * 根据 shopCondition 分页返回相应的店铺列表
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
    /**
     * 注册新店铺，包括对图片的处理
     * @param shop
     * @param shopImgInputStream
     * @param fileName
     * @return
     * @throws ShopOperationException
     */
    ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;

    /**
     * 编辑店铺信息，包括对图片的处理
     * @param shop
     * @param shopImgInputStream
     * @param fileName
     * @return
     * @throws ShopOperationException
     */
    ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;

}
