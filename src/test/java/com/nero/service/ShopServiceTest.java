package com.nero.service;

import com.nero.BaseTest;
import com.nero.dto.ShopExecution;
import com.nero.entity.Area;
import com.nero.entity.PersonInfo;
import com.nero.entity.Shop;
import com.nero.entity.ShopCategory;
import com.nero.enums.ShopStateEnum;
import com.nero.util.ImageUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/5/15 0015
 * Time: 18:37
 */
public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    public void testAddShop() {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试店铺10");
        shop.setShopDesc("测试10");
        shop.setShopAddr("测试10");
        shop.setPhone("test10");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");

        File file = new File("D:/Picture/saber.jpg");
        ShopExecution shopExecution = shopService.addShop(shop, file);
        assertEquals(ShopStateEnum.CHECK.getState(), shopExecution.getState());
    }

    @Test
    public void testFile() {
        File file = new File("D:\\Picture\\image\\saber.jpg");
        // File file = new File("D:\\Picture\\image\\watermark.png");
        try {
            ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
