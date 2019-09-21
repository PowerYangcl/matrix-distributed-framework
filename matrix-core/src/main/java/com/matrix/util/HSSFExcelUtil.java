package com.matrix.util;

import com.matrix.annotation.ExcelAttributePosition;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: excel工具类
 *
 * @author:gengsl
 * @date: 2019/8/2 13:34
 * @version: 1.0.1
 */
public class HSSFExcelUtil {

    //日志
    private static Logger logger = LoggerFactory.getLogger(HSSFExcelUtil.class);

    /**
     * @description: 获取远程文件
     *
     * @param url
     * @return
     * @author wangjie
     * @date 2019年7月24日 下午2:49:49
     * @version 1.0.0.1
     */
    public static HSSFSheet getExcelSheet(String url) {
        HSSFSheet xssfSheet = null;
        try {
            URL importUrl = new URL(url);
            if ("https".equalsIgnoreCase(importUrl.getProtocol())) {
                SslUtils.ignoreSsl();
            }
            HttpURLConnection conn = (HttpURLConnection) importUrl.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            POIFSFileSystem fs;
            fs = new POIFSFileSystem(is);
            HSSFWorkbook xssfWorkbook = new HSSFWorkbook(fs);
            xssfSheet = xssfWorkbook.getSheetAt(0);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("url:{}获取excel失败", url);
        }
        return xssfSheet;
    }

    /**
     * @description: excel转换成对应实体类
     *
     * @param
     * @author:gengsl
     * @date: 2019/8/5 12:59
     * @return
     * @version: 1.0.1
     */
    public static <T> List<T> convertT(Class<T> instance, HSSFSheet hssfSheet) {
        List<T> result = new ArrayList<>();
        int lastRowNum = hssfSheet.getLastRowNum();

        for (int rowIndex = 1; rowIndex <= lastRowNum; rowIndex++) {
            HSSFRow hssfRow = hssfSheet.getRow(rowIndex);
            if (hssfRow.getCell(0) == null) {
                continue;
            }
            if (StringUtils.isEmpty(hssfRow.getCell(0).getStringCellValue())) {
                continue;
            }
            int colunmIndex = 0;
            try {
                int firstCellNum = hssfRow.getFirstCellNum();//行首
                int lastCellNum = hssfRow.getLastCellNum();//行尾
                T object = instance.newInstance();
                Field[] fieldArray = object.getClass().getDeclaredFields();
                for (colunmIndex = firstCellNum; colunmIndex <= lastCellNum; colunmIndex++) {
                    HSSFCell hssfCell = hssfRow.getCell(colunmIndex);//第几行第几列的单元格
                    for (Field field : fieldArray) {
                        field.setAccessible(true);
                        ExcelAttributePosition excelAttributePosition = field.getAnnotation(ExcelAttributePosition.class);
                        if (excelAttributePosition != null && excelAttributePosition.value() != null && excelAttributePosition.value().equals(String.valueOf(colunmIndex))) {
                            field.set(object, hssfCell.getStringCellValue());
                        }
                    }
                }
                result.add(object);
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.warn("excel转换异常，excel的第{}行，第{}列有问题", rowIndex, colunmIndex);
            }
        }
        return result;
    }

}
