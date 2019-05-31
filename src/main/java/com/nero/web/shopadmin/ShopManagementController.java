package com.nero.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nero.dao.ShopCategoryDao;
import com.nero.dto.ShopExecution;
import com.nero.entity.Area;
import com.nero.entity.PersonInfo;
import com.nero.entity.Shop;
import com.nero.entity.ShopCategory;
import com.nero.enums.ShopStateEnum;
import com.nero.service.AreaService;
import com.nero.service.ShopCategoryService;
import com.nero.service.ShopService;
import com.nero.util.HttpServletRequestUtil;
import com.nero.util.ImageUtil;
import com.nero.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/5/16 0016
 * Time: 10:10
 */
@Controller
@RequestMapping("/shopAdmin")
public class ShopManagementController {
    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/getShopInitInfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopInitInfo() {
        Map<String, Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        List<Area> areaList = new ArrayList<>();
        try {
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/registerShop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registerShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 接收并转化相应的参数，包括店铺信息以及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (IOException e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        // 接收图片
        CommonsMultipartFile shopImg;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不可为空。");
            return modelMap;
        }
        // 注册店铺
        if (shop != null && shopImg != null) {
            PersonInfo owner = new PersonInfo();
            // Session TODO
            owner.setUserId(1);
            shop.setOwner(owner);

            // 这里取消原来的将CommonsMultipartFile转换为inputStream的写法
            // File shopImgFile = new File(PathUtil.getImgBasePath() + ImageUtil.getRandomFileName());
            // try {
            //     shopImgFile.createNewFile();
            // } catch (IOException e) {
            //     e.printStackTrace();
            //     modelMap.put("success", false);
            //     modelMap.put("errMsg", e.getMessage());
            //     return modelMap;
            // }

            // try {
            //     inputStreamToFile(shopImg.getInputStream(), shopImgFile);
            // } catch (IOException e) {
            //     e.printStackTrace();
            //     modelMap.put("success", false);
            //     modelMap.put("errMsg", e.getMessage());
            //     return modelMap;
            // }

            ShopExecution shopExecution = null;
            try {
                shopExecution = shopService.addShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
                if (shopExecution.getState() == ShopStateEnum.CHECK.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", shopExecution.getStateInfo());
                }
            } catch (IOException e) {
                e.printStackTrace();
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }

            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺信息。");
            return modelMap;
        }
    }

    private static void inputStreamToFile(InputStream inputStream, File file) {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int byteRead = 0;
            byte[] buffer = new byte[1024];
            while ((byteRead = inputStream.read(buffer)) != -1) {
                os.write(buffer, 0, byteRead);
            }
        } catch (Exception e) {
            throw new RuntimeException("调用inputStreamToFile转换文件格式异常：" + e.getMessage());
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("inputStreamToFile关闭IO异常：" + e.getMessage());
            }
        }
    }
}
