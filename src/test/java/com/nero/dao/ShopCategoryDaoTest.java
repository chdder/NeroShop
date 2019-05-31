package com.nero.dao;

import static org.junit.Assert.assertEquals;
import com.nero.BaseTest;
import com.nero.entity.Area;
import com.nero.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/5/31 0031
 * Time: 15:03
 */
public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void testQueryShopCategory() {
        // List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(new ShopCategory());
        // assertEquals(1, shopCategoryList.size());

        ShopCategory testShopCategory = new ShopCategory();
        ShopCategory parentShopCategory = new ShopCategory();
        parentShopCategory.setShopCategoryId(1L);
        testShopCategory.setParent(parentShopCategory);
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(testShopCategory);
        assertEquals(1, shopCategoryList.size());

    }
}
