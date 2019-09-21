package com.matrix.qrcode;

import java.awt.image.BufferedImage;

/**
 * @description:二维码配置
 *
 * @author: liwt
 * @date: 2019/8/12 14:38
 * @version: 1.0.1
*/
public final class MatrixToImageConfig {

  public static final int BLACK = 0xFF000000;
  public static final int WHITE = 0xFFFFFFFF;
  
  private final int onColor;
  private final int offColor;

  /**
   *@description:默认配置
   *
   *@param
   *@author liwt
   *@date 2019/8/12 14:38
   *@return
   *@version 1.0.1
  */
  public MatrixToImageConfig() {
    this(BLACK, WHITE);
  }

  /**
   *@description:设置颜色像素
   *
   *@param
   *@author liwt
   *@date 2019/8/12 14:38
   *@return
   *@version 1.0.1
  */
  public MatrixToImageConfig(int onColor, int offColor) {
    this.onColor = onColor;
    this.offColor = offColor;
  }

  public int getPixelOnColor() {
    return onColor;
  }

  public int getPixelOffColor() {
    return offColor;
  }

  int getBufferedImageColorModel() {
	  return BufferedImage.TYPE_INT_ARGB;
  }

}