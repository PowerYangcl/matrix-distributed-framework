package com.matrix.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:处理字符工具
 *
 * @author: liwt
 * @date: 2019/7/30 14:18
 * @version: 1.0.1
*/
public class StringUtil {

    /**
     *@description: 格式化BigDecimal不保留小数
     *
     *@param
     *@author liwt
     *@date 2019/7/30 14:18
     *@return
     *@version 1.0.1
    */
    public static String decimailFormatNoScale(BigDecimal value) {
        if (null == value) {
            value = BigDecimal.ZERO;
        }
        return String.valueOf(value.setScale(0, RoundingMode.HALF_UP).intValue());
    }

    /**
     *@description:格式化BigDecimal为保留2位小数
     *
     *@param
     *@author liwt
     *@date 2019/7/30 14:18
     *@return
     *@version 1.0.1
    */
    public static String decimailFormat2Scale(BigDecimal value) {
        if (null == value) {
            value = new BigDecimal("0.00");
        }
        return String.format("%.2f", value);
    }

    /**
     *@description:处理为字符串
     *
     *@param
     *@author liwt
     *@date 2019/7/30 14:19
     *@return
     *@version 1.0.1
    */
    public static String append(Object... args) {
        if (null == args) {
            return "";
        }
        StringBuilder str = new StringBuilder();
        for (Object o : args) {
            str.append(o);
        }
        return str.toString();
    }

   /**
    *@description:生成字符和数字的随机字符串
    *
    *@param
    *@author liwt
    *@date 2019/7/30 14:19
    *@return
    *@version 1.0.1
   */
   public static String genRandomStr(int len,Long prefix) {
       len = len-1;
       // 35是因为数组是从0开始的，26个字母+10个数字
       final int maxNum = 26;
       final int maxNum2 = 10;
       int i; // 生成的随机数
       int count = 0; // 生成的密码的长度
       char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
       char[] number = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
       StringBuffer pwd = new StringBuffer("");
       Random r = new Random();
       while (count < len) {
           // 生成随机数，取绝对值，防止生成负数，
           // 生成的数最大为26-1
           if (count < 1) {// 第一个为字母
               i = Math.abs(r.nextInt(maxNum));
               if (i >= 0 && i < str.length) {
                   pwd.append(str[i]);
                   pwd.append(prefix);
                   count++;
               }
           } else {// 剩余为数字
               i = Math.abs(r.nextInt(maxNum2));
               if (i >= 0 && i < number.length) {
                   pwd.append(number[i]);
                   count++;
               }
           }
       }
       return pwd.toString();
   }

    /**
     *@description:生成只有数字的字符串
     *
     *@param
     *@author liwt
     *@date 2019/7/30 14:19
     *@return
     *@version 1.0.1
    */
    public static String genRandomStrByNumber(int len) {
        // 35是因为数组是从0开始的，26个字母+10个数字
        final int maxNum = 10;
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < len) {
            // 生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }

    /**
     *@description:隐藏手机号码的第四位到第七位
     *
     *@param
     *@author liwt
     *@date 2019/7/30 14:19
     *@return
     *@version 1.0.1
    */
    public static String hideMobileNumber(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return "";
        }
        mobile = mobile.trim();
        return new StringBuilder(mobile.substring(0, 3)).append("****").append(mobile.substring(7)).toString();
    }

    /**
     *@description:当用户名长度大于2时，掐头去尾进行隐藏
     *
     *@param
     *@author liwt
     *@date 2019/7/30 14:19
     *@return
     *@version 1.0.1
    */
    public static String hideUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return "";
        }
        username = username.trim();
        if (username.length() < 4) {
            return username;
        }
        return new StringBuilder(username.substring(0, 1)).append("**").append(username.substring(username.length() - 1)).toString();
    }

    /**
     *@description:将空对象转换为空串
     *
     *@param
     *@author liwt
     *@date 2019/7/30 14:19
     *@return
     *@version 1.0.1
    */
    public static String conversion2Str(Object o) {
        if (null == o)
            return "";
        return String.valueOf(o);
    }

    /**
     *@description:过滤特殊字符
     *
     *@param
     *@author liwt
     *@date 2019/7/30 14:19
     *@return
     *@version 1.0.1
    */
    public static String characterFilter(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        // String regEx =
        // "[`~!@#$%^&+=|{}':;',\\\\\\[\\].<>/?~！@#￥%……&——+|{}【】‘；：”“’。，、？]";
        // String regEx =
        // "[`~!@#$%^&+=|{}':;',\"\\\\\\[\\].<>/?~！@#￥%……&——+|{}【】‘；：”“’。，、？|\\+\\-\\&\\|\\!\\(\\)\\{\\}\\[\\]\\^\\~\\*\\?\\:\\\\]";
        String regEx = "[`~!@#$%^&=|{}':;',\"\\\\.<>/?￥%……&——|{}【】‘；：”“’。，、？|\\|\\{\\}\\[\\]\\^\\~\\?\\\\]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        String mString = m.replaceAll("").trim();
        return mString.substring(0, mString.length() > 50 ? 50 : mString.length());
    }

    /**
     *@description:过滤特殊字符
     *
     *@param
     *@author liwt
     *@date 2019/7/30 14:20
     *@return
     *@version 1.0.1
    */
    public static String replace(String str) {
        // if (StringUtils.isBlank(str)) { return null; }
        // String regEx = "[0-9ml°]+";
        // Pattern p = Pattern.compile(regEx);
        // Matcher m = p.matcher(str);
        // return m.replaceAll("").trim();
        return str;
    }

    /**
     *@description:判断字符是否全英文
     *
     *@param
     *@author liwt
     *@date 2019/7/30 14:20
     *@return
     *@version 1.0.1
    */
    public static boolean isEnglishStr(String word) {
        boolean sign = true; // 初始化标志为为'true'
        int size = word.length();
        for (int i = 0; i < size; i++) {
            if (!(word.charAt(i) >= 'A' && word.charAt(i) <= 'Z') && !(word.charAt(i) >= 'a' && word.charAt(i) <= 'z')) {
                sign = false;
            }
        }
        return sign;
    }

    /**
     *@description:随机生成数字
     *
     *@param
     *@author liwt
     *@date 2019/7/30 14:20
     *@return
     *@version 1.0.1
    */
    public static String genRandomNumber(int length) {
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        final int maxNum = str.length;

        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < length) {
            // 生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }

    /**
     *@description:随机生成字符
     *
     *@param
     *@author liwt
     *@date 2019/7/30 14:20
     *@return
     *@version 1.0.1
    */
    public static String genRandomChar(int length) {
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        final int maxNum = str.length;

        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < length) {
            // 生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }

    /**
     *@description:获取UUID随机字符串
     *
     *@param
     *@author liwt
     *@date 2019/7/30 14:20
     *@return
     *@version 1.0.1
    */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "").toLowerCase();
    }


    /**
     *@description:过滤html标签，包括script、style、html、空格、回车标签
     *
     *@param
     *@author liwt
     *@date 2019/8/21 19:55
     *@return
     *@version 1.0.1
    */
    public static String delHTMLTag(String htmlStr){
        if(htmlStr==null){
            return "";
        }
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
        String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签

        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签

        return htmlStr.trim(); // 返回文本字符串
    }
}
