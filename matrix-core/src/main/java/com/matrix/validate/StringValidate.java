package com.matrix.validate;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;


/**
 * @description: 字符串验证
 *
 * @author Yangcl
 * @date 2018年12月22日 下午3:38:26 
 * @version 1.0.0.1
 */
public class StringValidate {

	/**
	 * @description: 判断字符串是正整数
	 *
	 * @param string
	 * @author Yangcl
	 * @date 2018年12月22日 下午3:38:18 
	 * @version 1.0.0.1
	 */
	public static boolean isNumeric(String string) {
        if (StringUtils.isBlank(string))
            return false;
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(string).matches();  
    }
	
	
}
