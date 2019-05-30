package com.nero.service;

import com.nero.dto.ShopExecution;
import com.nero.entity.Shop;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/5/15 0015
 * Time: 16:49
 */
public interface ShopService {
    ShopExecution addShop(Shop shop, File file);
}
