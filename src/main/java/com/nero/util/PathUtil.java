package com.nero.util;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/5/14 0014
 * Time: 13:45
 */
public class PathUtil {
    // 获取系统分隔符
    private static String separator = System.getProperty("file.separator");

    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = "D:/Picture/image/";
        } else {
            basePath = "/home/usr/image/";
        }
        basePath = basePath.replace("/", separator);
        return basePath;
    }

    public static String getShopImagePath(Long shopId) {
        String imagePath = "upload/item/shop/" + shopId + "/";
        return imagePath.replace("/", separator);
    }
}
