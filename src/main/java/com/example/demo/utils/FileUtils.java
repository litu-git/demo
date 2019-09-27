package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Base64;

/**
 * 文件及文件夹工具类
 *
 * @Author: li_tu@suixingpay.com
 * @Date: 2019-09-27 15:28
 * @Version: 1.0
 */
@Slf4j
public class FileUtils {

    /**
     * 将本地图片进行Base64位编码
     *
     * @Author: li_tu@suixingpay.com
     * @Date: 2019-09-27 15:28
     * @Version: 1.0
     */
    public static String encodeImgageToBase64(File imageFile) {
        if (imageFile == null) {
            log.error("将本地图片进行Base64位编码|图片文件为null.");
            return "";
        }
        if (!imageFile.exists()) {
            log.error("将本地图片进行Base64位编码|图片文件不存在.");
            return "";
        }

        log.info("将本地图片进行Base64位编码|文件名:" + imageFile.getName());
        ByteArrayOutputStream outputStream = null;
        String encodeStr = "";
        try {
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);

            // 对字节数组Base64编码
            // 返回Base64编码过的字节数组字符串
            encodeStr = Base64.getEncoder().encodeToString(outputStream.toByteArray()).replaceAll("\r|\n", "");
        } catch (MalformedURLException e1) {
            log.error("将本地图片进行Base64位编码|异常：", e1);
        } catch (IOException e) {
            log.error("将本地图片进行Base64位编码|异常：", e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("将本地图片进行Base64位编码|关闭流错误.");
                }
            }
        }

        return encodeStr;
    }
}
