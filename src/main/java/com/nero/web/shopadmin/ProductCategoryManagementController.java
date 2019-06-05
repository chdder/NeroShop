package com.nero.web.shopadmin;

import com.nero.dto.Result;
import com.nero.entity.ProductCategory;
import com.nero.entity.Shop;
import com.nero.enums.ProductCategoryStateEnum;
import com.nero.service.ProductCategoryService;
import com.nero.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/6/5 0005
 * Time: 15:48
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
    @ResponseBody
    private Result<List<ProductCategory>> listProductCategorys(HttpServletRequest request) {
        // TODO WILL BE REMOVED
        Shop shop = new Shop();
        shop.setShopId(1L);
        request.getSession().setAttribute("currentShop", shop);

        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        List<ProductCategory> list = null;
        if (currentShop != null && currentShop.getShopId() > 0) {
            list = productCategoryService.getProductCategoryList(currentShop.getShopId());
            return new Result<List<ProductCategory>>(true, list);
        } else {
            ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
            return new Result<List<ProductCategory>>(false, ps.getState(), ps.getStateInfo());
        }
    }

    // @RequestMapping(value = "/addproductcategorys", method = RequestMethod.POST)
    // @ResponseBody
    // private Map<String, Object> addProductCategorys(
    //         @RequestBody List<ProductCategory> productCategoryList,
    //         HttpServletRequest request) {
    //     Map<String, Object> modelMap = new HashMap<String, Object>();
    //     Shop currentShop = (Shop) request.getSession().getAttribute(
    //             "currentShop");
    //     for (ProductCategory pc : productCategoryList) {
    //         pc.setShopId(currentShop.getShopId());
    //     }
    //     if (productCategoryList != null && productCategoryList.size() > 0) {
    //         try {
    //             ProductCategoryExecution pe = productCategoryService
    //                     .batchAddProductCategory(productCategoryList);
    //             if (pe.getState() == ProductCategoryStateEnum.SUCCESS
    //                     .getState()) {
    //                 modelMap.put("success", true);
    //             } else {
    //                 modelMap.put("success", false);
    //                 modelMap.put("errMsg", pe.getStateInfo());
    //             }
    //         } catch (RuntimeException e) {
    //             modelMap.put("success", false);
    //             modelMap.put("errMsg", e.toString());
    //             return modelMap;
    //         }
    //
    //     } else {
    //         modelMap.put("success", false);
    //         modelMap.put("errMsg", "请至少输入一个商品类别");
    //     }
    //     return modelMap;
    // }
    //
    // @RequestMapping(value = "/removeproductcategory", method = RequestMethod.POST)
    // @ResponseBody
    // private Map<String, Object> removeProductCategory(Long productCategoryId,
    //                                                   HttpServletRequest request) {
    //     Map<String, Object> modelMap = new HashMap<String, Object>();
    //     if (productCategoryId != null && productCategoryId > 0) {
    //         try {
    //             Shop currentShop = (Shop) request.getSession().getAttribute(
    //                     "currentShop");
    //             ProductCategoryExecution pe = productCategoryService
    //                     .deleteProductCategory(productCategoryId,
    //                             currentShop.getShopId());
    //             if (pe.getState() == ProductCategoryStateEnum.SUCCESS
    //                     .getState()) {
    //                 modelMap.put("success", true);
    //             } else {
    //                 modelMap.put("success", false);
    //                 modelMap.put("errMsg", pe.getStateInfo());
    //             }
    //         } catch (RuntimeException e) {
    //             modelMap.put("success", false);
    //             modelMap.put("errMsg", e.toString());
    //             return modelMap;
    //         }
    //
    //     } else {
    //         modelMap.put("success", false);
    //         modelMap.put("errMsg", "请至少选择一个商品类别");
    //     }
    //     return modelMap;
    // }
}
