package com.matrix.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import sun.misc.BASE64Encoder;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;

/**
 *@description:二维码生成工具类
 *
 *@param
 *@author liwt
 *@date 2019/8/12 14:40
 *@return
 *@version 1.0.1
 */
public class MatrixToImageWriterEx {


    private static final MatrixToLogoImageConfig DEFAULT_CONFIG = new MatrixToLogoImageConfig();

    /**
     *@description:根据内容生成二维码数据
     *
     *@param content 二维码文字内容[为了信息安全性，一般都要先进行数据加密]
     *@param width 二维码照片宽度
     *@param height 二维码照片高度
     *@author liwt
     *@date 2019/8/12 14:42
     *@return
     *@version 1.0.1
     */
    public static BitMatrix createQRCode(String content, int width, int height) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        //设置字符编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix matrix = null;
        try {
            matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return matrix;
    }

    /**
     *@description:写入二维码、以及将照片logo写入二维码中
     *
     *@author liwt
     *@date 2019/8/12 14:42
     *@return
     *@version 1.0.1
     */
    public static BufferedImage writeToFile(BufferedImage imageCode, BufferedImage logo){


        //将logo写入二维码中
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = drawImage(logo, imageCode, DEFAULT_CONFIG);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }


    /**
     *@description:将logo添加到二维码中间
     *
     *@param image    需要绘制的二维码图片
     *@param logoConfig    配置参数
     *@author liwt
     *@date 2019/8/12 14:44
     *@return
     *@version 1.0.1
     */
    private static BufferedImage drawImage(BufferedImage logo, BufferedImage image, MatrixToLogoImageConfig logoConfig) throws IOException {
        if (logoConfig == null) {
            logoConfig = DEFAULT_CONFIG;
        }

        try {
            logo.setRGB(0, 0, BufferedImage.TYPE_INT_BGR);
            Graphics2D g = image.createGraphics();

            //考虑到logo照片贴到二维码中，建议大小不要超过二维码的1/5;
            int width = image.getWidth() / logoConfig.getLogoPart();
            int height = image.getHeight() / logoConfig.getLogoPart();

            //logo起始位置，此目的是为logo居中显示
            int x = (image.getWidth() - width) / 2;
            int y = (image.getHeight() - height) / 2;

            //绘制图
            g.drawImage(logo, x, y, width, height, null);

            g.dispose();
            return logo;
        } catch (Exception e) {   //捕捉异常后不做任何处理，防止图片路径错误而导致二维码生成失败

        }
        return null;
    }


    /**
     *@description:BitMatrix转Base64
     *
     *@param
     *@author liwt
     *@date 2019/8/13 9:07
     *@return
     *@version 1.0.1
     */
    public static String BitMatrixToBase64(BitMatrix bitMatrix) {
        String binary = "";
        BufferedImage image = toBufferedImage(bitMatrix);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //转换成png格式的IO流
        try {
            ImageIO.write(image, "png", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = out.toByteArray();
        //将字节数组转为二进制
        BASE64Encoder encoder = new BASE64Encoder();
        binary = encoder.encodeBuffer(bytes).trim();
        //去掉换行
        binary = binary.replaceAll("\r|\n", "");
        return binary;

    }

    /**
     *@description:BitMatrix转BufferedImage
     *
     *@param
     *@author liwt
     *@date 2019/8/13 9:07
     *@return
     *@version 1.0.1
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }
}
