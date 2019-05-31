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
    ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;
}
