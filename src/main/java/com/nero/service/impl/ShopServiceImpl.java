package com.nero.service.impl;

import com.nero.dao.ShopDao;
import com.nero.dto.ShopExecution;
import com.nero.entity.Shop;
import com.nero.enums.ShopStateEnum;
import com.nero.exceptions.ShopOperationException;
import com.nero.service.ShopService;
import com.nero.util.ImageUtil;
import com.nero.util.PageCalculator;
import com.nero.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/5/15 0015
 * Time: 16:50
 */
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;

    // 添加事务管理
    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, InputStream shopImg, String fileName) {
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            int effectedNum = shopDao.insertShop(shop);
            if (effectedNum <= 0) {
                throw new ShopOperationException("店铺创建失败!");
            } else {
                if (shopImg != null) {
                    // 存储图片
                    try {
                        addShopImg(shop, shopImg, fileName);
                    } catch (Exception e) {
                        throw new ShopOperationException("add shopImg error:" + e.getMessage());
                    }
                    // 更新店铺图片地址
                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0) {
                        throw new ShopOperationException("'update shopImg error!");
                    }
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException("add shop error:" + e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.SUCCESS, shop);
    }

    // 添加店铺图片
    private void addShopImg(Shop shop, InputStream shopImg, String fileName) {
        // 获取店铺图片目录的绝对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImg, fileName, dest);
        shop.setShopImg(shopImgAddr);
    }

    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution shopExecution = new ShopExecution();
        if (shopList != null) {
            shopExecution.setShopList(shopList);
            shopExecution.setCount(count);
        } else {
            shopExecution.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return shopExecution;
    }

    @Override
    public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException {
        if (shop == null || shop.getShopId() == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else {
            // 1.首先判断是否需要更新图片
            try {
                if (shopImgInputStream != null && fileName != null && !"".equals(fileName)) {
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    // 如果原来有图片，则先删除原来的图片
                    if (tempShop.getShopImg() != null) {
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop, shopImgInputStream, fileName);
                }
                // 2.更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            } catch (Exception e) {
                throw new ShopOperationException("modify shop error." + e.getMessage());
            }
        }
    }
}
