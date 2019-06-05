package com.nero.util;

import com.nero.exceptions.ShopOperationException;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/5/14 0014
 * Time: 11:08
 */
public class ImageUtil {
    // 获取classpath绝对路径
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();

    public static void main(String[] args) throws IOException {

        System.out.println(basePath);
        // Thumbnails.of(new File(basePath + "/picture/5a092dc15d62b.jpg")).size(2560, 1440)
        Thumbnails.of("D:\\图片\\Sample Pictures\\Pictures\\5a092dc15d62b.jpg").size(2560, 1440)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/picture/watermark.png")), 0.25f)
                .outputQuality(0.8f).toFile(basePath + "/picture/press_saber.jpg");
    }

    public static String generateThumbnail(InputStream thumbnail, String fileName, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(fileName);
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File destination = new File(PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnail).size(2560, 1440)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "picture/watermark.png")), 0.25f)
                    .outputQuality(0.8f).toFile(destination);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ShopOperationException("创建缩略图失败：" + e.toString());
        }
        return relativeAddr;
    }

    public static String generateNormalImg(File thumbnail, String fileName, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(fileName);
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnail).size(337, 640).outputQuality(0.5f).toFile(dest);
        } catch (IOException e) {
            throw new RuntimeException("创建缩略图失败：" + e.toString());
        }
        return relativeAddr;
    }

    public static List<String> generateNormalImgs(List<String> imgs, String targetAddr) {
        int count = 0;
        List<String> relativeAddrList = new ArrayList<String>();
        if (imgs != null && imgs.size() > 0) {
            makeDirPath(targetAddr);
            for (String img : imgs) {
                String realFileName = getRandomFileName();
                String extension = getFileExtension(img);
                String relativeAddr = targetAddr + realFileName + count + extension;
                File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
                count++;
                try {
                    Thumbnails.of(img).size(600, 300).outputQuality(0.5f).toFile(dest);
                } catch (IOException e) {
                    throw new RuntimeException("创建图片失败：" + e.toString());
                }
                relativeAddrList.add(relativeAddr);
            }
        }
        return relativeAddrList;
    }

    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            boolean result = dirPath.mkdirs();
            if (!result) {
                throw new ShopOperationException("创建目录出错。");
            }
        }
    }

    /**
     * 获取输入文件名流的扩展名
     *
     * @param fileName
     * @return
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring((fileName.lastIndexOf(".")));
    }

    /**
     * 随机生成文件名，格式为 当前年月日时分秒+五位随机数字
     *
     * @return
     */
    public static String getRandomFileName() {
        // 获取随机五位数
        int rannum = r.nextInt(8999) + 10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + rannum;
    }

    /**
     * storePath是文件的路径或者目录路径，如果storePath是文件则删除文件
     * 如果storePath是目录则删除该目录下所有文件以及该目录
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath) {
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        if (fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) {
                File files[] = fileOrPath.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
        }
        fileOrPath.delete();
    }

}
