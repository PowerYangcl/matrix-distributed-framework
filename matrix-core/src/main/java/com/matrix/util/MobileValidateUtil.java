package com.matrix.util;
import java.util.regex.Pattern;

/**
 * @description: 验证手机/座机号码
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年12月25日 下午8:29:42 
 * @version 1.0.0.1
 */
public class MobileValidateUtil {
	
		// 简单手机号码校验，校验手机号码的长度和1开头 
		private static final String SIMPLE_PHONE_CHECK = "^(?:\\+86)?1\\d{10}$";  
		// 座机电话格式验证
	    private static final String PHONE_CALL_PATTERN = "^(?:\\(\\d{3,4}\\)|\\d{3,4}-)?\\d{7,8}(?:-\\d{1,4})?$";  
	    // 中国电信号码格式验证 手机段： 133,153,180,181,189,177,1700,173 
	    private static final String CHINA_TELECOM_PATTERN = "(?:^(?:\\+86)?1(?:33|53|7[37]|8[019])\\d{8}$)|(?:^(?:\\+86)?1700\\d{7}$)";  
	    // 中国联通号码格式验证 手机段：130,131,132,155,156,185,186,145,176,1707,1708,1709,175 
	    private static final String CHINA_UNICOM_PATTERN = "(?:^(?:\\+86)?1(?:3[0-2]|4[5]|5[56]|7[56]|8[56])\\d{8}$)|(?:^(?:\\+86)?170[7-9]\\d{7}$)";  
	    // 中国移动号码格式验证  手机段：134,135,136,137,138,139,150,151,152,157,158,159,182,183,184  ,187,188,147,178,1705
	    private static final String CHINA_MOBILE_PATTERN = "(?:^(?:\\+86)?1(?:3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(?:^(?:\\+86)?1705\\d{7}$)";  
	  
	    // 仅手机号格式校验 
	    private static final String PHONE_PATTERN = new StringBuilder(300).append(CHINA_MOBILE_PATTERN).append("|")  
	            .append(CHINA_TELECOM_PATTERN).append("|")  
	            .append(CHINA_UNICOM_PATTERN).toString();  
	  
	    // 手机和座机号格式校验  
	    private static final String PHONE_TEL_PATTERN = new StringBuilder(350).append(PHONE_PATTERN).append("|").append("(")  
	            .append(PHONE_CALL_PATTERN).append(")").toString();  
	  
	    /**
	     * @description: 匹配多个号码
	     * 	以,、或空格隔开的格式，如 17750581369、13306061248、(596)3370653,17750581369,13306061248 (0596)3370653 
	     *
	     * @param input
	     * @param separator 可以自己指定分隔符，如"、, "表示可以以顿号、逗号和空格分隔 
	     * @author Yangcl
	     * @date 2017年12月22日 下午3:16:04 
	     * @version 1.0.0.2
	     */
	    public static boolean checkMultiPhone(String input, String separator) {  
	        separator = escapeMetacharacterOfStr(separator);  
	        String regex = "^(?!.+[" + separator + "]$)(?:(?:(?:(?:\\(\\d{3,4}\\)|\\d{3,4}-)?\\d{7,8}(?:-\\d{1,4})?)|(?:1\\d{10}))(?:[" + separator + "]|$))+$";  
	        return match(regex, input);  
	    }  
	  
	  
	    /**
	     * @description: 仅手机号码校验|通用的手机号码验证类，联调 移动 电信等等。 
	     *
	     * @param input 电话号码
	     * @author Yangcl
	     * @date 2017年12月22日 下午3:17:55 
	     * @version 1.0.0.2
	     */
	    public static boolean isPhone(String input) {  
	        return match(PHONE_PATTERN, input);  
	    }  
	  
	    /**
	     * @description: 手机号或座机号校验 
	     *
	     * @param input
	     * @return 
	     * @author Yangcl
	     * @date 2017年12月22日 下午3:18:07 
	     * @version 1.0.0.2
	     */
	    public static boolean isPhoneOrTel(String input) {  
	        return match(PHONE_TEL_PATTERN, input);  
	    }  
	  
	    /**
	     * @description: 验证电话号码的格式
	     *
	     * @param str 校验电话字符串 
	     * @return 返回true,否则为false 
	     * @author Yangcl
	     * @date 2017年12月22日 下午3:18:35 
	     * @version 1.0.0.2
	     */
	    public static boolean isPhoneCallNum(String str) {  
	        return match(PHONE_CALL_PATTERN, str);  
	    }  
	  
	    /**
	     * @description: 验证【电信】手机号码的格式 
	     *
	     * @param str 校验手机字符串 
	     * @return 返回true,否则为false 
	     * @author Yangcl
	     * @date 2017年12月22日 下午3:19:09 
	     * @version 1.0.0.2
	     */
	    public static boolean isChinaTelecomPhoneNum(String str) {  
	        return match(CHINA_TELECOM_PATTERN, str);  
	    }  
	  
	    /**
	     * @description: 验证【联通】手机号码的格式
	     *
	     * @param str 校验手机字符串 
	     * @return 返回true,否则为false 
	     * @author Yangcl
	     * @date 2017年12月22日 下午3:19:09 
	     * @version 1.0.0.2
	     */
	    public static boolean isChinaUnicomPhoneNum(String str) {  
	        return match(CHINA_UNICOM_PATTERN, str);  
	    }  
	  
	    /**
	     * @description:验证【移动】手机号码的格式  
	     *
	     * @param str  校验手机字符串 
	     * @return 返回true,否则为false 
	     * @author Yangcl
	     * @date 2017年12月22日 下午3:19:09 
	     * @version 1.0.0.2
	     */
	    public static boolean isChinaMobilePhoneNum(String str) {  
	        return match(CHINA_MOBILE_PATTERN, str);  
	    }  
	  
	    /**
	     * @description: 简单手机号码校验，校验手机号码的长度和1开头
	     *
	     * @param str
	     * @return 
	     * @author Yangcl
	     * @date 2017年12月22日 下午3:19:09 
	     * @version 1.0.0.2
	     */
	    public static boolean isPhoneSimple(String str) {  
	        return match(SIMPLE_PHONE_CHECK, str);  
	    }  
	  
	    /**
	     * @description: 匹配函数 
	     * 
	     * @author Yangcl
	     * @date 2017年12月22日 下午3:19:09 
	     * @version 1.0.0.2
	     */
	    private static boolean match(String regex, String input) {  
	        return Pattern.matches(regex, input);  
	    }  
	    
	    /**
	     * @description: 转义字符串中的[]-^\元字符
	     *
	     * @param input
	     * @author Yangcl
	     * @date 2017年12月22日 下午3:17:30 
	     * @version 1.0.0.2
	     */
	    private static String escapeMetacharacterOfStr(String input) {  
	        String regex = "[-^\\[\\]\\\\]";  
	        return input.replaceAll(regex, "\\\\$0");  
	    }
}
