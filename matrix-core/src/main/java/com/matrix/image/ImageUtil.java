package com.matrix.image;

import com.matrix.util.SslUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @description:图片处理
 *
 * @author: liwt
 * @date: 2019/8/13 16:17
 * @version: 1.0.1
 */
public class ImageUtil {
    public static BASE64Decoder decoder = new BASE64Decoder();
    public static BASE64Encoder encoder = new BASE64Encoder();
    public static final float DEFAULT_QUALITY = 0.2125f;


    /**
     *@description:图片压缩
     *
     *@param
     *@author liwt
     *@date 2019/8/13 16:11
     *@return
     *@version 1.0.1
     */
    public static BufferedImage compressImage(BufferedImage bfi, float quality, int width, int height, boolean autoSize) {
        BufferedImage targetImage = null;
        if (quality < 0F || quality > 1F) {
            quality = DEFAULT_QUALITY;
        }
        try {
            Image img = bfi;
            //如果用户输入的图片参数合法则按用户定义的复制,负值参数表示执行默认值
            int newwidth = (width > 0) ? width : img.getWidth(null);
            //如果用户输入的图片参数合法则按用户定义的复制,负值参数表示执行默认值
            int newheight = (height > 0) ? height : img.getHeight(null);
            //如果是自适应大小则进行比例缩放
            if (autoSize) {
                // 为等比缩放计算输出的图片宽度及高度
                double Widthrate = ((double) img.getWidth(null)) / (double) width + 0.1;
                double heightrate = ((double) img.getHeight(null)) / (double) height + 0.1;
                double rate = Widthrate > heightrate ? Widthrate : heightrate;
                newwidth = (int) (((double) img.getWidth(null)) / rate);
                newheight = (int) (((double) img.getHeight(null)) / rate);
            }
            //创建目标图像文件
            targetImage = new BufferedImage(newwidth, newheight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = targetImage.createGraphics();
            g.drawImage(img, 0, 0, newwidth, newheight, null);
            //如果添加水印或者文字则继续下面操作,不添加的话直接返回目标文件----------------------
            g.dispose();

        } catch (Exception e) {
            throw new RuntimeException("图片压缩操作异常");
        }
        return targetImage;
    }

    public static BufferedImage base64StringToImage(String base64String) {
        try {
            byte[] bytes1 = decoder.decodeBuffer(base64String);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            BufferedImage bi1 = ImageIO.read(bais);
            return bi1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *@description:Image转Base64
     *
     *@param
     *@author liwt
     *@date 2019/8/13 16:07
     *@return
     *@version 1.0.1
     */
    public static String ImagetoBase64(BufferedImage bfi) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
            ImageIO.write(bfi, "png", baos);//写入流中
            byte[] bytes = baos.toByteArray();//转换成字节

            String png_base64 = encoder.encodeBuffer(bytes).trim();//转换成base64串
            png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
            return png_base64;
        } catch (Exception ex) {

        }
        return "";
    }

    /**
     *@description:图片合成
     *
     *@param
     *@author liwt
     *@date 2019/8/13 16:10
     *@return
     *@version 1.0.1
     */
    public static BufferedImage addWaterMark(BufferedImage bfi, BufferedImage bfiMark, int x, int y, float alpha) {
        BufferedImage targetImage = null;
        try {
            // 加载待处理图片文件
            Image img = bfi;

            //创建目标图象文件
            targetImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = targetImage.createGraphics();
            g.drawImage(img, 0, 0, null);

            // 加载水印图片文件
            Image markImg = bfiMark;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g.drawImage(markImg, x, y, null);
            g.dispose();
        } catch (Exception e) {
            throw new RuntimeException("添加图片操作异常");
        }
        return targetImage;

    }


    /**
     *@description:图片添加文字
     *
     *@param
     *@author liwt
     *@date 2019/8/13 16:10
     *@return
     *@version 1.0.1
     */
    public static BufferedImage addTextMark(BufferedImage bfi, String text, Font font, Color color, float x, float y, float alpha) {
        BufferedImage targetImage = null;
        try {
            Font Dfont = (font == null) ? new Font("宋体", 20, 13) : font;
            Image img = bfi;
            //创建目标图像文件
            targetImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = targetImage.createGraphics();
            g.drawImage(img, 0, 0, null);
            g.setColor(color);
            g.setFont(Dfont);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g.drawString(text, x, y);
            g.dispose();
        } catch (Exception e) {
            throw new RuntimeException("添加文字操作异常");
        }
        return targetImage;
    }

    /**
     * @description 下载网络图片(支持htts)

     * @param
     * @author: gengsl
     * @date: 2019/8/26 11:07
     * @return
     * @version: 1.0.1
     */
    public static BufferedImage getPicture(String imageUrl) {
        int timeOut = 3000;
        try {
            URL u = new URL(imageUrl);
            if ("https".equalsIgnoreCase(u.getProtocol())) {
                SslUtils.ignoreSsl();
            }
            URLConnection conn = u.openConnection();
            conn.setConnectTimeout(timeOut);
            conn.setReadTimeout(timeOut);
            BufferedImage bufferedImage = ImageIO.read(conn.getInputStream());
            return bufferedImage;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
